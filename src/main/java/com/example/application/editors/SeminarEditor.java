package com.example.application.editors;

import com.example.application.data.entity.Seminars;
import com.example.application.data.service.SeminarsRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.LocalDateTimeToDateConverter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class SeminarEditor extends Editor {

    public static final String TITLE_IN_SET_ATTRIBUTE = "title";
    public static final String NUMBERS_ONLY = "Numbers only. 0,1,2,3,4,5,6,7,8,9";
    TextField name;
    TextField presenter;
    TextField description;
    TextField length;
    TextField seats_booked;
    DateTimePicker date_time;
    //TextField date_added;
    TextField active;


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
        //dateAddedEdit();
        activeEdit();


        // TextField lägger till fälten att redigera datan i.
        seminarFormLayout.add(name, presenter, description, length, seats_booked,
                date_time, /*date_added,*/ active);

        seminarEdit.add(actions);

        add(seminarFormLayout, seminarEdit);

// --------------------------------------------------------------------------------------------------------

//TODO - Fixa converteringen för date_time

        seminarsBinder.forField(date_time).withConverter(
                new LocalDateTimeToDateConverter(ZoneId.systemDefault()))
                .bind(Seminars::getDateTime, Seminars::setDateTime);


        /* // TODO -> Fixa
        seminarsBinder.forField(length).withConverter(
                new TimePicker()).
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
        name.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void precenterEdit() {
        presenter = new TextField("Presenter");
        presenter.setPlaceholder("Enter a presenter");
        presenter.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Deborah Rademaker");
        presenter.setClearButtonVisible(true);
        presenter.setErrorMessage("Your description needs to be at least one character long");
        presenter.setMinLength(1);
        presenter.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void descriptionEdit() {
        description = new TextField ("Description");
        description.setPlaceholder("Enter a description");
        description.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: Experience the best places for yoga and becoming one with nature!");
        description.setClearButtonVisible(true);
        description.setErrorMessage("Your description needs to be at least one character long");
        description.setMinLength(1);
        description.setMaxLength(1000);
        description.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void lenghtEdit() {
        length = new TextField ("Length");
        length.setPlaceholder("Enter a lenght");
        length.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 01:30:00");
        length.setPattern("\\d{2}\\:\\d{2}\\:\\d{2}");
        length.setErrorMessage(NUMBERS_ONLY);
        length.setClearButtonVisible(true);
        length.setErrorMessage(NUMBERS_ONLY + "\n And : \nYour seminar lenght needs to be in -> hours : minutes : seconds");
        length.setMinLength(8);
        length.addThemeVariants(TextFieldVariant.LUMO_SMALL);


    }
//TODO kolla upp patterns
    private void seatsBookedEdit() {
        seats_booked = new TextField ("Seats Booked");
        seats_booked.setPlaceholder("Enter a seats booked");
        seats_booked.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, " " + "Example: 15" + NUMBERS_ONLY);
        seats_booked.setClearButtonVisible(true);
        //seats_booked.setPattern("\\d{1,2,3}");
        seats_booked.setErrorMessage("Your specify if there is seats booked" + NUMBERS_ONLY);
        seats_booked.setMinLength(1);
        seats_booked.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }

    private void dateTimeEdit() {
        date_time = new DateTimePicker("Date Time");
        LocalDateTime now = LocalDateTime.now();
        date_time.setValue(now);
        date_time.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "You need to pick a date and a time for the seminar");
        date_time.setErrorMessage("Your time needs tobe date and time");
    }


    private void activeEdit() {
        active = new TextField ("Active");
        active.setPlaceholder("Enter active");
        active.getElement().setAttribute(TITLE_IN_SET_ATTRIBUTE, "Example: 1 or 0\n0 = Not Avtive\n1 = Active");
        active.setClearButtonVisible(true);
        active.setErrorMessage("Your can only select 1 or 0");
        active.setMinLength(1);
        active.setMaxLength(1);
        active.addThemeVariants(TextFieldVariant.LUMO_SMALL);
    }


    void saveCatcher(){
        try{
            seminarsBinder.writeBean(seminars);
            saveSeminar(seminars);
            saveNotification();
        } catch (ValidationException throwables){
            throwables.printStackTrace();
        }
    }

    private void saveNotification() {
        Notification savePersonNotification = new Notification("You saved your seminar");
        savePersonNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        savePersonNotification.setDuration(4000);
        savePersonNotification.setPosition(Notification.Position.MIDDLE);
        savePersonNotification.open();
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
