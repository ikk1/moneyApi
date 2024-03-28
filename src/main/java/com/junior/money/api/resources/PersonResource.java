package com.junior.money.api.resources;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.dto.PersonDto;
import com.junior.money.api.service.PersonService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonResource {

    private final PersonService personService;
    private final ApplicationEventPublisher publisher;
  
    public PersonResource(PersonService personService, ApplicationEventPublisher publisher) {
        this.personService = personService;
        this.publisher = publisher;
    }

    @GetMapping
    public List<PersonDto> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{code}")
    public ResponseEntity<PersonDto> getPersonByCode(@PathVariable Long code) {
        PersonDto savedPerson = personService.getPersonByCode(code);
        return ResponseEntity.ok(savedPerson);
    }

    @PostMapping
    public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody PersonDto personDto, HttpServletResponse response) {
        PersonDto savedPerson = personService.createPerson(personDto, response, publisher);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @PutMapping("/{code}")
    public ResponseEntity<PersonDto> putPerson(@PathVariable Long code, @Valid @RequestBody PersonDto personDto) {
        PersonDto savedPerson = personService.updatePerson(code, personDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedPerson);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<PersonDto> patchPerson(@PathVariable Long code, @RequestBody PersonDto personDto) throws BeansException {
        PersonDto savedPerson = personService.updatePerson(code, personDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedPerson);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long code) {
        personService.deletePersonByCode(code);
        return ResponseEntity.noContent().build();
    }

}