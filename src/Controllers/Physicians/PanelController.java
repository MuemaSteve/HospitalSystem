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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Controllers.settings.appName;
import static Controllers.settings.id;

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
    public TableColumn<AppointmentMasterClass, String> tabClinicAppointmentsTableVisitorName;
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
    private ObservableList<RecordsMasterClass> recordsMasterClassObservableList = FXCollections.observableArrayList();
    private ObservableList<AppointmentMasterClass> appointmentMasterClassObservableList = FXCollections.observableArrayList();

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
        viewPatientAppointments();
    }

    private void buttonListeners() {
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
                addPatientDetails();
            }
        });

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
        String[] colRecs = {"conditionName", "date", "category", "description"};
        String[] values = {condition, date, category, description};
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
//todo continue from here by adding appointments to table make appointment selectible and add session to session sqlite bd on local machine
        System.out.println(id.get("userid"));
        String id = settings.id.get("userid");
        String query = "SELECT * FROM appointments WHERE doctorId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet foundrecords = preparedStatement.executeQuery();
            if (foundrecords.isBeforeFirst()) {

                while (foundrecords.next()) {
                    AppointmentMasterClass appointmentMasterClass = new AppointmentMasterClass();
                    appointmentMasterClass.setSize(appointmentMasterClass.getSize() + 1);
                    appointmentMasterClass.setId(foundrecords.getString("id"));
                    appointmentMasterClass.setDoctorId(foundrecords.getString("doctorId"));
                    appointmentMasterClass.setPatientId(foundrecords.getString("PatientId"));
                    PreparedStatement selectpatientName = connection.prepareStatement("SELECT * FROM patients WHERE  id=?");
                    selectpatientName.setString(1, appointmentMasterClass.getPatientId());
                    ResultSet resultSet = selectpatientName.executeQuery();
                    appointmentMasterClass.setName(resultSet.getString("name"));
                    appointmentMasterClassObservableList.add(appointmentMasterClass);
                }
                tabClinicAppointmentsTable.setItems(appointmentMasterClassObservableList);
                tabClinicAppointmentsTableId.setCellValueFactory(new PropertyValueFactory<>("id"));
                tabClinicAppointmentsTableVisitorName.setCellValueFactory(new PropertyValueFactory<>("name"));

                patienttable.refresh();
            } else {
                showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), null, "SUCH AN ACCOUNT DOES NOT EXIST");
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
        final String[] date = new String[1];
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
                date[0] = String.valueOf(i);
            }

        };
        dob.setOnAction(event);
        return date[0];
    }
}
