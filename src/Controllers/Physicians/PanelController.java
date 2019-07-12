package Controllers.Physicians;

import Controllers.MasterClasses.AppointmentMasterClass;
import Controllers.MasterClasses.RecordsMasterClass;
import Controllers.Super;
import Controllers.settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import static Controllers.settings.appName;
import static Controllers.settings.user;

public class PanelController extends Super implements Initializable, Physician {
    public TabPane tabContainer;
    public Tab searchPatientTab;
    public Tab addpatienthistoryTab;
    public AnchorPane addPatientHistoryContainer;
    public Tab labtestsTab;
    public AnchorPane labtestscontainer;
    public AnchorPane panel;
    public Label clock;
    public Label title;
    public Button logout;
    //logout
    public TextField findinrecords;
    public Button findinrecordsbutton;
    public TableView<RecordsMasterClass> patienttable;
    public TableColumn<RecordsMasterClass, String> colpatientname;
    public TableColumn<RecordsMasterClass, String> colpatientemail;
    public TableColumn<RecordsMasterClass, String> colpatientnumber;
    public TabPane tabcontainerhistorypane;//history tab
    public TabPane tabcontainerclinicpane;//clinic tab
    //code for condition panel variable initialisation
    public Tab addconditionssubtab;
    public TextField conditionAddField;
    public TextField conditionAddCategoryField;
    public TextArea conditionAddDescription;
    public Button conditionAddButton;//Add condition button
    public DatePicker conditionAddDateDiagnosed;
    //code for history tab
    public Tab viewHistoryTab;
    public TableView tablehistory;
    public TableColumn tablehistoryId;
    public TableColumn tablehistoryDate;
    public TableColumn tablehistoryDoctor;
    public TableColumn tablehistoryPrescription;
    public TableColumn tablehistoryTests;
    public TableColumn tablehistoryRatings;
    public Button tablehistoryViewPrescriptionsButton;
    public Button tablehistoryViewLabTestButton;
    public Button tablehistoryViewDiagnosisButton;
    public Button tablehistoryGetReportButton;
    //existing conditions code
    public Tab existingConditionsTab;
    public TableView existingConditionsTabTable;
    public TableColumn existingConditionsTabTableId;
    public TableColumn existingConditionsTabTableName;
    public TableColumn existingConditionsTabTableDateAdded;
    public TableColumn existingConditionsTabTableCategory;
    public TableColumn existingConditionsTabTableDoctor;
    public Button existingConditionsTabTableViewDetailsButton;
    //    clinic Appointments
    public Tab tabClinicAppointments;
    public TableView<AppointmentMasterClass> tabClinicAppointmentsTable;
    public TableColumn<AppointmentMasterClass, String> tabClinicAppointmentsTableId;
    //    public TableColumn <AppointmentMasterClass,String> tabClinicAppointmentsTableVisitorName;
    public TableColumn<AppointmentMasterClass, String> tabClinicAppointmentsTableTypeOfVisit;
    public TableColumn<AppointmentMasterClass, String> tabClinicAppointmentsTableTimeOfAppointment;
    public Button tabClinicAppointmentsTableCallInButton;
    //    clinic lab tests
    public Tab tabClinicLabTests;
    public TableView tabClinicLabTestsTable;
    public TableColumn tabClinicLabTestsTableId;
    public TableColumn tabClinicLabTestsTableTestType;
    public TableColumn tabClinicLabTestsTableTechnician;
    public TableColumn tabClinicLabTestsTablePatientName;
    public Button tabClinicLabTestsTableGetFullReportButton;
    public Button tabClinicLabTestsTablemoveReportToDiagnosis;
    //    clinic diagnosis
    public Tab tabClinicDiagnosis;
    public TextArea tabClinicDiagnosisInput;
    public Button tabClinicDiagnosisSubmit;
    public Tab tabClinicPrescription;
    public Button tabClinicPrescriptionSubmit;
    public TextArea tabClinicPrescriptionText;
    public Button endPatientSession;
    public TabPane appointmentssearch;
    //SESSIONS TAB
    public TableView<AppointmentMasterClass> tabClinicSessionsTable;
    public TableColumn<AppointmentMasterClass, String> tabClinicSessionsTableId;
    public TableColumn<AppointmentMasterClass, String> tabClinicSessionsTablePatientEmail;
    public TableColumn<AppointmentMasterClass, String> tabClinicSessionsTableName;
    public Button tabClinicSessionsTableResumeInButton;
    public Tab sessionsTab;
    public Button startSessionButton;
    private ObservableList<RecordsMasterClass> recordsMasterClassObservableList = FXCollections.observableArrayList();
    private ObservableList<AppointmentMasterClass> appointmentMasterClassObservableList = FXCollections.observableArrayList();
    private ObservableList<AppointmentMasterClass> appointmentMasterClassObservableList2 = FXCollections.observableArrayList();

