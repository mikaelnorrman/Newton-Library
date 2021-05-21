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

    TextField title = new TextField();
    private TextField description = new TextField();
    private TextField genre = new TextField(); //dropdown?
    private TextField author = new TextField();
    private TextField forAges = new TextField();
    //private TextField physicalAmount = new TextField("Physical Amount");
    //private TextField e_book = new TextField("E book");
    //private TextField price = new TextField("Price");
    //private TextField physicalActiveBorrowed = new TextField("Physical Active Borrowed");
    //private TextField eActiveBorrowed = new TextField("E Active Borrowed");
    //private TextField totalAmountBorrowed = new TextField("Total Amount Borrowed");
    //private TextField shelf = new TextField("Shelf");
    //private TextField shelf = new TextField("Shelf");
    private TextField section = new TextField();
    //private TextField dateAdded = new TextField("Date Added");
    private TextField isbn = new TextField();
    private TextField publisher = new TextField();
    //private TextField isActive = new TextField("Is Active");
    //private TextField id = new TextField("Id");
    private TextField name = new TextField();


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

        sidbarBooksEdit();

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

    private void sidbarBooksEdit() {
        title.setLabel("Book title");
        title.setPlaceholder("Enter book title");
        title.getElement().setAttribute("title", "Example: Alkemisten");
        title.setClearButtonVisible(true);
        title.setErrorMessage("Your title needs to be at least one character long");
        title.setMinLength(1);
        title.setRequired(true);
        //-----------------------------------------------------------------------
        description.setLabel("Description");
        description.setPlaceholder("Enter a description");
        description.getElement().setAttribute("Title", "Example: The Alchemist follows the journey of an Andalusian shepherd boy named Santiago.");
        description.setClearButtonVisible(true);
        description.setErrorMessage("Your description needs to be at least one character long");
        description.setMinLength(1);
        //-----------------------------------------------------------------------
        // Borde göras till en dropdown!!
        genre.setLabel("Genre");
        genre.setPlaceholder("Enter a Genre");
        genre.getElement().setAttribute("Title", "Example: Skönlittteratur or Kokböcker");
        genre.setClearButtonVisible(true);
        genre.setErrorMessage("Your genre needs to be at least one character long");
        genre.setMinLength(1);
        //-----------------------------------------------------------------------
        author.setLabel("Author");
        author.setLabel("Book Author");
        author.setPlaceholder("Enter the author");
        author.getElement().setAttribute("title", "Example: Paulo Coelho");
        author.setClearButtonVisible(true);
        author.setErrorMessage("Your book author needs to be at least one character long");
        author.setMinLength(1);
        //-----------------------------------------------------------------------
        // Borde göras till en dropdown!!
        forAges.setLabel("For Ages");
        forAges.setPlaceholder("Enter a age");
        forAges.getElement().setAttribute("Title", "Example: 0-3 or 15-99");
        forAges.setPattern("^[0-9]"); // Lägg till - bindestreck
        forAges.setErrorMessage("Numbers only. 0,1,2,3,4,5,6,7,8,9");
        forAges.setClearButtonVisible(true);
        forAges.setErrorMessage("Your book age needs to be at least one number long");
        forAges.setMinLength(1);
        //-----------------------------------------------------------------------
        // Borde göras till en dropdown!!
        section.setLabel("Section");
        section.setPlaceholder("Enter a section");
        section.getElement().setAttribute("Title", "Example: Skönlitteratur");
        section.setClearButtonVisible(true);
        section.setErrorMessage("Your book section needs to be at least one character long");
        section.setMinLength(1);
        //-----------------------------------------------------------------------
        isbn.setLabel("isbn");
        isbn.setPlaceholder("Enter a isbn");
        isbn.getElement().setAttribute("Title", "Example: ");
        forAges.setPattern("^[0-9]");
        forAges.setErrorMessage("Numbers only. 0,1,2,3,4,5,6,7,8,9");
        isbn.setClearButtonVisible(true);
        isbn.setErrorMessage("Your book isbn number needs to be ten character long");
        isbn.setMinLength(10);
        //-----------------------------------------------------------------------
        publisher.setLabel("Publisher");
        publisher.setPlaceholder("Enter a publisher");
        publisher.getElement().setAttribute("Title", "Example: HarperTorch");
        publisher.setClearButtonVisible(true);
        publisher.setErrorMessage("");
        publisher.setMinLength(1);
        //-----------------------------------------------------------------------
        name.setLabel("Name");
        name.setPlaceholder("Enter a name");
        name.getElement().setAttribute("Title", "Example: ");
        name.setClearButtonVisible(true);
        name.setErrorMessage("");
        name.setMinLength(1);
        //-----------------------------------------------------------------------
        /*
        price.setLabel("Book price");
        price.setPlaceholder("Enter a price");
        price.getElement().setAttribute("title", "Example: 150");
        price.setClearButtonVisible(true);
        price.setRequired(true);
        price.setMinLength(1);
        price.setPattern("[0-9]");
        price.setErrorMessage("Numbers only. 0,1,2,3,4,5,6,7,8,9");
        price.addValueChangeListener(event -> {
            if (price.isInvalid()) {
                Notification.show("Invalid price");
            }
        });
         */
        //------------------------------------------

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
