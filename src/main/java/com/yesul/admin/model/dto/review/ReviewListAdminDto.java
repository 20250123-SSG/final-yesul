package com.yesul.admin.model.dto.review;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReviewListAdminDto { // 용도: 이상한글삭제용
    private String id;
    private String alcoholName;
    private String userName;
    private String content;
    private LocalDateTime updatedAt; // 조회용
}
