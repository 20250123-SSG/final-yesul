package com.yesul.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.yesul.user.model.entity.User;

@Getter
@Setter
@ToString
public class UserRegisterDto {

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 30, message = "비밀번호는 8~30자로 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String confirmPwd;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 20, message = "이름은 20자를 초과할 수 없습니다.")
    private String name; // User 엔티티의 name 필드와 매핑

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @Size(max = 50, message = "이메일은 50자를 초과할 수 없습니다.")
    private String email; // User 엔티티의 email 필드와 매핑 (기존 username 역할)

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(max = 20, message = "닉네임은 20자를 초과할 수 없습니다.")
    private String nickname;

    @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$", message = "생년월일 8자리를 YYYYMMDD 형식으로 입력해주세요.")
    private String birthday;

    @Size(max = 100, message = "주소는 100자를 초과할 수 없습니다.")
    private String address;

    // DTO에서 To Entity
    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(this.email) // DTO의 email을 User 엔티티의 email에 매핑
                .password(encodedPassword)
                .name(this.name)
                .nickname(this.nickname)
                .birthday(this.birthday)
                .address(this.address)
                .type('1') // 일반 사용자
                .status('2') // 초기 상태: 미인증
                .profile(null) // 프로필 이미지 URL (현재는 null)
                .build();
    }
}