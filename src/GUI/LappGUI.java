package GUI;

import Buttons.CornerButton;
import Lapp.Lapp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static GUI.Main.symbol;


public class LappGUI extends Stage {
    private Lapp lapp;
    private TextArea txt = new TextArea();
    private Group g = new Group();
    private double xOffset, yOffset = 0;

    public LappGUI(Lapp lapp) {
        this.lapp = lapp;
        setTitle(lapp.getOverSkrift());
        setHeight(300);
        setWidth(300);
        initStyle(StageStyle.UNDECORATED);
        show();

        Scene scene = new Scene(g);
        scene.setFill(Color.YELLOW);
        scene.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        scene.setOnMouseDragged(e -> {
            setX(e.getScreenX() - xOffset);
            setY(e.getScreenY() - yOffset);
        });

        setScene(scene);

        makeTextBox();
        makeButtons();

    }

    public void makeTextBox() {
        g.getChildren().add(txt);
        txt.setStyle("-fx-background-color: white;");
        txt.setPrefHeight(270);
        txt.setPrefWidth(300);
        txt.setLayoutY(30);
        this.setResizable(false);
        txt.setText(lapp.getText());
        Font font = new Font(15);
        txt.setFont(font);
    }

    public void makeButtons() {
        CornerButton deleteButton = new CornerButton("ø", 0, -15);
        CornerButton exitButton = new CornerButton("x", 265, -15);
        CornerButton hidebutton = new CornerButton("-", 240, -15);

        deleteButton.setOnMouseClicked(e -> {
            Main.lapper.remove(lapp);
            Main.closeGui(lapp);
        });

        exitButton.setOnMouseClicked(e -> {
            Main.closeGui(lapp);
        });

        hidebutton.setOnMouseClicked(e -> setIconified(true));

        g.getChildren().add(deleteButton);
        g.getChildren().add(exitButton);
        g.getChildren().add(hidebutton);
    }

    public String getText() {
        return txt.getText();
    }
}
