package Controllers;

import javafx.beans.property.SimpleStringProperty;

public class OriginMasterClass {
    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    private SimpleStringProperty id = new SimpleStringProperty();
}
