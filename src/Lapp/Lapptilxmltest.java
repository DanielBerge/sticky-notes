package Lapp;

import GUI.LappGUI;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Lapptilxmltest {
    private static List<Lapp> lapper = new ArrayList<>();

    public static void main(String[] args) {
        Lapp en = new Lapp("Over");
        Lapp to = new Lapp("nr2");
        en.setText("fsdf");
        lapper.add(en);
        lapper.add(to);
        XStream xstream = new XStream(new DomDriver());
        toXML(xstream);
        //System.out.println(fraXML(xstream));
    }

    public static List<Lapp> fraXML(XStream xstream) {
        String xml = "<list>\n" +
                "  <Lapp.Lapp>\n" +
                "    <overSkrift>Over</overSkrift>\n" +
                "    <open>false</open>\n" +
                "  </Lapp.Lapp>\n" +
                "  <Lapp.Lapp>\n" +
                "    <overSkrift>nr2</overSkrift>\n" +
                "    <open>false</open>\n" +
                "  </Lapp.Lapp>\n" +
                "</list>";
        List<Lapp> lapper = (List<Lapp>)xstream.fromXML(xml);
        return lapper;
    }

    public static void toXML(XStream xstream) {
        Path path = FileSystems.getDefault().getPath("data.xml");
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.print(e);
        }

        String xml = xstream.toXML(lapper);
        System.out.println(xml);

        try {
            PrintWriter pw = new PrintWriter("data.xml");
            pw.print(xml);
            pw.close();
        } catch (FileNotFoundException e) {
            //Nothing
        }
    }
}
