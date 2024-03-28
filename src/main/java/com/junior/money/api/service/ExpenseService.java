package com.junior.money.api.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.models.Expense;
import com.junior.money.api.repository.ExpenseRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseByCode(Long code) {
        return expenseRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Expense createExpense(Expense expense, HttpServletResponse response, ApplicationEventPublisher publisher) {
        Expense savedExpense = expenseRepository.save(expense);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedExpense.getCode()));
        return savedExpense;
    }
}
