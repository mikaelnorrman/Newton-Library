package com.example.application.data.service;

import com.example.application.data.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books, Long> {

    List<Books> findByTitleStartsWithIgnoreCase (String title);
    List<Books> findByGenreStartsWithIgnoreCase (String genre);
    List<Books> findByAuthorStartsWithIgnoreCase (String author);
    List<Books> findByPublisherStartsWithIgnoreCase (String publisher);
    List<Books> findByIsbnStartsWithIgnoreCase (int isbn);

}
