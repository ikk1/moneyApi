package com.junior.money.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.junior.money.api.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}
