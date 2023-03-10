package com.board.application.post.controller;

import com.board.application.post.domain.Post;
import com.board.application.post.dto.PostRequest;
import com.board.application.post.repository.PostRepository;
import com.board.application.user.controller.UserController;
import com.board.application.user.domain.User;
import com.board.application.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PostControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void deleteAll(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("???????????? ?????? ????????? ??? ??????")
    @Test
    void getPostTest() throws Exception {
        User user = userRepository.save(createUser());
        Long postId = getPostId(user);

        mockMvc.perform(get("/posts/{id}", postId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("title").description("??????"),
                                fieldWithPath("content").description("??????"),
                                fieldWithPath("created_at").description("?????? ??????"),
                                fieldWithPath("created_by").description("?????????"),
                                fieldWithPath("likeCount").description("??? ????????? ???")
                        )
                ));
    }

    @DisplayName("???????????? ????????? ????????? ??? ??????")
    @Test
    void getPostById() throws Exception {
        User user = userRepository.save(createUser());
        Long postId = getPostId(user);

        mockMvc.perform(get("/posts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-get-list",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].title").description("??????"),
                                fieldWithPath("[].content").description("??????"),
                                fieldWithPath("[].created_at").description("?????? ??????"),
                                fieldWithPath("[].created_by").description("?????????"),
                                fieldWithPath("[].likeCount").description("??? ????????? ???")
                        )
                ));
    }

    @DisplayName("???????????? ?????? ????????? ??? ??????")
    @Test
    void getPostBySearchWord() throws Exception {
        User user = userRepository.save(createUser());
        Long postId = getPostId(user);

        String searchWord = "????????? ?????????";

        mockMvc.perform(get("/posts/search?searchWord=" + searchWord))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-get-search",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("searchWord").description("?????????")
                        ),
                        responseFields(
                                fieldWithPath("[].title").description("??????"),
                                fieldWithPath("[].content").description("??????"),
                                fieldWithPath("[].created_at").description("?????? ??????"),
                                fieldWithPath("[].created_by").description("?????????"),
                                fieldWithPath("[].likeCount").description("??? ????????? ???")
                        )
                ));
    }

    @DisplayName("???????????? ????????? ??? ??????")
    @Test
    void createPost() throws Exception {
        User user = userRepository.save(createUser());
        Long postId = getPostId(user);

        PostRequest createPostRequest = new PostRequest("????????????","??????????????? ??????????????????.");

        MockHttpSession session = createSession(user.getId());

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest))
                        .session(session))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("post-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("??????"),
                                fieldWithPath("content").description("??????")
                        )
                ));
    }

    @DisplayName("???????????? ????????? ??? ??????")
    @Test
    void updatePost() throws Exception {
        User user = userRepository.save(createUser());
        Long postId = getPostId(user);

        PostRequest updatePostRequest = new PostRequest("????????? ????????? ?????????", "3????????? ???????????????");

        MockHttpSession session = createSession(user.getId());

        mockMvc.perform(patch("/posts/{id}",postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostRequest))
                        .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("????????? ??????")
                        ),
                        requestFields(
                                fieldWithPath("title").description("??????"),
                                fieldWithPath("content").description("??????")
                        )
                ));
    }

    @DisplayName("???????????? ????????? ??? ??????")
    @Test
    void deletePost() throws Exception {
        User user = userRepository.save(createUser());
        Long postId = getPostId(user);

        MockHttpSession session = createSession(user.getId());

        mockMvc.perform(delete("/posts/{id}",postId)
                        .session(session))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document("post-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("????????? ??????")
                        )
                ));
    }

    private MockHttpSession createSession(Long id) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("SESSION-KEY", id);
        return session;
    }

    private User createUser(){
        return new User("?????????", "??????", 25, "test1@naver.com", "1234");
    }

    private Long getPostId(User user){
        Post post1 = new Post("????????? ?????????", "2????????? ??????", user);
        Post post2 = new Post("????????? ?????? ?????????", "3????????? ??????", user);

        List<Post> posts = postRepository.saveAll(List.of(post1, post2));

        return posts.get(0).getId();
    }
}
