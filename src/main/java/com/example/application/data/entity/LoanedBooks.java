package com.example.application.data.entity;

import javax.persistence.*;

@Table(name = "loaned_books", schema = "agile_library")
@Entity
public class LoanedBooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loaned")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "loaned")
    private String loaned;

    @Column(name = "expired")
    private Integer expired;

    @Column(name = "books_id_books")
    private Integer books_id_books;

    @Column(name = "due_date")
    private String dueDate;

    @Column(name = "users_id_users")
    private Integer userId;

    @Column(name= "first_name")
    private String firstName;

    @Column(name= "last_name")
    private String lastName;

    public LoanedBooks(){
    }

    public LoanedBooks(

            Integer books_id_books,
            Integer users_id_users,
            String title,
            Integer expired,
            String firstName,
            String lastName) {

        this.books_id_books = books_id_books;
        this.userId = users_id_users;
        this.title = title;
        this.expired = expired;
        this.firstName = firstName;
        this.lastName = lastName;
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
        return userId;
    }

    public void setUsers_id_users(Integer users_id_users) {
        this.userId = users_id_users;
    }

    public String getTitle(){return this.title;}

    public String getDueDate (){return  this.dueDate;}

    public void setDueDate (){this.dueDate = dueDate;}

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }


    @Override
    public String toString() {
        return "LoanedBooks{" +
                "id=" + id +
                ", title='" + title +
                ", loaned='" + loaned + '\'' +
                ", expired=" + expired +
                ", books_id_books=" + books_id_books +
                ", users_id_users='" + userId +
                ", dueDate='" + dueDate +
                ", firstName='" + firstName +
                ", lastName='" + lastName +
                '}';
    }
}
