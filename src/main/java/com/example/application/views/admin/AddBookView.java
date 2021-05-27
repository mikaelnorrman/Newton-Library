package com.example.application.views.admin;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;
import com.example.application.editors.BookEditor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@PageTitle("Add BookView")
@CssImport("./styles/views/admin/admin-view.css")
public class AddBookView extends VerticalLayout {

    private final BooksRepository repository;
    final BookEditor editor;
    final Grid<Books> grid;
    final TextField filterTitle;
    final TextField filterGenre;
    final TextField filterAuthor;
    final TextField filterPublisher;
    final TextField filterIbis;
    private final Button addBook;
    private final Button back;


    @Autowired
    public AddBookView(BooksRepository repository){
        this.repository = repository;
        this.grid = new Grid<>(Books.class);
        this.editor = new BookEditor(repository);

        this.filterTitle = new TextField();
        this.filterTitle.setPlaceholder("Filter by title");
        this.filterTitle.setClearButtonVisible(true);
        this.filterTitle.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterTitle.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.filterGenre = new TextField();
        this.filterGenre.setPlaceholder("Filter by genre");
        this.filterGenre.setClearButtonVisible(true);
        this.filterGenre.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterGenre.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.filterAuthor = new TextField();
        this.filterAuthor.setPlaceholder("Filter by author");
        this.filterAuthor.setClearButtonVisible(true);
        this.filterAuthor.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterAuthor.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.filterPublisher = new TextField();
        this.filterPublisher.setPlaceholder("Filter by publisher");
        this.filterPublisher.setClearButtonVisible(true);
        this.filterPublisher.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterPublisher.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.filterIbis = new TextField();
        this.filterIbis.setPlaceholder("Filter by ibis");
        this.filterIbis.setClearButtonVisible(true);
        this.filterIbis.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterIbis.setPrefixComponent(VaadinIcon.SEARCH.create());


        this.addBook = new Button("New book", VaadinIcon.PLUS.create());
        this.back = new Button("Back", VaadinIcon.HOME.create());


        //Build layout
        var actions = new HorizontalLayout(filterTitle, filterGenre, filterAuthor, filterPublisher, addBook, back);
        add(actions, grid, editor);
        actions.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        grid.setHeight("400px");
        grid.setColumns("id", "title", "description", "genre", "author", "ages", "physical_amount",
                "e_book", "price", "physical_active_borrowed", "e_active_borrowed", "total_amount_borrowed",
                "shelf", "section", "isbn", "publisher", "is_active");
        grid.getColumnByKey("title").setAutoWidth(true);
        grid.getColumnByKey("genre").setAutoWidth(true);
        grid.getColumnByKey("author").setAutoWidth(true);
        grid.getColumnByKey("ages").setAutoWidth(true);
        grid.getColumnByKey("physical_amount").setAutoWidth(true);
        grid.getColumnByKey("physical_active_borrowed").setAutoWidth(true);
        grid.getColumnByKey("e_active_borrowed").setAutoWidth(true);
        grid.getColumnByKey("total_amount_borrowed").setAutoWidth(true);
        grid.getColumnByKey("shelf").setAutoWidth(true);
        grid.getColumnByKey("section").setAutoWidth(true);
        grid.getColumnByKey("isbn").setAutoWidth(true);
        grid.getColumnByKey("publisher").setAutoWidth(true);
        grid.getColumnByKey("is_active").setAutoWidth(true);
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);
        grid.getColumnByKey("description").setWidth("150px").setFlexGrow(0);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        //Hook logic
        //Replace listing with filter
        filterTitle.setValueChangeMode(ValueChangeMode.EAGER);
        filterTitle.addValueChangeListener(e -> listBook(e.getValue(),1));
        filterGenre.setValueChangeMode(ValueChangeMode.EAGER);
        filterGenre.addValueChangeListener(e -> listBook(e.getValue(),2));
        filterAuthor.setValueChangeMode(ValueChangeMode.EAGER);
        filterAuthor.addValueChangeListener(e -> listBook(e.getValue(),3));
        filterPublisher.setValueChangeMode(ValueChangeMode.EAGER);
        filterPublisher.addValueChangeListener(e -> listBook(e.getValue(),4));
        filterIbis.setValueChangeMode(ValueChangeMode.EAGER);
        filterIbis.addValueChangeListener(e -> listBook(e.getValue(),5));

        //Connect selected staff to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e ->  editor.editBook(e.getValue()));


        //TODO Ta bort id och name..
        //instantiate end edit new staff
        addBook.addClickListener (e -> editor.
                editBook(new Books("","","","","",
                        "","","", "","",
                        "","","","","",
                        "")));



        //back button | .navigate("") -> Bestämmer till vilken vy man skall gå till.
        back.addClickListener(e -> UI.getCurrent().navigate("opening"));

        //Listen changes made by the editor, refresh data
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listBook(filterTitle.getValue(),1);
            listBook(filterGenre.getValue(),2);
            listBook(filterAuthor.getValue(),3);
            listBook(filterPublisher.getValue(),4);
            listBook(filterIbis.getValue(),5);
        });

        //Initialize listing
        listBook(null,5);
    }

    void listBook(String filterText, int choice) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repository.findAll());
        } else {
            if (choice == 1) {
                grid.setItems(repository.findByTitleStartsWithIgnoreCase(filterText));
            } else if (choice == 2) {
                grid.setItems(repository.findByGenreStartsWithIgnoreCase(filterText));
            } else if (choice == 3) {
                grid.setItems(repository.findByAuthorStartsWithIgnoreCase(filterText));
            } else if (choice == 4) {
                grid.setItems(repository.findByPublisherStartsWithIgnoreCase(filterText));
            } else if (choice == 5) {
                grid.setItems(repository.findByIsbnStartsWithIgnoreCase(filterText));
            }
        }
    }
}