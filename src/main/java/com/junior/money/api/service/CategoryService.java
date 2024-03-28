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

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByCode(Long code) {
        return findCategoryByCode(code);
    }

    public Category createCategory(Category category, HttpServletResponse response, ApplicationEventPublisher publisher) {
        Category savedCategory = categoryRepository.save(category);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedCategory.getCode()));
        return savedCategory;
    }

    public void deleteCategoryByCode(Long code) {
        Category savedCategory = findCategoryByCode(code);
        categoryRepository.delete(savedCategory);
    }

    private Category findCategoryByCode(Long code) {
        Category savedCategory = categoryRepository.findById(code)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return savedCategory;
    }

}
