package Controllers;

import javafx.beans.property.SimpleStringProperty;

public class OriginMasterClass {
    private SimpleStringProperty id = new SimpleStringProperty();

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
