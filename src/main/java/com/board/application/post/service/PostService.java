package com.board.application.post.service;

import com.board.application.like.domain.Like;
import com.board.application.like.repository.LikeRepository;
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
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final UserService userService;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    public PostService(PostRepository postRepository, UserService userService, LikeRepository likeRepository){
        this.postRepository = postRepository;
        this.userService = userService;
        this.likeRepository = likeRepository;
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

    public PostResponse getPostDetail(Long postId){
        Post post = findPostById(postId);

        return post.toPostResponse();
    }

    @Transactional
    public Long createPost(PostRequest request, Long userId){
        User user = userService.findUserById(userId);

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

    @Transactional
    public void addLike(Long postId, Long userId){
        User user = userService.findUserById(userId);
        Post post = findPostById(postId);

        Optional<Like> userLikeOfPost = likeRepository.findByUserAndPost(user, post);

        if(userLikeOfPost.isPresent()){
            likeRepository.delete(userLikeOfPost.get());
        }else{
            likeRepository.save(new Like(user, post));
        }
    }

    public Post findPostById(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
    }
}
