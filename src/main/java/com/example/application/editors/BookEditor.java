package com.example.application.editors;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;


public class BookEditor extends Editor {

    public static final String TITLE_IN_SET_ATTRIBUTE = "title";
    public static final String NUMBERS_ONLY = "Numbers only. 0,1,2,3,4,5,6,7,8,9";
    TextField title;
    TextField description;
    TextField genre;
    TextField author;
    TextField forAges;
    TextField physicalAmount;
    TextField price;
    TextField shelf;
    TextField section;
    TextField isbn;
    TextField publisher;

    @Autowired
    public BookEditor (BooksRepository booksRepo){
        this.booksRepository = booksRepo;
        this.booksBinder = new Binder<>(Books.class);

// --------------------------------------------------------------------------------------------------------
        HorizontalLayout bookEdit = new HorizontalLayout();
        FormLayout bookFormLayout = new FormLayout();
        // Setting the desired responsive steps for the columns in the layout
        bookFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        //initierar textfields för inmatning
        titleEdit();
        descriptionEdit();
        genreEdit();
        authorEdit();
        forAgeEdit();
        amountOfBooks();
        priceEdit();
        shelfEdit();
        sectionEdit();
        isbnEdit();
        publisherEdit();

        //Lägg till knappar
        bookFormLayout.add(title,description,genre,author,forAges,
                physicalAmount,price,shelf,section,isbn,publisher);

        //Lägg till knappar
        bookEdit.add(actions);

        add(bookFormLayout, bookEdit);
// --------------------------------------------------------------------------------------------------------

        booksBinder.bindInstanceFields(this);
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        //Listener för save med bind ENTER
        addKeyPressListener(Key.ENTER, e -> saveCatcher());

        //Knyt actions knapparna
        save.addClickListener(e ->saveCatcher());
        delete.addClickListener(e->deleteBook(books));
        cancel.addClickListener(e-> editBook(books));
        setVisible(false);

    }

    private void titleEdit() {
        title = new TextField();
        title.setLabel("Book title");
        title.setPlaceholder("Enter book title");
        title.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Alkemisten");
        title.setClearButtonVisible(true);
        title.setErrorMessage("Your title needs to be at least one character long");
        title.setMinLength(1);
        title.setRequired(true);
        title.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void descriptionEdit() {
        description  = new TextField();
        description.setLabel("Description");
        description.setPlaceholder("Enter a description");
        description.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: The Alchemist follows the journey of an Andalusian shepherd boy named Santiago.");
        description.setClearButtonVisible(true);
        description.setErrorMessage("Your description needs to be at least one character long");
        description.setMinLength(1);
        description.setMaxLength(1000);
        description.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void genreEdit() {
        // Borde göras till en dropdown!!
        genre  = new TextField();
        genre.setLabel("Genre");
        genre.setPlaceholder("Enter a Genre");
        genre.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Roman");
        genre.setClearButtonVisible(true);
        genre.setErrorMessage("Your genre needs to be at least one character long");
        genre.setMinLength(1);
        genre.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void authorEdit() {
        author = new TextField();
        author.setLabel("Book Author");
        author.setPlaceholder("Enter the author");
        author.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Paulo Coelho");
        author.setClearButtonVisible(true);
        author.setErrorMessage("Your book author needs to be at least one character long");
        author.setMinLength(1);
        author.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void forAgeEdit() {
        // Borde göras till en dropdown!!
        forAges = new TextField();
        forAges.setLabel("For Ages");
        forAges.setPlaceholder("Enter a age");
        forAges.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 0-3 or 15-99");
        forAges.setPattern("(\\d{1,2}\\-\\d{1,3})");
        forAges.setClearButtonVisible(true);
        forAges.setErrorMessage(NUMBERS_ONLY + "\nThe book age you enter needs to be an age range between two ages\nExample: 0-3, 3-6 or 15-99 etc" );
        forAges.setMinLength(1);
        forAges.setMaxLength(12);
        forAges.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void isbnEdit() {
        isbn = new TextField();
        isbn.setLabel("ISBN");
        isbn.setPlaceholder("Enter a isbn number");
        isbn.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: ISBN står för International Standard Book Number och är ett 13‑siffrigt identifikationsnummer för böcker.");
        isbn.setPattern("[0-9]+");
        isbn.setErrorMessage(NUMBERS_ONLY);
        isbn.setClearButtonVisible(true);
        isbn.setErrorMessage("Your book isbn number needs to be ten character long");
        isbn.setMinLength(10);
        isbn.setMaxLength(12);
        isbn.setRequired(true);
        isbn.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void amountOfBooks() {
        physicalAmount = new TextField();
        physicalAmount.setLabel("Physical Amount");
        physicalAmount.setPlaceholder("Enter a amount");
        physicalAmount.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 15");
        physicalAmount.setClearButtonVisible(true);
        physicalAmount.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void priceEdit() {
        price = new TextField();
        price.setLabel("Book price");
        price.setPlaceholder("Enter a price");
        price.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 150");
        price.setClearButtonVisible(true);
        price.setMinLength(1);
        price.setPattern("[0-9]");
        price.setErrorMessage(NUMBERS_ONLY);
        price.setRequired(true);
        price.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void shelfEdit() {
        shelf = new TextField();
        shelf.setLabel("Shelf");
        shelf.setPlaceholder("Enter a shelf");
        shelf.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 5");
        shelf.setClearButtonVisible(true);
        shelf.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void sectionEdit() {
        // Borde göras till en dropdown!!
        section = new TextField();
        section.setLabel("Section");
        section.setPlaceholder("Enter a section");
        section.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Skönlitteratur");
        section.setClearButtonVisible(true);
        section.setErrorMessage("Your book section needs to be at least one character long");
        section.setMinLength(1);
        section.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void publisherEdit() {
        publisher = new TextField();
        publisher.setLabel("Publisher");
        publisher.setPlaceholder("Enter a publisher");
        publisher.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: HarperTorch");
        publisher.setClearButtonVisible(true);
        publisher.setErrorMessage("");
        publisher.setMinLength(1);
        publisher.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }



    void saveCatcher(){
        try{
            booksBinder.writeBean(books);
            saveBook(books);
            saveNotification();
        } catch (ValidationException throwables){
            throwables.printStackTrace();
        }
    }

    private void saveNotification() {
        Notification savePersonNotification = new Notification("You saved your book");
        savePersonNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        savePersonNotification.setDuration(4000);
        savePersonNotification.setPosition(Notification.Position.MIDDLE);
        savePersonNotification.open();
    }

    void deleteBook(Books books){
   booksRepository.delete(books);
    changeHandler.onChange();
    }

    void saveBook(Books books) {
        booksRepository.save(books);
        changeHandler.onChange();
    }

    public final void editBook(Books book){
        if (book == null){
            setVisible(false);
            return;
        }
        final boolean persisted = book.getId() != null;
        if (persisted){
            books = booksRepository.findById(book.getId()).get();
        } else {
            books = book;
        }
        cancel.setVisible(persisted);
        booksBinder.setBean(books);
        setVisible(true);
        title.focus();
    }
}
