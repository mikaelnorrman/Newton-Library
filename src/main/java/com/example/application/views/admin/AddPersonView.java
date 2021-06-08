package com.example.application.views.admin;

import com.example.application.data.entity.Person;
import com.example.application.data.entity.Role;
import com.example.application.data.service.PersonRepository;
import com.example.application.editors.PersonEditor;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

@PageTitle("Persons")
@CssImport("./styles/views/admin/admin-view.css")
public class AddPersonView extends VerticalLayout {

    private final PersonRepository repository;
    final PersonEditor editor;
    final Grid<Person> grid;
    final TextField filterFirstName;
    final TextField filterLastName;
    final TextField filterEmail;
    private final Button addPerson;
    private final Button back;

    @Autowired
    public AddPersonView(PersonRepository repository) {
        this.repository = repository;
        this.grid = new Grid<>(Person.class);
        this.editor = new PersonEditor(repository);

        this.filterFirstName = new TextField();
        this.filterFirstName.setPlaceholder("Filter by First Name");
        this.filterFirstName.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterFirstName.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.filterLastName = new TextField();
        this.filterLastName.setPlaceholder("Filter by Last Name");
        this.filterLastName.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterLastName.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.filterEmail = new TextField();
        this.filterEmail.setPlaceholder("Filter by Email");
        this.filterEmail.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        this.filterEmail.setPrefixComponent(VaadinIcon.SEARCH.create());

        this.addPerson = new Button("New person", VaadinIcon.PLUS.create());
        this.addPerson.addThemeVariants(ButtonVariant.LUMO_SMALL);

        this.back = new Button("Back", VaadinIcon.HOME.create());
        this.back.addThemeVariants(ButtonVariant.LUMO_SMALL);

        //Build layout
        var actions = new HorizontalLayout(filterFirstName, filterLastName, filterEmail, addPerson, back);
        add(actions, grid, editor);
        actions.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        grid.setHeight("400px");
        grid.setColumns("idPersons", "firstName", "lastName", "email", "phone", "street", "postalCode",
                "city", "socialSecurityNo", "activeBorrowedBooks", "totalBorrowedBooks", "loancard", "roleId");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.getColumnByKey("idPersons").setWidth("50px").setFlexGrow(0);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        //Hook logic
        //Replace listing with filter
        filterFirstName.setValueChangeMode(ValueChangeMode.EAGER);
        filterFirstName.addValueChangeListener(e -> listPerson(e.getValue(),1));
        filterLastName.setValueChangeMode(ValueChangeMode.EAGER);
        filterLastName.addValueChangeListener(e -> listPerson(e.getValue(),2));
        filterEmail.setValueChangeMode(ValueChangeMode.EAGER);
        filterEmail.addValueChangeListener(e -> listPerson(e.getValue(),3));

        //Connect selected staff to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e ->  editor.editPerson(e.getValue()));

        //instantiate end edit new staff
        addPerson.addClickListener (e -> editor.
                editPerson(new Person("","","","","",
                        "","","","","0","0",false, Role.USER)));



        //back button | .navigate("") -> Bestämmer till vilken vy man skall gå till.
        back.addClickListener(e -> UI.getCurrent().navigate("opening"));

        //Listen changes made by the editor, refresh data
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listPerson(filterFirstName.getValue(),1);
            listPerson(filterLastName.getValue(),2);
            listPerson(filterEmail.getValue(),3);
        });

        //Initialize listing
        listPerson(null,3);
    }

    void listPerson(String filterText, int choice) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repository.findAll());
        } else {
            if (choice == 1) {
                grid.setItems(repository.findByFirstNameStartsWithIgnoreCase(filterText));
            } else if (choice == 2) {
                grid.setItems(repository.findByLastNameStartsWithIgnoreCase(filterText));
            } else if (choice == 3) {
                grid.setItems(repository.findByEmailStartsWithIgnoreCase(filterText));
            }
        }
    }


}