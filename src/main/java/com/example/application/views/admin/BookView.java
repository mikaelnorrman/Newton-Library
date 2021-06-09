package com.example.application.views.admin;

import com.example.application.Connector.ConnectorMySQL;
import com.example.application.data.entity.Books;
import com.example.application.data.entity.LoanedBooks;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BookService;
import com.example.application.data.service.BooksRepository;
import com.example.application.data.service.LoanedBooksRepository;
import com.example.application.editors.LoanedBookEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.sql.SQLException;

@PageTitle("Books")
@CssImport("./styles/views/admin/admin-view.css")
public class BookView extends Div {

    public static final int DURATION_NOTIFICATION = 4500;
    ConnectorMySQL connectorMySQL = new ConnectorMySQL();
    final LoanedBookEditor loanedBookEditor;
    public static final String TITLE_IN_SET_ATTRIBUTE = "Title";
    public static final String NUMBERS_ONLY = "Numbers only. 0,1,2,3,4,5,6,7,8,9";
    private Grid<Books> grid;

    private LoanedBooks loanedbooks = new LoanedBooks();

    private BookService bookService;

    @Autowired
    public BookView(BookService bookService, LoanedBooksRepository loanedBooksRepository, BooksRepository booksRepository) {
        this.loanedBookEditor = new LoanedBookEditor(loanedBooksRepository);
        setSizeFull();
        setId("book-admin-view");
        this.bookService = bookService;
        // Configure Grid - This will show up in the Grid
        grid = new Grid<>(Books.class);

        add(grid);
        grid.setColumns("title", "genre", "author", "section", "shelf");
        //grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.getColumnByKey("title").setAutoWidth(true);
        grid.getColumnByKey("genre").setAutoWidth(true);
        grid.getColumnByKey("author").setAutoWidth(true);
        grid.getColumnByKey("section").setAutoWidth(true);
        grid.getColumnByKey("shelf").setAutoWidth(true);
        //grid.getColumnByKey("description").setWidth("150px").setFlexGrow(0);
        grid.addComponentColumn(Book -> createLoanButton(grid, Book));
        grid.setDataProvider(new CrudServiceDataProvider<Books, Void>(bookService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();
        grid.setVisible(true);
        itemDetails();
    }


// ----------------------------------------------------------------------------------------------------

    private Button createLoanButton(Grid<Books> grid, Books item) {
        boolean checkLoancard = VaadinSession.getCurrent().getAttribute(Person.class).getLoancard() == true; // koll så att användaren har ett lånekort.
        //boolean checkExpired = VaadinSession.getCurrent().getAttribute(LoanedBooks.class).getExpired();
        Integer idPersons = VaadinSession.getCurrent().getAttribute(Person.class).getIdPersons();       // hämta ut inloggade personens id.
        String firstNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getFirstName(); // hämta ut inloggade personens fistName.
        String lastNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getLastName();   // hämta ut inloggade personens lastName.



        Button loanButton = new Button("Loan book",  editor  -> {
            Integer idOfBooks = item.getId();
            String titleOfBook = item.getTitle();
            item.getTitle();
            item.getId();
            loanedBookEditor.saveLoaned(new LoanedBooks(idOfBooks, idPersons,titleOfBook, false));
            successLoanedBookNotification(item);
        });

        Button loanedButton = new Button ("Book loaned", editor -> {
            errorLoanedBookNotification(item);
        });

        Button noCardButton = new Button ("Loan Book", editor -> {
            loanedCardNotification(item, firstNamePersons, lastNamePersons);
        });

        Button expiredButton = new Button ("BOOK EXPIRED!", editor -> {
            errorLoanedBookExpiredNotification(item);
        });


        if (checkLoancard) {
            try {
                if (!connectorMySQL.callcheck_loan(idPersons,item.getId()))
                {
                    loanButton.setDisableOnClick(true);
                    loanButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
                    return loanButton;
                } else {
                    loanedButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);
                    return loanedButton;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }/* else if (checkExpired){
            expiredButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            return expiredButton;
        } */
         else {
            loanButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_SMALL);
        }
        return noCardButton;
    }

    private void loanedCardNotification(Books item, String firstNamePersons, String lastNamePersons) {
        Notification loanedCardNotificationFail = new Notification(firstNamePersons + " " + lastNamePersons +
                "\nYou cant loan the book " + item.getTitle() + "\nYou need to get a loan card");
        loanedCardNotificationFail.addThemeVariants(NotificationVariant.LUMO_ERROR);
        loanedCardNotificationFail.setDuration(DURATION_NOTIFICATION);
        loanedCardNotificationFail.setPosition(Notification.Position.MIDDLE);
        loanedCardNotificationFail.open();
    }

    private void errorLoanedBookNotification(Books item) {
        Notification errorLoanedBookNotification = new Notification("You already loaned the book \n" + item.getTitle());
        errorLoanedBookNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        errorLoanedBookNotification.setDuration(DURATION_NOTIFICATION);
        errorLoanedBookNotification.setPosition(Notification.Position.MIDDLE);
        errorLoanedBookNotification.open();
    }

    private void errorLoanedBookExpiredNotification(Books item) {
        Notification errorLoanedBookExpiredNotification = new Notification("This book has expired \n" + item.getTitle() + "! \n Please return this imediately or we'll have to charge you. ");
        errorLoanedBookExpiredNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        errorLoanedBookExpiredNotification.setDuration(DURATION_NOTIFICATION);
        errorLoanedBookExpiredNotification.setPosition(Notification.Position.MIDDLE);
        errorLoanedBookExpiredNotification.open();
    }

    private void successLoanedBookNotification (Books item) {
        Notification successLoanedBookNotification = new Notification("You loaned the book \n" + item.getTitle());
        successLoanedBookNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        successLoanedBookNotification.setDuration(DURATION_NOTIFICATION);
        successLoanedBookNotification.setPosition(Notification.Position.MIDDLE);
        successLoanedBookNotification.open();
    }
    private void itemDetails() {
        grid.setItemDetailsRenderer(TemplateRenderer.<Books>of(
                "<div class='custom-details' style='border: 2px solid #1676f3; border-radius: 5px;"
                        + " padding: 10px 15px; width: 100%; box-sizing: border-box;'>"
                        + "<div>"
                        + "<H3 style='margin: 0 0 0.25em;'>[[item.title]]</H3>"
                        + "<H4 style='margin: 0 0 0.75em; font-style: italic; font-weight: 400;'>[[item.author]]</H4>"
                        + "<p style='margin: 0 0 0.75em;'>[[item.description]]</p>"
                        + "<div style='display: flex; flex-flow: row wrap; /*justify-content: space-between;*/'>"
                        + "<span style='margin-right: 1.75em; min-width: 150px;'>Publisher: <b>[[item.publisher]]</b></span>"
                        + "<span style='margin-right: 1.75em; min-width: 150px;'>ISBN: <b>[[item.isbn]]</b></span>"
                        + "<span style='margin-right: 1.75em; min-width: 150px;'>Books available: <b>[[item.available]]</b></span>"
                        + "</div>"
                        + "</div>"
                        + "</div>")
                .withProperty("title", Books::getTitle)
                .withProperty("author", Books::getAuthor)
                .withProperty("description", Books::getDescription)
                .withProperty("publisher", Books::getPublisher)
                .withProperty("isbn", Books::getIsbn)
                .withProperty("available", Books::getPhysicalAvailableBooks)
                .withEventHandler("handleClick", books -> {
                    grid.getDataProvider().refreshItem(books);
                }));

        grid.setDetailsVisibleOnClick(true);
        //grid.addColumn(new NativeButtonRenderer<>("Details", item -> grid.setDetailsVisible(item, !grid.isDetailsVisible(item))));
    }

}