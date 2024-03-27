package com.junior.money.api.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.models.Category;
import com.junior.money.api.service.CategoryService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> listAll() {
        return categoryService.listAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> findCategoryByCode(@PathVariable Long code) {
        Category savedCategory = categoryService.getCategoryById(code);
        return ResponseEntity.ok(savedCategory);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category,
            HttpServletResponse response) {
        Category savedCategory = categoryService.createCategory(category, response);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

}
