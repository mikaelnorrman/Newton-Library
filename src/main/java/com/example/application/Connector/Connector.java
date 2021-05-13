package com.example.application.Connector;

import com.example.application.data.entity.Books;
import org.joda.time.DateTime;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Optional;

public interface Connector {

    Optional <Books> getBook(String firstName);

    void callAddBook(String title, String description, String genre, String author, String for_ages,
                     String physical_amount, String e_book, String price, String shelf, String section,
                     String isbn, String publisher) ;

    void callAddSeminar(String name, String presenter, String description, Time length, Date dateTime, boolean active);

    void callAddUser(String first_name, String last_name, String email, String phone, String street, String postal_code,
                     String city, int social_security_no, int active_borrowed_books, int total_borrowed_books, String password,
                     DateTime date_added, int loancard, int role_id, int id);

    void callDeleteBook(int bookID) throws SQLException;
    void callDeleteUser(int userID) throws SQLException;
    void callUpdateSeminar(int seminarID, String name, String presenter, String description, Time lenght, Date dateTime,
                           boolean active) throws SQLException;
}
