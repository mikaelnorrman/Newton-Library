package com.example.application.editors;

import com.example.application.data.entity.AttendingSeminars;
import com.example.application.data.service.AttendingSeminarsRepository;
import com.vaadin.flow.data.binder.Binder;

public class AttendingSeminarsEditor extends Editor {


    public AttendingSeminarsEditor(AttendingSeminarsRepository attendingSeminarsRepository) {
        this.attendingSeminarsRepository = attendingSeminarsRepository;
        this.attendingSeminarsBinder = new Binder<>(AttendingSeminars.class);
    }

    public void saveAttendingSeminar(AttendingSeminars attendingSeminars){
        attendingSeminarsRepository.save(attendingSeminars);
    }

}
