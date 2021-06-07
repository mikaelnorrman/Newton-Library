package com.example.application.views.admin;

import com.example.application.Connector.ConnectorMySQL;
import com.example.application.data.entity.Books;
import com.example.application.data.entity.LoanedBooks;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BookService;
import com.example.application.data.service.LoanedBooksRepository;
import com.example.application.editors.LoanedBookEditor;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.sql.SQLException;
import java.util.Optional;

@PageTitle("Books")
@CssImport("./styles/views/admin/admin-view.css")
public class BookView extends Div {

    ConnectorMySQL connectorMySQL;
    final LoanedBookEditor loanedBookEditor;
    public static final String TITLE_IN_SET_ATTRIBUTE = "Title";
    public static final String NUMBERS_ONLY = "Numbers only. 0,1,2,3,4,5,6,7,8,9";
    private Grid<Books> grid;

    private TextField title = new TextField();
    private TextField description = new TextField();
    private TextField genre = new TextField(); //dropdown?
    private TextField author = new TextField();
    private TextField forAges = new TextField();
    private TextField physicalAmount = new TextField();
    private TextField eBook = new TextField();
    private TextField price = new TextField();
    private TextField shelf = new TextField();
    private TextField section = new TextField();
    private TextField isbn = new TextField();
    private TextField publisher = new TextField();

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Books> binder;

    private Books book = new Books();
    private LoanedBooks loanedbooks = new LoanedBooks();

    private BookService bookService;

