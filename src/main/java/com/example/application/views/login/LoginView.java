package com.example.application.views.login;

import com.example.application.data.service.AuthService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.*;

@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
@RouteAlias(value = "") // Gör så man login sidan startar upp direkt
public class LoginView extends Composite<LoginOverlay> {

    public LoginView(AuthService authService) {
        setId("login-view");

        LoginOverlay loginOverlay = getContent();
        loginOverlay.setTitle("Welcome");
        loginOverlay.setDescription("LibSys");
        loginOverlay.setOpened(true);

        loginOverlay.addLoginListener(event -> {try {
            authService.authenticate(event.getUsername(), event.getPassword());
            loginOverlay.close();
            UI.getCurrent().navigate("opening");
        } catch (AuthService.AuthException e) {
            e.printStackTrace();
            Notification.show("Wrong credentials.");
            loginOverlay.setError(true);
        }
        });


        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setForgotPassword("Search books");
        i18n.setAdditionalInformation("You can search for books in Libsys library here!");
        loginOverlay.setI18n(i18n);
        loginOverlay.addForgotPasswordListener(e -> {
            loginOverlay.close();
            UI.getCurrent().navigate("visitor");
        });
    }
}