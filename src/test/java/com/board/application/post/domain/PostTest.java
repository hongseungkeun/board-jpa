package com.board.application.post.domain;

import com.board.application.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostTest {

    @DisplayName("제목은 빈값일 수 없다.")
    @Test
    void titleCannotNull(){
        User user = new User("홍승근", "취미", 25);

        assertThrows(IllegalArgumentException.class, () -> new Post("", "안녕하세요", user));
        assertThrows(IllegalArgumentException.class, () -> new Post(" ", "안녕하세요", user));
        assertThrows(IllegalArgumentException.class, () -> new Post(null, "안녕하세요", user));
    }

    @DisplayName("글은 빈값일 수 없다")
    @Test
    void contentCannotNull(){
        User user = new User("홍승근", "취미", 25);

        assertThrows(IllegalArgumentException.class, () -> new Post("제목", "", user));
        assertThrows(IllegalArgumentException.class, () -> new Post("제목", " ", user));
        assertThrows(IllegalArgumentException.class, () -> new Post("제목", null, user));
    }
}
