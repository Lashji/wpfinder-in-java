package utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.MediaLibrary;
import main.Controller;
import utils.Settings;

import java.io.FileNotFoundException;
import java.util.*;


public class Menus {

    private MediaLibrary medialib;
    private ImageView iv;
    private Settings settings;
    private Controller controller;

    public Menus(MediaLibrary medialib, ImageView iv, Settings settings, Controller controller) {
        this.medialib = medialib;
        this.iv = iv;
        this.settings = settings;
        this.controller = controller;
    }


    public MenuBar getTopMenu() {
        MenuBar mb = new MenuBar();
        mb.getMenus().addAll(menuFile(), menuAdd(), menuMode(), settingsMenu());
//        mb.setStyle("-fx-background-color: gray");
        return mb;
    }

    private Menu menuMode() {
        Menu menuMode = new Menu("Mode");

        MenuItem selectMode = new MenuItem("Select Mode");
        selectMode.setOnAction((e) -> {

            Stage modeStage = new Stage();

            VBox modes = new VBox();


            ToggleGroup tg = new ToggleGroup();
            RadioButton file = new RadioButton("FOLDER");
            file.setUserData("FILE");
            file.setToggleGroup(tg);
            RadioButton imgur = new RadioButton("IMGUR");
            imgur.setUserData("IMGUR");
            imgur.setToggleGroup(tg);


            modes.getChildren().addAll(file, imgur);
            modes.setSpacing(10);
            modes.setAlignment(Pos.CENTER_LEFT);
            modes.setPadding(new Insets(20, 20, 20, 20));

            Button applyBtn = loadApplyButton();


            HBox buttons = new HBox();
            buttons.getChildren().addAll(applyBtn, getCancelButton(modeStage));
            modes.getChildren().add(buttons);

            buttons.setSpacing(10);
            buttons.setAlignment(Pos.CENTER);
            modeStage.setResizable(false);
            modeStage.setScene(new Scene(modes));
            modeStage.show();


        });

        menuMode.getItems().addAll(selectMode);

        return menuMode;

    }

    private Menu menuAdd() {
        Menu menuAdd = new Menu("Add");
        MenuItem addNewAlbum = new MenuItem("Add new Album");

        addNewAlbum.setOnAction((e) -> {

            Stage addStage = new Stage();
            HBox hbox = new HBox();
            Label label = new Label("Add new Album");
            TextField field = new TextField();
            Button add = new Button("Add");


            add.setOnAction((event) -> {


            });

            label.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            hbox.setSpacing(15);
            hbox.setAlignment(Pos.CENTER_LEFT);

            hbox.getChildren().addAll(label, field, add, getCancelButton(addStage));

            addStage.setResizable(false);
            addStage.setScene(new Scene(hbox));
            addStage.show();

        });

        menuAdd.getItems().addAll(addNewAlbum);

        return menuAdd;
    }

    private Menu menuFile() {
        Menu menuFile = new Menu("File");

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((e) -> {
            System.exit(0);
        });

        menuFile.getItems().add(exit);

        return menuFile;
    }

    private Menu settingsMenu() {

        Menu menuSettings = new Menu("Settings");

        MenuItem preferences = new MenuItem("Preferences");

        preferences.setOnAction((e) -> {


        });

        menuSettings.getItems().addAll(preferences);

        return menuSettings;
    }


    private Button getCancelButton(Stage stage) {

        Button cancelBtn = new Button("Cancel");

        cancelBtn.setOnAction((event) -> {
            stage.close();
        });

        return cancelBtn;
    }


    public List<Button> getButtons() {


        Map<String, Button> buttons = new HashMap<>();

        Button next = new Button("Next");
        Button previous = new Button("Previous");
        Button copy = new Button("Save");
        Button setwallpaper = new Button("Set as Wallpaper");


        buttons.put("previous", previous);
        buttons.put("save", copy);
        buttons.put("wallpaper", setwallpaper);
        buttons.put("next", next);

        loadFunctionality(buttons);
        List<Button> list = new ArrayList<>();

        list.add(buttons.get("previous"));
        list.add(buttons.get("save"));
        list.add(buttons.get("wallpaper"));
        list.add(buttons.get("next"));


        return list;
    }

    private Button loadApplyButton() {
        Button applyBtn = new Button("Apply");
        applyBtn.setOnAction((event) -> {

            try {
                settings.writeSettings();
                controller.reload();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        });

        return applyBtn;
    }


    private void loadNextButtonFunc(Button btn) {

        btn.setOnAction((e) -> {

            Image img = this.medialib.getNextImage();
            iv.setImage(img);

        });

    }


    private void loadPreviousButtonFunc(Button btn) {
        btn.setOnAction((e) -> {
            Image img = this.medialib.getPreviousImage();
            iv.setImage(img);


        });


    }

    private void loadSaveFunc(Button btn) {
        btn.setOnAction((e) -> {

            medialib.save();

        });


    }

    private void loadFunctionality(Map<String, Button> buttons) {


        loadNextButtonFunc(buttons.get("next"));
        loadPreviousButtonFunc(buttons.get("previous"));
        loadSaveFunc(buttons.get("save"));
        loadWallpaperFunc(buttons.get("wallpaper"));

    }

    private void loadWallpaperFunc(Button btn) {
        btn.setOnAction((e) -> {
            if (!medialib.saveAsWallpaper()) {
                System.out.println("Saving Failed");
            }
        });


    }

}
