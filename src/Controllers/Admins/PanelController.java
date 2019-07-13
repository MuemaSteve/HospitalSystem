package Controllers.Admins;

import Controllers.MasterClasses.PatientsMasterClass;
import Controllers.MasterClasses.RecordsMasterClass;
import Controllers.MasterClasses.StaffMasterClass;
import Controllers.Super;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Controllers.settings.appName;
import static Controllers.settings.siteHelp;


public class PanelController extends Super implements Initializable {

    public WebView webview;
    public Button adduser;
    public TextArea userdescription;
    public TextField useridentifier;
    public TextField useremail;
    public TextField username;
    public AnchorPane panel;
    public ChoiceBox role;
    public TextField location;
    public Button logout;
    public Button addcertfile;
    public Tab regstaff;
    public Tab patientinfo;
    public Tab staffinfo;
    public Tab news;
    public TextField searchPatientID;
    public Button searchpatientbutton;
    public Label clock;
    public TabPane tabpane;
    public Label title;
    public TableView<StaffMasterClass> viewStaff;
    public TableColumn<StaffMasterClass,String> viewStaffId;
    public TableColumn<StaffMasterClass,String> viewStaffName;
    public TableColumn<StaffMasterClass,String> viewStaffEmail;
    public TableColumn<StaffMasterClass,String> viewStaffIdentity;
    public TableColumn<StaffMasterClass,String> viewStaffBranch;
    public TableColumn<StaffMasterClass,String> viewStaffStatus;
    public Button fire;
    public Button suspend;
    public Button maternity;
    public Button leave;
    public Button shortBreak;
    double tabWidth = 200.0;
    ArrayList<TabPane> tabPaneArrayList = new ArrayList<>();
    private ObservableList<StaffMasterClass> staffMasterClassObservableList = FXCollections.observableArrayList();
    private File file;
    private FileInputStream fileInputStream;
    private int length;

