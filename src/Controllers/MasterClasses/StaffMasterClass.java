package Controllers.MasterClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class StaffMasterClass {
    private SimpleIntegerProperty size = new SimpleIntegerProperty(0);
    private SimpleStringProperty id=new SimpleStringProperty();
    private SimpleStringProperty name=new SimpleStringProperty();
    private SimpleStringProperty email=new SimpleStringProperty();
    private SimpleStringProperty identity=new SimpleStringProperty();
    private SimpleStringProperty branch=new SimpleStringProperty();
    private SimpleStringProperty status=new SimpleStringProperty();

    public int getSize() {
        return size.get();
    }

    public SimpleIntegerProperty sizeProperty() {
        return size;
    }

    public void setSize(int size) {
        this.size.set(size);
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

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getIdentity() {
        return identity.get();
    }

    public SimpleStringProperty identityProperty() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity.set(identity);
    }

    public String getBranch() {
        return branch.get();
    }

    public SimpleStringProperty branchProperty() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch.set(branch);
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



}
