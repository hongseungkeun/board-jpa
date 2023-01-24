package com.board.application.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @DisplayName("유저의 이름은 빈값일 수 없다.")
    @ParameterizedTest
    @MethodSource("blankOrNullStrings")
    void NameCannotNull(String name){
        assertThrows(IllegalArgumentException.class, () -> new User(name, "취미", 25));
    }

    @DisplayName("유저의 나이는 0살 이하일 수 없다")
    @Test
    void ageCannotZero(){
        assertThrows(IllegalArgumentException.class, () -> new User("홍승근", "취미", 0));
    }

    static Stream<String> blankOrNullStrings() {
        return Stream.of("", " ", null);
    }
}