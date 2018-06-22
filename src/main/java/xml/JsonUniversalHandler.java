package xml;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonUniversalHandler {

    private String directory;

    public JsonUniversalHandler(String directory) {
        this.directory = directory;
    }

    public void save(String fileName, Object value) {
        Gson gson = getGson();
        String json = gson.toJson(value);
        saveAsFile(fileName, json);
    }

    public <T> T load(String fileName, Class<T> clazz) {
        String fileText = loadFile(fileName);
        Gson gson = getGson();
        return gson.fromJson(fileText, clazz);
    }

    private void saveAsFile(String fileName, String json){
        File file = getFile(fileName);
        try {
            FileUtils.writeStringToFile(file, json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String loadFile(String fileName){
        try(InputStream inputStream = new FileInputStream(getFile(fileName))){
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File getFile(String fileName){
        File folder = new File(directory);
        if(!folder.exists())
            folder.mkdir();
        return new File(folder, fileName);
    }

    public String getStorageDirectory() {
        return directory;
    }

    private Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        return gsonBuilder.create();
    }

}