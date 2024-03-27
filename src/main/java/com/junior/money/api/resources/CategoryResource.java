package com.junior.money.api.resources;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.models.Category;
import com.junior.money.api.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher publisher;

    public CategoryResource(CategoryRepository categoryRepository, ApplicationEventPublisher publisher) {
        this.categoryRepository = categoryRepository;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Category> findCategoryByCode(@PathVariable Long code) {
        return categoryRepository.findById(code).map(category -> ResponseEntity.ok().body(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category,
            HttpServletResponse response) {
        Category savedCategory = categoryRepository.save(category);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedCategory.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

}
