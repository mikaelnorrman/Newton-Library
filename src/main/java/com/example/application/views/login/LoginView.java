package com.example.application.views.login;

import com.example.application.data.service.AuthService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;


@Route(value = "login")
@PageTitle("Login")
@CssImport("./styles/views/login/login-view.css")
@RouteAlias(value = "") // Gör så man login sidan startar upp direkt
public class LoginView extends Composite<LoginOverlay> {

    public LoginView(AuthService authService) {
        setId("login-view");

        LoginOverlay loginOverlay = getContent();
        loginOverlay.setTitle("Welcome to Office Space");
        loginOverlay.setDescription("Newton");
        loginOverlay.setOpened(true);

        loginOverlay.addLoginListener(event -> {try {
            authService.authenticate(event.getUsername(), event.getPassword());
            UI.getCurrent().navigate("home");
        } catch (AuthService.AuthException e) {
            e.printStackTrace();
                        Notification.show("Wrong credentials.");
        }
    });
        new RouterLink("Register", RegisterView.class); // This part is not showing up???
    }
}
