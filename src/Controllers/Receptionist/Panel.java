package Controllers.Receptionist;

import Controllers.RecordsMasterClass;
import Controllers.Super;
import Controllers.settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Controllers.settings.appName;
import static Controllers.settings.siteHelp;

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
    public TableView<RecordsMasterClass> patienttable;
    public TableColumn<RecordsMasterClass, String> colpatientname;
    public TableColumn<RecordsMasterClass, String> colpatientemail;
    public TableColumn<RecordsMasterClass, String> colpatientnumber;
    public TabPane tabpane;
    public Tab tabexisting;
    public Tab tabnew;
    public AnchorPane existingcontainer;
    public AnchorPane newcontainer;
    public WebView webview;
    public Label title;
    private ObservableList<RecordsMasterClass> data = FXCollections.observableArrayList();
    private String date, radioval = null;
    private double tabWidth = 200.0;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time(clock);
        buttonListeners();
        pickdate();
        radioListener();
        patienttable.setPlaceholder(new Label(""));
        configureView();
        WebEngine engine = webview.getEngine();//help web page
        engine.load(siteHelp);
        title.setText(appName + " Reception");
    }
    // Private

    private void configureView() {
        tabpane.setTabMinWidth(tabWidth);
        tabpane.setTabMaxWidth(tabWidth);
        tabpane.setTabMinHeight(tabWidth - 140.0);
        tabpane.setTabMaxHeight(tabWidth - 140.0);
        tabpane.setRotateGraphic(true);


//        configureTab(tabexisting, "EXISTING PATIENTS", "resources/images/22-Cardi-B-Money.png");
//        configureTab(tabnew, "NEW PATIENTS", "resources/images/22-Cardi-B-Money.png");
    }

    private void configureTab(Tab tab, String title, String iconPath) {
        double imageWidth = 40.0;

        ImageView imageView = new ImageView(new Image(iconPath));
        imageView.setFitHeight(imageWidth);
        imageView.setFitWidth(imageWidth);

        Label label = new Label(title);
        label.setMaxWidth(tabWidth - 20);
        label.setPadding(new Insets(5, 0, 0, 0));
        label.setStyle("-fx-text-fill: black; -fx-font-size: 8pt; -fx-font-weight: normal;");
        label.setTextAlignment(TextAlignment.CENTER);

        BorderPane tabPane = new BorderPane();
//        tabPane.setRotate(90.0);

        tabPane.setMaxWidth(tabWidth);
        tabPane.setCenter(imageView);
        tabPane.setBottom(label);

        tab.setText("");
        tab.setGraphic(tabPane);
    }


    //snm2@gmail.com
    private void buttonListeners() {
        logout.setOnMouseClicked(event -> logOut(panel));
        addpatient.setOnMouseClicked(event -> validation());
        findinrecordsbutton.setOnMouseClicked(event -> {
            if (data.size() > 0) {
                data.clear();
            }
            findInRecordsMethod(panel, data, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
        });
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

    private void clearFields() {
        patientemail.clear();
        phonenumber.clear();
        patientname.clear();

    }
}
