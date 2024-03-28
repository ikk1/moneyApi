package com.junior.money.api.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.models.Expense;
import com.junior.money.api.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseResource {

    private final ExpenseService expenseService;

    public ExpenseResource(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Expense> getExpenseByCode(@PathVariable Long code) {
        Expense savedExpense = expenseService.getExpenseByCode(code);
        return ResponseEntity.ok(savedExpense);
    }
}
