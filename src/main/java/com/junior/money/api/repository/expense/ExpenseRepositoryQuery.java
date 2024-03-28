package com.junior.money.api.repository.expense;

import java.util.List;

import com.junior.money.api.models.Expense;
import com.junior.money.api.repository.filter.ExpenseFilter;

public interface ExpenseRepositoryQuery {

    public List<Expense> filter(ExpenseFilter filter);
}
