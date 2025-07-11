package com.yesul.community.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yesul.community.model.dto.PostResponseDto;
import com.yesul.community.model.entity.QLike;
import com.yesul.community.model.entity.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostResponseDto> findPopularPostsByLikes() {
        QPost post = QPost.post;
        QLike like = QLike.like;

        return queryFactory
                .select(Projections.constructor(PostResponseDto.class, post.id, post.title))
                .from(post)
                .join(like).on(like.post.eq(post))
                .groupBy(post.id)
                .orderBy(like.id.count().desc())
                .fetch();
    }

}
