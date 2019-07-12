package Controllers.MasterClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ConditionsMasterClass {
    private SimpleIntegerProperty size = new SimpleIntegerProperty(0);
    private SimpleStringProperty patientId = new SimpleStringProperty();
    private SimpleStringProperty patientemail = new SimpleStringProperty();
    private SimpleStringProperty conditionName = new SimpleStringProperty();
    private SimpleStringProperty date = new SimpleStringProperty();
    private SimpleStringProperty category = new SimpleStringProperty();
    private SimpleStringProperty doctor = new SimpleStringProperty();

    public String getConditionName() {
        return conditionName.get();
    }

    public SimpleStringProperty conditionNameProperty() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName.set(conditionName);
    }


    public String getPatientId() {
        return patientId.get();
    }

    public int getSize() {
        return size.get();
    }

    public SimpleIntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public SimpleStringProperty patientIdProperty() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId.set(patientId);
    }

    public String getPatientemail() {
        return patientemail.get();
    }

    public SimpleStringProperty patientemailProperty() {
        return patientemail;
    }

    public void setPatientemail(String patientemail) {
        this.patientemail.set(patientemail);
    }

    public String getPatientName() {
        return conditionName.get();
    }

    public SimpleStringProperty patientNameProperty() {
        return conditionName;
    }

    public void setPatientName(String patientName) {
        this.conditionName.set(patientName);
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

    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
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

}
