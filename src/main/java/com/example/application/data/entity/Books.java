package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Books extends AbstractEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id_books;
    private String title;
    private String description;
    private String genre;
    private String author;
    private String for_ages;
    private int physical_amount;
    private int e_book;
    private double price;
    private int physical_active_borrowed;
    private int e_active_borrowed;
    private int total_amount_borrowed;
    private int shelf;
    private String section;
    private Date date_added;
    private int isbn;
    private String publisher;
    private int is_active;


    public Long getId_books() { return Id_books; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getFor_ages() { return for_ages; }
    public void setFor_ages(String for_ages) { this.for_ages = for_ages; }
    public int getPhysical_amount() { return physical_amount; }
    public void setPhysical_amount(int physical_amount) { this.physical_amount = physical_amount; }
    public int getE_book() { return e_book; }
    public void setE_book(int e_book) { this.e_book = e_book; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getPhysical_active_borrowed() { return physical_active_borrowed; }
    public void setPhysical_active_borrowed(int physical_active_borrowed) { this.physical_active_borrowed = physical_active_borrowed; }
    public int getE_active_borrowed() { return e_active_borrowed; }
    public void setE_active_borrowed(int e_active_borrowed) { this.e_active_borrowed = e_active_borrowed; }
    public int getTotal_amount_borrowed() { return total_amount_borrowed; }
    public void setTotal_amount_borrowed(int total_amount_borrowed) { this.total_amount_borrowed = total_amount_borrowed; }
    public int getShelf() { return shelf; }
    public void setShelf(int shelf) { this.shelf = shelf; }
    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }
    public Date getDate_added() { return date_added; }
    public void setDate_added(Date date_added) { this.date_added = date_added; }
    public int getIsbn() { return isbn; }
    public void setIsbn(int isbn) { this.isbn = isbn; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public int getIs_active() { return is_active; }
    public void setIs_active(int is_active) { this.is_active = is_active; }


    @Override
    public String toString(){
        return String.format("Book[id=%d, Title='%s', description='%s', genre='%s', author='%s', " +
                        "for_ages='%s', physical_amount='%s', e_book='%s', price='%s', physical_active_borrowed='%s', " +
                        "e_active_borrowed='%s', total_amount_borrowed='%s', shelf='%s', section='%s', " +
                        "date_added='%s', isbn='%s',  publisher='%s', is_active='%s', id='%s', name='%s',]",
                Id_books, title, description, genre, author, for_ages, physical_amount,
                e_book, price, physical_active_borrowed, e_active_borrowed, total_amount_borrowed,
                shelf, section, date_added, isbn, publisher, is_active);
    }

}
