package com.junior.money.api.models;

public enum ExpenseType {
    REVENUE("Revenue"),
    EXPENSE("Expense");

    private String description;

    ExpenseType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
