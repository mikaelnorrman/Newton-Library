package com.example.application.views.search;

import com.example.application.views.main.MainViewLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

//@Route(value = "search") // -> Kan användas för att skapa en öppen view
@PageTitle("Search")
@CssImport(value = "./styles/views/search/search-view.css")
@JsModule("@vaadin/vaadin-lumo-styles/badge.js")
public class SearchView extends Div {

    private TextField name;
    private Button sayHello;

    public SearchView() {
        setId("search-view");
        addClassName("search-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setSizeFull(FlexComponent.Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

    private void setSizeFull(FlexComponent.Alignment end, TextField name, Button sayHello) {

    }

}
