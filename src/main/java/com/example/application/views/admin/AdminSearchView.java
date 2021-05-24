package com.example.application.views.admin;


import com.example.application.data.service.BooksRepository;
import com.example.application.views.search.SearchView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Admin Search")
public class AdminSearchView extends SearchView {
    final com.example.application.editors.BookEditor editor;
    private final Button addMovie;

    @Autowired
    public AdminSearchView(BooksRepository repository){
        super(repository);
        this.editor = new com.example.application.editors.BookEditor(repository);
        this.addMovie = new Button("New Book", VaadinIcon.BOOK.create());
    }
}
