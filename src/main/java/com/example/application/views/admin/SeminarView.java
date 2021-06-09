package com.example.application.views.admin;

import com.example.application.Connector.ConnectorMySQL;
import com.example.application.data.entity.Seminars;
import com.example.application.data.entity.AttendingSeminars;
import com.example.application.data.entity.Person;
import com.example.application.data.service.SeminarService;
import com.example.application.data.service.SeminarsRepository;
import com.example.application.data.service.AttendingSeminarsRepository;
import com.example.application.editors.AttendingSeminarsEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.sql.SQLException;

@PageTitle("Seminars")
@CssImport("./styles/views/admin/admin-view.css")
public class SeminarView extends Div {

    public static final int DURATION_NOTIFICATION = 4500;
    ConnectorMySQL connectorMySQL = new ConnectorMySQL();
    final AttendingSeminarsEditor attendingSeminarsEditor;

    private Grid<Seminars> grid;

    private AttendingSeminars attendingSeminars = new AttendingSeminars();

    private SeminarService seminarService;



    @Autowired
    public SeminarView(SeminarService seminarService, AttendingSeminarsRepository attendingSeminarsRepository, SeminarsRepository SeminarsRepository) {
        this.attendingSeminarsEditor = new AttendingSeminarsEditor(attendingSeminarsRepository);
        setSizeFull();
        setId("seminar-view");
        this.seminarService = seminarService;
        // Configure Grid - This will show up in the Grid
        this.grid = new Grid<>(Seminars.class);

        add(grid);
        grid.setColumns("name", "presenter", "length", "dateTime");
        grid.getColumnByKey("name").setAutoWidth(true);
        grid.getColumnByKey("presenter").setAutoWidth(true);
        grid.getColumnByKey("length").setAutoWidth(true);
        grid.getColumnByKey("dateTime").setAutoWidth(true);
        //grid.getColumnByKey("description").setWidth("150px").setFlexGrow(0);
        //grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.addComponentColumn(Seminar -> createAttendingButton(grid, Seminar));

        grid.setDataProvider(new CrudServiceDataProvider<Seminars, Void>(seminarService));

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        grid.setHeightFull();
        grid.setVisible(true);
        itemDetailsSeminars();

    }


// ----------------------------------------------------------------------------------------------------

    private Button createAttendingButton(Grid<Seminars> grid, Seminars item) {
        boolean checkLoancard = VaadinSession.getCurrent().getAttribute(Person.class).getLoancard() == true;    // koll så att användaren har ett lånekort.
        Integer idPersons = VaadinSession.getCurrent().getAttribute(Person.class).getIdPersons();               // hämta ut inloggade personens id.
        String firstNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getFirstName();         // hämta ut inloggade personens fistName.
        String lastNamePersons = VaadinSession.getCurrent().getAttribute(Person.class).getLastName();           // hämta ut inloggade personens lastName.

        Button attendButton = new Button("Book seminar",  editor  -> {
            Integer idOfSeminar = item.getId_seminar();
            String nameOfSeminar = item.getName();
            item.getId_seminar();
            item.getName();
            attendingSeminarsEditor.saveAttendingSeminar(new AttendingSeminars (nameOfSeminar, 0,idOfSeminar,idPersons,firstNamePersons,lastNamePersons));
            successAttendingSeminarNotification(item);
        });

        Button attendingButton = new Button ("Seminar booked!", editor -> {
            errorAttendingSeminarNotification(item);
        });

        Button noCardButton = new Button ("Loan Book", editor -> {
            attendingSeminarNotification(item, firstNamePersons, lastNamePersons);

        });

            if (checkLoancard) {
            try {
            if (!connectorMySQL.callattendingSeminar(idPersons,item.getId_seminar()))
            {
                attendButton.setDisableOnClick(true);
                attendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SMALL);
                return attendButton;
            } else {
                attendingButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_SMALL);
                return attendingButton;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    } else {
                attendButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_SMALL);
    }
        return noCardButton;
    }




    private void attendingSeminarNotification(Seminars item, String firstNamePersons, String lastNamePersons) {
        Notification loanedCardNotificationFail = new Notification(firstNamePersons + " " + lastNamePersons +
                "\nYou cant attend the seminar " + item.getName() + "\nYou need to get a SuperHero T-Shirt");
        loanedCardNotificationFail.addThemeVariants(NotificationVariant.LUMO_ERROR);
        loanedCardNotificationFail.setDuration(DURATION_NOTIFICATION);
        loanedCardNotificationFail.setPosition(Notification.Position.MIDDLE);
        loanedCardNotificationFail.open();
    }

    private void errorAttendingSeminarNotification(Seminars item) {
        Notification errorLoanedBookNotification = new Notification("You already attend the seminar \n" + item.getName());
        errorLoanedBookNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        errorLoanedBookNotification.setDuration(DURATION_NOTIFICATION);
        errorLoanedBookNotification.setPosition(Notification.Position.MIDDLE);
        errorLoanedBookNotification.open();
    }

    private void successAttendingSeminarNotification (Seminars item) {
        Notification successLoanedBookNotification = new Notification("You attending the seminar \n" + item.getName());
        successLoanedBookNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        successLoanedBookNotification.setDuration(DURATION_NOTIFICATION);
        successLoanedBookNotification.setPosition(Notification.Position.MIDDLE);
        successLoanedBookNotification.open();
    }

    private void itemDetailsSeminars() {
        grid.setItemDetailsRenderer(TemplateRenderer.<Seminars>of(
                "<div class='custom-details' style='border: 2px solid #1676f3; border-radius: 5px;"
                        + " padding: 10px 15px; width: 100%; box-sizing: border-box;'>"
                        + "<div>"
                        + "<H3 style='margin: 0 0 0.25em;'>[[item.name]]</H3>"
                        + "<H4 style='margin: 0 0 0.75em; font-style: italic; font-weight: 400;'>[[item.author]]</H4>"
                        + "<p style='margin: 0 0 0.75em;'>[[item.description]]</p>"
                        + "</div>")
                .withProperty("title", Seminars::getName)
                .withProperty("description", Seminars::getDescription)
                .withEventHandler("handleClick", seminars -> {
                    grid.getDataProvider().refreshItem(seminars);
                }));

        grid.setDetailsVisibleOnClick(true);
        //grid.addColumn(new NativeButtonRenderer<>("Details", item -> grid.setDetailsVisible(item, !grid.isDetailsVisible(item))));
    }
}