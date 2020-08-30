package LanguageApp.main;

import LanguageApp.controller.DataBaseController;
import LanguageApp.controller.ForgetController;
import LanguageApp.controller.FormController;
import LanguageApp.controller.LoginController;
import LanguageApp.controller.MainController;
import LanguageApp.controller.PrincipalController;
import LanguageApp.controller.RegistrationController;
import LanguageApp.controller.WelcomeController;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
   private WelcomeController welcomeController;
   private FormController formController;
   private RegistrationController registrationController;
   private ForgetController forgetController;
   private DataBaseController dataBaseController;
   private BorderPane mainView;
   private AnchorPane principalView;
   private HBox loginView;
   private AnchorPane welcomeView;
   private AnchorPane formView;
   private AnchorPane registrationView;
   private AnchorPane forgetView;
   private AnchorPane dataBaseView;

   // Setting the global varibles
   private String usuario_nombre;
   private int usuario_activo;
   // The real usuario_id
   private static int usuario_id;
   // When I'm in the welcome screen
   boolean welcomeScreen;

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="init">
   /**
    * load the initial configuration before all the other things loads
    *
    * @throws Exception FilerException - if the same pathname has already been opened for writing, if the source module cannot be determined, or if the target
    * module is not writable, or if an explicit target module is specified and the location does not support it. IOException - if the file cannot be opened
    * IllegalArgumentException - for an unsupported location IllegalArgumentException - if moduleAndPkg is ill-formed IllegalArgumentException - if relativeName
    * is not relative
    */
   @Override
   public void init () throws Exception
   {
      super.init();
      Font.loadFont(MainScene.class.getResource("/LanguageApp/resources/fonts/FiraCode_3/FiraCode-Regular.ttf").
              toExternalForm(), 10);


      // Setting the welcome user
      usuario_nombre = null;
      usuario_id = 0;
      usuario_activo = 0;
      welcomeScreen = true;
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
      dataBaseView();
      // 
      mainView();
      //
      welcomeView();
      formView();
      registrationView();
      forgetView();
      loginView();

      // Checking if there's some active user
      usuario_nombre = handleCheckNombre();
      welcomeController.handleCheckNombre(usuario_nombre);

      this.mainStage.setResizable(false);
      this.mainStage.setWidth(1215);
      this.mainStage.setHeight(640);
      this.mainStage.show();

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

      } catch (IOException e) {
         e.printStackTrace();
      }
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
         loginController.loginViewAnchorPane.getChildren().add(welcomeView);

         // Create the Scene and put it in the center or the borderLayout
         mainView.setCenter(loginView);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Welcome">

   /**
    *
    */
   private void welcomeView ()
   {

      try {
         // Create the FXMLLoader
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainScene.class
                 .getResource("/LanguageApp/view/WelcomeView.fxml"));
         welcomeView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         welcomeController = loader.getController();
         welcomeController.setMainScene(this);

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

//<editor-fold defaultstate="collapsed" desc="RecordarView">
   private void forgetView ()
   {

      try {
         // Create the FXMLLoader
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainScene.class
                 .getResource("/LanguageApp/view/ForgetView.fxml"));

         forgetView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         forgetController = loader.getController();
         forgetController.setMainScene(this);

      } catch (IOException e) {
         e.printStackTrace();
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handles">

   /**
    *
    * @return
    */
   public String handleCheckNombre ()
   {
      Connection conn = null;
      Statement stmt = null;
      usuario_id = 0;
      usuario_nombre = null;

      // Preparing statement
      try {
         String sql = "SELECT usuario_id, usuario_nombre FROM usuarios WHERE usuario_activo = 1";
         // Try connection
         conn = dataBaseController.connect();

         stmt = conn.createStatement();
         //
         ResultSet rs = stmt.executeQuery(sql);

         while (rs.next()) {
            usuario_id = rs.getInt("usuario_id");
            usuario_nombre = rs.getString("usuario_nombre");
         }

         stmt.close();

         if (usuario_id > 0) {
            conn.close();
            //setting the Actul Static user
            return usuario_nombre;
         }

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / CheckUser()", e.toString(), e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception e) {
            message(Alert.AlertType.ERROR, "Error message", "MainScene / CheckUser()", e.toString(), e);
         }
      }
      return null;
   }

   /**
    *
    * @param activoBoolean
    */
   public void handleEntrar (boolean activoBoolean)
   {
      Connection conn = null;
      Statement stmt = null;
      PreparedStatement pstmt = null;

      // In SQLinte doesn't exit boolean
      usuario_activo = (activoBoolean) ? 1 : 0;

      try {
         // Try connection
         conn = dataBaseController.connect();
         conn.setAutoCommit(false);

         String sql = "UPDATE usuarios SET usuario_activo = 0;";
         stmt = conn.createStatement();
         stmt.executeUpdate(sql);
         conn.commit();
         stmt.close();
         // set the value
         sql = "UPDATE usuarios SET usuario_activo = ? WHERE usuario_id = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, usuario_activo);
         pstmt.setInt(2, usuario_id);
         pstmt.executeUpdate();
         conn.commit();
         pstmt.close();
         conn.close();

         welcomeScreen = false;
         principalView();
         handleDashBoardMenu();

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / handleEntrar()", e.toString(), e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception e) {
            message(Alert.AlertType.ERROR, "Error message", "MainScene / handleEntrar()", e.toString(), e);
         }
      }
   }

   /**
    * @param usuarioString
    * @param passwordString
    * @param activoBoolean
    * @return
    */
   public boolean handlelogin (String usuarioString, String passwordString, boolean activoBoolean)
   {

      Connection conn = null;
      PreparedStatement pstmt = null;
      Statement stmt = null;
      // In SQLinte doesn't exit boolean
      usuario_activo = (activoBoolean) ? 1 : 0;

      // Preparing statement
      String sql = "SELECT usuario_id FROM usuarios WHERE usuario_nombre = ? and password = ?";
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


         if (rs.next()) {
            usuario_id = rs.getInt("usuario_id");

            // put the user usuario_activo or not

            sql = "UPDATE usuarios SET usuario_activo = 0;";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            
            conn.commit();

            // set the value
            sql = "UPDATE usuarios SET usuario_activo = ? WHERE usuario_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, usuario_activo);
            pstmt.setInt(2, usuario_id);
            pstmt.executeUpdate();
            conn.commit();
            stmt.close();

            // Start the dashborad
            welcomeScreen = false;
            principalView();
            handleDashBoardMenu();
         }

         // if exits the user and password return true
         pstmt.close();
         conn.close();

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / handleLogin()", e.toString(), e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            message(Alert.AlertType.ERROR, "Error message", "MainScene / handleLogin()", e.toString(), e);
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
   public boolean handleRegistro (String usuarioString, String passwordString, String preguntaString, String respuestaString)
   {

      Connection conn = null;
      PreparedStatement pstmt = null;

      // Preparing statement
      String sql = "SELECT usuario_id FROM usuarios WHERE usuario_nombre = ? and password = ?";
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

         sql = "INSERT INTO  usuarios (usuario_nombre, password, usuario_activo, pregunta, respuesta) " +
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
         message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception e) {
            message(Alert.AlertType.ERROR, "Error message", "DataBaseController / handleRegistro()", e.toString(), e);
         }
      }
      return false;
   }

   public String handleRecordar (String usuarioString, String preguntaString, String respuestaString)
   {

      Connection conn = null;
      PreparedStatement pstmt = null;
      String password = null;

      // Preparing statement
      String sql = "SELECT password FROM usuarios WHERE usuario_nombre = ? and pregunta = ? and respuesta = ?";
      try {
         // Try connection
         conn = dataBaseController.connect();
         conn.setAutoCommit(false);

         // preparing statement
         pstmt = conn.prepareStatement(sql);

         // set the value
         pstmt.setString(1, usuarioString);
         pstmt.setString(2, preguntaString);
         pstmt.setString(3, respuestaString);

         //
         ResultSet rs = pstmt.executeQuery();
         conn.commit();

         while (rs.next()) {
            password = rs.getString(1);
            return password;
         }

         pstmt.close();
         conn.close();

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception e) {
            message(Alert.AlertType.ERROR, "Error message", "DataBaseController / handleRegistro()", e.toString(), e);
         }
      }
      return password;
   }

   /**
    * Change the scene registrationView form to formView
    *
    */
   public void handleAntiguoUsuario ()
   {
      // Create the Scene formView
      loginView.getChildren().remove(registrationView);
      loginView.getChildren().add(formView);
   }

   /**
    * Change the scene from formView to forgetView
    *
    */
   public void handleForgetUsuario ()
   {
      // Create the Scene forgetView
      loginView.getChildren().remove(formView);
      loginView.getChildren().add(forgetView);
   }

   /**
    * Change the scene from forgetView to formView
    *
    */
   public void handleAntiguoUsuarioForget ()
   {
      // Create the Scene formView
      loginView.getChildren().remove(forgetView);
      loginView.getChildren().add(formView);
   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setters and Getters">
   /**
    *
    * @param usuario_id
    *
    */
   public static void gg (int usuario_id)
   {
      MainScene.usuario_id = usuario_id;
   }

   /**
    *
    * @param usuario_id
    * @return
    *
    */
   public static int getUsuario_id ()
   {
      return usuario_id;
   }

   /**
    * Change the scene from formView to registrationView
    *
    */
   public void handleNuevoUsuario ()
   {
      // Create the Scene registrationView
      loginView.getChildren().remove(formView);
      loginView.getChildren().add(registrationView);
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
    * it's called by mainController when i click in the open menu
    */
   public void handleOpenMenu ()
   {
      try {
         // if doesn't be user, return
         if (welcomeScreen) {
            return;
         }
         principalController.handleOpenMenu();
      } catch (Exception e) {
      }
   }

   /**
    * it's called by mainController when i click in the close menu
    */
   public void handleCloseMenu ()
   {
      try {
         // if doesn't be user, return
         if (welcomeScreen) {
            return;
         }
         principalController.handleCloseMenu("handleCloseMenu");
      } catch (Exception e) {
      }
   }

   /**
    * it's called by mainController when i click in the Controles menu
    */
   public void handleControlesMenu ()
   {
      message(Alert.AlertType.INFORMATION, "LanguageApp", "Ayuda",
              "Controles: \n\n" +
              "Cursores:  para desplazarte por los diferentes " +
              "elementos.\n\n" +
              "Barra espaciadora / Enter:  para activar los " +
              "elementos.\n\n" +
              "Control + Derecha / Izquierda: para mover los slider de volumen" +
              ", Balance, velocidad y control de la película.\n\n" +
              "Control o Shift: para seleccionar mas de una palabra al " +
              "reproducirla.\n\n", null);
   }

   /**
    * it's called by mainController when i click in the About menu
    */
   public void handleAboutMenu ()
   {
     message(Alert.AlertType.INFORMATION, "LanguageApp", "Sobre esta aplicación:", "Autor: Roberto Garrido Trillo",
              null);
   }

   /**
    * it's called by mainController when i click in the Login menu
    */
   public void handleLoginMenu ()
   {

      try {
         // Create the Scene and put it in the center or the borderLayout
         mainView.getChildren().removeAll(principalView, dataBaseView);
         loginView.getChildren().removeAll(welcomeView, formView, registrationView, forgetView);
         loginView.getChildren().add(formView);
         mainView.setCenter(loginView);
         welcomeScreen = true;

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
    * it's called by mainController when i click in the Login menu
    */
   public void handleUnloginMenu ()
   {
      // if doesn't be user, return
      if (welcomeScreen && usuario_id == 0) {
         return;
      }
      boolean salida = message(Alert.AlertType.CONFIRMATION, "Cerrar sesión", "Cerrar la sesión?", "", null);
      if (!salida) {
         return;
      }

      try {
         // Create the Scene and put it in the center or the borderLayout
         mainView.getChildren().removeAll(principalView, dataBaseView);
         loginView.getChildren().removeAll(welcomeView, formView, registrationView, forgetView);

         // Deleting the active in the database
         handleEntrar(false);
         // Deleting the global variables
         usuario_id = 0;
         usuario_nombre = null;
         usuario_activo = 0;
         welcomeScreen = true;

         // Checking if there's some active user
         usuario_nombre = handleCheckNombre();
         welcomeController.handleCheckNombre(usuario_nombre);
         loginView.getChildren().add(welcomeView);
         mainView.setCenter(loginView);


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
      // if doesn't be user, return
      if (usuario_id == 0) {
         return;
      }
      try {
         mainView.getChildren().removeAll(principalView, dataBaseView, loginView);
         mainView.setCenter(principalView);

         String result = principalController.getMediaStatus();
         if (result.equals("pause")) {
            principalController.handlePlayButton();
         } else if (result.equals("originalPause")) {
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
      // if doesn't be user, return
      if (usuario_id == 0) {
         return;
      }
      try {
         // Create the Scene and put it in the center or the borderLayout
         mainView.getChildren().removeAll(principalView, dataBaseView, loginView);
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
   public boolean message (Alert.AlertType alertType, String title, String about, String contextText, Exception ex)
   {

      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      //lert.getDialogPane().setMinWidth(600);
      //alert.getDialogPane().setMinHeight(480);
      //alert.getDialogPane().setPrefWidth(600);
      //alert.getDialogPane().setPrefHeight(480);
      alert.setResizable(true);
      alert.getDialogPane().setHeaderText(about);
      alert.getDialogPane().setContentText(contextText);

      if (ex != null) {
         // Create expandable Exception.
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         ex.printStackTrace(pw);
         String exceptionText = sw.toString();

         Label label = new Label("El seguimiento del error fue:");

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

      try {
   Optional<ButtonType> result = alert.showAndWait();
         if (result.get() == ButtonType.OK) {
            return true;
         }
      } catch (Exception e) {
      }
      return false;
   }

//</editor-fold>


   /**
    * public static void main
    *
    * @param args
    */
   public static void main (String[] args)
   {
      launch(args);
   }
}
