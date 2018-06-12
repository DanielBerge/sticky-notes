package Lapp;

public class Lapp {
    private String overSkrift;
    private String tekst;

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

}
