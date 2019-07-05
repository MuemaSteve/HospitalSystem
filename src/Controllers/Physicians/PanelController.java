package Controllers.Physicians;

import Controllers.RecordsMasterClass;
import Controllers.Super;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Controllers.settings.appName;

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
    public TabPane tabcontainerhistorypane;
    public TabPane tabcontainerclinicpane;
    //code for condition panel variable initialisation
    public Tab addconditionssubtab;
    public TextField conditionAddField;
    public TextField conditionAddCategoryField;
    public TextArea conditionAddDescription;
    public Button conditionAddButton;
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
    public TableView tabClinicAppointmentsTable;
    public TableColumn tabClinicAppointmentsTableId;
    public TableColumn tabClinicAppointmentsTableVisitorName;
    public TableColumn tabClinicAppointmentsTableTypeOfVisit;
    public TableColumn tabClinicAppointmentsTableTimeOfAppointment;
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
    private ObservableList<RecordsMasterClass> data = FXCollections.observableArrayList();
    private ArrayList<TabPane> tabPaneArrayList = new ArrayList<>();
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tabPaneArrayList.add(tabContainer);
        tabPaneArrayList.add(tabcontainerclinicpane);
        tabPaneArrayList.add(tabcontainerhistorypane);
        configureView(tabPaneArrayList);
        time(clock);
        title.setText(appName + " Clinic Panel");
        buttonListeners();
    }

    private void buttonListeners() {
        logout.setOnMouseClicked(event -> logOut(panel));
        findinrecordsbutton.setOnMouseClicked(event -> {
            if (data.size() > 0) {
                data.clear();
            }
            findInRecordsMethod(panel, data, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
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
            }
        });

    }

    private void configureView(ArrayList<TabPane> tabPanes) {
        for (TabPane tabpane : tabPanes
        ) {
            double tabWidth = 200.0;
            tabpane.setTabMinWidth(tabWidth);
            tabpane.setTabMaxWidth(tabWidth);
            tabpane.setTabMinHeight(tabWidth - 150.0);
            tabpane.setTabMaxHeight(tabWidth - 150.0);
        }


    }

    @Override
    protected void findInRecordsMethod(AnchorPane panel, ObservableList<RecordsMasterClass> data, TextField findinrecords, TableView<RecordsMasterClass> patienttable, TableColumn<RecordsMasterClass, String> colpatientname, TableColumn<RecordsMasterClass, String> colpatientemail, TableColumn<RecordsMasterClass, String> colpatientnumber) {
        super.findInRecordsMethod(panel, data, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
    }

    @Override
    public void addPatientDetails() {

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

    }

    @Override
    public void Patientdiagnosis() {

    }

    @Override
    public void Patientprescription() {

    }
}
