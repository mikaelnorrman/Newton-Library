package com.example.application.views.admin;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Loaned Books")
@CssImport("./styles/views/admin/admin-view.css")
public class LoanedBooksView extends VerticalLayout {

    public LoanedBooksView() {
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
