package Controllers.labtechnician;

import Controllers.MasterClasses.LabTestsMasterClass;
import Controllers.Super;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static Controllers.settings.appName;
import static Controllers.settings.user;

public class PanelController extends Super implements Initializable {
    public AnchorPane panel;
    public TabPane tabContainer;
    public Tab pendingteststab;
    public TableView<LabTestsMasterClass> pendingTestsTable;
    public TableColumn<LabTestsMasterClass, String> pendingTestsTableid;
    public TableColumn<LabTestsMasterClass, String> pendingTestsTabledoctor;
    public TableColumn<LabTestsMasterClass, String> pendingTestsTablepatientname;
    public Button pendingTestsTablestartTest;
    public Button pendingTestsTableviewdetails;
    public Tab labtestresultstab;
    public AnchorPane labtestsresultscontainer;
    public TextArea testresults;
    public Button submitImageResult;
    public Button submitTypedResult;
    public Tab sessionsTab;
    public TableView tablabSessionsTable;
    public TableColumn tablabSessionsTableid;
    public TableColumn tablabSessionsTablepatient;
    public TableColumn tablabSessionsTabledoctor;
    public Button tablabSessionsTableresume;
    public Label clock;
    public Button logout;
    public Label title;
    ObservableList<LabTestsMasterClass> labTestsMasterClassObservableList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time(clock);
        title.setText(appName + " Labs");
        buttonListeners();

        startSession();
        endSession();
        viewSessions();
        submitResults();
        initializer();
    }

    private void initializer() {

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                labTestsMasterClassObservableList.clear();

                viewTests();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }),
                new KeyFrame(Duration.seconds(60))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    private void viewTests() throws SQLException {
        //select tests where technician is the current logged in user
        String selectTechnicianTests = "SELECT * FROM labtests WHERE technician=?";
        PreparedStatement select = connection.prepareStatement(selectTechnicianTests);
        select.setString(1, user.get("user"));
        ResultSet selectedResults = select.executeQuery();
        if (selectedResults.isBeforeFirst()) {
            while (selectedResults.next()) {
                LabTestsMasterClass labTestsMasterClass = new LabTestsMasterClass();
                labTestsMasterClass.setId(selectedResults.getString("id"));
                labTestsMasterClass.setDocName(selectedResults.getString("doctorname"));
                labTestsMasterClass.setPatientName(selectedResults.getString("patientname"));
                labTestsMasterClassObservableList.add(labTestsMasterClass);
            }
            pendingTestsTable.setItems(labTestsMasterClassObservableList);
            pendingTestsTableid.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("id"));
            pendingTestsTabledoctor.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("docName"));
            pendingTestsTablepatientname.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("patientName"));
            pendingTestsTable.refresh();
//           labTestsMasterClassObservableList.clear();
        } else {
            showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "FREEDOM", "THERE ARE NO TESTS TO BE DONE BY YOU");
        }
    }

    private void startSession() {

    }

    private void endSession() {

    }

    private void viewSessions() {

    }

    private void submitResults() {
    }

    private void buttonListeners() {
        tablabSessionsTableresume.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logOut(panel);
            }
        });
        submitImageResult.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        submitTypedResult.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
        pendingTestsTablestartTest.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        pendingTestsTableviewdetails.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }
}
