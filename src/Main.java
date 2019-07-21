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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static Controllers.settings.APPLICATION_ICON;
import static Controllers.settings.localDb;


public class Main extends Application {


    private static final int SPLASH_WIDTH = 676;
    private static final int SPLASH_HEIGHT = 227;
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private Stage mainStage;

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
                        FXCollections.observableArrayList();
                ObservableList<String> tasksToDo =
                        FXCollections.observableArrayList(
                                "Initializing modules", "Setting up files", "Opening Files", "Initiating database", "Synchronising databases"
                        );

                updateMessage("Running task . . .");
                for (int i = 0; i < tasksToDo.size(); i++) {
                    Thread.sleep(4);
                    updateProgress(i + 1, tasksToDo.size());
                    String nextTask = tasksToDo.get(i);
                    observableArrayList.add(nextTask);
                    updateMessage("Running task . . . " + nextTask);
                    if (i == 3) {
//                     create sqlite tables and db
                        createLocalDb();
                    } else if (i == 0) {
                        try {
                            getConnection();
                        } catch (SQLException e) {
                            System.out.println("COULD NOT CONNECT TO LOCAL DB");
                        }
                    }
                }
                Thread.sleep(4);
                updateMessage("All TASKS COMPLETED.");

                return observableArrayList;
            }

            private void createLocalDb() {
                Connection connection = null;
                try {
//            create SESSION DB
                    connection = getConnection();
                    Statement statement = connection.createStatement();
                    statement.setQueryTimeout(30); // set timeout to 30 sec.
                    String sessions = "CREATE TABLE IF NOT EXISTS SessionPatients (" + "id INTEGER primary key autoincrement ,name TEXT ,email TEXT,sessionId  TEXT)";
                    statement.executeUpdate(sessions);

                    String sessionsLab = "CREATE TABLE IF NOT EXISTS SessionLabs (" + "id INTEGER primary key autoincrement ,name TEXT ,email TEXT,sessionId  TEXT,testText TEXT)";
                    statement.executeUpdate(sessionsLab);
                } catch (SQLException e) {
                    // if the error message is "out of memory",
                    // it probably means no securityandtime file is found
                    System.err.println(e.getMessage());
                } finally {
                    try {
                        if (connection != null)
                            connection.close();
                    } catch (SQLException e) {
                        // connection close failed.
                        e.printStackTrace();
                    }
                }
            }

            private Connection getConnection() throws SQLException {
                return DriverManager.getConnection(localDb);

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
        mainStage.setMaxHeight(700);
        mainStage.setMaxWidth(1200);
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
                initStage.toFront();
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