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
import java.util.ArrayList;
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
    public TabPane tabcontainerhistorypane;
    public TabPane tabcontainerclinicpane;
    private ObservableList<RecordsMasterClass> data = FXCollections.observableArrayList();
    private ArrayList<TabPane> tabPaneArrayList = new ArrayList<>();
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        tabPaneArrayList.add(tabContainer);
        tabPaneArrayList.add(tabcontainerclinicpane);
        tabPaneArrayList.add(tabcontainerhistorypane);
        configureView(tabPaneArrayList);
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

    private void configureView(ArrayList<TabPane> tabPanes) {
        for (TabPane tabpane : tabPanes
        ) {
            double tabWidth = 200.0;
            tabpane.setTabMinWidth(tabWidth);
            tabpane.setTabMaxWidth(tabWidth);
            tabpane.setTabMinHeight(tabWidth - 150.0);
            tabpane.setTabMaxHeight(tabWidth - 150.0);
        }


    }

    @Override
    protected void findInRecordsMethod(AnchorPane panel, ObservableList<RecordsMasterClass> data, TextField findinrecords, TableView<RecordsMasterClass> patienttable, TableColumn<RecordsMasterClass, String> colpatientname, TableColumn<RecordsMasterClass, String> colpatientemail, TableColumn<RecordsMasterClass, String> colpatientnumber) {
        super.findInRecordsMethod(panel, data, findinrecords, patienttable, colpatientname, colpatientemail, colpatientnumber);
    }
}
