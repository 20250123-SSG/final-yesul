package com.yesul.notification.service;

import com.yesul.notification.model.dto.request.CreateNotificationRequestDto;

public interface NotificationService {
    void sendNotification(CreateNotificationRequestDto dto);
    void sendPostOwnerCommentNotification(Long postOwnerId, Long commenterId, Long commentId,String boardName);
    void sendPostOwnerLikeNotification(Long postId, Long likeOwnerId, Long postOwnerId, String boardName);
}
