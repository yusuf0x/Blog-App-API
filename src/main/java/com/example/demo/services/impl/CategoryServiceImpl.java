package com.example.demo.services.impl;

import com.example.demo.dto.CategoryDTO;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.payload.response.CategoryResponse;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDto) {
        Category category = CategoryDTO.toEntity(categoryDto);
        Category saved = categoryRepository.save(category);
        return CategoryDTO.fromEntity(saved);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        categoryDto.setId(category.getId());
        BeanUtils.copyProperties(categoryDto,category);
        Category updated = categoryRepository.save(category);
        return CategoryDTO.fromEntity(updated);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        categoryRepository.delete(category);
    }


    @Override
    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", " Id ", categoryId));
        return CategoryDTO.fromEntity(category);
    }


    @Override
    public CategoryResponse getCategories(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortDir) {
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize);
        Page<Category> categories = categoryRepository.findAll(pageRequest);
        List<CategoryDTO> categoryDTOList = categories.stream().map(
                CategoryDTO::fromEntity
        ).toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOList);
        categoryResponse.setPageNumber(categories.getNumber());
        categoryResponse.setPageSize(categories.getSize());
        categoryResponse.setTotalElements(categories.getTotalElements());
        categoryResponse.setTotalPages(categories.getTotalPages());
        categoryResponse.setLastPage(categories.isLast());
        return categoryResponse;
    }
}
