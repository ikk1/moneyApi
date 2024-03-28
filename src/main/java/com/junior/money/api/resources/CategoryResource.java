package com.junior.money.api.resources;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.dto.CategoryDto;
import com.junior.money.api.service.CategoryService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryService categoryService;
    private final ApplicationEventPublisher publisher;

    public CategoryResource(CategoryService categoryService, ApplicationEventPublisher publisher) {
        this.categoryService = categoryService;
        this.publisher = publisher;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{code}")
    public ResponseEntity<CategoryDto> findCategoryByCode(@PathVariable Long code) {
        CategoryDto savedCategory = categoryService.getCategoryByCode(code);
        return ResponseEntity.ok(savedCategory);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto,
            HttpServletResponse response) {
                CategoryDto savedCategory = categoryService.createCategory(categoryDto, response, publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

}
