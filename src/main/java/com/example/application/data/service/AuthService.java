package com.example.application.data.service;

import com.example.application.data.entity.Person;
import com.example.application.data.entity.Role;
import com.example.application.views.admin.AddBookAdminView;
import com.example.application.views.admin.BookAdminView;
import com.example.application.views.admin.PersonAdminView;
import com.example.application.views.admin.SeminarAdminView;
import com.example.application.views.home.HomeView;
import com.example.application.views.logout.LogoutView;
import com.example.application.views.main.MainViewLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    // Här sätter länkar vi till @PageTitle("Book AdminView")
    public static final String HOME = "home";
    public static final String SEARCH = "search";
    public static final String LOGOUT = "logout";
    public static final String ADMIN = "admin";
    public static final String seminarAdminView = "seminarAdminView";
    public static final String bookAdminView = "bookAdminView";
    public static final String addBookAdminView = "addBookAdminView";

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

        /**
         * HÄr sätter vi namnen på flikarna i menyen.
         */
        var home = "Home";
        var logout = "Logout";
        var userAdmin = "User";
        var admin = "Admin";
        var bookAdmin = "Books";
        var addBookAdmin = "Add Book & Search";
        var seminarAdmin = "Seminar";

        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute(HOME, home, HomeView.class));
            routes.add(new AuthorizedRoute(LOGOUT, logout, LogoutView.class));

        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute(HOME, home, HomeView.class));
            routes.add(new AuthorizedRoute(bookAdminView,bookAdmin, BookAdminView.class));
            routes.add(new AuthorizedRoute(addBookAdminView,addBookAdmin, AddBookAdminView.class));
            routes.add(new AuthorizedRoute(seminarAdminView,seminarAdmin, SeminarAdminView.class));
            routes.add(new AuthorizedRoute(ADMIN, userAdmin, PersonAdminView.class));
            routes.add(new AuthorizedRoute(LOGOUT, logout, LogoutView.class));

        } else if (role.equals(Role.SUPERADMIN)) {
            routes.add(new AuthorizedRoute(HOME, home, HomeView.class));
            routes.add(new AuthorizedRoute(ADMIN, admin, PersonAdminView.class));
            routes.add(new AuthorizedRoute(LOGOUT, logout, LogoutView.class));
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
