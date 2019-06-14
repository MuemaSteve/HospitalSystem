package Controllers.Receptionist;

import Controllers.Records;
import Controllers.Super;
import Controllers.settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public Button findinrecordsbutton;
    public TableView<Records> patienttable;
    public TableColumn<Records, String> colpatientname;
    public TableColumn<Records, String> colpatientemail;
    public TableColumn<Records, String> colpatientnumber;
    public TabPane tabpane;
    public Tab tabexisting;
    public Tab tabnew;
    private ObservableList<Records> data = FXCollections.observableArrayList();
    private String date, radioval = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time(clock);
        buttonListeners();
        pickdate();
        radioListener();
        patienttable.setPlaceholder(new Label(""));
    }


    //snm2@gmail.com
    private void buttonListeners() {
        logout.setOnMouseClicked(event -> logOut(panel));
        addpatient.setOnMouseClicked(event -> validation());
        findinrecordsbutton.setOnMouseClicked(event -> {
            if (data.size() > 0) {
                data.clear();
            }
            findInRecordsMethod();
        });
    }

    private void findInRecordsMethod() {
        String[] name = {findinrecords.getText()};

        String query = "SELECT * FROM patients WHERE name LIKE '%" + name[0] + "%'";
        try {
            Statement statement = connection.createStatement();
            ResultSet foundrecords = statement.executeQuery(query);
            if (foundrecords.isBeforeFirst()) {

                while (foundrecords.next()) {
                    Records records = new Records();
                    records.setName(foundrecords.getString("name"));
                    records.setEmail(foundrecords.getString("email"));
                    records.setPhonenumber(foundrecords.getString("phonenumber"));
                    data.add(records);
                }
                patienttable.setItems(data);

                colpatientname.setCellValueFactory(new PropertyValueFactory<Records, String>("name"));
                colpatientemail.setCellValueFactory(new PropertyValueFactory<Records, String>("email"));
                colpatientnumber.setCellValueFactory(new PropertyValueFactory<Records, String>("phonenumber"));
                patienttable.refresh();
            } else {
                showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), null, "SUCH AN ACCOUNT DOES NOT EXIST");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
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
        if (radioval == null || patientname.getText().isEmpty() || patientemail.getText().isEmpty() || phonenumber.getText().isEmpty() || dob.getValue() == null) {
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
                    if (preparedStatement.executeUpdate() != 0) {
                        clearFields();
                        showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SUCCESS", "THE PATIENT HAS BEEN ADDED SUCCESSFULLY");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void clearFields() {
        patientemail.clear();
        phonenumber.clear();
        patientname.clear();

    }

    private void pickdate() {

        String pattern = "dd-MM-yyyy";
        dob.setPromptText(pattern.toLowerCase());

        dob.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

        Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        // Must call super
                        super.updateItem(item, empty);

                        // Show Weekends in blue color
                        DayOfWeek day = DayOfWeek.from(item);
                        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                            this.setTextFill(Color.BLUE);
                        }

                        // Disable all future date cells
                        if (item.isAfter(LocalDate.now())) {
                            this.setDisable(true);
                        }
                    }
                };
            }
        };

        dob.setDayCellFactory(dayCellFactory);
        EventHandler<ActionEvent> event = e -> {
            // get the date picker value

            LocalDate i = dob.getValue();
            if (i.isAfter(LocalDate.now())) {
                showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "ERROR", "TIME TRAVEL IS NOT YET A THING ");
//                dob.setValue(null);
            } else {
                // get the selected date
                datepicked.setText(String.valueOf(i));
                date = String.valueOf(i);
            }

        };
        dob.setOnAction(event);
    }

}
