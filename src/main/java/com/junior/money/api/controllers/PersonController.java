package com.junior.money.api.controllers;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.dto.PersonDto;
import com.junior.money.api.helper.mappers.PersonMapper;
import com.junior.money.api.models.Person;
import com.junior.money.api.service.PersonService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("persons")
public class PersonController {

    private final PersonService personService;
    private final ApplicationEventPublisher publisher;
    private final PersonMapper personMapper;

    public PersonController(PersonService personService, ApplicationEventPublisher publisher, PersonMapper personMapper) {
        this.personService = personService;
        this.publisher = publisher;
        this.personMapper = personMapper;
    }

    @GetMapping
    public List<PersonDto> getAllPersons() {
        return personService.getAllPersons().stream().map(personMapper::toDto).toList();
    }

    @GetMapping("/{code}")
    public ResponseEntity<PersonDto> getPersonByCode(@PathVariable Long code) {
        return ResponseEntity.ok(personMapper.toDto(personService.getPersonByCode(code)));
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody PersonDto personDto,
            HttpServletResponse response) {
        Person personEntity = personMapper.toEntity(personDto);
        personService.createPerson(personEntity, response, publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(personDto);
    }

    @PutMapping("/{code}")
    public ResponseEntity<PersonDto> putPerson(@PathVariable Long code, @Valid @RequestBody PersonDto personDto) {
        Person personEntity = personMapper.toEntity(personDto);
        personService.updatePerson(code, personEntity);
        return ResponseEntity.status(HttpStatus.OK).body(personDto);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<PersonDto> patchPerson(@PathVariable Long code, @RequestBody PersonDto personDto)
            throws BeansException {

        Person personEntity = personMapper.toEntity(personDto);
        personService.updatePerson(code, personEntity);
        return ResponseEntity.status(HttpStatus.OK).body(personDto);
    }

}