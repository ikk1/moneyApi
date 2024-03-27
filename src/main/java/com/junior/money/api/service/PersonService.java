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
    private final ApplicationEventPublisher publisher;
    private HttpServletResponse response;
    private final NullAwareBeanUtils nullAwareBeanUtils;

    public PersonService(PersonRepository personRepository, ApplicationEventPublisher publisher,
            HttpServletResponse response, NullAwareBeanUtils nullAwareBeanUtils) {
        this.personRepository = personRepository;
        this.publisher = publisher;
        this.response = response;
        this.nullAwareBeanUtils = nullAwareBeanUtils;
    }

    public List<Person> listAll() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long code) {
        Person savedPerson = personRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
        return savedPerson;
    }

    public void deletePersonById(Long code) {
        Person savedPerson = personRepository.findById(code).orElseThrow(() -> new EmptyResultDataAccessException(1));
        personRepository.delete(savedPerson);
    }

    public Person updatePerson(Long code, Person person) {
        Person savedPerson = personRepository.findById(code).orElse(null);
        if (savedPerson == null)
            throw new EmptyResultDataAccessException(1);
        nullAwareBeanUtils.copyProperties(person, savedPerson);
        personRepository.save(person);
        return savedPerson;
    }

    public Person createPerson(Person person) {
        Person savedPerson = personRepository.save(person);
        publisher.publishEvent(new CreatedResourceEvent(this, response, savedPerson.getCode()));
        return savedPerson;
    }

}