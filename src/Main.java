import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        Main.launch(args);
    }

    //launcher of the application
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("resources/views/basic/LoginScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.getIcons().add(new Image("resources/images/22-Cardi-B-Money.png"));
        stage.setOnCloseRequest(event -> Platform.exit());

        stage.setTitle("E-Doctor hospital system");
        stage.setMaxWidth(1200.0);
        stage.setMaxHeight(1000.0);
        stage.setMaximized(false);
        stage.setFullScreen(false);
        stage.show();
    }
}
