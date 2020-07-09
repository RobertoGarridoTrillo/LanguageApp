package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class MainController {

//<editor-fold defaultstate="collapsed" desc="fields class">

   @FXML private BorderPane MainViewBorderPane;
   @FXML private MenuItem openMenu;
   @FXML private MenuItem closeMenu;
   @FXML private MenuItem controlesMenu;
   @FXML private MenuItem aboutMenu;
   @FXML private MenuItem loginMenu;
   @FXML private MenuItem resultadosMenu;
   @FXML private MenuItem DashBoard;

   // Reference to the main Stage from the main Scene
   private Stage mainStage;
   // Reference to the main Scene
   private MainScene mainScene;

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

//<editor-fold defaultstate="collapsed" desc="Menu">

   /**
    * Open a SelectFile and seek a json to load the phrases (Part 1)
    */
   @FXML public void handleOpenMenu ()
   {
      mainScene.handleOpenMenu();
   }


   /**
    * When click on the close menu
    */
   @FXML private void handleCloseMenu ()
   {
      mainScene.handleCloseMenu();
   }

   /**
    * When click on the close menu
    */
   @FXML private void handleControlesMenu ()
   {
      mainScene.handleControlesMenu();
   }

   /**
    * handle of the About menu"
    */
   @FXML private void handleAboutMenu ()
   {
      mainScene.handleAboutMenu();
   }

   /**
    * handle of the login menu
    */
   @FXML private void handleLoginMenu ()
   {
      mainScene.handleLoginMenu();
   }

   /**
    * handle of the Resultados menu
    */
   @FXML private void handleResultadosMenu ()
   {
      mainScene.handleResultadosMenu();
   }
      
   /**
    * handle of the Dashboard menu
    */
   @FXML private void handleDashBoardMenu ()
   {
      mainScene.handleDashBoardMenu();
   }
}
