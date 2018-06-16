package Lapp;

public class Lapp {
    private String overSkrift;
    private String tekst;
    private double posX, posY;
    private boolean open;
    private int rowLength;
    private String color;

    public Lapp(String s, String color, double posX, double posY) {
        overSkrift = s;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
    }

    public void setText(String s) {
        tekst = s;
    }

    public String getText() {
        return tekst;
    }

    public String getOverSkrift() {
        return overSkrift;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void setRowLength(int rowLength) {
        this.rowLength = rowLength;
    }

    public int getRowLength() {
        return rowLength;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
