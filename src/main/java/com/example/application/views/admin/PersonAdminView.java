package com.example.application.views.admin;

import com.example.application.data.entity.Person;
import com.example.application.data.service.PersonService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@PageTitle("User Admin")
@CssImport("./styles/views/admin/admin-view.css")
public class PersonAdminView extends Div {

    private Grid<Person> grid;

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private TextField phone = new TextField("Phone");
    private TextField street = new TextField("Street");
    private TextField postalCode = new TextField("Postal code");
    private TextField city = new TextField("City");
    private TextField socialSecurityNo = new TextField("Social security number");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Person> binder;

    private Person person = new Person();

    private PersonService personService;

    public PersonAdminView(@Autowired PersonService personService) {
        setId("person-admin-view");
        this.personService = personService;
        // Configure Grid
        grid = new Grid<>(Person.class);
        grid.setColumns("first_name", "last_name", "email", "phone", "street", "postal_code", "city", "social_security_no");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.setDataProvider(new CrudServiceDataProvider<Person, Void>(personService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Person> personFromBackend = personService.get(event.getValue().getId_users());
                // when a row is selected but the data is no longer available, refresh grid
                if (personFromBackend.isPresent()) {
                    populateForm(personFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new Binder<>(Person.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.person == null) {
                    this.person = new Person();
                }
                binder.writeBean(this.person);
                personService.update(this.person);
                clearForm();
                refreshGrid();
                Notification.show("Person details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the person details.");
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

        sidbarPersonEdit();

        FormLayout formLayout = new FormLayout();
        AbstractField[] fields = new AbstractField[] { firstName, lastName, email, phone, street, postalCode, city, socialSecurityNo};
        for (AbstractField field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void sidbarPersonEdit() {

        firstName.setLabel("First Name");
        firstName.setPlaceholder("Enter first name");
        firstName.getElement().setAttribute("title", "Example: Johan");
        firstName.setClearButtonVisible(true);
        firstName.setErrorMessage("Your firstname needs to be at least two character long");
        firstName.setPattern("^[A-Z]");
        firstName.setErrorMessage("Numbers only. A-Z");
        firstName.setMinLength(2);
        firstName.setRequired(true);
        //-----------------------------------------------------------------------
        lastName.setLabel("Last Name");
        lastName.setPlaceholder("Enter last name");
        lastName.getElement().setAttribute("title", "Example: Johansson");
        lastName.setClearButtonVisible(true);
        lastName.setErrorMessage("Your last name needs to be at least two character long");
        lastName.setPattern("^[A-Z]");
        lastName.setErrorMessage("Numbers only. A-Z");
        lastName.setMinLength(2);
        lastName.setRequired(true);
        //-----------------------------------------------------------------------
        email.setLabel("E-mail");
        email.setPlaceholder("Enter email");
        email.getElement().setAttribute("title", "Example: Johan.Johansson@libsys.se");
        email.setClearButtonVisible(true);
        email.setErrorMessage("Your email needs to be at least five character long");
        // email.setPattern("^[A-Z]"); Lägg till fler tecken
        email.setErrorMessage("error message");
        email.setMinLength(5);
        email.setRequired(true);
        //-----------------------------------------------------------------------
        phone.setLabel("Phone");
        phone.setPlaceholder("Enter phonenumber");
        phone.getElement().setAttribute("title", "Example: 08-23423 & 0709123987");
        phone.setClearButtonVisible(true);
        phone.setErrorMessage("Your phonenumber needs to be at least 7 character long");
        phone.setPattern("^[0-9]"); //Lägg till + tecken
        phone.setErrorMessage("Phonenumber needs to be 7 characters long up to 13");
        phone.setMinLength(7);
        phone.setMaxLength(13);
        phone.setRequired(true);
        //-----------------------------------------------------------------------
        street.setLabel("Street");
        street.setPlaceholder("Enter street");
        street.getElement().setAttribute("title", "Example: Stengatan");
        street.setClearButtonVisible(true);
        street.setErrorMessage("Your street needs to be at least 2 character long");
        street.setPattern("^[a-zA-Z\\s]+[0-9]");
        street.setErrorMessage("Phonenumber needs to be 7 characters long up to 13");
        street.setMinLength(2);
        street.setRequired(true);
        //-----------------------------------------------------------------------
        postalCode.setLabel("Postal Code");
        postalCode.setPlaceholder("Enter postal code");
        postalCode.getElement().setAttribute("title", "Example: 12398");
        postalCode.setClearButtonVisible(true);
        postalCode.setErrorMessage("Your postal code needs to be at least 5 character long");
        postalCode.setPattern("^[0-9]");
        postalCode.setErrorMessage("Postal Code needs to be 5 characters long");
        postalCode.setMinLength(5);
        postalCode.setRequired(true);

        //-----------------------------------------------------------------------
        city.setLabel("City");
        city.setPlaceholder("Enter city");
        city.getElement().setAttribute("title", "Example: Stockholm");
        city.setClearButtonVisible(true);
        city.setErrorMessage("Your city needs to be at least 2 character long");
        city.setPattern("^[a-zA-z + åäöÅÄÖ]");
        city.setErrorMessage("City needs to be 2 characters long");
        city.setMinLength(2);
        city.setRequired(true);
        //-----------------------------------------------------------------------
        socialSecurityNo.setLabel("Social Security Number");
        socialSecurityNo.setPlaceholder("Enter social security Number");
        socialSecurityNo.getElement().setAttribute("title", "Example: 7304205120");
        socialSecurityNo.setClearButtonVisible(true);
        socialSecurityNo.setErrorMessage("Your city needs to be at least 2 character long");
        socialSecurityNo.setPattern("^[0-9]");
        socialSecurityNo.setErrorMessage("Social security number needs to be 10 characters long");
        socialSecurityNo.setMinLength(10);
        socialSecurityNo.setMaxLength(12);
        socialSecurityNo.setRequired(true);
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

    private void populateForm(Person value) {
        this.person = value;
        binder.readBean(this.person);
    }
}
