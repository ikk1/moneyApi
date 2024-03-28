package com.junior.money.api.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.junior.money.api.dto.ExpenseDto;
import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.helper.mappers.ExpenseMapper;
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
    private final ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseRepository expenseRepository, PersonRepository personRepository, ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.personRepository = personRepository;
        this.expenseMapper = expenseMapper;
    }

    public Page<ExpenseDto> getAllExpenses(ExpenseFilter expenseFilter, Pageable pageable) {
        return expenseRepository.filter(expenseFilter, pageable);
    }

    public ExpenseDto getExpenseByCode(Long code) {
        Expense savedExpense = findExpenseByCode(code);
        return expenseMapper.toDto(savedExpense);
    }

    public ExpenseDto createExpense(ExpenseDto expenseDto, HttpServletResponse response,
            ApplicationEventPublisher publisher) {

        Person savedPerson = personRepository.findById(expenseDto.person().code())
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        if (savedPerson.isInactive())
            throw new PersonInactiveOrMissingException();

        Expense expenseEntity = expenseMapper.toEntity(expenseDto);

        Expense savedExpense = expenseRepository.save(expenseEntity);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedExpense.getCode()));
        return expenseMapper.toDto(savedExpense);
    }

    public void deleteExpense(Long code) {
        Expense savedExpense = findExpenseByCode(code);
        expenseRepository.delete(savedExpense);
    }

    private Expense findExpenseByCode(Long code) {
        return expenseRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}
