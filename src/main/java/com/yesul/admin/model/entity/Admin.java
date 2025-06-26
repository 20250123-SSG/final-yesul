package com.yesul.admin.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "admin")
@Table(name = "admin")
public class Admin {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "login_id")
    private String loginId;
    @Column(name = "login_pwd")
    private String loginPwd;
}

