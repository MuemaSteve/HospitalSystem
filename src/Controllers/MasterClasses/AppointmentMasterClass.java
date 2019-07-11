package Controllers.MasterClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AppointmentMasterClass {
    private SimpleIntegerProperty size = new SimpleIntegerProperty(0);
    private SimpleStringProperty patientId = new SimpleStringProperty();
    private SimpleStringProperty doctorId = new SimpleStringProperty();

    private SimpleStringProperty type = new SimpleStringProperty();
    private SimpleStringProperty time = new SimpleStringProperty();
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();

    public String getPatientId() {
        return patientId.get();
    }

    public void setPatientId(String patientId) {
        this.patientId.set(patientId);
    }

    public SimpleStringProperty patientIdProperty() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId.get();
    }

    public void setDoctorId(String doctorId) {
        this.doctorId.set(doctorId);
    }

    public SimpleStringProperty doctorIdProperty() {
        return doctorId;
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


    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }


}
