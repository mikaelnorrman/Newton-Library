package com.example.application.data.entity;

import javax.persistence.*;

@Table(name = "loaned_books", schema = "agile_library")
@Entity
public class LoanedBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loaned")
    private Integer id;

    @Column(name = "loaned")
    private String loaned;

    @Column(name = "expired")
    private Integer expired;

    @Column(name = "books_id_books")
    private Integer books_id_books;

    @Column(name = "users_id_users")
    private Integer users_id_users;

    public LoanedBooks(){
    }

    public LoanedBooks(

            Integer books_id_books,
            Integer users_id_users,
            Integer expired) {
        this.books_id_books = books_id_books;
        this.users_id_users = users_id_users;
        this.expired = expired;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoaned() {
        return loaned;
    }

    public void setLoaned(String loaned) {
        this.loaned = loaned;
    }

    public int getExpired() {
        return expired;
    }

    public void setExpired(int expired) {
        this.expired = expired;
    }

    public Integer getBooks_id_books() {
        return books_id_books;
    }

    public void setBooks_id_books(Integer books_id_books) {
        this.books_id_books = books_id_books;
    }

    public Integer getUsers_id_users() {
        return users_id_users;
    }

    public void setUsers_id_users(Integer users_id_users) {
        this.users_id_users = users_id_users;
    }

    @Override
    public String toString() {
        return "LoanedBooks{" +
                "id=" + id +
                ", loaned='" + loaned + '\'' +
                ", expired=" + expired +
                ", books_id_books=" + books_id_books +
                ", users_id_users=" + users_id_users +
                '}';
    }
}
