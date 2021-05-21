package com.example.application.editors;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;


public class BookEditor extends Editor {

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

        //initierar textfields för inmatning
        title = new TextField("Book title");
        description = new TextField("Description");
        genre = new TextField("Book genre");
        author = new TextField("Author");
        forAges = new TextField("For ages");
        physicalAmount = new TextField("Amount of books");
        price = new TextField("Price");
        shelf = new TextField("Shelf");
        section = new TextField("Book section");
        isbn = new TextField("ISBN #");
        publisher = new TextField("Publisher");

        //Lägg till knappar
        add(title,description,genre,author,forAges,physicalAmount,price,shelf,
                section,isbn,publisher,actions);

        booksBinder.bindInstanceFields(this);
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        //Listener för save med bind ENTER
        addKeyPressListener(Key.ENTER, e -> saveCatcher());

        //Knyt actions knapparna
        save.addClickListener(e->saveCatcher());
        delete.addClickListener(e->deleteBook(books));
        cancel.addClickListener(e-> editBook(books));
        setVisible(false);

    }
    void saveCatcher(){
        try{
            booksBinder.writeBean(books);
            saveBook(books);
        } catch (ValidationException throwables){
            throwables.printStackTrace();
        }
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
        final boolean persisted = book.getId_books() != null;
        if (persisted){
            books = booksRepository.findById(book.getId_books()).get();
        } else {
            books = book;
        }
        cancel.setVisible(persisted);
        booksBinder.setBean(books);
        setVisible(true);
        title.focus();
    }
}
