package Controllers;

import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Super {
    protected Connection connection;

    {
        try {
            connection = DriverManager
                    .getConnection(settings.des[0], settings.des[1], settings.des[2]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
    }
}
