package com.example.application.data.service;

import com.example.application.data.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

   // List<Person> findByFirstNameIgnoreCase(String first_name);
   // List<Person> findByLastNameIgnoreCase( String last_name);
    List<Person> findByEmailIgnoreCase( String email);

}