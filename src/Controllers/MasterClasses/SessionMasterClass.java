package Controllers.MasterClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SessionMasterClass {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty size = new SimpleIntegerProperty(0);
    private SimpleStringProperty patientEmail = new SimpleStringProperty();
    private SimpleStringProperty id = new SimpleStringProperty();

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getSize() {
        return size.get();
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public SimpleIntegerProperty sizeProperty() {
        return size;
    }

    public String getPatientEmail() {
        return patientEmail.get();
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail.set(patientEmail);
    }

    public SimpleStringProperty patientEmailProperty() {
        return patientEmail;
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
    }


}
