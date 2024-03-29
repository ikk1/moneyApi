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
import com.junior.money.api.helper.mappers.CategoryMapper;
import com.junior.money.api.models.Category;
import com.junior.money.api.service.CategoryService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryService categoryService;
    private final ApplicationEventPublisher publisher;
    private final CategoryMapper categoryMapper;

    public CategoryResource(CategoryService categoryService, ApplicationEventPublisher publisher, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.publisher = publisher;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories().stream().map(categoryMapper::toDto).toList();
    }

    @GetMapping("/{code}")
    public ResponseEntity<CategoryDto> findCategoryByCode(@PathVariable Long code) {
        return ResponseEntity.ok(categoryMapper.toDto(categoryService.getCategoryByCode(code)));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto,
            HttpServletResponse response) {
        Category categoryEntity = categoryMapper.toEntity(categoryDto);
        categoryService.createCategory(categoryEntity, response, publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

}
