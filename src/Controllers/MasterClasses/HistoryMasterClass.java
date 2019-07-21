package Controllers.MasterClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HistoryMasterClass {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleStringProperty doctor = new SimpleStringProperty();
    private SimpleStringProperty ratings = new SimpleStringProperty();
    private SimpleStringProperty prescription = new SimpleStringProperty();
    private SimpleStringProperty tests = new SimpleStringProperty();
    private SimpleIntegerProperty times = new SimpleIntegerProperty(0);

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public int getTimes() {
        return times.get();
    }

    public void setTimes(int times) {
        this.times.set(times);
    }

    public SimpleIntegerProperty timesProperty() {
        return times;
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public String getDoctor() {
        return doctor.get();
    }

    public void setDoctor(String doctor) {
        this.doctor.set(doctor);
    }

    public SimpleStringProperty doctorProperty() {
        return doctor;
    }

    public String getRatings() {
        return ratings.get();
    }

    public void setRatings(String ratings) {
        this.ratings.set(ratings);
    }

    public SimpleStringProperty ratingsProperty() {
        return ratings;
    }

    public String getPrescription() {
        return prescription.get();
    }

    public void setPrescription(String prescription) {
        this.prescription.set(prescription);
    }

    public SimpleStringProperty prescriptionProperty() {
        return prescription;
    }

    public String getTests() {
        return tests.get();
    }

    public void setTests(String tests) {
        this.tests.set(tests);
    }

    public SimpleStringProperty testsProperty() {
        return tests;
    }


}
