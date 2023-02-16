package com.board.application.post.repository;

import com.board.application.post.domain.Post;
import com.board.application.post.domain.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Post> findPostsBySearchWord(String searchWord, Pageable pageable) {
        List<Post> content = queryFactory.selectFrom(QPost.post)
                .where(QPost.post.title.contains(searchWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(QPost.post.count())
                .from(QPost.post)
                .where(QPost.post.title.contains(searchWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }
}