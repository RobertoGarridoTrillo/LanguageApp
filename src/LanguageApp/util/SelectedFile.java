package LanguageApp.util;

import LanguageApp.controller.MainController;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class SelectedFile {

  /**
   * Show a filechooser
   *
   * @param mainStage A reference of Main Stage like context, (Inverse control)
   * @param lastDirectory The las directory (Directory.getLstDirectory)
   * @return the file selected
   */
  public File getSelectedFile (Stage mainStage, String lastDirectory) {

    FileChooser fch = new FileChooser();
    File file = null;

    try {

      // Set extension filter
      FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSon files (*.json)", "*.json");

      fch.getExtensionFilters().add(extFilter);
      
      // Adding title
      fch.setTitle("Open Resource File"); 

      // Choosing the inicial directory
      File fl = new File(lastDirectory);

      if (!fl.exists()) {       fl.mkdirs();     }
      
      fch.setInitialDirectory(fl);

      // Opening the FileChooser in the mainStage
      file = fch.showOpenDialog(mainStage);

    } catch (Exception e) {
      message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "\nSelectedFile.java / Filechooser()", e);
    }
    return file;
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
      alert.getDialogPane().setMinWidth(600);
      alert.getDialogPane().setMinHeight(480);
      alert.getDialogPane().setPrefWidth(600);
      alert.getDialogPane().setPrefHeight(480);
      alert.setResizable(true);
      alert.setHeaderText(about);
      alert.getDialogPane().setContentText(contextText);

      if (ex != null) {
         // Create expandable Exception.
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         ex.printStackTrace(pw);
         String exceptionText = sw.toString();

         Label label = new Label("The exception stacktrace was:");

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

      alert.getDialogPane().getStylesheets().add(getClass().getResource("/LanguageApp/style/style.css").toExternalForm());
      alert.getDialogPane().getStyleClass().add("style");

      Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
      Image icon = new Image(getClass().getResourceAsStream("/LanguageApp/resources/images/languages_128.png"));
      stage.getIcons().add(icon);


      alert.showAndWait();
   }
//</editor-fold>

}
