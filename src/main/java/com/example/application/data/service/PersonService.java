package com.example.application.data.service;

import com.example.application.data.entity.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class PersonService extends CrudService <Person, Integer> {

    private PersonRepository personRepository;

    public PersonService(@Autowired PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /*
    @Override
    protected PersonRepository getRepository() {
        return personRepository;
    }

     */

    @Override
    protected JpaRepository<Person, Integer> getRepository() {
        return personRepository;
    }
}