    private ArrayList<TabPane> tabPaneArrayList = new ArrayList<>();
    private String date;
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tabPaneArrayList.add(tabContainer);
        tabPaneArrayList.add(tabcontainerclinicpane);
        tabPaneArrayList.add(tabcontainerhistorypane);
        tabPaneArrayList.add(appointmentssearch);
        configureView(tabPaneArrayList);
        time(clock);
        title.setText(appName + " Clinic Panel");
        buttonListeners();
        date = datepicker(conditionAddDateDiagnosed);
        System.out.println(date);
//        viewPatientAppointments();
        if (tabClinicAppointments.isSelected()) {
            viewPatientAppointments();
        }
        loadSessions();
//        createSession();
    }

    private void createSession(TableView<RecordsMasterClass> tableView) {
        RecordsMasterClass recordsMasterClass = tableView.getSelectionModel().getSelectedItem();
        if (currentSession.isEmpty()) {
            currentSession.put("currentSession", recordsMasterClass.getEmail());
        } else {
            currentSession.replace("currentSession", recordsMasterClass.getEmail());
        }
//        tabcontainerhistorypane.getSelectionModel().select(1);
        SingleSelectionModel<Tab> selectionModel = tabcontainerhistorypane.getSelectionModel();
        selectionModel.select(1); //select by object
        System.out.println("Success...");


    }

    private void loadSessions() {
        try {
            Statement preparedStatement = localDbConnection.createStatement();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM SessionPatients");
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    AppointmentMasterClass appointmentMasterClass = new AppointmentMasterClass();
                    appointmentMasterClass.setSize(appointmentMasterClass.getSize() + 1);
                    appointmentMasterClass.setId(resultSet.getString("sessionId"));
                    appointmentMasterClass.setName(resultSet.getString("name"));
                    System.out.println(resultSet.getString("name"));
                    appointmentMasterClass.setPatientEmail(resultSet.getString("email"));
                    appointmentMasterClassObservableList2.add(appointmentMasterClass);
                }
                tabClinicSessionsTable.setItems(appointmentMasterClassObservableList2);
                tabClinicSessionsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
                tabClinicSessionsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
                tabClinicSessionsTablePatientEmail.setCellValueFactory(new PropertyValueFactory<>("patientEmail"));
                tabClinicSessionsTable.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void buttonListeners() {
        tabClinicSessionsTableResumeInButton.setOnAction(event -> resumeSession(tabClinicSessionsTable));
        startSessionButton.setOnAction(event -> createSession(patienttable));
        tabClinicAppointmentsTableCallInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AppointmentMasterClass appointmentMasterClass = tabClinicAppointmentsTable.getSelectionModel().getSelectedItem();

                callIn(appointmentMasterClass);
            }

            private void callIn(AppointmentMasterClass appointmentMasterClass) {
                String patientId = appointmentMasterClass.getPatientId();
                ResultSet resultSet = null;
                String query = "SELECT * FROM patients WHERE id =?";
                try {
                    PreparedStatement patientSelection = connection.prepareStatement(query);
                    patientSelection.setString(1, patientId);
                    resultSet = patientSelection.executeQuery();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    PreparedStatement statement = localDbConnection.prepareStatement("INSERT INTO SessionPatients(name, email, sessionId) VALUES (?,?,?)");
                    if (resultSet.isBeforeFirst()) {
                        while (resultSet.next()) {
                            statement.setString(1, resultSet.getString("name"));
                            statement.setString(2, resultSet.getString("email"));
                        }
                    }
                    statement.setString(3, dateTimeMethod());


                    if (statement.executeUpdate() > 0) {
//                        remove from table
                        String query1 = "DELETE FROM appointments WHERE PatientId=?";
                        PreparedStatement preparedStatement = connection.prepareStatement(query1);
                        preparedStatement.setString(1, patientId);
                        int rows = preparedStatement.executeUpdate();
                        if (rows == 1) {
                            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SUCCESS", "OPERATION SUCCESSFUL");
                            tabClinicAppointmentsTable.refresh();
                        } else {
                            showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "ERROR", "THE OPERATION WAS UNSUCCESSFULL");

                        }
                    } else {
                        showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "ERROR", "THE OPERATION WAS UNSUCCESSFULL");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            private String dateTimeMethod() {
                Date date = new Date(System.currentTimeMillis());
                return date.toString() + "::" + user.get("user");
            }
        });
        logout.setOnMouseClicked(event -> logOut(panel));
        findinrecordsbutton.setOnMouseClicked(event -> {
            if (recordsMasterClassObservableList.size() > 0) {
                recordsMasterClassObservableList.clear();
            }
            findInRecordsMethod(panel, recordsMasterClassObservableList, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
        });
        endPatientSession.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //end patient session
            }
        });
        conditionAddButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //add condition button
                if (currentSession.get("currentSession") == null) {
                    showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SUCCESS", "CREATE SESSION FIRST");
                } else {
                    addPatientDetails();
                }
            }
        });

    }

    private void resumeSession(TableView<AppointmentMasterClass> tabClinicSessionsTable) {
        AppointmentMasterClass appointmentMasterClass = tabClinicSessionsTable.getSelectionModel().getSelectedItem();
        if (currentSession.isEmpty()) {
            currentSession.put("currentSession", appointmentMasterClass.getPatientEmail());
        } else {
            currentSession.replace("currentSession", appointmentMasterClass.getPatientEmail());
        }
    }


    @Override
    protected void findInRecordsMethod(AnchorPane panel, ObservableList<RecordsMasterClass> data, TextField findinrecords, TableView<RecordsMasterClass> patienttable, TableColumn<RecordsMasterClass, String> colpatientname, TableColumn<RecordsMasterClass, String> colpatientemail, TableColumn<RecordsMasterClass, String> colpatientnumber) {
        super.findInRecordsMethod(panel, data, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
    }

    @Override
    public void addPatientDetails() {
        String condition = conditionAddField.getText();
        String category = conditionAddCategoryField.getText();
        String description = conditionAddDescription.getText();
        String tablename = "conditions";
        String[] colRecs = {"conditionName", "date", "category", "description", "patientemail"};
        String[] values = {condition, date, category, description, currentSession.get("currentSession")};

        try {
            if (insertIntoTable(tablename, colRecs, values) > 0)
                showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SUCCESS", "OPERATION WAS SUCCESSFULL");
            else
                showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "ERROR", "OPERATION FAILED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void viewPatientDetails() {

    }

    @Override
    public void viewPatientHistory() {

    }

    @Override
    public void viewPatientLabTests() {

    }

    @Override
    public void viewPatientAppointments() {


        String query = "SELECT * FROM appointments WHERE doctorId=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, settings.id.get("userid"));
            ResultSet foundrecords = preparedStatement.executeQuery();
            if (foundrecords.isBeforeFirst()) {

                while (foundrecords.next()) {
                    AppointmentMasterClass appointmentMasterClass = new AppointmentMasterClass();
                    appointmentMasterClass.setSize(appointmentMasterClass.getSize() + 1);
                    appointmentMasterClass.setId(foundrecords.getString("id"));
                    appointmentMasterClass.setDoctorId(foundrecords.getString("doctorId"));
                    appointmentMasterClass.setPatientId(foundrecords.getString("PatientId"));
                    appointmentMasterClass.setTime(foundrecords.getString("time"));
                    appointmentMasterClass.setType(foundrecords.getString("type"));

                    appointmentMasterClassObservableList.add(appointmentMasterClass);
                }
                tabClinicAppointmentsTable.setItems(appointmentMasterClassObservableList);
                tabClinicAppointmentsTableTimeOfAppointment.setCellValueFactory(new PropertyValueFactory<>("time"));
                tabClinicAppointmentsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
                tabClinicAppointmentsTableTypeOfVisit.setCellValueFactory(new PropertyValueFactory<>("type"));

                tabClinicAppointmentsTable.refresh();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void Patientdiagnosis() {

    }

    @Override
    public void Patientprescription() {

    }

    public String datepicker(DatePicker dob) {
        String pattern = "dd-MM-yyyy";
        dob.setPromptText(pattern.toUpperCase());

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
                this.date = String.valueOf(i);
            }

        };
        dob.setOnAction(event);
        return this.date;
    }
}
