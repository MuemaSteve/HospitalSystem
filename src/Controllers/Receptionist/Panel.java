package Controllers.Receptionist;

import Controllers.Super;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Panel extends Super implements Initializable {
    public Label clock;
    public AnchorPane panel;
    public TextField patientname;
    public TextField patientemail;
    public Button addpatient;
    public TextField residence;
    public TextField age;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time(clock);
    }
}
