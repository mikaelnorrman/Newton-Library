package com.example.application.data.service;

import com.example.application.data.entity.Person;
import com.example.application.data.entity.Role;
import com.example.application.views.admin.*;
import com.example.application.views.home.OpeningView;
import com.example.application.views.logout.LogoutView;
import com.example.application.views.main.MainViewLayout;
import com.example.application.views.search.SearchView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    // Här sätter vi länkarna till @PageTitle("Book AdminView")
    public static final String OPENING = "opening";
    public static final String SEARCH = "search";
    public static final String LOGOUT = "logout";
    public static final String ADMIN = "admin";
    public static final String SEMINARS = "seminar";
    public static final String BOOKS = "books";
    public static final String USER_BOOKS = "userBooks";
    public static final String ADD_BOOK_VIEW = "addBookView";
    public static final String ADD_SEMINAR_VIEW = "addSeminarView";
    public static final String PERSONS = "persons";
    public static final String LOANED_BOOKS_VIEW = "loanedBooks";

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) { }

    public class AuthException extends Exception { }

    private final PersonRepository personRepository;

    public AuthService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    //TODO - ta bort && user.isActive()
    public void authenticate(String email, String password) throws AuthException {
        List<Person> personList = personRepository.findByEmailStartsWithIgnoreCase(email);
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
                                route.route, route.view, MainViewLayout.class)); //TODO - Kolla upp!!!
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
        var opening = "Opening";
        var logout = "Logout";
        var userAdmin = "Users";
        var admin = "Admin";
        var bookAdmin = "Books";
        var userBook = "UserBooks";
        var addBooks = "Add Books";
        var loanedBooks = "Loaned Books";
        var persons = "Add Users";
        var seminars = "Seminars";
        var search = "Search";
        var addSeminars = "Add Seminars";

        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute(OPENING, opening, OpeningView.class));
            routes.add(new AuthorizedRoute(SEARCH, search, SearchView.class));
            routes.add(new AuthorizedRoute(BOOKS,bookAdmin, BookView.class));
            routes.add(new AuthorizedRoute(SEMINARS,seminars, SeminarView.class));
            routes.add(new AuthorizedRoute(LOGOUT, logout, LogoutView.class));

        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute(OPENING, opening, OpeningView.class));
            routes.add(new AuthorizedRoute(SEARCH, search, SearchView.class));
            routes.add(new AuthorizedRoute(ADD_BOOK_VIEW,addBooks, AddBookView.class));
            routes.add(new AuthorizedRoute(LOANED_BOOKS_VIEW,loanedBooks, LoanedBooksView.class));
            routes.add(new AuthorizedRoute(ADD_SEMINAR_VIEW,addSeminars, AddSeminarView.class));
            routes.add(new AuthorizedRoute(PERSONS,persons, AddPersonView.class));
            routes.add(new AuthorizedRoute(LOGOUT, logout, LogoutView.class));

        } else if (role.equals(Role.SUPERADMIN)) {
            routes.add(new AuthorizedRoute(OPENING, opening, OpeningView.class));
            routes.add(new AuthorizedRoute(SEARCH, search, SearchView.class));
            routes.add(new AuthorizedRoute(BOOKS,bookAdmin, BookView.class));
            routes.add(new AuthorizedRoute(ADD_BOOK_VIEW,addBooks, AddBookView.class));
            routes.add(new AuthorizedRoute(LOANED_BOOKS_VIEW,loanedBooks, LoanedBooksView.class));
            routes.add(new AuthorizedRoute(SEMINARS,seminars, SeminarView.class));
            routes.add(new AuthorizedRoute(ADD_SEMINAR_VIEW,addSeminars, AddSeminarView.class));
            routes.add(new AuthorizedRoute(ADMIN, userAdmin, PersonView.class));
            routes.add(new AuthorizedRoute(PERSONS,persons, AddPersonView.class));
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
