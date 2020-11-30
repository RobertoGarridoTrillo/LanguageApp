package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.Message;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
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
public class WelcomeController implements Initializable
 {

//<editor-fold defaultstate="collapsed" desc="Field Class">

  @FXML public AnchorPane welcomeViewAnchorPaneRight;

  @FXML private Label Bienvenido;
  @FXML private Label nameUserLabel;

  @FXML private HBox HBoxActivoWelcome;
  @FXML private JFXCheckBox activoCheckBoxWelcome;

  @FXML private HBox HBoxInicioWelcome;
  @FXML private JFXButton inicioButtonWelcome;

  @FXML private HBox HBoxLoginWelcome;
  @FXML private JFXButton loginButtonWelcome;

  @FXML private HBox HBoxCrearWelcome;
  @FXML private JFXButton crearButtonWelcome;

  // pop-up messages
  Message message;

  // The nodes of the view
  private Node[] node;

  // The focused and old node
  Node currentNode, oldNode;

  // Reference to the main Stage from the main Scene
  private Stage mainStage;
  // Reference to the main Scene
  private static MainScene mainScene;

  // The Login or not Login
  boolean login, loginUser, loginPassword;

  // The active user
  String nombre;

  // For the bounle of idioms
  ResourceBundle resources;

  // Fade in / out
  FadeTransition welcomeFadeIn;
  FadeTransition welcomeFadeOut;

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

      // Create the locale for the pop up messages
      /*/*HandleLocale01.handleLocale01();*/
      message = new Message(resources);

      node = new Node[]{
        inicioButtonWelcome,
        activoCheckBoxWelcome,
        loginButtonWelcome,
        crearButtonWelcome
      };

      // Setting the current node
      currentNode = inicioButtonWelcome;
      oldNode = inicioButtonWelcome;
      inicioButtonWelcome.requestFocus();

      // Setting the jfxtextfield name
      setJFXTextField();

      // Settiong the intial border
      setBorder(inicioButtonWelcome);

      // Settiong a active user
      nombre = null;

      // The value of the progressBar in mainScene
      //inicioButtonWelcome.visibleProperty().bind(progressBarValue.greaterThanOrEqualTo(1));
      progressBarValue.addListener((observable, oldValue, newValue) -> {
        inicioButtonWelcome.setDisable(progressBarValue.lessThan(1).get());
      });

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "WelcomeController / initialize()", e.toString(), e);
    }
   }

//</editor-fold> 

//<editor-fold defaultstate="collapsed" desc="Setting Field">

  /**
   *
   */
  private void setJFXTextField()
   {
    eventButton(inicioButtonWelcome, 3, 1);
    eventButton(activoCheckBoxWelcome, 0, 2);
    eventButton(loginButtonWelcome, 1, 3);
    eventButton(crearButtonWelcome, 2, 0);
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
        setBorder(n);
      });

      // setting onKey
      n.setOnKeyPressed(new EventHandler<KeyEvent>()
       {
        @Override
        public void handle(KeyEvent ke)
         {
          int i = -1;

          if (ke.getCode().equals(KeyCode.UP) && !node[up].isDisable()) {
            ke.consume();
            i = up;
          }
          if (ke.getCode().equals(KeyCode.DOWN) && !node[down].isDisable()) {
            ke.consume();
            i = down;
          }
          if (ke.getCode().equals(KeyCode.UP) || ke.getCode().equals(KeyCode.DOWN)) ke.consume();

          if (ke.getCode().equals(KeyCode.ENTER) || ke.getCode().equals(KeyCode.SPACE)) {
            switch (n.getId()) {
              case "inicioButtonWelcome":
                handleEntrar();
                break;
              case "activoCheckBoxWelcome":
                activoCheckBoxWelcome.setSelected(!activoCheckBoxWelcome.isSelected());
                ke.consume();
                break;
              case "loginButtonWelcome":
                handleLogin();
                break;
              case "crearButtonWelcome":
                handleRegistro();
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
        n.requestFocus();
        setBorder(n);
        oldNode = n;
        MouseEvent.consume();

        switch (n.getId()) {
          case "inicioButtonWelcome":
            handleEntrar();
            break;
          case "activoCheckBoxWelcome":
            activoCheckBoxWelcome.setSelected(activoCheckBoxWelcome.isSelected());
            break;
          case "loginButtonWelcome":
            handleLogin();
            break;
          case "crearButtonWelcome":
            handleRegistro();
            break;
          default:
            break;
        }
      });
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "WelcomeController / eventButton()", e.toString(), e);
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

    if (n.equals(inicioButtonWelcome)) {
      eraserBorder();
      HBoxInicioWelcome.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }

    if (n.equals(activoCheckBoxWelcome)) {
      eraserBorder();
      HBoxActivoWelcome.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }

    if (n.equals(loginButtonWelcome)) {
      eraserBorder();
      HBoxLoginWelcome.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }

    if (n.equals(crearButtonWelcome)) {
      eraserBorder();
      HBoxCrearWelcome.getStyleClass().add("borderLoginVisible");
      oldNode = currentNode;
      currentNode = n;
      return;
    }
    eraserBorder();
    n.getStyleClass().add("borderLoginVisible");
    oldNode = currentNode;
    currentNode = n;

   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="eraser Border">

  /**
   * Helper to setting Color Border
   */
  private void eraserBorder()
   {
    currentNode.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxInicioWelcome.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxActivoWelcome.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxLoginWelcome.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
    HBoxCrearWelcome.getStyleClass()
            .removeAll("borderLoginVisible", "borderLoginInvisible");
   }

  //</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Handles">  
  /**
   * It's called from mainScene to put the name of the user in the label
   *
   * @param nombre
   */
  public void handlePutName(String nombre)
   {
    try {
      this.nombre = nombre;

      if (nombre != null) {
        nameUserLabel.setText(nombre);
      } else {
        nameUserLabel.setText(resources.getString("Usuario no identificado"));
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "handlePutName / eventButton()", e.toString(), e);
    }
   }


  /**
   *
   */
  private void handleEntrar()
   {

    boolean activoBoolean = activoCheckBoxWelcome.isSelected();

    if (nombre != null) {

      mainScene.handleEntrar(activoBoolean, true); 
    } else {
      mainScene.buttonLoginMenu();
    }
   }


  /**
   *
   */
  private void handleLogin()
   {
    mainScene.buttonLoginMenu();
   }


  /**
   *
   */
  private void handleRegistro()
   {
    mainScene.buttonRegistro();
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setters and Getters">

  /**
   *
   * @param progressBarValue
   */
  public void setProgressBarValue(DoubleProperty progressBarValue)
   {
    this.progressBarValue.setValue(progressBarValue.getValue());
    //System.out.println("welcome " + this.progressBarValue.getValue());
   }

//</editor-fold>

 }
