package ui;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Styles {


    public static void setSettingStyles(VBox settingsTOP, HBox settingsBottom) {

        settingsTOP.setSpacing(10);
        settingsBottom.setSpacing(10);
        settingsBottom.setAlignment(Pos.CENTER);
    }
}
