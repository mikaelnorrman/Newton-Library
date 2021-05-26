package com.example.application.views.search;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;

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
        if (filterText == null || filterText.isEmpty()) {
            grid.setItems(repository.findAll());
        } else {
            switch (choice) {
                case TITLE -> grid.setItems(repository.findByTitleStartsWithIgnoreCase(filterText));
                case GENRE -> grid.setItems(repository.findByGenreStartsWithIgnoreCase(filterText));
                case AUTHOR -> grid.setItems(repository.findByAuthorStartsWithIgnoreCase(filterText));
                case PUBLISHER -> grid.setItems(repository.findByPublisherStartsWithIgnoreCase(filterText));
                case ISBN -> grid.setItems(repository.findByIsbnStartsWithIgnoreCase(filterText));
                case MULTISEARCH -> grid.setItems(repository.findByAuthorOrTitleOrGenreOrIsbn(filterText));
                default -> throw new NoSuchMethodError(
                        "No repository method has been matched against argument of value '" + choice + "'");
            }
        }
    }

}
