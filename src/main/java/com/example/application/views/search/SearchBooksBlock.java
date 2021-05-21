package com.example.application.views.search;

import com.example.application.data.entity.Books;
import com.example.application.data.service.BooksRepository;

public class SearchBooksBlock extends AbstractSearchBlock<Books, BooksRepository> {

    public SearchBooksBlock(Class<Books> entityClass, BooksRepository repository) {
        super(entityClass, repository);
    }

    @Override
    protected void filterItems(String filterText, int choice) {
        if (filterText == null || filterText.isEmpty()) {
            grid.setItems(repository.findAll());
        } else {
            switch (choice) {
                case 1 -> grid.setItems(repository.findByTitleStartsWithIgnoreCase(filterText));
                case 2 -> grid.setItems(repository.findByGenreStartsWithIgnoreCase(filterText));
                case 3 -> grid.setItems(repository.findByAuthorStartsWithIgnoreCase(filterText));
                case 4 -> grid.setItems(repository.findByPublisherStartsWithIgnoreCase(filterText));
                case 5 -> grid.setItems(repository.findByIsbnStartsWithIgnoreCase(filterText));
                default -> throw new NoSuchMethodError("No repository method has been matched against argument of value " + choice);
            }
        }
    }

}
