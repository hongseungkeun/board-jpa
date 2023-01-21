package com.board.application.user.domain;

import com.board.application.post.domain.Post;
import com.board.core.domain.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.Assert;

import java.util.List;

@Entity
@Table(name = "users")
@DynamicInsert
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @ColumnDefault("'없음'")
    private String hobby;
    @Column(nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHobby() {
        return hobby;
    }

    public Integer getAge() {
        return age;
    }

    protected User() {
    }

    public User(Long id, String name, String hobby, int age) {
        Assert.notNull(name, "name must not be null");
        Assert.hasText(name, "name must be at least 0 character long");
        Assert.isTrue(age>0, "age must not be below 0");

        this.id = id;
        this.name = name;
        this.hobby = hobby;
        this.age = age;
    }

    public User(String name, String hobby, int age) {
        Assert.notNull(name, "name must not be null");
        Assert.hasText(name, "name must be at least 0 character long");
        Assert.isTrue(age>0, "age must not be below 0");

        this.name = name;
        this.hobby = hobby;
        this.age = age;
    }
}