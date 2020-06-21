package LanguageApp.util;

import LanguageApp.model.Item;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class GetJson {


    /**
     * Read a json file and return an Array of Items object
     *
     * @param fl The file return by filechooser
     * @return An array of item object
     */
    public Item[] getJsonOriginal (File fl) {

        Item[] is = null;
        Gson gson = new Gson();

        try {
            Reader reader = new FileReader(fl);
            
            // Convert JSON File to Java Object
            is = gson.fromJson(reader, Item[].class);

            // Put the id handly
            for (int i = 0; i < is.length; i++) {
                is[i].setId(i);
                is[i].setStart(is[i].getStart() - 0.2);
                is[i].setEnd(is[i].getEnd() + 0.2);
                is[i].setText(is[i].getText());
            }

        } catch (Exception e) {
           // new MainController().message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "GetJson.java / getJsonOriginal()",e);
        }
        return is;
    }


    /**
     * Read a json file and return an Array of Items object It´s necessary to
     * have previously read the json file in English
     *
     * @param fl The English file return by filechooser
     * @return An array of item object
     */
    public Item[] getJsonTranslation (File fl) {

        Item[] is = null;
        Gson gson = new Gson();

        // Getting the absolute path
        String absolutePath = fl.getAbsolutePath();

        // Extractint the name of the file        
        // String nameFile = absolutePath.substring(0, absolutePath.lastIndexOf(fl.separator));

        // Replaacing the extension with spanish.json
        absolutePath = absolutePath.replace(".json", ".tra");

        // Creating a new File spanish
        File file = new File(absolutePath);

        try {
            Reader reader = new FileReader(file);

            // Convert JSON File to Java Object
            is = gson.fromJson(reader, Item[].class);

            // Put the id handly
            for (int i = 0; i < is.length; i++) {
                is[i].setId(i);
                is[i].setStart(is[i].getStart() - 0.2);
                is[i].setEnd(is[i].getEnd() + 0.2);
                is[i].setText(is[i].getText());
               //new MainController().message(Alert.AlertType.ERROR, "Error message", is[i].getText(), "GetJson.java / getJsonTranslation()", null);
            }

        } catch (Exception e) {
            // new MainController().message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "GetJson.java / getJsonTranslation()", e);
        }
        return is;
    }


}
