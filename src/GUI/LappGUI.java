package GUI;

import Lapp.Lapp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LappGUI extends Stage {
    private Lapp lapp;
    private TextArea txt = new TextArea();
    private Group g = new Group();

    public LappGUI(Lapp lapp) {
        this.lapp = lapp;
        setTitle(lapp.getOverSkrift());
        setHeight(300);
        setWidth(300);
        //initStyle(StageStyle.TRANSPARENT);
        show();

        Scene scene = new Scene(g);
        scene.setFill(Color.YELLOW);
        setScene(scene);

        makeTextBox();
        makeButtons();

    }

    public void makeTextBox() {
        g.getChildren().add(txt);
        txt.setStyle("-fx-background-color: white;");
        txt.setPrefHeight(300);
        txt.setPrefWidth(300);
        this.setResizable(false);
        txt.setText(lapp.getText());
        Font font = new Font(15);
        txt.setFont(font);
    }

    public void makeButtons() {

    }

    public String getText() {
        return txt.getText();
    }
}
