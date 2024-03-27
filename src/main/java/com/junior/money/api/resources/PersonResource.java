package com.junior.money.api.resources;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.helper.NullAwareBeanUtils;
import com.junior.money.api.models.Person;
import com.junior.money.api.repository.PersonRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/person")
public class PersonResource {

    private final PersonRepository personRepository;
    private final ApplicationEventPublisher publisher;
    private final NullAwareBeanUtils nullAwareBeanUtils;

    public PersonResource(PersonRepository personRepository, ApplicationEventPublisher publisher, NullAwareBeanUtils nullAwareBeanUtils) {
        this.personRepository = personRepository;
        this.publisher = publisher;
        this.nullAwareBeanUtils = nullAwareBeanUtils;
    }

    @GetMapping
    public List<Person> listAll() {
        return personRepository.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long code) {
        return personRepository.findById(code)
                .map(person -> ResponseEntity.ok().body(person))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person savedPerson = personRepository.save(person);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedPerson.getCode()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Person> putPerson(@PathVariable Long code, @Valid @RequestBody Person person) {
        Person savedPerson = personRepository.findById(code).orElse(null);
        if (savedPerson == null) return ResponseEntity.notFound().build();
        nullAwareBeanUtils.copyProperties(person, savedPerson);
        personRepository.save(person);
        return ResponseEntity.status(HttpStatus.OK).body(savedPerson);
    }

    @PatchMapping("/{code}")
    public ResponseEntity<Person> patchPerson(@PathVariable Long code, @RequestBody Person person) throws BeansException {
        Person savedPerson = personRepository.findById(code).orElse(null);
        if (savedPerson == null) return ResponseEntity.notFound().build();
        nullAwareBeanUtils.copyProperties(person, person);
        personRepository.save(savedPerson);
        return ResponseEntity.status(HttpStatus.OK).body(savedPerson);
    }

}