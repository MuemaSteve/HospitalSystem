package Controllers.basic;

import Controllers.settings;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
    public VBox panel;
    public Hyperlink site;
    public Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clickListeners();
    }

    private void clickListeners() {
        back.setOnMouseClicked(event -> {
            panel.getChildren().removeAll();
            try {
//                  panel.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/basic/Help.fxml"))));
                panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/basic/LoginScene.fxml")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        site.setOnMouseClicked(event -> {
            try {
                Desktop.getDesktop().browse(new URL(settings.siteHelp).toURI());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }
}
