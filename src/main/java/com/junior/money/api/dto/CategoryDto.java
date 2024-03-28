package com.junior.money.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDto(
        Long code,
        @NotNull @NotBlank String name
) {}