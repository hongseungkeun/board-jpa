package com.board.application.user.domain;

import com.board.application.post.domain.Post;
import com.board.core.domain.BaseEntity;
import com.board.core.exception.LoginFailedException;
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
    @Column(unique = true, nullable = false, length = 20)
    private String email;
    @Column(nullable = false, length = 20)
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
        validate(name, age, email, password);

        this.id = id;
        this.name = name;
        this.hobby = hobby;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public void isPossibleLogin(String password){
        if(!this.password.equals(password)){
            throw new LoginFailedException(ErrorCode.LOGIN_FAILED);
        }
    }

    private void validate(String name, int age, String email, String password) {
        validateName(name);
        validateAge(age);
        validateEmail(email);
        validatePassword(password);
    }

    private void validateName(String name) {
        Assert.notNull(name, "이름을 입력해주세요.");
        Assert.hasText(name, "이름을 한 글자 이상 입력해주세요.");
    }

    private void validateAge(int age) {
        Assert.isTrue(age>0, "나이를 한 살 이상 입력해주세요.");
    }

    private void validateEmail(String email) {
        Assert.notNull(email, "이메일을 입력해주세요.");
        Assert.hasText(email, "이메일을 한 글자 이상 입력해주세요.");
        Assert.isTrue(email.length() <= 20, "이메일은 20글자 이상일 수 없습니다.");
    }

    private void validatePassword(String password) {
        Assert.notNull(password, "비밀번호를 입력해주세요.");
        Assert.hasText(password, "비밀번호를 한 글자 이상 입력해주세요.");
        Assert.isTrue(password.length() <= 20, "비밀번호는 20글자 이상일 수 없습니다.");
    }
}
