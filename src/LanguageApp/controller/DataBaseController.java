package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.util.HandleLocale01;
import LanguageApp.util.Message;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class DataBaseController implements Initializable{

//<editor-fold defaultstate="collapsed" desc="Field Class">

   @FXML private AnchorPane dataBaseViewAnchorPane;
   @FXML private Button boton;

   // Reference to the main Stage from the main Scene
   private Stage mainStage;
   // Reference to the main Scene
   private MainScene mainScene;

   // Helper to directoriy
   private String path;
   String se;

   // Savepoint
   Savepoint sp;

   //Global varibles
   Connection conn;
   Statement stmt;

   // pop-up messages
   Message message;

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
      
      this.resources = resources;
         
      // References to mainStage
      mainStage = MainScene.getMainStage();

      // Create the locale for the pop up messages
      HandleLocale01.handleLocale01();
      message = new Message(resources);

      // creating the path
      // The path is an absolute path (relarive to the initial instalation)    
      path = System.getProperty("user.dir");
      se = System.getProperty("file.separator");

      //Global varibles
      conn = null;
      stmt = null;

      // checking the initial database
      createDatabase(connect());
      //insertData(connect()); // Only for testing purpose
   }

//</editor-fold>
   
//<editor-fold defaultstate="collapsed" desc="Connect">
   /**
    *
    * @return
    */
   public Connection connect ()
   {
      conn = null;

      try {
         // path to the database
         String url = "jdbc:sqlite:" + path + se + "database.db";
         conn = DriverManager.getConnection(url);

         // if doesn't exit it's create the database
         Class.forName("org.sqlite.JDBC");
      } catch (ClassNotFoundException | SQLException e) {
         message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / connect()", e.toString(), e);
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

      stmt = null;

      try {

         conn.setAutoCommit(false);
         sp = conn.setSavepoint("crera_database");

         String sql = "PRAGMA foreign_keys = ON;";

         sql += "CREATE TABLE IF NOT EXISTS usuarios ( " +
                 "usuario_id INTEGER  NOT NULL, " +
                 "usuario_nombre TEXT NOT NULL CHECK(length(usuario_nombre)<=100), " +
                 "password TEXT NOT NULL CHECK(length(password)<=20), " +
                 "usuario_activo INTEGER NOT NULL, " +
                 "pregunta TEXT NOT NULL CHECK(length(pregunta)<=100), " +
                 "respuesta TEXT NOT NULL CHECK(length(respuesta)<=100), " +
                 "PRIMARY KEY(usuario_id)" +
                 ");";

         sql += "CREATE TABLE IF NOT EXISTS materias ( " +
                 "materia_id INTEGER NOT NULL, " +
                 "fk_usuario_id INTEGER NOT NULL, " +
                 "materia_nombre TEXT NOT NULL, " +
                 "directorio TEXT NOT NULL, " +
                 "materia_activo INTEGER NOT NULL, " +
                 "PRIMARY KEY(materia_id), " +
                 "FOREIGN KEY (fk_usuario_id) REFERENCES usuarios(usuario_id) " +
                 "ON DELETE CASCADE ON UPDATE CASCADE" +
                 ");";

         sql += "CREATE TABLE IF NOT EXISTS idiomas ( " +
                 "idioma_id INTEGER NOT NULL, " +
                 "fk_materia_id INTEGER NOT NULL, " +
                 "idioma_nombre TEXT NOT NULL, " +
                 "PRIMARY KEY(idioma_id), " +
                 "FOREIGN KEY (fk_materia_id) REFERENCES materias(materia_id) " +
                 ");";

         sql += "CREATE TABLE IF NOT EXISTS datos ( " +
                 "datos_id INTEGER NOT NULL, " +
                 "fk_idioma_id INTEGER NOT NULL, " +
                 "escribir INTEGER NOT NULL, " +
                 "traducir INTEGER NOT NULL, " +
                 "PRIMARY KEY(datos_id), " +
                 "FOREIGN KEY (fk_idioma_id) REFERENCES idiomas(idioma_id) " +
                 "ON DELETE CASCADE ON UPDATE CASCADE" +
                 ");";

         stmt = conn.createStatement();
         stmt.executeUpdate(sql);
         conn.commit();

         stmt.close();
         conn.close();

      } catch (Exception e) {
         try {
            message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / createDatabase()", e.toString(), e);
            conn.rollback(sp);
         } catch (Exception ex) {
            message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / createDatabase()", e.toString(), e);
         }
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception e) {
            message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / createDatabase()", e.toString(), e);
         }
      }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="insertData">
   /**
    * Create a new database if doesn't exit
    */
   private void insertData (Connection conn)
   {

      stmt = null;

      try {

         conn.setAutoCommit(false);
         sp = conn.setSavepoint("insertar_tablas");

         String sql = "INSERT INTO main.usuarios " +
                 "(usuario_nombre, password, usuario_activo, pregunta, respuesta) " +
                 "VALUES ('roberto', '1111', '1', '¿Cuál es tu comida favorita?', '1111');";
         sql += "INSERT INTO main.usuarios " +
                 "(usuario_nombre, password, usuario_activo, pregunta, respuesta) " +
                 "VALUES ('garrido', '2222', '0', '¿Cuál es tu comida favorita?', '2222');";
         sql += "INSERT INTO main.usuarios " +
                 "(usuario_nombre, password, usuario_activo, pregunta, respuesta) " +
                 "VALUES ('trillo', '3333', '0', '¿Cuál es tu comida favorita?', '3333');";

         sql += "INSERT INTO main.materias " +
                 "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
                 "VALUES ('1', 'Los_Vengadores_1.mp4', " +
                 "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Los_Vengadores_1\\', " +
                 "'1');";
         sql += "INSERT INTO main.materias " +
                 "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
                 "VALUES ('1', 'Numb_Linkin_Park.mp4', " +
                 "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Numb_Linkin_Park\\', " +
                 "'0');";
         sql += "INSERT INTO main.materias " +
                 "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
                 "VALUES ('2', 'Los_Vengadores_1.mp4', " +
                 "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Los_Vengadores_1\\', " +
                 "'1');";
         sql += "INSERT INTO main.materias " +
                 "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
                 "VALUES ('2', 'Numb_Linkin_Park.mp4', " +
                 "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Numb_Linkin_Park\\', " +
                 "'0');";
         sql += "INSERT INTO main.materias " +
                 "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
                 "VALUES ('3', 'Los_Vengadores_1.mp4', " +
                 "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Los_Vengadores_1\\', " +
                 "'1');";
         sql += "INSERT INTO main.materias " +
                 "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
                 "VALUES ('3', 'Numb_Linkin_Park.mp4', " +
                 "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Numb_Linkin_Park\\', " +
                 "'0');";

         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('1', 'English.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('1', 'Spanish.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('1', 'Japanese.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('1', 'Italian.jason');";

         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('2', 'English.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('2', 'Spanish.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('2', 'Japanese.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('2', 'Italian.jason');";

         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('3', 'English.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('3', 'Spanish.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('3', 'Japanese.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('3', 'Italian.jason');";

         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('4', 'English.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('4', 'Spanish.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('4', 'Japanese.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('4', 'Italian.jason');";

         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('5', 'English.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('5', 'Spanish.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('5', 'Japanese.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('5', 'Italian.jason');";

         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('6', 'English.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('6', 'Spanish.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('6', 'Japanese.jason');";
         sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
                 "VALUES ('6', 'Italian.jason');";


         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('1', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('1', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('1', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('2', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('2', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('2', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('3', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('3', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('3', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('4', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('4', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('4', '55', '66');";

         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('5', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('5', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('5', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('6', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('6', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('6', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('7', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('7', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('7', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('8', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('8', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('8', '55', '66');";

         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('9', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('9', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('9', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('10', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('10', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('10', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('11', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('11', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('11', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('12', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('12', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('12', '55', '66');";

         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('13', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('13', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('13', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('14', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('14', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('14', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('15', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('15', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('15', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('16', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('16', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('16', '55', '66');";

         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('17', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('17', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('17', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('18', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('18', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('18', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('19', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('19', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('19', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('20', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('20', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('20', '55', '66');";

         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('21', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('21', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('21', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('22', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('22', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('22', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('23', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('23', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('23', '55', '66');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('24', '11', '22');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('24', '33', '44');";
         sql += "INSERT INTO main.datos (fk_idioma_id, escribir, traducir) " +
                 "VALUES ('24', '55', '66');";

         sp = conn.setSavepoint("insertar_tablas");
         stmt = conn.createStatement();
         stmt.executeUpdate(sql);
         conn.commit();

         stmt.close();
         conn.close();

      } catch (Exception e) {
         try {
            message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / insertData()", e.toString(), e);
            conn.rollback(sp);
         } catch (Exception ex) {
            message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / insertData()", e.toString(), e);
         }
      } finally {
         try {
            if (conn != null) {
               conn.close();
            }
         } catch (Exception e) {
            message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / insertData()", e.toString(), e);
         }
      }
   }
//</editor-fold>

//</editor-fold>   

}
