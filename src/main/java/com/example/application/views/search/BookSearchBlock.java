package com.example.application.views.search;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;

public class BookSearchBlock extends AbstractSearchBlock<Books, BooksRepository> {

    public static final String TITLE = "Title";
    public static final String GENRE = "Genre";
    public static final String AUTHOR = "Author";
    public static final String PUBLISHER = "Publisher";
    public static final String ISBN = "ISBN";
    public static final String MULTISEARCH = "Title, author, genre or ISBN";

    public BookSearchBlock(Class<Books> entityClass, BooksRepository repository) {
        super(entityClass, repository);
    }

    @Override
    protected void filterItems(String filterText, String choice) {
        if (choice.equals(MULTISEARCH)) {
            if (filterText == null || filterText.isEmpty()) {
                grid.setItems(repository.findAll());
            } else {
                grid.setItems(repository.findByAuthorOrTitleOrGenreOrIsbn(filterText));
            }
        } else if (choice.equals(ISBN)) {
            if (filterText == null || filterText.isEmpty()) {
                grid.setItems(repository.findAll());
            } else {
                grid.setItems(repository.findByIsbnStartsWithIgnoreCase(filterText));
            }
        } else {
            String[] searchArgs = {"%", "%", "%", "%"};
            filters.forEach(textField -> {
                String searchString = textField.getValue();
                if ( ! searchString.isEmpty() ) {
                    switch (textField.getLabel()) {
                        case TITLE -> searchArgs[0] = "%" + searchString + "%";
                        case AUTHOR -> searchArgs[1] = "%" + searchString + "%";
                        case GENRE -> searchArgs[2] = "%" + searchString + "%";
                        case PUBLISHER -> searchArgs[3] = "%" + searchString + "%";
                    }
                }
            });
            grid.setItems(
                    repository.findByTitleAndAuthorAndGenreAndPublisher(searchArgs[0], searchArgs[1], searchArgs[2], searchArgs[3])
            );
        }
    }

    protected void itemDetails() {
        grid.setItemDetailsRenderer(TemplateRenderer.<Books>of(
                "<div class='custom-details' style='border: 2px solid #1676f3; border-radius: 5px;"
                    + " padding: 10px 15px; width: 100%; box-sizing: border-box;'>"
                        + "<div>"
                        + "<H3 style='margin: 0 0 0.25em;'>[[item.title]]</H3>"
                        + "<H4 style='margin: 0 0 0.75em; font-style: italic; font-weight: 400;'>[[item.author]]</H4>"
                        + "<p style='margin: 0 0 0.75em;'>[[item.description]]</p>"
                        + "<div style='display: flex; flex-flow: row wrap; /*justify-content: space-between;*/'>"
                        + "<span style='margin-right: 1.75em; min-width: 150px;'>Publisher: <b>[[item.publisher]]</b></span>"
                        + "<span style='margin-right: 1.75em; min-width: 150px;'>ISBN: <b>[[item.isbn]]</b></span>"
                        + "<span style='margin-right: 1.75em; min-width: 150px;'>Books available: <b>[[item.available]]</b></span>"
                        + "</div>"
                        + "</div>"
                        + "</div>")
                .withProperty("title", Books::getTitle)
                .withProperty("author", Books::getAuthor)
                .withProperty("description", Books::getDescription)
                .withProperty("publisher", Books::getPublisher)
                .withProperty("isbn", Books::getIsbn)
                .withProperty("available", Books::getPhysicalAvailableBooks)
                .withEventHandler("handleClick", person -> {
                    grid.getDataProvider().refreshItem(person);
                }));

        grid.setDetailsVisibleOnClick(false);
        grid.addColumn(new NativeButtonRenderer<>("Details", item -> grid
                .setDetailsVisible(item, !grid.isDetailsVisible(item))));
    }

}
