package main;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.GraphicUi;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        GraphicUi ui = new GraphicUi();
        ui.run(primaryStage);


    }


    public static void main(String[] args) {
        launch(args);
    }
}
