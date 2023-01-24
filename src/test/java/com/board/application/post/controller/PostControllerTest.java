package com.board.application.post.controller;

import com.board.application.post.domain.Post;
import com.board.application.post.dto.CreatePostRequest;
import com.board.application.post.dto.UpdatePostRequest;
import com.board.application.post.repository.PostRepository;
import com.board.application.user.domain.User;
import com.board.application.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTest {
    @Autowired
    private PostController postController;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        User user = new User("홍승근", "취미", 25);
        User save = userRepository.save(user);
        Post post1 = new Post("제목1", "안녕하세요", save);
        Post post2 = new Post("제목2", "안녕하세요", save);
        postRepository.save(post1);
        postRepository.save(post2);
    }

    @DisplayName("게시글을 조회할 수 있다")
    @Test
    void getPostsTest() throws Exception {
        mockMvc.perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("게시글을 단건 조회할 수 있다")
    @Test
    void getPostTest() throws Exception {
        mockMvc.perform(get("/posts/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("게시글을 생성할 수 있다")
    @Test
    void createPostTest() throws Exception {
        CreatePostRequest createPostRequest = new CreatePostRequest("제목3","안녕하세요",1L);

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("게시글을 수정할 수 있다")
    @Test
    void updatePostTest() throws Exception {
        UpdatePostRequest updatePostRequest = new UpdatePostRequest("제목3", "안녕하세요 반갑습니다");

        mockMvc.perform(patch("/posts/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
