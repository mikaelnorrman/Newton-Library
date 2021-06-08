package com.example.application.views.admin;

import com.example.application.Connector.ConnectorMySQL;
import com.example.application.data.entity.Books;
import com.example.application.data.entity.LoanedBooks;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BookService;
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
    public BookView(BookService bookService, LoanedBooksRepository loanedBooksRepository) {
        this.loanedBookEditor = new LoanedBookEditor(loanedBooksRepository);
        setSizeFull();
        setId("book-admin-view");
        this.bookService = bookService;
        // Configure Grid - This will show up in the Grid
        grid = new Grid<>(Books.class);

        add(grid);
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
        grid.setVisible(true);

    }


// ----------------------------------------------------------------------------------------------------

    private Button createLoanButton(Grid<Books> grid, Books item) {
        boolean checkLoancard = VaadinSession.getCurrent().getAttribute(Person.class).getLoancard() == true; // koll så att användaren har ett lånekort.
        Integer idPersons = VaadinSession.getCurrent().getAttribute(Person.class).getIdPersons();       // hämta ut inloggade personens id.
        String firstNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getFirstName(); // hämta ut inloggade personens fistName.
        String lastNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getLastName();   // hämta ut inloggade personens lastName.

        Button loanButton = new Button("Loan book",  editor  -> {
            Integer idOfBooks = item.getId();
            String titleOfBook = item.getTitle();
            item.getTitle();
            item.getId();
            loanedBookEditor.saveLoaned(new LoanedBooks(idOfBooks, idPersons,titleOfBook, 0));
            successLoanedBookNotification(item);
        });

        Button loanedButton = new Button ("Book loaned", editor -> {
            errorLoanedBookNotification(item);
        });

        Button noCardButton = new Button ("Loan Book", editor -> {
            loanedCardNotification(item, firstNamePersons, lastNamePersons);

        });


        if (checkLoancard) {
            try {
                if (!connectorMySQL.callcheck_loan(idPersons,item.getId()))
                {
                    loanButton.setDisableOnClick(true);
                    loanButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
                    return loanButton;
                } else {
                    loanedButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
                    return loanedButton;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
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

    private void successLoanedBookNotification (Books item) {
        Notification successLoanedBookNotification = new Notification("You loaned the book \n" + item.getTitle());
        successLoanedBookNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        successLoanedBookNotification.setDuration(DURATION_NOTIFICATION);
        successLoanedBookNotification.setPosition(Notification.Position.MIDDLE);
        successLoanedBookNotification.open();
    }

}