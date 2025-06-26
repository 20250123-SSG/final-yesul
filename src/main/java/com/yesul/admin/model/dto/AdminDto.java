package com.yesul.admin.model.dto;

import lombok.*;

@NoArgsConstructor
@Getter
public class AdminDto {  // DB 조회된 admin 담는 용도
    private Long id;
    private String loginId;
    private String loginPwd;
}
