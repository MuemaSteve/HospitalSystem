package Controllers;

import Controllers.MasterClasses.RecordsMasterClass;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import static Controllers.settings.*;

public class Super {
    protected Connection localDbConnection;
    protected Connection connection;

    {
        try {
            localDbConnection = DriverManager.getConnection(localDb);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    {
        try {
            connection = DriverManager
                    .getConnection(settings.des[0], settings.des[1], settings.des[2]);
        } catch (SQLException e) {
//            System.out.println("Error connecting to database:" + e.getMessage());
            e.printStackTrace();
        }
    }

    protected boolean isInternetAvailable() throws IOException {
        return isHostAvailable("www.google.com") || isHostAvailable("wwww.amazon.com")
                || isHostAvailable("www.facebook.com") || isHostAvailable("www.apple.com");
    }

    private boolean isHostAvailable(String hostName) throws IOException {
        try (Socket socket = new Socket()) {
            int port = 80;
            InetSocketAddress socketAddress = new InetSocketAddress(hostName, port);
            socket.connect(socketAddress, 13000);

            return true;
        } catch (UnknownHostException unknownHost) {
            return false;
        }
    }

    protected void findInRecordsMethod(AnchorPane panel, ObservableList<RecordsMasterClass> data, TextField findinrecords, TableView<RecordsMasterClass> patienttable, TableColumn<RecordsMasterClass, String> colpatientname, TableColumn<RecordsMasterClass, String> colpatientemail, TableColumn<RecordsMasterClass, String> colpatientnumber) {
        String[] name = {findinrecords.getText()};

        String query = "SELECT * FROM patients WHERE name LIKE '%" + name[0] + "%'";
        try {
            Statement statement = connection.createStatement();
            ResultSet foundrecords = statement.executeQuery(query);
            if (foundrecords.isBeforeFirst()) {

                while (foundrecords.next()) {
                    RecordsMasterClass recordsMasterClass = new RecordsMasterClass();
                    recordsMasterClass.setSize(recordsMasterClass.getSize() + 1);
                    recordsMasterClass.setId(foundrecords.getString("id"));
                    recordsMasterClass.setName(foundrecords.getString("name"));
                    recordsMasterClass.setEmail(foundrecords.getString("email"));
                    recordsMasterClass.setPhonenumber(foundrecords.getString("phonenumber"));
                    data.add(recordsMasterClass);
                }
                patienttable.setItems(data);

                colpatientname.setCellValueFactory(new PropertyValueFactory<>("name"));
                colpatientemail.setCellValueFactory(new PropertyValueFactory<>("email"));
                colpatientnumber.setCellValueFactory(new PropertyValueFactory<>("phonenumber"));
                patienttable.refresh();
            } else {
                showAlert(Alert.AlertType.INFORMATION, panel.getScene().getWindow(), null, "SUCH AN ACCOUNT DOES NOT EXIST");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    /**
     * @param table
     * @param records
     * @param values
     * @return integer of the rows affected by the sql update
     * @throws SQLException
     */
    protected int insertIntoTable(String table, String[] records, String[] values) throws SQLException {
        StringBuilder cols = new StringBuilder(), prepstmts = new StringBuilder();
        int maxIndex = records.length - 1, counter = 0, count = 1;
        for (String record : records
        ) {
//            System.out.println(record);
            if (counter < maxIndex) {
                prepstmts.append("?").append(",");
                cols.append(record).append(",");
            } else {
                prepstmts.append("?");
                cols.append(record);
            }

            counter++;

        }
//        System.out.println(prepstmts + "\n" + cols);
        String query = "";
//        insert into ____ (records)Values(records.length())
//foreach(e : records):
//    counter++.....
        query = "INSERT INTO " + table + "(" + cols.toString() + ")" + "VALUES(" + prepstmts.toString() + ")";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        for (String element : values
        ) {

            preparedStatement.setString(count, element);
            count++;
        }
        return preparedStatement.executeUpdate();

    }

    protected <E extends OriginMasterClass> String tableRowIdSelected(E object, TableView<E> tab) {
        int row = 0;
        object = tab.getSelectionModel().getSelectedItem();
        return object.getId();
    }

    protected ResultSet searchDetails(String preparedQuery, String[] fields) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(preparedQuery);
        int counter = 1;
        for (String x : fields) {
            preparedStatement.setString(counter, x);
            counter += 1;
        }
        return preparedStatement.executeQuery();


    }

    protected void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
    }

    protected String dateTimeMethod() {
        java.util.Date date = new Date(System.currentTimeMillis());
        return date.toString() + "::" + user.get("user");
    }

    protected void logOut(AnchorPane panel) {
        try {
            //logout code
            login.clear();
            user.clear();
            panel.getChildren().setAll(Collections.singleton(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/views/basic/LoginScene.fxml")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void time(Label clock) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            String mins = null, hrs = null, secs = null, pmam = null;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            // Convert timestamp to instant
            Instant instant = timestamp.toInstant();
            // Convert instant to timestamp
            Timestamp tsFromInstant = Timestamp.from(instant);
            int minutes = Integer.parseInt(String.valueOf(tsFromInstant.getMinutes()));
            int seconds = Integer.parseInt(String.valueOf(tsFromInstant.getSeconds()));
            int hours = Integer.parseInt(String.valueOf(tsFromInstant.getHours()));

            if (hours >= 12) {
//                    hrs= "0"+String.valueOf(hours-12);
                pmam = "PM";
            } else {
                pmam = "AM";

            }
            if (minutes > 9) {
                mins = String.valueOf(minutes);
            } else {
                mins = "0" + minutes;

            }
            if (seconds > 9) {
                secs = String.valueOf(seconds);
            } else {
                secs = "0" + seconds;

            }
            clock.setText(hours + ":" + (mins) + ":" + (secs) + " " + pmam);

        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }


    public void LabelInvisible(Label label) {
        PauseTransition visiblePause = new PauseTransition(
                Duration.seconds(8)
        );
        visiblePause.setOnFinished(
                event -> label.setVisible(false)
        );
        visiblePause.play();
    }

    protected void configureView(ArrayList<TabPane> tabPanes) {
        for (TabPane tabpane : tabPanes
        ) {
            double tabWidth = 200.0;
            tabpane.setTabMinWidth(tabWidth);
            tabpane.setTabMaxWidth(tabWidth);
            tabpane.setTabMinHeight(tabWidth - 150.0);
            tabpane.setTabMaxHeight(tabWidth - 150.0);
        }


    }
}
