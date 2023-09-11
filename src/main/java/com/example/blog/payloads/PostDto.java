package com.example.blog.payloads;

import com.example.blog.entities.Category;
import com.example.blog.entities.Comment;
import com.example.blog.entities.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PostDto {

    private Integer id;
    private String title;

    private String content;
    private String imageName;
    private Date dateAdded;
    private UserDto user;
    private CategoryDto category;
    private List<CommentDto> comments = new ArrayList<>();
}
