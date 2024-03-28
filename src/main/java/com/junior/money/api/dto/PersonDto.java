package com.junior.money.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PersonDto(
        Long code,
        @NotBlank @NotNull String name,
        @NotNull Boolean active,
        AddressDto address
) {}