package com.example.application.views.admin;

import com.example.application.data.entity.Seminars;
import com.example.application.data.service.SeminarsRepository;
import com.example.application.editors.SeminarEditor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.Date;


@PageTitle("Add SeminarView")
@CssImport("./styles/views/admin/admin-view.css")
public class AddSeminarView extends VerticalLayout {

    private final SeminarsRepository repository;
    final SeminarEditor editor;
    final Grid<Seminars> grid;
    final TextField filterName;
    final TextField filterPresenter;
    private final Button addSeminar;
    private final Button back;


    public AddSeminarView(SeminarsRepository repository) {
        this.repository = repository;
        this.grid = new Grid<>(Seminars.class);
        this.editor = new SeminarEditor(repository);

        this.filterName = new TextField();
        this.filterName.setPlaceholder("Filter by name");
        this.filterName.setClearButtonVisible(true);
        this.filterName.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterName.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.filterPresenter = new TextField();
        this.filterPresenter.setPlaceholder("Filter by presenter");
        this.filterPresenter.setClearButtonVisible(true);
        this.filterPresenter.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterPresenter.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.addSeminar = new Button("New seminar", VaadinIcon.PLUS.create());
        this.back = new Button("Back", VaadinIcon.HOME.create());

        //Build layout
        var actions = new HorizontalLayout(filterName, filterPresenter, addSeminar, back);
        add(actions, grid, editor);
        actions.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        grid.setHeight("400px");
        grid.setColumns("id_seminar", "name", "presenter", "description", "length", "seats_booked", "date_time",
                 "active");
        grid.getColumnByKey("id_seminar").setWidth("50px").setFlexGrow(0);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        //Hook logic
        //Replace listing with filter
        filterName.setValueChangeMode(ValueChangeMode.EAGER);
        filterName.addValueChangeListener(e -> listSeminar(e.getValue(),1));
        filterPresenter.setValueChangeMode(ValueChangeMode.EAGER);
        filterPresenter.addValueChangeListener(e -> listSeminar(e.getValue(),2));


        //Connect selected staff to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e ->  editor.editSeminar(e.getValue()));


        //instantiate end edit new staff
        addSeminar.addClickListener (e -> editor.
                editSeminar(new Seminars("","","",  "00:00:00","", Date.from(Instant.now()),
                        "")));



        //back button | .navigate("") -> Bestämmer till vilken vy man skall gå till.
        back.addClickListener(e -> UI.getCurrent().navigate("opening"));

        //Listen changes made by the editor, refresh data
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listSeminar(filterName.getValue(),1);
            listSeminar(filterPresenter.getValue(),2);
        });

        //Initialize listing
        listSeminar(null,2);
    }

    void listSeminar(String filterText, int choice) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repository.findAll());
        } else {
            if (choice == 1) {
                grid.setItems(repository.findByNameStartsWithIgnoreCase(filterText));
            } else if (choice == 2) {
                grid.setItems(repository.findByPresenterStartsWithIgnoreCase(filterText));
            }
        }
    }
}