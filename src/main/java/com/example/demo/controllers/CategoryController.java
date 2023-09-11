package com.example.demo.controllers;

import com.example.demo.config.AppConstants;
import com.example.demo.dto.CategoryDTO;
import com.example.demo.exceptions.ApiResponse;
import com.example.demo.payload.response.CategoryResponse;
import com.example.demo.services.CategoryService;
import com.example.demo.services.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO ){
            CategoryDTO created = categoryService.createCategory(categoryDTO);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id ,@RequestBody CategoryDTO categoryDTO ){
        CategoryDTO updated = categoryService.updateCategory(id,categoryDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(
                new ApiResponse("Category is deleted successfully", true), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getCategories(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        CategoryResponse categoryResponse = categoryService.getCategories(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
}
