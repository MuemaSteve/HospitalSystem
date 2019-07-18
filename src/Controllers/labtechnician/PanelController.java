package Controllers.labtechnician;

import Controllers.Super;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static Controllers.settings.appName;

public class PanelController extends Super implements Initializable {
    public AnchorPane panel;
    public TabPane tabContainer;
    public Tab pendingteststab;
    public TableView pendingTestsTable;
    public TableColumn pendingTestsTableid;
    public TableColumn pendingTestsTabledoctor;
    public TableColumn pendingTestsTablepatientname;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        time(clock);
        title.setText(appName + " Labs");
        buttonListeners();
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
