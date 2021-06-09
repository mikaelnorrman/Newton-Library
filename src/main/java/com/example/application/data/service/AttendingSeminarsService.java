package com.example.application.data.service;

import com.example.application.data.entity.AttendingSeminars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class AttendingSeminarsService extends CrudService<AttendingSeminars, Integer> {

    private AttendingSeminarsRepository attendingSeminarsRepository;

    public AttendingSeminarsService(@Autowired AttendingSeminarsRepository attendingSeminarsRepository)
    {this.attendingSeminarsRepository = attendingSeminarsRepository;}

    @Override
    protected JpaRepository<AttendingSeminars, Integer> getRepository() {
        return attendingSeminarsRepository; }


}
