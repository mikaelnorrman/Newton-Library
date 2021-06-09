package com.example.application.data.entity;

import javax.persistence.*;

@Table(name = "attending_seminars", schema = "agile_library")
@Entity
public class AttendingSeminars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attending")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "attending")
    private String attending;

    @Column(name = "expired")
    private Integer expired;


    @Column(name = "seminars_id_seminars")
    private Integer seminars_id_seminars;

    @Column(name = "users_id_users")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public AttendingSeminars(){
    }

    public AttendingSeminars(String name,
                             Integer expired,
                             Integer seminars_id_seminars,
                             Integer users_id_users,
                             String firstName,
                             String lastName) {
        this.name = name;
        this.expired = expired;
        this.seminars_id_seminars = seminars_id_seminars;
        this.userId = users_id_users;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public Integer getSeminars_id_seminars() {
        return seminars_id_seminars;
    }

    public void setSeminars_id_seminars(Integer seminars_id_seminars) {
        this.seminars_id_seminars = seminars_id_seminars;
    }

    public Integer getUsers_id_users() {
        return userId;
    }

    public void setId_users(Integer users_id_users) {
        this.userId = users_id_users;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "AttendingSeminars{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", attending='" + attending + '\'' +
                ", expired=" + expired +
                ", id_seminars=" + seminars_id_seminars +
                ", id_users=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
