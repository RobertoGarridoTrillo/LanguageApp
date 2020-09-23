package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.HandleLocale01;
import LanguageApp.util.Message;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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

  @FXML public AnchorPane anchorRight;
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

  // The focused and old node
  Node currentNode, oldNode;

  // Reference to the main Stage from the main Scene
  private Stage mainStage;
  // Reference to the main Scene
  private MainScene mainScene;

  // The string fields
  String usuarioString, passwordString, preguntaString, respuestaString;

  // The Login or not Login
  boolean registro, registroUser, registroPassword, registroPregunta, registroRespuesta;

  // For the bounle of idioms
  ResourceBundle resources;

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
      HandleLocale01.handleLocale01();
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

      // Settiong the intial border
      setBorder(usuarioTextFieldRegistro);

      // Setting the ConboBox options
      preguntaComboBoxRegistro.getItems().removeAll(preguntaComboBoxRegistro.getItems());
      preguntaComboBoxRegistro.getItems().addAll(
              resources.getString("¿Cuál es tu comida favorita?"),
              resources.getString("¿Cuál es tu color favorito?"),
              resources.getString("¿Cuál es tu ciudad favorita?"),
              resources.getString("¿Cuál es tu ropa favorita?"),
              resources.getString("¿Cuál es tu bebida favorita?"));
      // preguntaComboBoxRegistro.getSelectionModel().select("Option B");

      // HBoxError disabled
      handleEraseError();
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
        if (newVal) {
          handleEraseError();
          setBorder(n);
        }
      });

      // setting setOnKeyPressed
      n.setOnKeyPressed(new EventHandler<KeyEvent>()
       {
        @Override
        public void handle(KeyEvent ke)
         {
          handleEraseError();
          int i = -1;

          if (ke.getCode().equals(KeyCode.UP)) {
            i = up;
          }
          if (ke.getCode().equals(KeyCode.DOWN)) {
            i = down;
          }
          if (ke.getCode().equals(KeyCode.ENTER)) {
            switch (n.getId()) {
              case "usuarioTextFieldRegistro":
                handleValidationUser();
                break;
              case "passwordTextFieldRegistro":
                handleValidationPassword();
                break;
              case "preguntaComboBoxRegistro":
                handlePregunta();
                break;
              case "respuestaTextFieldRegistro":
                handleValidationRespuesta();
                break;
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
          if (ke.getCode().equals(KeyCode.SPACE)) {
            switch (n.getId()) {
              case "preguntaComboBoxRegistro":
                handlePregunta();
                break;
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
          if (i != -1) {
            node[i].requestFocus();
            setBorder(node[i]);
            //oldNode = n;
            ke.consume();

          }
         }
       }
      );

      // setting onClick
      n.setOnMouseClicked((MouseEvent) -> {
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

//<editor-fold defaultstate="collapsed" desc="setBorder">
  /**
   * Setting the border (cursor) of the node
   *
   * @param n the node to put the border
   */
  private void setBorder(Node n)
   {
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
    currentNode = n;

   }

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
   */
  private void handleValidationUser()
   {
    usuarioString = usuarioTextFieldRegistro.getText().trim();
    registroUser = true;

    if (usuarioString.isEmpty() || usuarioString.length() == 0) {
      showErrorUser("No puede estar vacío");
    }
    if (usuarioString.length() > 100) {
      showErrorUser("No puede tener mas de 100 letras");
    }

   }

  /**
   *
   * @param text
   */
  private void showErrorUser(String text)
   {
    errorUserLabel.setManaged(true);
    errorUserLabel.setText(resources.getString(text));
    registroUser = false;
   }

  /**
   *
   */
  private void handleValidationPassword()
   {
    passwordString = passwordTextFieldRegistro.getText().trim();
    registroPassword = true;

    if (passwordString.isEmpty() || passwordString.length() == 0) {
      showErrorPassword("No puede estar vacío");
    }
    if (passwordString.length() > 20) {
      showErrorPassword("No puede tener mas de 20 letras");
    }
   }

  /**
   *
   * @param text
   */
  private void showErrorPassword(String text)
   {
    errorPasswordLabel.setManaged(true);
    errorPasswordLabel.setText(resources.getString(text));
    registroPassword = false;
   }

  /**
   *
   */
  private void handlePregunta()
   {
    preguntaComboBoxRegistro.show();
   }

  /**
   *
   */
  private void handleValidationRespuesta()
   {
    Object preguntaObject = preguntaComboBoxRegistro.getValue();
    respuestaString = respuestaTextFieldRegistro.getText().trim();
    registroPregunta = true;
    registroRespuesta = true;

    if (preguntaObject == null) {
      showErrorRespuesta("Elige una pregunta de seguridad");
      return;
    } else {
      preguntaString = preguntaObject.toString();
    }

    if (respuestaString.isEmpty() || respuestaString.length() == 0) {
      showErrorRespuesta("No puede estar vacío");
    }
    if (respuestaString.length() > 100) {
      showErrorRespuesta("No puede tener mas de 100 letras");
    }
   }

  /**
   *
   * @param text
   */
  private void showErrorRespuesta(String text)
   {
    errorRespuestaLabel.setManaged(true);
    errorRespuestaLabel.setText(resources.getString(text));
    registroPregunta = false;
    registroRespuesta = false;
   }

  /**
   *
   */
  private void handleRegistro()
   {
    handleValidationUser();
    handleValidationPassword();
    handleValidationRespuesta();

    //boolean activoBoolean = activoCheckBox.isSelected();
    if (registroUser == true && registroPassword == true &&
            registroPregunta == true && registroRespuesta == true) {

      int result = mainScene.handleRegistro(usuarioString, passwordString, preguntaString, respuestaString);

      if (result != 0) {
        showErrorUser("El usuario ya existe");
      } else {
        mainScene.handleEntrar(true, true);
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

//<editor-fold defaultstate="collapsed" desc="EraseError">

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

//</editor-fold>
 }
