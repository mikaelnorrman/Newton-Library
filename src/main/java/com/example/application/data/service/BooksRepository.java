package com.example.application.data.service;

import com.example.application.data.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BooksRepository extends JpaRepository<Books, Integer> {

    List<Books> findByTitleStartsWithIgnoreCase (String title);
    List<Books> findByGenreStartsWithIgnoreCase (String genre);
    List<Books> findByAuthorStartsWithIgnoreCase (String author);
    List<Books> findByPublisherStartsWithIgnoreCase (String publisher);
    List<Books> findByIsbnStartsWithIgnoreCase (String isbn);

    @Query(value = "SELECT * FROM books WHERE " +
            "author REGEXP ?1 OR " +
            "title REGEXP ?1 OR " +
            "genre REGEXP ?1 OR " +
            "isbn REGEXP ?1", nativeQuery = true)
    List<Books> findByAuthorOrTitleOrGenreOrIsbn (String searchString);

}
