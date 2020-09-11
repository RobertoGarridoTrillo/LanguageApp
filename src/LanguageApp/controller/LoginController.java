package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.Message;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class LoginController {

//<editor-fold defaultstate="collapsed" desc="Field Class">

   @FXML public HBox loginViewAnchorPane;


   // Reference to the main Stage from the main Scene
   private Stage mainStage;
   // Reference to the main Scene
   private MainScene mainScene;
   
   // pop-up messages
   Message message;   
   
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Reference to MainScene">
   /**
    *
    * @param aThis
    */
   public void setMainScene (MainScene aThis)
   {
      mainScene = aThis;
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Initialize">
   /**
    * When the method is initialize
    */
   public void initialize ()
   {
      // References to mainStage
      mainStage = MainScene.getMainStage();
   }


//</editor-fold>  
   
}
