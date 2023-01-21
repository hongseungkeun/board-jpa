package com.board.application.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @DisplayName("이름은 빈값일 수 없다.")
    @Test
    void NameCannotNull(){
        assertThrows(IllegalArgumentException.class, () -> new User("", "취미", 25));
        assertThrows(IllegalArgumentException.class, () -> new User(" ", "취미", 25));
        assertThrows(IllegalArgumentException.class, () -> new User(null, "취미", 25));
    }

    @DisplayName("나이는 0살 이하일 수 없다")
    @Test
    void ageCannotZero(){
        assertThrows(IllegalArgumentException.class, () -> new User("홍승근", "취미", 0));
    }
}