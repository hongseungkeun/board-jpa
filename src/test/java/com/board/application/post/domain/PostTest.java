package com.board.application.post.domain;

import com.board.application.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostTest {

    @DisplayName("제목은 빈값일 수 없다.")
    @ParameterizedTest
    @MethodSource("blankOrNullStrings")
    void titleCannotNull(String title) {
        User user = new User("홍승근", "취미", 25);

        assertThrows(IllegalArgumentException.class, () -> new Post(title, "안녕하세요", user));
    }

    @DisplayName("글은 빈값일 수 없다")
    @ParameterizedTest
    @MethodSource("blankOrNullStrings")
    void contentCannotNull(String content){
        User user = new User("홍승근", "취미", 25);

        assertThrows(IllegalArgumentException.class, () -> new Post("제목", content, user));
    }

    static Stream<String> blankOrNullStrings() {
        return Stream.of("", " ", null);
    }
}
