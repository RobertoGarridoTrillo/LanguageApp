package LanguageApp.util;

import LanguageApp.model.Item;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;


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
    public Item[] getJson (File fl) {

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
           message(Alert.AlertType.ERROR, "Error message", "GetJson.java / getJsonOriginal()", e.toString(), e);
        }
        return is;
    }

//<editor-fold defaultstate="collapsed" desc="Executing Emergentes messages">
   /**
    * show a standard emergent message
    *
    * @param alertType alertType.CONFIRMATION, ERROR, INFORMATION, NONE, WARNING
    * @param title The title of the windows
    * @param about The them to expose
    * @param contextText The showed text
    * @param ex The thrown exception
    */
   public void message (Alert.AlertType alertType, String title, String about, String contextText, Exception ex)
   {

      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      //lert.getDialogPane().setMinWidth(600);
      //alert.getDialogPane().setMinHeight(480);
      //alert.getDialogPane().setPrefWidth(600);
      //alert.getDialogPane().setPrefHeight(480);
      alert.setResizable(true);
      alert.getDialogPane().setHeaderText(about);
      alert.getDialogPane().setContentText(contextText);

      if (ex != null) {
         // Create expandable Exception.
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         ex.printStackTrace(pw);
         String exceptionText = sw.toString();

         Label label = new Label("El seguimiento del error fue:");

         TextArea textArea = new TextArea(exceptionText);
         textArea.setEditable(true);
         textArea.setWrapText(true);

         textArea.setMaxWidth(Double.MAX_VALUE);
         textArea.setMaxHeight(Double.MAX_VALUE);
         GridPane.setVgrow(textArea, Priority.ALWAYS);
         GridPane.setHgrow(textArea, Priority.ALWAYS);

         GridPane expContent = new GridPane();
         expContent.setMaxWidth(Double.MAX_VALUE);
         expContent.add(label, 0, 0);
         expContent.add(textArea, 0, 1);
         // Set expandable Exception into the dialog pane.
         alert.getDialogPane().setExpandableContent(expContent);
      }

      alert.getDialogPane().getStylesheets().
              add(getClass().getResource("/LanguageApp/style/style.css").toExternalForm());
      alert.getDialogPane().getStyleClass().add("style");

      Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
      Image icon = new Image(getClass().getResourceAsStream("/LanguageApp/resources/images/languages_128.png"));
      stage.getIcons().add(icon);


      alert.showAndWait();
   }
//</editor-fold>

}
