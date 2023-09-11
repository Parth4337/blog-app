package com.example.blog.services.impl;

import com.example.blog.entities.Comment;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CommentDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.repositories.CommentRepository;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "ID", userId));
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "ID", postId));

        Comment comment =modelMapper.map(commentDto, Comment.class);
        comment.setDateAdded(new Date());
        comment.setPost(post);
        comment.setUser(user);
        Comment createdComment = commentRepository.save(comment);

        return modelMapper.map(createdComment, CommentDto.class);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment=commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "ID", commentId));
        comment.setContent(commentDto.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "ID",commentId));
        commentRepository.delete(comment);
    }


}
