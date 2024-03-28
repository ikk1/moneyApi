package com.junior.money.api.dto;

public record AddressDto(
        String address,
        String addressLine2,
        String addressNumber,
        String district,
        String zipCode
) {}