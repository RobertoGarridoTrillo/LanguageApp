package LanguageApp.util;

import LanguageApp.main.MainScene;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class Message
 {

  // Reference to the main Stage from the main Scene
  private Stage mainStage;

  // For the bounle of idioms
  ResourceBundle resources;

  public Message(ResourceBundle resources)
   {
    this.resources = resources;
   }

  /**
   * show a standard emergent message
   *
   * @param alertType alertType.CONFIRMATION, ERROR, INFORMATION, NONE, WARNING
   * @param title The title of the windows
   * @param about The them to expose
   * @param contextText The showed text
   * @param ex The thrown exception
   * @return
   */
  public boolean message(Alert.AlertType alertType, String title, String about, String contextText, Exception ex)
   {

    // References to mainScene
    mainStage = MainScene.getMainStage();

    Alert alert = new Alert(alertType);
    alert.setResizable(true);
    
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initOwner(mainStage);


    if (about.equals("¿Quieres cerrar la sesión?") ||
            about.equals("¿Quieres salir de la aplicación?") ||
            about.equals("Acerca de esta aplicación:")) {
      alert.getDialogPane().setHeaderText(resources.getString(about));
    } else {
      alert.getDialogPane().setHeaderText(about);
    }

    if (contextText.equals("Autor: Roberto Garrido Trillo")) {
      alert.getDialogPane().setContentText(resources.getString(contextText));
    } else {
      alert.getDialogPane().setContentText(contextText);
    }

    if (ex != null) {
      // Create expandable Exception.
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ex.printStackTrace(pw);
      String exceptionText = sw.toString();


      Label label = new Label(resources.getString("El seguimiento del error fue:"));
      label.setStyle("  -fx-font-size: 16pt;");
      TextArea textArea = new TextArea(exceptionText);
      textArea.setEditable(true);
      textArea.setWrapText(true);

      textArea.setMaxWidth(Double.MAX_VALUE);
      textArea.setMaxHeight(Double.MAX_VALUE);
      GridPane.setVgrow(textArea, Priority.ALWAYS);
      GridPane.setHgrow(textArea, Priority.ALWAYS);

      GridPane expContent = new GridPane();
      expContent.setMaxWidth(Double.MAX_VALUE);
      expContent.add(label, 0, 0);
      expContent.add(textArea, 0, 1);
      // Set expandable Exception into the dialog pane.
      alert.getDialogPane().setExpandableContent(expContent);
    }

    alert.getDialogPane().getStylesheets().
            add(getClass().getResource("/LanguageApp/style/messages.css").toExternalForm());
    alert.getDialogPane().getStyleClass().add("messages");

    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    Image icon = new Image(getClass().getResourceAsStream("/LanguageApp/resources/images/languages_128.png"));
    stage.getIcons().add(icon);

    try {
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        return true;
      }
    } catch (Exception e) {
    }
    return false;
   }
 }
