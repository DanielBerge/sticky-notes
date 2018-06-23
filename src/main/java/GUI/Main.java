package GUI;

import Buttons.CornerButton;
import Buttons.LappRow;
import Lapp.Lapp;
import Lapp.LappList;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import storage.JsonHandler;

import java.io.File;
import java.util.*;

public class Main extends Application {
    public static final Font symbol = new Font(25);
    public static LappList lapper;
    private static Scene vindu;
    private static Group root = new Group();
    private static int mellomrom = 45;
    public static Font font = new Font(20);
    public static Map map = new HashMap<Lapp, LappGUI>();
    public static Map colorMap = new HashMap<String, Color>();
    public static Stage primaryStage;
    private static double xOffset, yOffset = 0;
    private static JsonHandler jsonHandler = new JsonHandler("storage");


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        File file = jsonHandler.getFile("Output.json");
        if(!file.exists()) {
            lapper = new LappList();
        } else {
            lapper = jsonHandler.load("Output.json", LappList.class);
        }
        for(Lapp lapp: lapper.getList()) {
            if (lapp.isOpen()) {
                createLappGUI(lapp);
            }
        }

        makeScene();
        initColors();
        primaryStage.setTitle("Klistrelapper");
        primaryStage.setScene(vindu);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            saveAndClose();
        });
        print();
    }

    private static void makeScene() {
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

    private static void makeNewNote() {
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

        for(Lapp lapp: lapper.getList()) {
            LappRow lappRow = new LappRow(0, mellomrom, lapp);
            root.getChildren().add(lappRow.getG());
            mellomrom += 35;
        }
        mellomrom = 45;
        double height = (mellomrom-8)*lapper.size()+40;
        if(height < 300) {
            primaryStage.setHeight(300);
        } else {
            primaryStage.setHeight(height);
        }
    }

    private static void makeButtons() {
        CornerButton nyLapp = new CornerButton("+", vindu,0, 0);
        CornerButton exitButton = new CornerButton("⨯", vindu, 265, -15);
        CornerButton hideButton = new CornerButton("-", vindu, 245, -15);

        nyLapp.setOnMouseClicked(e -> makeNewNote());

        exitButton.setOnMouseClicked(e -> {
            saveAndClose();
            primaryStage.close();
        });

        hideButton.setOnMouseClicked(e -> {
            primaryStage.setIconified(true);
            Set keys = map.keySet();
            for(Object key: keys) {
                LappGUI gui = (LappGUI)map.get(key);
                gui.setIconified(true);
            }
        });

        root.getChildren().add(nyLapp);
        root.getChildren().add(exitButton);
        root.getChildren().add(hideButton);
    }

    public static void closeGui(Lapp lapp) {
        lapp.setOpen(false);
        LappGUI gui = (LappGUI) map.get(lapp);
        if(gui == null) {
            return;
        }
        lapp.setText(gui.getText()); //Larger teksten
        lapp.setWidth(gui.getWidth()-4);
        map.remove(lapp);
        gui.close();
        print();
    }

    public static void saveAndClose() {
        Set keys = map.keySet();
        List<Lapp> lapp = new ArrayList<>();
        //Dobbelt loop pga concurrentmodification
        for(Object key: keys)
            lapp.add((Lapp)key);
        for(Lapp la: lapp) {
            if(la.isOpen()) {
                closeGui(la);
                la.setOpen(true);
            }
        }
        jsonHandler.save("Output.json", lapper);
    }

    public static void createLappGUI(Lapp lapp) {
        LappGUI lappGUI = new LappGUI(lapp);
        map.put(lapp, lappGUI);
        lappGUI.setOnCloseRequest(event1 -> {
            closeGui(lapp);
        });
    }

    public static int clampInt(int var, int min, int max) {
        if(var >= max) {
            return max;
        } else if(var <= min) {
            return min;
        } else {
            return var;
        }
    }

    public static double clampDo(double var, double min, double max) {
        if(var >= max) {
            return max;
        } else if(var <= min) {
            return min;
        } else {
            return var;
        }
    }

    private static void initColors() {
        colorMap.put("yellow", Color.YELLOW.desaturate());
        colorMap.put("purple", Color.PURPLE.desaturate());
        colorMap.put("blue", Color.BLUE.darker());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
