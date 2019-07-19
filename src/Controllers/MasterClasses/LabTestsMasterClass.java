package Controllers.MasterClasses;

import javafx.beans.property.SimpleStringProperty;

public class LabTestsMasterClass {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty docName = new SimpleStringProperty();
    private SimpleStringProperty patientName = new SimpleStringProperty();
    private SimpleStringProperty tests = new SimpleStringProperty();
    private SimpleStringProperty technician = new SimpleStringProperty();

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getDocName() {
        return docName.get();
    }

    public SimpleStringProperty docNameProperty() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName.set(docName);
    }

    public String getPatientName() {
        return patientName.get();
    }

    public SimpleStringProperty patientNameProperty() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
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

    public String getTechnician() {
        return technician.get();
    }

    public SimpleStringProperty technicianProperty() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician.set(technician);
    }


}
