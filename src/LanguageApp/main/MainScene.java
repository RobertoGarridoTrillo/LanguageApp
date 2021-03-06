package LanguageApp.main;

import LanguageApp.controller.DataBaseController;
import LanguageApp.controller.ForgetController;
import LanguageApp.controller.FormController;
import LanguageApp.controller.LoginController;
import LanguageApp.controller.MainController;
import LanguageApp.controller.PrincipalController;
import LanguageApp.controller.RegistrationController;
import LanguageApp.controller.WelcomeController;
import LanguageApp.model.Usuario;
import LanguageApp.util.ConnectionsPool;
import LanguageApp.util.Directory;
import LanguageApp.util.HandleLocale;
import LanguageApp.util.Message;
import LanguageApp.util.PreguntasRegistro;
import static LanguageApp.util.Message.message;
import static LanguageApp.util.Message.showException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Locale;
import java.util.ResourceBundle;
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


public class MainScene extends Application
 {

//<editor-fold defaultstate="collapsed" desc="fileds class">
  private static Stage mainStage;
  private static MainScene mainScene;
  private static Scene scene;

  private MainController mainController;
  private PrincipalController principalController;
  private LoginController loginController;
  private WelcomeController welcomeController;
  private FormController formController;
  private RegistrationController registrationController;
  private ForgetController forgetController;
  private static DataBaseController dataBaseController;
  private Directory dire;
  private BorderPane mainView;
  private AnchorPane principalView;
  private HBox loginView;
  private AnchorPane welcomeView;
  private AnchorPane formView;
  private AnchorPane registrationView;
  private AnchorPane forgetView;
  private AnchorPane dataBaseView;

  // Setting the global varibles
  private int usuario_activo; // if I remenber or not in the next login

  // The real usuario_id
  private static int usuario_id;
  private static String usuario_nombre;
  private static int usuario_ultimo;

  // When I'm in the welcome screen
  boolean welcomeScreen;

  // Setting the idioms's bundle
  ResourceBundle resources;
  Locale locale;
  FXMLLoader loader;

  // The pool connections
  ConnectionsPool connectionsPool;

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
  private String[] subtitle;

  // Fade in / out
  private Node centerNode;

  FadeTransition fIn, fadeLoginIn;
  FadeTransition fOut, fadeLoginOut;

  // the status of the media when change scene
  private String look;

  ChangeListener mainChangeListener;
//</editor-fold>  


//<editor-fold defaultstate="collapsed" desc="init">
  @Override
  public void init() throws Exception
   {
    try {
      super.init();

      // setting a referenc to mainScene (me)
      setMainScene(this);

      // Create the locale for the pop up messages
      resources = HandleLocale.getResource();
      message = new Message(resources);

      // Instances
      dire = new Directory();

      // Creating the pool conections
      connectionsPool = ConnectionsPool.create();

      // Load the text fonts
      Font.loadFont(MainScene.class
              .getResource("/LanguageApp/resources/fonts/freefont/FreeSans.ttf")
              .toExternalForm(), 10);

      // Setting the welcome user
      usuario_activo = 0;
      welcomeScreen = true;

      // setting progressbar. I use a doubleProperty (it's a class wrapper) 
      // instead double.
      progressBarValue = new SimpleDoubleProperty();
      labelText = new SimpleStringProperty();
      progressBarValue.setValue(0.0);
      labelText.setValue("");

      // Setting the status to the fade the change scene
      look = "";

    } catch (Exception e) {
      showException(e);
    }
   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="start">
  @Override
  public void start(Stage mainStage)
   {
    try {

      this.mainStage = mainStage;

      // Set the Title to the Stage
      this.mainStage.setTitle("LanguagesApp");

      // Set the application icon.
      this.mainStage.getIcons().add(new Image(getClass()
              .getResourceAsStream("/LanguageApp/resources/images/" +
                      "languages_128.png")));


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
      Pair pair = handleCheckNombre();
      Object id = pair.getKey();
      Object name = pair.getValue();

      // in the init usuario_last and usuario_id are the same
      /*/* usuario_id = (id != null) ? (Integer) id : 0; */
      setUsuario_id((Integer) id);
      usuario_ultimo = getUsuario_id();

      // Put the name in the welcome label
      if (name == null) {
        usuario_nombre = null;
        welcomeController.handlePutName(usuario_nombre);
      } else {
        usuario_nombre = (name.equals("root")) ? null : name.toString();
        welcomeController.handlePutName(usuario_nombre);
      }

      // Checking the colour of the back progress bar
      centerNode = mainController.checkCenter();

      this.mainStage.setResizable(false);
      //this.mainStage.setWidth(1215);
      //this.mainStage.setHeight(710);

      this.mainStage.centerOnScreen();
      this.mainStage.show();

      principalView();
    } catch (Exception e) {
      showException(e);
    }
   }
//</editor-fold> 


//<editor-fold defaultstate="collapsed" desc="Welcome">
  private void welcomeView() throws Exception
   {

    handleLoadViewAndResource("WelcomeView");

    welcomeView = (AnchorPane) loader.load();

    // Give the mainController access to the main app (It´s like a instance)
    welcomeController = loader.getController();
    welcomeController.setMainScene(this);
   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="FormView">
  private void formView() throws Exception
   {
    handleLoadViewAndResource("FormView");

    formView = (AnchorPane) loader.load();

    // Give the mainController access to the main app (It´s like a instance)
    formController = loader.getController();
    formController.setMainScene(this);
   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="RegisterView">
  private void registrationView() throws Exception
   {
    handleLoadViewAndResource("RegistrationView");

    registrationView = (AnchorPane) loader.load();

    // Give the mainController access to the main app (It´s like a instance)
    registrationController = loader.getController();
    registrationController.setMainScene(this);
   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="RecordarView">
  private void forgetView() throws Exception
   {
    handleLoadViewAndResource("ForgetView");

    forgetView = (AnchorPane) loader.load();

    // Give the mainController access to the main app (It´s like a instance)
    forgetController = loader.getController();
    forgetController.setMainScene(this);

   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="LoginView">
  private void loginView() throws Exception
   {
    handleLoadViewAndResource("LoginView");

    loginView = (HBox) loader.load();

    // Give the mainController access to the main app (It´s like a instance)
    loginController = loader.getController();
    loginController.setMainScene(this);

    // Put the welcome in the main (It's the initial view)
    loginController.loginViewHbox.getChildren().add(welcomeView);

    // Create the Scene and put it in the center or the borderLayout
    mainView.setCenter(loginView);
   }
//</editor-fold>  


//<editor-fold defaultstate="collapsed" desc="mainView">
  private void mainView() throws Exception
   {
    handleLoadViewAndResource("MainView");

    mainView = (BorderPane) loader.load();

    // Set the Scene to the Stage (It the main wiew)
    scene = new Scene(mainView);
    mainStage.setScene(scene);

    // Default color
    //mainScene.setFill(Color.rgb(0, 79, 138));
    scene.setFill(Color.rgb(37, 37, 37));

    // Give the mainController access to the main app (It´s like a instance)
    mainController = loader.getController();
    mainController.setMainScene(this);

    // Adding dark style
    JMetro jMetro = new JMetro(Style.DARK);
    jMetro.setScene(scene);
   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="principalView">
  public void principalView() throws Exception
   {
    handleLoadViewAndResource("PrincipalView");
    principalView = (AnchorPane) loader.load();

    // Give the mainController access to the main app (It´s like a instance)
    principalController = loader.getController();
    // I use getMainScene in databaseController
    //principalController.setMainScene(this);
   }
//</editor-fold>  


//<editor-fold defaultstate="collapsed" desc="DataBaseView">
  private void dataBaseView() throws Exception
   {
    handleLoadViewAndResource("DataBaseView");
    dataBaseView = (AnchorPane) loader.load();

    // Give the mainController access to the main app (It´s like a instance)
    dataBaseController = loader.getController();
    // I use getMainScene in databaseController
    //dataBaseController.setMainScene(this);
   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="Handles">
  //<editor-fold defaultstate="collapsed" desc="PrincipalController methods">
  public synchronized void handleCloseMenu(String origen) throws Exception
   {
    principalController.handleCloseMenu(origen);
   }

  //</editor-fold>

  /* ------------------------there are two methods of close*/
  //<editor-fold defaultstate="collapsed" desc="MainScene methods">
  public synchronized void handleEntrar(boolean activoBoolean, boolean usuario_last)
          throws Exception
   {

    // In SQLinte doesn't exit boolean
    usuario_activo = (activoBoolean) ? 1 : 0;
    usuario_ultimo = getUsuario_id();

    handleBorrarMarcar(activoBoolean, getUsuario_id());

    if (!usuario_last) {
      principalController.handleOpenMenu2Play();
    }

    // change the center
    welcomeScreen = false;

    setFadeLogin("dashboard");

    // change the color of the anchor panel bottom
    mainController.checkCenter();
   }


  public synchronized void handleForgetUsuario() throws Exception
   {
    setFadeLogin("forget");
   }


  public synchronized void handleClose() throws Exception // the close button
   {
    mainStage.setOnCloseRequest(e -> {
      try {
        e.consume();
        fadeOldOut();
        helperFadePlayOut("withFileChooser");
        if (!message(Alert.AlertType.CONFIRMATION, "Salir",
                "¿Quieres salir de la aplicación?", "", null)) {
          fadeNewIn();
          fadeOldIn();
          helperFadePlayIn("withFileChooser");
          return;
        }
      } catch (Exception ex) {
        showException(ex);
      }
      Platform.exit();
      System.exit(0);

    });
   }
  //</editor-fold>


  //<editor-fold defaultstate="collapsed" desc="DataBase methods">
  /*/*public synchronized int handleCreate(String usuarioString, String passwordString,
          boolean activoBoolean, String preguntaString, String respuestaString)
          throws Exception
   {

    int ret = dataBaseController.handleRegistro(usuarioString, passwordString,
            activoBoolean, preguntaString, respuestaString);

    dataBaseController.handleRegistro03(usuarioString, passwordString, activoBoolean,
            preguntaString, respuestaString);
    dataBaseController.handleCloseModal();

    return ret;
   }*/
  public synchronized String handleRecordar(String usuarioString, String preguntaString,
          String respuestaString) throws Exception
   {
    return dataBaseController.handleRecordar(usuarioString, preguntaString, respuestaString);
   }


  public synchronized void handleUpdate(Usuario user) throws Exception
   {
    dataBaseController.handleUpdate(user);
   }


  public synchronized void handleCloseModal() throws Exception
   {
    dataBaseController.handleCloseModal();
   }


  public synchronized Pair<String, String> handleUpdateCorrection(String trans, String currentTab,
          int indexItemV, double success) throws Exception
   {
    return dataBaseController.handleUpdateCorrection(trans, currentTab, indexItemV, success);
   }


  private synchronized void handleLoadViewAndResource(String s) throws Exception
   {
    URL urlFXML = new URL(MainScene.class
            .getResource("/LanguageApp/view/" + s + ".fxml").toExternalForm());

    loader = new FXMLLoader(urlFXML, resources);
   }


  public synchronized Pair<Integer, String> handleCheckNombre() throws SQLException, Exception
   {
    return dataBaseController.handleCheckNombre();
   }


  public synchronized Pair<Boolean, String> handleCheckMateriaActivo(int usuarioId) throws Exception
   {
    return dataBaseController.handleCheckMateriaActivo(usuarioId);
   }


  public synchronized void handleBorrarMarcar(boolean activoBoolean, int usuario_id)
          throws SQLException, Exception
   {
    dataBaseController.handleBorrarMarcar(activoBoolean, usuario_id);
   }


  public synchronized Pair<Integer, String> handleCheckUser(String usuarioString,
          String passwordString) throws SQLException, Exception
   {
    return dataBaseController.handleCheckUser(usuarioString, passwordString);
   }


  public synchronized int handleRegistro(String usuarioString, String passwordString,
          boolean activoBoolean, String preguntaString,
          String respuestaString) throws SQLException, Exception
   {
    return dataBaseController.handleRegistro(usuarioString, passwordString,
            activoBoolean, preguntaString, respuestaString);
   }


  public synchronized void handleRegistro02(String usuarioString, String passwordString,
          boolean activoBoolean) throws Exception
   {
    dataBaseController.handleRegistro02(usuarioString, passwordString, activoBoolean);
   }


  public synchronized void handleRegistro03(String usuarioString, String passwordString,
          boolean activoBoolean, String preguntaString, String respuestaString)
          throws Exception
   {
    dataBaseController.handleRegistro03(usuarioString, passwordString,
            activoBoolean, preguntaString, respuestaString);
   }


  public synchronized void handleDelete(Usuario u) throws SQLException, Exception
   {
    dataBaseController.handleDelete(u);
   }


  public synchronized ResultSet handleCheckInit(int usuario_id) throws Exception
   {
    return dataBaseController.handleCheckInit(usuario_id);
   }


  public synchronized void handleCreateIni(int usuario_id) throws Exception
   {
    dataBaseController.handleCreateinit(usuario_id);
   }


  public synchronized int handleCheckAndSetLastDirectory(String lastFile, String lastDirectory,
          int usuario_id) throws Exception
   {
    return dataBaseController.handleCheckAndSetLastDirectory(lastFile, lastDirectory, usuario_id);
   }


  public synchronized int handleCheckAndSetIdioma(String[] subtitle) throws Exception
   {
    return dataBaseController.handleCheckAndSetIdioma(subtitle);
   }


  public void handleDeleteFromMateria(int usuario_id) throws Exception
   {
    dataBaseController.handleDeleteFromMateria(usuario_id);
   }


  public void handleActualizarUsuario() throws Exception
   {
    dataBaseController.handleActualizarUsuario();
   }
  //</editor-fold>


  //<editor-fold defaultstate="collapsed" desc="Directory methods">
  public boolean direCheckIni(ResultSet rs) throws Exception
   {
    return dire.direCheckIni(rs);
   }


  public void direSetMateriaId(int materia_id) throws Exception
   {
    dire.direSetMateriaId(materia_id);
   }


  public void direSetName(String name) throws Exception
   {
    dire.direSetName(name);
   }


  public String getPath() throws Exception
   {
    return dire.getPath();
   }


  public void setPath(String path) throws Exception
   {
    dire.setPath(path);
   }


  public String getLastDirectory() throws Exception
   {
    return dire.getLastDirectory();
   }


  public void setLastDirectory(String lastDirectory) throws Exception
   {
    dire.setLastDirectory(lastDirectory);
   }


  public String getLastFile() throws Exception
   {
    return dire.getLastFile();
   }


  public void setLastFile(String lastFile) throws Exception
   {
    dire.setLastFile(lastFile);
   }
  //</editor-fold>
  //</editor-fold>


//<editor-fold defaultstate="collapsed" desc="Setters and Getters">
  public static void setUsuario_id(int usuario_id) throws Exception
   {
    MainScene.usuario_id = usuario_id;
   }


  public static int getUsuario_id() throws Exception
   {
    return MainScene.usuario_id;
   }


  public static void setUsuario_nombre(String usuario_nombre) throws Exception
   {
    MainScene.usuario_nombre = usuario_nombre;
   }


  public static String getUsuario_nombre() throws Exception
   {
    return MainScene.usuario_nombre;
   }


  public static void setUsuario_ultimo(int usuario_ultimo)
   {
    MainScene.usuario_ultimo = usuario_ultimo;
   }


  public static int getUsuario_ultimo() throws Exception
   {
    return usuario_ultimo;
   }


  public double getProgressBarValue() throws Exception
   {
    return progressBarValue.getValue();
   }


  public void setProgressBarValue(double longitud) throws Exception
   {
    double oldValue = (getProgressBarValue() * 100.0) / 100.0;
    double newvalue = (((1 / longitud) * 100.0) / 100.0) + 0.0001; // +0.0001 to ensure bettewen 1 and 1.99
    progressBarValue.set(oldValue + newvalue);

    mainController.setProgressBarValue(progressBarValue);
    welcomeController.setProgressBarValue(progressBarValue);
    formController.setProgressBarValue(progressBarValue);
    registrationController.setProgressBarValue(progressBarValue);
    forgetController.setProgressBarValue(progressBarValue);
    principalController.setProgressBarValue(progressBarValue);
    //System.out.println("scene " + progressBarValue.getValue());

    if (progressBarValue.getValue() >= 1) progressBarValue.set(0.0);
   }


  public String getLabelText() throws Exception
   {
    return labelText.getValue();
   }


  public void setLabelText(String text) throws Exception
   {
    labelText.setValue(text);
    mainController.setLabelText(text);
   }


  public void setVisibleFlagMenu(String[] subtitle, String subtitleAudio) throws Exception
   {
    this.subtitle = subtitle;
    mainController.setVisibleFlagMenu(this.subtitle, subtitleAudio);
   }


  public void setInvisibleFlagMenu(String[] subtitle, String subtitleAudio) throws Exception
   {
    this.subtitle = subtitle;
    mainController.setInvisibleFlagMenu(this.subtitle, subtitleAudio);
   }


  public void setButtonSubtitle(String flag) throws Exception
   {
    principalController.changeListViewByFlag(flag);
   }


  public static Stage getMainStage() throws Exception
   {
    return mainStage;
   }


  public static MainScene getMainScene() throws Exception
   {
    return mainScene;
   }


  public static void setMainScene(MainScene ms) throws Exception
   {
    mainScene = ms;
   }


  public static void setDataBaseController(DataBaseController db) throws Exception
   {
    dataBaseController = db;
   }
//</editor-fold> 


//<editor-fold defaultstate="collapsed" desc="Menu Buttons">
  public void buttonOpenMenu() throws Exception
   {
    // if doesn't be user, return
    if (welcomeScreen) {
      return;
    }
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))
      return;
    fadeOldOut();
    helperFadePlayOut("withFileChooser");

    if (principalController.handleOpenMenu()) {

      mainView.getChildren().removeAll(loginView, principalView, dataBaseView);
      loginView.getChildren().removeAll(welcomeView, formView, registrationView, forgetView);

      loginView.getChildren().add(welcomeView);
      mainView.setCenter(principalView);

      loginView.setOpacity(1.0);
      welcomeScreen = false;
      fadeNewIn();
      fadeOldIn();
      helperFadePlayIn("withFileChooser");


      //Update the dashborad of the database
      mainScene.handleActualizarUsuario();

    } else {
      fadeOldIn();
      helperFadePlayIn("withFileChooser");
    }
   }


  public void buttonCloseMenu() throws Exception
   {
    // if doesn't be user, return
    if (welcomeScreen) return;
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            !principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))
      return;

    fadeOldOut();
    helperFadePlayOut("withFileChooser");

    if (!message(
            Alert.AlertType.CONFIRMATION, "Salir",
            "¿Quieres cerrar el archivo?", "", null)) {
      fadeOldIn();
      helperFadePlayIn("withFileChooser");
      return;
    } else {
      fadeOldIn();
      fadeNewIn();
      helperFadePlayIn("withFileChooser");
    }
    principalController.handleCloseMenu("handleCloseMenu");
   }


  /* ------------------------there are two methods of close*/
  public void buttonExitMenu() throws Exception
   {

    fadeOldOut();
    helperFadePlayOut("withFileChooser");

    if (!message(Alert.AlertType.CONFIRMATION, "Salir",
            "¿Quieres salir de la aplicación?", "", null)) {

      fadeOldIn();
      helperFadePlayIn("withFileChooser");
      return;
    }

    Platform.exit();
    System.exit(0);
   }


  public void buttonControlesMenu() throws Exception
   {
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            !principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))
      return;

    fadeOldOut();
    helperFadePlayOut("withFileChooser");
    message(Alert.AlertType.INFORMATION, "LanguageApp", "Ayuda",
            "Controles: \n\n" +
            "Cursores:  para desplazarte por los diferentes " +
            "elementos.\n\n" +
            "Barra espaciadora / Enter:  para activar los " +
            "elementos.\n\n" +
            "Control + Derecha / Izquierda: para mover los slider de volumen" +
            ", Balance, velocidad y control de la película.\n\n" +
            "Control o Shift: para seleccionar mas de una palabra al " +
            "reproducirla.\n", null);

    fadeOldIn();
    helperFadePlayIn("withFileChooser");
   }


  public void buttonAboutMenu() throws Exception
   {
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            !principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))    
      return;
    
    fadeOldOut();
    helperFadePlayOut("withFileChooser");

    message(Alert.AlertType.INFORMATION, "LanguageApp", "Acerca de esta aplicación:",
            "Autor: Roberto Garrido Trillo", null);
    fadeNewIn();
    fadeOldIn();
    helperFadePlayIn("withFileChooser");
   }


  public void buttonLoginMenu() throws Exception
   {
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            !principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))
      return;

    centerNode = mainController.checkCenter();
    if (centerNode.getId().equals("loginViewHbox") &&
            loginView.getChildren().get(1).getId().equals("anchorRightLogin")) return;

    setFadeLogin("login");
   }


  public void buttonUnloginMenu() throws Exception
   {
    // if doesn't be user, return
    if (welcomeScreen) return;
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            !principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))
      return;

    fadeOldOut();
    helperFadePlayOut("withFileChooser");

    boolean salida = message(Alert.AlertType.CONFIRMATION, "Cerrar sesión",
            "¿Quieres cerrar la sesión?", "", null);
    if (!salida) {
      fadeOldIn();
      fadeNewIn();
      helperFadePlayIn("withFileChooser");
      return;
    }

    principalController.handleCloseMenu("buttonUnloginMenu");

    // Create the Scene and put it in the center or the borderLayout
    mainView.getChildren().removeAll(principalView, dataBaseView, loginView);
    loginView.getChildren().removeAll(welcomeView, formView, registrationView, forgetView);
    loginView.getChildren().add(welcomeView);
    mainView.setCenter(loginView);

    // Deleting the active in the database
    handleBorrarMarcar(false, getUsuario_id());

    // Deleting the global variables
    setUsuario_id(0);
    usuario_nombre = null;
    usuario_activo = 0;
    welcomeScreen = true;

    welcomeController.handlePutName(usuario_nombre); //null

    fadeNewIn();
    fadeOldIn();
    helperFadePlayIn("withFileChooser");
   }


  public void buttonRegistro() throws Exception
   {
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            !principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))
      return;

    centerNode = mainController.checkCenter();
    if (centerNode.getId().equals("loginViewHbox") &&
            loginView.getChildren().get(1).getId().equals(
                    "registrationViewanchorRight")) return;

    setFadeLogin("registro");
   }


  public void buttonDashBoardMenu() throws Exception
   {

    // if doesn't be user, return
    if (getUsuario_id() == 0 || welcomeScreen ||
            mainController.checkCenter().getId().equals("principalViewAnchorPane"))
      return;
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            !principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))   
      return;
    
    setFadeLogin("dashboard");
   }


  public void buttonDatabaseMenu() throws Exception
   {
    // if doesn't be user, return
    if (getUsuario_id() == 0 || welcomeScreen ||
            mainController.checkCenter().getId().equals("dataBaseViewAnchorPane")) {
      return;
    }
    // if the app is loading a film return
    /*/*if (principalController.handleOpenMenu2Thread == null ||
            !principalController.handleOpenMenu2Thread.getState().equals(Thread.State.WAITING))*/
    if (principalController.handleOpenMenu2Thread == null ||
            principalController.handleOpenMenu2Thread.getState().equals(Thread.State.RUNNABLE))    
      return;

    //Update the dashborad of the database
    mainScene.handleActualizarUsuario();

    setFadeLogin("database");
   }
