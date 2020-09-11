package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.Message;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
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

   @FXML private MenuBar menuBar;

   @FXML private Menu fileMenu;
   @FXML private Menu userMenu;
   @FXML private Menu goMenu;
   @FXML private Menu helpMenu;

   @FXML private MenuItem openMenu;
   @FXML private MenuItem closeMenu;
   @FXML private MenuItem exitMenu;

   @FXML private MenuItem loginMenu;
   @FXML private MenuItem unloginMenu;
   @FXML private MenuItem registroMenu;

   @FXML private MenuItem dashBoard;
   @FXML private MenuItem databaseMenu;

   @FXML private MenuItem controlesMenu;
   @FXML private MenuItem aboutMenu;

   // pop-up messages
   Message message;

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
      
      // Setting messages
      message = new Message();
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Menu">

   /**
    * Open a SelectFile and seek a json to load the phrases
    */
   @FXML private void handleOpenMenu ()
   {
      mainScene.buttonOpenMenu();
   }

   /**
    * When click on the close menu
    */
   @FXML private void handleCloseMenu ()
   {
      mainScene.buttonCloseMenu();
   }

   /**
    * When click on the close menu
    */
   @FXML private void handleExitMenu ()
   {
      mainScene.buttonExitMenu();
   }

   /**
    * handle of the login menu
    */
   @FXML private void handleLoginMenu ()
   {
      mainScene.buttonLoginMenu();
   }

   /**
    * handle of the Unlogin menu
    */
   @FXML private void handleUnloginMenu ()
   {
      mainScene.buttonUnloginMenu();
   }

   /**
    * handle of the login menu
    */
   @FXML private void handleNuevoUsuario ()
   {
      mainScene.buttorRegistro();
   }

   /**
    * When click on the close menu
    */
   @FXML private void handleControlesMenu ()
   {
      mainScene.buttonControlesMenu();
   }

   /**
    * handle of the About menu"
    */
   @FXML private void handleAboutMenu ()
   {
      mainScene.buttonAboutMenu();
   }

   /**
    * handle of the Resultados menu
    */
   @FXML private void handleDatabaseMenu ()
   {
      mainScene.buttonDatabaseMenu();
   }

   /**
    * handle of the Dashboard menu
    */
   @FXML private void handleDashBoardMenu ()
   {
      mainScene.buttonDashBoardMenu();
   }
}
