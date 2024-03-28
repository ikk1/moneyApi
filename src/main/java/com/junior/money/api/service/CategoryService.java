package com.junior.money.api.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.junior.money.api.dto.CategoryDto;
import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.helper.mappers.CategoryMapper;
import com.junior.money.api.models.Category;
import com.junior.money.api.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public CategoryDto getCategoryByCode(Long code) {
        Category savedCategory = findCategoryByCode(code);
        return categoryMapper.toDto(savedCategory);
    }

    public CategoryDto createCategory(CategoryDto category, HttpServletResponse response,
            ApplicationEventPublisher publisher) {
        Category savedCategory = categoryRepository.save(categoryMapper.toEntity(category));
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedCategory.getCode()));
        return categoryMapper.toDto(savedCategory);
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
