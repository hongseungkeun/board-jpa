package com.board.application.user.controller;

import com.board.application.user.dto.CreateUserRequest;
import com.board.application.user.dto.LoginUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("유저는 회원가입 할 수 있다")
    @Test
    void createUser() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest("홍승근", "운동", 25, "test2@naver.com", "1234");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("user-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("이름"),
                                fieldWithPath("hobby").optional().description("취미"),
                                fieldWithPath("age").description("나이"),
                                fieldWithPath("email").description("이메일").attributes(Attributes.key("constraint").value("길이 20 이하")),
                                fieldWithPath("password").description("비밀번호").attributes(Attributes.key("constraint").value("길이 20 이하"))
                        )
                ));
    }

    @DisplayName("유저는 로그인 할 수 있다")
    @Test
    void loginUser() throws Exception {
        createUserSample();

        LoginUserRequest loginUserRequest = new LoginUserRequest( "test1@naver.com", "1234");

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("이메일").attributes(Attributes.key("constraint").value("길이 20 이하")),
                                fieldWithPath("password").description("비밀번호").attributes(Attributes.key("constraint").value("길이 20 이하"))
                        )
                ));
    }

    private void createUserSample(){
        CreateUserRequest createUserRequest = new CreateUserRequest("홍승근", "운동", 25, "test1@naver.com", "1234");
        userController.createUser(createUserRequest);
    }

}
