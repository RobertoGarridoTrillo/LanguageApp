package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.Message;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
public class ForgetController {

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
   @FXML private JFXTextField errorPasswordLabelForget;

   @FXML private HBox HBoxAntiguoUsuarioForget;
   @FXML private JFXButton oldUserButtonForget;

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
      
      // Setting messages
      message =  new Message();      

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

      // Settiong the intial border
      setBorder(usuarioTextFieldForget);

      // Setting the ConboBox options
      preguntaComboBoxForget.getItems().removeAll(preguntaComboBoxForget.getItems());
      preguntaComboBoxForget.getItems().addAll(
              "¿Cuál es tu comida favorita?",
              "¿Cuál es tu color favorito?",
              "¿Cuál es tu ciudad favorita",
              "¿Cuál es tu ropa favorita?",
              "¿Cuál es tu bebida favorita?");
      // preguntaComboBoxForget.getSelectionModel().select("Option B");

      // HBoxError disabled
      handleEraseError();
   }
//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setting Field">
   /**
    *
    */
   private void setJFXTextField ()
   {
      eventButton(usuarioTextFieldForget, 4, 1);
      eventButton(preguntaComboBoxForget, 0, 2);
      eventButton(respuestaTextFieldForget, 1, 3);
      eventButton(recuperarButtonForget, 2, 4);
      eventButton(oldUserButtonForget, 3, 0);

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
         n.setOnKeyPressed(
                 new EventHandler<KeyEvent>() {
            @Override
            public void handle (KeyEvent ke)
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
                     case "usuarioTextFieldForget":
                        handleValidationUser();
                        break;
                     case "preguntaComboBoxForget":
                        handlePregunta();
                        break;
                     case "respuestaTextFieldForget":
                        handleValidationRespuesta();
                        break;
                     case "recuperarButtonForget":
                        handleEnviar();
                        break;
                     case "oldUserButtonForget":
                        handleAntiguoUsuario();
                        break;
                     default:
                        break;
                  }
               }
               if (ke.getCode().equals(KeyCode.SPACE)) {
                  switch (n.getId()) {
                     case "preguntaComboBoxForget":
                        handlePregunta();
                        break;
                     case "recuperarButtonForget":
                        handleEnviar();
                        break;
                     case "oldUserButtonForget":
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
               case "recuperarButtonForget":
                  handleEnviar();
                  break;
               case "oldUserButtonForget":
                  handleAntiguoUsuario();
                  break;
               default:
                  break;
            }
         }
         );
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "ForgetController / eventButton()", e.toString(), e);         
      }
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
      if (n.equals(usuarioTextFieldForget)) {
         eraserBorder();
         HBoxUsuarioForget.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(preguntaComboBoxForget)) {
         eraserBorder();
         HBoxPreguntaForget.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(respuestaTextFieldForget)) {
         eraserBorder();
         HBoxRespuestaForget.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(recuperarButtonForget)) {
         eraserBorder();
         HBoxRecuperarForget.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(oldUserButtonForget)) {
         eraserBorder();
         HBoxAntiguoUsuarioForget.getStyleClass().add("borderLoginVisible");
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
    */
   private void handleValidationUser ()
   {
      usuarioString = usuarioTextFieldForget.getText().trim();
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
      preguntaComboBoxForget.show();
   }

   /**
    *
    */
   private void handleValidationRespuesta ()
   {
      Object preguntaObject = preguntaComboBoxForget.getValue();
      respuestaString = respuestaTextFieldForget.getText().trim();
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
                 mainScene.handleRecordar(usuarioString, preguntaString, respuestaString);
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
      errorPasswordLabelForget.setManaged(true);
      errorPasswordLabelForget.setText(text);
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

//<editor-fold defaultstate="collapsed" desc="EraseError">

   /**
    *
    */
   private void handleEraseError ()
   {
      errorUserLabel.setManaged(false);
      errorUserLabel.setText(null);
      errorPasswordLabelForget.setManaged(false);
      errorPasswordLabelForget.setText(null);
      errorRespuestaLabel.setManaged(false);
      errorRespuestaLabel.setText(null);
   }

//</editor-fold>

//</editor-fold>
}
