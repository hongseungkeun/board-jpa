package com.board.application.post.service;

import com.board.application.post.domain.Post;
import com.board.application.post.dto.PostRequest;
import com.board.application.post.dto.PostResponse;
import com.board.application.post.repository.PostRepository;
import com.board.application.user.domain.User;
import com.board.application.user.service.UserService;
import com.board.core.exception.PostNotFoundException;
import com.board.core.exception.error.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<PostResponse> getPosts(Pageable pageable){
        Page<Post> posts = postRepository.findAll(pageable);

        return posts.stream()
                .map(Post::toPostResponse)
                .toList();
    }

    public List<PostResponse> getPostsBySearchWord(String searchWord, Pageable pageable) {
        Page<Post> posts = postRepository.findPostsBySearchWord(searchWord, pageable);

        return posts.stream()
                .map(Post::toPostResponse)
                .toList();
    }

    public PostResponse getPost(Long postId){
        Post post = findPostById(postId);

        return post.toPostResponse();
    }

    @Transactional
    public Long createPost(PostRequest request, Long userid){
        User user = userService.findUserById(userid);

        Post post = postRepository.save(request.toPost(user));

        return post.getId();
    }

    @Transactional
    public void updatePost(Long postId, PostRequest request, Long userId){
        Post post = findPostById(postId);

        post.isPossibleCreatePost(userId);

        post.updatePost(request.title(), request.content());
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = findPostById(postId);

        post.isPossibleCreatePost(userId);

        postRepository.delete(post);
    }

    public Post findPostById(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
    }
}
