package com.example.application.views.search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public abstract class AbstractSearchBlock<S, T extends JpaRepository<S, Integer>> extends VerticalLayout {

    protected T repository;
    protected Grid<S> grid;
    protected ArrayList<TextField> filters = new ArrayList<>();

    private HorizontalLayout filterLayout = new HorizontalLayout();
    private H3 filterHead = new H3();
    private String filterTitle;

    public AbstractSearchBlock(Class<S> entityClass, T repository) {
        this.repository = repository;
        this.grid = new Grid<>(entityClass);
        this.add(filterLayout, grid);
    }

    public void setColumns(String... propertyNames) {
        grid.setColumns(propertyNames);
        grid.getColumns().forEach(column -> column.setAutoWidth(true)); //Bör inte bestämmas här
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeight("400px");
        grid.setItems(repository.findAll());
    }

    public void addFilters(String... choices) {
        for (String choice : choices) {
            TextField filter = new TextField(choice);
            filter.setValueChangeMode(ValueChangeMode.EAGER);
            filter.addValueChangeListener(E -> filterItems(E.getValue(), choice));
            filters.add(filter);
        }
        buildFilters();
    }

    protected abstract void filterItems(String filterText, String choice);

    public void buildFilters() {
        //filterLayout.removeAll();
        if (filters.isEmpty()) return;

        filterHead.add(filterTitle);
        filterLayout.add(filterHead);
        filters.forEach(filterLayout::add);
        filterLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    }

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public Grid<S> getGrid() {
        return this.grid;
    }

    public HorizontalLayout getFilterLayout() {
        return this.filterLayout;
    }
}
