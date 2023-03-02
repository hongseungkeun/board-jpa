package com.board.application.post.service;

import com.board.application.post.domain.Post;
import com.board.application.post.dto.PostRequest;
import com.board.application.post.dto.PostResponse;
import com.board.application.post.repository.PostRepository;
import com.board.application.user.domain.User;
import com.board.application.user.service.UserService;
import com.board.core.exception.PostNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private PostService postService;
    private Post samplePost;
    private User sampleUserWithId;
    private Post samplePostWithId;

    @BeforeEach
    void setUp(){
        samplePost = new Post("제목", "안녕하세요", new User("홍승근", "취미", 25, "123@123.com", "123123"));
        sampleUserWithId = new User(1L, "홍승근", "취미", 25, "123@123.com", "123123");
        samplePostWithId = new Post(1L, "제목", "안녕하세요", sampleUserWithId);
    }

    @DisplayName("게시글을 페이징 조회할 수 있다.")
    @Test
    void getPostsTest() {
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            posts.add(samplePost);
        }
        PageRequest request = PageRequest.of(1, 10);
        Page<Post> postPage = new PageImpl<>(posts, request, 10);
        List<PostResponse> postResponses = postPage.stream().map(Post::toPostResponse).toList();

        given(postRepository.findAll(request)).willReturn(postPage);

        List<PostResponse> postList = postService.getPosts(request);

        assertThat(postList).isEqualTo(postResponses);
    }

    @DisplayName("게시글 단건 조회시")
    @Nested
    class getPost {
        @DisplayName("게시물이 없다면 PostNotFoundException 발생")
        @Test
        void getPostFailureTest() {
            given(postRepository.findById(anyLong())).willThrow(PostNotFoundException.class);

            Assertions.assertThatThrownBy(() -> postService.findPostById(3L)).isInstanceOf(PostNotFoundException.class);
        }

        @DisplayName("게시물이 있다면 해당 게시물을 조회한다")
        @Test
        void getPostSuccessTest() {
            given(postRepository.findById(anyLong())).willReturn(Optional.of(samplePost));

            assertThat(postService.findPostById(1L)).isEqualTo(samplePost);
        }
    }

    @DisplayName("게시물을 생성할 수 있다")
    @Test
    void createPostTest() {
        PostRequest request = new PostRequest("제목", "안녕하세요");

        given(userService.findUserById(anyLong())).willReturn(sampleUserWithId);
        given(postRepository.save(any())).willReturn(samplePostWithId);

        Long id = postService.createPost(request, 1L);

        assertThat(id).isEqualTo(samplePostWithId.getId());
    }

    @DisplayName("게시물을 수정할 수 있다")
    @Test
    void updatePostTest() {
        PostRequest request = new PostRequest("테스트", "안녕하세요 반갑습니다");

        given(postRepository.findById(anyLong())).willReturn(Optional.of(samplePostWithId));

        postService.updatePost(1L, request, 1L);

        assertThat(samplePostWithId.getContent()).isEqualTo(request.content());
    }
}
