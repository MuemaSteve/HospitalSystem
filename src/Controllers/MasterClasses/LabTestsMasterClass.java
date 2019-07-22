package Controllers.MasterClasses;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.InputStream;

public class LabTestsMasterClass {
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty docName = new SimpleStringProperty();
    private SimpleStringProperty patientName = new SimpleStringProperty();
    private SimpleStringProperty tests = new SimpleStringProperty();
    private SimpleStringProperty technician = new SimpleStringProperty();
    private SimpleStringProperty results = new SimpleStringProperty();
    private SimpleObjectProperty<InputStream> resultImage = new SimpleObjectProperty<>();

    public String getResults() {
        return results.get();
    }

    public SimpleStringProperty resultsProperty() {
        return results;
    }

    public void setResults(String results) {
        this.results.set(results);
    }


    private SimpleStringProperty status = new SimpleStringProperty();
    public String getId() {
        return id.get();
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getDocName() {
        return docName.get();
    }

    public void setDocName(String docName) {
        this.docName.set(docName);
    }

    public SimpleStringProperty docNameProperty() {
        return docName;
    }

    public String getPatientName() {
        return patientName.get();
    }

    public void setPatientName(String patientName) {
        this.patientName.set(patientName);
    }

    public SimpleStringProperty patientNameProperty() {
        return patientName;
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

    public String getTechnician() {
        return technician.get();
    }

    public void setTechnician(String technician) {
        this.technician.set(technician);
    }

    public SimpleStringProperty technicianProperty() {
        return technician;
    }


    public InputStream getResultImage() {
        return resultImage.get();
    }

    public SimpleObjectProperty<InputStream> resultImageProperty() {
        return resultImage;
    }

    public void setResultImage(InputStream resultImage) {
        this.resultImage.set(resultImage);
    }
}