    //Administrator panel controller
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
        //viewStaffInfo();
        title.setText(appName + " Admin Panel ");
    }

    private void init() {
//        if(changepassword.containsKey("change")){
//            if(changepassword.get("change")){
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setHeaderText(null);
//                alert.setTitle("Change your password");
//                alert.setContentText("Please change your default password for your own account privacy");
//                Optional<ButtonType> result = alert.showAndWait();
//                if (result.get() == ButtonType.OK){
//                    PasswordDialog pd = new PasswordDialog();
//                    Optional<String> res = pd.showAndWait();
//                    res.ifPresent(password ->{
//                        out.println(password);} );
//
//
//                }
//            }
//            changepassword.remove("change");
//        }
        tabPaneArrayList.add(tabpane);
        time(clock);
        buttonListeners();
        enterPressed();
        WebEngine engine = webview.getEngine();//help web page
        engine.load(siteHelp);
        configureView(tabPaneArrayList);
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

    private void enterPressed() {

        useridentifier.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                addNewUser();
            }
        });
        useremail.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                addNewUser();
            }
        });
        userdescription.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                addNewUser();
            }
        });
        username.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                addNewUser();
            }
        });

    }

    private void buttonListeners() {
        addcertfile.setOnMouseClicked(event -> {

            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extensionFilterPdf = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().addAll(extensionFilterPdf);
            fileChooser.setTitle("SELECT PDF FILE OF CERTIFICATION");
            //Show open file dialog
            file = fileChooser.showOpenDialog(null);
            length = (int) file.length();

            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        adduser.setOnMouseClicked(event -> {
//            add new user
            addNewUser();
        });
        logout.setOnMouseClicked(event -> {
            logOut(panel);

        });
        fire.setOnMouseClicked(event -> fireUser(viewStaff));
        staffinfo.setOnSelectionChanged(event -> {
            if (staffinfo.isSelected()) {
                System.out.println("Tab is Selected");
                viewStaffInfo();
            }
        });
        suspend.setOnMouseClicked(event -> suspendUser(viewStaff));
        maternity.setOnMouseClicked(event -> giveMaternityToUser(viewStaff));
        leave.setOnMouseClicked(event -> giveLeaveToUser(viewStaff));
        shortBreak.setOnMouseClicked(event -> giveBreakToUser(viewStaff));

    }

    private void addNewUser() {
        try {
            validation();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validation() throws SQLException {
//check if all fields are filled
        if (username.getText().isEmpty() || useremail.getText().isEmpty() || userdescription.getText().isEmpty() || useridentifier.getText().isEmpty() || !file.exists() || file.length() == 0) {
            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(),
                    "FILL ALL FIELDS", "PLEASE FILL ALL FIELDS");

        } else {
            String roleval = role.getValue().toString();
            String locationString = location.getText();
            String name = username.getText();
            String description = userdescription.getText();
            String identification = useridentifier.getText();
            String email = useremail.getText();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE email=?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(), "User exists", "The user email is already used");

            } else {
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (matcher.matches()) {
                    //adding user into database
                    PreparedStatement insertStaff = connection.prepareStatement("INSERT INTO users(name, email, password, hospital, status, userclearancelevel,certification,identification,description) VALUES (?,?,?,?,?,?,?,?,?)");
                    insertStaff.setString(1, name);
                    insertStaff.setString(2, email);
                    insertStaff.setString(3, email);
                    insertStaff.setString(4, locationString.toUpperCase());
                    insertStaff.setString(5, "active");
                    insertStaff.setString(6, roleval);
                    try {
                        insertStaff.setBinaryStream(7, FileUtils.openInputStream(file), length);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    insertStaff.setString(8, identification);
                    insertStaff.setString(9, description);
                    int x = insertStaff.executeUpdate();
                    if (x > 0) {
                        showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), "SUCCESS", "OPERATION SUCCESSFULL");
                        username.clear();
                        userdescription.clear();
                        useremail.clear();
                        location.clear();
                        useridentifier.clear();
                    }
                } else {
                    showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(),
                            "INVALID EMAIL", "PLEASE ENTER A VALID EMAIL");
                }
            }


        }
    }
    public void viewStaffInfo(){
        viewStaff.getItems().clear();
        String query="SELECT * FROM users";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()){
                while (resultSet.next()){
                    StaffMasterClass staffMasterClass=new StaffMasterClass();
                    staffMasterClass.setId(resultSet.getString("id"));
                    staffMasterClass.setName(resultSet.getString("name"));
                    staffMasterClass.setEmail(resultSet.getString("email"));
                    staffMasterClass.setIdentity(resultSet.getString("userclearancelevel"));
                    staffMasterClass.setBranch(resultSet.getString("hospital"));
                    staffMasterClass.setStatus(resultSet.getString("status"));
                    staffMasterClassObservableList.add(staffMasterClass);
                }
                viewStaff.setItems(staffMasterClassObservableList);
                viewStaffBranch.setCellValueFactory(new PropertyValueFactory<>("branch"));
                viewStaffEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
                viewStaffId.setCellValueFactory(new PropertyValueFactory<>("id"));
                viewStaffIdentity.setCellValueFactory(new PropertyValueFactory<>("identity"));
                viewStaffName.setCellValueFactory(new PropertyValueFactory<>("name"));
                viewStaffStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void fireUser(TableView<StaffMasterClass> tableView){
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"DO YOU WANT TO DELETE THE USER", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setTitle("WARNING");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            StaffMasterClass staffMasterClass=tableView.getSelectionModel().getSelectedItem();
            String email=staffMasterClass.getEmail();
            String query="DELETE FROM users WHERE email=?";
            try {
                PreparedStatement preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,email);
                int resultSet=preparedStatement.executeUpdate();
                if (resultSet>0){
                    //viewStaff.getItems().clear();
                    viewStaffInfo();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public void suspendUser(TableView<StaffMasterClass> tableView){
        StaffMasterClass staffMasterClass=tableView.getSelectionModel().getSelectedItem();
        String email=staffMasterClass.getEmail();
        String status="Suspended";
        String query="UPDATE users SET status=? WHERE email=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setString(2,email);
            int resultSet=preparedStatement.executeUpdate();
            if (resultSet>0){
                viewStaffInfo();
            }
        } catch (SQLException e) {
            System.out.println("No Success");
            e.printStackTrace();
        }
    }
    public void giveLeaveToUser(TableView<StaffMasterClass> tableView){
        StaffMasterClass staffMasterClass=tableView.getSelectionModel().getSelectedItem();
        String email=staffMasterClass.getEmail();
        String status="On Leave";
        String query="UPDATE users SET status=? WHERE email=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setString(2,email);
            int resultSet=preparedStatement.executeUpdate();
            if (resultSet>0){
                viewStaffInfo();
            }
        } catch (SQLException e) {
            System.out.println("No Success");
            e.printStackTrace();
        }
    }
    public void giveBreakToUser(TableView<StaffMasterClass> tableView){
        StaffMasterClass staffMasterClass=tableView.getSelectionModel().getSelectedItem();
        String email=staffMasterClass.getEmail();
        String status="On Break";
        String query="UPDATE users SET status=? WHERE email=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setString(2,email);
            int resultSet=preparedStatement.executeUpdate();
            if (resultSet>0){
                viewStaffInfo();
            }
        } catch (SQLException e) {
            System.out.println("No Success");
            e.printStackTrace();
        }
    }
    public void giveMaternityToUser(TableView<StaffMasterClass> tableView){
        StaffMasterClass staffMasterClass=tableView.getSelectionModel().getSelectedItem();
        String email=staffMasterClass.getEmail();
        String status="Maternity Leave";
        String query="UPDATE users SET status=? WHERE email=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,status);
            preparedStatement.setString(2,email);
            int resultSet=preparedStatement.executeUpdate();
            if (resultSet>0){
                viewStaffInfo();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void searchPatient(){

    }

}
