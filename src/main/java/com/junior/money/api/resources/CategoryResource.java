package com.junior.money.api.resources;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.models.Category;
import com.junior.money.api.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/categories")
public class CategoryResource {
    
    private final CategoryRepository categoryRepository;

    public CategoryResource(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

}
