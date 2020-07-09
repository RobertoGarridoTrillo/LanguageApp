package LanguageApp.util;

//<editor-fold defaultstate="collapsed" desc="import">
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class CompareFile extends Application {

//<editor-fold defaultstate="collapsed" desc="fields class">
   File firstFile, secondFile, thirdFile;
   String firstPath = "", secondPath = "", thirdPath = "",
           firstName = "", secondName = "", thirdName = "";
   String[] firstWords, secondWords, thirdWords;
   List<String> as;

   String thisLine;
   Stage stage;

   Button firstButton, secondButton, thirdButton, fourthButton;
   Label firstLabel, secondLabel, thirdLabel, fourthLabel;

   StringBuilder sb = new StringBuilder();
//</editor-fold>

   @Override
   public void start (Stage stage)
   {
      this.stage = stage;


      // Create the root
      VBox root = new VBox();

      // this is the file.txt that it´s created when I execute the film for the first time
      HBox hBox1 = new HBox();
      firstButton = new Button("First File");
      firstLabel = new Label("The file.txt that it´s created when I execute the film for the first time");
      hBox1.setStyle("-fx-background-color:POWDERBLUE");
      root.setMargin(hBox1, new Insets(10, 10, 5, 5));
      hBox1.setMargin(firstLabel, new Insets(5, 0, 0, 5));
      hBox1.getChildren().setAll(firstButton, firstLabel);

      // Now I compare this file with the "Great" dictionary and get the new words.
      HBox hBox2 = new HBox();
      secondButton = new Button("Compare with dictionary");
      secondLabel = new Label("The \"Great\" dictionary");
      hBox2.setStyle("-fx-background-color:POWDERBLUE");
      root.setMargin(hBox2, new Insets(5, 10, 5, 5));
      hBox2.setMargin(secondLabel, new Insets(5, 0, 0, 5));
      hBox2.getChildren().setAll(secondButton, secondLabel);

      // If I saw that all it allright, update the great dictionary, adding the new words.
      HBox hBox3 = new HBox();
      thirdButton = new Button("Update \"Great\" dictionary");
      thirdLabel = new Label("Update the great dictionary");
      hBox3.setStyle("-fx-background-color:POWDERBLUE");
      root.setMargin(hBox3, new Insets(5, 10, 5, 5));
      hBox3.setMargin(thirdLabel, new Insets(5, 0, 0, 5));
      hBox3.getChildren().setAll(thirdButton, thirdLabel);

      // Get the files.mp3 of the big folder "dictionary" and paste them in the carpet dictionay of the media
      HBox hBox4 = new HBox();
      fourthButton = new Button("Update media/\"First_file\"dictionary/*.mp3");
      fourthLabel = new Label("Get the files.mp3 of the big folder \"dictionary\"");
      hBox4.setStyle("-fx-background-color:POWDERBLUE");
      root.setMargin(hBox4, new Insets(5, 10, 5, 5));
      hBox4.setMargin(fourthLabel, new Insets(5, 0, 0, 5));
      hBox4.getChildren().setAll(fourthButton, fourthLabel);

      // Setting the events in the buttons
      firstButton.setOnMouseClicked(new FirstButton());
      secondButton.setOnMouseClicked(new SecondButton());
      thirdButton.setOnMouseClicked(new ThirdButton());
      fourthButton.setOnMouseClicked(new FourthButton());

      root.setMinSize(350, 250);
      root.getChildren().addAll(hBox1, hBox2, hBox3, hBox4);

      // Create the scene
      Scene scene = new Scene(root);

      // Create the stage
      stage.setMinHeight(300);
      stage.setMinWidth(600);
      stage.setTitle("CompareFile");
      stage.setScene(scene);
      stage.show();
   }


   /**
    * Returns a selectrd file
    *
    * @param stage
    *
    * @return the selected file
    */
   public File filechooser (Stage stage)
   {

      File file = null;
      try {
         FileChooser fc = new FileChooser();
         // Set extension filter
         FileChooser.ExtensionFilter filterTXT =
                 new FileChooser.ExtensionFilter(
                         "Text files (*.txt)", "*.txt");
         FileChooser.ExtensionFilter filterMP3 =
                 new FileChooser.ExtensionFilter(
                         "Music files (*.mp3)", "*.mp3");
         FileChooser.ExtensionFilter filterMP4 =
                 new FileChooser.ExtensionFilter(
                         "Video files (*.mp4, *.flv)", "*.mp4", "*.flv");
         FileChooser.ExtensionFilter filterAll =
                 new FileChooser.ExtensionFilter(
                         "All files (*.*)", "*.*");

         fc.getExtensionFilters().addAll(filterTXT, filterMP3, filterMP4, filterAll);

         /* fc.getExtensionFilters().addAll(
                    new ExtensionFilter("Music files (*.mp3)", "*.mp3"),
                    new ExtensionFilter("All Video files (*.mp4, *.flv)",
                             "*.mp4", "*.flv"),
                    new ExtensionFilter("All files", "*")); */
         file = fc.showOpenDialog(stage);

      } catch (Exception ex) {
         ex.printStackTrace();
      }
      return file;
   }

   private class FirstButton implements EventHandler<MouseEvent> {

      @Override
      public void handle (MouseEvent t)
      {
         try {
            // Select the first file
            firstFile = filechooser(stage);
            // Read the path
            firstPath = firstFile.getAbsolutePath().toString();
            firstPath = firstPath.replace("\\", "/");
            int lastBar = firstPath.lastIndexOf("/");
            // Read the file name
            firstName = firstPath.substring(lastBar + 1, firstPath.length());
            // Put the file name in the label
            firstLabel.setText(firstName);

            // Open a bufferReader to the file
            BufferedReader br = new BufferedReader(new FileReader(firstPath));
            // Read the file
            firstWords = countWords(br);


         } catch (Exception e) {
            e.printStackTrace();
         }
      }

   }

   private class SecondButton implements EventHandler<MouseEvent> {

      @Override
      public void handle (MouseEvent t)
      {
         // Select the  file
         secondFile = filechooser(stage);
         // Read the path
         secondPath = secondFile.getAbsolutePath();
         secondPath = secondPath.replace("\\", "/");
         int lastBar = secondPath.lastIndexOf("/");
         // Read the file name
         secondName = secondPath.substring(lastBar + 1, secondPath.length());
         // Put the file name in the label
         secondLabel.setText(secondName);

         // open a bufferedReader to the dictionay
         try {
            // open the dictionary
            BufferedReader br = readDictionary(secondFile);
            // Read the dictinoary
            secondWords = countWords(br);
            // Compare the dictionary with the file
            compareDictionary();

         } catch (Exception e) {
            message(Alert.AlertType.ERROR, "Error", "SecondButton", e.getMessage());
            e.printStackTrace();
         }

      }
   }

   private class ThirdButton implements EventHandler<MouseEvent> {

      @Override
      public void handle (MouseEvent t)
      {

         if (secondWords == null) {
            return;
         }

         thirdFile = filechooser(stage);
         // Read the path
         thirdPath = thirdFile.getAbsolutePath().toString();
         thirdPath = thirdPath.replace("\\", "/");
         int lastBar = thirdPath.lastIndexOf("/");
         // Read the file name
         thirdName = thirdPath.substring(lastBar + 1, thirdPath.length());
         // Put the file name in the label
         thirdLabel.setText(thirdName);

         // Join the dictionary and the new words
         for (int i = 0; i < secondWords.length; i++) {
            as.add(secondWords[i]);
         }
         // Sorting the Arraylist
         Collections.sort(as);
         try {
            //
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(thirdFile));

            for (int i = 0; i < as.size(); i++) {
               bw.write(as.get(i) + "\n");
               bw.flush();
            }
            bw.close();
         } catch (IOException ex) {
            ex.printStackTrace();
         }


      }
   }

   private class FourthButton implements EventHandler<MouseEvent> {

      @Override
      public void handle (MouseEvent t)
      {

         if (secondWords == null || firstWords == null) {
            return;
         }

         // Get the path to the file.txt with the words of the media
         int lastBar = firstPath.lastIndexOf("/");
         // Read the path without the file name
         String pathCopiedFile = firstPath.substring(0, lastBar);

         // Get the path to the dictionary.txt with all the words
         lastBar = secondPath.lastIndexOf("/");
         // Read the path without the file name
         String pathOriginalfile = secondPath.substring(0, lastBar);

         // if the directory dictionary doesn't exit then it's create
         File file = new File(pathCopiedFile + "/dictionary");
         file.mkdir();

         InputStream sourceStream = null;
         OutputStream targetStream = null;
         File sourceFile = null;
         File targetFile = null;
         long start = System.nanoTime();
         String error = "";
         try {
            for (String  firstWord: firstWords) {
               error = firstWord;
               System.out.println(firstWord);
               
               sourceFile = new File(pathOriginalfile + "/" + firstWord + ".mp3");
               targetFile = new File(pathCopiedFile + "/dictionary/" + firstWord + ".mp3");

               sourceStream = new FileInputStream(sourceFile);
               targetStream = new FileOutputStream(targetFile);
               
               byte[] buffer = new byte[1024];
               int length;
               while ((length = sourceStream.read(buffer)) > 0) {
                  targetStream.write(buffer, 0, length);
               }
            }
         } catch (Exception e) {
            message(Alert.AlertType.ERROR, "Error", "FourthButton \nFalta el archivo " + error + ".mp3", e.getMessage());
            e.printStackTrace();
         } finally {
            try {
               sourceStream.close();
               targetStream.close();
            } catch (IOException ex) {
               ex.printStackTrace();
            }
         }
         fourthLabel.setText("Time taken " + (System.nanoTime() - start));
      }
   }


   /**
    * Read the content of the dictionary
    *
    * @return @throws IOException
    */
   private BufferedReader readDictionary (File file) throws IOException
   {
      BufferedReader br = new BufferedReader(
              new FileReader(file));
      // I need the first file open
      if (firstPath.equals("")) {
         message(Alert.AlertType.ERROR, "Error message",
                 "Selecciona primer archivo", "Error en ThirdButton()");
      }
      return br;
   }

   /**
    * Compare the dictionary with the file. Create a new file with the new words
    */
   private void compareDictionary ()
   {

      // fields
      as = new ArrayList<String>();
      int j;
      boolean b = false;
      int lengthFirstWords = firstWords.length;
      int lengthSecondWords = secondWords.length;


      try {
         // Doing two loops witd Strings[] and compare one with the other
         for (int i = 0; i < lengthFirstWords; i++) {

            b = false;

            for (j = 0; j < lengthSecondWords; j++) {

               if (firstWords[i].equals(secondWords[j])) {
                  b = true;
                  break;
               }
            }

            j--;
            if (!b && i < lengthFirstWords) {
               as.add(firstWords[i]);
            }
         }


         // Cast to String[] / pass data from ArraList to String[]
         thirdWords = new String[as.size()];
         thirdWords = as.toArray(thirdWords);

         // Print in the screen the numbers of new labels
         secondLabel.setText("New words: " + thirdWords.length);

         // if there are some new words save a new file with the new words
         BufferedWriter bw = new BufferedWriter(
                 new FileWriter(firstFile.getParent() + "/newWords.txt"));
         for (String thirdWord : thirdWords) {
            bw.write(thirdWord + "\n");
            bw.flush();
         }
         bw.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * Read the words of a file
    */
   private String[] countWords (BufferedReader br)
   {

      String[] words = null;
      String newLine = String.valueOf(Character.toChars(10));

      try {

         // Extract the words in a StringBuilder
         while ((thisLine = br.readLine()) != null) {
            sb.append(thisLine).append("\n");
         }

         // Count the number of words
         int cont = 0;
         // El método codePointAt() devuelve un entero no negativo 
         // que equivale al valor Unicode code point del carácter
         for (int i = 0; i < sb.length(); i++) {
            if (sb.codePointAt(i) == 10) {
               cont++;
            }
         }

         // setting the Array of strings according to the new line
         words = new String[cont];

         int i = 0;
         // Separate the words 
         while (sb.length() > 0) {
            // Looking for the new line character
            int newLineIndex = sb.indexOf(newLine);
            // Extracting the word
            words[i] = sb.substring(0, newLineIndex);

            // Deleting the word and starting againg
            sb = sb.delete(0, newLineIndex + 1);
            i++;
         }
      } catch (IOException e) {
      }
      return words;
   }


   /**
    * show a standard emergent message
    *
    * @param alertType alertType.CONFIRMATION, ERROR, INFORMATION, NONE, WARNING
    * @param title The title of the windows
    * @param about The them to expose
    * @param contextText The showed text
    */
   private void message (Alert.AlertType alertType, String title,
           String about, String contextText)
   {

      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      alert.setHeaderText(about);
      alert.setContentText(contextText);

      alert.showAndWait();
   }

   /**
    *
    * @param args
    */
   public static void main (String[] args)
   {

      Application.launch(args);

   }

}
