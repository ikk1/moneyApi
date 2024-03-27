package com.junior.money.api.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.models.Category;
import com.junior.money.api.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher publisher;

    public CategoryService(CategoryRepository categoryRepository, ApplicationEventPublisher publisher) {
        this.categoryRepository = categoryRepository;
        this.publisher = publisher;
    }

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long code) {
        return findCategoryById(code);
    }

    public Category createCategory(Category category, HttpServletResponse response) {
        Category savedCategory = categoryRepository.save(category);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedCategory.getCode()));
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long code) {
        Category savedCategory = findCategoryById(code);
        categoryRepository.delete(savedCategory);
    }

    private Category findCategoryById(Long code) {
        Category savedCategory = categoryRepository.findById(code)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return savedCategory;
    }

}
