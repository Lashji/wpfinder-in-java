package main;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.MediaLibrary;
import ui.Styles;
import utils.Menus;
import utils.Settings;
import utils.StageBounds;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Controller {

    private final Settings settings;
    private final Stage stage;
    private final BorderPane root;
    private final Styles styles;
    private final MediaLibrary mediaLibrary;
    private final ImageView iv;
    private final Utils utils;
    private final Menus menus;

    public Controller(Stage stage, BorderPane root) {
        this.stage = stage;
        this.root = root;
        this.iv = new ImageView();
        this.styles = new Styles();
        this.settings = new Settings();
        settings.loadSettings();
        this.mediaLibrary = new MediaLibrary(settings);
        this.utils = new Utils();
        this.menus = new Menus(mediaLibrary, iv, settings,  this);

    }


    public void init() {
        VBox settingsTOP = new VBox();
        HBox settingsBottom = new HBox();

        root.setTop(settingsTOP);
        root.setBottom(settingsBottom);

        settingsTOP.getChildren().addAll(this.getTop());
        settingsBottom.getChildren().addAll(this.getBottom());

        Styles.setSettingStyles(settingsTOP, settingsBottom);

        mediaLibrary.init();
        StackPane center = new StackPane();
        iv.setImage(mediaLibrary.getNextImage());

        iv.setPreserveRatio(true);

        iv.fitWidthProperty().bind(stage.widthProperty());
        final StageBounds sb = new StageBounds();

        setImageViewClickResize(center, sb);

        center.getChildren().addAll(iv, settingsBottom);
        root.setCenter(center);
        settingsBottom.setAlignment(Pos.BASELINE_CENTER);

//        stage.setMaximized(true);

    }

    private void setImageViewClickResize(StackPane center, StageBounds sb) {
        center.setOnMouseClicked((e) -> {

            int count = e.getClickCount();
            if (count == 2) {

                if (!stage.isMaximized()) {


                    sb.setX(stage.getX());
                    sb.setY(stage.getY());
                    sb.setWidth(stage.getWidth());
                    sb.setHeight(stage.getHeight());
                }


                if (stage.isMaximized()) {
                    stage.setMaximized(false);

                    stage.setX(sb.getX());
                    stage.setY(sb.getY());
                    stage.setWidth(sb.getWidth());
                    stage.setHeight(sb.getHeight());

                } else {

                    stage.setMaximized(true);
                }

            }

        });
    }

    private MenuBar getTop() {
        MenuBar mb = menus.getTopMenu();


        return mb;

    }

    private Collection<? extends Node> getBottom() {

        List<Node> bottom = new ArrayList<>(menus.getButtons());


        return bottom;

    }

    public void reload() {


    }
}
