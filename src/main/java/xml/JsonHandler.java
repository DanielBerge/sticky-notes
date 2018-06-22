package xml;

import GUI.Main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Lapp.Lapp;
import Lapp.LappList;

import java.io.*;
import java.util.List;

public class JsonHandler<T> {

    public void listToJson(LappList obj) {
        try (Writer writer = new FileWriter("Output.json")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(obj, writer);
        } catch (IOException e) {}
    }

    public LappList JsonToList() {
        try {
            Reader reader = new InputStreamReader(new FileInputStream("Output.json" ));
            Gson gson = new GsonBuilder().create();
            return gson.fromJson(reader, LappList.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
