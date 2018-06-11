package Lapp;

import GUI.LappGUI;

import java.util.ArrayList;
import java.util.List;

public class Lapp {
    private String overSkrift;
    private String tekst;
    public boolean open = false;
    private LappGUI gui;

    public Lapp(String s) {
        overSkrift = s;
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

    public boolean isOpen() {
        return open;
    }

    public LappGUI getGui() {
        return gui;
    }

    public void setGui(LappGUI gui) {
        this.gui = gui;
    }
}
