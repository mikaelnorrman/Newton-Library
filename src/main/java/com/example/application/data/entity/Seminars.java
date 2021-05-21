package com.example.application.data.entity;

import io.swagger.models.auth.In;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Seminars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_seminar;

    private String name = "";
    private String presenter = "";
    private String description = "";
    /*private Integer length;
    private Integer seats_booked;
    private Integer date_time;
    private Integer date_added;
    private Integer active;
    */
    @NotNull
    private Integer id;



    public Seminars(Integer id_seminar, String name, String presenter,
                    String description /*, Integer length, Integer seats_booked,
                    Integer date_time, Integer date_added, Integer active */,
                    Integer id) {
        this.id_seminar = id_seminar;
        this.name = name;
        this.presenter = presenter;
        this.description = description;
        /*
        this.length = length;
        this.seats_booked = seats_booked;
        this.date_time = date_time;
        this.date_added = date_added;
        this.active = active;

         */
        this.id = id;


    }

    public Seminars(){

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

    /*
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getSeats_booked() {
        return seats_booked;
    }

    public void setSeats_booked(Integer seats_booked) {
        this.seats_booked = seats_booked;
    }

    public Integer getDate_time() {
        return date_time;
    }

    public void setDate_time(Integer date_time) {
        this.date_time = date_time;
    }

    public Integer getDate_added() {
        return date_added;
    }

    public void setDate_added(Integer date_added) {
        this.date_added = date_added;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "Seminars{" +
                "id_seminar=" + id_seminar +
                ", name='" + name + '\'' +
                ", presenter='" + presenter + '\'' +
                ", description='" + description + '\'' +
                 /*", length=" + length +
                ", seats_booked=" + seats_booked +
                ", date_time=" + date_time +
                ", date_added=" + date_added +
                ", active=" + active + */
                ", id=" + id +
                '}';
    }
}
