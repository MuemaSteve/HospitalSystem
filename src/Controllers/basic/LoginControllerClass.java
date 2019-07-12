package Controllers.basic;

import Controllers.Super;
import Controllers.settings;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

import static Controllers.settings.APPLICATION_ICON;
import static Controllers.settings.appName;

public class LoginControllerClass extends Super implements Initializable {
    public AnchorPane panel;
    public TextField name;
    public PasswordField password;
    public Button login;
    public Button help;
    public Label message;
    public Label title;
    public ImageView logo1;
    public ImageView logo2;
    public ImageView logo3;

    ToastController toastController = new ToastController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            clickListeners();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        setLogos();
        title.setText(appName + " Login");
        LabelInvisible(message);
    }

    private void setLogos() {
        logo1.setImage(new Image(APPLICATION_ICON));
        logo2.setImage(new Image(APPLICATION_ICON));
        logo3.setImage(new Image(APPLICATION_ICON));

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

    private void clickListeners() throws SocketException {

        login.setOnMouseClicked(event -> {
            loginValidation();

        });

        help.setOnMousePressed(new EventHandler<MouseEvent>() {
            //            got to help page


            @Override
            public void handle(MouseEvent event) {


                try {
                    if (isInternetAvailable()) {

                        panel.getChildren().removeAll();
                        try {
                            panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/basic/Help.fxml")))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showAlert(Alert.AlertType.ERROR, panel.getScene().getWindow(), "CONNECTION ERROR", "Looks Like your machine is offline");
                    }
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
                                    settings.login.put("loggedinasadmin", true);
                                    settings.user.put("user", resultSet.getString("email"));
                                    settings.id.put("userid", resultSet.getString("id"));
                                    settings.hospital.put("hospital_name", resultSet.getString("hospital"));
                                    panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/Admins/panel.fxml")))));
                                    assert false;
//                                    work as sessions and hold user session data

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
                                    settings.hospital.put("hospital_name", resultSet.getString("hospital"));
                                    settings.login.put("loggedinasdoctor", true);
                                    settings.user.put("user", resultSet.getString("email"));
                                    settings.id.put("userid", resultSet.getString("id"));

                                    panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/Physicians/panel.fxml")))));
                                    assert false;
                                    //                                    work as sessions and hold user session data
                                    assert false;
//                                    work as sessions and hold user session data

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
                                    settings.login.put("loggedinasreceptionist", true);
                                    settings.user.put("user", resultSet.getString("email"));
                                    settings.id.put("userid", resultSet.getString("id"));

                                    settings.hospital.put("hospital_name", resultSet.getString("hospital"));

                                    panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/Receptionist/panel.fxml")))));
                                    assert false;
                                    //                                    work as sessions and hold user session data
                                    assert false;
//                                    work as sessions and hold user session data
                                    if (resultSet.getString("email").equals(resultSet.getString("password"))) {
                                        settings.changepassword.put("change", true);
                                    }


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
//            toastController.showToast("CHECK YOUR CONNECTION!!",panel,6000,1000,1000);
            message.setText("CHECK YOUR CONNECTION!!");
            message.setVisible(true);
            LabelInvisible(message);
        }
    }


}
