package com.example.blog.payloads;


import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryDto {

    private Integer id;
    @Size(min = 4, message = "Category Title should be minimum of 4 characters")
    private String title;
    @Size(min = 8, message = "Category description should be minimum of 4 characters")
    private String description;
}
