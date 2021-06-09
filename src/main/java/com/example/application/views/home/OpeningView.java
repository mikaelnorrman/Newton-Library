package com.example.application.views.home;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Open hours")
@CssImport(value = "./styles/views/home/home-view.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class OpeningView extends VerticalLayout {


    public OpeningView() {
        setId("opening-view");
        addClassName("opening-view");
        setSizeFull();

        TextArea textArea = new TextArea();
        textArea.setWidth("400px");

        textArea.setValue("""
               Biblioteket har öppet följande tider:
               
               MÅNDAG      10:00 - 19:00
               TISDAG         10:00 - 19:00
               ONSDAG       10:00 - 19:00
               TORSDAG     10:00 - 19:00
               FREDAG        10:00 - 19:00
               LÖRDAG       10:00 - 16:00     
               SÖNDAG       10:00 - 16:00
               
               Kring helgdagar kan tiderna ändras.
               Kontakta personal för frågor.
               
               Tillgänglighet av information, alltid och var som helst!""");
        add(textArea);


    }
}