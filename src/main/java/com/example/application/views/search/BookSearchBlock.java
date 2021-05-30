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

}
