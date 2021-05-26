package com.example.application.views.home;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;

import java.util.Arrays;
import java.util.List;

@PageTitle("Opening")
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
               MÅNDAG      10:00 - 19:00
               TISDAG         10:00 - 19:00
               ONSDAG       10:00 - 19:00
               TORSDAG     10:00 - 19:00
               FREDAG        10:00 - 19:00
               LÖRDAG       10:00 - 16:00     
               SÖNDAG       10:00 - 16:00""");
        add(textArea);

    }
}