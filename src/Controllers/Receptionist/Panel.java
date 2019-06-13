package Controllers.Receptionist;

import Controllers.Super;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Panel extends Super implements Initializable {
    public Label clock;
    public AnchorPane panel;
    public TextField patientname;
    public TextField patientemail;
    public Button addpatient;
    public DatePicker dob;
    public Button logout;
    public Label datepicked;
    public TextField phonenumber;

    String date;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time(clock);
        buttonListeners();
        pickdate();

    }

    private void buttonListeners() {
        logout.setOnMouseClicked(event -> logOut(panel));
        addpatient.setOnMouseClicked(event -> validation());
    }

    private void validation() {
        if (patientname.getText().isEmpty() || patientemail.getText().isEmpty() || phonenumber.getText().isEmpty() || dob.getValue() == null) {
            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(),
                    "FILL ALL FIELDS", "PLEASE FILL ALL FIELDS");

        } else {

        }
    }

    private String pickdate() {

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // get the date picker value
                LocalDate i = dob.getValue();

                // get the selected date
                datepicked.setText(String.valueOf(i));
                date = String.valueOf(i);
            }
        };
        dob.setOnAction(event);
        return date;
    }

}
