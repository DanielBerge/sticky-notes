package GUI;

import Lapp.Lapp;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Set;

public class ColorGUI extends Stage {
    private Group g = new Group();
    private Lapp lapp;

    public ColorGUI(double x, double y, Lapp lapp) {
        this.lapp = lapp;
        setX(x);
        setY(y);
        setHeight(50);
        setWidth(200);
        initStyle(StageStyle.UNDECORATED);

        Scene scene = new Scene(g);
        scene.setFill(Color.YELLOW.saturate());
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
        show();
        makeButtons();
    }

    private void makeButtons() {
        double mellomrom = 20;
        Set colors = Main.colorMap.keySet();
        for(Object key: colors) {
            Circle circle = new Circle();
            circle.setFill((Color)Main.colorMap.get(key));
            circle.setVisible(true);
            circle.setCenterX(20+mellomrom);
            circle.setCenterY(22.5);
            circle.setRadius(20);
            mellomrom += 50;
            circle.setOnMouseClicked(e -> {
                lapp.setColor((String)key);
                close();
                if(Main.map.containsKey(lapp)) {
                    LappGUI lappGUI = (LappGUI)Main.map.get(lapp);
                    lappGUI.updateColor();
                    //Main.closeGui(lapp);
                    //Main.createLappGUI(lapp);
                }
                Main.print();
            });
            g.getChildren().add(circle);
        }

    }
}
