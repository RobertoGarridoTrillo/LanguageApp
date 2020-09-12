package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.Message;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
public class FormController implements Initializable {

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

   // The focused and old node
   Node currentNode, oldNode;

   // Reference to the main Stage from the main Scene
   private Stage mainStage;
   // Reference to the main Scene
   private MainScene mainScene;

   // The Login or not Login
   boolean login, loginUser, loginPassword;

   // For the bounle of idioms
   ResourceBundle resources;

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
   @Override
   public void initialize (URL location, ResourceBundle resources)
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
            newUserButtonLogin};

         // Setting the current node
         currentNode = usuarioTextFieldLogin;
         oldNode = usuarioTextFieldLogin;
         usuarioTextFieldLogin.requestFocus();

         // Setting the jfxtextfield name
         setJFXTextField();

         // Settiong the intial border
         setBorder(usuarioTextFieldLogin);

         // HBoxError disabled
         handleEraseError();
      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "FormController / initialize()", e.toString(), e);
      }
   }


//</editor-fold> 
   
//<editor-fold defaultstate="collapsed" desc="Setting Field">
   /**
    *
    */
   private void setJFXTextField ()
   {
      eventButton(usuarioTextFieldLogin, 5, 1);
      eventButton(passwordTextFieldLogin, 0, 2);
      eventButton(loginButtonLogin, 1, 3);
      eventButton(activoCheckBoxLogin, 2, 4);
      eventButton(recuperarButtonLogin, 3, 5);
      eventButton(newUserButtonLogin, 4, 0);
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

         // setting onClick
         n.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
                     case "usuarioTextFieldLogin":
                        handleValidationUser();
                        break;
                     case "passwordTextFieldLogin":
                        handleValidationPassword();
                        break;
                     case "loginButtonLogin":
                        handlelogin();
                        break;
                     case "activoCheckBoxLogin":
                        activoCheckBoxLogin.setSelected(!activoCheckBoxLogin.isSelected());
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
               if (ke.getCode().equals(KeyCode.SPACE)) {
                  switch (n.getId()) {
                     case "loginButtonLogin":
                        handlelogin();
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
                  //oldNode = n;
                  ke.consume();

               }
            }

         });

         // setting onClick
         n.setOnMouseClicked((MouseEvent) -> {
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

//<editor-fold defaultstate="collapsed" desc="EraseError">

   /**
    *
    */
   private void handleEraseError ()
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
   private void setBorder (Node n)
   {
      try {
         if (n.equals(usuarioTextFieldLogin)) {
            eraserBorder();
            HBoxUsuarioLogin.getStyleClass().add("borderLoginVisible");
            oldNode = currentNode;
            currentNode = n;
            return;
         }

         if (n.equals(passwordTextFieldLogin)) {
            eraserBorder();
            HboxPasswordLogin.getStyleClass().add("borderLoginVisible");
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
         currentNode = n;
      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "FormController / eventButton()", e.toString(), e);
      }

   }

   /**
    * Helper to setting Color Border
    */
   private void eraserBorder ()
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
   private void handleForget ()
   {
      mainScene.handleForgetUsuario();
   }

   /**
    *
    */
   private String handleValidationUser ()
   {
      String usuarioString = usuarioTextFieldLogin.getText().trim();
      loginUser = true;

      if (usuarioString.isEmpty() || usuarioString.length() == 0) {
         showErrorUser("No puede estar vacío");
         return null;
      }
      if (usuarioString.length() > 100) {
         showErrorUser("No puede tener mas de 100 letras");
         return null;
      }
      return usuarioString;
   }

   /**
    *
    * @param text
    */
   private void showErrorUser (String text)
   {
      errorUserLabel.setManaged(true);
      errorUserLabel.setText(resources.getString(text));
      loginUser = false;
   }

   /**
    *
    */
   private String handleValidationPassword ()
   {
      String passwordString = passwordTextFieldLogin.getText().trim();
      loginPassword = true;

      if (passwordString.isEmpty() || passwordString.length() == 0) {
         showErrorPassword("No puede estar vacío");
         return null;
      }
      if (passwordString.length() > 20) {
         showErrorPassword("No puede tener mas de 20 letras");
         return null;
      }
      return passwordString;
   }

   /**
    *
    * @param text
    */
   private void showErrorPassword (String text)
   {
      errorPasswordLabel.setManaged(true);
      errorPasswordLabel.setText(resources.getString(text));
      loginPassword = false;
   }

   /**
    *
    */
   private void handlelogin ()
   {

      String usuarioString = handleValidationUser();
      String passwordString = handleValidationPassword();
      boolean activoBoolean = activoCheckBoxLogin.isSelected();

      // If user and password are valid
      if (loginUser == true && loginPassword == true) {

         // Check if that user exits (One or more  exits, 0 doesn't exit)
         int usuario_id = (Integer) mainScene.handleCheckUser(usuarioString, passwordString).getKey();

         // Put the number of user, just in case there was not active user
         // Check if the new user and (assumig there was) the active user of the data base were the
         if (usuario_id > 0) {

            int usuario_last = (Integer) mainScene.handleCheckNombre().getKey();
            mainScene.setUsuario_id(usuario_id);

            if (usuario_id == usuario_last) {

               mainScene.handleEntrar(activoBoolean, true);
            }
            mainScene.handleEntrar(activoBoolean, false);
         } else {
            showErrorUser("El usuario no existe");
         }
      }
   }

   /**
    *
    */
   private void handleNuevoUsuario ()
   {
      mainScene.buttorRegistro();

   }
//</editor-fold>

}
