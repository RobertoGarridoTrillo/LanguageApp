package LanguageApp.util;

import LanguageApp.main.MainScene;
import LanguageApp.model.Initial;
import static LanguageApp.util.Message.showException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import static java.util.Objects.isNull;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class Directory
 {

//<editor-fold defaultstate="collapsed" desc="Field Class">
  // Global directories
  private String path;
  private String lastDirectory;
  private String lastFile;

  //String se = "/";
  String se = System.getProperty("file.separator");
  String initialFile;

  // Instancias
  private Initial initial;

  // Global variables
  private int usuario_id;
  private int materia_id;
  private String materia_nombre;
  private int materia_activo;

  // Connetion varibles
  private Connection conn;
  private Statement stmt;
  private PreparedStatement pstmt;
  private String sql;
  // Resulset of the tableview
  private ResultSet rs;

  // Reference to the main Scene
  private MainScene mainScene;
//</editor-fold>

  /**
   * constructor
   */
  public Directory()
   {
    try {

      // Create a model class with the last conn...etc
      initial = new Initial();

      // References to mainScene
      mainScene = MainScene.getMainScene();

      // The path is an absolute path (retarive to the initial instalation)    
      path = System.getProperty("user.dir");
      path = path.replace("/", se);
      path = path.replace("//", se);
      path = path.replace("\\", se);

      //path = path.replace("//", se);

      // The last conn is an absolute rute    
      lastDirectory = path + se + "media" + se;
      initialFile = "";

      // Creating the conection
      conn = mainScene.getConnection();

      // Setting the connetion variables
      stmt = null;
      pstmt = null;
      sql = null;
    } catch (Exception e) {
      mainScene.releaseConnection(conn);
      showException(e);
    }
   }


  /**
   *
   * @param usuario_id
   * @return
   * @throws java.lang.Exception
   */
  public boolean checkIni(int usuario_id) throws Exception
   {
    pstmt = null;
    this.usuario_id = usuario_id;

    // Preparing statement
    /*
         SELECT u.usuario_nombre, u.usuario_activo, m.materia_nombre, m.materia_id, 
         i.idioma_nombre, d.escribir, d.traducir
         FROM usuarios u
         INNER JOIN materias m on u.usuario_id = m.fk_usuario_id 
         INNER JOIN idiomas i on m.materia_id = i.fk_materia_id
         INNER JOIN datos d on  i.idioma_id = d.fk_idioma_id
         WHERE u.usuario_activo = 1 and m.materia_activo = 1
     */
    sql = "SELECT m.directorio, m.materia_nombre FROM materias m " +
            "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
            "WHERE u.usuario_id = ? and m.materia_activo = 1";

    conn.setAutoCommit(false);

    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, usuario_id);

    rs = pstmt.executeQuery();
    conn.commit();

    if (rs.next()) {
      lastDirectory = rs.getString(1);
      initialFile = rs.getString(2);

      // creating initial objet
      setPath(path);
      setLastDirectory(lastDirectory);
      setLastFile(initialFile);

      return true;
    } else {
      lastDirectory = path + se + "media" + se;
      initialFile = "";

      setPath(path);
      setLastDirectory(lastDirectory);
      setLastFile(initialFile);
    }

    resourcesClose();

    return false;
   }


  /**
   * pone todos materias activos a cero del usuario que este activo
   *
   * @param usuario_id
   * @throws java.lang.Exception
   */
  public void createIni(int usuario_id) throws Exception
   {
    pstmt = null;
    this.usuario_id = usuario_id;

    conn.setAutoCommit(false);

    sql = "UPDATE materias set materia_activo = 0 " +
            "WHERE materia_id IN ( " +
            "SELECT m.materia_id FROM materias m " +
            "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
            "WHERE u.usuario_id = ?)";

    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, usuario_id);

    pstmt.executeUpdate();
    conn.commit();

   }


  /**
   * @param name
   * @param lastDirectory
   * @param usuario_id
   * @throws java.lang.Exception
   *
   */
  public void checkAndSetLastDirectory(String name, String lastDirectory,
          int usuario_id) throws Exception
   {
    this.usuario_id = usuario_id;

    pstmt = null;
    materia_id = 0;
    boolean salida = false; // flase create a new registre

    conn.setAutoCommit(false);

    // I check if there is any equal record
    sql = "SELECT m.materia_id, m.materia_nombre FROM materias m " +
            "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
            "WHERE u.usuario_id = ?";

    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, usuario_id);
    //pstmt.setString(2, name);

    rs = pstmt.executeQuery();
    conn.commit();

    // if there is any 
    while (rs.next()) {

      // si hay un registro igual al pedido por el filechoose
      if (rs.getString("materia_nombre").equals(name)) {
        materia_id = rs.getInt("materia_id");
        sql = "UPDATE materias set materia_activo = 0 " +
                "WHERE materia_id IN ( " +
                "SELECT m.materia_id FROM materias m " +
                "INNER JOIN usuarios u " +
                "ON u.usuario_id = m.fk_usuario_id " +
                "WHERE u.usuario_id = ?)";

        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, usuario_id);
        pstmt.executeUpdate();
        conn.commit();

        // set the value on active
        sql = "UPDATE materias set materia_activo = 1, directorio = ? " +
                "WHERE materia_id IN ( " +
                "SELECT m.materia_id FROM materias m " +
                "INNER JOIN usuarios u " +
                "ON u.usuario_id = m.fk_usuario_id " +
                "WHERE u.usuario_id = ? and m.materia_id = ? )";
        pstmt = null;
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, lastDirectory);
        pstmt.setInt(2, usuario_id);
        pstmt.setInt(3, materia_id);
        pstmt.executeUpdate();
        conn.commit();

        // save in the model the global conn
        setPath(path);
        setLastDirectory(lastDirectory);
        setLastFile(name);

        salida = true;
      }
    }

    // si no hay un registro igual al pedido por el filechoose
    if (salida == false) {

      sql = "UPDATE materias set materia_activo = 0 " +
              "WHERE materia_id IN ( " +
              "SELECT m.materia_id FROM materias m " +
              "INNER JOIN usuarios u " +
              "ON u.usuario_id = m.fk_usuario_id " +
              "WHERE u.usuario_id = ?)";

      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, usuario_id);
      pstmt.executeUpdate();
      conn.commit();

      sql = "INSERT INTO materias (fk_usuario_id, materia_nombre, directorio," +
              " materia_activo) " + "VALUES (?,?,?,?)";
      pstmt = conn.prepareStatement(sql);
      // set the value
      pstmt.setInt(1, usuario_id);
      pstmt.setString(2, name);
      pstmt.setString(3, lastDirectory);
      pstmt.setInt(4, 1);
      pstmt.executeUpdate();
      conn.commit();

      // save in the model the global conn
      setPath(path);
      setLastDirectory(lastDirectory);
      setLastFile(name);
      rs = conn.prepareStatement("SELECT last_insert_rowid();").executeQuery();
      if (rs.next()) materia_id = rs.getInt(1);

      resourcesClose();

    }
   }


  /**
   *
   * @param subtitle
   * @throws java.lang.Exception
   */
  public void checkAndSetIdioma(String[] subtitle) throws Exception
   {
    pstmt = null;

    conn.setAutoCommit(false);

    sql = "SELECT m.materia_id FROM materias m " +
            "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
            "WHERE u.usuario_id = ? AND m.materia_activo = 1";
    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, usuario_id);
    rs = pstmt.executeQuery();
    conn.commit();

    if (rs.next()) {
      materia_id = rs.getInt("materia_id");
    }

    // I check if there is any equal record
    sql = "SELECT i.idioma_nombre FROM idiomas i WHERE i.fk_materia_id = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, materia_id);
    rs = pstmt.executeQuery();
    conn.commit();

    ArrayList<String> names = new ArrayList<>();
    while (rs.next()) {
      names.add(rs.getString("idioma_nombre"));
    }

    for (String sub : subtitle) {
      if (!names.contains(sub)) {
        sql = "INSERT INTO idiomas (fk_materia_id, idioma_nombre) VALUES (?,?)";
        pstmt = conn.prepareStatement(sql);
        // set the value
        pstmt.setInt(1, materia_id);
        pstmt.setString(2, sub);
        pstmt.executeUpdate();
        conn.commit();
      }
    }

    resourcesClose();

   }


  /**
   *
   * @throws Exception
   */
  public void resourcesClose()
   {
    try {
      if (!isNull(stmt)) stmt.close();
      if (!isNull(pstmt)) pstmt.close();
      if (!isNull(rs)) rs.close();
    } catch (Exception e) {
      showException(e);
    }
   }

