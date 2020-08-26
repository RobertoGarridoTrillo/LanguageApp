package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class DataBaseController {

//<editor-fold defaultstate="collapsed" desc="Field Class">

   @FXML private AnchorPane dataBaseViewAnchorPane;
   @FXML private Button boton;

   // Reference to the main Stage from the main Scene
   private Stage mainStage;
   // Reference to the main Scene
   private MainScene mainScene;

   // Helper to directoriy
   private String path;
   String se = "/";

   // Savepoint
   Savepoint sp;

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

      // creating the path
      // The path is an absolute path (relarive to the initial instalation)    
      path = System.getProperty("user.dir");
      path = path.replace("\\",
              "/");

      // checking the initial database

      createDatabase(connect());
   }

//<editor-fold defaultstate="collapsed" desc="Connect">
   /**
    *
    */
   public Connection connect ()
   {
      Connection conn = null;


      try {
         // path to the database
         String url = "jdbc:sqlite:" + path + se + "database.db";
         conn = DriverManager.getConnection(url);
         conn.setAutoCommit(false);
         
         // if doesn't exit it's create the database
         Class.forName("org.sqlite.JDBC");
      } catch (ClassNotFoundException e) {
         message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "DataBaseController / connect()", e);
      } catch (SQLException e) {
         message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "DataBaseController / connect()", e);
      }
      return conn;
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="CreateDataBase">

   /**
    * Create a new database if doesn't exit
    */
   private void createDatabase (Connection conn)
   {

      Statement stmt = null;

      try {

         conn.setAutoCommit(false);
         
         String sql = "PRAGMA foreign_keys = ON;";

         sql += "CREATE TABLE IF NOT EXISTS usuarios (\n" +
                 " usuario_id INTEGER  NOT NULL PRIMARY KEY,\n" +
                 " nombre TEXT NOT NULL CHECK(length(nombre)<=100),\n" +
                 " password TEXT NOT NULL NOT NULL CHECK(length(nombre)<=40),\n" +
                 " activo INTEGER NOT NULL,\n" +
                 " pregunta TEXT NOT NULL CHECK(length(nombre)<=100),\n" +
                 " respuesta TEXT NOT NULL CHECK(length(nombre)<=100)" +
                 ");";

         sql += "CREATE TABLE IF NOT EXISTS materias (\n" +
                 " materia_id INTEGER NOT NULL PRIMARY KEY, \n" +
                 " fk_usuario_id INTEGER NOT NULL,\n" +
                 " nombre TEXT NOT NULL,\n" +
                 " items INTEGER NOT NULL,\n" +
                 " FOREIGN KEY (fk_usuario_id) REFERENCES usuarios(usuario_id)\n" +
                 " ON DELETE CASCADE ON UPDATE CASCADE" +
                 " );";

         sql += "CREATE TABLE IF NOT EXISTS datos (\n" +
                 " datos_id INTEGER PRIMARY KEY NOT NULL, \n" +
                 " fk_materia_id INTEGER,\n" +
                 " escribir INTEGER NOT NULL, \n" +
                 " traducir INTEGER NOT NULL, \n" +
                 " FOREIGN KEY (fk_materia_id) REFERENCES materias(materia_id)\n" +
                 " ON DELETE CASCADE ON UPDATE CASCADE" +
                 " );";

         sp = conn.setSavepoint("crear_tablas");
         stmt = conn.createStatement();
         stmt.executeUpdate(sql);
         conn.commit();

         stmt.close();
         conn.close();

      } catch (SQLException e) {
         try {
            message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "DataBaseController / createDatabase()", e);
            conn.rollback(sp);
         } catch (SQLException ex) {
            message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "DataBaseController / createDatabase()", e);
         }
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (SQLException e) {
            message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "DataBaseController / createDatabase()", e);
         }
      }
   }
//</editor-fold>

//</editor-fold>   

//<editor-fold defaultstate="collapsed" desc="Executing Emergentes messages">
   /**
    * show a standard emergent message
    *
    * @param alertType alertType.CONFIRMATION, ERROR, INFORMATION, NONE, WARNING
    * @param title The title of the windows
    * @param about The them to expose
    * @param contextText The showed text
    * @param ex The thrown exception
    */
   public void message (Alert.AlertType alertType, String title, String about, String contextText, Exception ex)
   {

      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      alert.getDialogPane().setMinWidth(600);
      alert.getDialogPane().setMinHeight(480);
      alert.getDialogPane().setPrefWidth(600);
      alert.getDialogPane().setPrefHeight(480);
      alert.setResizable(true);
      alert.setHeaderText(about);
      alert.getDialogPane().setContentText(contextText);

      if (ex != null) {
         // Create expandable Exception.
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         ex.printStackTrace(pw);
         String exceptionText = sw.toString();

         Label label = new Label("The exception stacktrace was:");

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
              add(getClass().getResource("/LanguageApp/style/style.css").toExternalForm());
      alert.getDialogPane().getStyleClass().add("style");

      Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
      Image icon = new Image(getClass().getResourceAsStream("/LanguageApp/resources/images/languages_128.png"));
      stage.getIcons().add(icon);


      alert.showAndWait();
   }
//</editor-fold>
}
