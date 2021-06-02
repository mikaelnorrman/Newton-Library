package com.example.application.views.search;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.VaadinIcon;
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
        this.title = new TextField("Title");
        this.genre = new TextField("Genre");
        this.author = new TextField("Author");
        this.publisher = new TextField("Publisher");
        this.back = new Button("Back", VaadinIcon.HOME.create());

        //bygg layout
        HorizontalLayout actions = new HorizontalLayout(new H4("Filter books by..."), title,genre,author,publisher,back);
        add(actions, grid);
        actions.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);


        grid.setHeight("400px");
        grid.setColumns("id", "title", "description","genre", "author", "ages",
                "physicalAmount", "eBook", "price", "physicalActiveBorrowed", "eActiveBorrowed",
                "totalAmountBorrowed", "shelf", "section", "isbn", "publisher", "isActive");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.getColumnByKey("title").setAutoWidth(true);
        grid.getColumnByKey("genre").setAutoWidth(true);
        grid.getColumnByKey("author").setAutoWidth(true);
        grid.getColumnByKey("ages").setAutoWidth(true);
        grid.getColumnByKey("physicalAmount").setAutoWidth(true);
        grid.getColumnByKey("physicalActiveBorrowed").setAutoWidth(true);
        grid.getColumnByKey("eActiveBorrowed").setAutoWidth(true);
        grid.getColumnByKey("totalAmountBorrowed").setAutoWidth(true);
        grid.getColumnByKey("shelf").setAutoWidth(true);
        grid.getColumnByKey("section").setAutoWidth(true);
        grid.getColumnByKey("isbn").setAutoWidth(true);
        grid.getColumnByKey("publisher").setAutoWidth(true);
        grid.getColumnByKey("isActive").setAutoWidth(true);
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.getColumnByKey("description").setWidth("150px").setFlexGrow(0);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);


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
