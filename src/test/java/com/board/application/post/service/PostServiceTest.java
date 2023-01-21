package com.board.application.post.service;

import com.board.application.post.domain.Post;
import com.board.application.post.dto.CreatePostRequest;
import com.board.application.post.dto.PostResponse;
import com.board.application.post.dto.UpdatePostRequest;
import com.board.application.post.repository.PostRepository;
import com.board.application.user.domain.User;
import com.board.application.user.repository.UserRepository;
import com.board.core.exception.PostNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
    private UserRepository userRepository;
    @InjectMocks
    private PostService postService;

    @DisplayName("게시글 페이징 조회")
    @Test
    void getPostsTest() {
        User user = new User("홍승근", "취미", 25);
        Post post = new Post("제목", "안녕하세요", user);
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            posts.add(post);
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

        @DisplayName("게시물이 있다면 해당 게시물 리턴")
        @Test
        void getPostSuccessTest() {
            User user = new User(1L, "홍승근", "취미", 25);
            Post post = new Post(1L, "제목", "안녕하세요", user);

            given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

            assertThat(postService.findPostById(1L)).isEqualTo(post);
        }
    }

    @DisplayName("게시물 생성")
    @Test
    void createPostTest() {
        User user = new User(1L, "홍승근", "취미", 25);
        Post post = new Post(1L, "제목", "안녕하세요", user);
        CreatePostRequest request = new CreatePostRequest("제목", "안녕하세요", 1L);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(postRepository.save(any())).willReturn(post);
        Long id = postService.createPost(request);

        assertThat(id).isEqualTo(post.getId());
    }

    @DisplayName("게시물 수정")
    @Test
    void updatePostTest() {
        User user = new User(1L, "홍승근", "취미", 25);
        Post post = new Post(1L, "제목", "안녕하세요", user);
        UpdatePostRequest request = new UpdatePostRequest("테스트", "안녕하세요 반갑습니다");

        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));

        postService.updatePost(1L, request);

        assertThat(post.getContent()).isEqualTo(request.content());
    }
}
