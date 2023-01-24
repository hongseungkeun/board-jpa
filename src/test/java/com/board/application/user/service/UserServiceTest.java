package com.board.application.user.service;

import com.board.application.user.domain.User;
import com.board.application.user.dto.CreateUserRequest;
import com.board.application.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @DisplayName("유저를 생성할 수 있다")
    @Test
    void createUser(){
        User user = new User(1L, "유저", "테스트",10);
        CreateUserRequest request = new CreateUserRequest("유저", "테스트", 10);

        given(userRepository.save(any())).willReturn(user);

        userService.createUser(request);

        verify(userRepository).save(any());
    }
}
