package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.Message;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class LoginController implements Initializable
 {

//<editor-fold defaultstate="collapsed" desc="Field Class">

  @FXML public HBox loginViewHbox;


  // Reference to the main Stage from the main Scene
  private Stage mainStage;
  // Reference to the main Scene
  private MainScene mainScene;

  // pop-up messages
  Message message;

  // For the bounle of idioms
  ResourceBundle resources;

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Reference to MainScene">
  /**
   *
   * @param aThis
   */
  public void setMainScene(MainScene aThis) throws Exception
   {
    mainScene = aThis;
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Initialize">

  /**
   * When the method is initialize
   */
  @Override
  public void initialize(URL location, ResourceBundle resources)
   {
    try {
      // References to mainStage
      mainStage = MainScene.getMainStage();

      // Create the locale for the pop up messages
      /*/*HandleLocale01.handleLocale01();*/
      message = new Message(mainStage,resources);

    } catch (Exception e) {
      Message.showException(e);
    }
   }

//</editor-fold>  


 }
