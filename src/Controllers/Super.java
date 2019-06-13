package Controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.Objects;

import static Controllers.settings.login;
import static Controllers.settings.user;

public class Super {
    protected Connection connection;

    {
        try {
            connection = DriverManager
                    .getConnection(settings.des[0], settings.des[1], settings.des[2]);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.showAndWait();
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
                secs = "0" + String.valueOf(seconds);

            }
            clock.setText(hours + ":" + (mins) + ":" + (secs) + " " + pmam);

        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
