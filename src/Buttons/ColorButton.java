package Buttons;

import GUI.ColorGUI;
import GUI.Main;
import Lapp.Lapp;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ColorButton extends Circle{
    private Color color;

    public ColorButton(int x, int y, Lapp lapp) {
        setCenterX(x);
        setCenterY(y);
        setRadius(13);
        setVisible(true);

        color = (Color)Main.colorMap.get(lapp.getColor());
        setFill(color);

        setOnMouseEntered(e -> setFill(color.saturate()));
        setOnMouseExited(e -> setFill(color));
        setOnMouseClicked(e -> new ColorGUI(Main.primaryStage.getX() + x, Main.primaryStage.getY()+y, lapp));
    }
}
