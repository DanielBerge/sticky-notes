package GUI;

import Buttons.CornerButton;
import Buttons.LappButton;
import Lapp.Lapp;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xml.XMLHandler;

import java.util.*;

public class Main extends Application {
    public static final Font symbol = new Font(25);
    public static List<Lapp> lapper;
    private static Scene vindu;
    private static Group root = new Group();
    private static int mellomrom = 45;
    public static Font font = new Font(20);
    public static Map map = new HashMap<Lapp, LappGUI>();
    private static Stage primaryStage;
    private static double xOffset, yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        lapper = XMLHandler.xmlToObject(); //Henter data fra data.xml hvis finnes
        if(lapper == null) {
           lapper = new ArrayList<>();
        }
        for(Lapp lapp: lapper) {
            if (lapp.isOpen()) {
                createLappGUI(lapp);
            }
        }

        makeScene();
        primaryStage.setTitle("Klistrelapper");
        primaryStage.setScene(vindu);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            saveAndClose();
        });
        print();
    }

    public static void makeScene() {
        vindu = new Scene(root, 300, 300);
        vindu.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        vindu.setOnMouseDragged(e -> {
            primaryStage.setX(e.getScreenX() - xOffset);
            primaryStage.setY(e.getScreenY() - yOffset);
        });
        vindu.setFill(Color.YELLOW.desaturate());
    }

    public static void makeNewNote() {
        TextField tf = new TextField();
        tf.setFont(font);
        tf.setStyle("-fx-background-color: white;");
        tf.setOpacity(0.7);
        tf.setLayoutX(10);
        tf.setLayoutY(10);
        root.getChildren().add(tf);
        tf.requestFocus();
        root.getChildren().remove(0);
        root.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();
            if(keyCode == KeyCode.ENTER) {
                if(tf.getText().equals("")) {
                    root.getChildren().remove(root.getChildren().lastIndexOf(tf));
                    print();
                    return;
                }
                lapper.add(new Lapp(tf.getText(),"yellow" ,
                        primaryStage.getX()+350 ,primaryStage.getY()-50));
                root.getChildren().remove(root.getChildren().lastIndexOf(tf));
                print();

            }
        });
    }

    public static void print() {
        root.getChildren().clear();
        makeButtons();

        for(Lapp lapp: lapper) {
            LappButton lappbutton = new LappButton(0, mellomrom, lapp);
            mellomrom += 30;

            root.getChildren().add(lappbutton);
        }
        mellomrom = 45;
    }

    public static void makeButtons() {
        CornerButton nyLapp = new CornerButton("+", 0, 0);
        CornerButton exitButton = new CornerButton("⨯", 265, -15);
        CornerButton hidebutton = new CornerButton("-", 245, -15);

        nyLapp.setOnMouseClicked(e -> makeNewNote());

        exitButton.setOnMouseClicked(e -> {
            saveAndClose();
            primaryStage.close();
        });

        hidebutton.setOnMouseClicked(e -> {
            primaryStage.setIconified(true);
            Set keys = map.keySet();
            for(Object key: keys) {
                LappGUI gui = (LappGUI)map.get(key);
                gui.setIconified(true);
            }
        });

        root.getChildren().add(nyLapp);
        root.getChildren().add(exitButton);
        root.getChildren().add(hidebutton);
    }

    public static void closeGui(Lapp lapp) {
        lapp.setOpen(false);
        LappGUI gui = (LappGUI) map.get(lapp);
        lapp.setText(gui.getText()); //Larger teksten
        map.remove(lapp);
        gui.close();
        print();
    }

    public static void saveAndClose() {
        Set keys = map.keySet();
        List<Lapp> lapp = new ArrayList<>();
        //Dobbelt loop pga concurrentmodification
        for(Object key: keys) {
            lapp.add((Lapp)key);
        }
        for(Lapp la: lapp) {
            if(la.isOpen()) {
                closeGui(la);
                la.setOpen(true);
            }
        }
        XMLHandler.toXML(lapper);
    }

    public static void createLappGUI(Lapp lapp) {
        LappGUI lappGUI = new LappGUI(lapp);
        map.put(lapp, lappGUI);
        lappGUI.setOnCloseRequest(event1 -> {
            closeGui(lapp);
        });
    }

    public static int clamp(int var, int min, int max) {
        if(var >= max) {
            return var = max;
        } else if(var <= min) {
            return var = min;
        } else {
            return var;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