    @Autowired
    public BookView(BookService bookService, LoanedBooksRepository loanedBooksRepository) {
        this.loanedBookEditor = new LoanedBookEditor(loanedBooksRepository);

        setId("book-admin-view");
        this.bookService = bookService;
        // Configure Grid - This will show up in the Grid
        grid = new Grid<>(Books.class);
        grid.setColumns("title", "genre", "description", "author", "section", "shelf");
        //grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.getColumnByKey("title").setAutoWidth(true);
        grid.getColumnByKey("genre").setAutoWidth(true);
        grid.getColumnByKey("author").setAutoWidth(true);
        grid.getColumnByKey("section").setAutoWidth(true);
        grid.getColumnByKey("shelf").setAutoWidth(true);
        grid.getColumnByKey("description").setWidth("150px").setFlexGrow(0);
        grid.addComponentColumn(Book -> createLoanButton(grid, Book));
        grid.setDataProvider(new CrudServiceDataProvider<Books, Void>(bookService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Books> booksFromBackend = bookService.get(event.getValue().getId());
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
                genre, author, forAges, section, isbn, publisher};
        for (AbstractField field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void sidbarBooksEdit() {
        titleSidebarEditor();
        descriptionSidebarEditor();
        genreSidebarEditor();
        authorSidebarEditor();
        forAgeSidebarEditor();
        physicalAmountSidebarEditor();
        ebbokSidebarEditor();
        shelfSidebarEditor();
        sectionSidebarEditor();
        isbnSidebarEditor();
        publisherSidebarEditor();
        priceSidebarEditor();

    }

    private void titleSidebarEditor() {
        title.setLabel("Book title");
        title.setPlaceholder("Enter book title");
        title.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Alkemisten");
        title.setClearButtonVisible(true);
        title.setErrorMessage("Your title needs to be at least one character long");
        title.setMinLength(1);
        title.setRequired(true);
        title.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void descriptionSidebarEditor() {
        description.setLabel("Description");
        description.setPlaceholder("Enter a description");
        description.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: The Alchemist follows the journey of an Andalusian shepherd boy named Santiago.");
        description.setClearButtonVisible(true);
        description.setErrorMessage("Your description needs to be at least one character long");
        description.setMinLength(1);
        description.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void genreSidebarEditor() {
        // Borde göras till en dropdown!!
        genre.setLabel("Genre");
        genre.setPlaceholder("Enter a Genre");
        genre.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Skönlittteratur or Kokböcker");
        genre.setClearButtonVisible(true);
        genre.setErrorMessage("Your genre needs to be at least one character long");
        genre.setMinLength(1);
        genre.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void authorSidebarEditor() {
        author.setLabel("Author");
        author.setLabel("Book Author");
        author.setPlaceholder("Enter the author");
        author.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Paulo Coelho");
        author.setClearButtonVisible(true);
        author.setErrorMessage("Your book author needs to be at least one character long");
        author.setMinLength(1);
        author.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void forAgeSidebarEditor() {
        // Borde göras till en dropdown!!
        forAges.setLabel("For Ages");
        forAges.setPlaceholder("Enter a age");
        forAges.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 0-3 or 15-99");
        forAges.setPattern("\\d{1,2}\\-\\d{1,3}");
        forAges.setErrorMessage(NUMBERS_ONLY);
        forAges.setClearButtonVisible(true);
        forAges.setErrorMessage("Your book age needs to be at least one number long");
        forAges.setMinLength(1);
        forAges.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void physicalAmountSidebarEditor() {
        physicalAmount.setLabel("Physical Amount");
        physicalAmount.setPlaceholder("Enter a amount");
        physicalAmount.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void ebbokSidebarEditor() {
        eBook.setLabel("E book");
        eBook.setPlaceholder("Enter eBook");
        eBook.setClearButtonVisible(true);
        eBook.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void priceSidebarEditor() {
        price.setLabel("Book price");
        price.setPlaceholder("Enter a price");
        price.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 150");
        price.setClearButtonVisible(true);
        price.setRequired(true);
        price.setMinLength(1);
        price.setPattern("[0-9]");
        price.setErrorMessage(NUMBERS_ONLY);
        /* Kanske går att använda vid en validering.
        price.addValueChangeListener(event -> {
            if (price.isInvalid()) {
                Notification.show("Invalid price");
            }
        });

         */
        price.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void shelfSidebarEditor() {
        shelf.setLabel("Shelf");
        shelf.setClearButtonVisible(true);
        shelf.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void sectionSidebarEditor() {
        // Borde göras till en dropdown!!
        section.setLabel("Section");
        section.setPlaceholder("Enter a section");
        section.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Skönlitteratur");
        section.setClearButtonVisible(true);
        section.setErrorMessage("Your book section needs to be at least one character long");
        section.setMinLength(1);
        section.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void isbnSidebarEditor() {
        isbn.setLabel("ISBN");
        isbn.setPlaceholder("Enter a isbn number");
        isbn.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: ");
        isbn.setPattern("^[0-9]");
        isbn.setErrorMessage(NUMBERS_ONLY);
        isbn.setClearButtonVisible(true);
        isbn.setErrorMessage("Your book isbn number needs to be ten character long");
        isbn.setMinLength(10);
        isbn.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void publisherSidebarEditor() {
        publisher.setLabel("Publisher");
        publisher.setPlaceholder("Enter a publisher");
        publisher.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: HarperTorch");
        publisher.setClearButtonVisible(true);
        publisher.setErrorMessage("");
        publisher.setMinLength(1);
        publisher.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }


// ----------------------------------------------------------------------------------------------------
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

    private Button createLoanButton(Grid<Books> grid, Books item) {
        boolean checkLoancard = VaadinSession.getCurrent().getAttribute(Person.class).getLoancard() == true; // koll så att användaren har ett lånekort.

        Button loanedButton = new Button("Loan book",  editor  -> {
            Integer idPersons = VaadinSession.getCurrent().getAttribute(Person.class).getIdPersons();       // hämta ut inloggade personens id.
            String firstNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getFirstName(); // hämta ut inloggade personens fistName.
            String lastNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getLastName();   // hämta ut inloggade personens lastName.

            if (checkLoancard) {
                try {
                    if (!connectorMySQL.callcheck_loan(idPersons,item.getId())) {

                        Integer idOfBooks = item.getId();
                        item.getTitle();
                        item.getId();


                        loanedBookEditor.saveLoaned(new LoanedBooks(idOfBooks, idPersons, 0));
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else {
                    Notification loanedNotificationFail = new Notification(firstNamePersons + " " + lastNamePersons +
                            "\nYou cant loan the book " + item.getTitle() + "\nYou need to get a loaned card");
                    loanedNotificationFail.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    loanedNotificationFail.setDuration(5000);
                    loanedNotificationFail.setPosition(Notification.Position.MIDDLE);
                    loanedNotificationFail.open();
                    return;
                }

            Notification loanedNotification = new Notification("You loaned the book \n" + item.getTitle());
            loanedNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            loanedNotification.setDuration(5000);
            loanedNotification.setPosition(Notification.Position.MIDDLE);
            loanedNotification.open();
        });

        loanedButton.setDisableOnClick(true);
        loanedButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
        return loanedButton;
    }



}
