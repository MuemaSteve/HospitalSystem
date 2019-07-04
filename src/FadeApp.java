//import javafx.animation.FadeTransition;
//import javafx.application.Application;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.concurrent.Task;
//import javafx.concurrent.Worker;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Pos;
//import javafx.geometry.Rectangle2D;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.control.ProgressBar;
//import javafx.scene.effect.DropShadow;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.stage.Screen;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import javafx.util.Duration;
//
//import java.io.IOException;
//
///**
// * Example of displaying a splash page for a standalone JavaFX application
// */
//public class FadeApp extends Application {
//    public static final String APPLICATION_ICON =
//            "resources/images/logo.png";
//    public static final String SPLASH_IMAGE =
//            "resources/images/logo.png";
//    private Pane splashLayout;
//    private ProgressBar loadProgress;
//    private Label progressText;
//    private Stage mainStage;
//    private static final int SPLASH_WIDTH = 676;
//    private static final int SPLASH_HEIGHT = 227;
//
//    public static void main(String[] args) throws Exception {
//        launch(args);
//    }
//
//    @Override
//    public void init() {
//        ImageView splash = new ImageView(new Image(
//                SPLASH_IMAGE
//        ));
//        splash.setFitWidth(SPLASH_WIDTH-20);
//        loadProgress = new ProgressBar();
//        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
//        progressText = new Label("Will find friends for peanuts . . .");
//        splashLayout = new VBox();
//        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
//        progressText.setAlignment(Pos.CENTER);
//        splashLayout.setStyle(
//                "-fx-padding: 5; " +
//                        "-fx-background-color: cornsilk; " +
//                        "-fx-border-width:5; " +
//                        "-fx-border-color: " +
//                        "linear-gradient(to right, rgba(110, 243, 255, 0.4), rgba(255, 56, 87, 0.38))"
//        );
//        splashLayout.setEffect(new DropShadow());
//    }
//
//    @Override
//    public void start(final Stage initStage) throws Exception {
//        final Task<ObservableList<String>> listTask = new Task<ObservableList<String>>() {
//            @Override
//            protected ObservableList<String> call() throws InterruptedException {
//                ObservableList<String> foundFriends =
//                        FXCollections.<String>observableArrayList();
//                ObservableList<String> tasksToDo =
//                        FXCollections.observableArrayList(
//                                "Initializing modules","Opening Files","Setting up files","Initiating database sync"
//                        );
//
//                updateMessage("Running task . . .");
//                for (int i = 0; i < tasksToDo.size(); i++) {
//                    Thread.sleep(1400);
//                    updateProgress(i + 1, tasksToDo.size());
//                    String nextTask = tasksToDo.get(i);
//                    foundFriends.add(nextTask);
//                    updateMessage("Running task . . . " + nextTask);
//                }
//                Thread.sleep(1400);
//                updateMessage("All TASKS COMPLETED.");
//
//                return foundFriends;
//            }
//        };
//
//        showSplash(
//                initStage,
//                listTask,
//                () -> {
//                    try {
//                        showMainStage();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//        );
//        new Thread(listTask).start();
//    }
//
//    private void showMainStage(
//    ) throws IOException {        Parent root = FXMLLoader.load(getClass().getResource("resources/views/basic/LoginScene.fxml"));
//
//        mainStage = new Stage(StageStyle.DECORATED);
//        mainStage.getIcons().add(new Image(
//                APPLICATION_ICON
//        ));
//
//
//        mainStage.setScene(new Scene(root));
//        mainStage.show();
//    }
//
//    private void showSplash(
//            final Stage initStage,
//            Task<?> task,
//            InitCompletionHandler initCompletionHandler
//    ) {
//        progressText.textProperty().bind(task.messageProperty());
//        loadProgress.progressProperty().bind(task.progressProperty());
//        task.stateProperty().addListener((observableValue, oldState, newState) -> {
//            if (newState == Worker.State.SUCCEEDED) {
//                loadProgress.progressProperty().unbind();
//                loadProgress.setProgress(1);
////                initStage.toFront();
//                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
//                fadeSplash.setFromValue(1.0);
//                fadeSplash.setToValue(0.0);
//                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
//                fadeSplash.play();
//
//                initCompletionHandler.complete();
//            } // todo add code to gracefully handle other task states.
//        });
//
//        Scene splashScene = new Scene(splashLayout, Color.TRANSPARENT);
//        final Rectangle2D bounds = Screen.getPrimary().getBounds();
//        initStage.setScene(splashScene);
////        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
////        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
//        initStage.setHeight(SPLASH_HEIGHT);
//        initStage.setWidth(SPLASH_WIDTH);
//        initStage.initStyle(StageStyle.TRANSPARENT);
//        initStage.setAlwaysOnTop(true);
//        initStage.show();
//    }
//
//    public interface InitCompletionHandler {
//        void complete();
//    }
//}