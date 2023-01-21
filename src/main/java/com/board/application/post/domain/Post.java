package com.board.application.post.domain;

import com.board.application.post.dto.PostResponse;
import com.board.application.user.domain.User;
import com.board.core.domain.BaseEntity;
import jakarta.persistence.*;
import org.springframework.util.Assert;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected Post() {
    }

    public Post(String title, String content, User user) {
        Assert.notNull(title, "title must not be null");
        Assert.hasText(title, "title must be at least 0 character long");
        Assert.notNull(content, "content must not be null");
        Assert.hasText(content, "content must be at least 0 character long");

        this.title = title;
        this.content = content;
        this.user = user;
        this.createdBy(user.getName());
    }

    public Post(Long id, String title, String content, User user) {
        Assert.notNull(title, "title must not be null");
        Assert.hasText(title, "title must be at least 0 character long");
        Assert.notNull(content, "content must not be null");
        Assert.hasText(content, "content must be at least 0 character long");

        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdBy(user.getName());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public void updatePost(String title, String content){
        this.title = title;
        this.content = content;
    }

    public PostResponse toPostResponse(){
        return new PostResponse(this.title, this.content, this.getCreated_at(), this.getCreated_by());
    }
}
