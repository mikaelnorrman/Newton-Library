package com.example.application.views.search;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
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

    private ArrayList<TextField> filters = new ArrayList<>();
    private Div filterWrapper = new Div(), gridWrapper = new Div();
    private H3 filterHead = new H3();
    private String filterTitle;

    public AbstractSearchBlock(Class<S> entityClass, T repository) {
        this.repository = repository;
        this.grid = new Grid<>(entityClass);
        filterWrapper.setId("filter-wrapper");
        filterWrapper.setWidthFull();
        gridWrapper.setId("grid-wrapper");
        gridWrapper.setSizeFull();
        gridWrapper.add(grid);
        this.add(filterWrapper, gridWrapper);
    }

    public void setColumns(String... propertyNames) {
        grid.setColumns(propertyNames);
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setMinHeight("25vh");
        grid.setItems(repository.findAll());
    }

    public void addFilters(String... labels) {
        int choice = filters.size();
        for (String label : labels) {
            choice++;
            int finalChoice = choice;
            TextField filter = new TextField(label);
            filter.setValueChangeMode(ValueChangeMode.EAGER);
            filter.addValueChangeListener(E -> filterItems(E.getValue(), finalChoice));
            filters.add(filter);
        }
        buildFilters();
    }

    protected abstract void filterItems(String filterText, int choice);

    private void buildFilters() {
        filterWrapper.removeAll();
        if (filters.isEmpty()) return;

        HorizontalLayout layout = new HorizontalLayout();
        filterHead.add(filterTitle);
        layout.add(filterHead);
        filters.forEach(layout::add);
        layout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
        filterWrapper.add(layout);
    }

    public void connectEditorToGrid(HasValue.ValueChangeListener listener) {}

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

}
