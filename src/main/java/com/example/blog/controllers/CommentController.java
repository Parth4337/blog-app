package com.example.blog.controllers;

import com.example.blog.entities.Comment;
import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.CommentDto;
import com.example.blog.repositories.CommentRepository;
import com.example.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {
    @Autowired
    CommentService commentService;

    @Autowired
    ModelMapper modelMapper;
    @PostMapping("posts/{postId}/users/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @PathVariable Integer userId){
        return new ResponseEntity<>(commentService.createComment(commentDto, postId, userId), HttpStatus.CREATED);
    }

    @PutMapping("posts/{postId}/users/{userId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @PathVariable Integer userId, @PathVariable Integer commentId){
        return new ResponseEntity<>(commentService.updateComment(commentDto, commentId), HttpStatus.CREATED);
    }

    @DeleteMapping("posts/{postid}/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment deleted successfully", true), HttpStatus.OK);
    }
}
