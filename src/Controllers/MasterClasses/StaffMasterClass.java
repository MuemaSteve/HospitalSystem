package Controllers.MasterClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StaffMasterClass {
    private SimpleIntegerProperty size = new SimpleIntegerProperty(0);
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty identity = new SimpleStringProperty();
    private SimpleStringProperty branch = new SimpleStringProperty();
    private SimpleStringProperty status = new SimpleStringProperty();

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

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public String getIdentity() {
        return identity.get();
    }

    public void setIdentity(String identity) {
        this.identity.set(identity);
    }

    public SimpleStringProperty identityProperty() {
        return identity;
    }

    public String getBranch() {
        return branch.get();
    }

    public void setBranch(String branch) {
        this.branch.set(branch);
    }

    public SimpleStringProperty branchProperty() {
        return branch;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }


}
