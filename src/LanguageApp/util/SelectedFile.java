package LanguageApp.util;

import LanguageApp.controller.MainController;
import java.io.File;
import javafx.scene.control.Alert;
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
      new MainController().message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "\nSelectedFile.java / Filechooser()", e);
    }
    return file;
  }


}
