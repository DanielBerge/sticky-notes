package GUI;

import Buttons.CornerButton;
import Lapp.Lapp;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.ResizeHelper;


public class LappGUI extends Stage {
    private Lapp lapp;
    private TextArea txt = new TextArea();
    private Group g = new Group();
    private double xOffset, yOffset = 0;
    private int rows;
    private CornerButton exitButton;
    private CornerButton hideButton;
    private boolean ctrl, u, l = false;
    private Scene scene;

    public LappGUI(Lapp lapp) {
        this.lapp = lapp;
        rows = lapp.getRowLength();
        lapp.setOpen(true);
        setTitle(lapp.getOverSkrift());
        setWidth(lapp.getWidth() + 4);
        setX(lapp.getPosX());
        setY(lapp.getPosY());
        initStyle(StageStyle.UNDECORATED);

        show();
        this.setResizable(true);


        scene = makeScene();
        updateColor();
        setScene(scene);

        ResizeHelper.addResizeListener(this);

        makeTextBox();
        makeButtons();
        makeHeader();
    }

    public Scene makeScene() {
        Scene scene = new Scene(g);
        scene.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        scene.setOnMouseMoved(e -> {
            if (e.getX() < scene.getWidth() - 20) {
                scene.setCursor(Cursor.HAND);
            }
        });
        scene.setOnMouseDragged(e -> {
            if (scene.getCursor() == Cursor.HAND || scene.getCursor() == Cursor.CLOSED_HAND) {
                double x = e.getScreenX() - xOffset;
                double y = e.getScreenY() - yOffset;
                setX(x);
                setY(y);
                lapp.setPosX(x);
                lapp.setPosY(y);
            }
        });
        scene.setOnDragDetected(e -> {
            if (scene.getCursor() == Cursor.HAND)
                scene.setCursor(Cursor.CLOSED_HAND);
        });
        scene.setOnDragDone(e -> scene.setCursor(Cursor.DEFAULT));
        return scene;
    }

    public void makeTextBox() {
        g.getChildren().add(txt);
        txt.setPrefWidth(lapp.getWidth());
        txt.setPrefHeight(270);
        txt.setLayoutY(30);
        txt.setText(lapp.getText());
        txt.setWrapText(true);
        Font font = new Font(16);
        txt.setFont(font);
        updateHeight();
        txt.textProperty().addListener((observable, oldValue, newValue) -> {
            rows = getRowCount(txt);
            rows = Main.clampInt(rows, 0, 30);
            updateHeight();

            String text = txt.getText();
            CharSequence s1 = "* ";
            CharSequence s2 = "• ";
            txt.replaceText(0, txt.getLength(), text.replace(s1, s2));
            lapp.setRowLength(rows);
        });

        txt.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.CONTROL) ctrl = true;
            else if (e.getCode() == KeyCode.U) u = true;
            else if (e.getCode() == KeyCode.L) l = true;
            if (ctrl && u) {
                String newText = txt.getSelectedText();
                newText = newText.toUpperCase();
                txt.replaceText(txt.getSelection(), newText);
            }
            if (ctrl && l) {
                String newText = txt.getSelectedText();
                newText = newText.toLowerCase();
                txt.replaceText(txt.getSelection(), newText);
            }

        });
        txt.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.CONTROL) ctrl = false;
            else if (e.getCode() == KeyCode.U) u = false;
            else if (e.getCode() == KeyCode.L) l = false;
        });

    }

    public void makeButtons() {
        CornerButton deleteButton = new CornerButton("\uD83D\uDDD1", getScene(), -10, -11);
        exitButton = new CornerButton("⨯", getScene(), lapp.getWidth() - 35, -15);
        hideButton = new CornerButton("-", getScene(), lapp.getWidth() - 60, -15);

        deleteButton.setOnMouseClicked(e -> {
            Main.lapper.remove(lapp);
            Main.closeGui(lapp);
        });

        exitButton.setOnMouseClicked(e -> {
            Main.closeGui(lapp);
        });

        hideButton.setOnMouseClicked(e -> setIconified(true));

        g.getChildren().add(deleteButton);
        g.getChildren().add(exitButton);
        g.getChildren().add(hideButton);
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
        if (rows < 10) {
            txt.setPrefHeight(270);
            setHeight(300);
        } else {
            double heightForRows = 24.4 * (rows - 10);
            txt.setPrefHeight(270 + heightForRows);
            setHeight(300 + heightForRows);
        }
    }

    public void updateColor() {
        scene.getStylesheets().clear();
        scene.getStylesheets().add("CSS/style.css");
        if (lapp.getColor().equals("yellow")) {
            scene.setFill(Color.YELLOW);
            scene.getStylesheets().add("CSS/yellow.css");
        } else if (lapp.getColor().equals("purple")) {
            scene.setFill(Color.PURPLE);
            scene.getStylesheets().add("CSS/purple.css");
        } else if (lapp.getColor().equals("blue")) {
            scene.setFill(Color.BLUE);
            scene.getStylesheets().add("CSS/blue.css");
        }
    }

    private int getRowCount(TextArea textArea) {
        int currentRowCount = 0;
        Text helper = new Text();
        if (textArea.isWrapText()) {
            Text text = (Text) textArea.lookup(".text");
            if (text == null) {
                return currentRowCount;
            }
            helper.setFont(textArea.getFont());
            for (CharSequence paragraph : textArea.getParagraphs()) {
                helper.setText(paragraph.toString());
                Bounds localBounds = helper.getBoundsInLocal();

                double paragraphWidth = localBounds.getWidth();
                if (paragraphWidth > text.getWrappingWidth()) {
                    double oldHeight = localBounds.getHeight();
                    helper.setWrappingWidth(text.getWrappingWidth());
                    double newHeight = helper.getBoundsInLocal().getHeight();
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

    public TextArea getTxt() {
        return txt;
    }

    public CornerButton getExitButton() {
        return exitButton;
    }

    public CornerButton getHideButton() {
        return hideButton;
    }
}
