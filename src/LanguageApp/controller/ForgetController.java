package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class ForgetController {

//<editor-fold defaultstate="collapsed" desc="Field Class">

   @FXML public AnchorPane anchorRight;

   @FXML private JFXTextField usuarioTextField;
   @FXML private Label errorUserLabel;
   @FXML private JFXComboBox preguntaComboBox;
   @FXML private JFXTextField respuestaTextField;
   @FXML private Label errorRespuestaLabel;
   @FXML private JFXButton enviarButton;
   @FXML private Label oldUserLabel;
   @FXML private JFXTextField errorPasswordLabel;
   @FXML private Pane errorPasswordPane;

   @FXML private HBox HBoxUsuario;
   @FXML private HBox HBoxErrorUser;
   @FXML private HBox HBoxPregunta;
   @FXML private HBox HBoxRespuesta;
   @FXML private HBox HBoxErrorRespuesta;
   @FXML private HBox HBoxEnviar;
   @FXML private HBox HBoxAntiguoUsuario;

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
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Reference to MainScene">
   /**
    *
    * @param aThis
    */
   public void setMainScene (MainScene aThis)
   {
      mainScene = aThis;
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Initialize">
   /**
    * When the method is initialize
    */
   public void initialize ()
   {
      // References to mainStage
      mainStage = MainScene.getMainStage();

      node = new Node[]{
         usuarioTextField,
         preguntaComboBox,
         respuestaTextField,
         enviarButton,
         oldUserLabel
      };

      // Setting the current node
      currentNode = usuarioTextField;
      oldNode = usuarioTextField;
      usuarioTextField.requestFocus();

      // Setting the jfxtextfield name
      setJFXTextField();

      // Settiong the intial border
      setBorder(usuarioTextField);

      // Setting the ConboBox options
      preguntaComboBox.getItems().removeAll(preguntaComboBox.getItems());
      preguntaComboBox.getItems().addAll(
              "¿Cuál es tu comida favorita?",
              "¿Cuál es tu color favorita?",
              "¿Cuál es tu ciudad favorita",
              "¿Cuál es tu ropa favorita?",
              "¿Cuál es tu bebida favorita?");
      // preguntaComboBox.getSelectionModel().select("Option B");

      // HBoxError disabled
      errorUserLabel.setManaged(false);
      errorRespuestaLabel.setManaged(false);
      errorPasswordLabel.setManaged(false);
      }
//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setting Field">
   /**
    *
    */
   private void setJFXTextField ()
   {
      eventButton(usuarioTextField, 4, 1);
      eventButton(preguntaComboBox, 0, 2);
      eventButton(respuestaTextField, 1, 3);
      eventButton(enviarButton, 2, 4);
      eventButton(HBoxAntiguoUsuario, 3, 0);

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
   private void eventButton (Node n, int up, int down)
   {
      // setting onFocus (USe of Tab)
      n.focusedProperty().addListener((o, oldVal, newVal) ->
      {
         if (newVal) {
            errorUserLabel.setManaged(false);
            errorRespuestaLabel.setManaged(false);
            errorPasswordLabel.setManaged(false);     
            setBorder(n);
         }
      });

      // setting setOnKeyPressed
      n.setOnKeyPressed(
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent ke)
         {
            errorUserLabel.setManaged(false);
            errorRespuestaLabel.setManaged(false);
            errorPasswordLabel.setManaged(false);
            int i = -1;

            if (ke.getCode().equals(KeyCode.UP)) {
               i = up;
            }
            if (ke.getCode().equals(KeyCode.DOWN)) {
               i = down;
            }
            if (ke.getCode().equals(KeyCode.ENTER)) {
               switch (n.getId()) {
                  case "usuarioTextField":
                     handleValidationUser();
                     break;
                  case "preguntaComboBox":
                     handlePregunta();
                     break;
                  case "respuestaTextField":
                     handleValidationRespuesta();
                     break;
                  case "enviarButton":
                     handleEnviar();
                     break;
                  case "HBoxAntiguoUsuario":
                     handleAntiguoUsuario();
                     break;
                  default:
                     break;
               }
            }
            if (ke.getCode().equals(KeyCode.SPACE)) {
               switch (n.getId()) {
                  case "preguntaComboBox":
                     handlePregunta();
                     break;
                  case "enviarButton":
                     handleEnviar();
                     break;
                  case "HBoxAntiguoUsuario":
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
         errorUserLabel.setManaged(false);
         errorRespuestaLabel.setManaged(false);
         errorPasswordLabel.setManaged(false);

         n.requestFocus();
         setBorder(n);
         oldNode = n;
         MouseEvent.consume();

         switch (n.getId()) {
            case "enviarButton":
               handleEnviar();
               break;
            case "HBoxAntiguoUsuario":
               handleAntiguoUsuario();
               break;
            default:
               break;
         }
      }
      );
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="setBorder">
   /**
    * Setting the border (cursor) of the node
    *
    * @param n the node to put the border
    */
   private void setBorder (Node n)
   {
      if (n.equals(usuarioTextField)) {
         eraserBorder();
         HBoxUsuario.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }
      
      if (n.equals(preguntaComboBox)) {
         eraserBorder();
         HBoxPregunta.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(respuestaTextField)) {
         eraserBorder();
         HBoxRespuesta.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(enviarButton)) {
         eraserBorder();
         HBoxEnviar.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(oldUserLabel)) {
         eraserBorder();
         HBoxAntiguoUsuario.getStyleClass().add("borderLoginVisible");
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
   private void eraserBorder ()
   {
      currentNode.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxUsuario.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxPregunta.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxRespuesta.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxEnviar.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxAntiguoUsuario.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
   }
//</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handlers">
   /**
    *
    */
   private void handleValidationUser ()
   {
      usuarioString = usuarioTextField.getText().trim();
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
   private void showErrorUser (String text)
   {
      errorUserLabel.setManaged(true);
      errorUserLabel.setText(text);
      registroUser = false;
   }

   /**
    *
    */
   private void handlePregunta ()
   {
      preguntaComboBox.show();
   }

   /**
    *
    */
   private void handleValidationRespuesta ()
   {
      Object preguntaObject = preguntaComboBox.getValue();
      respuestaString = respuestaTextField.getText().trim();
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
   private void showErrorRespuesta (String text)
   {
      errorRespuestaLabel.setManaged(true);
      errorRespuestaLabel.setText(text);
      registroPregunta = false;
      registroRespuesta = false;
   }

   /**
    *
    */
   private void handleEnviar ()
   {
      handleValidationUser();
      handleValidationRespuesta();

      //boolean activoBoolean = activoCheckBox.isSelected();
      if (registroUser == true && registroPregunta == true && registroRespuesta == true) {
         String result =
                 mainScene.handleRecordar(usuarioString,preguntaString,respuestaString);
         if (result != null) {
            showErrorPassword("Password: " + result);
         } else {
            showErrorPassword("El usuario no existe");
         }
      }
   }

      /**
    *
    * @param text
    */
   private void showErrorPassword (String text)
   {
      errorPasswordLabel.setManaged(true);
      errorPasswordPane.setManaged(true);
      errorPasswordLabel.setText(text);
      registroPregunta = false;
      registroRespuesta = false;
   }
   /**
    *
    */
   private void handleAntiguoUsuario ()
   {
      mainScene.handleAntiguoUsuarioForget();
   }
//</editor-fold>
}
