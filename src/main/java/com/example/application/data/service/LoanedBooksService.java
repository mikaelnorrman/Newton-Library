package com.example.application.data.service;

import com.example.application.data.entity.LoanedBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class LoanedBooksService extends CrudService<LoanedBooks, Integer> {

    private LoanedBooksRepository loanedBooksRepository;

    public LoanedBooksService(@Autowired LoanedBooksRepository loanedBooksRepository)
    {this.loanedBooksRepository = loanedBooksRepository;}


    @Override
    protected JpaRepository<LoanedBooks, Integer> getRepository() {
        return loanedBooksRepository;
    }
}