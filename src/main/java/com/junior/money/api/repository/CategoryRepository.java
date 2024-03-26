package com.junior.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junior.money.api.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
}
