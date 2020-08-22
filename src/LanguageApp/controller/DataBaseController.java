package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
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

   private String path;
   String se = "/";
   
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
      path = path.replace("\\", "/");
      
      // checking the initial database
      createDatabase();
   }

   /**
    * Create a new database if doesn't exit
    */
   private void createDatabase ()
   {
      // path to the database
      String url = "jdbc:sqlite:" + path + se + "database.db";
      Connection conn = null;
      Statement stmt = null;
      // if doesn't exit it's create
      try {
         Class.forName("org.sqlite.JDBC");
         conn = DriverManager.getConnection(url);

         conn.setAutoCommit(false);

         stmt = conn.createStatement();

         String sql = "PRAGMA foreign_keys = ON;";

         sql += "CREATE TABLE IF NOT EXISTS \'usuarios\' (\n" +
                 " \'usuario_id\' INTEGER  NOT NULL PRIMARY KEY,\n" +
                 " \'nombre\' TEXT  NOT NULL,\n" +
                 " \'password\' TEXT NOT NULL,\n" +
                 " \'activo\' INTEGER DEFAULT NULL\n" +
                 ");";

         sql += "CREATE TABLE IF NOT EXISTS \'materias\' (\n" +
                 " \'materia_id\' INTEGER NOT NULL PRIMARY KEY, \n" +
                 " \'fk_usuario_id\' INTEGER NOT NULL,\n" +
                 " \'nombre\' TEXT NOT NULL,\n" +
                 " \'items\' INTEGER NOT NULL,\n" +
                 " FOREIGN KEY (fk_usuario_id) REFERENCES usuarios(usuario_id)\n" +
                 " ON DELETE CASCADE ON UPDATE CASCADE" +
                 " );";

         sql += "CREATE TABLE IF NOT EXISTS \'datos\' (\n" +
                 " \'datos_id\' INTEGER PRIMARY KEY NOT NULL, \n" +
                 " \'fk_materia_id\' INTEGER,\n" +
                 " \'escribir\' INTEGER NOT NULL, \n" +
                 " \'traducir\' INTEGER NOT NULL, \n" +
                 " FOREIGN KEY (fk_materia_id) REFERENCES materias(materia_id)\n" +
                 " ON DELETE CASCADE ON UPDATE CASCADE" +
                 " );";

         sp = conn.setSavepoint("crear_tablas");
         stmt = conn.createStatement();
         stmt.executeUpdate(sql);
         conn.commit();

         DatabaseMetaData meta = conn.getMetaData();
         stmt.close();
         conn.close();

      } catch (ClassNotFoundException | SQLException e) {
         try {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
            conn.rollback(sp);
            stmt.close();
            conn.close();
         } catch (SQLException ex) {
            ex.printStackTrace();
         }
      }
   }

//</editor-fold>   

}
