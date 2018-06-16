package Buttons;

import static GUI.Main.closeGui;
import static GUI.Main.font;
import static GUI.Main.map;

import GUI.LappGUI;
import GUI.Main;
import Lapp.Lapp;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class LappButton extends Button {

    public LappButton(double x, double y, Lapp lapp) {
        setLayoutY(y);
        setLayoutX(x);
        setStyle("-fx-background-color: transparent;");
        setFont(font);

        if(lapp.isOpen()) {
            setTextFill(Color.GRAY);
        } else {
            setTextFill(Color.BLACK);
        }

        setText(lapp.getOverSkrift());
        if(map.containsKey(lapp)) {
            setTextFill(Color.GRAY);
        }
        setOnMouseEntered(event -> setTextFill(Color.GRAY));
        setOnMouseExited(event -> {
            if(!map.containsKey(lapp)) setTextFill(Color.BLACK);
        });

        setOnAction(event -> {
            if(map.containsKey(lapp)) {
                closeGui(lapp);
                setTextFill(Color.BLACK);
            } else {
               Main.createLappGUI(lapp);
            }
        });
    }


}
