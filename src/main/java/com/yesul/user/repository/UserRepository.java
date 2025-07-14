package com.yesul.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.yesul.user.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByEmail(String mail);

    Optional<User> findByNickname(String nickName);

    @Modifying(clearAutomatically = true)
    int updateUserStatus(Long userId, Character status);
}