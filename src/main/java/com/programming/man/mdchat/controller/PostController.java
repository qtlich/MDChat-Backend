package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.OperationResultDto;
import com.programming.man.mdchat.dto.PostRequest;
import com.programming.man.mdchat.dto.PostResponse;
import com.programming.man.mdchat.model.Post;
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

    @PostMapping(value = "create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) {
        return status(HttpStatus.OK).body(postService.save(postRequest));
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping(value = "by-id/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    //    @GetMapping(value = "by-channel/{channelId}")
//    public  ResponseEntity<List<Post>> getPostsByChannel(@PathVariable Long channelId) {
//        return status(HttpStatus.OK).body(this.postService.selectChannelPosts(channelId));
//    }
    @GetMapping(value = "chposts/{id}")
    public ResponseEntity<List<PostResponse>> getPostsByChannel(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsByChannel(id));
    }

    @PostMapping(value = "delete/{postId}")
    public ResponseEntity<List<OperationResultDto>> deletePostByPostId(@PathVariable Long postId) {
        return status(HttpStatus.OK).body(postService.deletePostById(postId));
    }

    @GetMapping(value = "by-user/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}
