package com.yesul.user.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.*;

import com.yesul.common.BaseTimeEntity;
import com.yesul.community.model.entity.Like;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "password", length = 68)
    private String password;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "birthday", length = 8)
    private String birthday;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "type", nullable = false)
    private Character type;

    @Column(name = "status", nullable = false, length = 1)
    private Character status;

    @Column(name = "profile", columnDefinition = "TEXT")
    private String profile;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email_check_token")
    private String emailCheckToken;

    @Column(name = "email_check_token_generated_at")
    private LocalDateTime emailCheckTokenGeneratedAt;

    @Column(name = "description")
    private String description;

    @Column(name = "point")
    private Integer point;

    // 이메일 인증 토큰 설정
    public void generateEmailCheckToken() {
        this.emailCheckToken = java.util.UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void refreshEmailCheckTokenAndMarkUnverified() {
        this.generateEmailCheckToken();   // token + generatedAt 설정
        this.status = '2';                // now unverified
    }

    public void completeSignUp() {
        this.status = '1';
        this.emailCheckToken = null;
        this.emailCheckTokenGeneratedAt = null;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateProfileUrl(String profileUrl) {
        this.profile = profileUrl;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Like> likes = new ArrayList<>();

    @Builder
    public User(Long id, String email, String password, String name, String nickname, String birthday, String address, Character type, Character status, String profile, String provider, String providerId, String description, Integer point) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.birthday = birthday;
        this.address = address;
        this.type = type;
        this.status = status;
        this.profile = profile;
        this.provider = provider;
        this.providerId = providerId;
        this.description = description;
        this.point = point;
    }
}