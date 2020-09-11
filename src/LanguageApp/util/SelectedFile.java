package LanguageApp.util;

import java.io.File;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class SelectedFile {
      
   // pop-up messages
   Message message = new Message();

   /**
    * Show a filechooser
    *
    * @param mainStage A reference of Main Stage like context, (Inverse control)
    * @param lastDirectory The las directory (Directory.getLstDirectory)
    * @return the file selected
    */
   public File getSelectedFile (Stage mainStage, String lastDirectory)
   {

      FileChooser fch = new FileChooser();
      File file = null;

      try {

         // Set extension filter
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Mp4 files (*.mp4)", "*.mp4");

         fch.getExtensionFilters().add(extFilter);

         // Adding title
         fch.setTitle("Open Resource File");

         // Choosing the inicial directory
         File fl = new File(lastDirectory);

         if (!fl.exists()) {
            fl.mkdirs();
         }

         fch.setInitialDirectory(fl);

         // Opening the FileChooser in the mainStage
         file = fch.showOpenDialog(mainStage);

      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "\nSelectedFile.java / Filechooser()", e.toString(), e);
      }
      return file;
   }

}
