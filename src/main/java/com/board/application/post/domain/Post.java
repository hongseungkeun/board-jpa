package com.board.application.post.domain;

import com.board.application.post.dto.PostResponse;
import com.board.application.user.domain.User;
import com.board.core.domain.BaseEntity;
import com.board.core.exception.AuthFailedException;
import com.board.core.exception.error.ErrorCode;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addUser(User user) {
        if (this.user != null) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    protected Post() {
    }

    public Post(String title, String content, User user) {
        this(null, title, content, user);
    }

    public Post(Long id, String title, String content, User user) {
        validateTitle(title);
        validateContent(content);

        this.id = id;
        this.title = title;
        this.content = content;
        addUser(user);
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

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostResponse toPostResponse() {
        return new PostResponse(this.title, this.content, this.getCreated_at(), this.getCreated_by());
    }

    public void isPossibleCreatePost(Long userId) {
        if (!this.getUser().getId().equals(userId)) {
            throw new AuthFailedException(ErrorCode.AUTH_FAILED);
        }
    }

    private void validateTitle(String title) {
        Assert.notNull(title, "제목을 입력해주세요.");
        Assert.hasText(title, "제목을 한 글자 이상 입력해주세요.");
    }

    private void validateContent(String content) {
        Assert.notNull(content, "글을 입력해주세요.");
        Assert.hasText(content, "글을 한 글자 이상 입력해주세요.");
    }
}
