package com.example.application.views.admin;

import com.example.application.data.entity.Books;
import com.example.application.data.entity.Seminars;
import com.example.application.data.service.SeminarService;
import com.example.application.data.service.SeminarsRepository;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Seminars")
@CssImport("./styles/views/admin/admin-view.css")
public class SeminarView extends Div {

    public static final String TITLE_IN_SET_ATTRIBUTE = "title";
    private Grid<Seminars> grid;
    private SeminarService seminarService;
    private final SeminarsRepository seminarsRepository;



    public SeminarView(@Autowired SeminarService seminarService, SeminarsRepository seminarsRepository) {
        setId("seminar-view");
        setSizeFull();
        // Configure Grid - This will show up in the Grid
        this.grid = new Grid<>(Seminars.class);
        this.seminarService = seminarService;
        this.seminarsRepository = seminarsRepository;

        add(grid);
        grid.setColumns("name", "presenter", "description", "length", "dateTime");
        grid.getColumnByKey("name").setAutoWidth(true);
        grid.getColumnByKey("presenter").setAutoWidth(true);
        grid.getColumnByKey("length").setAutoWidth(true);
        grid.getColumnByKey("dateTime").setAutoWidth(true);
        grid.getColumnByKey("description").setWidth("150px").setFlexGrow(0);
        //grid.getColumns().forEach(column -> column.setAutoWidth(true));
        //grid.setDataProvider(new CrudServiceDataProvider<Seminars, Void>(seminarService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
        grid.setHeightFull();
        grid.setItems();
        grid.setVisible(true);
        //listSeminars();
        itemDetailsSeminars();

    }
    void listSeminars(){
        grid.setItems(seminarsRepository.findAll());
    }

    private void itemDetailsSeminars() {
        grid.setItemDetailsRenderer(TemplateRenderer.<Books>of(
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