//</editor-fold>


//<editor-fold defaultstate="collapsed" desc="Total Fade In / Out">
  //<editor-fold defaultstate="collapsed" desc="Setting fade without filechooser">
  public void setFadeLogin(String destiny) throws Exception
   {

    mainChangeListener = new ChangeListener<Double>()
     {
      @Override
      public void changed(ObservableValue<? extends Double> observable,
              Double oldValue, Double newValue)
       {
        try {
          //System.out.println("newValue " + newValue);
          if (newValue <= 0) {

            centerNode = mainView.getCenter();
            centerNode.opacityProperty().removeListener(mainChangeListener);

            mainView.getChildren().removeAll(loginView, principalView, dataBaseView);
            loginView.getChildren().removeAll(welcomeView, formView,
                    registrationView, forgetView);


            // Temporal nodes
            Node loginNode = null;
            Node mainNode = null;

            switch (destiny) {

              case "login":
                loginNode = formView;
                mainNode = loginView;
                formController.setText();
                welcomeScreen = true;
                break;

              case "registro":
                loginNode = registrationView;
                mainNode = loginView;
                registrationController.setText();
                welcomeScreen = true;
                break;

              case "forget":
                loginNode = forgetView;
                mainNode = loginView;
                forgetController.setText();
                welcomeScreen = true;
                break;

              case "dashboard":
                loginNode = welcomeView;
                mainNode = principalView;
                welcomeScreen = false;
                helperFadePlayIn("withoutFileChooser");
                break;

              case "database":
                loginNode = welcomeView;
                mainNode = dataBaseView;
                welcomeScreen = false;
                helperFadePlayOut("withoutFileChooser");
                break;

              default:
                loginNode = welcomeView;
                mainNode = loginView;
                principalView.setOpacity(1.0);
                welcomeScreen = true;
                break;
            }

            loginView.getChildren().add(loginNode);
            mainView.setCenter(mainNode);

            fadeLoginIn = handleSetFadeIn();
            fadeLoginOut.stop();
            fadeLoginIn.play();

          }

        } catch (Exception e) {
          showException(e);
        }
       }


     };

    //centerNode = mainController.checkCenter();
    fadeLoginOut = handleSetFadeOut();
    fadeLoginOut.play();
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Fade in / out of the scene with filechooser">
  public void fadeOldIn() throws Exception
   {
    mainController.mainFadeOldIn();
   }


  public void fadeOldOut() throws Exception
   {
    mainController.mainFadeOldOut();
   }


  public void fadeNewIn() throws Exception
   {
    mainController.mainFadeNewIn();
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Fade in / out of the scene without filechooser">
  private FadeTransition handleSetFadeIn() throws Exception
   {
    centerNode = mainController.checkCenter();

    fIn = new FadeTransition(Duration.millis(250), centerNode);
    //fIn.setFromValue(0.0);
    fIn.setToValue(1.0);

    return fIn;
   }


  private FadeTransition handleSetFadeOut() throws Exception
   {
    centerNode = mainController.checkCenter();
    centerNode.opacityProperty().addListener(mainChangeListener);

    fOut = new FadeTransition(Duration.millis(250), centerNode);
    fOut.setFromValue(1.0);
    fOut.setToValue(0.0);

    return fOut;
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="helper Fade Play In Out">
  private void helperFadePlayIn(String key) throws Exception
   {

    String result = principalController.getMediaStatus();
    if (!key.equals(look) ||
            (result.equals("pause") && look.equals(""))) return;

    look = "";
    switch (result) {
      case "pause":
        principalController.handlePlayButton();
        break;
      case "pauseOriginal":
        principalController.handlePlayButtonItemOriginal();
        break;
      default:
        break;
    }
   }


  private void helperFadePlayOut(String key) throws Exception
   {
    String result = principalController.getMediaStatus();

    if (!key.equals(look) && !look.equals("")) return;

    if (result.equals("pause")) {
      look = "";
      return;
    }

    look = key;

    switch (result) {
      case "playing":
        principalController.handlePlayButton();
        break;
      case "playingOriginal":
        principalController.handlePlayButtonItemOriginal();
        break;
      default:
        break;
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Fade out of the label and progressbar">
  public void fadeLabel() throws Exception
   {
    mainController.fadeLabel();
   }


  public void fadeProgressBar() throws Exception
   {
    mainController.fadeProgressBar();
   }

  //</editor-fold>
//</editor-fold>

  public static void main(String[] args)
   {
    launch(args);
   }


 }
