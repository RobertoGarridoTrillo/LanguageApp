package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.Message;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class FormController implements Initializable
 {

//<editor-fold defaultstate="collapsed" desc="Field Class">

  @FXML public AnchorPane anchorRight;
  @FXML private HBox HBoxUsuarioLogin;
  @FXML private JFXTextField usuarioTextFieldLogin;

  @FXML private HBox HBoxErrorUser;
  @FXML private Label errorUserLabel;

  @FXML private HBox HboxPasswordLogin;
  @FXML private JFXPasswordField passwordTextFieldLogin;

  @FXML private HBox HBoxErrorPassword;
  @FXML private Label errorPasswordLabel;

  @FXML private HBox HBoxLoginLogin;
  @FXML private JFXButton loginButtonLogin;

  @FXML private JFXCheckBox activoCheckBoxLogin;
  @FXML private HBox HBoxActivoLogin;

  @FXML private HBox HBoxRecuperarLogin;
  @FXML private JFXButton recuperarButtonLogin;

  @FXML private HBox HBoxNuevoUsuarioLogin;
  @FXML private JFXButton newUserButtonLogin;

  // pop-up messages
  Message message;

  // The nodes of the view
  private Node[] node;
  private HashMap<Integer, Node> errorLabelMap;

  // The focused and old node
  Node currentNode, oldNode;

  // Reference to the main Stage from the main Scene
  private Stage mainStage;
  // Reference to the main Scene
  private MainScene mainScene;

  // The Login or not Login
  Node[] fieldsChecked;
  String[] fieldString;

  // The Login or not Login
  private Boolean[] registro;
  private int fieldsNumber;

  // For the bounle of idioms
  ResourceBundle resources;

  // The value of the progressBar in mainScene
  DoubleProperty progressBarValue = new SimpleDoubleProperty(0.0);

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Reference to MainScene">
  /**
   *
   * @param aThis
   */
  public void setMainScene(MainScene aThis)
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

      this.resources = resources;

      // References to mainStage
      mainStage = MainScene.getMainStage();

      node = new Node[]{
        usuarioTextFieldLogin,
        passwordTextFieldLogin,
        loginButtonLogin,
        activoCheckBoxLogin,
        recuperarButtonLogin,
        newUserButtonLogin
      };

      // Create the locale for the pop up messages
      /*/*HandleLocale01.handleLocale01();*/
      message = new Message(resources);

      // Setting the current node
      currentNode = usuarioTextFieldLogin;
      oldNode = usuarioTextFieldLogin;
      usuarioTextFieldLogin.requestFocus();

      // Setting the jfxtextfield name
      setJFXTextField();
      setText();

      // Settiong the intial border
      setBorder(usuarioTextFieldLogin);

      // HBoxError disabled
      handleEraseError();

      // Validation fieldsNumber
      fieldsChecked = new Node[]{
        usuarioTextFieldLogin, passwordTextFieldLogin
      };
      fieldsNumber = fieldsChecked.length; // the numbers of field to check
      fieldString = new String[fieldsNumber];
      Arrays.fill(fieldString, "");

      // The Login or not Login
      registro = new Boolean[fieldsNumber];
      Arrays.fill(registro, false);

      progressBarValue.addListener((observable, oldValue, newValue) -> {
        loginButtonLogin.setDisable(progressBarValue.lessThan(1).get());
      });
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "FormController / initialize()", e.toString(), e);
    }
   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setting Field">

  /**
   *
   */
  private void setJFXTextField()
   {
    eventButton(usuarioTextFieldLogin, 5, 1);
    eventButton(passwordTextFieldLogin, 0, 2);
    eventButton(loginButtonLogin, 1, 3);
    eventButton(activoCheckBoxLogin, 2, 4);
    eventButton(recuperarButtonLogin, 3, 5);
    eventButton(newUserButtonLogin, 4, 0);
   }


  /**
   *
   */
  public void setText()
   {
    usuarioTextFieldLogin.setText("");
    passwordTextFieldLogin.setText("");
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helpers Fields">

  //<editor-fold defaultstate="collapsed" desc="Button">

  /**
   * Helper to the play button event
   *
   * @param up the above node
   * @param down the belong node
   * @param right the right node
   * @param left the left node
   * @param button The play button
   */
  private void eventButton(Node n, int up, int down)
   {
    try {

      // setting onFocus (USe of Tab)
      n.focusedProperty().addListener((o, oldVal, newVal) ->
      {
        handleEraseError();
        setBorder(n);
      });

      // setting onKey
      n.setOnKeyPressed(new EventHandler<KeyEvent>()
       {
        @Override
        public void handle(KeyEvent ke)
         {
          handleEraseError();

          int i = -1;

          if (ke.getCode().equals(KeyCode.UP) || ke.getCode().equals(KeyCode.DOWN)) ke.consume();

          if (ke.getCode().equals(KeyCode.UP) && !node[up].isDisable()) {
            ke.consume();
            i = up;
          }
          if (ke.getCode().equals(KeyCode.DOWN) && !node[down].isDisable()) {
            ke.consume();
            i = down;
          }

          if (ke.getCode().equals(KeyCode.ENTER)) {
            switch (n.getId()) {
              case "usuarioTextFieldLogin":
                i = handleValidation(usuarioTextFieldLogin);
                break;
              case "passwordTextFieldLogin":
                i = handleValidation(passwordTextFieldLogin);
                break;
              default:
                break;
            }
          }

          if (ke.getCode().equals(KeyCode.SPACE) || ke.getCode().equals(KeyCode.ENTER)) {
            switch (n.getId()) {
              case "loginButtonLogin":
                handlelogin();
                break;
              case "activoCheckBoxLogin":
                activoCheckBoxLogin.setSelected(!activoCheckBoxLogin.isSelected());
                ke.consume();
                break;
              case "recuperarButtonLogin":
                handleForget();
                break;
              case "newUserButtonLogin":
                handleNuevoUsuario();
                break;
              default:
                break;
            }
          }

          if (i != -1) {
            node[i].requestFocus();
            setBorder(node[i]);
            ke.consume();
            //oldNode = n;

          }
         }

       });

      // setting onClick
      n.setOnMouseClicked((MouseEvent) -> {
        if (n.isDisable()) return;

        // HBoxError disabled
        handleEraseError();

        n.requestFocus();
        setBorder(n);
        oldNode = n;
        MouseEvent.consume();

        switch (n.getId()) {
          case "loginButtonLogin":
            handlelogin();
            break;
          case "activoCheckBoxLogin":
            activoCheckBoxLogin.setSelected(activoCheckBoxLogin.isSelected());
            break;
          case "recuperarButtonLogin":
            handleForget();
            break;
          case "newUserButtonLogin":
            handleNuevoUsuario();
            break;
          default:
            break;
        }
      });
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "FormController / eventButton()", e.toString(), e);
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Erase Error">

  /**
   *
   */
  private void handleEraseError()
   {
    errorUserLabel.setManaged(false);
    errorUserLabel.setText(null);
    errorPasswordLabel.setManaged(false);
    errorPasswordLabel.setText(null);
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="setBorder">

  /**
   * Setting the border (cursor) of the node
   *
   * @param n the node to put the border
   */
  private void setBorder(Node n)
   {
    HashMap<Node, Node> m = new HashMap<>();
    m.put(usuarioTextFieldLogin, HBoxUsuarioLogin);
    m.put(passwordTextFieldLogin, HboxPasswordLogin);
    m.put(loginButtonLogin, HBoxLoginLogin);
    m.put(activoCheckBoxLogin, HBoxActivoLogin);
    m.put(recuperarButtonLogin, HBoxRecuperarLogin);
    m.put(newUserButtonLogin, HBoxNuevoUsuarioLogin);

    try {
      eraserBorder();
      m.get(n).getStyleClass().add("borderLoginVisible");
      /*/*HboxPasswordLogin.getStyleClass().add("borderLoginVisible"); */
      oldNode = currentNode;
      currentNode = n;

      /*/*
      if (n.equals(usuarioTextFieldLogin)) {
        eraserBorder();
        HBoxUsuarioLogin.getStyleClass().add("borderLoginVisible");
        oldNode = currentNode;
        currentNode = n;
        return;
      }

    

      if (n.equals(loginButtonLogin)) {
        eraserBorder();
        HBoxLoginLogin.getStyleClass().add("borderLoginVisible");
        oldNode = currentNode;
        currentNode = n;
        return;
      }

      if (n.equals(activoCheckBoxLogin)) {
        eraserBorder();
        HBoxActivoLogin.getStyleClass().add("borderLoginVisible");
        oldNode = currentNode;
        currentNode = n;
        return;
      }

      if (n.equals(recuperarButtonLogin)) {
        eraserBorder();
        HBoxRecuperarLogin.getStyleClass().add("borderLoginVisible");
        oldNode = currentNode;
        currentNode = n;
        return;
      }
      if (n.equals(newUserButtonLogin)) {
        eraserBorder();
        HBoxNuevoUsuarioLogin.getStyleClass().add("borderLoginVisible");
        oldNode = currentNode;
        currentNode = n;
        return;
      }
      eraserBorder();
      n.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n; */
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "FormController / setBorder()", e.toString(), e);
    }

   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="eraserBorder">

  /**
   * Helper to setting Color Border
   */
  private void eraserBorder()
   {
    currentNode.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxUsuarioLogin.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HboxPasswordLogin.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxLoginLogin.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxActivoLogin.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxRecuperarLogin.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxNuevoUsuarioLogin.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
   }

  //</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handles">   

  /**
   *
   */
  private void handleForget()
   {
    mainScene.handleForgetUsuario();
   }


  /**
   *
   * @param n
   * @return
   */
  private int handleValidation(Node n)
   {
    try {
      int indNode = Arrays.asList(fieldsChecked).indexOf(n);

      handleValidation02(n, true);
      if (registro[indNode] == false) return indNode;

      for (int i = 0; i < fieldsNumber; i++) {
        if (i != Arrays.asList(fieldsChecked).indexOf(n)) handleValidation02(fieldsChecked[i], false);

        if (registro[i] == false) return i;
      }

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "FormController / handleValidation()", e.toString(), e);
    }
    return (node[fieldsNumber].isDisable() ? -1 : fieldsNumber);
   }


  /**
   *
   * @param n
   * @param indNode
   * @param show
   */
  private void handleValidation02(Node n, boolean show)
   {
    int indNode = Arrays.asList(fieldsChecked).indexOf(n);
    String instance = "";
    Object preguntaObject = null;

    errorLabelMap = new HashMap<>();
    errorLabelMap.put(0, errorUserLabel);
    errorLabelMap.put(1, errorPasswordLabel);

    if (n instanceof JFXTextField) {
      fieldString[indNode] = ((JFXTextField) n).getText().trim();
      instance = "JFXTextField";
    }
    if (n instanceof JFXPasswordField) {
      fieldString[indNode] = ((JFXPasswordField) n).getText().trim();
      instance = "JFXPasswordField";
    }
    if (n instanceof JFXComboBox) {
      preguntaObject = ((JFXComboBox) n).getValue();
      if (preguntaObject != null) {
        fieldString[indNode] = preguntaObject.toString();
      } else {
        fieldString[indNode] = "";
      }
      instance = "JFXComboBox";
    }
    String text = "";
    registro[indNode] = true;

    if (fieldString[indNode].isEmpty() || fieldString[indNode].length() == 0) {
      text = "No puede estar vacío";
    }
    if (fieldString[indNode].length() > 100 && instance.equals("JFXTextField")) {
      text = "No puede tener mas de 100 letras";
    }
    if (fieldString[indNode].length() > 20 && instance.equals("JFXPasswordField")) {
      text = "No puede tener mas de 20 letras";
    }
    if ((fieldString[indNode].isEmpty() || fieldString[indNode].length() == 0) &&
            instance.equals("JFXComboBox")) {
      text = "Elige una pregunta de seguridad" +
              "";
    }
    if (!text.equals("")) {
      registro[indNode] = false;

      if (show) {
        Label tempLabel = (Label) errorLabelMap.get(indNode);
        tempLabel.setManaged(true);
        tempLabel.setText(resources.getString(text));
      }
    }
   }


  /**
   *
   */
  private void handlelogin()
   {
    handleValidation(usuarioTextFieldLogin);
    handleValidation(passwordTextFieldLogin);

    boolean activoBoolean = activoCheckBoxLogin.isSelected();

    // If user and password are valid
    if (registro[0] == true && registro[1] == true) {

      // Check if that user exits (One or more  exits, 0 doesn't exit)
      Pair pair = mainScene.handleCheckUser(fieldString[0], fieldString[1]);

      int usuario_id = (Integer) pair.getKey();
      String usuario_nombre = (String) pair.getValue();

      // Put the number of user, just in case there was not active user
      // Check if the new user and (assumig there was) the active user of the data base were the
      if (usuario_id > 0) {
        // id of the new user
        mainScene.setUsuario_id(usuario_id);
        mainScene.setUsuario_nombre(usuario_nombre);

        // id of the old user active
        Object old_id = mainScene.handleCheckNombre().getKey();// the user with the active user in the DDBB
        int usuario_last_ddbb = (old_id != null) ? (Integer) old_id : 0;// From object to int (evito el null)
        
        // true if the old user has a materia_activo
        boolean materia_activo = mainScene.handleCheckMateriaActivo(usuario_last_ddbb);
        // if materia_activo exits delete the materia charged
        if (materia_activo &&
                usuario_last_ddbb != usuario_id) mainScene.handleCloseMenu("buttonLoginMenu");
   
        mainScene.handleEntrar(activoBoolean, usuario_last_ddbb == usuario_id);
        

      } else {
        errorUserLabel.setManaged(true);
        errorUserLabel.setText(resources.getString("El usuario no existe"));
      }
    }
   }


  /**
   *
   */
  private void handleNuevoUsuario()
   {
    mainScene.buttonRegistro();

   }


  /**
   *
   * @param progressBarValue
   */
  public void setProgressBarValue(DoubleProperty progressBarValue)
   {
    this.progressBarValue.setValue(progressBarValue.getValue());
   }

//</editor-fold>

 }
