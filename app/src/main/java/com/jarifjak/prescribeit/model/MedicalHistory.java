package com.jarifjak.prescribeit.model;

public class MedicalHistory {

    private String imagePath;
    private String doctorName;
    private String details;
    private String date;

    public MedicalHistory() {

    }

    public MedicalHistory(String imagePath, String doctorName, String details, String date) {

        this.imagePath = imagePath;
        this.doctorName = doctorName;
        this.details = details;
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
