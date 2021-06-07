package com.example.application.editors;

import com.example.application.data.entity.LoanedBooks;
import com.example.application.data.service.LoanedBooksRepository;
import com.vaadin.flow.data.binder.Binder;

public class LoanedBookEditor extends Editor {


    public LoanedBookEditor(LoanedBooksRepository loanedBooksRepository) {
        this.loanedBooksRepository = loanedBooksRepository;
        this.loanedBooksBinder = new Binder<>(LoanedBooks.class);

    }

     public void saveLoaned(LoanedBooks loanedBooks) {
        loanedBooksRepository.save(loanedBooks);
           //changeHandler.onChange();
        }
}