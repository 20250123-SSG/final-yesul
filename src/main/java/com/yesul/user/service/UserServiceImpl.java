package com.yesul.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.yesul.user.model.dto.response.UserProfileResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.yesul.utill.ImageUpload;
import com.yesul.exception.handler.UpdateFailedException;
import com.yesul.exception.handler.UserNotFoundException;
import com.yesul.user.model.dto.request.UserUpdateRequestDto;
import com.yesul.exception.handler.EntityNotFoundException;
import com.yesul.user.model.dto.request.UserRegisterRequestDto;
import com.yesul.user.model.entity.User;
import com.yesul.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final ImageUpload imageUpload;


    /**
     * 일반 사용자 회원가입 처리 (이메일 인증 대기 상태로 저장 및 인증 메일 발송)
     *
     * @param userRegisterRequestDto 회원가입 요청 DTO
     * @return 저장된 User 엔티티 (이메일 발송 등에 활용)
     * @throws IllegalArgumentException 중복된 이메일 또는 닉네임이 있을 경우
     */
    @Override
    @Transactional
    public User registerUser(UserRegisterRequestDto userRegisterRequestDto) {
        if (isEmailDuplicated(userRegisterRequestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (isNicknameDuplicated(userRegisterRequestDto.getNickname())) {
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }

        // DTO → Entity
        User user = userRegisterRequestDto.toEntity(passwordEncoder.encode(userRegisterRequestDto.getPassword()));
        user.generateEmailCheckToken();
        return userRepository.save(user);

    }

    /**
     * 이메일 중복 확인
     *
     * @param email 확인할 이메일
     * @return 중복되면 true, 아니면 false
     */
    @Override
    public boolean isEmailDuplicated(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * 닉네임 중복 확인
     *
     * @param nickname 확인할 닉네임
     * @return 중복되면 true, 아니면 false
     */
    @Override
    public boolean isNicknameDuplicated(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    /**
     * 이메일 인증 메일 발송
     *
     * @param user 인증 메일을 받을 사용자 엔티티
     */
    @Override
    @Async("asyncExecutor")
    public void sendVerificationEmail(User user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // true는 멀티파트 메시지 허용 (파일 첨부 등), UTF-8 인코딩
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(user.getEmail()); // 수신자 이메일 주소
            mimeMessageHelper.setSubject("Yesul 회원가입 이메일 인증을 완료해주세요."); // 이메일 제목

            // 인증 링크 생성: 서버 주소와 인증 엔드포인트, 사용자 이메일, 토큰 포함
            String verificationLink = "http://localhost:8080/user/verify-email?email=" + user.getEmail() + "&token=" + user.getEmailCheckToken();
            String content = "안녕하세요, " + user.getName() + "님!<br><br>"
                    + "Yesul에 가입해 주셔서 감사합니다. 아래 링크를 클릭하여 회원가입을 완료해주세요: <br><br>"
                    + "<a href=\"" + verificationLink + "\">Yesul 회원가입 완료하기</a>"
                    + "<br><br>이 링크는 <strong>15분</strong> 동안 유효합니다. 이메일 인증을 완료하시면 Yesul의 모든 서비스를 이용할 수 있습니다."
                    + "<br><br>감사합니다.<br>Yesul 드림";
            mimeMessageHelper.setText(content, true); // true는 HTML 형식으로 전송
            javaMailSender.send(mimeMessage);
            log.info("인증 이메일 발송 성공: {}", user.getEmail());
        } catch (MessagingException e) {
            log.error("인증 이메일 발송 실패: {}", user.getEmail(), e);
            throw new RuntimeException("이메일 발송 중 오류가 발생했습니다. 다시 시도해주세요.", e);
        }
    }

    /**
     * 이메일 인증 링크를 통한 사용자 활성화 처리
     *
     * @param email 인증을 요청한 사용자의 이메일
     * @param token 이메일로 발송된 인증 토큰
     * @return 인증 성공 시 true, 실패 시 false
     */
    @Override
    @Transactional
    public boolean verifyEmail(String email, String token) {
        // 1. 이메일로 사용자 조회
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            log.warn("이메일 인증 실패: 해당 이메일을 가진 사용자를 찾을 수 없습니다. 이메: {}", email);
            throw new EntityNotFoundException("이메일 인증 실패: 사용자 정보를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();

        // 2. 이미 인증된 계정인지 확인
        if (user.getStatus() == '1') {
            log.info("이메일 인증 성공 (이미 활성화된 계정): {}", email);
            return true;
        }

        // 3. 토큰 유효성 및 만료 시간 검사
        if (user.getEmailCheckToken() == null || !user.getEmailCheckToken().equals(token) ||
                user.getEmailCheckTokenGeneratedAt() == null ||
                user.getEmailCheckTokenGeneratedAt().plusMinutes(15).isBefore(LocalDateTime.now())) {
            
            log.warn("이메일 인증 실패: 유효하지 않거나 만료된 토큰입니다. 이메일: {}", email);
            user.refreshEmailCheckTokenAndMarkUnverified();
            userRepository.save(user);
            try {
                sendVerificationEmail(user);
                log.info("만료된 토큰으로 인한 인증 실패, 새로운 인증 이메일 재발송 완료: {}", user.getEmail());
            } catch (RuntimeException e) {
                log.error("만료된 토큰으로 인한 재발송 이메일 발송 실패: {}", user.getEmail(), e);
            }
            return false;
        }

        user.completeSignUp();
        userRepository.save(user);

        log.info("이메일 인증 완료 및 계정 활성화: {}", user.getEmail());
        return true;
    }

    /**
     * 이메일로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return User 엔티티 (Optional)
     */
    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 사용자 프로필을 업데이트하는 메서드
     * @param userId 업데이트할 사용자의 ID
     * @param dto 업데이트할 데이터를 담은 DTO
     */
    @Override
    @Transactional
    public User updateUserProfile(Long userId, UserUpdateRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        if (dto.getName() != null)     user.updateName(dto.getName());
        if (dto.getNickname() != null) user.updateNickname(dto.getNickname());
        if (dto.getBirthday() != null) user.updateBirthday(dto.getBirthday());
        if (dto.getAddress() != null)  user.updateAddress(dto.getAddress());

        if (dto.getProfileImage() != null && !dto.getProfileImage().isEmpty()) {
            String url = imageUpload.uploadAndGetUrl("profile", dto.getProfileImage());
            user.updateProfileUrl(url);
        }

        return user;
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resignUser(Long userId, String rawPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
        }

        String resignedIdentifier = "resigned_user_" + user.getId();
        user.setName("탈퇴한사용자");
        user.setNickname(resignedIdentifier); // 유니크 제약조건이 있다면 고유값으로 변경
        user.setEmail(resignedIdentifier + "@example.com"); // 이메일도 고유값으로 변경
        user.setPassword(""); // 비밀번호 비우기
        user.setAddress(null);
        user.setBirthday(null);
        user.setProfile(null);
        user.setEmailCheckToken(null);
        user.setStatus('3');

        userRepository.save(user);
        log.info("사용자 탈퇴 처리 완료: ID {}", userId);
    }

    @Override
    @Transactional
    public void resetPassword(String email, String token, String newPassword) {
        User u = userRepository.findByEmail(email)
                .filter(x -> token.equals(x.getEmailCheckToken()))
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않거나 만료된 링크입니다."));

        if (u.getEmailCheckTokenGeneratedAt().plusMinutes(15)
                .isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("링크가 만료되었습니다.");
        }

        u.setPassword(passwordEncoder.encode(newPassword));
        u.setEmailCheckToken(null);
        u.setEmailCheckTokenGeneratedAt(null);
        userRepository.save(u);
    }
}