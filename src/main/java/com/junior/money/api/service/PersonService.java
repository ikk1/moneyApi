package com.junior.money.api.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.junior.money.api.dto.PersonDto;
import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.helper.NullAwareBeanUtils;
import com.junior.money.api.helper.mappers.PersonMapper;
import com.junior.money.api.models.Person;
import com.junior.money.api.repository.PersonRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final NullAwareBeanUtils nullAwareBeanUtils;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, NullAwareBeanUtils nullAwareBeanUtils,
            PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.nullAwareBeanUtils = nullAwareBeanUtils;
        this.personMapper = personMapper;
    }

    public List<PersonDto> getAllPersons() {
        return personRepository.findAll().stream().map(personMapper::toDto).toList();
    }

    public PersonDto getPersonByCode(Long code) {
        Person savedPerson = personRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return personMapper.toDto(savedPerson);
    }

    public void deletePersonByCode(Long code) {
        Person savedPerson = findPersonByCode(code);
        personRepository.delete(savedPerson);
    }

    public PersonDto updatePerson(Long code, PersonDto person) {
        Person savedPerson = findPersonByCode(code);
        Person personEntity = personMapper.toEntity(person);
        nullAwareBeanUtils.copyProperties(personEntity, savedPerson);
        personRepository.save(personEntity);
        return personMapper.toDto(personEntity);
    }

    public PersonDto createPerson(PersonDto personDto, HttpServletResponse response, ApplicationEventPublisher publisher) {
        Person savedPerson = personRepository.save(personMapper.toEntity(personDto));
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedPerson.getCode()));
        return personMapper.toDto(savedPerson);
    }

    private Person findPersonByCode(Long code) {
        Person savedPerson = personRepository.findById(code)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));
        return savedPerson;
    }
}
