package Lapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LappList {
    private List<Lapp> lapps = new ArrayList<>();

    public int size() {
        return lapps.size();
    }

    public void add(Lapp lapp) {
        lapps.add(lapp);
    }

    public Lapp get(int i) {
        return lapps.get(i);
    }

    public List<Lapp> getList() {
        return lapps;
    }

    public void remove(Lapp lapp) {
        lapps.remove(lapp);
    }
}
