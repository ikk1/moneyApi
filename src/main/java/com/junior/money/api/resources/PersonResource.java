package com.junior.money.api.resources;

import java.util.List;

import org.springframework.beans.BeansException;
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

import com.junior.money.api.models.Person;
import com.junior.money.api.service.PersonService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/person")
public class PersonResource {

    private final PersonService personService;
  
    public PersonResource(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public List<Person> listAll() {
        return personService.listAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long code) {
        Person savedPerson = personService.getPersonById(code);
        return ResponseEntity.ok(savedPerson);
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person savedPerson = personService.createPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Person> putPerson(@PathVariable Long code, @Valid @RequestBody Person person) {
        Person savedPerson = personService.updatePerson(code, person);
        return ResponseEntity.status(HttpStatus.OK).body(savedPerson);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Person> patchPerson(@PathVariable Long code, @RequestBody Person person) throws BeansException {
        Person savedPerson = personService.updatePerson(code, person);
        return ResponseEntity.status(HttpStatus.OK).body(savedPerson);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long code) {
        personService.deletePersonById(code);
        return ResponseEntity.noContent().build();
    }

}