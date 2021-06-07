package com.example.application.data.service;

import com.example.application.data.entity.LoanedBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanedBooksRepository extends JpaRepository<LoanedBooks, Integer> {

    //List<LoanedBooks>findByUsersStartsWithIgnoreCase (Integer user);
    //List<LoanedBooks>findExpiredBooks (Integer expired);






}
