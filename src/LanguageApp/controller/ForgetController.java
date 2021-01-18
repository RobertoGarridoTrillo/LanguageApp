package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import static LanguageApp.util.HandleLocale.toLocale;
import static LanguageApp.util.Message.showException;
import LanguageApp.util.PreguntasRegistro;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
public class ForgetController implements Initializable
 {

//<editor-fold defaultstate="collapsed" desc="Field Class">

  @FXML public AnchorPane anchorRight;
  @FXML private HBox HBoxUsuarioForget;
  @FXML private JFXTextField usuarioTextFieldForget;

  @FXML private HBox HBoxErrorUser;
  @FXML private Label errorUserLabel;

  @FXML private HBox HBoxPreguntaForget;
  @FXML private JFXComboBox preguntaComboBoxForget;

  @FXML private HBox HBoxRespuestaForget;
  @FXML private JFXTextField respuestaTextFieldForget;

  @FXML private HBox HBoxErrorRespuesta;
  @FXML private Label errorRespuestaLabel;

  @FXML private HBox HBoxRecuperarForget;
  @FXML private JFXButton recuperarButtonForget;

  @FXML private HBox HBoxErrorPassword;
  @FXML private JFXTextField errorPasswordLabel;

  @FXML private HBox HBoxAntiguoUsuarioForget;
  @FXML private JFXButton oldUserButtonForget;

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
   * @throws java.lang.Exception
   */
  public void setMainScene(MainScene aThis) throws Exception
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

      node = new Node[]{
        usuarioTextFieldForget,
        preguntaComboBoxForget,
        respuestaTextFieldForget,
        recuperarButtonForget,
        oldUserButtonForget
      };

      // Setting the current node
      currentNode = usuarioTextFieldForget;
      oldNode = usuarioTextFieldForget;
      usuarioTextFieldForget.requestFocus();

      // Setting the jfxtextfield name
      setJFXTextField();
      setText();

      // Settiong the intial border
      setBorder(usuarioTextFieldForget);

      // For the answers of control
      preguntasRegistro = PreguntasRegistro.preguntas();

      // Setting the ConboBox options
      preguntaComboBoxForget.getItems().removeAll(preguntaComboBoxForget.getItems());
      preguntaComboBoxForget.getItems().addAll(
              toLocale(preguntasRegistro.get(0)),
              toLocale(preguntasRegistro.get(1)),
              toLocale(preguntasRegistro.get(2)),
              toLocale(preguntasRegistro.get(3)),
              toLocale(preguntasRegistro.get(4)));
      // preguntaComboBoxForget.getSelectionModel().select("Option B");

      // HBoxError disabled
      handleEraseError();

      // Validation fieldsNumber
      fieldsChecked = new Node[]{
        usuarioTextFieldForget,
        preguntaComboBoxForget, respuestaTextFieldForget
      };
      fieldsNumber = fieldsChecked.length; // the numbers of field to check
      fieldString = new String[fieldsNumber];
      Arrays.fill(fieldString, "");

      // The Login or not Login
      fieldsNumber = 3; // the numbers of field to check      
      registro = new Boolean[fieldsNumber];
      Arrays.fill(registro, false);

      progressBarValue.addListener((observable, oldValue, newValue) -> {
        recuperarButtonForget.setDisable(progressBarValue.lessThan(1)
                .get());
      });

    } catch (Exception e) {
      showException(e);
    }
   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setting Field">
  /**
   *
   * @throws Exception
   */
  private void setJFXTextField() throws Exception
   {
    eventButton(usuarioTextFieldForget, 4, 1);
    eventButton(preguntaComboBoxForget, 0, 2);
    eventButton(respuestaTextFieldForget, 1, 3);
    eventButton(recuperarButtonForget, 2, 4);
    eventButton(oldUserButtonForget, 3, 0);
   }


  /**
   *
   * @throws java.lang.Exception
   */
  public void setText() throws Exception
   {
    usuarioTextFieldForget.setText("");
    respuestaTextFieldForget.setText("");
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helpers Fields">

  //<editor-fold defaultstate="collapsed" desc="Button">
  /**
   *
   * @param n
   * @param up
   * @param down
   * @throws Exception
   */
  private void eventButton(Node n, int up, int down) throws Exception
   {

    // setting onFocus (USe of Tab)
    n.focusedProperty().addListener((o, oldVal, newVal) ->
    {
      try {
        handleEraseError();
        setBorder(n);
      } catch (Exception e) {
        showException(e);
      }
    });

    // setting setOnKeyPressed
    n.setOnKeyPressed(new EventHandler<KeyEvent>()
     {
      @Override
      public void handle(KeyEvent ke)
       {
        try {
          handleEraseError();

          int i = -1;

          if (ke.getCode().equals(KeyCode.UP) || ke.getCode().equals(KeyCode.DOWN))
            ke.consume();

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
              case "usuarioTextFieldForget":
                i = handleValidation(usuarioTextFieldForget);
                break;
              case "preguntaComboBoxForget":
                i = handleValidation(preguntaComboBoxForget);
                break;
              case "respuestaTextFieldForget":
                i = handleValidation(respuestaTextFieldForget);
                break;
              default:
                break;
            }
            ke.consume();
          }

          if (ke.getCode().equals(KeyCode.SPACE) ||
                  ke.getCode().equals(KeyCode.ENTER)) {
            switch (n.getId()) {
              case "recuperarButtonForget":
                handleEnviar();
                break;
              case "oldUserButtonForget":
                handleAntiguoUsuario();
                break;
              default:
                break;
            }
            ke.consume();
          }

          if (ke.getCode().equals(KeyCode.SPACE) &&
                  n.getId().equals("preguntaComboBoxForget")) {
            preguntaComboBoxForget.show();
            ke.consume();
          }

          if (i != -1) {
            node[i].requestFocus();
            setBorder(node[i]);
            ke.consume();
            //oldNode = n;
          }

        } catch (Exception e) {
          showException(e);
        }
       }

     }
    );

    // setting onClick
    n.setOnMouseClicked((MouseEvent) -> {
      try {

        if (n.isDisable()) return;
        // HBoxError disabled
        handleEraseError();

        n.requestFocus();
        setBorder(n);
        oldNode = n;
        MouseEvent.consume();

        switch (n.getId()) {
          case "recuperarButtonForget":
            handleEnviar();
            break;
          case "oldUserButtonForget":
            handleAntiguoUsuario();
            break;
          default:
            break;
        }
      } catch (Exception e) {
        showException(e);
      }
    }
    );
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="EraseError">
  /**
   *
   * @throws Exception
   */
  private void handleEraseError() throws Exception
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
   * @throws Exception
   */
  private void setBorder(Node n) throws Exception
   {
    HashMap<Node, Node> m = new HashMap<>();
    m.put(usuarioTextFieldForget, HBoxUsuarioForget);
    m.put(preguntaComboBoxForget, HBoxPreguntaForget);
    m.put(respuestaTextFieldForget, HBoxRespuestaForget);
    m.put(recuperarButtonForget, HBoxRecuperarForget);
    m.put(oldUserButtonForget, HBoxAntiguoUsuarioForget);

    eraserBorder();
    m.get(n).getStyleClass().add("borderLoginVisible");
    oldNode = currentNode;
    currentNode = n;
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="eraserBorder">
  /**
   *
   * @throws Exception
   */
  private void eraserBorder() throws Exception
   {
    currentNode.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxUsuarioForget.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxPreguntaForget.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxRespuestaForget.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxRecuperarForget.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxAntiguoUsuarioForget.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
   }
  //</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handlers">
  /**
   *
   * @param n
   * @return
   * @throws Exception
   */
  private int handleValidation(Node n) throws Exception
   {
    int indNode = Arrays.asList(fieldsChecked).indexOf(n);

    handleValidation02(n, true);
    if (registro[indNode] == false) return indNode;

    for (int i = 0; i < fieldsNumber; i++) {
      if (i != Arrays.asList(
              fieldsChecked).indexOf(n)) handleValidation02(fieldsChecked[i],
                false);

      if (registro[i] == false) return i;
    }

    return (node[fieldsNumber].isDisable() ? -1 : fieldsNumber);
   }


  /**
   *
   * @param n
   * @param show
   * @throws Exception
   */
  private void handleValidation02(Node n, boolean show) throws Exception
   {
    int indNode = Arrays.asList(fieldsChecked).indexOf(n);
    String instance = "";
    Object preguntaObject = null;

    errorLabelMap = new HashMap<>();
    errorLabelMap.put(0, errorUserLabel);
    errorLabelMap.put(1, errorUserLabel);
    errorLabelMap.put(2, errorRespuestaLabel);

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
    if (fieldString[indNode].length() > 100 &&
            instance.equals("JFXTextField")) {
      text = "No puede tener mas de 100 letras";
    }
    if (fieldString[indNode].length() > 20 &&
            instance.equals("JFXPasswordField")) {
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
        tempLabel.setText(toLocale(text));
      }
    }
   }


  /**
   *
   * @throws Exception
   */
  private void handleEnviar() throws Exception
   {
    handleValidation(usuarioTextFieldForget);
    handleValidation(preguntaComboBoxForget);
    handleValidation(respuestaTextFieldForget);

    //boolean activoBoolean = activoCheckBox.isSelected();
    if (registro[0] == true && registro[1] == true && registro[2] == true) {
      String result = null;

      // change the question into a "original language" question  
      int original = preguntaComboBoxForget.getSelectionModel().getSelectedIndex();
      fieldString[1] = preguntasRegistro.get(original);

      result = mainScene.handleRecordar(
              fieldString[0], fieldString[1], fieldString[2]);

      if (result != null) {
        errorPasswordLabel.setManaged(true);
        errorPasswordLabel.setText(toLocale("Contraseña") + ": " + result);
        //break;
      } else {
        errorPasswordLabel.setManaged(true);
        errorPasswordLabel.setText(toLocale("El usuario no existe"));

      }
    }
   }


  /**
   *
   * @throws Exception
   */
  private void handleAntiguoUsuario() throws Exception
   {
    mainScene.buttonRegistro();
   }


  /**
   *
   * @param progressBarValue
   * @throws java.lang.Exception
   */
  public void setProgressBarValue(DoubleProperty progressBarValue) throws Exception
   {
    this.progressBarValue.setValue(progressBarValue.getValue());
   }
//</editor-fold>

 }
