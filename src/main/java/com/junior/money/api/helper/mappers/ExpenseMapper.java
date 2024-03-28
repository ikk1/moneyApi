package com.junior.money.api.helper.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.junior.money.api.dto.ExpenseDto;
import com.junior.money.api.models.Expense;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ExpenseMapper {

    Expense toEntity(ExpenseDto expenseDto);

    ExpenseDto toDto(Expense expense);

}
