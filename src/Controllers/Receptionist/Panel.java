package Controllers.Receptionist;

import Controllers.Super;
import Controllers.settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public RadioButton radiomale, radiofemale;
    public TextField findinrecords;


    private String date, radioval;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time(clock);
        buttonListeners();
        pickdate();
        radioListener();

    }

    //snm2@gmail.com
    private void buttonListeners() {
        logout.setOnMouseClicked(event -> logOut(panel));
        addpatient.setOnMouseClicked(event -> validation());
    }

    private void radioListener() {
        ToggleGroup toggleGroup = new ToggleGroup();
        radiofemale.setToggleGroup(toggleGroup);
        radiomale.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener((ov, t, t1) -> {

            RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
            radioval = chk.getText().toLowerCase();

        });
    }

    private void validation() {
        if (patientname.getText().isEmpty() || patientemail.getText().isEmpty() || phonenumber.getText().isEmpty() || dob.getValue() == null) {
            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(),
                    "FILL ALL FIELDS", "PLEASE FILL ALL FIELDS");

        } else {

            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(patientemail.getText());
            if (matcher.matches()) {
//                proceed
                insert();
            } else {
                showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "INVALID EMAIL", "ENTER A VALID EMAIL ADDRESS");
            }
        }
    }

    private void insert() {
        String name, email, number, dateofbirth, radioSelected;
        name = patientname.getText();
        email = patientemail.getText();
        number = phonenumber.getText();
        dateofbirth = date;
        radioSelected = radioval;
        try {
            PreparedStatement search = connection.prepareStatement("SELECT * FROM patients where email=?");
            search.setString(1, email);
            ResultSet rs = search.executeQuery();
            if (rs.isBeforeFirst()) {
                showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "ERROR", "THE USER IS REGISTERED");

            } else {
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO patients (name, email, phonenumber, birthdate, sex, branch) VALUES (?,?,?,?,?,?)");
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, email);
                    preparedStatement.setString(3, number);
                    preparedStatement.setString(4, dateofbirth);
                    preparedStatement.setString(5, radioSelected);
                    preparedStatement.setString(6, settings.hospital.get("hospital_name"));
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void pickdate() {

        EventHandler<ActionEvent> event = e -> {
            // get the date picker value
            LocalDate i = dob.getValue();
            if (i.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "ERROR", "TIME TRAVEL IS NOT YET A THING ");
                dob.setValue(null);
            } else {
                // get the selected date
                datepicked.setText(String.valueOf(i));
                date = String.valueOf(i);
            }

        };
        dob.setOnAction(event);
    }

}
