package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
public class WelcomeController {

//<editor-fold defaultstate="collapsed" desc="Field Class">

   @FXML public AnchorPane anchorRight;
   @FXML private JFXButton inicioButton;
   @FXML private JFXButton loginButton;
   @FXML private Label nameUserLabel;
   @FXML private JFXCheckBox activoCheckBox;

   @FXML private HBox HBoxInicio;
   @FXML private HBox HBoxLogin;
   @FXML private HBox HBoxRecordar;


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

   // The active user
   String nombre;
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
         inicioButton,
         loginButton,
         activoCheckBox
      };

      // Setting the current node
      currentNode = inicioButton;
      oldNode = inicioButton;
      inicioButton.requestFocus();

      // Setting the jfxtextfield name
      setJFXTextField();

      // Settiong the intial border
      setBorder(inicioButton);

      // Settiong a active user
      nombre = null;

   }
//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setting Field">
   /**
    *
    */
   private void setJFXTextField ()
   {
      eventButton(inicioButton, 2, 1);
      eventButton(loginButton, 0, 2);
      eventButton(activoCheckBox, 1, 0);
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
                  case "inicioButton":
                     handleEntrar();
                     break;
                  case "loginButton":
                     handleLogin();
                     break;
                  case "activoCheckBox":
                     activoCheckBox.setSelected(!activoCheckBox.isSelected());
                     
                     break;
                  default:
                     break;
               }
            }
            if (ke.getCode().equals(KeyCode.SPACE)) {
               switch (n.getId()) {
                  case "inicioButton":
                     handleEntrar();
                     break;
                  case "loginButton":
                     handleLogin();
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
            case "inicioButton":
               handleEntrar();
               break;
            case "loginButton":
               handleLogin();
               break;
            case "activoCheckBox":
               activoCheckBox.setSelected(activoCheckBox.isSelected());
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

      if (n.equals(inicioButton)) {
         eraserBorder();
         HBoxInicio.getStyleClass().add("borderLoginVisible");
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
      HBoxInicio.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxLogin.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
      HBoxRecordar.getStyleClass()
              .removeAll("borderLoginVisible", "borderLoginInvisible");
   }
//</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handles">  
   /**
    *
    * @param nombre
    */
   public void handleCheckNombre (String nombre)
   {
      try {
         this.nombre = nombre;

         if (nombre != null) {
            nameUserLabel.setText(nombre);
         } else {
            nameUserLabel.setText("Usuario no identificado");
         }
      } catch (Exception e) {
      }
   }

   /**
    *
    */
   private void handleEntrar ()
   {

      boolean activoBoolean = activoCheckBox.isSelected();
      if (nombre != null) {

         mainScene.handleEntrar(activoBoolean);

      } else {
         mainScene.handleLoginMenu();
      }
   }

   /**
    *
    */
   private void handleLogin ()
   {
      mainScene.handleLoginMenu();
   }
//</editor-fold>

}
