package com.junior.money.api.resources;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.models.Expense;
import com.junior.money.api.repository.filter.ExpenseFilter;
import com.junior.money.api.service.ExpenseService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/expenses")
public class ExpenseResource {

    private final ExpenseService expenseService;
    private final ApplicationEventPublisher publisher;

    public ExpenseResource(ExpenseService expenseService, ApplicationEventPublisher publisher) {
        this.expenseService = expenseService;
        this.publisher = publisher;
    }

    @GetMapping
    public List<Expense> getAllExpenses(ExpenseFilter expenseFilter) {
        return expenseService.getAllExpenses(expenseFilter);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Expense> getExpenseByCode(@PathVariable Long code) {
        Expense savedExpense = expenseService.getExpenseByCode(code);
        return ResponseEntity.ok(savedExpense);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense, HttpServletResponse response) {
        expense = expenseService.createExpense(expense, response, publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(expense);
    }
    
}
