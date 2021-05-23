package com.example.application.data.service;

import com.example.application.data.entity.Person;
import com.example.application.data.entity.Role;
import com.example.application.views.admin.AdminSearchView;
import com.example.application.views.admin.PersonAdminView;
import com.example.application.views.home.HomeView;
import com.example.application.views.logout.LogoutView;
import com.example.application.views.main.MainViewLayout;
import com.example.application.views.search.SearchView;
import com.example.application.views.search.VisitorBookSearchView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) { }

    public class AuthException extends Exception { }

    private final PersonRepository personRepository;

    public AuthService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    //TODO - ta bort && user.isActive()
    public void authenticate(String email, String password) throws AuthException {
        List<Person> personList = personRepository.findByEmailIgnoreCase(email);
        if (personList.size() != 1) {
            throw new AuthException();
        }
        Person person = personList.get(0);
        if (person != null && person.checkPassword(password)) {
            VaadinSession.getCurrent().setAttribute(Person.class, person);
            createRoutes(person.getRole());
        } else {
            throw new AuthException();
        }
    }

    private void createRoutes(Role role) {
        getAuthorizedRoutes(role).stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute(
                                route.route, route.view, MainViewLayout.class));
    }


    /**
     * Här vill vi skapa olika routes baserade på vilken role användaren har.
     * @param role
     * @return
     */
    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        var routes = new ArrayList<AuthorizedRoute>();

        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("search", "Search", SearchView.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));

        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("search", "Search", SearchView.class));
            routes.add(new AuthorizedRoute("admin", "User Admin", PersonAdminView.class));
            routes.add(new AuthorizedRoute("visitor", "Search Books", VisitorBookSearchView.class)); //TEST
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));

        } else if (role.equals(Role.SUPERADMIN)) {
            routes.add(new AuthorizedRoute("home", "Home", HomeView.class));
            routes.add(new AuthorizedRoute("search", "Search", SearchView.class));
            routes.add(new AuthorizedRoute("admin search", "Admin Search", AdminSearchView.class));
            routes.add(new AuthorizedRoute("admin", "Admin", PersonAdminView.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        }

        return routes;
    }

    /*public void register(String email, String password) {
        User user = userRepository.save(new User(email, password));
        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@example.com");
        message.setSubject("Confirmation email");
        message.setText(text);
        message.setTo(email);
        mailSender.send(message);
    }*/

    /*public void activate(String activationCode) throws AuthException {
        User user = userRepository.getByActivationCode(activationCode);
        if (user != null) {
            user.setActive(true);
            userRepository.save(user);
        } else {
            throw new AuthException();
        }
    }*/

}
