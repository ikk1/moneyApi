package com.junior.money.api.resources;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.models.Person;
import com.junior.money.api.repository.PersonRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
public class PersonResource {

    private final PersonRepository personRepository;
    private final ApplicationEventPublisher publisher;

    public PersonResource(PersonRepository personRepository, ApplicationEventPublisher publisher) {
        this.personRepository = personRepository;
        this.publisher = publisher;
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
    public ResponseEntity<Person> postMethodName(@Valid @RequestBody Person person, HttpServletResponse response) {
        Person savedPerson = personRepository.save(person);
        try {
            publisher.publishEvent(new CreatedResourceEvent(this, response, savedPerson.getCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

}
