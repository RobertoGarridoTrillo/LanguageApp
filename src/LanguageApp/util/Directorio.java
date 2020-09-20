package LanguageApp.util;

import LanguageApp.model.Initial;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class Directorio
 {

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
  int usuario_id;
  int materia_id;
  String materia_nombre;
  int materia_activo;


  Connection conn = null;
  Statement stmt;
  PreparedStatement pstmt;
  String sql;

  // pop-up messages
  Message message;

  /**
   * constructor
   */

  public Directorio()
   {

    // Create a model class with the last directory...etc
    initial = new Initial();

    // pop-up messages
    Message message = new Message(HandleLocale01.handleLocale01());

    // The path is an absolute path (retarive to the initial instalation)    
    path = System.getProperty("user.dir");
    path = path.replace("/", se);
    path = path.replace("//", se);
    path = path.replace("\\", se);

    //path = path.replace("//", se);

    // The last directory is an absolute rute    
    lastDirectory = path + se + "media" + se;
    initialFile = "";


    // Setting the connetion variables
    conn = null;
    stmt = null;
    pstmt = null;
    sql = null;
   }

  /**
   *
   * @param usuario_id
   * @return
   */
  public boolean checkIni(int usuario_id)
   {
    conn = null;
    pstmt = null;
    this.usuario_id = usuario_id;

    // Preparing statement
    try {
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

      // Try connection
      conn = connect();
      conn.setAutoCommit(false);

      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, usuario_id);

      ResultSet rs = pstmt.executeQuery();
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
      pstmt.close();
      conn.close();
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "Directorio / checkIni()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "Directorio / checkIni()", e.toString(), e);
      }
    }
    return false;
   }

  /**
   * pone todos materias activos a cero del usuario que este activo
   *
   * @param usuario_id
   */
  public void createIni(int usuario_id)
   {
    conn = null;
    pstmt = null;
    this.usuario_id = usuario_id;

    try {
      // Try connection

      conn = connect();
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
      pstmt.close();
      conn.close();

    } catch (Exception e) {
       message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleEntrar()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleEntrar()", e.toString(), e);
      }
    }
   }

  /**
   * @param name
   * @param lastDirectory
   * @param usuario_id
   *
   */
  public void checkAndSetLastDirectory(String name, String lastDirectory, int usuario_id)
   {
    this.usuario_id = usuario_id;
    conn = null;
    pstmt = null;
    materia_id = 0;
    boolean salida = false; // flase create a new registre

    // Preparing statement
    try {
      // Try connection
      conn = connect();
      conn.setAutoCommit(false);


      // I check if there is any equal record
      sql = "SELECT m.materia_id, m.materia_nombre FROM materias m " +
              "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
              "WHERE u.usuario_id = ?";

      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, usuario_id);
      //pstmt.setString(2, name);

      ResultSet rs = pstmt.executeQuery();
      conn.commit();
      //rs.first();
      // if there is any 
      while (rs.next() && salida == false) {

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

          // save in the model the global directory
          setPath(path);
          setLastDirectory(lastDirectory);
          setLastFile(name);

          salida = true;
        }
      }

      // si no hay un registro igual al pedido por el filechoose
      if (salida == false) {

        sql = "INSERT INTO materias (fk_usuario_id, materia_nombre, directorio, materia_activo) " +
                "VALUES (?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        // set the value
        pstmt.setInt(1, usuario_id);
        pstmt.setString(2, name);
        pstmt.setString(3, lastDirectory);
        pstmt.setInt(4, 1);
        pstmt.executeUpdate();
        conn.commit();
        pstmt.close();
        conn.close();

        // save in the model the global directory
        setPath(path);
        setLastDirectory(lastDirectory);
        setLastFile(name);
      }
    } catch (Exception e) {
       message.message(Alert.AlertType.ERROR, "Error message", "Directorio / checkAndSetLastDirectory()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "Directorio / checkAndSetLastDirectory()", e.toString(), e);
      }
    }
   }

//<editor-fold defaultstate="collapsed" desc="Setters and Getters">

  /**
   * Return the actual Directory
   *
   * @return the actual getPath
   */
  public String getPath()
   {
    return initial.getPath();
   }

  /**
   *
   * @param path
   */
  public void setPath(String path)
   {
    this.path = path;
    initial.setPath(path);
   }

  /**
   * Return the actual Last Directory
   *
   * @return the actual Last Directory
   */
  public String getLastDirectory()
   {
    return initial.getLastDirectory();
   }

  /**
   * Set the actual directory
   *
   * @param lastDirectory String The actual directory
   */
  public void setLastDirectory(String lastDirectory)
   {
    this.lastDirectory = lastDirectory;
    initial.setLastDirectory(lastDirectory);
   }

  /**
   * Return the actual Last File
   *
   * @return the actual Last File
   *
   */
  public String getLastFile()
   {
    return initial.getLastFile();
   }

  /**
   * Set the last open file
   *
   * @param lastFile String the last open file
   */
  public void setLastFile(String lastFile)
   {
    this.lastFile = lastFile;
    initial.setLastFile(lastFile);
   }

  /**
   * setting the last directory
   *
   */
  public void update()
   {
    try {
      // Try connection
      conn = connect();
      conn.setAutoCommit(false);


      // set the value
      sql = "UPDATE materias SET materia_nombre = ?, directorio = ?, materia_activo = ? WHERE materia_id = ?";
      pstmt = conn.prepareStatement(sql);

      pstmt.setInt(1, materia_id);
      pstmt.setString(2, materia_nombre);
      pstmt.setString(3, lastDirectory);
      pstmt.setInt(4, materia_activo);

      pstmt.executeUpdate();
      conn.commit();
      pstmt.close();

    } catch (Exception e) {
       message.message(Alert.AlertType.ERROR, "Error message", "Directorio / update()", e.toString(), e);
    } finally {
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "Directorio / update()", e.toString(), e);
      }
    }
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Connect">
  /**
   *
   * @return
   */
  public Connection connect()
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
  public void message(Alert.AlertType alertType, String title, String about, String contextText, Exception ex)
   {

    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    //lert.getDialogPane().setMinWidth(600);
    //alert.getDialogPane().setMinHeight(480);
    //alert.getDialogPane().setPrefWidth(600);
    //alert.getDialogPane().setPrefHeight(480);
    alert.setResizable(true);
    alert.getDialogPane().setHeaderText(about);
    alert.getDialogPane().setContentText(contextText);

    if (ex != null) {
      // Create expandable Exception.
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ex.printStackTrace(pw);
      String exceptionText = sw.toString();

      Label label = new Label("El seguimiento del error fue:");

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
