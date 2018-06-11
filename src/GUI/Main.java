package GUI;

import Lapp.Lapp;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    private List<Lapp> lapper = new ArrayList<>();
    private Scene vindu;
    private Group root = new Group();
    private Button nyLapp = new Button();
    private int listeStart = 45;
    private Font font = new Font(20);

    @Override
    public void start(Stage primaryStage) throws Exception{
        vindu = new Scene(root, 300, 300);
        vindu.setFill(Color.YELLOW.desaturate());
        primaryStage.setTitle("Klistrelapper");
        primaryStage.setScene(vindu);
        primaryStage.show();

        print();
    }

    public void lagNy() {
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
        Font pluss = new Font("+",25);

        nyLapp.setText("+");
        nyLapp.setLayoutX(0);
        nyLapp.setLayoutY(0);
        nyLapp.setTextFill(Color.BLACK);
        nyLapp.setFont(pluss);
        nyLapp.setStyle("-fx-background-color: transparent;");
        nyLapp.setOnMouseEntered(e -> nyLapp.setTextFill(Color.GRAY));
        nyLapp.setOnMouseExited(e -> nyLapp.setTextFill(Color.BLACK));
        nyLapp.setOnMouseClicked(e -> lagNy());

        root.getChildren().add(nyLapp);


        for(Lapp lapp: lapper) {
            Button tmp = new Button();
            tmp.setLayoutY(listeStart);
            listeStart += 30;
            tmp.setLayoutX(0);
            tmp.setText(lapp.getOverSkrift());
            tmp.setStyle("-fx-background-color: transparent;");
            tmp.setFont(font);
            tmp.setOnMouseEntered(event -> tmp.setTextFill(Color.GRAY));
            tmp.setOnMouseExited(event -> {
                if(!lapp.isOpen()) tmp.setTextFill(Color.BLACK);
            });

            tmp.setOnAction(event -> {
                if(lapp.isOpen()) {
                    lapp.getGui().close();
                    lapp.setText(lapp.getGui().getText());
                    tmp.setTextFill(Color.BLACK);
                    lapp.open = false;
                } else {
                    tmp.setTextFill(Color.GRAY);
                    lapp.open = true;
                    LappGUI lappGUI = new LappGUI(lapp);
                    lapp.setGui(lappGUI);
                    lappGUI.setAlwaysOnTop(true);
                    lappGUI.setOnCloseRequest(event1 -> {
                        lapp.setText(lappGUI.getText());
                        tmp.setTextFill(Color.BLACK);
                    });
                }
            });
            root.getChildren().add(tmp);
        }
        listeStart = 45;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
