package com.junior.money.api.helper.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.junior.money.api.dto.CategoryDto;
import com.junior.money.api.models.Category;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);
    
}
