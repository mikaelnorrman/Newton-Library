package com.example.application.editors;

import com.example.application.data.entity.Person;
import com.example.application.data.service.PersonRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToBooleanConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

public class PersonEditor extends Editor {

    public static final String TITLE_IN_SET_ATTRIBUTE = "title";
    TextField first_name;
    TextField last_name;
    TextField email;
    TextField phone;
    TextField street;
    TextField postalCode;
    TextField city;
    TextField social_security_no;
    TextField active_borrowed_books;
    TextField total_borrowed_books;
    TextField password;
    TextField date_added;
    TextField loancard;
    TextField role_id;


    public PersonEditor(PersonRepository personRepository) {
        this.personRepository = personRepository;
        this.personBinder = new Binder<>(Person.class);

        firstNameEdit();
        last_name = new TextField("Last Name");
        email = new TextField("Email");
        phone = new TextField("Phone");
        street = new TextField("Street");
        postalCode = new TextField("Postal Code");
        city = new TextField("City");
        social_security_no = new TextField("Social Security No");
        active_borrowed_books = new TextField("Active Borrowed Books");
        total_borrowed_books = new TextField("Total Borrowed Books");
        password = new TextField("Password");
        date_added = new TextField("Date Added");
        loancard = new TextField("Loancard");
        role_id = new TextField("Role Id");

        HorizontalLayout personerEdit = new HorizontalLayout();

        //TODO eventuellt skapa en FormLayout

        //Lägg till knappar
        personerEdit.add(first_name, last_name,email,phone, street,postalCode,city,social_security_no,
                active_borrowed_books,total_borrowed_books,password,date_added,loancard,role_id, actions);

        add(personerEdit);


        //TODO -> !!!
        personBinder.forField(loancard).withConverter(new StringToBooleanConverter("Must be true or false"))
                .bind(Person::getLoancard, Person::setLoancard);
        personBinder.forField(role_id).withConverter(new StringToIntegerConverter("Must be 1-5"))
                .bind(Person::getRole_id, Person::setRole_id);
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



    void saveCatcher(){
        try{
            personBinder.writeBean(persons);
            savePerson(persons);
            Notification.show("YOu saved your person");
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
        final boolean persisted = person.getId_person() != null;
        if (persisted){
            persons = personRepository.findById(person.getId_person()).get();
        } else {
            persons = person;
        }
        cancel.setVisible(persisted);
        personBinder.setBean(persons);
        setVisible(true);
        first_name.focus();
    }
}
