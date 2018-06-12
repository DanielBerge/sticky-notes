package GUI;

import Lapp.Lapp;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import xml.XMLHandler;

import java.util.*;


public class Main extends Application {
    private List<Lapp> lapper;
    private Scene vindu;
    private Group root = new Group();
    private Button nyLapp = new Button();
    private Button exitButton = new Button();
    private Button hidebutton = new Button();
    private int listeStart = 45;
    private Font font = new Font(20);
    private Map map = new HashMap<Lapp, LappGUI>();
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        lapper = XMLHandler.xmlToObject();
        if(lapper == null) {
           lapper = new ArrayList<>();
        }
        vindu = new Scene(root, 300, 300);
        vindu.setFill(Color.YELLOW.desaturate());
        primaryStage.setTitle("Klistrelapper");
        primaryStage.setScene(vindu);
       // primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            saveAndClose();
        });
        print();
    }

    public void makeNewNote() {
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
                lapper.add(new Lapp(tf.getText()));
                root.getChildren().remove(root.getChildren().lastIndexOf(tf));
                print();

            }
        });
    }

    public void print() {
        root.getChildren().clear();
        makeButtons();

        for(Lapp lapp: lapper) {
            Button tmp = new Button();
            tmp.setLayoutY(listeStart);
            listeStart += 30;
            tmp.setLayoutX(0);
            tmp.setText(lapp.getOverSkrift());
            tmp.setStyle("-fx-background-color: transparent;");
            tmp.setFont(font);
            if(map.containsKey(lapp)) {
                tmp.setTextFill(Color.GRAY);
            }
            tmp.setOnMouseEntered(event -> tmp.setTextFill(Color.GRAY));
            tmp.setOnMouseExited(event -> {
                if(!map.containsKey(lapp)) tmp.setTextFill(Color.BLACK);
            });

            tmp.setOnAction(event -> {
                if(map.containsKey(lapp)) {
                    closeGui(lapp);
                    tmp.setTextFill(Color.BLACK);
                } else {
                    tmp.setTextFill(Color.GRAY);
                    LappGUI lappGUI = new LappGUI(lapp);
                    map.put(lapp, lappGUI);
                    lappGUI.setAlwaysOnTop(true);
                    lappGUI.setOnCloseRequest(event1 -> {
                        closeGui(lapp);
                        tmp.setTextFill(Color.BLACK);
                    });
                }
            });
            root.getChildren().add(tmp);
        }
        listeStart = 45;
    }

    public void makeButtons() {
        Font pluss = new Font("+",25);

        nyLapp.setText("+");
        nyLapp.setLayoutX(0);
        nyLapp.setLayoutY(0);
        nyLapp.setTextFill(Color.BLACK);
        nyLapp.setFont(pluss);
        nyLapp.setStyle("-fx-background-color: transparent;");
        nyLapp.setOnMouseEntered(e -> nyLapp.setTextFill(Color.GRAY));
        nyLapp.setOnMouseExited(e -> nyLapp.setTextFill(Color.BLACK));
        nyLapp.setOnMouseClicked(e -> makeNewNote());

        root.getChildren().add(nyLapp);

        exitButton.setText("x");
        exitButton.setLayoutX(265);
        exitButton.setLayoutY(-15);
        exitButton.setFont(pluss);
        exitButton.setStyle("-fx-background-color: transparent;");
        exitButton.setOnMouseEntered(e -> exitButton.setTextFill(Color.GRAY));
        exitButton.setOnMouseExited(e -> exitButton.setTextFill(Color.BLACK));
        exitButton.setOnMouseClicked(e -> {
            saveAndClose();
            primaryStage.close();
        });

        root.getChildren().add(exitButton);

        hidebutton.setText("-");
        hidebutton.setLayoutX(240);
        hidebutton.setLayoutY(-15);
        hidebutton.setFont(pluss);
        hidebutton.setStyle("-fx-background-color: transparent;");
        hidebutton.setOnMouseEntered(e -> hidebutton.setTextFill(Color.GRAY));
        hidebutton.setOnMouseExited(e -> hidebutton.setTextFill(Color.BLACK));
        hidebutton.setOnMouseClicked(e -> {
            primaryStage.setIconified(true);
            Set keys = map.keySet();
            for(Object key: keys) {
                LappGUI gui = (LappGUI)map.get(key);
                gui.setIconified(true);
            }
        });

        root.getChildren().add(hidebutton);
    }

    public void closeGui(Lapp lapp) {
        LappGUI gui = (LappGUI) map.get(lapp);
        lapp.setText(gui.getText()); //Larger teksten
        map.remove(lapp);
        gui.close();
    }

    public void saveAndClose() {
        Set keys = map.keySet();
        List<Lapp> lapp = new ArrayList<>();
        //Dobbelt loop pga concurrentmodification
        for(Object key: keys) {
            lapp.add((Lapp)key);
        }
        for(Lapp la: lapp) {
            closeGui(la);
        }
        XMLHandler.toXML(lapper);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
