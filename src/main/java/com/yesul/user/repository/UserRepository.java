package com.yesul.user.repository;

import java.util.List;
import java.util.Optional;

import com.yesul.user.model.dto.response.UserListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yesul.user.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String mail);
    Optional<User> findByNickname(String nickName);

    @Query("SELECT new com.yesul.user.model.dto.UserListResponseDto(u.id, " +
            "u.email, u.nickname, u.birthday, u.status ,u.createdAt) FROM User u")
    List<UserListResponseDto> findAllUserInfoDto();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.status = :status WHERE u.id = :userId")
    int updateUserStatus(Long userId, Character status);
}