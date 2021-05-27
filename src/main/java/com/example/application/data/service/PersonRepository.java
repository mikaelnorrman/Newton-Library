package com.example.application.data.service;

import com.example.application.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    List<Person> findByFirstNameStartsWithIgnoreCase(String first_name);
    List<Person> findByLastNameStartsWithIgnoreCase( String last_name);
    List<Person> findByEmailStartsWithIgnoreCase( String email);

}