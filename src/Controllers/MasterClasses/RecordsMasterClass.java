package Controllers.MasterClasses;

import javafx.beans.property.SimpleStringProperty;

public class RecordsMasterClass {
    //    name,email,phonenumber
    private SimpleStringProperty id = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty phonenumber = new SimpleStringProperty();
    private int size = 0;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public String getPhonenumber() {
        return phonenumber.get();
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber.set(phonenumber);
    }

    public SimpleStringProperty phonenumberProperty() {
        return phonenumber;
    }
}
