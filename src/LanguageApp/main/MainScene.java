package LanguageApp.main;

import LanguageApp.controller.DataBaseController;
import LanguageApp.controller.ForgetController;
import LanguageApp.controller.FormController;
import LanguageApp.controller.LoginController;
import LanguageApp.controller.MainController;
import LanguageApp.controller.PrincipalController;
import LanguageApp.controller.RegistrationController;
import LanguageApp.controller.WelcomeController;
import LanguageApp.util.HandleLocale01;
import LanguageApp.util.Message;
import LanguageApp.util.PreguntasRegistro;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class MainScene extends Application
 {

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

  // pop-up messages
  Message message;

  // Preguntas para el registro y la recuperacion
  PreguntasRegistro pr = new PreguntasRegistro();

  // Savepoint
  Savepoint sp;

  // Value  and text of the progress
  private DoubleProperty progressBarValue;
  private StringProperty labelText;

  // Flags of the menu item
  private String[] idiomaSubt;

  // Fade in / out
  private String centerString;
  private Node centerNode;

  FadeTransition fIn, fadeLoginIn;
  FadeTransition fOut, fadeLoginOut;

  ChangeListener mainChangeListener;

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="init">

  /**
   * load the initial configuration before all the other things loads
   *
   * @throws Exception FilerException - if the same pathname has already been opened for writing, if the source module cannot be determined, or if the target module is not writable, or if an explicit
   * target module is specified and the location does not support it. IOException - if the file cannot be opened IllegalArgumentException - for an unsupported location IllegalArgumentException - if
   * moduleAndPkg is ill-formed IllegalArgumentException - if relativeName is not relative
   */
  @Override
  public void init() throws Exception
   {
    super.init();
    Font.loadFont(MainScene.class.getResource("/LanguageApp/resources/fonts/freefont/FreeSans.ttf").toExternalForm(), 10);

    // Setting the welcome user
    usuario_activo = 0;
    welcomeScreen = true;

    // setting progressbar. I use a doubleProperty (it's a class wrapper) instead double.
    progressBarValue = new SimpleDoubleProperty();
    labelText = new SimpleStringProperty();

    progressBarValue.setValue(0.0);
    labelText.setValue("");
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
  public void start(Stage mainStage)
   {
    this.mainStage = mainStage;

    // Create the locale for the pop up messages
    resources = HandleLocale01.handleLocale01();
    message = new Message(resources);

    // Set the Title to the Stage
    this.mainStage.setTitle("LanguagesApp");

    // Set the application icon.
    this.mainStage.getIcons().add(new Image(getClass()
            .getResourceAsStream("/LanguageApp/resources/images/languages_128.png")));

    // Setting the close button
    handleClose();

    dataBaseView();
    mainView();
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


    // Checking the colour of the back progress bar
    centerNode = mainController.checkCenter();
    centerString = centerNode.getId();

    // Put the name in the welcome label
    welcomeController.handlePutName(usuario_nombre);

    this.mainStage.setResizable(false);
    //this.mainStage.setWidth(1215);
    //this.mainStage.setHeight(710);

    this.mainStage.centerOnScreen();
    this.mainStage.show();

    principalView();
   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Welcome">

  /**
   *
   */
  private void welcomeView()
   {

    try {
      // Create the FXMLLoader
      HandleLocale01.handleLocale01();
      handleLocale02("WelcomeView");

      welcomeView = (AnchorPane) loader.load();

      // Give the mainController access to the main app (It´s like a instance)
      welcomeController = loader.getController();
      welcomeController.setMainScene(this);


    } catch (IOException e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / welcomeView()", e.toString(), e);
    }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="FormView">
  /**
   *
   */
  private void formView()
   {

    try {
      // Create the FXMLLoader
      HandleLocale01.handleLocale01();
      handleLocale02("FormView");

      formView = (AnchorPane) loader.load();

      // Give the mainController access to the main app (It´s like a instance)
      formController = loader.getController();
      formController.setMainScene(this);

    } catch (IOException e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / formView()", e.toString(), e);
    }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="RegisterView">
  private void registrationView()
   {

    try {
      // Create the FXMLLoader
      HandleLocale01.handleLocale01();
      handleLocale02("RegistrationView");

      registrationView = (AnchorPane) loader.load();

      // Give the mainController access to the main app (It´s like a instance)
      registrationController = loader.getController();
      registrationController.setMainScene(this);

    } catch (IOException e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / registrationView()", e.toString(), e);
    }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="RecordarView">
  private void forgetView()
   {

    try {
      // Create the FXMLLoader
      HandleLocale01.handleLocale01();
      handleLocale02("ForgetView");

      forgetView = (AnchorPane) loader.load();

      // Give the mainController access to the main app (It´s like a instance)
      forgetController = loader.getController();
      forgetController.setMainScene(this);

    } catch (IOException e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / forgetView()", e.toString(), e);
    }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="LoginView">

  private void loginView()
   {

    try {
      // Create the FXMLLoader
      HandleLocale01.handleLocale01();
      handleLocale02("LoginView");

      loginView = (HBox) loader.load();

      // Give the mainController access to the main app (It´s like a instance)
      loginController = loader.getController();
      loginController.setMainScene(this);

      // Put the welcome in the main (It's the initial view)
      loginController.loginViewHbox.getChildren().add(welcomeView);

      // Create the Scene and put it in the center or the borderLayout
      mainView.setCenter(loginView);

    } catch (IOException e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / loginView()", e.toString(), e);
    }
   }

//</editor-fold>  

//<editor-fold defaultstate="collapsed" desc="mainView">

  /**
   * It managed the load of the main fxml into the border panel that I use it like root.
   *
   * i can also create a "link" to the controller, in case I need to send data.
   */
  private void mainView()

   {
    try {
      // Create the FXMLLoader
      HandleLocale01.handleLocale01();
      handleLocale02("MainView");

      mainView = (BorderPane) loader.load();

      // Set the Scene to the Stage (It the main wiew)
      mainScene = new Scene(mainView);
      mainStage.setScene(mainScene);

      // Default color
      //mainScene.setFill(Color.rgb(0, 79, 138));
      mainScene.setFill(Color.rgb(37, 37, 37));

      // Give the mainController access to the main app (It´s like a instance)
      mainController = loader.getController();
      mainController.setMainScene(this);

      // Adding dark style
      JMetro jMetro = new JMetro(Style.DARK);
      jMetro.setScene(mainScene);

    } catch (IOException e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / mainView()", e.toString(), e);
    }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="pricipalView">

  /**
   *
   */
  private void principalView()
   {

    try {
      // Create the FXMLLoader
      HandleLocale01.handleLocale01();
      handleLocale02("PrincipalView");

      principalView = (AnchorPane) loader.load();

      // Give the mainController access to the main app (It´s like a instance)
      principalController = loader.getController();
      principalController.setMainScene(this);

    } catch (IOException e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / principalView()", e.toString(), e);
    }
   }

//</editor-fold>  

//<editor-fold defaultstate="collapsed" desc="DataBaseView">

  private void dataBaseView()
   {

    try {
      // Create the FXMLLoader
      HandleLocale01.handleLocale01();
      handleLocale02("DataBaseView");

      dataBaseView = (AnchorPane) loader.load();

      // Give the mainController access to the main app (It´s like a instance)
      dataBaseController = loader.getController();
      dataBaseController.setMainScene(this);

    } catch (IOException e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / dataBaseView()", e.toString(), e);
    }
   }

//</editor-fold>  

//<editor-fold defaultstate="collapsed" desc="Handles">

  /**
   *
   * @return
   */
  public Pair handleCheckNombre()
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
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / CheckUser()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / CheckUser()", e.toString(), e);
      }
    }
    return new Pair(usuario_id, usuario_nombre);
   }

  /**
   *
   * @param activoBoolean
   * @param usuario_last
   */
  public void handleEntrar(boolean activoBoolean, boolean usuario_last)
   {

    Connection conn = null;
    Statement stmt = null;

    // In SQLinte doesn't exit boolean
    usuario_activo = (activoBoolean) ? 1 : 0;

    try {
      // Try connection
      conn = dataBaseController.connect();
      conn.setAutoCommit(false);

      handlBorrarMarcar(activoBoolean);

      if (!usuario_last) {
        // First I close the thead media to slider
        principalController.handleCloseMenu("buttonLoginMenu");

        principalView();
      }

      // change the center
      welcomeScreen = false;
      buttonDashBoardMenu();

      // change the color of the anchor panel bottom
      mainController.checkCenter();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleEntrar()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleEntrar()", e.toString(), e);
      }
    }
   }

  /**
   *
   * @param activoBoolean If only wants to delete, put it false
   */
  public void handlBorrarMarcar(boolean activoBoolean)
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

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handlBorrarMarcar()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handlBorrarMarcar()", e.toString(), e);
      }
    }
   }

  /**
   *
   * @param usuarioString
   * @param passwordString
   * @return
   */
  public Pair handleCheckUser(String usuarioString, String passwordString)
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
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleCheckUser()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleCheckUser()", e.toString(), e);
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
  public int handleRegistro(String usuarioString, String passwordString, String preguntaString, String respuestaString)
   {

    Connection conn = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    String sql = null;
    Pair usuario;

    try {

      //  if don't, I create him
      usuario = handleCheckUser(usuarioString, passwordString);
      if ((String) usuario.getValue() != null) {
        return (Integer) usuario.getKey();
      }

      // I create the user
      conn = dataBaseController.connect();
      conn.setAutoCommit(false);
      sp = conn.setSavepoint("Registration");

      handlBorrarMarcar(false); //put all the usuario_id to 0

      // Preparing statement
      sql = "INSERT INTO  usuarios (usuario_nombre, password, usuario_activo, pregunta, respuesta) " + "VALUES (?,?,1,?,?)";
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
      try {
        conn.rollback(sp);
      } catch (SQLException ex) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", ex.toString(), ex);
      }
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
      }
    }

    // Read the usuario_id of the rec
    usuario = handleCheckUser(usuarioString, passwordString);
    setUsuario_id(usuario_id);

    return 0;
   }

  public String handleRecordar(String usuarioString, String preguntaString, String respuestaString)
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
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
      }
    }
    return password;
   }

  /**
   * Change the scene from formView to forgetView
   *
   */
  public void handleForgetUsuario()
   {

    setFadeLogin("forget");

   }

  /* ------------------------there are two methods of close*/
  /**
   *
   */
  public void handleClose() // the close button
   {
    mainStage.setOnCloseRequest(e -> {
      e.consume();

      fadeOldOut();

      if (!message.message(Alert.AlertType.CONFIRMATION, "Salir", "¿Quieres salir de la aplicación?", "", null)) {
        fadeNewIn();
        fadeOldIn();
        return;
      }
      Platform.exit();
      System.exit(0);

    });
   }

  /**
   *
   */
  private void handleLocale02(String s)
   {
    try {
      HandleLocale01.handleLocale01();
      URL urlFXML = new URL(MainScene.class
              .getResource("/LanguageApp/view/" + s + ".fxml").toExternalForm());

      loader = new FXMLLoader(urlFXML, resources);
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
    }
   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setters and Getters">

  /**
   *
   * @param usuario_id
   *
   */
  public static void setUsuario_id(int usuario_id)
   {
    MainScene.usuario_id = usuario_id;
   }

  /**
   *
   * @return
   *
   */
  public static int getUsuario_id()
   {
    return MainScene.usuario_id;
   }

  /**
   *
   * @return
   */
  public double getProgressBarValue()
   {
    return progressBarValue.getValue();
   }

  /**
   *
   * @param longitud
   */
  public void setProgressBarValue(double longitud)
   {
    double oldValue = Math.round(getProgressBarValue() * 100.0) / 100.0;
    double newvalue = Math.round((1 / longitud) * 100.0) / 100.0;
    progressBarValue.set(oldValue + newvalue);

    mainController.setProgressBarValue(progressBarValue);
    welcomeController.setProgressBarValue(progressBarValue);
    formController.setProgressBarValue(progressBarValue);
    registrationController.setProgressBarValue(progressBarValue);
    forgetController.setProgressBarValue(progressBarValue);
    //System.out.println("mainScene " + progressBarValue.getValue());

    if (progressBarValue.getValue() >= 1) progressBarValue.set(0.0);
   }

  /**
   *
   * @return
   */
  public String getLabelText()
   {
    return labelText.getValue();
   }

  /**
   *
   * @param text
   */
  public void setLabelText(String text)
   {
    labelText.setValue(text);
    mainController.setLabelText(text);
   }

  public void setFlagMenu(String[] s)
   {

    idiomaSubt = new String[s.length];
    for (String string : s) {
      idiomaSubt = s;
      mainController.setEraserFlags(s);
    }

   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Menu Buttons">

  /**
   * it's called by mainController when i click in the open menu
   */
  public void buttonOpenMenu()
   {
    try {
      // if doesn't be user, return
      if (welcomeScreen) {
        return;
      }

      fadeOldOut();

      if (principalController.handleOpenMenu()) {

        mainView.getChildren().removeAll(loginView, principalView, dataBaseView);
        loginView.getChildren().removeAll(welcomeView, formView, registrationView, forgetView);

        loginView.getChildren().add(welcomeView);
        mainView.setCenter(principalView);

        loginView.setOpacity(1.0);
        welcomeScreen = false;
        fadeNewIn();
        fadeOldIn();
      } else {
        fadeOldIn();
      }

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonOpenMenu()", e.toString(), e);
    }
   }

  /**
   * it's called by mainController when i click in the close menu
   */
  public void buttonCloseMenu()
   {
    try {
      // if doesn't be user, return
      if (welcomeScreen) {
        return;
      }

      fadeOldOut();

      if (!message.message(Alert.AlertType.CONFIRMATION, "Salir",
              "¿Quieres cerrar el archivo?", "", null)) {
        fadeOldIn();
        return;
      } else {
        fadeOldIn();
        fadeNewIn();
      }
      principalController.handleCloseMenu("handleCloseMenu");
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonCloseMenu()", e.toString(), e);
    }
   }

  /* ------------------------there are two methods of close*/
  /**
   * it's called by mainController when i click in the close menu
   */
  public void buttonExitMenu()
   {

    fadeOldOut();

    if (!message.message(Alert.AlertType.CONFIRMATION, "Salir",
            "¿Quieres salir de la aplicación?", "", null)) {

      fadeOldIn();
      return;
    }

    Platform.exit();
    System.exit(0);
   }

  /**
   * it's called by mainController when i click in the Controles menu
   */
  public void buttonControlesMenu()
   {
    fadeOldOut();
    message.message(Alert.AlertType.INFORMATION, "LanguageApp", "Ayuda",
            "Controles: \n\n" +
            "Cursores:  para desplazarte por los diferentes " +
            "elementos.\n\n" +
            "Barra espaciadora / Enter:  para activar los " +
            "elementos.\n\n" +
            "Control + Derecha / Izquierda: para mover los slider de volumen" +
            ", Balance, velocidad y control de la película.\n\n" +
            "Control o Shift: para seleccionar mas de una palabra al " +
            "reproducirla.\n\n", null);

    fadeOldIn();
   }

  /**
   * it's called by mainController when i click in the About menu
   */
  public void buttonAboutMenu()
   {
    fadeOldOut();

    message.message(Alert.AlertType.INFORMATION, "LanguageApp", "Acerca de esta aplicación:", "Autor: Roberto Garrido Trillo", null);
    fadeNewIn();
    fadeOldIn();
   }

  /**
   * it's called by mainController when i click in the Login menu
   */
  public void buttonLoginMenu()
   {

    try {
      centerNode = mainController.checkCenter();
      if (centerNode.getId().equals("loginViewHbox") &&
              loginView.getChildren().get(1).getId().equals("loginViewHbox")) return;


      setFadeLogin("login");

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonLoginMenu()", e.toString(), e);
    }
   }

  /**
   * it's called by mainController when i click in the Login menu
   */
  public void buttonUnloginMenu()
   {
    // if doesn't be user, return
    if (welcomeScreen && usuario_id == 0) {
      return;
    }

    fadeOldOut();

    boolean salida = message.message(Alert.AlertType.CONFIRMATION, "Cerrar sesión", "¿Quieres cerrar la sesión?", "", null);
    if (!salida) {
      fadeOldIn();
      fadeNewIn();
      return;
    }

    try {
      principalController.handleCloseMenu("buttonUnloginMenu");

      // Create the Scene and put it in the center or the borderLayout
      mainView.getChildren().removeAll(principalView, dataBaseView, loginView);
      loginView.getChildren().removeAll(welcomeView, formView, registrationView, forgetView);
      loginView.getChildren().add(welcomeView);
      mainView.setCenter(loginView);

      // Deleting the active in the database
      handlBorrarMarcar(false);

      // Deleting the global variables
      usuario_id = 0;
      usuario_nombre = null;
      usuario_activo = 0;
      welcomeScreen = true;

      welcomeController.handlePutName(usuario_nombre); //null

      fadeNewIn();
      fadeOldIn();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonUnloginMenu()", e.toString(), e);
    }
   }

  /**
   * it's called by Welcome when i click in the Crear cuenta
   */
  public void buttonRegistro()
   {

    try {
      centerNode = mainController.checkCenter();
      if (centerNode.getId().equals("loginViewHbox") &&
              loginView.getChildren().get(1).getId().equals("registrationViewanchorRight")) return;
      
      setFadeLogin("registro");

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonRegistro()", e.toString(), e);
    }
   }

  /**
   * it's called by Welcome when i click in the subtitle menu
   */
  public void buttonSubtitle()
   {
    // if doesn't be user, return
    if (welcomeScreen && usuario_id == 0) {
    }

   }

  /**
   * it's called by mainController when i click in the Dashboard menu
   */
  public void buttonDashBoardMenu()
   {
    // if doesn't be user, return
    if (usuario_id == 0 || welcomeScreen || mainController.checkCenter().getId().equals("principalViewAnchorPane")) {
      return;
    }

    try {

      setFadeLogin("dashboard");

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonDashBoardMenu()", e.toString(), e);
    }
   }

  /**
   * it's called by mainController when i click in the Resultados menu
   */
  public void buttonDatabaseMenu()
   {
    // if doesn't be user, return
    if (usuario_id == 0 || welcomeScreen || mainController.checkCenter().getId().equals("dataBaseViewAnchorPane")) {
      return;
    }
    try {

      setFadeLogin("database");

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonDatabaseMenu()", e.toString(), e);
    }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Total Fade In / Out">

  //<editor-fold defaultstate="collapsed" desc="Setting fade without filechooser">

  public void setFadeLogin(String destiny)
   {

    try {

      mainChangeListener = new ChangeListener<Double>()
       {
        @Override
        public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue)
         {
          //System.out.println("newValue " + newValue);
          if (newValue <= 0) {

            centerNode = mainView.getCenter();
            centerNode.opacityProperty().removeListener(mainChangeListener);

            mainView.getChildren().removeAll(loginView, principalView, dataBaseView);
            loginView.getChildren().removeAll(welcomeView, formView, registrationView, forgetView);


            // Temporal nodes
            Node loginNode = null;
            Node mainNode = null;

            switch (destiny) {

              case "login":
                loginNode = formView;
                mainNode = loginView;
                /*/*principalView.setOpacity(1.0);*/
                welcomeScreen = true;
                break;

              case "registro":
                loginNode = registrationView;
                mainNode = loginView;
                /*/*principalView.setOpacity(1.0);*/
                welcomeScreen = true;
                break;

              case "forget":
                loginNode = forgetView;
                mainNode = loginView;
                /*/*principalView.setOpacity(1.0);*/
                welcomeScreen = true;
                break;

              case "dashboard":
                loginNode = welcomeView;
                mainNode = principalView;
                /*/*loginView.setOpacity(1.0);*/
                welcomeScreen = false;
                break;

              case "database":
                loginNode = welcomeView;
                mainNode = dataBaseView;
                /*/*loginView.setOpacity(1.0);*/
                welcomeScreen = false;
                break;

              default:
                loginNode = welcomeView;
                mainNode = loginView;
                /*/*loginView.setOpacity(1.0);*/
                principalView.setOpacity(1.0);
                welcomeScreen = true;
                break;
            }

            loginView.getChildren().add(loginNode);
            mainView.setCenter(mainNode);

            fadeLoginIn = handleSetFadeIn();
            fadeLoginOut.stop();
            fadeLoginIn.play();

            String result = principalController.getMediaStatus();
            if (result.equals("playing")) {
              principalController.handlePlayButton();
            } else if (result.equals("playingOriginal")) {
              principalController.handlePlayButtonItemOriginal();
            }
          }
         }
       };

      //centerNode = mainController.checkCenter();
      fadeLoginOut = handleSetFadeOut();

      fadeLoginOut.play();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / buttonDatabaseMenusetFadeLogin()", e.toString(), e);
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Fade in / out of the scene with filechooser">

  /**
   *
   */
  public void fadeOldIn()
   {
    mainController.mainFadeOldIn();
   }

  /**
   *
   */
  public void fadeOldOut()
   {
    mainController.mainFadeOldOut();
   }

  /**
   *
   */
  public void fadeNewIn()
   {
    mainController.mainFadeNewIn();
   }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Fade in / out of the scene without filechooser">

  private FadeTransition handleSetFadeIn()
   {
    centerNode = mainController.checkCenter();

    fIn = new FadeTransition(Duration.millis(250), centerNode);
    //fIn.setFromValue(0.0);
    fIn.setToValue(1.0);
    return fIn;
   }

  private FadeTransition handleSetFadeOut()
   {
    centerNode = mainController.checkCenter();
    centerNode.opacityProperty().addListener(mainChangeListener);

    fOut = new FadeTransition(Duration.millis(250), centerNode);
    fOut.setFromValue(1.0);
    fOut.setToValue(0.0);
    return fOut;
   }

  //</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="getMainStage">

  /**
   * Returns the main stage, if you need it, for example to use it with the filechooser in the mainController class
   *
   * @return An object of type Stage
   */
  public static Stage getMainStage()
   {
    return mainStage;
   }

//</editor-fold>

  /**
   * public static void main
   *
   * @param args
   */
  public static void main(String[] args)
   {
    launch(args);
   }
 }
