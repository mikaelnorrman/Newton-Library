package com.example.application.data.service;

import com.example.application.data.entity.AttendingSeminars;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendingSeminarsRepository extends JpaRepository<AttendingSeminars, Integer>{

    List<AttendingSeminars> findByUserId (Integer IdUser);
    List<AttendingSeminars>findAll();


}
