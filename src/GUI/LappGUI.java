package GUI;

import Lapp.Lapp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class LappGUI extends Stage {
    private Lapp lapp;
    private  TextArea txt = new TextArea();

    public LappGUI(Lapp lapp) {
        this.lapp = lapp;
        this.setTitle(lapp.getOverSkrift());
        this.show();
        this.setHeight(300);
        this.setWidth(300);
        Group g = new Group();
        Scene scene = new Scene(g);
        scene.setFill(Color.YELLOW);
        this.setScene(scene);



        g.getChildren().add(txt);
        txt.setStyle("-fx-background-color: white;");
        txt.setPrefHeight(300);
        txt.setPrefWidth(300);
        this.setResizable(false);
        txt.setText(lapp.getText());
        Font font = new Font(15);
        txt.setFont(font);
    }

    public String getText() {
        return txt.getText();
    }
}
