package com.board.application.user.controller;

import com.board.application.user.dto.CreateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("유저를 생성할 수 있다")
    @Test
    void createUserSuccessTest() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("홍승근", "운동", 25);

        mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createUserRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("이름이 빈 값인 유저는 생성할 수 없다")
    @Test
    void createUserNameNullTest() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("", "운동", 25);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("나이가 0인 유저는 생성할 수 없다")
    @Test
    void createUserAgeZeroTest() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("홍승근", "운동", 0);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("취미가 없는 유저는 생성할 수 있다")
    @Test
    void createUserHobbyNullTest() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("홍승근", null, 25);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
