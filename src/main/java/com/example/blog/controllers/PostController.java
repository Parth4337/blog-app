package com.example.blog.controllers;

import com.example.blog.config.AppConstants;
import com.example.blog.payloads.ApiResponse;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class PostController {
    @Autowired
    private PostService postService;
    // get - get all
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDirection", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortDirection){

        return new ResponseEntity<>(postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection), HttpStatus.OK);
    }
    // get - get by postID
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@Valid @PathVariable Integer postId){
        PostDto post = postService.getPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    // get - get by userID
    @GetMapping("/posts/byuser/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@Valid @PathVariable Integer userId){
        List<PostDto> posts = postService.getAllPostsByUser(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // get - get by catID
    @GetMapping("/posts/bycategory/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@Valid @PathVariable Integer categoryId){
        List<PostDto> posts = postService.getAllPostsByCategory(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // get - search using keyword
    @GetMapping("/posts/bytitle/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable String keyword){
        return new ResponseEntity<>(postService.searchPosts(keyword), HttpStatus.OK);
    }


    // post - create post
    @PostMapping("/posts/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
        PostDto createdPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    // put - update post
    @PutMapping("/posts/update/{postId}/category/{categoryId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId, @PathVariable Integer categoryId){
        PostDto updatedPost = postService.updatePost(postDto, postId, categoryId);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    // delete - delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);
    }

}
