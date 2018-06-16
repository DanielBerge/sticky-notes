package GUI;

import Buttons.CornerButton;
import Lapp.Lapp;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LappGUI extends Stage {
    private Lapp lapp;
    private TextArea txt = new TextArea();
    private Group g = new Group();
    private double xOffset, yOffset = 0;
    private int rows;

    public LappGUI(Lapp lapp) {
        this.lapp = lapp;
        rows = lapp.getRowLength();
        lapp.setOpen(true);
        setTitle(lapp.getOverSkrift());
        setHeight(300);
        setWidth(300);
        setX(lapp.getPosX());
        setY(lapp.getPosY());
        initStyle(StageStyle.UNDECORATED);

        show();
        this.setResizable(true);

        Scene scene = makeScene();
        setScene(scene);

        makeTextBox();
        makeButtons();
        makeHeader();
    }

    public Scene makeScene() {
        Scene scene = new Scene(g);
        scene.getStylesheets().add("style.css");
        System.out.println(lapp.getColor());
        if(lapp.getColor().equals("yellow")) {
            scene.setFill(Color.YELLOW);
        } else if(lapp.getColor().equals("purple")) {
            scene.setFill(Color.PURPLE);
        }
        scene.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        scene.setOnMouseDragged(e -> {
            double x = e.getScreenX() - xOffset;
            double y = e.getScreenY() - yOffset;
            setX(x);
            setY(y);
            lapp.setPosX(x);
            lapp.setPosY(y);
        });
        return scene;
    }

    public void makeTextBox() {
        g.getChildren().add(txt);
        txt.setPrefWidth(300);
        txt.setPrefHeight(270);
        txt.setLayoutY(30);
        txt.setText(lapp.getText());
        txt.setWrapText(true);
        Font font = new Font(16);
        txt.setFont(font);
        updateHeight();
        txt.textProperty().addListener((observable, oldValue, newValue) -> {
            rows = getRowCount(txt);
            rows = Main.clamp(rows, 0, 30);
            updateHeight();

            String text = txt.getText();
            CharSequence s1 = "* ";
            CharSequence s2 = "• ";
            txt.replaceText(0, txt.getLength(), text.replace(s1, s2));
            lapp.setRowLength(rows);
        });

    }

    public void makeButtons() {
        CornerButton deleteButton = new CornerButton("\uD83D\uDDD1", -10, -11);
        CornerButton exitButton = new CornerButton("⨯", 265, -15);
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

    public void makeHeader() {
        Label label = new Label();
        label.setText(lapp.getOverSkrift());
        label.setLayoutX(40);
        label.setLayoutY(0);
        label.setTextFill(Color.BLACK);
        label.setFont(Main.font);

        g.getChildren().add(label);

    }

    public String getText() {
        return txt.getText();
    }

    private void updateHeight() {
        if(rows < 10) {
            txt.setPrefHeight(270);
            setHeight(300);
        } else {
            double heightForRows = 24.4*(rows-10);
            txt.setPrefHeight(270 + heightForRows);
            setHeight(300 + heightForRows);
        }
    }

    private int getRowCount(TextArea textArea) {
        int currentRowCount = 0;
        Text helper = new Text();
        if(textArea.isWrapText()) {
            // text needs to be on the scene
            Text text = (Text) textArea.lookup(".text");
            if(text == null) {
                return currentRowCount;
            }
            helper.setFont(textArea.getFont());
            for (CharSequence paragraph : textArea.getParagraphs()) {
                helper.setText(paragraph.toString());
                Bounds localBounds = helper.getBoundsInLocal();

                double paragraphWidth = localBounds.getWidth();
                if(paragraphWidth > text.getWrappingWidth()) {
                    double oldHeight = localBounds.getHeight();
                    // this actually sets the automatic size adjustment into motion...
                    helper.setWrappingWidth(text.getWrappingWidth());
                    double newHeight = helper.getBoundsInLocal().getHeight();
                    // ...and we reset it after computation
                    helper.setWrappingWidth(0.0D);

                    int paragraphLineCount = Double.valueOf(newHeight / oldHeight).intValue();
                    currentRowCount += paragraphLineCount;
                } else {
                    currentRowCount += 1;
                }
            }
        } else {
            currentRowCount = textArea.getParagraphs().size();
        }
        return currentRowCount;
    }
}
