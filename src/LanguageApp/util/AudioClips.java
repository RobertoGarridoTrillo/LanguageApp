package LanguageApp.util;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class AudioClips {

   // HashMap with the name and the Audiclip
   private Map<String, AudioClip> audioClips;
   // An unique audioclip
   private AudioClip ac;
   private Set<String> wordSet;
   private String path;

   /**
    * Setting the audioclips
    *
    * @param wordSet a Set of String, result of creating a list of words of the media
    * @param lastdirectory
    * @param lastFile
    * @return A map with the name and the Audioclip
    */
   public Map<String, AudioClip> setAudioClip (Set<String> wordSet, String lastdirectory, Slider rateSlider, Slider balanceSlider, Slider volumeSlider)
   {
      // instance of the Map
      audioClips = new HashMap<>();
      //this.wordSet = wordSet;
      String audioError = null;

      String se = System.getProperty("file.separator");

      try {
         for (String ws : wordSet) {

            // This is to fix the forbbiden name con. in windows
            if (ws.equals("con")) {
               ws = "connn";
            }
            audioError = ws; // if this audid doesn´t exist I show it in an message.

            String s = lastdirectory + se + ws + ".mp3";

            URI resource = new File(s).toURI();

            ac = new AudioClip(resource.toString());

            audioClips.put(ws, ac);

            audioClips.get(ws).rateProperty().bind(rateSlider.valueProperty());
            audioClips.get(ws).balanceProperty().bind(balanceSlider.valueProperty());
            audioClips.get(ws).volumeProperty().bind(volumeSlider.valueProperty());
         }
      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "Falta el audio: \"" + audioError + "\"", "AudioClips.java / setAudioClip()", e);
      }
      return audioClips;
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
