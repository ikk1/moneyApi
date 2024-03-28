package com.junior.money.api.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.models.Expense;
import com.junior.money.api.models.Person;
import com.junior.money.api.repository.ExpenseRepository;
import com.junior.money.api.repository.PersonRepository;
import com.junior.money.api.repository.filter.ExpenseFilter;
import com.junior.money.api.service.exception.PersonInactiveOrMissingException;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final PersonRepository personRepository;

    public ExpenseService(ExpenseRepository expenseRepository, PersonRepository personRepository) {
        this.expenseRepository = expenseRepository;
        this.personRepository = personRepository;
    }

    public Page<Expense> getAllExpenses(ExpenseFilter expenseFilter, Pageable pageable) {
        return expenseRepository.filter(expenseFilter, pageable);
    }

    public Expense getExpenseByCode(Long code) {
        return expenseRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    public Expense createExpense(Expense expense, HttpServletResponse response, ApplicationEventPublisher publisher) {
        Person savedPerson = personRepository.findById(expense.getPerson().getCode()).orElseThrow(() -> new EmptyResultDataAccessException(1));

        if(savedPerson.isInactive())
            throw new PersonInactiveOrMissingException();

        Expense savedExpense = expenseRepository.save(expense);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedExpense.getCode()));
        return savedExpense;
    }

    public void deleteExpense(Long code) {
        Expense savedExpense = getExpenseByCode(code);
        expenseRepository.delete(savedExpense);
    }
}
