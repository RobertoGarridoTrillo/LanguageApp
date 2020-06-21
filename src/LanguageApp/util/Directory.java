package LanguageApp.util;

import LanguageApp.controller.MainController;
import LanguageApp.model.Initial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import javafx.scene.control.Alert;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class Directory {

  private String path;
  private String lastDirectory;
  private String lastFile;
  private Initial initial;

  String se="/";
  String initialFile;

  /**
   * constructor
   */
  public Directory () {


    // The path is an absolute path (retarive to the initial instalation)    
    path = System.getProperty("user.dir");
    path = path.replace("\\", "/");

    // The last directory is an absolute rute    
    lastDirectory = path + se + "media"+se;
    //lastDirectory = path;


    // The initial file is an absolute rute + the file "LanguageApp.jaso"
    // initialFile = path + se + "LanguageApp.json";
    initialFile = "LanguageApp.json";
    

    // new MainController().message(Alert.AlertType.INFORMATION, "message", "so " + so, "separador " + se, null);

    initial = new Initial();
  }


  /**
   * This is called by the constructor and
   *
   * @return true if the init file exits
   */
  public String init () {
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
      new MainController().message(Alert.AlertType.ERROR, "Error message",
              e.getMessage(), initial.getLastDirectory() + initial.getLastFile() + "\nDirectory.java / checkIni()", e);
    }
    return null;
  }


  /**
   * Check if exits the configuration file
   *
   * @param path The path (method setDirectory) to the file
   * @return boolean true if it exits
   */
  private boolean checkIni () {
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
      new MainController().message(Alert.AlertType.ERROR, "Error message", e.getMessage(), " " + "\nDirectory.java / checkIni()", e);
    }

    return false;
  }


  /**
   * Read the initial file
   */
  private void readIni () {
    Gson gson = new Gson();

    try {
      Reader reader = new FileReader(initialFile);

      // Convert JSON File to Java Object
      initial = gson.fromJson(reader, Initial.class);

      reader.close();

    } catch (Exception e) {
      new MainController().message(Alert.AlertType.ERROR, "Error message",
              e.getMessage(), "Directory.java / readIni()", e);
    }
  }


  /**
   * Write the initial file
   */
  private void createIni () {
    try {
      // pretty print           
      Gson gson = new GsonBuilder().setPrettyPrinting().create();

      // creating initial objet
      initial.setDirectory(path);
      initial.setLastDirectory(lastDirectory);
      initial.setLastFile(initialFile);
      // Java objects to String
      String json = gson.toJson(initial);
      // Java objects to File
      FileWriter writer = new FileWriter(initialFile);

      gson.toJson(initial, writer);
      writer.close();

    } catch (Exception e) {
      new MainController().message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "\nDirectory.java / createIni()", e);
    }
  }


  /**
   * Return the actual Directory
   *
   * @return the actual getDirectory
   */
  public String getDirectory () {
    return initial.getDirectory();
  }


  /**
   * Return the actual Last Directory
   *
   * @return the actual Last Directory
   */
  public String getLastDirectory () {
    return initial.getLastDirectory();
  }


  /**
   * Set the actual directory
   *
   * @param lastDirectory String The actual directory
   */
  public void setLastDirectory (String lastDirectory) {
    this.lastDirectory = lastDirectory;
    initial.setLastDirectory(lastDirectory);
    update();
  }

  /**
   *  Return  the actual Last File
   *
   * @return the actual Last File
   *
   */
  public String getLastFile () {
    return initial.getLastFile();
  }

  /**
   * Set the last open file
   *
   * @param lastFile String the last open file
   */
  public void setLastFile (String lastFile) {
    this.lastFile = lastFile;
    initial.setLastFile(lastFile);
    update();
  }


  /**
   * setting the last directory
   *
   */
  public void update () {

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
      new MainController().message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "\nDirectory.java / updateIni()", e);
    }
  }


}
