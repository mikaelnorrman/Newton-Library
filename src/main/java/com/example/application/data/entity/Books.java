package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
public class Books {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_books;

    private String title = "";
    private String description = "";
    private String genre = "";
    private String author = "";
    private String for_ages = "";
    private Integer physical_amount;
    private Integer e_book;

    @NotNull
    private Integer price;
    private Integer physical_active_borrowed;
    private Integer e_active_borrowed;
    private Integer total_amount_borrowed;
    private Integer shelf;
    private String section = "";
    private Date date_added;

    @NotNull
    private String isbn = "";
    private String publisher = "";
    private Integer is_active;

    @NotNull
    private Integer id;
    private String name = "";

    public Books(Integer id_books, String title, String description,
                 String genre, String author, String for_ages,
                 Integer physical_amount, Integer e_book, Integer price,
                 Integer physical_active_borrowed, Integer e_active_borrowed,
                 Integer total_amount_borrowed, Integer shelf, String section,
                 Date date_added, String isbn, String publisher, Integer is_active,
                 Integer id, String name) {
        this.id_books = id_books;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.author = author;
        this.for_ages = for_ages;
        this.physical_amount = physical_amount;
        this.e_book = e_book;
        this.price = price;
        this.physical_active_borrowed = physical_active_borrowed;
        this.e_active_borrowed = e_active_borrowed;
        this.total_amount_borrowed = total_amount_borrowed;
        this.shelf = shelf;
        this.section = section;
        this.date_added = date_added;
        this.isbn = isbn;
        this.publisher = publisher;
        this.is_active = is_active;
        this.id = id;
        this.name = name;
    }

    public Books(){
    }

    public Integer getId_books() {
        return id_books;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFor_ages() {
        return for_ages;
    }

    public void setFor_ages(String for_ages) {
        this.for_ages = for_ages;
    }

    public Integer getPhysical_amount() {
        return physical_amount;
    }

    public void setPhysical_amount(Integer physical_amount) {
        this.physical_amount = physical_amount;
    }

    public Integer getE_book() {
        return e_book;
    }

    public void setE_book(Integer e_book) {
        this.e_book = e_book;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPhysical_active_borrowed() {
        return physical_active_borrowed;
    }

    public void setPhysical_active_borrowed(Integer physical_active_borrowed) {
        this.physical_active_borrowed = physical_active_borrowed;
    }

    public Integer getE_active_borrowed() {
        return e_active_borrowed;
    }

    public void setE_active_borrowed(Integer e_active_borrowed) {
        this.e_active_borrowed = e_active_borrowed;
    }

    public Integer getTotal_amount_borrowed() {
        return total_amount_borrowed;
    }

    public void setTotal_amount_borrowed(Integer total_amount_borrowed) {
        this.total_amount_borrowed = total_amount_borrowed;
    }

    public Integer getShelf() {
        return shelf;
    }

    public void setShelf(Integer shelf) {
        this.shelf = shelf;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getIs_active() {
        return is_active;
    }

    public void setIs_active(Integer is_active) {
        this.is_active = is_active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*
    @Override
    public String toString(){
        return String.format("Book[id=%d, Title='%s', description='%s', genre='%s', author='%s', " +
                        "for_ages='%s', physical_amount='%s', e_book='%s', price='%s', physical_active_borrowed='%s', " +
                        "e_active_borrowed='%s', total_amount_borrowed='%s', shelf='%s', section='%s', " +
                        "date_added='%s', isbn='%s',  publisher='%s', is_active='%s', id='%s', name='%s',]",
                id_books, title, description, genre, author, for_ages, physical_amount,
                e_book, price, physical_active_borrowed, e_active_borrowed, total_amount_borrowed,
                shelf, section, date_added, isbn, publisher, is_active);
    }
     */

    @Override
    public String toString() {
        return "Books{" +
                "id_books=" + id_books +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", author='" + author + '\'' +
                ", for_ages='" + for_ages + '\'' +
                ", physical_amount=" + physical_amount +
                ", e_book=" + e_book +
                ", price=" + price +
                ", physical_active_borrowed=" + physical_active_borrowed +
                ", e_active_borrowed=" + e_active_borrowed +
                ", total_amount_borrowed=" + total_amount_borrowed +
                ", shelf=" + shelf +
                ", section='" + section + '\'' +
                ", date_added=" + date_added +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", is_active=" + is_active +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
