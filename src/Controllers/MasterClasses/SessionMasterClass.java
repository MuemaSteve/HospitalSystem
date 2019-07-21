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

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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

    public String getPatientEmail() {
        return patientEmail.get();
    }

    public SimpleStringProperty patientEmailProperty() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail.set(patientEmail);
    }

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }


}
