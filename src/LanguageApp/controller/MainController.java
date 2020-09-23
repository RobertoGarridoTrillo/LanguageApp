package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.HandleLocale01;
import LanguageApp.util.Message;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
  @FXML private Menu goMenu;
  @FXML private Menu helpMenu;
  @FXML private MenuItem openMenu;
  @FXML private MenuItem closeMenu;
  @FXML private MenuItem exitMenu;
  @FXML private MenuItem loginMenu;
  @FXML private MenuItem unloginMenu;
  @FXML private MenuItem registroMenu;
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
  private double progressBarValue;

  // For the bounle of idioms
  ResourceBundle resources;

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

    // Initial value of the progressBar and label
    labelText = "";
    progressBarValue = 0.0;

    mainProgressBarBottom.setProgress(progressBarValue);
    mainLabelBottom.setText(labelText);

    // Create the locale for the pop up messages
    HandleLocale01.handleLocale01();
    message = new Message(resources);
    
  }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Menu">

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
    checkCenter();
   }

  /**
   * handle of the login menu
   */
  @FXML private void handleNuevoUsuario()
   {
    mainScene.buttorRegistro();

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

  public void checkCenter()
   {
    String center = mainViewBorderPane.getCenter().getId();

    if (center.equals("loginViewAnchorPane")) {

      mainViewAnchorPaneBottom.setStyle("-fx-background-color: #004f8a");

    } else {

      mainViewAnchorPaneBottom.setStyle("-fx-background-color: #252525");
    }
   }

  //</editor-fold>

  /*/*
    mainLabelBottom.textProperty().addListener((observable) -> {
      synchronized (labelLock) {
        mainLabelBottom.setVisible(true);
        labelPausa = false;
        //System.out.println("observable ");
        labelLock.notify();
      }
    });

    labelThread = new Thread(new Runnable()
     {
      @Override
      public void run()
       {
        while ( ! labelSalir) {
            while (labelPausa) {
          synchronized (labelLock) {
              try {
                labelLock.wait();
                //Thread.sleep(1000);
                System.out.println("labelThread " + Thread.currentThread().getName());
                mainLabelBottom.setVisible(false);
                labelPausa = true;
              } catch (InterruptedException ex) {
                ex.printStackTrace();
              }
            }

          }
        }
       }
     });


    mainProgressBarBottom.progressProperty().addListener(new InvalidationListener()
     {

     @Override public void invalidated(Observable observable)
       {
        if (mainProgressBarBottom.progressProperty().getValue() >= 1) {
          synchronized (progressBarLock) {
            mainProgressBarBottom.setVisible(true);
            progressBarPausa = false;
            progressBarLock.notify();
          }
        }
       }
     });

    progressBarThread = new Thread(() -> {
      try {
        while ( ! progressBarSalir) {
            while (progressBarPausa) {
          synchronized (progressBarLock) {
              progressBarLock.wait();
              System.out.println("progressBarThread " + Thread.currentThread().getName());
              Thread.sleep(0000);
              mainProgressBarBottom.setVisible(false);
              progressBarPausa = true;
            }
          }
        }
      } catch (InterruptedException ex) {
      }
    });
    progressBarThread.start();
    labelThread.start(); */

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
   * @param progress
   */
  public void setProgressBarValue(double progress)
   {
    Platform.runLater(() -> {
      mainProgressBarBottom.setVisible(true);
      
      double newProgress = mainProgressBarBottom.getProgress() + progress;
           
      mainProgressBarBottom.setProgress(newProgress);
      
      if (newProgress >= 1) {
      new Thread(() -> {
        try {
          Thread.sleep(2000);
        } catch (InterruptedException ex) {
        }
        mainProgressBarBottom.setVisible(false);
        mainProgressBarBottom.setProgress(0.0);
      }).start();
      }
    });
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
      new Thread(() -> {
        try {
          Thread.sleep(5000);
          } catch (InterruptedException ex) {
        }
        mainLabelBottom.setVisible(false);
      }).start();
    });

   }

  //</editor-fold>

 }
