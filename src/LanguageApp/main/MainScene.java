package LanguageApp.main;

import LanguageApp.controller.DataBaseController;
import LanguageApp.controller.FormController;
import LanguageApp.controller.LoginController;
import LanguageApp.controller.MainController;
import LanguageApp.controller.PrincipalController;
import LanguageApp.controller.RegistrationController;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class MainScene extends Application {

//<editor-fold defaultstate="collapsed" desc="fileds class">
   private static Stage mainStage;
   public Scene mainScene;
   private MainController mainController;
   private PrincipalController principalController;
   private LoginController loginController;
   private FormController formController;
   private RegistrationController registrationController;
   private DataBaseController dataBaseController;
   private BorderPane mainView;
   private AnchorPane principalView;
   private HBox loginView;
   private AnchorPane formView;
   
   private AnchorPane registrationView;
   private AnchorPane dataBaseView;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="init">
   /**
    * load the initial configuration before all the other things loads
    *
    * @throws Exception FilerException - if the same pathname has already been opened for writing, if the source module
    * cannot be determined, or if the target module is not writable, or if an explicit target module is specified and
    * the location does not support it. IOException - if the file cannot be opened IllegalArgumentException - for an
    * unsupported location IllegalArgumentException - if moduleAndPkg is ill-formed IllegalArgumentException - if
    * relativeName is not relative
    */
   @Override
   public void init () throws Exception
   {
      super.init();
      Font.loadFont(MainScene.class.getResource(
              "/LanguageApp/resources/fonts/FiraCode_3/FiraCode-Regular.ttf").
              toExternalForm(), 10);
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="start">
   /**
    * It setting and showing the stage.
    *
    * @param mainStage The inicial Stage
    *
    */
   @Override
   public void start (Stage mainStage)
   {

      this.mainStage = mainStage;
      // Set the Title to the Stage
      this.mainStage.setTitle("LanguagesApp");
      // Set the application icon.

      this.mainStage.getIcons().add(new Image(getClass()
              .getResourceAsStream("/LanguageApp/resources/images/languages_128.png")));

      // 
      mainView();
      formView();
      loginView();
      registrationView();
      dataBaseView();
      principalView();

      this.mainStage.setResizable(false);
      this.mainStage.setWidth(1215);
      this.mainStage.show();
   }
//</editor-fold>   

//<editor-fold defaultstate="collapsed" desc="mainView">

   /**
    * It managed the load of the main fxml into the border panel that I use it like root.
    *
    * i can also create a "link" to the controller, in case I need to send data.
    */
   private void mainView ()
   {
      try {
         // Create the FXMLLoader
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainScene.class
                 .getResource("/LanguageApp/view/MainView.fxml"));
         mainView = (BorderPane) loader.load();

         // Set the Scene to the Stage
         mainScene = new Scene(mainView);
         mainStage.setScene(mainScene);

         // Give the mainController access to the main app (It´s like a instance)
         mainController = loader.getController();
         mainController.setMainScene(this);

         // Adding dark style
         JMetro jMetro = new JMetro(Style.DARK);
         jMetro.setScene(mainScene);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="pricipalView">
   private void principalView ()
   {

      try {
         // Create the FXMLLoader
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainScene.class
                 .getResource("/LanguageApp/view/PrincipalView.fxml"));
         principalView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         principalController = loader.getController();
         principalController.setMainScene(this);

         // Adding dark style
         //JMetro jMetro = new JMetro(Style.DARK);
         //jMetro.setScene(mainScene);         

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//</editor-fold>
   
//<editor-fold defaultstate="collapsed" desc="LoginView">
   private void loginView ()
   {

      try {
         // Create the FXMLLoader
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainScene.class
                 .getResource("/LanguageApp/view/LoginView.fxml"));
         loginView = (HBox) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         loginController = loader.getController();
         loginController.setMainScene(this);
         loginController.loginViewAnchorPane.getChildren().add(formView);

         // Create the Scene and put it in the center or the borderLayout
         mainView.setCenter(loginView);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="FormView">

   /**
    *
    */
   private void formView ()
   {

      try {
         // Create the FXMLLoader
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainScene.class
                 .getResource("/LanguageApp/view/FormView.fxml"));
         formView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         formController = loader.getController();
         formController.setMainScene(this);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="RegisterView">
   private void registrationView ()
   {

      try {
         // Create the FXMLLoader
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainScene.class
                 .getResource("/LanguageApp/view/RegistrationView.fxml"));

         registrationView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         registrationController = loader.getController();
         registrationController.setMainScene(this);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="DataBaseView">
   private void dataBaseView ()
   {

      try {
         // Create the FXMLLoader
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainScene.class
                 .getResource("/LanguageApp/view/DataBaseView.fxml"));
         dataBaseView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         dataBaseController = loader.getController();
         dataBaseController.setMainScene(this);

         // Adding dark style
         //JMetro jMetro = new JMetro(Style.LIGHT);
         //jMetro.setScene(mainScene);         

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Menu Buttons">

   /**
    * Returns the main stage, if you need it, for example to use it with the filechooser in the mainController class
    *
    * @return An object of type Stage
    */
   public static Stage getMainStage ()
   {
      return mainStage;
   }

   /**
    * public static void main
    *
    * @param args
    */
   public static void main (String[] args)
   {
      launch(args);

   }

   /**
    * it's called by mainController when i click in the open menu
    */
   public void handleOpenMenu ()
   {
      principalController.handleOpenMenu();
   }

   /**
    * it's called by mainController when i click in the close menu
    */
   public void handleCloseMenu ()
   {
      principalController.handleCloseMenu();
   }

   /**
    * it's called by mainController when i click in the Controles menu
    */
   public void handleControlesMenu ()
   {
      principalController.handleControlesMenu();
   }

   /**
    * it's called by mainController when i click in the About menu
    */
   public void handleAboutMenu ()
   {
      principalController.handleAboutMenu();
   }

   /**
    * it's called by mainController when i click in the Login menu
    */
   public void handleLoginMenu ()
   {

      try {
         // Create the Scene and put it in the center or the borderLayout
         mainView.getChildren().remove(principalView);
         mainView.setCenter(loginView);

         // return to the originall size
         principalController.setChangedSizeLogin();

         String result = principalController.getMediaStatus();
         if (result.equals("playing")) {
            principalController.handlePlayButton();
         } else if (result.equals("playingOriginal")) {
            principalController.handlePlayButtonItemOriginal();
         }

      } catch (Exception e) {
      }
   }

   /**
    * it's called by mainController when i click in the Resultados menu
    */
   public void handleDatabaseMenu ()
   {
      try {
         // Create the Scene and put it in the center or the borderLayout
         mainView.getChildren().remove(principalView);
         mainView.setCenter(dataBaseView);

         String result = principalController.getMediaStatus();
         if (result.equals("playing")) {
            principalController.handlePlayButton();
         } else if (result.equals("playingOriginal")) {
            principalController.handlePlayButtonItemOriginal();
         }
      } catch (Exception e) {
      }
   }

   /**
    * it's called by mainController when i click in the Dashboard menu
    */
   public void handleDashBoardMenu ()
   {
      mainView.getChildren().remove(loginView);
      mainView.setCenter(principalView);

      // return to the originall size
      principalController.setChangedSizeDashboard();


      String result = principalController.getMediaStatus();
      if (result.equals("pause")) {
         principalController.handlePlayButton();
      } else if (result.equals("originalPause")) {
         principalController.handlePlayButtonItemOriginal();
      }
   }
//</editor-fold>

   /**
    * Change the scene from form to register
    *
    * @param n
    */
   public void handleNuevoUsuario ()
   {
      // Create the Scene Registration and put it in login 
      loginView.getChildren().remove(formView);
      loginView.getChildren().add(registrationView);
   }
   
   /**
    * Change the scene from form to register
    *
    * @param n
    */
   public void handleAntiguoUsuario ()
   {
      // Create the Scene Login and put it in login 
      loginView.getChildren().remove(registrationView);
      loginView.getChildren().add(formView);
   }

   /**
    * Change the scene from form to register
    *
    * @param n
    */
   public void handelLogin ()
   {
      
   }
   
   /**
    * 
    */
   public void handleRegistro ()
   {
   }
   

}
