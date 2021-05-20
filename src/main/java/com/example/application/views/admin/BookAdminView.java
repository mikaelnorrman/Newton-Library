package com.example.application.views.admin;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BookService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.Div;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@PageTitle("Book Admin View")
@CssImport("./styles/views/admin/admin-view.css")
public class BookAdminView extends Div {

    private Grid<Books> grid;

    private TextField title = new TextField("Title");
    private TextField description = new TextField("Description");
    private TextField genre = new TextField("Genre"); //dropdown?
    private TextField author = new TextField("Author");
    private TextField forAges = new TextField("For Ages");
    //private TextField physicalAmount = new TextField("Physical Amount");
    //private TextField e_book = new TextField("E book");
    //private TextField price = new TextField("Price");
    //private TextField physicalActiveBorrowed = new TextField("Physical Active Borrowed");
    //private TextField eActiveBorrowed = new TextField("E Active Borrowed");
    //private TextField totalAmountBorrowed = new TextField("Total Amount Borrowed");
    //private TextField shelf = new TextField("Shelf");
    //private TextField shelf = new TextField("Shelf");
    private TextField section = new TextField("Section");
    //private TextField dateAdded = new TextField("Date Added");
    private TextField isbn = new TextField("isbn");
    private TextField publisher = new TextField("Publisher");
    //private TextField isActive = new TextField("Is Active");
    //private TextField id = new TextField("Id");
    private TextField name = new TextField("Name");


    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Books> binder;

    private Books book = new Books();

    private BookService bookService;

    public BookAdminView(@Autowired BookService bookService){
        setId("book-admin-view");
        this.bookService = bookService;
        // Configure Grid - This will show up in the Grid
        grid = new Grid<>(Books.class);
        grid.setColumns("title", "description", "genre", "author", "for_ages", "physical_amount",
                "e_book", "price", "physical_active_borrowed", "e_active_borrowed", "total_amount_borrowed",
                "shelf", "section", "date_added", "isbn", "publisher", "is_active", "id", "name");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.setDataProvider(new CrudServiceDataProvider<Books, Void>(bookService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Books> booksFromBackend = bookService.get(event.getValue().getId_books());
                // when a row is selected but the data is no longer available, refresh grid
                if (booksFromBackend.isPresent()) {
                    populateForm(booksFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new Binder<>(Books.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.book == null) {
                    this.book = new Books();
                }
                binder.writeBean(this.book);
                bookService.update(this.book);
                clearForm();
                refreshGrid();
                Notification.show("Book details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the book details.");
            }
        });

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        AbstractField[] fields = new AbstractField[] {title, description,
                genre, author, forAges, section, isbn, publisher, name};
        for (AbstractField field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Books value) {
        this.book = value;
        binder.readBean(this.book);


    }
}
