package com.yesul.admin.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class NoticeAdminDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
}
