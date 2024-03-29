package com.junior.money.api.repository.expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.junior.money.api.models.Expense;
import com.junior.money.api.repository.filter.ExpenseFilter;

public interface ExpenseRepositoryQuery {

    public Page<Expense> filter(ExpenseFilter filter, Pageable pageable);
}
