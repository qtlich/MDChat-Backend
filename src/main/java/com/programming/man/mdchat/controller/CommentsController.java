package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
@AllArgsConstructor
public class CommentsController {
    private final CommentService commentService;

    @PostMapping(value = "cud") //comments/cud
    public ResponseEntity<List<CommentCUDResponse>> createPost(@RequestBody CommentCUDRequest commentCUDRequest) {
        return status(HttpStatus.OK).body(commentService.commentCUD(commentCUDRequest));
    }

    @PostMapping(value = "universal-comments")
    public ResponseEntity<List<GetUserCommentsUniversalResponse>> getUserPostHistory(@RequestBody GetUserCommentsUniversalRequest request) {
        return status(HttpStatus.OK).body(commentService.getUserCommentsUniversal(request));
    }

    @GetMapping(value = "by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK).body(commentService.getAllCommentsForPost(postId));
    }

    @PostMapping(value = "by-post")
    public ResponseEntity<List<GetAllCommentsResponseDto>> getAllPostComments(@RequestBody GetAllCommentsRequestDto request) {
        return ResponseEntity.status(OK).body(commentService.getAllPostComments(request));
    }
}
