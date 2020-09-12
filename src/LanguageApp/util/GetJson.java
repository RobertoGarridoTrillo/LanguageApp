package LanguageApp.util;

import LanguageApp.model.Item;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import javafx.scene.control.Alert;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class GetJson {

   // pop-up messages
   Message message = new Message(HandleLocale01.handleLocale01());

   /**
    * Read a json file and return an Array of Items object
    *
    * @param fl The file return by filechooser
    * @return An array of item object
    */
   public Item[] getJson (File fl)
   {

      Item[] is = null;

      Gson gson = new Gson();

      try {
         Reader reader = new FileReader(fl);

         // Convert JSON File to Java Object
         is = gson.fromJson(reader, Item[].class);

         // Put the id handly
         for (int i = 0; i < is.length; i++) {
            is[i].setId(i);
            is[i].setStart(is[i].getStart());
            is[i].setEnd(is[i].getEnd());
            is[i].setText(is[i].getText());
         }

      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "GetJson.java / getJson()", e.toString(), e);
      }
      return is;
   }
}
