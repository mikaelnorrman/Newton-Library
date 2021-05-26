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
public class VisitorBookSearchView extends Div {

    private final BookSearchBlock visitorSearch;

    @Autowired
    public VisitorBookSearchView(BooksRepository repository) {
        visitorSearch = new BookSearchBlock(Books.class, repository);
        visitorSearch.setFilterTitle("Filter by...");
        visitorSearch.addFilters(BookSearchBlock.MULTISEARCH);
        visitorSearch.setColumns("title", "author", "genre", "for_ages", "section", "shelf");
        this.add(visitorSearch);
    }
}
