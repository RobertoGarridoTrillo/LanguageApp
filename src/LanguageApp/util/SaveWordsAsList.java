package LanguageApp.util;

import LanguageApp.model.Item;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class SaveWordsAsList {

   /**
    * Save in a File a list of words
    *
    * @param items An item of item objects
    * @param titleMP4 An string with the name of the file
    * @param lastDirectory the last open directory
    * @return
    */
   public Set<String> saveWordsAsList (Item[] items)
   {

      // Array with the words of one phrase
      String[] wordPhrase;
      // Set with all the words (no repeat words)
      Set<String> ws = new HashSet<>();
      // The "words set" above pased to String, to save in disk
      String wString = "";

      try {

         for (int i = 0; i < items.length - 1; i++) {

            String phrase = items[i].getText();

            wordPhrase = cleanWords(phrase);
            // Adding the words of this phrase to the List
            ws.addAll(Arrays.asList(wordPhrase));
         }

         // Sorting the Set (Set doesn´t sort, I put it in a list)
         List<String> wordList = new ArrayList<>(ws);
         Collections.sort(wordList);

         // Passing the List to String to save into a file
         for (Iterator<String> iterator = wordList.iterator();
                 iterator.hasNext();) {
            wString = wString.concat(iterator.next()).concat("\n");

         }
      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "SaveWordsAsList.java / handleSaveAsList()", e.toString(), e);
      }
      return ws;
   }


   public String[] cleanWords (String phrase)
   {

      String[] wordPhrase = null;
      Pattern pattern;
      Matcher matcher;
      String sub;
      String s;
      try {
         // I'm going to probe without the normalizer
         s = phrase;
         /*
            // Deleting accents
            s = Normalizer.normalize(phrase, Normalizer.Form.NFD);
            s = s.replaceAll("\\p{InCombiningDiacriticalMarks}+",
                    "");
            
            // Deleting the first blank space of the phrase
            pattern = Pattern.compile("[^a-zA-Z0-9]");
            for (int i = 0; i < s.length(); i++) {

                sub = s.substring(i, i + 1);
                matcher = pattern.matcher(sub);

                if (matcher.find()) {

                    s = s.substring(i + 1, s.length());
                    i = -1;
                } else {
                    break;
                }

            }

            // Deleting the last non normal character of th phrase
            pattern = Pattern.compile("[^a-zA-Z0-9]");
            for (int i = s.length(); i >= 0; i--) {

                sub = s.substring(s.length() - 1, s.length());
                matcher = pattern.matcher(sub);

                if (matcher.find()) {

                    s = s.substring(0, s.length() - 1);
                    i = s.length();

                } else {
                    break;
                }

            }
            
          */
         // To lowerCase
         phrase = s.toLowerCase();
         phrase = phrase.replace("  ", "");

         // Breaking down the phrase in words aput then in a Array
         wordPhrase = phrase.split(" ");
         // Deleting the first and last 'dot, comma, acent... of every
         // word of the phrase
         for (int k = 0; k < wordPhrase.length; k++) {
            //pattern = Pattern.compile("[^a-zA-Z0-9]");
            pattern = Pattern.compile("[ ºª\\\\!|\"@·#$~%€&¬/()=?¿¡^`+*ç\\[\\]\\{\\}_\\-\\.:\\,\\;'´<>]");

            for (int i = 0; i < 4; i++) {
               String first = wordPhrase[k].substring(0, 1);
               String last = wordPhrase[k].substring(wordPhrase[k].length() - 1);

               Matcher matcherFirst = pattern.matcher(first);
               Matcher matcherLast = pattern.matcher(last);
               if (matcherFirst.find()) {
                  wordPhrase[k] = wordPhrase[k].substring(1, wordPhrase[k].length());
               }
               if (matcherLast.find()) {
                  wordPhrase[k] = wordPhrase[k].substring(0, wordPhrase[k].length() - 1);
               }
            }
         }
      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "SaveWordsAsList.java / cleanWords()", e.toString(), e);
      }
      return wordPhrase;
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
