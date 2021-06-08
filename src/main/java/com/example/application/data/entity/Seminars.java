package com.example.application.data.entity;

import javax.persistence.*;
import java.util.Date;

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
    private String seatsBooked;

    @Column(name="date_time")
    private Date dateTime;

    @Column(name="active")
    private String active;


    public Seminars(){
    }

    public Seminars(String name,
                    String presenter,
                    String description,
                    String length,
                    String seatsBooked,
                    Date dateTime,
                    String active
                    ) {
        this.name = name;
        this.presenter = presenter;
        this.description = description;
        this.length = length;
        this.seatsBooked = seatsBooked;
        this.dateTime = dateTime;
        this.active = active;
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

    public String getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(String seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "Seminars{" +
                "id_seminar=" + id_seminar +
                ", name='" + name + '\'' +
                ", presenter='" + presenter + '\'' +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", seats_booked=" + seatsBooked +
                ", date_time=" + dateTime +
                ", active=" + active +
                '}';
    }
}