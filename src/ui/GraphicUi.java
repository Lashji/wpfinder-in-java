package ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Controller;

public class GraphicUi {


    public void run(Stage stage){
        BorderPane root = new BorderPane();
        Controller controller = new Controller(stage, root);
        controller.init();
        stage.setTitle("Wallpaper Finder");
        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add("styles/main.css");
        stage.setScene(scene);
        stage.show();
    }
}
