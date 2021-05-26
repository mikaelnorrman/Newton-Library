package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "books", schema = "agile_library")
@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_books;

    @Column(name="title")
    private String title = "";

    @Column(name="description")
    private String description = "";

    @Column(name="genre")
    private String genre = "";

    @Column(name="author")
    private String author = "";

    @Column(name="for_ages")
    private String for_ages = "";

    @Column(name="physical_amount")
    private String physical_amount;

    @Column(name="e_book")
    private String e_book;

    @NotNull
    @Column(name="price")
    private String price;

    @Column(name="physical_active_borrowed")
    private String physical_active_borrowed;

    @Column(name="e_active_borrowed")
    private String e_active_borrowed;

    @Column(name="total_amount_borrowed")
    private String total_amount_borrowed;

    @Column(name="shelf")
    private String shelf;

    @Column(name="section")
    private String section = "";

    /*
    @Column(name="date_added")
    private Date date_added;
     */

    @NotNull
    @Column(name="isbn")
    private String isbn = "";

    @Column(name="publisher")
    private String publisher = "";

    @Column(name="is_active")
    private String is_active;

    /*
    @NotNull
    @Column(name="id")
    private String id;

    @Column(name="name")
    private String name = "";

     */


    public Books(){
    }


    public Books(String title,
                 String description,
                 String genre,
                 String author,
                 String for_ages,
                 String physical_amount,
                 String e_book,
                 String price,
                 String physical_active_borrowed,
                 String e_active_borrowed,
                 String total_amount_borrowed,
                 String shelf,
                 String section,
                 //Date date_added,
                 String isbn,
                 String publisher,
                 String is_active){
                 //String id,
                 //String name) {
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
       // this.date_added = date_added;
        this.isbn = isbn;
        this.publisher = publisher;
        this.is_active = is_active;
        //this.id = id;
        //this.name = name;
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

    public String getPhysical_amount() {
        return physical_amount;
    }

    public void setPhysical_amount(String physical_amount) {
        this.physical_amount = physical_amount;
    }

    public String getE_book() {
        return e_book;
    }

    public void setE_book(String e_book) {
        this.e_book = e_book;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhysical_active_borrowed() {
        return physical_active_borrowed;
    }

    public void setPhysical_active_borrowed(String physical_active_borrowed) {
        this.physical_active_borrowed = physical_active_borrowed;
    }

    public String getE_active_borrowed() {
        return e_active_borrowed;
    }

    public void setE_active_borrowed(String e_active_borrowed) {
        this.e_active_borrowed = e_active_borrowed;
    }

    public String getTotal_amount_borrowed() {
        return total_amount_borrowed;
    }

    public void setTotal_amount_borrowed(String total_amount_borrowed) {
        this.total_amount_borrowed = total_amount_borrowed;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    /*
    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }
     */


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

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    /*
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

     */


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
                //", date_added=" + date_added +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", is_active=" + is_active +
               // ", id=" + id +
               // ", name='" + name + '\'' +
                '}';
    }
}
