package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "books", schema = "agile_library")
@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_books")
    private Integer id    ;

    @Column(name="title")
    private String title = "";

    @Column(name="description")
    private String description = "";

    @Column(name="genre")
    private String genre = "";

    @Column(name="author")
    private String author = "";

    @Column(name="for_ages")
    private String ages = "";

    @Column(name="physical_amount")
    private String physicalAmount;

    @Column(name="e_book")
    private String eBook;

    @NotNull
    @Column(name="price")
    private String price;

    @Column(name="physical_active_borrowed")
    private String physicalActiveBorrowed;

    @Column(name="e_active_borrowed")
    private String eActiveBorrowed;

    @Column(name="total_amount_borrowed")
    private String totalAmountBorrowed;

    @Column(name="shelf")
    private String shelf;

    @Column(name="section")
    private String section = "";

    @NotNull
    @Column(name="isbn")
    private String isbn = "";

    @Column(name="publisher")
    private String publisher = "";

    @Column(name="is_active")
    private String isActive;


    public Books(){
    }


    public Books(String title,
                 String description,
                 String genre,
                 String author,
                 String for_ages,
                 String physicalAmount,
                 String eBook,
                 String price,
                 String physicalActiveBorrowed,
                 String eActiveBorrowed,
                 String totalAmountBorrowed,
                 String shelf,
                 String section,
                 String isbn,
                 String publisher,
                 String isActive) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.author = author;
        this.ages = for_ages;
        this.physicalAmount = physicalAmount;
        this.eBook = eBook;
        this.price = price;
        this.physicalActiveBorrowed = physicalActiveBorrowed;
        this.eActiveBorrowed = eActiveBorrowed;
        this.totalAmountBorrowed = totalAmountBorrowed;
        this.shelf = shelf;
        this.section = section;
        this.isbn = isbn;
        this.publisher = publisher;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
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

    public String getAges() {
        return ages;
    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    public String getPhysicalAmount() {
        return physicalAmount;
    }

    public void setPhysicalAmount(String physicalAmount) {
        this.physicalAmount = physicalAmount;
    }

    public String geteBook() {
        return eBook;
    }

    public void seteBook(String eBook) {
        this.eBook = eBook;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhysicalActiveBorrowed() {
        return physicalActiveBorrowed;
    }

    public void setPhysicalActiveBorrowed(String physicalActiveBorrowed) {
        this.physicalActiveBorrowed = physicalActiveBorrowed;
    }

    public String geteActiveBorrowed() {
        return eActiveBorrowed;
    }

    public void seteActiveBorrowed(String eActiveBorrowed) {
        this.eActiveBorrowed = eActiveBorrowed;
    }

    public String getTotalAmountBorrowed() {
        return totalAmountBorrowed;
    }

    public void setTotalAmountBorrowed(String totalAmountBorrowed) {
        this.totalAmountBorrowed = totalAmountBorrowed;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getPhysicalAvailableBooks() {
        return "" + (Integer.parseInt(physicalAmount) - Integer.parseInt(physicalActiveBorrowed));
    }


    @Override
    public String toString() {
        return "Books{" +
                "id_books=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", author='" + author + '\'' +
                ", for_ages='" + ages + '\'' +
                ", physical_amount=" + physicalAmount +
                ", e_book=" + eBook +
                ", price=" + price +
                ", physical_active_borrowed=" + physicalActiveBorrowed +
                ", e_active_borrowed=" + eActiveBorrowed +
                ", total_amount_borrowed=" + totalAmountBorrowed +
                ", shelf=" + shelf +
                ", section='" + section + '\'' +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", is_active=" + isActive +
                '}';
    }
}
