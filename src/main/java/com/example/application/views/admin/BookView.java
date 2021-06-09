package com.example.application.views.admin;

import com.example.application.Connector.ConnectorMySQL;
import com.example.application.data.entity.Books;
import com.example.application.data.entity.LoanedBooks;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BooksRepository;
import com.example.application.data.service.LoanedBooksRepository;
import com.example.application.editors.LoanedBookEditor;
import com.example.application.views.search.BookSearchBlock;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

@PageTitle("Books")
@CssImport("./styles/views/admin/admin-view.css")
public class BookView extends Div {

    public static final int DURATION_NOTIFICATION = 4500;
    ConnectorMySQL connectorMySQL = new ConnectorMySQL();
    final LoanedBookEditor loanedBookEditor;

    private BookSearchBlock searchBlock;

    @Autowired
    public BookView(LoanedBooksRepository loanedBooksRepository, BooksRepository booksRepository) {
        this.loanedBookEditor = new LoanedBookEditor(loanedBooksRepository);
        setSizeFull();
        setId("book-admin-view");

        searchBlock = new BookSearchBlock(Books.class, booksRepository);
        searchBlock.addFilters(BookSearchBlock.TITLE, BookSearchBlock.AUTHOR, BookSearchBlock.GENRE, BookSearchBlock.ISBN);
        searchBlock.setColumns("title", "author", "genre", "ages", "section", "shelf");
        searchBlock.showItemDetailsButton();
        searchBlock.getGrid().addComponentColumn(Book -> createLoanButton(Book));
        add(searchBlock);
    }


// ----------------------------------------------------------------------------------------------------

    private Button createLoanButton(Books item) {
        boolean checkLoancard = VaadinSession.getCurrent().getAttribute(Person.class).getLoancard() == true; // koll så att användaren har ett lånekort.
        Integer idPersons = VaadinSession.getCurrent().getAttribute(Person.class).getIdPersons();       // hämta ut inloggade personens id.
        String firstNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getFirstName(); // hämta ut inloggade personens fistName.
        String lastNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getLastName();   // hämta ut inloggade personens lastName.

        Button loanButton = new Button("Loan book",  editor  -> {
            Integer idOfBooks = item.getId();
            String titleOfBook = item.getTitle();
            item.getTitle();
            item.getId();
            loanedBookEditor.saveLoaned(new LoanedBooks(idOfBooks, idPersons,titleOfBook, 0, firstNamePersons,
                    lastNamePersons));
            successLoanedBookNotification(item);
        });

        Button loanedButton = new Button ("Book loaned", editor -> {
            errorLoanedBookNotification(item);
        });
        Button expiredButton = new Button ("BOOK EXPIRED!", editor -> {
            expiredLoanedBookNotification(item);
        });

        Button noCardButton = new Button ("Loan Book", editor -> {
            loanedCardNotification(item, firstNamePersons, lastNamePersons);
        });


        if (checkLoancard) {
            try {
                if (!connectorMySQL.callcheck_loan(idPersons,item.getId()) && !connectorMySQL.checkExpiredBook(idPersons,item.getId()))
                {
                    loanButton.setDisableOnClick(true);
                    loanButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
                    return loanButton;
                } else if (connectorMySQL.checkExpiredBook(idPersons,item.getId())) {
                    expiredButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
                    return expiredButton;
                } else {
                    loanedButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);
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

    private void expiredLoanedBookNotification(Books item) {
        Notification errorLoanedBookNotification = new Notification("This book has expired: " + item.getTitle() + "! \n Please return it immediately or we will have to charge you for it. ");
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