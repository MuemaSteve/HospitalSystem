package Controllers.Physicians;

import Controllers.MasterClasses.AppointmentMasterClass;
import Controllers.MasterClasses.ConditionsMasterClass;
import Controllers.MasterClasses.LabTestsMasterClass;
import Controllers.MasterClasses.RecordsMasterClass;
import Controllers.Super;
import Controllers.settings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public TableView<ConditionsMasterClass> existingConditionsTabTable;
    public TableColumn<ConditionsMasterClass, String> existingConditionsTabTableId;
    public TableColumn<ConditionsMasterClass, String> existingConditionsTabTableName;
    public TableColumn<ConditionsMasterClass, String> existingConditionsTabTableDateAdded;
    public TableColumn<ConditionsMasterClass, String> existingConditionsTabTableCategory;
    public TableColumn<ConditionsMasterClass, String> existingConditionsTabTableDoctor;
    public Button existingConditionsTabTableViewDetailsButton;
    //    clinic Appointments
    public Tab tabClinicAppointments;
    public TableView<AppointmentMasterClass> tabClinicAppointmentsTable;
    public TableColumn<AppointmentMasterClass, String> tabClinicAppointmentsTableId;
    //    public TableColumn <AppointmentMasterClass,String> tabClinicAppointmentsTableVisitorName;
    public TableColumn<AppointmentMasterClass, String> tabClinicAppointmentsTableTypeOfVisit;
    public TableColumn<AppointmentMasterClass, String> tabClinicAppointmentsTableTimeOfAppointment;
    public Button tabClinicAppointmentsTableCallInButton;
    //    clinic lab teststext
    public Tab tabClinicLabTests;
    public TableView<LabTestsMasterClass> tabClinicLabTestsTable;
    public TableColumn<LabTestsMasterClass, String> tabClinicLabTestsTableId;
    public TableColumn<LabTestsMasterClass, String> tabClinicLabTestsTableTestType;
    public TableColumn<LabTestsMasterClass, String> tabClinicLabTestsTableTechnician;
    public TableColumn<LabTestsMasterClass, String> tabClinicLabTestsTablePatientName;
    public TableColumn<LabTestsMasterClass, String> tabClinicLabTestsTableStatus;

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
    public TabPane labteststabpane;
    public TextArea testsInputPhysician;
    public Button testsSendToLab;
    public ImageView resultPreview;
    private ObservableList<RecordsMasterClass> recordsMasterClassObservableList = FXCollections.observableArrayList();
    private ObservableList<ConditionsMasterClass> conditionsMasterClassObservableList = FXCollections.observableArrayList();
    private ObservableList<AppointmentMasterClass> appointmentMasterClassObservableList = FXCollections.observableArrayList();
    private ObservableList<AppointmentMasterClass> appointmentMasterClassObservableList2 = FXCollections.observableArrayList();
    private ObservableList<LabTestsMasterClass> labTestsMasterClassObservableList = FXCollections.observableArrayList();

    private ArrayList<TabPane> tabPaneArrayList = new ArrayList<>();
    private String date;
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
//        loadSessions();
//        viewPatientDetails();
        reloadTables();
        tabContainer.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
            if ((nv.textProperty().getValue().equals("PATIENT RECORDS") && currentSession.isEmpty()) || nv.textProperty().getValue().equals("CLINIC PANEL") && currentSession.isEmpty()) {
                tabContainer.getSelectionModel().select(ov);
                showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "ERROR", "CREATE SESSION FIRST");
            } else {
                reloadTables();
            }
        });
//        todo uncomment to make the tabpanes bigger
//        tabPaneArrayList.add(tabContainer);
//        tabPaneArrayList.add(tabcontainerclinicpane);
//        tabPaneArrayList.add(tabcontainerhistorypane);
//        tabPaneArrayList.add(appointmentssearch);

        tabcontainerhistorypane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                reloadTables();
            }
        });
