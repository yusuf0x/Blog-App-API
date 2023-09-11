package com.example.demo.services;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.payload.response.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDto);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDto);
    void deleteCategory(Long categoryId);
    CategoryDTO getCategoryById(Long categoryId);
    CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
