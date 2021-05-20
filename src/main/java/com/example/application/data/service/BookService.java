package com.example.application.data.service;

import com.example.application.data.entity.Books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.vaadin.artur.helpers.CrudService;

@Service
public class BookService extends CrudService <Books, Integer> {

    private BooksRepository booksRepository;

    public BookService(@Autowired BooksRepository booksRepository)
    {this.booksRepository = booksRepository;}


    @Override
    protected JpaRepository<Books, Integer> getRepository() {
        return booksRepository;
    }
}
