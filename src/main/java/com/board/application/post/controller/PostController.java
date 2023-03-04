package com.board.application.post.controller;

import com.board.application.post.dto.PostRequest;
import com.board.application.post.dto.PostResponse;
import com.board.application.post.service.PostService;
import com.board.core.annotation.LoginId;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts(Pageable pageable){
        List<PostResponse> posts = postService.getPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        PostResponse post = postService.getPost(id);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PostResponse>> getPostsBySearchWord(@RequestParam String searchWord, Pageable pageable){
        List<PostResponse> posts = postService.getPostsBySearchWord(searchWord, pageable);
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody PostRequest request, @LoginId Long userId){
        Long postId = postService.createPost(request, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequest request,
                                                   @LoginId Long userId){
        postService.updatePost(id, request, userId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @LoginId Long userId){
        postService.deletePost(id, userId);

        return ResponseEntity.noContent().build();
    }
}