//        labteststabpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
//            @Override
//            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
//                reloadTables();
//            }
//        });
        tabContainer.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                reloadTables();
            }
        });
        tabcontainerclinicpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                reloadTables();
            }
        });
        appointmentssearch.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                reloadTables();
            }
        });
        tabClinicDiagnosis.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                tabClinicDiagnosisInput.clear();
                resultPreview.setImage(null);
            }
        });
        configureView(tabPaneArrayList);
        time(clock);
        title.setText(appName + " Clinic Panel");
        buttonListeners();
        date = datepicker(conditionAddDateDiagnosed);
//        System.out.println(date);
//        viewPatientAppointments();
        if (tabClinicAppointments.isSelected()) {
            viewPatientAppointments();
        }
        if (sessionsTab.isSelected()) {
            loadSessions();
        }

    }


    private void createSession(String id, String email) {
        if (currentSession.isEmpty()) {
            currentSession.put("currentSession", email);

        } else {
            currentSession.replace("currentSession", email);
        }
        viewPatientDetails();

        System.out.println(email + " is the email");
        try {
            PreparedStatement main = connection.prepareStatement("SELECT * FROM patients WHERE id=?");
            main.setString(1, id);
            ResultSet rsmain = main.executeQuery();
            //check if in sessions table
            PreparedStatement preparedStatement = localDbConnection.prepareStatement("SELECT * FROM SessionPatients WHERE email=?");
            preparedStatement.setString(1, email);
            ResultSet check = preparedStatement.executeQuery();
            if (check.isBeforeFirst()) {
                showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "PATIENT ALREADY IN SESSION", "THE PATIENT HAS AN EXISTING SESSION");
            } else {
                PreparedStatement statement = localDbConnection.prepareStatement("INSERT INTO SessionPatients(name, email, sessionId) VALUES (?,?,?)");
                if (rsmain.isBeforeFirst()) {
                    while (rsmain.next()) {
                        statement.setString(1, rsmain.getString("name"));
                        statement.setString(2, currentSession.get("currentSession"));
                        statement.setString(3, dateTimeMethod());
                        if (statement.executeUpdate() > 0) {
                            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), currentSession.get("currentSession") + " session", "SESSION CREATED SUCCESSFULLY");

                        } else {
                            showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), currentSession.get("currentSession") + " session", "SESSION CREATION FAILED");

                        }
                    }
                } else {
                    showAlert(Alert.AlertType.WARNING, conditionAddButton.getScene().getWindow(), "ERROR", "UNEXPECTED ERROR");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        appointmentMasterClassObservableList2.clear();

        loadSessions();
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
        tablehistoryGetReportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                view history instance report
            }


        });
        tabClinicLabTestsTableGetFullReportButton.setOnAction(event -> getTestReport());
        tabClinicLabTestsTablemoveReportToDiagnosis.setOnAction(event -> {
            if (setScenevariables()) {
                currentSession.put("currentSession", temporarySession.get("temporarySession"));
                tabContainer.getSelectionModel().select(2);
                tabcontainerclinicpane.getSelectionModel().select(1);
                viewDiagnosis();
            }
        });
        testsSendToLab.setOnAction(event -> {
            sendTest();
        });
        tabClinicSessionsTableResumeInButton.setOnAction(event -> resumeSession(tabClinicSessionsTable));
        startSessionButton.setOnAction(event -> {
            RecordsMasterClass recordsMasterClass = patienttable.getSelectionModel().getSelectedItem();
            String id = recordsMasterClass.getId();
            createSession(id, recordsMasterClass.getEmail());
        });
        tabClinicAppointmentsTableCallInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AppointmentMasterClass appointmentMasterClass = tabClinicAppointmentsTable.getSelectionModel().getSelectedItem();
                callIn(appointmentMasterClass);
                viewPatientAppointments();
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
                    if (resultSet != null && resultSet.isBeforeFirst()) {
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
                currentSession.clear();
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

    private File createImage(String text) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 17);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2d.dispose();

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(java.awt.Color.BLACK);
        g2d.drawString(text, 0, fm.getAscent());
        g2d.dispose();
        File tempFile = null;
        try {
            tempFile = File.createTempFile("preview", ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempFile != null) {
            tempFile.deleteOnExit();
        }
        try {
            if (tempFile != null) {
                ImageIO.write(img, "png", tempFile);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return tempFile;
    }

    private void viewDiagnosis() {
        if (!Physician.resultText.isEmpty()) {
//            resultPreview.setImage(new Image(createImage(resultText.get("resultText")).toURI().toString()));
            tabClinicDiagnosisInput.setText(resultText.get("resultText"));
            Physician.resultText.clear();
        } else if (imageResult.get("resultImage") != null) {
            try {
                System.out.println(imageResult.containsKey("resultImage"));
                BufferedImage imBuff = ImageIO.read(imageResult.get("resultImage"));
                System.out.println(imBuff.getWidth() + "x" + imBuff.getHeight());
                Image image = SwingFXUtils.toFXImage(imBuff, null);
                resultPreview.setImage(image);
                imBuff.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (imageResult.get("resultImage") != null) {
                    try {
                        imageResult.get("resultImage").close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//    imageResult.clear();
        }
    }

    //SENDING TESTS TO LABTESTS TABLE is complete
    private void sendTest() {
        String tests = testsInputPhysician.getText();
        if (tests.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "NULL LAB TESTS", "LAB TESTS MUST BE SUBMITTED");
        } else {
            //send teststext to lab table
            //first select technician with least teststext and who is active and select them as the person in charge of the test
            String query = "SELECT * FROM USERS WHERE userclearancelevel=? and status=? ORDER BY numberoofappointments DESC ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "lab tech".toUpperCase());
                preparedStatement.setString(2, "active");
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    ArrayList<Integer> count = new ArrayList<>();
                    ArrayList<Integer> labtechid = new ArrayList<>();

                    while (resultSet.next()) {
                        count.add(Integer.valueOf(resultSet.getString("numberoofappointments")));
                        labtechid.add(Integer.valueOf(resultSet.getString("id")));

                    }
                    int min = Integer.MAX_VALUE;
                    for (Integer integer : count) {
                        if (integer < min) {
                            min = integer;
                        }
                    }
                    String docSelect = "SELECT * FROM users WHERE numberoofappointments=? AND userclearancelevel=?";
                    PreparedStatement p1 = connection.prepareStatement(docSelect);
                    p1.setInt(1, min);
                    p1.setString(2, "LAB TECH");
                    ResultSet r1 = p1.executeQuery();
                    if (r1.next()) {
                        //continue from here
                        String email = r1.getString("email");

                        String[] recs = {"tests", "technician", "patientname", "doctorname"};
                        String[] values = {tests, email, currentSession.get("currentSession"), user.get("user")};
                        insertIntoTable("labtests", recs, values);
                        String docUpdate = "UPDATE users SET numberoofappointments=? WHERE email=?";
                        PreparedStatement preparedStatement1 = connection.prepareStatement(docUpdate);
                        preparedStatement1.setInt(1, min + 1);
                        preparedStatement1.setString(2, email);
                        int x = preparedStatement1.executeUpdate();
                        if (x > 0) {
                            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SUCCESS", "OPERATION SUCCESSFULL");
                        } else {
                            showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "ERROR", "OPERATION FAILED");
                        }
                    }
                } else
                    showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), null, "SUCH AN ACCOUNT TYPE DOES NOT EXIST");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //completed method
    private void resumeSession(TableView<AppointmentMasterClass> tabClinicSessionsTable) {
        AppointmentMasterClass appointmentMasterClass = tabClinicSessionsTable.getSelectionModel().getSelectedItem();

        if (currentSession.isEmpty()) {
            currentSession.put("currentSession", appointmentMasterClass.getPatientEmail());
            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SESSION RESUMED", "SESSION FOR " + currentSession.get("currentSession") + " HAS BEEN RESUMED");
        } else if (currentSession.get("currentSession").equals(appointmentMasterClass.getPatientEmail())) {
            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SESSION IS ACTIVE", "SESSION FOR " + currentSession.get("currentSession") + " IS ALREADY ACTIVE");

        } else {
            currentSession.replace("currentSession", appointmentMasterClass.getPatientEmail());
            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SESSION RESUMED", "SESSION FOR " + currentSession.get("currentSession") + " HAS BEEN RESUMED");

        }
        viewPatientDetails();

    }


    @Override
    protected void findInRecordsMethod(AnchorPane panel, ObservableList<RecordsMasterClass> data, TextField findinrecords, TableView<RecordsMasterClass> patienttable, TableColumn<RecordsMasterClass, String> colpatientname, TableColumn<RecordsMasterClass, String> colpatientemail, TableColumn<RecordsMasterClass, String> colpatientnumber) {
        super.findInRecordsMethod(panel, data, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
    }

    @Override//completed method
    public void addPatientDetails() {
        String condition = conditionAddField.getText();
        String category = conditionAddCategoryField.getText();
        String description = conditionAddDescription.getText();
        if (condition.isEmpty() || category.isEmpty() || description.isEmpty() || date.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "FILL ALL INPUTS", "ALL INPUT FIELDS HAVE TO BE FILLED");
        } else {

            String tablename = "conditions";
            String[] colRecs = {"conditionName", "date", "category", "description", "patientemail", "doctorMail"};
            String[] values = {condition, date, category, description, currentSession.get("currentSession"), settings.user.get("user")};

            try {
                if (insertIntoTable(tablename, colRecs, values) > 0) {
                    showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SUCCESS", "OPERATION WAS SUCCESSFULL");
                    conditionAddField.clear();
                    conditionAddCategoryField.clear();
                    conditionAddDescription.clear();
                    String pattern = "dd-MM-yyyy";
                    conditionAddDateDiagnosed.setPromptText(pattern.toUpperCase());
                } else
                    showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "ERROR", "OPERATION FAILED");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void viewPatientHistory() {

    }

    @Override
    public void viewPatientLabTests() {
//select teststext where technician is the current logged in user
        String selectTechnicianTests = "SELECT * FROM labtests WHERE doctorname=?";
        PreparedStatement select = null;
        try {
            select = connection.prepareStatement(selectTechnicianTests);
            select.setString(1, user.get("user"));
            ResultSet selectedResults = select.executeQuery();
            if (selectedResults.isBeforeFirst()) {
                InputStream is = null;
                while (selectedResults.next()) {
                    LabTestsMasterClass labTestsMasterClass = new LabTestsMasterClass();
                    labTestsMasterClass.setId(selectedResults.getString("id"));
                    labTestsMasterClass.setDocName(selectedResults.getString("doctorname"));
                    labTestsMasterClass.setPatientName(selectedResults.getString("patientname"));
                    labTestsMasterClass.setTests(selectedResults.getString("tests"));
                    labTestsMasterClass.setStatus(selectedResults.getString("status"));
                    labTestsMasterClass.setResults(selectedResults.getString("results"));
                    is = selectedResults.getBinaryStream("imageResult");
                    labTestsMasterClass.setResultImage(is);
                    labTestsMasterClassObservableList.add(labTestsMasterClass);
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tabClinicLabTestsTable.setItems(labTestsMasterClassObservableList);
                tabClinicLabTestsTableId.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("id"));
                tabClinicLabTestsTableTechnician.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("docName"));
                tabClinicLabTestsTablePatientName.setCellValueFactory(new PropertyValueFactory<LabTestsMasterClass, String>("patientName"));
                tabClinicLabTestsTableTestType.setCellValueFactory(new PropertyValueFactory<>("tests"));
                tabClinicLabTestsTableStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
                tabClinicLabTestsTable.refresh();
//                labTestsMasterClassObservableList.clear();
            } else {
                showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "FREEDOM", "THERE ARE NO TESTS TO BE DONE BY YOU");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override//conmpleted method
    public void viewPatientDetails() {
        String query = "SELECT * FROM conditions WHERE patientemail=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            if (currentSession.get("currentSession") != null) {
                preparedStatement.setString(1, currentSession.get("currentSession"));
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.isBeforeFirst()) {
                    while (resultSet.next()) {
                        ConditionsMasterClass conditionsMasterClass = new ConditionsMasterClass();
                        conditionsMasterClass.setSize(conditionsMasterClass.getSize() + 1);
                        conditionsMasterClass.setPatientId(resultSet.getString("id"));
                        conditionsMasterClass.setPatientemail(resultSet.getString("patientemail"));
                        conditionsMasterClass.setConditionName(resultSet.getString("conditionName"));
                        conditionsMasterClass.setDate(resultSet.getString("date"));
                        conditionsMasterClass.setCategory(resultSet.getString("category"));
                        conditionsMasterClass.setDoctor(settings.name.get("username"));
                        conditionsMasterClassObservableList.add(conditionsMasterClass);
                    }
                    existingConditionsTabTable.setItems(conditionsMasterClassObservableList);
                    existingConditionsTabTableId.setCellValueFactory(new PropertyValueFactory<>("patientId"));
                    existingConditionsTabTableName.setCellValueFactory(new PropertyValueFactory<>("conditionName"));
                    existingConditionsTabTableDateAdded.setCellValueFactory(new PropertyValueFactory<>("date"));
                    existingConditionsTabTableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
                    existingConditionsTabTableDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
                    existingConditionsTabTable.refresh();
                } else {
                    System.out.println("Error");
                }
            }
        } catch (SQLException e) {
//            System.out.println("Table haiwork");
            e.printStackTrace();
        }

    }

    @Override//completed method
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

    private void getTestReport() {
        //show report from lab
        if (setScenevariables()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("resources/views/Physicians/report.fxml"));
            try {
                Parent parent = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.initStyle(StageStyle.UTILITY);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        reloadTables();
                    }
                });
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean setScenevariables() {
        LabTestsMasterClass labTestsMasterClass = tabClinicLabTestsTable.getSelectionModel().getSelectedItem();
        boolean returnValue = false;


        if (tabClinicLabTestsTable.getSelectionModel().getSelectedCells().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "ERROR", "SELECT ONE ROW TO VIEW THE REPORT");
        } else if (!labTestsMasterClass.getStatus().equalsIgnoreCase("COMPLETE")) {
            showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "ERROR", "TESTS ARE NOT DONE YET");

        } else {
            returnValue = true;
            if (!temporarySession.isEmpty())
                temporarySession.put("temporarySession", labTestsMasterClass.getPatientName());
            else
                temporarySession.replace("temporarySession", labTestsMasterClass.getPatientName());
            if (labTestsMasterClass.getResultImage() != null) {
                if (imageResult.isEmpty()) imageResult.put("resultImage", labTestsMasterClass.getResultImage());
                else {
                    imageResult.replace("resultImage", labTestsMasterClass.getResultImage());

                }
                try {
                    labTestsMasterClass.getResultImage().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                if (resultText.isEmpty()) {
                    resultText.put("resultText", labTestsMasterClass.getResults());

                } else {
                    resultText.replace("resultText", labTestsMasterClass.getResults());

                }
            }
            System.out.println(imageResult.containsKey("resultImage"));

        }
        return returnValue;
    }
    @Override
    public void Patientprescription() {

    }

    private String datepicker(DatePicker dob) {
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

    /**
     * reloads all tables
     */
    private void reloadTables() {
        recordsMasterClassObservableList.clear();
        conditionsMasterClassObservableList.clear();
        appointmentMasterClassObservableList.clear();
        appointmentMasterClassObservableList2.clear();
        labTestsMasterClassObservableList.clear();
        loadSessions();
        viewPatientAppointments();
        viewPatientDetails();
        viewPatientHistory();
        viewPatientLabTests();
    }

}
