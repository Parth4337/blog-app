package com.example.blog.services.impl;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CategoryDto;
import com.example.blog.payloads.PostDto;
import com.example.blog.payloads.PostResponse;
import com.example.blog.payloads.UserDto;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.repositories.PostRepository;
import com.example.blog.repositories.UserRepository;
import com.example.blog.services.PostService;
import jakarta.persistence.ManyToOne;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        Post post = modelMapper.map(postDto, Post.class);

        post.setImageName("Default.jpg");
        post.setDateAdded(new Date());

        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "ID", userId));
        post.setUser(user);

        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", categoryId));
        post.setCategory(category);

        Post createdPost = postRepository.save(post);
        return modelMapper.map(createdPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId, Integer categoryId) {

        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "ID", postId));

        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", categoryId));
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "ID", postId));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getAllPostsByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", categoryId));
        return  postRepository.findByCategory(category).stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostsByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "ID", userId));
        return  postRepository.findByUser(user).stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());


//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Page<Post> pagePost = postRepository.findAll(pageable);
//        List<PostDto> allPostDtos = pagePost.getContent().stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
//
//        PostResponse postResponse = new PostResponse();
//        postResponse.setPageNumber(pagePost.getNumber());
//        postResponse.setLastPage(pagePost.isLast());
//        postResponse.setPageSize(pagePost.getSize());
//        postResponse.setTotalPages(pagePost.getTotalPages());
//        postResponse.setContent(allPostDtos);
//        postResponse.setTotalElements(pagePost.getTotalElements());
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "ID", postId));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort;
        if (sortDirection.equalsIgnoreCase("desc"))
            sort = Sort.by(sortBy).descending();
        else
            sort = Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(pageable);
        List<PostDto> allPostDtos = pagePost.getContent().stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setLastPage(pagePost.isLast());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setContent(allPostDtos);
        postResponse.setTotalElements(pagePost.getTotalElements());

        return postResponse;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return postRepository.findByTitleContaining(keyword).stream().map((post)->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }
}
