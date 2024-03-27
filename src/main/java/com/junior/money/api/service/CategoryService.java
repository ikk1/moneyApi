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
    private HttpServletResponse response;

    public CategoryService(CategoryRepository categoryRepository, ApplicationEventPublisher publisher, HttpServletResponse response) {
        this.categoryRepository = categoryRepository;
        this.publisher = publisher;
        this.response = response;
    }

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long code) {
        Category savedCategory = categoryRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return savedCategory;
    }

    public Category createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedCategory.getCode()));
        return categoryRepository.save(category);
    }

    public void deleteCategoryById(Long code) {
        Category savedCategory = categoryRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
        categoryRepository.delete(savedCategory);
    }
    

}

