package com.yesul.admin.model.dto.userInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserInfoListAdminDto {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "email")
    private String email;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "status") // 관리자 수정가능
    private String status;
}
