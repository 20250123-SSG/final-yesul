package com.yesul.admin.model.dto.userInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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
    private String birthday;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "status") // 관리자 수정가능
    private Character status;

    public UserInfoListAdminDto(Long id, String email, String nickname, String birthday,Character status, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.birthday = birthday;
        this.status = status;
        this.createdAt = createdAt;
    }
}
