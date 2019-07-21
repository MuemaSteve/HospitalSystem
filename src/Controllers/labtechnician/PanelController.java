package Controllers.labtechnician;

import Controllers.MasterClasses.LabTestsMasterClass;
import Controllers.MasterClasses.SessionMasterClass;
import Controllers.Super;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.IdentityHashMap;
import java.util.ResourceBundle;

import static Controllers.settings.appName;
import static Controllers.settings.user;

public class PanelController extends Super implements Initializable, LabSettings {
    public AnchorPane panel;
    public TabPane tabContainer;
    public Tab pendingteststab;
    public TableView<LabTestsMasterClass> pendingTestsTable;
    public TableColumn<LabTestsMasterClass, String> pendingTestsTableid;
    public TableColumn<LabTestsMasterClass, String> pendingTestsTabledoctor;
    public TableColumn<LabTestsMasterClass, String> pendingTestsTablepatientname;
    public TableColumn<LabTestsMasterClass, String> pendingTestsTableTests;

    public Button pendingTestsTablestartTest;
    public Button pendingTestsTableviewdetails;
    public Tab labtestresultstab;
    public AnchorPane labtestsresultscontainer;
    public TextArea testresults;
    public Button submitImageResult;
    public Button submitTypedResult;
    public Tab sessionsTab;
    public TableView<SessionMasterClass> tablabSessionsTable;
    public TableColumn<SessionMasterClass, String> tablabSessionsTableid;
    public TableColumn<SessionMasterClass, String> tablabSessionsTablepatient;
    public TableColumn<SessionMasterClass, String> tablabSessionsTabledoctor;
    public Button tablabSessionsTableresume;
    public Label clock;
    public Button logout;
    public Label title;
    private IdentityHashMap<String, String> currentSession = new IdentityHashMap<>();

    private ObservableList<LabTestsMasterClass> labTestsMasterClassObservableList = FXCollections.observableArrayList();
    private ObservableList<SessionMasterClass> sessionMasterClassObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time(clock);
        title.setText(appName + " Labs");
        buttonListeners();

        viewSessions();
        submitResults();
        try {
            viewTests();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    private void initializer() {
//
//        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
//            try {
//                labTestsMasterClassObservableList.clear();
//
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//
//        }),
//                new KeyFrame(Duration.seconds(60))
//        );
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
//
//
//    }

    private void viewTests() throws SQLException {
        //select teststext where technician is the current logged in user
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
                labTestsMasterClass.setTests(selectedResults.getString("tests"));
                labTestsMasterClassObservableList.add(labTestsMasterClass);
            }
            pendingTestsTable.setItems(labTestsMasterClassObservableList);
            pendingTestsTableid.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("id"));
            pendingTestsTabledoctor.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("docName"));
            pendingTestsTablepatientname.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("patientName"));
            pendingTestsTableTests.setCellValueFactory(new PropertyValueFactory<>("tests"));
            pendingTestsTable.refresh();
            labTestsMasterClassObservableList.clear();
        } else {
            showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "FREEDOM", "THERE ARE NO TESTS TO BE DONE BY YOU");
        }
        viewTests();
    }

    private void startSession(String tests, String email) {
        if (currentSession.isEmpty()) {
            currentSession.put("currentSession", email);

        } else {
            currentSession.replace("currentSession", email);
        }
//            viewPatientDetails();

//            System.out.println(email + " is the email");
        try {
            PreparedStatement main = connection.prepareStatement("SELECT * FROM patients WHERE email=?");
            main.setString(1, email);
            ResultSet rsmain = main.executeQuery();
            //check if in sessions table
            PreparedStatement preparedStatement = localDbConnection.prepareStatement("SELECT * FROM SessionLabs WHERE email=?");
            preparedStatement.setString(1, email);
            ResultSet check = preparedStatement.executeQuery();
            if (check.isBeforeFirst()) {
                showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "TEST ALREADY IN SESSION", "THE TEST HAS AN EXISTING SESSION");
            } else {
                PreparedStatement statement = localDbConnection.prepareStatement("INSERT INTO SessionLabs(name, email, sessionId,testText) VALUES (?,?,?,?)");
                if (rsmain.isBeforeFirst()) {
                    while (rsmain.next()) {
                        statement.setString(1, rsmain.getString("name"));
                        statement.setString(2, currentSession.get("currentSession"));
                        statement.setString(3, dateTimeMethod());
                        statement.setString(4, tests);
                        if (statement.executeUpdate() > 0) {
                            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), currentSession.get("currentSession") + " session", "SESSION CREATED SUCCESSFULLY");

                        } else {
                            showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), currentSession.get("currentSession") + " session", "SESSION CREATION FAILED");

                        }
                    }
                } else {
                    showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "ERROR", "UNEXPECTED ERROR");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        labTestsMasterClassObservableList.clear();

        loadSessions();
    }

    private void loadSessions() {
        try {
            Statement preparedStatement = localDbConnection.createStatement();
            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM SessionLabs");
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    SessionMasterClass appointmentMasterClass = new SessionMasterClass();
                    appointmentMasterClass.setSize(appointmentMasterClass.getSize() + 1);
                    appointmentMasterClass.setId(resultSet.getString("sessionId"));
                    appointmentMasterClass.setName(resultSet.getString("name"));
                    System.out.println(resultSet.getString("name"));
                    appointmentMasterClass.setPatientEmail(resultSet.getString("email"));
                    sessionMasterClassObservableList.add(appointmentMasterClass);
                }
                tablabSessionsTable.setItems(sessionMasterClassObservableList);
                tablabSessionsTableid.setCellValueFactory(new PropertyValueFactory<>("id"));
                tablabSessionsTablepatient.setCellValueFactory(new PropertyValueFactory<>("name"));
                tablabSessionsTabledoctor.setCellValueFactory(new PropertyValueFactory<>("patientEmail"));
                tablabSessionsTable.refresh();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void endSession() {
        currentSession.clear();
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
                LabTestsMasterClass labTestsMasterClass = pendingTestsTable.getSelectionModel().getSelectedItem();
                String tests = labTestsMasterClass.getTests();
                String gemail = labTestsMasterClass.getPatientName();
                startSession(tests, gemail);
            }
        });

        pendingTestsTableviewdetails.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LabTestsMasterClass labTestsMasterClassselected = pendingTestsTable.getSelectionModel().getSelectedItem();
                ObservableList<TablePosition> selectedList = pendingTestsTable.getSelectionModel().getSelectedCells();
                if (selectedList.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "ERROR", "YOU MUST SELECT A ROW ON THE TABLE TO COMPLETE THIS OPERATION");

                } else {
                    viewDetails(labTestsMasterClassselected.getId());
                }

            }
        });
    }

    private void viewDetails(String id) {
        String query = "SELECT tests FROM labtests WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    teststext.put("teststext", resultSet.getString("tests"));

                }
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/views/labtechnician/popup.fxml"));
                try {
                    Parent parent = fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(parent));
                    stage.initStyle(StageStyle.UTILITY);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else
                showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "ERROR", "INVALID ID");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
