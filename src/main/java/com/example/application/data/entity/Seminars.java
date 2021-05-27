package com.example.application.data.entity;


import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "seminars", schema = "agile_library")
@Entity
public class Seminars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_seminar;

    @Column(name="name")
    private String name = "";

    @Column(name="presenter")
    private String presenter = "";

    @Column(name="description")
    private String description = "";

    @Column(name="length")
    private String length;

    @Column(name="seats_booked")
    private String seats_booked;

    @Column(name="date_time")
    private String date_time;

    @Column(name="active")
    private String active;

    @NotNull
    @Column(name="id")
    private String id;



    public Seminars(){
    }

    public Seminars(String name,
                    String presenter,
                    String description,
                    String length,
                    String seats_booked,
                    String date_time,
                    String active,
                    String id) {
        this.name = name;
        this.presenter = presenter;
        this.description = description;
        this.length = length;
        this.seats_booked = seats_booked;
        this.date_time = date_time;
        this.active = active;
        this.id = id;
    }

    public Integer getId_seminar() {
        return id_seminar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getSeats_booked() {
        return seats_booked;
    }

    public void setSeats_booked(String seats_booked) {
        this.seats_booked = seats_booked;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Seminars{" +
                "id_seminar=" + id_seminar +
                ", name='" + name + '\'' +
                ", presenter='" + presenter + '\'' +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", seats_booked=" + seats_booked +
                ", date_time=" + date_time +
                ", active=" + active +
                ", id=" + id +
                '}';
    }
}