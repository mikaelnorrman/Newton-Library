package com.example.application.data.service;

import com.example.application.data.entity.Seminars;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class SeminarService extends CrudService <Seminars, Integer> {

    private SeminarsRepository seminarsRepository;

    public SeminarService(@Autowired SeminarsRepository seminarsRepository)
    {this.seminarsRepository = seminarsRepository;}


    @Override
    protected JpaRepository<Seminars, Integer> getRepository() {
        return seminarsRepository;
    }
}
