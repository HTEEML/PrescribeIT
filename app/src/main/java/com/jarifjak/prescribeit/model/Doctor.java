package com.jarifjak.prescribeit.model;

public class Doctor {

    private int id;
    private String firstName;
    private String lastName;
    private String details;
    private String appointmentDate;
    private String number;
    private String email;

    public Doctor() {

    }

    public Doctor(int id, String firstName, String lastName, String details, String appointmentDate, String number, String email) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.details = details;
        this.appointmentDate = appointmentDate;
        this.number = number;
        this.email = email;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
