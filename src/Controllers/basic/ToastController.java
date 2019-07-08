package Controllers.basic;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ToastController {

    public static final int TOAST_SUCCESS = 11;
    public static final int TOAST_WARN = 12;
    public static final int TOAST_ERROR = 13;
    public static final int TOAST_HELP = 14;

    @FXML
    private HBox containerToast;

    @FXML
    private Label textToast;

    private void setToast(int toastType, String content) {
        textToast.setText(content);
        switch (toastType) {
            case TOAST_SUCCESS:
                containerToast.setStyle("-fx-background-color: #9FFF96");
                break;
            case TOAST_WARN:
                containerToast.setStyle("-fx-background-color: #FFCF82");
                break;
            case TOAST_ERROR:
                containerToast.setStyle("-fx-background-color: #FF777C");
                break;
            case TOAST_HELP:
                containerToast.setStyle("-fx-background-color: #11f7ff");

        }
    }

    public void showToast(String text, AnchorPane anchorPane, int toastDelay, int fadeInDelay, int fadeOutDelay) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-padding: 50px;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        Stage toastStage = (Stage) anchorPane.getScene().getWindow();
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        fadeInTimeline.setOnFinished((ae) ->
        {
            new Thread(() -> {
                try {
                    Thread.sleep(toastDelay);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                Timeline fadeOutTimeline = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay), new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
                fadeOutTimeline.play();
            }).start();
        });
        fadeInTimeline.play();
    }

}
