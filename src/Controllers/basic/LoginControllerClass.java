package Controllers.basic;

import Controllers.Super;
import Controllers.settings;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginControllerClass extends Super implements Initializable {
    public VBox panel;
    public TextField name;
    public PasswordField password;
    public Button login;
    public Button help;
    public Label message;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clickListeners();
        enterPressed();
    }

    private void enterPressed() {

        name.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                loginValidation();
            }
        });
        password.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                loginValidation();
            }
        });

    }

    private void clickListeners() {

        login.setOnMouseClicked(event -> {
            loginValidation();

        });

        help.setOnMousePressed(new EventHandler<MouseEvent>() {
            //            got to help page
            @Override
            public void handle(MouseEvent event) {
                panel.getChildren().removeAll();
                try {
                    panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/basic/Help.fxml")))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginValidation() {
        if (name.getText().isEmpty() || password.getText().isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(),
                    "FILL ALL FIELDS", "PLEASE FILL ALL FIELDS");

        } else {
            login();

        }
    }

    //login method
    private void login() {
//        get input text
        String emailSubmit = name.getText();
        String pass = password.getText();
        try {
//            create a connection
//            users:id,name,email,password,status,userclearancelevel

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE email=? OR name=?");
            statement.setString(1, emailSubmit);
            statement.setString(2, emailSubmit);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    if (Objects.equals(resultSet.getString("status"), "active")) {
                        //if account exists and password matches hashed password
                        if ((resultSet.getString("password").equals(pass))) {
                            if (resultSet.getString("userclearancelevel").equalsIgnoreCase("admin")) {
//if user account is admin
                                panel.getChildren().removeAll();
                                try {
//                                    go to admin panel
                                    panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/Admins/panel.fxml")))));
                                    assert false;
//                                    work as sessions and hold user session data
                                    settings.login.put("loggedinasadmin", true);
                                    settings.user.put("user", resultSet.getString("email"));
                                    settings.hospital.put("hospital_name", resultSet.getString("hospital"));
                                    if (resultSet.getString("email").equals(resultSet.getString("password"))) {
                                        settings.changepassword.put("change", true);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (resultSet.getString("userclearancelevel").equalsIgnoreCase("doctor")) {
//                                user is not admin go to doctor panel
                                panel.getChildren().removeAll();
                                try {
                                    panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/Physicians/panel.fxml")))));
                                    assert false;
                                    //                                    work as sessions and hold user session data
                                    assert false;
//                                    work as sessions and hold user session data
                                    settings.login.put("loggedinasdoctor", true);
                                    settings.user.put("user", resultSet.getString("email"));
                                    if (resultSet.getString("email").equals(resultSet.getString("password"))) {
                                        settings.changepassword.put("change", true);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (resultSet.getString("userclearancelevel").equalsIgnoreCase("receptionist")) {
//                                user is not admin go to receptionist panel
                                panel.getChildren().removeAll();
                                try {
                                    panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/Receptionist/panel.fxml")))));
                                    assert false;
                                    //                                    work as sessions and hold user session data
                                    assert false;
//                                    work as sessions and hold user session data
                                    if (resultSet.getString("email").equals(resultSet.getString("password"))) {
                                        settings.changepassword.put("change", true);
                                    }
                                    settings.login.put("loggedinasreceptionist", true);
                                    settings.user.put("user", resultSet.getString("email"));


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
//                            if passwords do not match
                            showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(),
                                    "WRONG PASSWORD!!", "ENTER THE CORRECT PASSWORD");

                        }
                    } else {
                        showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(),
                                "You are " + resultSet.getString("status") + "!!", "Your account is " + resultSet.getString("status") + "!!".toUpperCase());

                    }
                }

            } else {
                showAlert(Alert.AlertType.WARNING, panel.getScene().getWindow(),
                        "WRONG NAME/EMAIL !!", "PLEASE RE-ENTER A VALID USER NAME OR EMAIL");
//name or email does not exist
            }

        } catch (Exception e) {
            e.printStackTrace();
            message.setText("CHECK YOUR CONNECTION!!");
        }
    }


}
