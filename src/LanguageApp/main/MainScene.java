package LanguageApp.main;

import LanguageApp.controller.DataBaseController;
import LanguageApp.controller.FormController;
import LanguageApp.controller.LoginController;
import LanguageApp.controller.MainController;
import LanguageApp.controller.PrincipalController;
import LanguageApp.controller.RegistrationController;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
         loginView.getChildren().remove(formView);
         loginView.getChildren().remove(registrationView);
         handleAntiguoUsuario();
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
         e.printStackTrace();
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

//<editor-fold defaultstate="collapsed" desc="Handles">

   /**
    * @param usuarioString
    * @param passwordString
    * @param user
    * @return
    */
   public boolean handlelogin (String usuarioString, String passwordString, boolean activoBoolean)
   {
      // in SQLinte doesn't exit boolean
      int activoInteger = (activoBoolean) ? 1 : 0;

      Connection conn = null;
      PreparedStatement pstmt = null;
      Statement stmt = null;
      int usuario_id = 0;

      // Preparing statement
      String sql = "SELECT usuario_id FROM usuarios WHERE nombre = ? and password = ?";
      try {
         // Try connection
         conn = dataBaseController.connect();
         conn.setAutoCommit(false);

         // preparing statement
         pstmt = conn.prepareStatement(sql);

         // set the value
         pstmt.setString(1, usuarioString);
         pstmt.setString(2, passwordString);

         //
         ResultSet rs = pstmt.executeQuery();

         int count = 0;
         while (rs.next()) {
            count++;
            usuario_id = rs.getInt("usuario_id");
         }

         if (count > 0) {

            // put the user activo or not
            if (activoBoolean) {
               try {

                  sql = "UPDATE usuarios SET activo = 0;";
                  stmt = conn.createStatement();
                  stmt.executeUpdate(sql);
                  conn.commit();
                  stmt.close();

                  // set the value
                  sql = "UPDATE usuarios SET activo = 1 " +
                          "WHERE usuario_id = ?";
                  pstmt = conn.prepareStatement(sql);
                  pstmt.setInt(1, usuario_id);
                  pstmt.executeUpdate();
                  conn.commit();

               } catch (SQLException e) {
                  message(Alert.AlertType.ERROR, "Error message", e.getMessage(),
                          "MainScene / handleLogin() - activoBoolean", e);
               }
            }
            pstmt.close();
            conn.close();
            // if exits the user and password return true
            return true;
         }
      } catch (SQLException e) {
         message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainScene / handleLogin()", e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainScene / handleLogin()", e);
         }
      }
      return false;
   }

   /**
    * @param usuarioString
    * @param passwordString
    * @param preguntaString
    * @param respuestaString
    * @param user
    * @return
    */
   public boolean handleRegistro (String usuarioString, String passwordString, 
           String preguntaString, String respuestaString)
   {

      Connection conn = null;
      PreparedStatement pstmt = null;

      // Preparing statement
      String sql = "SELECT usuario_id FROM usuarios WHERE nombre = ? and password = ?";
      try {
         // Try connection
         conn = dataBaseController.connect();
         conn.setAutoCommit(false);

         // preparing statement
         pstmt = conn.prepareStatement(sql);

         // set the value
         pstmt.setString(1, usuarioString);
         pstmt.setString(2, passwordString);

         //
         ResultSet rs = pstmt.executeQuery();

         int count = 0;
         while (rs.next()) {
            count++;           
         }

         if (count > 0) {
            pstmt.close();
            conn.close();
            // if exits the user and password return true
            return true;
         }

         sql = "INSERT INTO  usuarios (nombre, password, activo, pregunta, respuesta) " +
                 "VALUES (?,?,0,?,?)";
         pstmt = conn.prepareStatement(sql);
         // set the value
         pstmt.setString(1, usuarioString);
         pstmt.setString(2, passwordString);
         pstmt.setString(3, preguntaString);
         pstmt.setString(4, respuestaString);
         pstmt.executeUpdate();
         conn.commit();
         pstmt.close();

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainScene / handleRegistro()", e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception e) {
            message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "DataBaseController / handleRegistro()", e);
         }
      }
      return false;
   }

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

//</editor-fold>

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
      alert.getDialogPane().setMinWidth(600);
      alert.getDialogPane().setMinHeight(480);
      alert.getDialogPane().setPrefWidth(600);
      alert.getDialogPane().setPrefHeight(480);
      alert.setResizable(true);
      alert.setHeaderText(about);
      alert.getDialogPane().setContentText(contextText);

      if (ex != null) {
         // Create expandable Exception.
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         ex.printStackTrace(pw);
         String exceptionText = sw.toString();

         Label label = new Label("The exception stacktrace was:");

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
