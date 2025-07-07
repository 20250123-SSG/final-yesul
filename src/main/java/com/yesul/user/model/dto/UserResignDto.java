package com.yesul.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResignDto {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;
}
