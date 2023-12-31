package com.example.blog.repositories;

import com.example.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// @Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String user);
}
