package com.example.application.views.search;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;
import com.example.application.views.main.MainViewLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

//@Route(value = "search") // -> Kan användas för att skapa en öppen view
@PageTitle("Search")
@CssImport(value = "./styles/views/search/search-view.css")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class SearchView extends Div {

    private final BooksRepository repository;
    final Grid<Books> grid;
    final TextField title, genre, author, publisher;
    private final Button back;

    @Autowired
    public SearchView (BooksRepository repository){
        this.repository = repository;
        this.grid = new Grid<>(Books.class);
        this.title = new TextField();
        this.genre = new TextField();
        this.author = new TextField();
        this.publisher = new TextField();
        this.back = new Button("Back", VaadinIcon.HOME.create());

        //bygg layout
        HorizontalLayout actions = new HorizontalLayout(title,genre,author,publisher,back);
        add(actions, grid);
        actions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);


        grid.setHeight("400px");
        grid.setColumns("id_books", "bookName", "description","genre", "author", "for_ages",
                "physical_amount", "e_book", "price", "physical_active_borrowed", "e_active_borrowed",
                "total_amount_borrowed", "shelf", "section", "date_added", "isbn", "publisher", "is_active",
                "id", "name");
        grid.getColumnByKey("Id_books").setWidth("50px").setFlexGrow(0);

        //Hook logic
        title.setValueChangeMode(ValueChangeMode.EAGER);
        title.addValueChangeListener(e -> listBooks(e.getValue(), 1));
        genre.setValueChangeMode(ValueChangeMode.EAGER);
        genre.addValueChangeListener(e -> listBooks(e.getValue(), 2));
        author.setValueChangeMode(ValueChangeMode.EAGER);
        author.addValueChangeListener(e-> listBooks(e.getValue(), 3));
        publisher.setValueChangeMode(ValueChangeMode.EAGER);
        publisher.addValueChangeListener(e->listBooks(e.getValue(), 4));

        back.addClickListener(e -> UI.getCurrent().navigate(""));

        listBooks(null,1);
    }

    void listBooks(String filterText, int choice){
        if (StringUtils.isEmpty(filterText)){
            grid.setItems(repository.findAll());
        }else {
            switch (choice) {
                case 1 -> grid.setItems(repository.findByTitleStartsWithIgnoreCase(filterText));
                case 2 -> grid.setItems(repository.findByGenreStartsWithIgnoreCase(filterText));
                case 3 -> grid.setItems(repository.findByAuthorStartsWithIgnoreCase(filterText));
                case 4 -> grid.setItems(repository.findByPublisherStartsWithIgnoreCase(filterText));
            }
        }

    }
}
