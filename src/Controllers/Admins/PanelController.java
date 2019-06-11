package Controllers.Admins;

import Controllers.Super;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Controllers.settings.*;


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
    private File file;
    private FileInputStream fileInputStream;
    private int length;

    //Administrator panel controller
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
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
        buttonlisteners();
        WebEngine engine = webview.getEngine();//help web page
        engine.load(siteHelp);
    }


    private void buttonlisteners() {
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
            try {
                //logout code
                login.clear();
                user.clear();
                panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/basic/LoginScene.fxml")))));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
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
        if (username.getText().isEmpty() || useremail.getText().isEmpty() || userdescription.getText().isEmpty() || useridentifier.getText().isEmpty()) {
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
                    insertStaff.executeUpdate();
                } else {
                    showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(),
                            "INVALID EMAIL", "PLEASE ENTER A VALID EMAIL");
                }
            }


        }
    }

}
