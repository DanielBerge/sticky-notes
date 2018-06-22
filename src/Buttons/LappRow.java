package Buttons;

import GUI.Main;
import Lapp.Lapp;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static GUI.Main.closeGui;
import static GUI.Main.map;

public class LappRow {
    private Group g = new Group();
    private LappButton lappbutton;
    private ColorButton colorPicker;
    private Circle circleBack;
    private Rectangle rectangle;
    private Lapp lapp;

    public LappRow(int x, int y, Lapp lapp) {
        this.lapp = lapp;
        lappbutton = new LappButton(0, y, lapp);
        colorPicker = new ColorButton(250, y+22, lapp);
        circleBack = new Circle();
        rectangle = new Rectangle();

        rectangle.setLayoutX(x);
        rectangle.setLayoutY(y+6);
        rectangle.setWidth(300);
        rectangle.setHeight(33);
        rectangle.setFill(Color.YELLOW.desaturate());
        rectangle.setVisible(true);
        rectangle.setOpacity(0.5);
        rectangle.setOnMouseEntered(e -> entered());
        rectangle.setOnMouseExited(e -> exited());
        rectangle.setOnMouseClicked(e -> action());

        lappbutton.setOnMouseEntered(e -> entered());
        lappbutton.setOnMouseExited(e -> exited());
        lappbutton.setOnAction(e -> action());


        circleBack.setFill(Color.BLACK);
        circleBack.setVisible(true);
        circleBack.setCenterX(250);
        circleBack.setCenterY(y+22);
        circleBack.setRadius(14);
        g.getChildren().addAll(rectangle,lappbutton,circleBack, colorPicker);
    }

    public Group getG() {
        return g;
    }

    private void entered() {
        rectangle.setFill(Color.YELLOW);
        lappbutton.setTextFill(Color.GRAY);
    }

    private void exited() {
        rectangle.setFill(Color.YELLOW.desaturate());
        if(!map.containsKey(lapp)) lappbutton.setTextFill(Color.BLACK);
    }

    private void action() {
        if(map.containsKey(lapp)) {
            closeGui(lapp);
            lappbutton.setTextFill(Color.BLACK);
        } else {
            Main.createLappGUI(lapp);
        }
    }
}
