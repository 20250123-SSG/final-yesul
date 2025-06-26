package com.yesul.chatroom.repository;

import com.yesul.chatroom.model.entity.Message;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository {
    Slice<Message> getMessagesWithCursor(Long chatRoomId, Long lastMessageId, int size);
}
