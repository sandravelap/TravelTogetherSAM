package com.sanalberto.jlg.viajesapi.models;

import java.sql.Date;

public class ViajeModel {
    private int id;
    private int user_id;
    private Date init_date;
    private Date end_date;
    private int participants;
    private String trip_name;
    private String trip_description;
    private boolean tobacco;
    private String pet_configuration;

    public ViajeModel() {}

    public ViajeModel(int id, int user_id, Date init_date, Date end_date, int participants, String trip_name, String trip_description, boolean tobacco, String pet_configuration) {
        this.id = id;
        this.user_id = user_id;
        this.init_date = init_date;
        this.end_date = end_date;
        this.participants = participants;
        this.trip_name = trip_name;
        this.trip_description = trip_description;
        this.tobacco = tobacco;
        this.pet_configuration = pet_configuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getInit_date() {
        return init_date;
    }

    public void setInit_date(Date init_date) {
        this.init_date = init_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getTrip_description() {
        return trip_description;
    }

    public void setTrip_description(String trip_description) {
        this.trip_description = trip_description;
    }

    public boolean isTobacco() {
        return tobacco;
    }

    public void setTobacco(boolean tobacco) {
        this.tobacco = tobacco;
    }

    public String getPet_configuration() {
        return pet_configuration;
    }

    public void setPet_configuration(String pet_configuration) {
        this.pet_configuration = pet_configuration;
    }

    @Override
    public String toString() {
        return "HelloApplication{" +
            "id=" + id +
            ", user_id=" + user_id +
            ", init_date=" + init_date +
            ", end_date=" + end_date +
            ", participants=" + participants +
            ", trip_name='" + trip_name + '\'' +
            ", trip_description='" + trip_description + '\'' +
            ", tobacco=" + tobacco +
            ", pet_configuration='" + pet_configuration + '\'' +
            '}';
    }

    public boolean checkPetConfiguration(String pet_configuration){
        return (pet_configuration.equalsIgnoreCase("todas") || pet_configuration.equalsIgnoreCase("asistencia") || pet_configuration.equalsIgnoreCase("no"));
    }

}
