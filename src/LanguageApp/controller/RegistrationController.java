package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.HandleLocale01;
import LanguageApp.util.Message;
import LanguageApp.util.PreguntasRegistro;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class RegistrationController implements Initializable
 {

//<editor-fold defaultstate="collapsed" desc="Field Class">

  @FXML public AnchorPane registrationViewanchorRight;
  @FXML private HBox HBoxUsuarioRegistro;
  @FXML private JFXTextField usuarioTextFieldRegistro;

  @FXML private HBox HBoxErrorUser;
  @FXML private Label errorUserLabel;

  @FXML private HBox HBoxPasswordRegistro;
  @FXML private JFXPasswordField passwordTextFieldRegistro;

  @FXML private HBox HBoxErrorPassword;
  @FXML private Label errorPasswordLabel;

  @FXML private HBox HBoxPreguntaRegistro;
  @FXML private JFXComboBox preguntaComboBoxRegistro;

  @FXML private HBox HBoxRespuestaRegistro;
  @FXML private JFXPasswordField respuestaTextFieldRegistro;

  @FXML private HBox HBoxErrorRespuesta;
  @FXML private Label errorRespuestaLabel;

  @FXML private HBox HBoxRegistrarRegistro;
  @FXML private JFXButton registroButtonRegistro;

  @FXML private HBox HBoxAntiguoUsuarioRegistro;
  @FXML private JFXButton oldUserButtonRegistro;

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

  // For the answers of control
  Map<Integer, String> preguntasRegistro;

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
   *
   * @param location
   * @param resources
   */
  @Override
  public void initialize(URL location, ResourceBundle resources)
   {
    try {

      this.resources = resources;

      // References to mainStage
      mainStage = MainScene.getMainStage();

      // Create the locale for the pop up messages
      /*/*HandleLocale01.handleLocale01();*/
      message = new Message(resources);

      node = new Node[]{
        usuarioTextFieldRegistro,
        passwordTextFieldRegistro,
        preguntaComboBoxRegistro,
        respuestaTextFieldRegistro,
        registroButtonRegistro,
        oldUserButtonRegistro
      };

      // Setting the current node
      currentNode = usuarioTextFieldRegistro;
      oldNode = usuarioTextFieldRegistro;
      usuarioTextFieldRegistro.requestFocus();

      // Setting the jfxtextfield name
      setJFXTextField();
      setText();

      // Settiong the intial border
      setBorder(usuarioTextFieldRegistro);

      // For the answers of control
      preguntasRegistro = PreguntasRegistro.preguntas();

      // Setting the ConboBox options
      preguntaComboBoxRegistro.getItems().removeAll(preguntaComboBoxRegistro.getItems());
      preguntaComboBoxRegistro.getItems().addAll(
              resources.getString(preguntasRegistro.get(0)),
              resources.getString(preguntasRegistro.get(1)),
              resources.getString(preguntasRegistro.get(2)),
              resources.getString(preguntasRegistro.get(3)),
              resources.getString(preguntasRegistro.get(4)));
      // preguntaComboBoxRegistro.getSelectionModel().select("Option B");

      // HBoxError disabled
      handleEraseError();

      // Validation fieldsNumber
      fieldsChecked = new Node[]{
        usuarioTextFieldRegistro, passwordTextFieldRegistro,
        preguntaComboBoxRegistro, respuestaTextFieldRegistro
      };
      fieldsNumber = fieldsChecked.length; // the numbers of field to check
      fieldString = new String[fieldsNumber];
      Arrays.fill(fieldString, "");

      // The Login or not Login
      registro = new Boolean[fieldsNumber];
      Arrays.fill(registro, false);

      progressBarValue.addListener((observable, oldValue, newValue) -> {
        registroButtonRegistro.setDisable(progressBarValue.lessThan(1).get());
      });

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "RegistrationController / initialize()", e.toString(), e);
    }
   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setting Field">

  /**
   *
   */
  private void setJFXTextField()
   {
    eventButton(usuarioTextFieldRegistro, 5, 1);
    eventButton(passwordTextFieldRegistro, 0, 2);
    eventButton(preguntaComboBoxRegistro, 1, 3);
    eventButton(respuestaTextFieldRegistro, 2, 4);
    eventButton(registroButtonRegistro, 3, 5);
    eventButton(oldUserButtonRegistro, 4, 0);
   }


  /**
   *
   */
  public void setText()
   {
    usuarioTextFieldRegistro.setText("");
    passwordTextFieldRegistro.setText("");
    respuestaTextFieldRegistro.setText("");
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

      // setting setOnKeyPressed
      n.setOnKeyPressed(new EventHandler<KeyEvent>()
       {
        @Override
        public void handle(KeyEvent ke)
         {
          handleEraseError();

          int i = -1;
          if (ke.getCode().equals(KeyCode.UP) || ke.getCode().equals(KeyCode.DOWN)) ke.consume();

          if (ke.getCode().equals(KeyCode.UP) && !node[up].isDisable()) {
            i = up;
            ke.consume();
          }

          if (ke.getCode().equals(KeyCode.DOWN) && !node[down].isDisable()) {
            i = down;
            ke.consume();
          }

          if (ke.getCode().equals(KeyCode.ENTER)) {
            switch (n.getId()) {
              case "usuarioTextFieldRegistro":
                i = handleValidation(usuarioTextFieldRegistro);
                break;
              case "passwordTextFieldRegistro":
                i = handleValidation(passwordTextFieldRegistro);
                break;
              case "preguntaComboBoxRegistro":
                i = handleValidation(preguntaComboBoxRegistro);
                break;
              case "respuestaTextFieldRegistro":
                i = handleValidation(respuestaTextFieldRegistro);
                break;
              default:
                break;
            }
            ke.consume();
          }

          if (ke.getCode().equals(KeyCode.SPACE) || ke.getCode().equals(KeyCode.ENTER)) {
            switch (n.getId()) {
              case "registroButtonRegistro":
                handleRegistro();
                break;
              case "oldUserButtonRegistro":
                handleAntiguoUsuario();
                break;
              default:
                break;
            }
            ke.consume();
          }

          if (ke.getCode().equals(KeyCode.SPACE) && n.getId().equals("preguntaComboBoxRegistro")) {
            preguntaComboBoxRegistro.show();
            ke.consume();
          }

          if (i != -1) {
            node[i].requestFocus();
            setBorder(node[i]);
            ke.consume();
            //oldNode = n;

          }
         }

       }
      );

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
          case "registroButtonRegistro":
            handleRegistro();
            break;
          case "oldUserButtonRegistro":
            handleAntiguoUsuario();
            break;
          default:
            break;
        }
      }
      );
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "RegistrationController / eventButton()", e.toString(), e);
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
    errorRespuestaLabel.setManaged(false);
    errorRespuestaLabel.setText(null);
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
    m.put(usuarioTextFieldRegistro, HBoxUsuarioRegistro);
    m.put(passwordTextFieldRegistro, HBoxPasswordRegistro);
    m.put(preguntaComboBoxRegistro, HBoxPreguntaRegistro);
    m.put(respuestaTextFieldRegistro, HBoxRespuestaRegistro);
    m.put(registroButtonRegistro, HBoxRegistrarRegistro);
    m.put(oldUserButtonRegistro, HBoxAntiguoUsuarioRegistro);

    try {
      eraserBorder();
      m.get(n).getStyleClass().add("borderLoginVisible");
      /*/*HboxPasswordLogin.getStyleClass().add("borderLoginVisible"); */
      oldNode = currentNode;
      currentNode = n;
      /*/*    
    
    
    if (n.equals(usuarioTextFieldRegistro)) {
      eraserBorder();
      HBoxUsuarioRegistro.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }

    if (n.equals(passwordTextFieldRegistro)) {
      eraserBorder();
      HBoxPasswordRegistro.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }
    if (n.equals(preguntaComboBoxRegistro)) {
      eraserBorder();
      HBoxPreguntaRegistro.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }

    if (n.equals(respuestaTextFieldRegistro)) {
      eraserBorder();
      HBoxRespuestaRegistro.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }

    if (n.equals(registroButtonRegistro)) {
      eraserBorder();
      HBoxRegistrarRegistro.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }

    if (n.equals(oldUserButtonRegistro)) {
      eraserBorder();
      HBoxAntiguoUsuarioRegistro.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }
    eraserBorder();
    n.getStyleClass().add("borderLoginVisible");
    oldNode = currentNode;
    currentNode = n; */
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "RegistratioController / setBorder()", e.toString(), e);
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
    HBoxUsuarioRegistro.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxPasswordRegistro.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxPreguntaRegistro.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxRespuestaRegistro.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxRegistrarRegistro.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxAntiguoUsuarioRegistro.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
   }

  //</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handlers">

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
        if (i != indNode) handleValidation02(fieldsChecked[i], false);

        if (registro[i] == false) return i;
      }

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "RegistrationController / handleValidation()", e.toString(), e);
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
    try {
      int indNode = Arrays.asList(fieldsChecked).indexOf(n);
      String instance = "";
      Object preguntaObject = null;

      errorLabelMap = new HashMap<>();
      errorLabelMap.put(0, errorUserLabel);
      errorLabelMap.put(1, errorPasswordLabel);
      errorLabelMap.put(2, errorPasswordLabel);
      errorLabelMap.put(3, errorRespuestaLabel);

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
        text = "Elige una pregunta de seguridad";
      }
      if (!text.equals("")) {
        registro[indNode] = false;

        if (show) {
          Label tempLabel = (Label) errorLabelMap.get(indNode);
          tempLabel.setManaged(true);
          tempLabel.setText(resources.getString(text));
        }
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "RegistrationController / handleValidation02()", e.toString(), e);
    }
   }


  /**
   *
   */
  private void handleRegistro()
   {
    handleValidation(usuarioTextFieldRegistro);
    handleValidation(passwordTextFieldRegistro);
    handleValidation(preguntaComboBoxRegistro);
    handleValidation(respuestaTextFieldRegistro);

    // change the question into a "original language" question
    int original = preguntaComboBoxRegistro.getSelectionModel().getSelectedIndex();
    fieldString[2] = preguntasRegistro.get(original);

    /*/*boolean activoBoolean = activoCheckBox.isSelected(); */
    if (registro[0] == true && registro[1] == true && registro[2] == true && registro[3] == true) {

      int userExist = mainScene.handleRegistro(
              fieldString[0], fieldString[1], true, fieldString[2], fieldString[3]);

      if (userExist != 0) {
        errorUserLabel.setManaged(true);
        errorUserLabel.setText(resources.getString("El usuario ya existe"));
      } else {
        // Checking if there's some active user
      /*/*Object id = mainScene.handleCheckNombre().getKey();

      int usuario_last = ( id != null) ? (Integer) id : 0; 
      
      mainScene.handleEntrar(true, mainScene.handleCheckMateriaActivo(usuario_last)); */
      
      mainScene.handleEntrar(true,true);

      }
    }
   }


  /**
   *
   */
  private void handleAntiguoUsuario()
   {
    mainScene.buttonLoginMenu();
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
