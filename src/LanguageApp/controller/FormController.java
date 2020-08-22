package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
public class FormController {

//<editor-fold defaultstate="collapsed" desc="Field Class">

   @FXML public AnchorPane anchorRight;

   @FXML private JFXTextField usuarioTextField;
   @FXML private Label errorUserLabel;
   @FXML private JFXPasswordField passwordTextField;
   @FXML private Label errorPasswordLabel;
   @FXML private JFXButton loginButton;
   @FXML private JFXButton forgetButton;
   @FXML private JFXCheckBox activoCheckBox;
   @FXML private Label newUserLabel;

   @FXML private HBox HBoxUsuario;
   @FXML private HBox HBoxErrorUser;
   @FXML private HBox HBoxPassword;
   @FXML private HBox HBoxErrorPassword;
   @FXML private HBox HBoxLogin;
   @FXML private HBox HBoxRecordar;
   @FXML private HBox HBoxOlvidaste;
   @FXML private HBox HBoxNuevoUsuario;

   // The nodes of the view
   private Node[] node;

   // The focused and old node
   Node currentNode, oldNode;

   // Reference to the main Stage from the main Scene
   private Stage mainStage;
   // Reference to the main Scene
   private MainScene mainScene;
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
         passwordTextField,
         loginButton,
         activoCheckBox,
         forgetButton,
         newUserLabel};

      // Setting the current node
      currentNode = usuarioTextField;
      oldNode = usuarioTextField;
      usuarioTextField.requestFocus();

      // Setting the jfxtextfield name
      setJFXTextField();

      // Settiong the intial border
      setBorder(usuarioTextField);

      // HBoxError disabled
      errorUserLabel.setManaged(false);
      errorPasswordLabel.setManaged(false);
   }


//</editor-fold> 
   
//<editor-fold defaultstate="collapsed" desc="Setting Field">
   /**
    *
    */
   private void setJFXTextField ()
   {
      eventButton(usuarioTextField, 5, 1);
      eventButton(passwordTextField, 0, 2);
      eventButton(loginButton, 1, 3);
      eventButton(activoCheckBox, 2, 4);
      eventButton(forgetButton, 3, 5);
      eventButton(HBoxNuevoUsuario, 4, 0);
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
            setBorder(n);
         }
      });

      // setting onClick
      n.setOnKeyPressed(
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent ke)
         {
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
                     handleValidation();
                     break;
                  case "passwordTextField":
                     handleValidation();
                     break;
                  case "loginButton":
                     handlelogin();
                     break;
                  case "activoCheckBox":
                     activoCheckBox.setSelected(!activoCheckBox.isSelected());
                     handleActivo();
                     break;
                  case "forgetButton":
                     handleforget();
                     break;
                  case "HBoxNuevoUsuario":
                     handleNuevoUsuario();
                     break;
                  default:
                     break;
               }
            }
            if (ke.getCode().equals(KeyCode.SPACE)) {
               switch (n.getId()) {
                  case "loginButton":
                     handlelogin();
                     break;
                  case "activoCheckBox":
                     handleActivo();
                     break;
                  case "forgetButton":
                     handleforget();
                     break;
                  case "HBoxNuevoUsuario":
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
         n.requestFocus();
         setBorder(n);
         oldNode = n;
         MouseEvent.consume();
         switch (n.getId()) {
            case "loginButton":
               handlelogin();
               break;
            case "activoCheckBox":
               activoCheckBox.setSelected(activoCheckBox.isSelected());
               break;
            case "forgetButton":
               handleforget();
               break;
            case "HBoxNuevoUsuario":
               handleNuevoUsuario();
               break;
            default:
               break;
         }
      });
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

      if (n.equals(passwordTextField)) {
         eraserBorder();
         HBoxPassword.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(loginButton)) {
         eraserBorder();
         HBoxLogin.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(activoCheckBox)) {
         eraserBorder();
         HBoxRecordar.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }

      if (n.equals(forgetButton)) {
         eraserBorder();
         HBoxOlvidaste.getStyleClass().add("borderLoginVisible");
         oldNode = currentNode;
         currentNode = n;
         return;
      }
      if (n.equals(newUserLabel)) {
         eraserBorder();
         HBoxNuevoUsuario.getStyleClass().add("borderLoginVisible");
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
      HBoxPassword.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxLogin.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxRecordar.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxOlvidaste.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxNuevoUsuario.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
   }
//</editor-fold>

//</editor-fold>

   /**
    *
    */
   private void handlelogin ()
   {
   }

   /**
    *
    */
   private void handleforget ()
   {
   }

   /**
    *
    */
   private void handleActivo ()
   {
   }
   
   /**
    *
    */
   private void handleNuevoUsuario ()
   {
      mainScene.handleNuevoUsuario();
   }

   /**
    *
    */
   private void handleValidation ()
   {
   }

}
