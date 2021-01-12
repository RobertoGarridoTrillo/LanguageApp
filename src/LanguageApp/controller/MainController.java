package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.Message;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class MainController implements Initializable
 {

  //<editor-fold defaultstate="collapsed" desc="fields class">

  @FXML private BorderPane mainViewBorderPane;
  @FXML private AnchorPane mainViewAnchorPaneBottom;
  @FXML private Label mainLabelBottom;
  @FXML private ProgressBar mainProgressBarBottom;

  @FXML private MenuBar menuBar;

  @FXML private Menu fileMenu;
  @FXML private Menu userMenu;
  @FXML private Menu flagsMenu;
  @FXML private Menu goMenu;
  @FXML private Menu helpMenu;

  @FXML private MenuItem openMenu;
  @FXML private MenuItem closeMenu;
  @FXML private MenuItem exitMenu;

  @FXML private MenuItem loginMenu;
  @FXML private MenuItem unloginMenu;
  @FXML private MenuItem registroMenu;

  @FXML private MenuItem EnglishMenu;
  @FXML private MenuItem SpanishMenu;
  @FXML private MenuItem FrenchMenu;
  @FXML private MenuItem ItalianMenu;
  @FXML private MenuItem JapaneseMenu;

  @FXML private MenuItem dashBoard;
  @FXML private MenuItem databaseMenu;

  @FXML private MenuItem controlesMenu;
  @FXML private MenuItem aboutMenu;

  // pop-up messages
  Message message;

  // Reference to the main Stage from the main Scene
  private Stage mainStage;

  // Reference to the main Scene
  private MainScene mainScene;

  // Text and progressBar value
  private String labelText;
  private DoubleProperty progressBarValue;

  // For the bounle of idioms
  ResourceBundle resources;

  // Effect fade
  private FadeTransition fadeProgressBar;
  private FadeTransition fadeLabel;

  // Array of nodes (Flags)
  private MenuItem[] menuItemFlags;

  // List of the Subtitles and audio
  private String[] subtitle;
  private String subtitleAudio;

  // Images of the flags
  ImageView[] imageViews;

  // Events of the flags
  EventHandler<ActionEvent> eventHandlerFlags;


  // Central Node
  private Node centerNode, centerNodeOld;
  private String centerString;

  // Fade in / out
  FadeTransition mainFadeOldIn, mainFadeNewIn;
  FadeTransition mainFadeOldOut;

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
   */
  @Override
  public void initialize(URL location, ResourceBundle resources)
   {
    try {
      this.resources = resources;
      message = new Message(mainStage, resources);

      // References to mainStage
      mainStage = MainScene.getMainStage();

      // Initial value of the progressBar and label
      progressBarValue = new SimpleDoubleProperty(0.0);
      labelText = "";

      mainProgressBarBottom.setProgress(progressBarValue.getValue());
      mainProgressBarBottom.setVisible(false);
      mainLabelBottom.setText(labelText);
      mainLabelBottom.setVisible(false);

      // Create the locale for the pop up messages

      // Setting the flags of the menu item
      setImageFlags();
      setEventFlags();

      // Effect fade
      fadeProgressBar = new FadeTransition(Duration.millis(2000), mainProgressBarBottom);
      fadeLabel = new FadeTransition(Duration.millis(2000), mainLabelBottom);
      fadeProgressBar.setFromValue(1.0);
      fadeProgressBar.setToValue(0.0);
      fadeLabel.setFromValue(1.0);
      fadeLabel.setToValue(0.0);
      fadeProgressBar.setDelay(Duration.millis(2000));
      fadeLabel.setDelay(Duration.millis(2000));
      fadeProgressBar.setOnFinished((e) -> {
        mainProgressBarBottom.setVisible(false);
        mainLabelBottom.setVisible(false);
        mainProgressBarBottom.setOpacity(1);
        mainLabelBottom.setOpacity(1);
      });


      // The value of the progressBar in mainScene
      //inicioButtonWelcome.visibleProperty().bind(progressBarValue.greaterThanOrEqualTo(1));
      progressBarValue.addListener((observable, oldValue, newValue) -> {
        menuBar.setDisable(progressBarValue.lessThan(1).get());
      });
    } catch (Exception e) {
      Message.showException(e);
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Setting menu-item flags">

  /**
   * Setting the image int the flags
      * @throws java.lang.Exception 
   */
  private void setImageFlags() throws Exception
   {
    imageViews = new ImageView[5];
    menuItemFlags = new MenuItem[]{EnglishMenu, SpanishMenu,
      FrenchMenu, ItalianMenu, JapaneseMenu};

    String[] ruta = {
      "English.png", "Spanish.png", "French.png", "Italian.png", "Japanese.png"};
    for (int i = 0; i < imageViews.length; i++) {
      Image image = new Image(getClass().getResource("/LanguageApp/resources/images/" + ruta[i]).toExternalForm());
      imageViews[i] = new ImageView(image);
      imageViews[i].setFitWidth(40);
      imageViews[i].setFitHeight(25);
      menuItemFlags[i].setGraphic(imageViews[i]);
      menuItemFlags[i].setVisible(false);
    }
   }


/**
 * 
 * @throws Exception 
 */
  private void setEventFlags() throws Exception
   {
    eventHandlerFlags = setEventHandlerFlags(); // Creo a new EventHandler
    EnglishMenu.setOnAction(eventHandlerFlags);
    FrenchMenu.setOnAction(eventHandlerFlags);
    ItalianMenu.setOnAction(eventHandlerFlags);
    JapaneseMenu.setOnAction(eventHandlerFlags);
    SpanishMenu.setOnAction(eventHandlerFlags);
   }


  /**
   *
   * @return
      * @throws java.lang.Exception 
   */
  private EventHandler<ActionEvent> setEventHandlerFlags() throws Exception
   {
    return new EventHandler<ActionEvent>()
     {

      @Override
      public void handle(ActionEvent event)
       {
        try {
          MenuItem menuItemitem = (MenuItem) event.getSource();
          String flag = menuItemitem.getId();

          mainScene.setButtonSubtitle(flag);
        } catch (Exception e) {
          Message.showException(e);
        }
       }

     };
   }

/**
 * 
 * @param s
 * @param ss
 * @throws Exception 
 */
  public void setInvisibleFlagMenu(String[] s, String ss) throws Exception
   {

    for (String menuItem : s) {
      menuItemFlags[Arrays.asList(s).indexOf(menuItem)].setVisible(false);
    }
    // the language of the media is always disabled
    menuItemFlags[Arrays.asList(s).indexOf(ss)].setDisable(false);
   }


  /**
   *
   * @param s Array of String with the names of languages loaded.
   * @param ss String, the audio lenguage
   * @throws java.lang.Exception
   */
  public void setVisibleFlagMenu(String[] s, String ss) throws Exception
   {

    for (String menuItem : s) {
      menuItemFlags[Arrays.asList(s).indexOf(menuItem)].setVisible(true);
    }
    // the language of the media is always disabled
    menuItemFlags[Arrays.asList(s).indexOf(ss)].setDisable(true);
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Buttond Menu">

  /**
   * Open a SelectFile and seek a json to load the phrases
   * @throws java.lang.Exception 
   */
  @FXML private void handleOpenMenu() throws Exception
   {
    mainScene.buttonOpenMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * When click on the close menu
   * @throws java.lang.Exception 
   */
  @FXML private void handleCloseMenu() throws Exception
   {
    mainScene.buttonCloseMenu();
   }


  /**
   * When click on the close menu
   *
   * @throws java.lang.Exception
   */
  @FXML private void handleExitMenu() throws Exception
   {
    mainScene.buttonExitMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * handle of the login menu
   *
   * @throws java.lang.Exception
   */
  @FXML private void handleLoginMenu() throws Exception
   {
    mainScene.buttonLoginMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * handle of the Unlogin menu
   *
   * @throws java.lang.Exception
   */
  @FXML private void handleUnloginMenu() throws Exception
   {
    mainScene.buttonUnloginMenu();

    // change the color of the bottom depens its place
    /*/*checkCenter(); */
   }


  /**
   * handle of the login menu
   *
   * @throws java.lang.Exception
   */
  @FXML private void handleNuevoUsuario() throws Exception
   {
    mainScene.buttonRegistro();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * When click on the close menu
   *
   * @throws java.lang.Exception
   */
  @FXML private void handleControlesMenu() throws Exception
   {
    mainScene.buttonControlesMenu();
   }


  /**
   * handle of the About menu
   *
   * @throws java.lang.Exception
   */
  @FXML private void handleAboutMenu() throws Exception
   {
    mainScene.buttonAboutMenu();
   }


  /**
   * handle of the Resultados menu
   *
   * @throws java.lang.Exception
   */
  @FXML private void handleDatabaseMenu() throws Exception
   {
    mainScene.buttonDatabaseMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * handle of the Dashboard menu
   *
   * @throws java.lang.Exception
   */
  @FXML private void handleDashBoardMenu() throws Exception
   {
    mainScene.buttonDashBoardMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="checkCenter">

  /**
   *
   * @return Node, The central node in mainview. Also change the colour of the bottom bar (blue or gray)
   * @throws java.lang.Exception
   */
  public Node checkCenter() throws Exception
   {
    centerNode = mainViewBorderPane.getCenter();
    centerString = centerNode.getId();

    //System.out.println("center " + centerString);

    if (centerString.equals("loginViewHbox")) {

      mainViewBorderPane.setStyle("-fx-background-color: #004f8a");

    } else {

      mainViewBorderPane.setStyle("-fx-background-color: #252525");
    }
    return centerNode;
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Setters and Getters">

  /**
   *
   * @return double, the value of the progressBar
   * @throws java.lang.Exception
   */
  public double getProgressBarValue() throws Exception
   {
    return mainProgressBarBottom.getProgress();
   }


  /**
   *
   * @param progressBarValue
   * @throws java.lang.Exception
   */
  public void setProgressBarValue(DoubleProperty progressBarValue) throws Exception
   {

    mainProgressBarBottom.setVisible(true);
    mainProgressBarBottom.setProgress(progressBarValue.getValue());
    //System.out.println("mainController " + progressBarValue.getValue());

    if (progressBarValue.getValue() >= 1) {
      fadeLabel.play();
      fadeProgressBar.play();
    }
   }


  /**
   *
   * @return double, the value of the progressBar
   * @throws java.lang.Exception
   */
  public String getLabelText() throws Exception
   {
    return mainLabelBottom.getText();
   }


  /**
   *
   * @param text
   * @throws Exception
   */
  public void setLabelText(String text) throws Exception
   {
    Platform.runLater(() -> {
      try {
        mainLabelBottom.setVisible(true);
        mainLabelBottom.setText(text);
      } catch (Exception e) {
        Message.showException(e);
      }
    });
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Fade in / out openMenu and Label-progressBar">

  /**
   *
   * @throws Exception
   */
  public void mainFadeNewIn() throws Exception
   {
    centerNode = checkCenter();

    mainFadeNewIn = new FadeTransition(Duration.millis(500), centerNode);

    // mainFadeNewIn.setFromValue(0.0);
    mainFadeNewIn.setToValue(1.0);

    // System.out.println("mainFadeNewIn " + mainFadeNewIn.getNode() + "\n");

    //centerNode.setVisible(true);    
    /*/*centerNode.setDisable(false);*/
    mainFadeNewIn.play();
   }


  /**
   *
   * @throws Exception
   */
  public void mainFadeOldIn() throws Exception
   {

    //mainFadeOldIn.setFromValue(0.0);
    mainFadeOldIn.setToValue(1.0);
    mainFadeOldIn.play();
    //centerNodeOld.setVisible(true);

    // System.out.println("mainFadeOldIn " + mainFadeOldIn.getNode() + "\n");
   }


  /**
   *
   * @throws Exception
   */
  public void mainFadeOldOut() throws Exception
   {
    centerNode = checkCenter();
    centerNodeOld = centerNode;

    mainFadeOldOut = new FadeTransition(Duration.millis(500), centerNodeOld);
    mainFadeOldIn = new FadeTransition(Duration.millis(500), centerNodeOld);

    mainFadeOldOut.setFromValue(1.0);
    mainFadeOldOut.setToValue(0.0);

    mainFadeOldOut.setOnFinished((e) -> {
      //centerNodeOld.setVisible(false);
    });

    /*/*centerNode.setDisable(false);*/
    // centerNode.setVisible(true);
    mainFadeOldOut.play();
   }


  /**
   *
   * @throws Exception
   */
  public void fadeLabel() throws Exception
   {
    fadeLabel.play();
   }


  public void fadeProgressBar()
   {
    fadeProgressBar.play();
   }


  //</editor-fold>  

 }
