package com.example.application.data.service;

import com.example.application.data.entity.LoanedBooks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanedBooksRepository extends JpaRepository<LoanedBooks, Integer> {

    List<LoanedBooks>findByUserId (Integer userId);
    List<LoanedBooks>findAll();
    //List<LoanedBooks>findExpiredBooks (Integer userID, Integer expired);






}
