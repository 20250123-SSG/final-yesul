package com.yesul.chatroom.service;

import com.yesul.chatroom.model.entity.Message;
import com.yesul.chatroom.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Slice<Message> getMessagesWithCursor(Long chatRoomId, Long lastMessageId, int size) {
        return messageRepository.getMessagesWithCursor(chatRoomId, lastMessageId, size);
    }

}
