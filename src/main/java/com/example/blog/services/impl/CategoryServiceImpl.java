package com.example.blog.services.impl;
import com.example.blog.entities.Category;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CategoryDto;
import com.example.blog.repositories.CategoryRepository;
import com.example.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category createdCategory = this.categoryRepository.save(category);

        return this.modelMapper.map(createdCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","ID",categoryId));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());

        Category updatedCategory = categoryRepository.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","ID",categoryId));

        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<CategoryDto> categoriesDto = categories.stream().map(category->modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
        return categoriesDto;
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","ID",categoryId));
        categoryRepository.delete(category);
    }
}
