package xml;

import Lapp.Lapp;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class XMLHandler {
    private static XStream xstream = new XStream(new DomDriver());

    public static void toXML(List<Lapp> lapper) {
        Path path = FileSystems.getDefault().getPath("data.xml");
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.print(e);
        }
        String xml = xstream.toXML(lapper);
        try {
            PrintWriter pw = new PrintWriter("data.xml");
            pw.print(xml);
            pw.close();
        } catch (FileNotFoundException e) {
            //Nothing
        }
    }

    public static List<Lapp> xmlToObject() {
        try {
            StringBuilder builder = new StringBuilder();
            File fil = new File("data.xml");
            if(fil == null) return null;
            Scanner sc = new Scanner(fil);

            while (sc.hasNext()) {
                builder.append(sc.nextLine() + "\n");
            }

            sc.close();
            List<Lapp> xmlObjects = (List<Lapp>)xstream.fromXML(builder.toString());
            return xmlObjects;
        } catch (FileNotFoundException e) {
            //Do nothing
        }
        return null;
    }
}
