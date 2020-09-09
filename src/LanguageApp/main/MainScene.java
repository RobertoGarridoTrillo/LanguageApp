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
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.util.Pair;
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

   // Setting the idioms's bundle
   ResourceBundle resources;
   Locale locale;
   FXMLLoader loader;

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
      Font.loadFont(MainScene.class.getResource("/LanguageApp/resources/fonts/freefont/FreeSans.ttf").toExternalForm(), 10);


      // Setting the welcome user
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

      // Setting the close button
      handleClose();
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
      Object nombre = handleCheckNombre().getValue();
      Object id = handleCheckNombre().getKey();

      usuario_nombre = (nombre != null) ? nombre.toString() : null;
      usuario_id = (id != null) ? (Integer) id : 0;

      // Put the name in the welcome label
      welcomeController.handlePutName(usuario_nombre);

      this.mainStage.setResizable(false);
      this.mainStage.setWidth(1215);
      this.mainStage.setHeight(640);
      this.mainStage.show();

      // Thread that charge the Principal Controlleer
      Task task = new Task<Void>() {
         @Override public Void call ()
         {
            {
               principalView();

            }
            return null;
         }
      };
      new Thread(task).start();
   }
//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="DataBaseView">
   private void dataBaseView ()
   {

      try {
         // Create the FXMLLoader
         handleLocale("DataBaseView");

         dataBaseView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         dataBaseController = loader.getController();
         dataBaseController.setMainScene(this);

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / dataBaseView()", e.toString(), e);
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
         handleLocale("MainView");

         mainView = (BorderPane) loader.load();

         // Set the Scene to the Stage (It the main wiew)
         mainScene = new Scene(mainView);
         mainStage.setScene(mainScene);

         // Give the mainController access to the main app (It´s like a instance)
         mainController = loader.getController();
         mainController.setMainScene(this);

         // Adding dark style
         JMetro jMetro = new JMetro(Style.DARK);
         jMetro.setScene(mainScene);

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / mainView()", e.toString(), e);
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="pricipalView">

   /**
    *
    */
   private void principalView ()
   {

      try {
         // Create the FXMLLoader
         handleLocale("PrincipalView");

         principalView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         principalController = loader.getController();
         principalController.setMainScene(this);

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / principalView()", e.toString(), e);
      }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="LoginView">
   private void loginView ()
   {

      try {
         // Create the FXMLLoader
         handleLocale("LoginView");

         loginView = (HBox) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         loginController = loader.getController();
         loginController.setMainScene(this);

         // Put the welcome in the main (It's the initial view)
         loginController.loginViewAnchorPane.getChildren().add(welcomeView);

         // Create the Scene and put it in the center or the borderLayout
         mainView.setCenter(loginView);

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / loginView()", e.toString(), e);
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
         handleLocale("WelcomeView");

         welcomeView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         welcomeController = loader.getController();
         welcomeController.setMainScene(this);

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / welcomeView()", e.toString(), e);
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
         handleLocale("FormView");

         formView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         formController = loader.getController();
         formController.setMainScene(this);

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / formView()", e.toString(), e);
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="RegisterView">
   private void registrationView ()
   {

      try {
         // Create the FXMLLoader
         handleLocale("RegistrationView");

         registrationView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         registrationController = loader.getController();
         registrationController.setMainScene(this);

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / registrationView()", e.toString(), e);
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="RecordarView">
   private void forgetView ()
   {

      try {
         // Create the FXMLLoader
         handleLocale("ForgetView");

         forgetView = (AnchorPane) loader.load();

         // Give the mainController access to the main app (It´s like a instance)
         forgetController = loader.getController();
         forgetController.setMainScene(this);

      } catch (IOException e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / forgetView()", e.toString(), e);
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handles">

   /**
    *
    * @return
    */
   public Pair handleCheckNombre ()
   {
      Connection conn = null;
      Statement stmt = null;
      usuario_nombre = null;

      usuario_nombre = null;

      // Preparing statement
      try {
         String sql = "SELECT usuario_id, usuario_nombre FROM usuarios WHERE usuario_activo = 1";
         // Try connection
         conn = dataBaseController.connect();

         stmt = conn.createStatement();
         //
         ResultSet rs = stmt.executeQuery(sql);

         if (rs.next()) {
            usuario_id = rs.getInt("usuario_id");
            usuario_nombre = rs.getString("usuario_nombre");
         } else {
            usuario_id = 0;
         }

         stmt.close();
         conn.close();

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
      return new Pair(usuario_id, usuario_nombre);
   }

   /**
    *
    * @param activoBoolean
    */
   public void handleEntrar (boolean activoBoolean, boolean usuario_last)
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

         if (!usuario_last) {
            principalView();
         }
         welcomeScreen = false;
         buttonDashBoardMenu();

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
    *
    */
   public Pair handleCheckUser (String usuarioString, String passwordString)
   {
      Connection conn = null;
      PreparedStatement pstmt = null;
      Statement stmt = null;

      usuario_nombre = null;

      // Preparing statement
      String sql = "SELECT usuario_id, usuario_nombre FROM usuarios WHERE usuario_nombre = ? and password = ?";
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
            usuario_nombre = rs.getString("usuario_nombre");
         } else {

         }
      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / handleCheckUser()", e.toString(), e);
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            message(Alert.AlertType.ERROR, "Error message", "MainScene / handleCheckUser()", e.toString(), e);
         }
      }
      return new Pair(usuario_id, usuario_nombre);
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

   /**
    *
    */
   public void handleClose ()
   {
      mainStage.setOnCloseRequest(e -> {
         e.consume();
         if (!message(Alert.AlertType.CONFIRMATION, "Salir",
                 "¿Salir de la aplicación?", "", null)) {
         } else {
         Platform.exit();
         System.exit(0);
         }
      });
   }

   /**
    *
    */
   private void handleLocale (String s)
   {
      try {
         // Create an array with the idioms of the app
         String[] languages = {"en", "es", "fr", "it", "ja"};

         // Get the local language
         locale = new Locale(Locale.getDefault().getLanguage());

         // if the local doesn't exit in the app, it uses "en"
         boolean salida = false;
         for (String language : languages) {
            if (locale.getLanguage().equals(new Locale(language).getLanguage())) {
               salida = true;
               break;
            }
         }
         if (!salida) {
            locale = new Locale("en");
         }

         resources = ResourceBundle.getBundle("LanguageApp.resources.bundles.LanguageApp", locale);
         URL urlFXML = new URL(MainScene.class
                 .getResource("/LanguageApp/view/" + s + ".fxml").toExternalForm());

         loader = new FXMLLoader(urlFXML, resources);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setters and Getters">
   /**
    *
    * @param id
    * @param usuario_id
    *
    */
   public static void setUsuario_id (int usuario_id)
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
      return MainScene.usuario_id;
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
   public void buttonOpenMenu ()
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
   public void buttonCloseMenu ()
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
    * it's called by mainController when i click in the close menu
    */
   public void buttonExitMenu ()
   {
      if (!message(Alert.AlertType.CONFIRMATION, "Salir",
              "¿Salir de la aplicación?", "", null)) {
         return;
      }

      Platform.exit();
      System.exit(0);
   }


   /**
    * it's called by mainController when i click in the Controles menu
    */
   public void buttonControlesMenu ()
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
   public void buttonAboutMenu ()
   {
      message(Alert.AlertType.INFORMATION, "LanguageApp", "Sobre esta aplicación:", "Autor: Roberto Garrido Trillo",
              null);
   }

   /**
    * it's called by mainController when i click in the Login menu
    */
   public void buttonLoginMenu ()
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
   public void buttonUnloginMenu ()
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
         Platform.runLater(new Runnable() {
            @Override
            public void run ()
            {
               handleEntrar(false, false);
            }
         });
         // Deleting the global variables
         usuario_id = 0;
         usuario_nombre = null;
         usuario_activo = 0;
         welcomeScreen = true;

         welcomeController.handlePutName(usuario_nombre); //null
         loginView.getChildren().add(welcomeView);
         mainView.setCenter(loginView);

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonUnloginMenu()", e.toString(), e);
      }
   }

   /**
    * it's called by Welcome when i click in the Crear cuenta
    */
   public void buttorRegistro ()
   {

      try {
         // Create the Scene and put it in the center or the borderLayout
         mainView.getChildren().removeAll(principalView, dataBaseView);
         loginView.getChildren().removeAll(welcomeView, formView, registrationView, forgetView);
         loginView.getChildren().add(registrationView);
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
    * it's called by mainController when i click in the Dashboard menu
    */
   public void buttonDashBoardMenu ()
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
   public void buttonDatabaseMenu ()
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
