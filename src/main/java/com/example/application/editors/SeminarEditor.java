package com.example.application.editors;

import com.example.application.data.entity.Seminars;
import com.example.application.data.service.SeminarsRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;


public class SeminarEditor extends Editor {

    TextField name;
    TextField presenter;
    TextField description;
    TextField length;
    TextField seats_booked;
    TextField date_time;
    TextField date_added;
    TextField active;
    TextField id;


    @Autowired
    public SeminarEditor (SeminarsRepository seminarsRepo){
        this.seminarRepository = seminarsRepo;
        this.seminarsBinder = new Binder<>(Seminars.class);

        //initierar textfields för inmatning
        name = new TextField("Name");
        presenter = new TextField("Presenter");
        description = new TextField ("Description");
       /*
        length = new TextField ("Length");
        seats_booked = new TextField ("Seats Booked");
        date_time = new TextField ("Date Time");
        date_added = new TextField ("Date Added");
        active = new TextField ("Active");
        id = new TextField ("Id");

        */

        //Lägg till knappar
        add(name, presenter, description /*, length, seats_booked,
                date_time, date_added, active, id*/);

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

    void saveCatcher(){
        try{
            seminarsBinder.writeBean(seminars);
            saveSeminar(seminars);
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
