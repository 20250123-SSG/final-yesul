package com.yesul.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class UserUpdateDto {

    // 유효성 검사 그룹 인터페이스
    public interface ValidationGroups {
        interface Update {} // 일반적인 프로필 업데이트 시 적용
        interface PasswordUpdate {} // 비밀번호 변경 시 적용
    }

    // Editable fields from User entity
    // HTML 폼에서 th:field="*{name}" 등으로 바인딩되므로 email도 포함 (읽기 전용이지만)
    @Size(max = 20, message = "이름은 20자를 초과할 수 없습니다.", groups = ValidationGroups.Update.class)
    private String name;

    @Size(max = 20, message = "닉네임은 20자를 초과할 수 없습니다.", groups = ValidationGroups.Update.class)
    private String nickname;

    @Pattern(regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$", message = "생년월일 8자리를 YYYYMMDD 형식으로 입력해주세요.", groups = ValidationGroups.Update.class)
    private String birthday;

    @Size(max = 100, message = "주소는 100자를 초과할 수 없습니다.", groups = ValidationGroups.Update.class)
    private String address;

    @Email(message = "유효한 이메일 주소를 입력해주세요.", groups = ValidationGroups.Update.class)
    @Size(max = 50, message = "이메일은 50자를 초과할 수 없습니다.", groups = ValidationGroups.Update.class)
    private String email; // HTML에서 readonly 이지만, th:field 바인딩을 위해 필요

    // For password change (optional for update)
    @Size(min = 8, max = 30, message = "새 비밀번호는 8~30자로 입력해주세요.", groups = ValidationGroups.PasswordUpdate.class)
    private String newPassword;
    private String confirmPassword;

    // For profile image upload
    private MultipartFile profileImage;

    // Original profile URL (if not changing file, this helps retain the current URL)
    private String profile; // hidden input 값 수신
}