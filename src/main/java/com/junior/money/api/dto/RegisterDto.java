package com.junior.money.api.dto;

import com.junior.money.api.models.UserRole;

public record RegisterDto(String name, String login, String password, UserRole role) {}
