package com.junior.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.junior.money.api.models.Expense;
import com.junior.money.api.repository.expense.ExpenseRepositoryQuery;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>, ExpenseRepositoryQuery{}
