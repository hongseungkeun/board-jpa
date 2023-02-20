package com.board.application.user.domain;

import com.board.application.post.domain.Post;
import com.board.core.domain.BaseEntity;
import com.board.core.exception.CustomException;
import com.board.core.exception.error.ErrorCode;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.Assert;

import java.util.ArrayList;
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
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Post> getPosts() {
        return new ArrayList<>(posts);
    }

    protected User() {
    }

    public User(String name, String hobby, int age, String email, String password) {
        this(null, name,hobby,age,email,password);
    }

    public User(Long id, String name, String hobby, int age, String email, String password) {
        validate(name, age);

        this.id = id;
        this.name = name;
        this.hobby = hobby;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void isPossibleLogin(String password){
        if(!this.password.equals(password)){
            throw new CustomException(ErrorCode.LOGIN_FAILED);
        }
    }

    private void validate(String name, int age) {
        validateName(name);
        validateAge(age);
    }

    private void validateName(String name) {
        Assert.notNull(name, "name must not be null");
        Assert.hasText(name, "name must be at least 0 character long");
    }

    private void validateAge(int age) {
        Assert.isTrue(age>0, "age must not be below 0");
    }
}
