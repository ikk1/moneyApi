package com.junior.money.api.dto;

import java.time.LocalDate;

import com.junior.money.api.models.ExpenseType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExpenseDto(
        Long code,
        @NotNull @NotBlank @Size(min = 3, max = 50) String description,
        @NotNull LocalDate dueDate,
        @NotNull LocalDate paymentDate,
        @NotNull Double value,
        @Size(max = 100) String notes,
        @NotNull ExpenseType type,
        @NotNull CategoryDto category,
        @NotNull PersonDto person
) {}
