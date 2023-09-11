package com.example.blog.repositories;

import com.example.blog.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
