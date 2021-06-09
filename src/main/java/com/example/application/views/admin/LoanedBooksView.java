package com.example.application.views.admin;

import com.example.application.data.entity.LoanedBooks;
import com.example.application.data.entity.Person;
import com.example.application.data.service.LoanedBooksRepository;
import com.example.application.data.service.LoanedBooksService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;

@PageTitle("Loaned Books")
@CssImport("./styles/views/admin/admin-view.css")
public class LoanedBooksView extends Div {

    private Grid<LoanedBooks> grid;
    private Grid<LoanedBooks> gridAdmin;
    private LoanedBooksService loanedBooksService;
    private final LoanedBooksRepository repository;

    public LoanedBooksView(LoanedBooksService loanedBooksService, LoanedBooksRepository loanedBooksRepository) throws SQLException {
        setId("loaned-books-view");
        addClassName("loaned-books-view");
        setSizeFull();
        this.loanedBooksService = loanedBooksService;
        this.repository = loanedBooksRepository;
        this.grid = new Grid<>(LoanedBooks.class);
        this.gridAdmin = new Grid<>(LoanedBooks.class);

        add(grid);
        grid.setColumns("title", "loaned", "dueDate", "expired");
        grid.getColumnByKey("title").setAutoWidth(true);
        grid.getColumnByKey("loaned").setAutoWidth(true);
        grid.getColumnByKey("expired").setAutoWidth(true);
        grid.getColumnByKey("dueDate").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();
        grid.setVisible(true);

        add(gridAdmin);
        gridAdmin.setColumns("title", "loaned", "dueDate", "expired", "firstName", "lastName");
        gridAdmin.getColumnByKey("title").setAutoWidth(true);
        gridAdmin.getColumnByKey("loaned").setAutoWidth(true);
        gridAdmin.getColumnByKey("expired").setAutoWidth(true);
        gridAdmin.getColumnByKey("dueDate").setAutoWidth(true);
       // gridAdmin.getColumnByKey("userId").setAutoWidth(true); //Vill inte fungera av någon anlednign
        gridAdmin.getColumnByKey("firstName").setAutoWidth(true);
        gridAdmin.getColumnByKey("lastName").setAutoWidth(true);
        gridAdmin.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        gridAdmin.setHeightFull();

        Integer roleIdKoll = VaadinSession.getCurrent().getAttribute(Person.class).getRoleId();

        if (roleIdKoll == 2 || roleIdKoll == 3) {
            listAllBooks();
        } else {
            listBooks(VaadinSession.getCurrent().getAttribute(Person.class).getIdPersons());
        }
    }

    void listBooks(Integer id){

        grid.setItems(repository.findByUserId(id));
    }

    void findExpiredBooks(Integer id){
        grid.setItems(repository.findByUserID(id));
    }


    void listAllBooks(){
        grid.setVisible(false);
        gridAdmin.setItems(repository.findAll());
        gridAdmin.setVisible(true);
    }
}
