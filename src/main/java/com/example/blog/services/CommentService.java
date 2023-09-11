package com.example.blog.services;

import com.example.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
    void deleteComment(Integer commentId);

    CommentDto updateComment(CommentDto commentDto, Integer commentId);
}
