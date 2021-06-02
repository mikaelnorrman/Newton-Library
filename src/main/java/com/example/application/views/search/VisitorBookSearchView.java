package com.example.application.views.search;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;
import com.example.application.views.main.MainViewLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "visitor", layout = MainViewLayout.class)
@PageTitle("Search")
@CssImport(value = "./styles/views/search/search-view.css")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class VisitorBookSearchView extends Div {

    private final BookSearchBlock visitorSearch;

    @Autowired
    public VisitorBookSearchView(BooksRepository repository) {
        visitorSearch = new BookSearchBlock(Books.class, repository);
        visitorSearch.setFilterTitle("Filter by...");
        visitorSearch.addFilters(BookSearchBlock.MULTISEARCH, BookSearchBlock.TITLE, BookSearchBlock.AUTHOR, BookSearchBlock.GENRE, BookSearchBlock.ISBN);
        visitorSearch.setColumns("title", "author", "genre", "ages", "section", "shelf");
        this.add(visitorSearch);
    }
}
