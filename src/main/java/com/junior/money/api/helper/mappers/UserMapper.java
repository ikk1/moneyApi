package com.junior.money.api.helper.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.junior.money.api.dto.RegisterDto;
import com.junior.money.api.models.User;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(RegisterDto userDto);
}
