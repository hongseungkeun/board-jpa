package com.board.application.post.service;

import com.board.application.post.domain.Post;
import com.board.application.post.dto.PostRequest;
import com.board.application.post.dto.PostResponse;
import com.board.application.post.repository.PostRepository;
import com.board.application.user.domain.User;
import com.board.application.user.repository.UserRepository;
import com.board.core.exception.CustomException;
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
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

    public PostResponse getPost(Long id){
        Post post = findPostById(id);

        return post.toPostResponse();
    }

    @Transactional
    public Long createPost(PostRequest request, Long userid){
        User user = userRepository.findById(userid)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post post = postRepository.save(request.toPost(user));

        return post.getId();
    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest request, Long userId){
        Post post = findPostById(id);

        validUser(post, userId);

        post.updatePost(request.title(), request.content());
        postRepository.save(post);

        return post.toPostResponse();
    }

    @Transactional
    public void deletePost(Long id, Long userId) {
        Post post = findPostById(id);

        validUser(post, userId);

        postRepository.delete(post);
    }

    public Post findPostById(Long id){
        return postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    private void validUser(Post post, Long userId){
        if(!post.getUser().getId().equals(userId)){
            throw new CustomException(ErrorCode.AUTH_FAILED);
        }
    }
}
