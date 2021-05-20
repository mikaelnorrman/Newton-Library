package com.example.application.editors;

import com.example.application.data.entity.Books;
import com.example.application.data.entity.Seminars;
import com.example.application.data.service.BooksRepository;
import com.example.application.data.service.SeminarsRepository;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class Editor extends VerticalLayout implements KeyNotifier {

    protected BooksRepository booksRepository;
    protected SeminarsRepository seminarRepository;

    protected Books books;
    protected Seminars seminars;

    Button save = new Button("Save", VaadinIcon.PLUS.create());
    Button cancel = new Button("Cancel", VaadinIcon.PLUS.create());
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Books> booksBinder;
    Binder<Seminars> seminarsBinder;

    protected ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }
    public void setChangeHandler(ChangeHandler handler) {changeHandler = handler;}

}
