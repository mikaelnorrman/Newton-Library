package com.example.application.views.admin;

import com.example.application.data.entity.LoanedBooks;
import com.example.application.data.service.LoanedBooksRepository;
import com.example.application.data.service.LoanedBooksService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

@PageTitle("Loaned Books")
@CssImport("./styles/views/admin/admin-view.css")
public class LoanedBooksView extends VerticalLayout {

    private Grid<LoanedBooks> grid;
    private LoanedBooksService loanedBooksService;

    public LoanedBooksView(LoanedBooksService loanedBooksService, LoanedBooksRepository loanedBooksRepository) {
        this.loanedBooksService = loanedBooksService;
        grid = new Grid<>(LoanedBooks.class);
        grid.setColumns("id", "loaned", "expired", "users_id_users");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.setDataProvider(new CrudServiceDataProvider<LoanedBooks,Void>(loanedBooksService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();


        setId("loaned-books-view");
        addClassName("loaned-books-view");
        setSizeFull();

        TextArea textArea = new TextArea();
        textArea.setWidth("400px");


        textArea.setValue("""
               Här kommer vi se alla lånade böcker""");
        add(textArea);

    }
}
