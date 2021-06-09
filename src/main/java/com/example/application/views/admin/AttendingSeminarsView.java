package com.example.application.views.admin;

import com.example.application.data.entity.AttendingSeminars;
import com.example.application.data.entity.Person;
import com.example.application.data.service.AttendingSeminarsRepository;
import com.example.application.data.service.AttendingSeminarsService;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;

@PageTitle("Attending Seminars")
@CssImport("./styles/views/admin/admin-view.css")
public class AttendingSeminarsView extends Div {

    private Grid<AttendingSeminars> grid;
    private Grid<AttendingSeminars> gridAdmin;
    private AttendingSeminarsService attendingService;
    private final AttendingSeminarsRepository repository;

    public AttendingSeminarsView(AttendingSeminarsService attendingService, AttendingSeminarsRepository attendingSeminarsRepository) throws SQLException {
        setId("attending-seminars-view");
        addClassName("attending-seminars-view");
        setSizeFull();
        this.attendingService = attendingService;
        this.repository = attendingSeminarsRepository;
        this.grid = new Grid<>(AttendingSeminars.class);
        this.gridAdmin = new Grid<>(AttendingSeminars.class);

        add(grid);
        grid.setColumns("name", "expired");
        grid.getColumnByKey("name").setAutoWidth(true);
        grid.getColumnByKey("expired").setAutoWidth(true);
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();
        grid.setVisible(true);


        add(gridAdmin);
        gridAdmin.setColumns("name", "date", "expired", "firstName", "lastName");
        gridAdmin.getColumnByKey("name").setAutoWidth(true);
        gridAdmin.getColumnByKey("date").setAutoWidth(true);
        gridAdmin.getColumnByKey("expired").setAutoWidth(true);
        gridAdmin.getColumnByKey("firstName").setAutoWidth(true);
        gridAdmin.getColumnByKey("lastName").setAutoWidth(true);
        gridAdmin.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        gridAdmin.setHeightFull();
        gridAdmin.setVisible(true);

        Integer roleIdKoll = VaadinSession.getCurrent().getAttribute(Person.class).getRoleId();

        if (roleIdKoll == 2 || roleIdKoll == 3) {
            listAllSeminars();
        } else {
            listSeminars(VaadinSession.getCurrent().getAttribute(Person.class).getIdPersons());
        }
    }

   void listSeminars(Integer id) {
       grid.setItems(repository.findByUserId(id));
    }


    void findExpiredSeminars(Integer id){
        grid.setItems(repository.findByUserId(id));
    }

    void listAllSeminars(){
        grid.setVisible(false);
        gridAdmin.setItems(repository.findAll());
        gridAdmin.setVisible(true);
    }
}
