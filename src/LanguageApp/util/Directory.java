package LanguageApp.util;

import LanguageApp.model.Initial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
public class Directory {

   private String path;
   private String lastDirectory;
   private String lastFile;
   private Initial initial;

   String se = "/";
   String initialFile;

   /**
    * constructor
    */
   public Directory ()
   {

      // The path is an absolute path (retarive to the initial instalation)    
      path = System.getProperty("user.dir");
      path = path.replace("\\", "/");

      // The last directory is an absolute rute    
      lastDirectory = path + se + "media" + se;
      initialFile = "LanguageApp.json";


      // message(Alert.AlertType.INFORMATION, "message", "so " + so, "separador " + se, null);
      initial = new Initial();
   }


   /**
    * This is called by the constructor and Check if ixits a init file and a database
    *
    * @return true if the init file exits
    */
   public String init ()
   {
      try {
         // reading the init
         if (checkIni()) {
            readIni();
            return initial.getLastDirectory() + se + initial.getLastFile();

         } else {
            createIni();
            return null;
         }
      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", initial.getLastDirectory() + initial.getLastFile() + "\nDirectory.java / checkIni()", e.toString(), e);
      }
      return null;
   }


   /**
    * Check if exits the configuration file
    *
    * @param path The path (method setDirectory) to the file
    * @return boolean true if it exits
    */
   private boolean checkIni ()
   {
      // Check if exits the init file (TRUE EXIST) Create or not a new file
      try {

         File file = new File(initialFile);

         if (file.createNewFile()) {
            return false; // no existe, se crea
         } else {
            return true; // si existe no se crea
         }
         //return !file.createNewFile(); // Creating initial file

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "Directory.java / checkIni()", e.toString(), e);
      }

      return false;
   }


   /**
    * Read the initial file
    */
   public void readIni ()
   {
      Gson gson = new Gson();

      try {
         Reader reader = new FileReader(initialFile);

         // Convert JSON File to Java Object
         initial = gson.fromJson(reader, Initial.class);

         reader.close();

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "Directory.java / readIni()", e.toString(), e);
      }
   }


   /**
    * Write the initial file
    */
   public void createIni ()
   {
      try {
         // pretty print           
         Gson gson = new GsonBuilder().setPrettyPrinting().create();

         // creating initial objet
         initial.setPath(path);
         initial.setLastDirectory(lastDirectory);
         initial.setLastFile(initialFile);
         // Java objects to String
         String json = gson.toJson(initial);
         // Java objects to File
         FileWriter writer = new FileWriter(initialFile);

         gson.toJson(initial, writer);
         writer.close();

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "\nDirectory.java / createIni()", e.toString(), e);
      }
   }


   /**
    * Return the actual Directory
    *
    * @return the actual getDirectory
    */
   public String getDirectory ()
   {
      return initial.getPath();
   }


   /**
    * Return the actual Last Directory
    *
    * @return the actual Last Directory
    */
   public String getLastDirectory ()
   {
      return initial.getLastDirectory();
   }


   /**
    * Set the actual directory
    *
    * @param lastDirectory String The actual directory
    */
   public void setLastDirectory (String lastDirectory)
   {
      this.lastDirectory = lastDirectory;
      initial.setLastDirectory(lastDirectory);
      update();
   }

   /**
    * Return the actual Last File
    *
    * @return the actual Last File
    *
    */
   public String getLastFile ()
   {
      return initial.getLastFile();
   }

   /**
    * Set the last open file
    *
    * @param lastFile String the last open file
    */
   public void setLastFile (String lastFile)
   {
      this.lastFile = lastFile;
      initial.setLastFile(lastFile);
      update();
   }

   /**
    * setting the last directory
    *
    */
   public void update ()
   {

      // pretty print
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      try {

         // readIni to update 
         Reader reader = new FileReader(initialFile);

         // Convert JSON File to Java Object
         initial = gson.fromJson(reader, Initial.class);

         // update an object inital
         initial.setLastDirectory(path);
         initial.setLastDirectory(lastDirectory);
         initial.setLastFile(lastFile);

         // Java objects to String
         String json = gson.toJson(initial);

         reader.close();

         // Java objects to File
         FileWriter writer = new FileWriter(initialFile);

         gson.toJson(initial, writer);

         writer.close();

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "\nDirectory.java / updateIni()", e.toString(), e);
      }
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
