package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.service.PostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@CrossOrigin
@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Slf4j
public class PostController {
    @Autowired
    private final PostService postService;

    @PostMapping(value = "cud") //posts/cud
    public ResponseEntity<List<PostCUDResponse>> createPost(@RequestBody PostCUDRequest postCUDRequest) {
        return status(HttpStatus.OK).body(postService.postCUD(postCUDRequest));
    }

    @PostMapping(value = "universal-posts")
    public ResponseEntity<List<GetAllUserPostsUniversalResponse>> getUserPostHistory(@RequestBody GetAllUserPostsUniversalRequest request) {
        return status(HttpStatus.OK).body(postService.getUserPostsUniversal(request));
    }

    @PostMapping(value = "view-post")
    public ResponseEntity<List<GetPostByIdResponseDto>> getPost(@RequestBody GetPostByIdRequestDto request) {
        return status(HttpStatus.OK).body(postService.getPostById(request));
    }
    @GetMapping(value = "get-comments-count/{id}")
    public ResponseEntity<Long> getCommentsCount(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getCommentsCount(id));
    }

    @PostMapping(value = "hide-post")
    public ResponseEntity<List<ShowHidePostResponseDto>>  showHidePost(@RequestBody ShowHidePostRequestDto request)
    {
        return status(HttpStatus.OK).body(postService.showHidePost(request));
    }
    @PostMapping(value = "bookmark-post")
    public ResponseEntity<List<BookmarkPostResponseDto>>  saveUnsavePost(@RequestBody BookmarkPostRequestDto request)
    {
        return status(HttpStatus.OK).body(postService.bookmarkPost(request));
    }

    @GetMapping(value = "chposts/{id}")
    public ResponseEntity<List<PostResponse>> getPostsByChannel(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsByChannel(id));
    }

    @GetMapping(value = "by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}
