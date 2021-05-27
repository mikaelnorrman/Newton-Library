package com.example.application.editors;

import com.example.application.data.entity.Seminars;
import com.example.application.data.service.SeminarsRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SeminarEditor extends Editor {

    public static final String TITLE_IN_SET_ATTRIBUTE = "title";
    TextField name;
    TextField presenter;
    TextField description;
    TextField length;
    TextField seats_booked;
    DateTimePicker date_time;
    TextField date_added;
    TextField active;
    TextField id;


    @Autowired
    public SeminarEditor (SeminarsRepository seminarsRepo){
        this.seminarRepository = seminarsRepo;
        this.seminarsBinder = new Binder<>(Seminars.class);

// --------------------------------------------------------------------------------------------------------
        HorizontalLayout seminarEdit = new HorizontalLayout();
        FormLayout seminarFormLayout = new FormLayout();
        // Setting the desired responsive steps for the columns in the layout
        seminarFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("25em", 1),
                new FormLayout.ResponsiveStep("32em", 2),
                new FormLayout.ResponsiveStep("40em", 3));
        //initierar textfields för inmatning
        nameEdit();
        precenterEdit();
        descriptionEdit();
        lenghtEdit();
        seatsBookedEdit();
        dateTimeEdit();
        dateAddedEdit();
        activeEdit();
        idEdit();

        // TextField lägger till fälten att redigera datan i.
        seminarFormLayout.add(name, presenter, description, length, seats_booked,
                date_time, date_added, active, id);

        seminarEdit.add(actions);

        add(seminarFormLayout, seminarEdit);

// --------------------------------------------------------------------------------------------------------

//TODO - Fixa converteringen för date_time

        seminarsBinder.forField(date_time).withConverter(
                new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Seminars::getDate_time, Seminars::setDate_time);

        /*
        seminarsBinder.forField(date_time).withConverter(new StringToIntegerConverter("TESTAR")).
                bind(Seminars::getLength,Seminars::setLength);
        */


        seminarsBinder.bindInstanceFields(this);
        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        //Listener för save med bind ENTER
        addKeyPressListener(Key.ENTER, e -> saveCatcher());

        //Knyt actions knapparna
        save.addClickListener(e->saveCatcher());
        delete.addClickListener(e-> deleteSeminar(seminars));
        cancel.addClickListener(e-> editSeminar(seminars));
        setVisible(false);
    }

    private void nameEdit() {
        name = new TextField("Seminar name");
        name.setPlaceholder("Enter seminar name");
        name.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Yoga in nature");
        name.setClearButtonVisible(true);
        name.setErrorMessage("Your seminar name needs to be at least one character long");
        name.setMinLength(1);
        name.setRequired(true);
    }

    private void precenterEdit() {
        presenter = new TextField("Presenter");
        presenter.setPlaceholder("Enter a presenter");
        presenter.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Deborah Rademaker");
        presenter.setClearButtonVisible(true);
        presenter.setErrorMessage("Your description needs to be at least one character long");
        presenter.setMinLength(1);
    }

    private void descriptionEdit() {
        description = new TextField ("Description");
        description.setPlaceholder("Enter a description");
        description.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Experience the best places for yoga and becoming one with nature!");
        description.setClearButtonVisible(true);
        description.setErrorMessage("Your description needs to be at least one character long");
        description.setMinLength(1);
    }

    private void lenghtEdit() {
        length = new TextField ("Length");
        length.setPlaceholder("Enter a lenght");
        length.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 01:30:00");
        length.setClearButtonVisible(true);
        length.setErrorMessage("Your seminar lenght needs to be in -> hours : minutes : seconds");
        length.setMinLength(8);
    }

    private void seatsBookedEdit() {
        seats_booked = new TextField ("Seats Booked");
        seats_booked.setPlaceholder("Enter a seats booked");
        seats_booked.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 15");
        seats_booked.setClearButtonVisible(true);
        seats_booked.setErrorMessage("Your specify if there is seats booked");
        seats_booked.setMinLength(1);
    }

    private void dateTimeEdit() {
        date_time = new DateTimePicker("Date Time");
        LocalDateTime now = LocalDateTime.now();
        date_time.setValue(now);
        date_time.setErrorMessage("Your time needs tobe date and time");
    }

    private void dateAddedEdit() {
        date_added = new TextField ("Date Added");
        date_added.setPlaceholder("Enter a date");
        date_added.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 2021-05-01 15:00:00");
        date_added.setClearButtonVisible(true);
        date_added.setErrorMessage("Your date needs tobe both date and time");
        date_added.setMinLength(15);
    }

    private void activeEdit() {
        active = new TextField ("Active");
        active.setPlaceholder("Enter active");
        active.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 1 or 0");
        active.setClearButtonVisible(true);
        active.setErrorMessage("Your can only select 1 or 0");
        active.setMinLength(1);
        active.setMaxLength(1);
    }

    private void idEdit() {
        id = new TextField ("Id");
        id.setPlaceholder("Enter a id");
        id.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 15");
        id.setClearButtonVisible(true);
        id.setErrorMessage("You specify a id for there seminar");
        id.setMinLength(1);
    }

    void saveCatcher(){
        try{
            seminarsBinder.writeBean(seminars);
            saveSeminar(seminars);
            Notification.show("You saved your seminar");
        } catch (ValidationException throwables){
            throwables.printStackTrace();
        }
    }

    void deleteSeminar(Seminars seminars){
        seminarRepository.delete(seminars);
        changeHandler.onChange();
    }

    void saveSeminar(Seminars seminars) {
        seminarRepository.save(seminars);
        changeHandler.onChange();
    }


    public final void editSeminar(Seminars seminar){
        if (seminar == null){
            setVisible(false);
            return;
        }
        final boolean persisted = seminar.getId_seminar() != null;
        if (persisted){
            seminars = seminarRepository.findById(seminar.getId_seminar()).get();
        } else {
            seminars = seminar;
        }
        cancel.setVisible(persisted);
        seminarsBinder.setBean(seminar);
        setVisible(true);
        name.focus();
    }
}
