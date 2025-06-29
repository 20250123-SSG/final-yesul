package com.yesul.chatroom.repository;

import com.yesul.chatroom.model.entity.ChatRoom;
import com.yesul.user.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByUser(User user);
}
