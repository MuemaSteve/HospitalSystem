import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

import static Controllers.settings.APPLICATION_ICON;


public class Main extends Application {


    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private Stage mainStage;
    private static final int SPLASH_WIDTH = 676;
    private static final int SPLASH_HEIGHT = 227;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image(
                APPLICATION_ICON
        ));
        splash.setFitWidth(SPLASH_WIDTH - 20);
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label();
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
                "-fx-padding: 5; " +
                        "-fx-background-color: #a9ff83; " +
                        "-fx-border-width:5; " +
                        "-fx-border-color: " +
                        "linear-gradient(to right, rgba(110, 243, 255, 0.4), rgba(255, 56, 87, 0.38))"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) {
        final Task<ObservableList<String>> listTask = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws InterruptedException {
                ObservableList<String> observableArrayList =
                        FXCollections.<String>observableArrayList();
                ObservableList<String> tasksToDo =
                        FXCollections.observableArrayList(
                                "Initializing modules", "Opening Files", "Setting up files", "Initiating database sync"
                        );

                updateMessage("Running task . . .");
                for (int i = 0; i < tasksToDo.size(); i++) {
                    Thread.sleep(1400);
                    updateProgress(i + 1, tasksToDo.size());
                    String nextTask = tasksToDo.get(i);
                    observableArrayList.add(nextTask);
                    updateMessage("Running task . . . " + nextTask);
                }
                Thread.sleep(1400);
                updateMessage("All TASKS COMPLETED.");

                return observableArrayList;
            }
        };

        showSplash(
                initStage,
                listTask,
                () -> {
                    try {
                        showMainStage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        new Thread(listTask).start();
    }

    private void showMainStage(
    ) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("resources/views/basic/LoginScene.fxml"));
        FadeTransition ft = new FadeTransition(Duration.millis(2500), root);
        ft.setFromValue(0.3);
        ft.setToValue(1.0);
        ft.play();
        mainStage = new Stage(StageStyle.DECORATED);
        mainStage.getIcons().add(new Image(
                APPLICATION_ICON
        ));

        mainStage.setResizable(false);
        mainStage.setScene(new Scene(root));
        mainStage.show();
    }

    private void showSplash(
            final Stage initStage,
            Task<?> task,
            InitCompletionHandler initCompletionHandler
    ) {

        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
//                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();

                initCompletionHandler.complete();
            }
        });

        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
        initStage.setScene(splashScene);
        initStage.getIcons().add(new Image(APPLICATION_ICON));
        initStage.setResizable(false);
        initStage.setHeight(SPLASH_HEIGHT);
        initStage.setWidth(SPLASH_WIDTH);
        initStage.initStyle(StageStyle.TRANSPARENT);
        initStage.setAlwaysOnTop(true);
        initStage.show();
    }

    public interface InitCompletionHandler {
        void complete();
    }
}