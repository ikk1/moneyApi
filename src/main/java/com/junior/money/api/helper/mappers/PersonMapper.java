package com.junior.money.api.helper.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.junior.money.api.dto.PersonDto;
import com.junior.money.api.models.Person;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    Person toEntity(PersonDto personDto);

    PersonDto toDto(Person person);

}