//<editor-fold defaultstate="collapsed" desc="Setters and Getters">
  /**
   * Return the actual Directory
   *
   * @return the actual getPath
   * @throws java.lang.Exception
   */
  public String getPath() throws Exception
   {
    return initial.getPath();
   }


  /**
   *
   * @param path
   * @throws java.lang.Exception
   */
  public void setPath(String path) throws Exception
   {
    this.path = path;
    initial.setPath(path);
   }


  /**
   * Return the actual Last Directory
   *
   * @return the actual Last Directory
   * @throws java.lang.Exception
   */
  public String getLastDirectory() throws Exception
   {
    return initial.getLastDirectory();
   }


  /**
   * Set the actual conn
   *
   * @param lastDirectory String The actual conn
   * @throws java.lang.Exception
   */
  public void setLastDirectory(String lastDirectory) throws Exception
   {
    this.lastDirectory = lastDirectory;
    initial.setLastDirectory(lastDirectory);
   }


  /**
   * Return the actual Last File
   *
   * @return the actual Last File
   * @throws java.lang.Exception
   *
   */
  public String getLastFile() throws Exception
   {
    return initial.getLastFile();
   }


  /**
   * Set the last open file
   *
   * @param lastFile String the last open file
   * @throws java.lang.Exception
   */
  public void setLastFile(String lastFile) throws Exception
   {
    this.lastFile = lastFile;
    initial.setLastFile(lastFile);
   }


  /**
   * setting the last conn
   *
   * @param usuario_id
   * @throws java.lang.Exception
   */
  public void delete(int usuario_id) throws Exception
   {
    pstmt = null;

    conn.setAutoCommit(false);
    Platform.runLater(() -> {
      try {
        Message.message(
                Alert.AlertType.ERROR,
                "Error message",
                "Archivos multimedia movidos o perdidos",
                "La aplicación se reiniciará ...",
                null);
      } catch (Exception ex) {
        showException(ex);
      }
      Platform.exit();
      System.exit(0);
    });
    // set the value
    sql = "DELETE FROM materias  " +
            "WHERE materia_id IN ( " +
            "SELECT m.materia_id FROM materias m " +
            "INNER JOIN usuarios u " +
            "ON u.usuario_id = m.fk_usuario_id " +
            "WHERE u.usuario_id = ? and m.materia_activo = 1)";

    pstmt = conn.prepareStatement(sql);

    pstmt.setInt(1, usuario_id);

    pstmt.executeUpdate();
    conn.commit();

    resourcesClose();

   }
//</editor-fold>

 }
