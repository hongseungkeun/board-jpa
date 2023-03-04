package com.board.application.post.repository;

import com.board.application.post.domain.Post;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import static com.board.application.post.domain.QPost.post;


public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<Post> findPostsBySearchWord(String searchWord, Pageable pageable) {
        List<String> words = new ArrayList<>(List.of(searchWord.split(" ")));
        BooleanBuilder builder = new BooleanBuilder();

        for (String word : words) {
            builder.and(post.title.contains(word));
        }

        List<Post> content = queryFactory.selectFrom(post)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory.select(post.count())
                .from(post)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(content, pageable, count);
    }
}