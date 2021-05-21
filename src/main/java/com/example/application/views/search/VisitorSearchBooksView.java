package com.example.application.views.search;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Search")
@CssImport(value = "./styles/views/search/search-view.css")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class VisitorSearchBooksView extends Div {

    private final SearchBooksBlock visitorSearch;

    @Autowired
    public VisitorSearchBooksView(BooksRepository repository) {
        visitorSearch = new SearchBooksBlock(Books.class, repository);
        visitorSearch.setFilterTitle("Filter by...");
        visitorSearch.addFilters("Title", "Genre", "Author");
        visitorSearch.setColumns("title", "description","genre", "author", "for_ages", "shelf", "section", "isbn", "publisher");
        this.add(visitorSearch);
    }
}
