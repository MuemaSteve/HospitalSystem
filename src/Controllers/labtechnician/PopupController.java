package Controllers.labtechnician;

import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class PopupController implements Initializable, LabSettings {
    public AnchorPane panel;
    public TextArea tests;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tests.setText(teststext.get("teststext"));
    }
}
