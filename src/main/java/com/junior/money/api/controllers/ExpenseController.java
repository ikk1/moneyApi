package com.junior.money.api.controllers;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.dto.ExpenseDto;
import com.junior.money.api.helper.mappers.ExpenseMapper;
import com.junior.money.api.models.Expense;
import com.junior.money.api.repository.filter.ExpenseFilter;
import com.junior.money.api.service.ExpenseService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ApplicationEventPublisher publisher;
    private final ExpenseMapper expenseMapper;

    public ExpenseController(ExpenseService expenseService, ApplicationEventPublisher publisher,
            ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.publisher = publisher;
        this.expenseMapper = expenseMapper;
    }

    @GetMapping
    public Page<ExpenseDto> getAllExpenses(ExpenseFilter expenseFilter, Pageable pageable) {
        return expenseService.getAllExpenses(expenseFilter, pageable).map(expenseMapper::toDto);
    }

    @GetMapping("/{code}")
    public ResponseEntity<ExpenseDto> getExpenseByCode(@PathVariable Long code) {
        return ResponseEntity.ok(expenseMapper.toDto(expenseService.getExpenseByCode(code)));
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@Valid @RequestBody ExpenseDto expenseDto,
            HttpServletResponse response) {
        Expense expenseEntity = expenseMapper.toEntity(expenseDto);
        expenseService.createExpense(expenseEntity, response, publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseDto);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long code) {
        expenseService.deleteExpense(code);
        return ResponseEntity.noContent().build();
    }

}
