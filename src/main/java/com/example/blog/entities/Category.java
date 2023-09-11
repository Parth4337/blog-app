package com.example.blog.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "categories")

@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name="title",nullable = false, length = 100)
    private String title;
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @OneToMany(mappedBy = "category", cascade = ALL, fetch = FetchType.EAGER)
    private List<Post> posts= new ArrayList<>();
}
