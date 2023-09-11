package com.example.blog.payloads;

import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class CommentDto {

    private Integer id;
    private String content;
    private Date dateAdded;
}
