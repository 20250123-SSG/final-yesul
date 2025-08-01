package com.yesul.community.model.dto.response;

import com.yesul.community.model.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(description = "포인트 응답 DTO")
public class PostResponseDto {

    private Long id;
    private Long userId;  // 작성자 ID 추가
    private String title;
    private String content;
    private String thumbnail;
    private String nickname;
    private String boardName;
    private List<String> imageUrls;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Integer viewCount;
    private Integer likeCount;         // 좋아요 수
    private Boolean likedByMe;         // 로그인 유저가 좋아요 눌렀는지 여부
    private Boolean isAuthor;          // 현재 사용자가 작성자인지 여부

    @Builder.Default
    private List<CommentResponseDto> comments = new ArrayList<>();  // 댓글 리스트

    public PostResponseDto(Long id, String title, String boardName, Integer likeCount) {
        this.id = id;
        this.title = title;
        this.boardName = boardName;
        this.likeCount = likeCount;
    }

    // Post → PostResponseDto 변환 메서드 (기본값만 설정)
    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .userId(post.getUser().getId())  // 작성자 ID 설정
                .boardName(post.getBoardName())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .nickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .viewCount(post.getViewCount())
                .build();
    }

    public static PostResponseDto from(Post post, boolean likedByMe) {
        return PostResponseDto.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .boardName(post.getBoardName())
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .nickname(post.getUser().getNickname())
                .likeCount(post.getLikes().size())
                .likedByMe(likedByMe)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .viewCount(post.getViewCount())
                .build();
    }
}