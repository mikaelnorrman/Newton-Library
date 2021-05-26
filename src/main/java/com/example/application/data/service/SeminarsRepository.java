package com.example.application.data.service;


import com.example.application.data.entity.Seminars;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeminarsRepository extends JpaRepository<Seminars, Integer> {

    List<Seminars> findByNameStartsWithIgnoreCase (String name);
    List<Seminars> findByPresenterStartsWithIgnoreCase (String presenter);

}
