package Controllers.MasterClasses;

import javafx.beans.property.SimpleStringProperty;

public class HistoryMasterClass {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleStringProperty doctor = new SimpleStringProperty();
    private SimpleStringProperty ratings = new SimpleStringProperty();
    private SimpleStringProperty prescription = new SimpleStringProperty();
    private SimpleStringProperty tests = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDoctor() {
        return doctor.get();
    }

    public SimpleStringProperty doctorProperty() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
    }

    public String getRatings() {
        return ratings.get();
    }

    public SimpleStringProperty ratingsProperty() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings.set(ratings);
    }

    public String getPrescription() {
        return prescription.get();
    }

    public SimpleStringProperty prescriptionProperty() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription.set(prescription);
    }

    public String getTests() {
        return tests.get();
    }

    public SimpleStringProperty testsProperty() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests.set(tests);
    }


}
