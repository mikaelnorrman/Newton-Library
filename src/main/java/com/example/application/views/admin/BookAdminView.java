package com.example.application.views.admin;

import com.example.application.Editors.BookEditor;
import com.example.application.data.entity.Books;
import com.example.application.data.entity.Person;
import com.example.application.data.service.BooksRepository;
import com.example.application.data.service.PersonService;
import com.example.application.views.search.SearchView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Book Admin")
@CssImport("./styles/views/admin/admin-view.css")
public class BookAdminView extends Div {

    private SearchView searchView;
    private BookEditor editor;

    @Autowired
    public BookAdminView(BooksRepository repository) {
        searchView = new SearchView(repository);
        editor = new BookEditor(repository);
        HorizontalLayout layout = new HorizontalLayout(searchView, editor);
        add(layout);
    }
    /*
    private Grid<Books> grid;

    private TextField title = new TextField("Title");
    private TextField description = new TextField("Description");
    private TextField genre = new TextField("Genre"); //dropdown?
    private TextField author = new TextField("Author");
    private TextField forAges = new TextField("For ages:"); //dropdown?
    private TextField amount = new TextField("Number of items");

    private RadioButtonGroup<Boolean> isEBook = new RadioButtonGroup<>();

    private TextField price = new TextField("Price");
    private TextField shelf = new TextField("Shelf");
    private TextField section = new TextField("Section"); //dropdown?

    private TextField isbn = new TextField("ISBN");
    private TextField publisher = new TextField("Publisher");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Books> binder;

    private Books book = new Books();

    private BookService bookService;

    public BookAdminView(@Autowired BookService bookService){

    }

     */

}
