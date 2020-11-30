package LanguageApp.controller;

import LanguageApp.main.MainScene;
import LanguageApp.model.Usuario;
import LanguageApp.util.HandleLocale01;
import LanguageApp.util.Message;
import LanguageApp.util.PreguntasRegistro;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.util.Pair;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;

/**
 * FXML Controller class
 *
 * @author ROBEG
 */
public class FormDataBaseController implements Initializable
 {

//<editor-fold defaultstate="collapsed" desc="Field Class">

  @FXML public AnchorPane anchorRightFormDataBase;

  @FXML private HBox HBoxUsuarioFormDataBase;
  @FXML private JFXTextField usuarioTextFieldFormDataBase;

  @FXML private HBox HBoxErrorUser;
  @FXML private Label errorUserLabel;

  @FXML private HBox HBoxPasswordFormDataBase;
  @FXML private JFXPasswordField passwordTextFieldFormDataBase;

  @FXML private HBox HBoxErrorPassword;
  @FXML private Label errorPasswordLabel;

  @FXML private HBox HBoxActivoFormDataBase;
  @FXML private JFXCheckBox activoCheckBoxFormDataBase;

  @FXML private HBox HBoxPreguntaFormDataBase;
  @FXML private JFXComboBox preguntaComboBoxFormDataBase;

  @FXML private HBox HBoxRespuestaFormDataBase;
  @FXML private JFXPasswordField respuestaTextFieldFormDataBase;

  @FXML private HBox HBoxErrorRespuesta;
  @FXML private Label errorRespuestaLabel;

  @FXML private HBox HBoxCrearFormData;
  @FXML private HBox HBoxCrearFormDataBase;
  @FXML private JFXButton crearButtonFormDataBase;

  @FXML private HBox HBoxActualizarFormDataBase;
  @FXML private JFXButton actualizarButtonFormDataBase;

  @FXML private HBox HBoxEliminarFormDataBase;
  @FXML private JFXButton eliminarButtonFormDataBase;
  @FXML private JFXButton confirmarButtonFormDataBase;

  private Usuario user;

  // pop-up messages
  Message message;

  // The nodes of the view
  private Node[] node;
  private HashMap<Integer, Node> errorLabelMap;

  // The focused and old node
  private Node currentNode, oldNode;

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
        usuarioTextFieldFormDataBase,
        passwordTextFieldFormDataBase,
        activoCheckBoxFormDataBase,
        preguntaComboBoxFormDataBase,
        respuestaTextFieldFormDataBase,
        actualizarButtonFormDataBase,
        eliminarButtonFormDataBase,
        confirmarButtonFormDataBase,
        crearButtonFormDataBase
      };

      // Create the locale for the pop up messages
      /*/*HandleLocale01.handleLocale01();*/
      message = new Message(resources);

      // For the answers of control
      preguntasRegistro = PreguntasRegistro.preguntas();

      // Setting the ConboBox options
      preguntaComboBoxFormDataBase.getItems()
              .removeAll(preguntaComboBoxFormDataBase.getItems());
      preguntaComboBoxFormDataBase.getItems()
              .addAll(
                      resources.getString(preguntasRegistro.get(0)),
                      resources.getString(preguntasRegistro.get(1)),
                      resources.getString(preguntasRegistro.get(2)),
                      resources.getString(preguntasRegistro.get(3)),
                      resources.getString(preguntasRegistro.get(4)));
      // preguntaComboBoxFormDataBase.getSelectionModel().select("Option B");

      // Setting the jfxtextfield name
      setJFXTextField();

      // HBoxError disabled
      handleEraseError();
      // Show eliminar / confirmar button
      handleEraseEliminar(true);

      // Validation fieldsNumber
      fieldsChecked = new Node[]{
        usuarioTextFieldFormDataBase, passwordTextFieldFormDataBase,
        respuestaTextFieldFormDataBase
      };

      fieldsNumber = fieldsChecked.length; // the numbers of field to check
      fieldString = new String[fieldsNumber];
      Arrays.fill(fieldString, "");

      // The Login or not Login
      registro = new Boolean[fieldsNumber];
      Arrays.fill(registro, false);

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
    eventButton(usuarioTextFieldFormDataBase, -1, -1, 1, -1);
    eventButton(passwordTextFieldFormDataBase, 0, -1, 2, -1);
    eventButton(activoCheckBoxFormDataBase, 1, -1, 3, -1);
    eventButton(preguntaComboBoxFormDataBase, 2, -1, 4, -1);
    eventButton(respuestaTextFieldFormDataBase, 3, -1, 5, -1);
    eventButton(actualizarButtonFormDataBase, 4, -1, 6, -1);
    eventButton(eliminarButtonFormDataBase, 5, 7, 8, -1);
    eventButton(confirmarButtonFormDataBase, 5, -1, 8, 6);
    eventButton(crearButtonFormDataBase, 6, -1, -1, -1);
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
  private void eventButton(Node n, int up, int right, int down, int left)
   {
    try {

      // setting onFocus (USe of Tab)
      n.focusedProperty().addListener((o, oldVal, newVal) ->
      {
        handleEraseError();
        if (!n.equals(eliminarButtonFormDataBase) && (!n.equals(confirmarButtonFormDataBase))) {
          handleEraseEliminar(true);
        }
        setBorder(n);
      });

      // setting onKey
      n.setOnKeyPressed(new EventHandler<KeyEvent>()
       {
        @Override
        public void handle(KeyEvent ke)
         {
          handleEraseError();
          if (!n.equals(eliminarButtonFormDataBase) && (!n.equals(confirmarButtonFormDataBase))) {
            handleEraseEliminar(true);
          }

          int i = -1;

          if (ke.getCode().equals(KeyCode.UP) || ke.getCode().equals(KeyCode.DOWN)) ke.consume();

          if (ke.getCode().equals(KeyCode.UP) && up != -1) {
            ke.consume();
            i = up;
          }
          if (ke.getCode().equals(KeyCode.DOWN) && down != -1) {
            ke.consume();
            i = down;
          }
          if (ke.getCode().equals(KeyCode.LEFT)) {
            ke.consume();
            i = left;
          }
          if (ke.getCode().equals(KeyCode.RIGHT)) {
            ke.consume();
            i = right;
          }
          if (ke.getCode().equals(KeyCode.ENTER)) {
            switch (n.getId()) {
              case "usuarioTextFieldFormDataBase":
                i = handleValidation(usuarioTextFieldFormDataBase);
                break;
              case "passwordTextFieldFormDataBase":
                i = handleValidation(passwordTextFieldFormDataBase);
                break;
              case "preguntaComboBoxFormDataBase":
                i = handleValidation(preguntaComboBoxFormDataBase);
                break;
              case "respuestaTextFieldFormDataBase":
                i = handleValidation(respuestaTextFieldFormDataBase);
                break;
              default:
                break;
            }
          }

          if (ke.getCode().equals(KeyCode.SPACE) || ke.getCode().equals(KeyCode.ENTER)) {
            switch (n.getId()) {
              case "activoCheckBoxFormDataBase":
                activoCheckBoxFormDataBase.setSelected(!activoCheckBoxFormDataBase.isSelected());
                ke.consume();
                break;
              case "actualizarButtonFormDataBase":
                handleUpdateCreate("update");
                break;
              case "eliminarButtonFormDataBase":
                handleDelete();
                break;
              case "confirmarButtonFormDataBase":
                handleDelete();
                break;
              case "crearButtonFormDataBase":
                handleUpdateCreate("create");
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
        if (!n.equals(eliminarButtonFormDataBase) && (!n.equals(confirmarButtonFormDataBase))) {
          handleEraseEliminar(true);
        }

        n.requestFocus();
        setBorder(n);
        oldNode = n;
        MouseEvent.consume();

        switch (n.getId()) {
          case "activoCheckBoxFormDataBase":
            activoCheckBoxFormDataBase.setSelected(activoCheckBoxFormDataBase.isSelected());
            break;
          case "actualizarButtonFormDataBase":
            handleUpdateCreate("update");
            break;
          case "eliminarButtonFormDataBase":
            handleDelete();
            break;
          case "confirmarButtonFormDataBase":
            handleDelete();
            break;
          case "crearButtonFormDataBase":
            handleUpdateCreate("create");
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

  //<editor-fold defaultstate="collapsed" desc="Erase Error / button eliminar">

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


  /**
   *
   * @param state boolean <br>
   * true -> button eliminar is visible, button confirmar is invisible<br>
   *
   * false -> button eliminar is invisible, button confirmar is visible
   */
  private void handleEraseEliminar(boolean state)
   {
    eliminarButtonFormDataBase.setManaged(state);
    eliminarButtonFormDataBase.setVisible(state);
    confirmarButtonFormDataBase.setManaged(!state);
    confirmarButtonFormDataBase.setVisible(!state);
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
    m.put(usuarioTextFieldFormDataBase, HBoxUsuarioFormDataBase);
    m.put(passwordTextFieldFormDataBase, HBoxPasswordFormDataBase);
    m.put(activoCheckBoxFormDataBase, HBoxActivoFormDataBase);
    m.put(preguntaComboBoxFormDataBase, HBoxPreguntaFormDataBase);
    m.put(respuestaTextFieldFormDataBase, HBoxRespuestaFormDataBase);
    m.put(actualizarButtonFormDataBase, HBoxActualizarFormDataBase);
    m.put(eliminarButtonFormDataBase, HBoxEliminarFormDataBase);
    m.put(confirmarButtonFormDataBase, HBoxEliminarFormDataBase);
    m.put(crearButtonFormDataBase, HBoxCrearFormDataBase);

    try {
      eraserBorder();
      m.get(n).getStyleClass().add("borderLoginVisible");
      /*/*HboxPasswordFormDataBase.getStyleClass().add("borderLoginVisible"); */
      oldNode = currentNode;
      currentNode = n;
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
    HBoxUsuarioFormDataBase.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxPasswordFormDataBase.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxActivoFormDataBase.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxPreguntaFormDataBase.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxRespuestaFormDataBase.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxActualizarFormDataBase.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxEliminarFormDataBase.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxCrearFormDataBase.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
   }

  //</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handles">   

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
      if (registro[indNode] == false) {
        if (indNode >= 2) indNode = indNode + 2;
        return indNode;
      }

      for (int i = 0; i < fieldsNumber; i++) {
        if (i != Arrays.asList(fieldsChecked).indexOf(n)) handleValidation02(fieldsChecked[i],
                  false);

        if (registro[i] == false) 
          return (i >= 2) ? i + 2 : i;
      }

      }catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "FormController / handleValidation()", e.toString(), e);
    }
      return (node[fieldsNumber].isDisable() ? -1 : 5);
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
   }


  /**
   *
   * @param select
   */
  private void handleUpdateCreate(String select)
   {
    try {

      handleValidation(usuarioTextFieldFormDataBase);
      handleValidation(passwordTextFieldFormDataBase);
      /*/*handleValidation(preguntaComboBoxFormDataBase); */
      handleValidation(respuestaTextFieldFormDataBase);

      //int activoBoolean = (activoCheckBoxFormDataBase.isSelected()) ? 1 : 0;
      boolean activoBoolean = activoCheckBoxFormDataBase.isSelected();

      // change the question into a "original language" question
      int original = preguntaComboBoxFormDataBase.getSelectionModel().getSelectedIndex();
      fieldString[2] = preguntasRegistro.get(original);

      int newUser = (Integer) mainScene.handleCheckUser(fieldString[0], fieldString[1]).getKey();
      // if the fields aren't valid then return
      if (!(registro[0] == true && registro[1] == true && registro[2] == true && registro[3] == true)) {
        return;
      }
      // if newUser != 0 -> Create newUser OR newUser != user.getUsuario_id() -> Update then return
      if ((newUser != 0 && select.equals("create")) ||
              (newUser != user.getUsuario_id() && newUser > 0 && select.equals("update"))) {
        errorUserLabel.setManaged(true);
        errorUserLabel.setText(resources.getString("El usuario ya existe"));
        return;
      }


      switch (select) {
        case "update":
          Usuario u = new Usuario();
          u.setUsuario_id(user.getUsuario_id());
          u.setUsuario_nombre(fieldString[0]);
          u.setPassword(fieldString[1]);
          u.setUsuario_activo(activoBoolean);
          u.setPregunta(fieldString[2]);
          u.setRespuesta(fieldString[3]);

          mainScene.handleUpdate(u);
          break;
        case "create":
          mainScene.handleCreate(fieldString[0], fieldString[1], activoBoolean, fieldString[2], fieldString[3]);
          break;
        default:
          break;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

   }


  /**
   *
   */
  private void handleDelete()
   {
    if (eliminarButtonFormDataBase.isManaged()) {
      handleEraseEliminar(false);

      currentNode = confirmarButtonFormDataBase;
      oldNode = confirmarButtonFormDataBase;
      confirmarButtonFormDataBase.requestFocus();
      setBorder(confirmarButtonFormDataBase);

    } else {
      mainScene.handleDelete(user);
      if (user.getUsuario_id() == mainScene.getUsuario_id())
        mainScene.buttonLoginMenu();
    }
   }


  /**
   *
   * @param user
   * @param index
   * @param root
   * @par
   */
  public void setRowIntoModal(Usuario user, int index, boolean root)
   {
    this.user = user;

    if (root) {
      HBoxCrearFormData.setVisible(true);
      anchorRightFormDataBase.setPrefHeight(640);

    } else {
      HBoxCrearFormData.setVisible(false);
      anchorRightFormDataBase.setPrefHeight(560);
      eventButton(eliminarButtonFormDataBase, 5, 7, -1, -1);
      eventButton(confirmarButtonFormDataBase, 5, -1, -1, 6);
    }
    usuarioTextFieldFormDataBase.setText(user.getUsuario_nombre());
    passwordTextFieldFormDataBase.setText(user.getPassword());
    activoCheckBoxFormDataBase.setSelected(user.getUsuario_activo());
    preguntaComboBoxFormDataBase.getSelectionModel().select(resources.getString(user.getPregunta()));
    respuestaTextFieldFormDataBase.setText(user.getRespuesta());

    // Setting the current node
    currentNode = node[index];
    oldNode = node[index];
    node[index].requestFocus();
    setBorder(node[index]);
   }

//</editor-fold>

 }