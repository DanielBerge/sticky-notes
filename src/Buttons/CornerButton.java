package Buttons;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import static GUI.Main.symbol;

public class CornerButton extends Button {

    public CornerButton(String tekst, double x, double y) {
        setText(tekst);
        setLayoutX(x);
        setLayoutY(y);
        setTextFill(Color.BLACK);
        setFont(symbol);
        setStyle("-fx-background-color: transparent;");
        setOnMouseEntered(e -> setTextFill(Color.GRAY));
        setOnMouseExited(e -> setTextFill(Color.BLACK));
    }
}
