package com.example.application.editors;

import com.example.application.data.entity.Person;
import com.example.application.data.service.PersonRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

public class PersonEditor extends Editor {

    public static final String TITLE_IN_SET_ATTRIBUTE = "title";
    TextField first_name;
    TextField last_name;
    EmailField email;
    TextField phone;
    TextField street;
    TextField postalCode;
    TextField city;
    TextField social_security_no;
    PasswordField password;
    Checkbox loancard;
    TextField roleId;
    //Checkbox roleId;


    public PersonEditor(PersonRepository personRepository) {
        this.personRepository = personRepository;
        this.personBinder = new Binder<>(Person.class);


// --------------------------------------------------------------------------------------------------------
        HorizontalLayout personerEdit = new HorizontalLayout();
        FormLayout personerFormLayout = new FormLayout();
        // Setting the desired responsive steps for the columns in the layout
        personerFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        //initierar textfields för inmatning
        firstNameEdit();
        lastNameEdit();
        emailEdit();
        phoneEdit();
        streetEdit();
        postalCodeEdit();
        cityEdit();
        socialSecurityNoEdit();
        passwordEdit();
        loancardEdit();
        roleIdEdit();
        personerFormLayout.add(first_name, last_name,email,phone,street,
                postalCode,city,social_security_no, password, loancard,roleId );

        //Lägg till knappar
        personerEdit.add(actions);

        add(personerFormLayout, personerEdit);
// --------------------------------------------------------------------------------------------------------


        //TODO -> !!!
        /*
        personBinder.forField(loancard).withConverter(new StringToBooleanConverter("Must be true or false"))
                .bind(Person::getLoancard, Person::setLoancard);
         */

        //TODO -> !!! Lägg tillbaka ev???

        personBinder.forField(roleId).withConverter(new StringToIntegerConverter("Must be 1-5"))
                .bind(Person::getRoleId, Person::setRoleId);



        personBinder.bindInstanceFields(this);
        setSpacing(true);


        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        //Listener för save med bind ENTER
        addKeyPressListener(Key.ENTER, e -> saveCatcher());

        //Knyt actions knapparna
        save.addClickListener(e ->saveCatcher());
        delete.addClickListener(e->deletePerson(persons));
        cancel.addClickListener(e-> editPerson(persons));
        setVisible(false);
    }

    private void firstNameEdit(){
        first_name = new TextField();
        first_name.setLabel("First name");
        first_name.setPlaceholder("Enter first name");
        first_name.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Johan");
        first_name.setClearButtonVisible(true);
        first_name.setErrorMessage("Your first name needs to be at least one character long");
        first_name.setMinLength(1);
        first_name.setRequired(true);
    }

    private void lastNameEdit() {
        last_name = new TextField("Last Name");
        last_name.setPlaceholder("Enter last name");
        last_name.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Svensson");
        last_name.setClearButtonVisible(true);
        last_name.setErrorMessage("Your last name needs to be at least one character long");
        last_name.setMinLength(1);
    }

    private void emailEdit() {
        email = new EmailField("Email");
        email.setPlaceholder("Enter a email");
        email.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: johan.svensson@mail.se");
        email.setClearButtonVisible(true);
        email.setRequiredIndicatorVisible(true);
    }

    private void phoneEdit() {
        phone = new TextField("Phone");
        phone.setPlaceholder("Enter your phone number");
        phone.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 08 13579");
        phone.setClearButtonVisible(true);
    }

    private void streetEdit() {
        street = new TextField("Street");
        street.setPlaceholder("Street");
        street.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Stengatan 32");
        street.setClearButtonVisible(true);
    }

    private void postalCodeEdit() {
        postalCode = new TextField("Postal Code");
        postalCode.setPlaceholder("Postal Code");
        postalCode.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 13589");
        postalCode.setClearButtonVisible(true);
    }

    private void cityEdit() {
        city = new TextField("City");
        city.setPlaceholder("Enter a city");
        city.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Stockholm");
        city.setClearButtonVisible(true);
    }

    private void socialSecurityNoEdit() {
        social_security_no = new TextField("Social Security No");
        social_security_no.setPlaceholder("Social Security No");
        social_security_no.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 19901015");
        social_security_no.setClearButtonVisible(true);
    }

    private void passwordEdit() {
        password = new PasswordField("Password");
        password.setPlaceholder("Enter password");
        password.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: D45hide3");
        password.setClearButtonVisible(true);
        password.setRequiredIndicatorVisible(true);
    }


    private void loancardEdit() {
        loancard = new Checkbox("Loancard");
        loancard.setValue(false);
        loancard.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Rutan är i fylld så har personen ett lånekort\nOm rutan är tom så har personen inte något lånekort!");
        loancard.getElement().setText("Loancard: Checkt = Yes\nEmpty = No");
    }

    private void roleIdEdit() {
        /*
        ComboBox<Person> comboBox = new ComboBox<>();
        comboBox.setLabel("Department");
        List<Person> roleList = getRole();

// Choose which property from Department is the presentation value
        comboBox.setItemLabelGenerator(Role::getRole);
        comboBox.setItems(roleList);
        add(comboBox);
         */

        /*
        CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
        checkboxGroup.setLabel("Employee titles");
        checkboxGroup.setItems("Account Manager", "Designer",
                "Marketing Manager", "Developer");
        checkboxGroup.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);

        personBinder.forField(checkboxGroup)
                .asRequired("Please choose employee titles")
                .bind(Person::getRole_id, Person::setRole_id);

        Button button = new Button("Submit", event -> {
            if (personBinder.writeBeanIfValid(persons)) {
                Notification.show("Submit successful", 2000,
                        Notification.Position.MIDDLE);
            }
        });
        add(checkboxGroup, button);
        */



        roleId = new TextField("Role Id");
        roleId.setPlaceholder("Role Id");
        roleId.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE,
                "Example: \n1 = INACTIVE\n2 = ADMIN\n" +
                "3 = SUPERADMIN\n4 = USER NO ACCESS\n5 = USER ");
        roleId.setClearButtonVisible(true);
        roleId.setRequiredIndicatorVisible(true);
        roleId.setRequired(true);


    }



    void saveCatcher(){
        try{
            personBinder.writeBean(persons);
            savePerson(persons);
            Notification.show("You saved your person");
        } catch (ValidationException throwables){
            throwables.printStackTrace();
        }
    }


    void deletePerson(Person person){
        personRepository.delete(person);
        changeHandler.onChange();
    }

    void savePerson(Person person) {
        personRepository.save(person);
        changeHandler.onChange();
    }

    public final void editPerson(Person person){
        if (person == null){
            setVisible(false);
            return;
        }
        final boolean persisted = person.getIdPersons() != null;
        if (persisted){
            persons = personRepository.findById(person.getIdPersons()).get();
        } else {
            persons = person;
        }
        cancel.setVisible(persisted);
        personBinder.setBean(persons);
        setVisible(true);
        first_name.focus();
    }
}
