package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.HandleLocale01;
import LanguageApp.util.Message;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
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
    this.resources = resources;

    // References to mainStage
    mainStage = MainScene.getMainStage();

    // Fade In / Out
    /*/*    setMainFade(); */

    // Initial value of the progressBar and label
    progressBarValue = new SimpleDoubleProperty(0.0);
    labelText = "";

    mainProgressBarBottom.setProgress(progressBarValue.getValue());
    mainProgressBarBottom.setVisible(false);
    mainLabelBottom.setText(labelText);
    mainLabelBottom.setVisible(false);

    // Create the locale for the pop up messages
    HandleLocale01.handleLocale01();
    message = new Message(resources);

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
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Setting menu-item flags">

  /**
   * Setting the image int the flags
   */
  private void setImageFlags()
   {

    try {

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

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainController / setImageFlags()", e.toString(), e);
    }
   }


  /**
   *
   */
  private void setEventFlags()
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
   */
  private EventHandler<ActionEvent> setEventHandlerFlags()
   {
    return new EventHandler<ActionEvent>()
     {

      @Override
      public void handle(ActionEvent event)
       {

        MenuItem menuItemitem = (MenuItem) event.getSource();
        String flag = menuItemitem.getId();

        mainScene.setButtonSubtitle(flag);
       }

     };
   }


  /**
   *
   * @param s Array of String with the names of languages loaded.
   * @param ss String, the audio lenguage
   */
  public void setVisibleFlagMenu(String[] s, String ss)
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
   */
  @FXML private void handleOpenMenu()
   {
    mainScene.buttonOpenMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * When click on the close menu
   */
  @FXML private void handleCloseMenu()
   {
    mainScene.buttonCloseMenu();
   }


  /**
   * When click on the close menu
   */
  @FXML private void handleExitMenu()
   {
    mainScene.buttonExitMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * handle of the login menu
   */
  @FXML private void handleLoginMenu()
   {
    mainScene.buttonLoginMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * handle of the Unlogin menu
   */
  @FXML private void handleUnloginMenu()
   {
    mainScene.buttonUnloginMenu();

    // change the color of the bottom depens its place
    /*/*checkCenter(); */
   }


  /**
   * handle of the login menu
   */
  @FXML private void handleNuevoUsuario()
   {
    mainScene.buttonRegistro();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * When click on the close menu
   */
  @FXML private void handleControlesMenu()
   {
    mainScene.buttonControlesMenu();
   }


  /**
   * handle of the About menu"
   */
  @FXML private void handleAboutMenu()
   {
    mainScene.buttonAboutMenu();
   }


  /**
   * handle of the Resultados menu
   */
  @FXML private void handleDatabaseMenu()
   {
    mainScene.buttonDatabaseMenu();

    // change the color of the bottom depens its place
    checkCenter();
   }


  /**
   * handle of the Dashboard menu
   */
  @FXML private void handleDashBoardMenu()
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
   */
  public Node checkCenter()
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
   */
  public double getProgressBarValue()
   {
    return mainProgressBarBottom.getProgress();
   }


  /**
   *
   * @param progressBarValue
   */
  public void setProgressBarValue(DoubleProperty progressBarValue)
   {
    //Platform.runLater(() -> {

    mainProgressBarBottom.setVisible(true);
    mainProgressBarBottom.setProgress(progressBarValue.getValue());
    //System.out.println("mainController " + progressBarValue.getValue());

    if (progressBarValue.getValue() >= 1) {
      fadeLabel.play();
      fadeProgressBar.play();
    }
    //});
   }


  /**
   *
   * @return double, the value of the progressBar
   */
  public String getLabelText()
   {
    return mainLabelBottom.getText();
   }


  /**
   *
   * @param text
   */
  public void setLabelText(String text)
   {
    Platform.runLater(() -> {
      mainLabelBottom.setVisible(true);
      mainLabelBottom.setText(text);
    });
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Fade in / out openMenu">

  /**
   *
   */
  public void mainFadeNewIn()
   {
    centerNode = checkCenter();

    mainFadeNewIn = new FadeTransition(Duration.millis(500), centerNode);

    //mainFadeNewIn.setFromValue(0.0);
    mainFadeNewIn.setToValue(1.0);

    System.out.println("mainFadeNewIn " + mainFadeNewIn.getNode() + "\n");

    //centerNode.setVisible(true);    
    /*/*centerNode.setDisable(false);*/
    mainFadeNewIn.play();
   }


  /**
   *
   */
  public void mainFadeOldIn()
   {

    //mainFadeOldIn.setFromValue(0.0);
    mainFadeOldIn.setToValue(1.0);
    mainFadeOldIn.play();
    //centerNodeOld.setVisible(true);

    System.out.println("mainFadeOldIn " + mainFadeOldIn.getNode() + "\n");
   }


  /**
   *
   */
  public void mainFadeOldOut()
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


  //</editor-fold>  

 }
