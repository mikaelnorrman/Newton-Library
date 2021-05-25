package com.example.application.views.admin;

import com.example.application.data.entity.Seminars;
import com.example.application.data.service.SeminarService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.html.Div;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@PageTitle("Seminars")
@CssImport("./styles/views/admin/admin-view.css")
public class SeminarView extends Div {

    public static final String TITLE_IN_SET_ATTRIBUTE = "title";
    private Grid<Seminars> grid;

    private TextField name = new TextField("Name");
    private TextField presenter = new TextField("Presenter");
    private TextField description = new TextField("Description");
    private TextField length = new TextField("Length");
    private TextField seats_booked = new TextField("Seats Booked");
   // private TextField date_time = new TextField("Date Time");
   // private TextField date_added = new TextField("Date Added");
    private TextField active = new TextField("Active");
   // private TextField id = new TextField("Id");


    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Seminars> binder;

    private Seminars seminar = new Seminars();

    private SeminarService seminarService;

    public SeminarView(@Autowired SeminarService seminarService){
        setId("book-admin-view");
        this.seminarService = seminarService;
        // Configure Grid - This will show up in the Grid
        grid = new Grid<>(Seminars.class);
        grid.setColumns("name", "presenter", "description", "length");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.setDataProvider(new CrudServiceDataProvider<Seminars, Void>(seminarService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Seminars> seminarsFromBackend = seminarService.get(event.getValue().getId_seminar());
                // when a row is selected but the data is no longer available, refresh grid
                if (seminarsFromBackend.isPresent()) {
                    populateForm(seminarsFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new Binder<>(Seminars.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.seminar == null) {
                    this.seminar = new Seminars();
                }
                binder.writeBean(this.seminar);
                seminarService.update(this.seminar);
                clearForm();
                refreshGrid();
                Notification.show("Seminar details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the seminar details.");
            }
        });

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
    }


    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        sidbarSeminarEdit();

        FormLayout formLayout = new FormLayout();
        AbstractField[] fields = new AbstractField[] {name, presenter, description, length, seats_booked, active};
        for (AbstractField field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    //TODO Fixa Patterns och Errormessage för sidbarSeminarEdit samt gör dem till egna methoder
    private void sidbarSeminarEdit() {
        nameEdit();
        presenterEdit();
        descriptionEdit();
        lengthEdit();
        seats_bookedEdit();
        activeEdit();
    }

    private void nameEdit() {
        name.setLabel("Name");
        name.setPlaceholder("Enter name for seminar");
        name.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Yoga in nature");
        name.setClearButtonVisible(true);
        name.setErrorMessage("Your firstname needs to be at least two character long");
        name.setPattern("^[A-Z]");
        name.setErrorMessage("Numbers only. A-Z");
        name.setMinLength(2);
        name.setRequired(true);
        name.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void presenterEdit() {
        presenter.setLabel("Presenter");
        presenter.setPlaceholder("Enter presenter");
        presenter.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Deborah Rademaker");
        presenter.setClearButtonVisible(true);
        presenter.setErrorMessage("Your presenter needs to be at least two character long");
        presenter.setPattern("^[A-Z]");
        presenter.setErrorMessage("Numbers only. A-Z");
        presenter.setMinLength(2);
        presenter.setRequired(true);
        presenter.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void descriptionEdit() {
        description.setLabel("Description");
        description.setPlaceholder("Enter description");
        description.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Experience the best places for yoga and becoming one with nature!");
        description.setClearButtonVisible(true);
        description.setErrorMessage("Your description needs to be at least two character long");
        description.setPattern("^[A-Z]");
        description.setErrorMessage("Numbers only. A-Z");
        description.setMinLength(2);
        description.setRequired(true);
        description.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void lengthEdit() {
        length.setPlaceholder("Enter a lenght");
        length.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 01:30:00");
        length.setClearButtonVisible(true);
        length.setErrorMessage("Your seminar lenght needs to be in -> hours : minutes : seconds");
        length.setMinLength(8);
        length.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void seats_bookedEdit() {
        seats_booked.setLabel("Seats Booked");
        seats_booked.setPlaceholder("Enter a seats booked");
        seats_booked.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 15");
        seats_booked.setClearButtonVisible(true);
        seats_booked.setErrorMessage("Your specify if there is seats booked");
        seats_booked.setPattern("^[0-9]");
        seats_booked.setErrorMessage("Numbers only.");
        seats_booked.setMinLength(1);
        seats_booked.setRequired(true);
        seats_booked.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void activeEdit() {
        active.setLabel("Active");
        active.setPlaceholder("Enter active");
        active.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 1 or 0");
        active.setClearButtonVisible(true);
        active.setErrorMessage("Your can only select 1 or 0");
        active.setMinLength(1);
        active.setMaxLength(1);
        active.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Seminars value) {
        this.seminar = value;
        binder.readBean(this.seminar);
    }
}