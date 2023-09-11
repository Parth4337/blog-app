package com.example.blog.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    private int id;
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
