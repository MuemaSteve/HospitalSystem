package Controllers.Physicians;

import Controllers.RecordsMasterClass;
import Controllers.Super;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static Controllers.settings.appName;

public class PanelController extends Super implements Initializable {
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
    public TextField findinrecords;
    public Button findinrecordsbutton;
    public TableView<RecordsMasterClass> patienttable;
    public TableColumn<RecordsMasterClass, String> colpatientname;
    public TableColumn<RecordsMasterClass, String> colpatientemail;
    public TableColumn<RecordsMasterClass, String> colpatientnumber;
    private ObservableList<RecordsMasterClass> data = FXCollections.observableArrayList();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        configureView();
        time(clock);
        title.setText(appName + " Clinic Panel");
        buttonListeners();
    }

    private void buttonListeners() {
        logout.setOnMouseClicked(event -> logOut(panel));
        findinrecordsbutton.setOnMouseClicked(event -> {
            if (data.size() > 0) {
                data.clear();
            }
            findInRecordsMethod(panel, data, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
        });
    }

    private void configureView() {
        double tabWidth = 200.0;
        tabContainer.setTabMinWidth(tabWidth);
        tabContainer.setTabMaxWidth(tabWidth);
        tabContainer.setTabMinHeight(tabWidth - 100.0);
        tabContainer.setTabMaxHeight(tabWidth - 100.0);

    }

    @Override
    protected void findInRecordsMethod(AnchorPane panel, ObservableList<RecordsMasterClass> data, TextField findinrecords, TableView<RecordsMasterClass> patienttable, TableColumn<RecordsMasterClass, String> colpatientname, TableColumn<RecordsMasterClass, String> colpatientemail, TableColumn<RecordsMasterClass, String> colpatientnumber) {
        super.findInRecordsMethod(panel, data, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
    }
}
