package com.example.blog.services;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.repositories.PostRepository;
import org.modelmapper.ModelMapper;

import java.util.List;

public interface PostService {


    //create
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    //update
    public PostDto updatePost(PostDto postDto, Integer postId, Integer categoryId);
    //delete
    public void deletePost(Integer postId);


    //get all by category
    List<PostDto> getAllPostsByCategory(Integer categoryId);
    //get all by user
    List<PostDto> getAllPostsByUser(Integer userId);
    //get post by id
    PostDto getPostById(Integer postId);
    //get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
    // search
    List<PostDto> searchPosts(String keyword);

}
