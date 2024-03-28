package com.junior.money.api.repository.expense;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.junior.money.api.dto.ExpenseDto;
import com.junior.money.api.repository.filter.ExpenseFilter;

public interface ExpenseRepositoryQuery {

    public Page<ExpenseDto> filter(ExpenseFilter filter, Pageable pageable);
}
