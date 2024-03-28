package com.junior.money.api.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.junior.money.api.event.CreatedResourceEvent;
import com.junior.money.api.helper.NullAwareBeanUtils;
import com.junior.money.api.models.Person;
import com.junior.money.api.repository.PersonRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final NullAwareBeanUtils nullAwareBeanUtils;

    public PersonService(PersonRepository personRepository, NullAwareBeanUtils nullAwareBeanUtils) {
        this.personRepository = personRepository;
        this.nullAwareBeanUtils = nullAwareBeanUtils;
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonByCode(Long code) {
        return findPersonByCode(code);
    }

    public void deletePersonByCode(Long code) {
        Person savedPerson = findPersonByCode(code);
        personRepository.delete(savedPerson);
    }

    public Person updatePerson(Long code, Person person) {
        Person savedPerson = findPersonByCode(code);
        nullAwareBeanUtils.copyProperties(person, savedPerson);
        personRepository.save(person);
        return savedPerson;
    }

    public Person createPerson(Person person, HttpServletResponse response, ApplicationEventPublisher publisher) {
        Person savedPerson = personRepository.save(person);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedPerson.getCode()));
        return savedPerson;
    }

    private Person findPersonByCode(Long code) {
        return personRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

}
