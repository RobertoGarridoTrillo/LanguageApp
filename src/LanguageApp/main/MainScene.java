package LanguageApp.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class MainScene extends Application {

//<editor-fold defaultstate="collapsed" desc="fileds class">
  private static Stage mainStage;
  public Scene scene;
  private AnchorPane anchorPane;

//</editor-fold>

  /**
   * It setting and showing the stage.
   *
   * @param mainStage The inicial Stage
   *
   */
  @Override
  public void start (Stage mainStage) {

    this.mainStage = mainStage;
    // Set the Title to the Stage
    this.mainStage.setTitle("LanguagesApp");
    // Set the application icon.
   
    this.mainStage.getIcons().add(new Image(getClass().getResourceAsStream("/LanguageApp/resources/images/languages_128.png")));

    // 
    sceneMenu();
    this.mainStage.setResizable(false);
    // height(885) + 40 of the bar
    this.mainStage.setWidth(1215);
    //this.mainStage.setHeight(945);
    // this.mainStage.setMinWidth(1400);
    // this.mainStage.setMinHeight(925);
    //this.mainStage.setMaxWidth(1250);

    //this.mainStage.setMaxHeight(925);

    this.mainStage.show();
  }

  /**
   * load the initial configuration before all the other things loads
   *
   * @throws Exception FilerException - if the same pathname has already been
   * opened for writing, if the source module cannot be determined, or if the
   * target module is not writable, or if an explicit target module is specified
   * and the location does not support it. IOException - if the file cannot be
   * opened IllegalArgumentException - for an unsupported location
   * IllegalArgumentException - if moduleAndPkg is ill-formed
   * IllegalArgumentException - if relativeName is not relative
   */
  @Override
  public void init () throws Exception {
    super.init();
    Font.loadFont(MainScene.class.getResource(
            "/LanguageApp/resources/fonts/FiraCode_3/FiraCode-Regular.ttf").
            toExternalForm(), 10);
  }

  /**
   * It managed the load of the main fxml into the border panel that I use it
   * like root.
   *
   * i can also create a "link" to the controller, in case I need to send data.
   */
  private void sceneMenu () {
    try {
      // Create the FXMLLoader
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainScene.class.getResource("/LanguageApp/view/MainView.fxml"));

      anchorPane = (AnchorPane) loader.load();

      // Give the controller access to the main app (It´s like a instance)
      // MainController controller = loader.getController();
      // controller.setMainScene(this);
      // Create the Scene
      scene = new Scene(anchorPane);
      
      // Adding dark style
      JMetro jMetro = new JMetro(Style.DARK);
      jMetro.setScene(scene);
      
      // Set the Scene to the Stage
      mainStage.setScene(scene);


    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the main stage, if you need it, for example to use it with the
   * filechooser in the controller class
   *
   * @return An object of type Stage
   */
  public static Stage getMainStage () {
    return mainStage;
  }

  /**
   * public static void main
   *
   * @param args
   */
  public static void main (String[] args) {
    launch(args);

  }


}
