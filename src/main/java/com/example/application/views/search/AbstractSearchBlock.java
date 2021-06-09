package com.example.application.views.search;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public abstract class AbstractSearchBlock<S, T extends JpaRepository<S, Integer>> extends VerticalLayout {

    protected T repository;
    protected Grid<S> grid;
    protected ArrayList<TextField> filters = new ArrayList<>();

    private HorizontalLayout filterLayout = new HorizontalLayout();

    public AbstractSearchBlock(Class<S> entityClass, T repository) {
        this.repository = repository;
        this.grid = new Grid<>(entityClass);
        this.add(filterLayout, grid);
        this.setHeightFull();
    }

    public void setColumns(String... propertyNames) {
        grid.setColumns(propertyNames);
        grid.getColumns().forEach(column -> column.setAutoWidth(true)); //Default
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();
        grid.setVisible(true);
        grid.setItems(repository.findAll());
    }

    public void setColumnWidth(String columnKey, String width) {
        grid.getColumnByKey(columnKey).setAutoWidth(false).setWidth(width).setFlexGrow(0);
    }

    public void addFilters(String... choices) {
        for (String choice : choices) {
            TextField filter = new TextField();
            filter.setPlaceholder(choice);
            filter.setClearButtonVisible(true);
            filter.addThemeVariants(TextFieldVariant.LUMO_SMALL);
            filter.setMinWidth("225px");
            filter.setPrefixComponent(VaadinIcon.SEARCH.create());
            filter.setValueChangeMode(ValueChangeMode.EAGER);
            filter.addValueChangeListener(E -> filterItems(E.getValue(), choice));
            filters.add(filter);
        }
        buildFilters();
    }

    protected abstract void filterItems(String filterText, String choice);

    public abstract void refreshGrid();

    public void buildFilters() {
        if (filters.isEmpty()) return;

        filters.forEach(filterLayout::add);
        filterLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
    }

    public void clearFilters() {
        filters.forEach(HasValue::clear);
    }

    public Grid<S> getGrid() {
        return this.grid;
    }

    public HorizontalLayout getFilterLayout() {
        return this.filterLayout;
    }
}
