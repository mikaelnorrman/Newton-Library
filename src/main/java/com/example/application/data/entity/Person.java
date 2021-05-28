package com.example.application.data.entity;


import javax.persistence.*;
import javax.annotation.Nullable;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Table(name = "person", schema = "agile_library")
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_persons;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String street;

    private String postal_code;

    private String city;

    private String social_security_no;

    private String active_borrowed_books;

    private String total_borrowed_books;

    private String password;

    private Boolean loancard;

    private int role_id;


    @Transient
    private Role role;

    public Person() {
    }

    public Person(String first_name,
                  String last_name,
                  String email,
                  String phone,
                  String street,
                  String postal_code,
                  String city,
                  String social_security_no,
                  String password,
                  Boolean loancard,
                  Role role)
    {
        this.firstName = first_name;
        this.lastName = last_name;
        this.email = email;
        this.phone = phone;
        this.street = street;
        this.postal_code = postal_code;
        this.city = city;
        this.social_security_no = social_security_no;
        this.loancard = loancard;
        this.role_id = role.getRole_id();
    }

    public Integer getId_persons() {
        return id_persons;
    }

    public void setId_persons(Integer id_persons) {
        this.id_persons = id_persons;
    }

    public Role getRole() throws NoSuchElementException {
        if ( role == null ) {
            determineRole();
        }
        return role;
    }

    public void setRole(@Nullable Role role) {
        if ( role == null ) {
            determineRole();
        } else {
            this.role = role;
        }
    }

    private void determineRole() throws NoSuchElementException {
        this.role = Stream.of(Role.values()).filter(r -> r.getRole_id() == this.role_id).findFirst().get();
    }

    public int getRole_id() { return role_id; }
    public void setRole_id(int id_user_role) throws NoSuchElementException {
        this.role_id = id_user_role;
        determineRole();
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getPostal_code() { return postal_code; }
    public void setPostal_code(String postal_code) { this.postal_code = postal_code; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getSocial_security_no() { return social_security_no; }
    public void setSocial_security_no(String social_security_no) { this.social_security_no = social_security_no; }

    public String getActive_borrowed_books() { return active_borrowed_books; }
    public void setActive_borrowed_books(String active_borrowed_books) { this.active_borrowed_books = active_borrowed_books; }

    public String getTotal_borrowed_books() { return total_borrowed_books; }
    public void setTotal_borrowed_books(String total_borrowed_books) { this.total_borrowed_books = total_borrowed_books; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getLoancard() { return loancard; }
    public void setLoancard(Boolean loancard) { this.loancard = loancard; }

    public boolean checkPassword(String password) {
        return true; //FIXME
        //TODO: Implementera lösenordskontroll.
    }


}
