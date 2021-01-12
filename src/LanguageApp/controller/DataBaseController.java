package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import static LanguageApp.main.MainScene.setUsuario_id;
import static LanguageApp.main.MainScene.getUsuario_id;
import static LanguageApp.main.MainScene.setUsuario_nombre;
import LanguageApp.model.Idioma;
import LanguageApp.model.Materia;
import LanguageApp.model.Usuario;
import static LanguageApp.util.HandleLocale.toLocale;
import LanguageApp.util.Message;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import static java.util.Objects.isNull;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

//</editor-fold>

/**
 *
 * @author Roberto Garrido Trillo
 */
public class DataBaseController implements Initializable
 {

  //<editor-fold defaultstate="collapsed" desc="Field Class">

  @FXML private TabPane tabPanelDataBase;

  @FXML private TableView<Usuario> usuarioTableView;

  @FXML private TableColumn<Usuario, Integer> usuario_idTableColumn;
  @FXML private TableColumn<Usuario, String> usuario_nombreTableColumn;
  @FXML private TableColumn<Usuario, String> passwordTableColumn;
  @FXML private TableColumn<Usuario, Integer> usuario_activoTableColumn;
  @FXML private TableColumn<Usuario, String> preguntaTableColumn;
  @FXML private TableColumn<Usuario, String> respuestaTableColumn;

  @FXML private TableView<Materia> materiasTableView;

  @FXML private TableColumn<Materia, Integer> materia_idTableColumn;
  @FXML private TableColumn<Materia, String> fk_usuario_nombreTableColumn;
  @FXML private TableColumn<Materia, String> materia_nombreTableColumn;
  @FXML private TableColumn<Materia, String> directorioTableColumn;

  @FXML private TableView<Idioma> idiomasTableView;

  @FXML private TableColumn<Idioma, Integer> idioma_idTableColumn;
  @FXML private TableColumn<Idioma, String> subject_nombreTableColumn;
  @FXML private TableColumn<Idioma, String> idiomaTableColumn;
  @FXML private TableColumn<Idioma, Integer> escribirTableColumn;
  @FXML private TableColumn<Idioma, Integer> traducirTableColumn;

  // for modal formDatabase
  private AnchorPane formDataBaseView;
  private Scene formDataBaseScene;
  private Stage formStage;
  private FormDataBaseController formDataBaseController;

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
  /*/* Connection conn, conn02;
  Statement stmt;
  PreparedStatement pstmt;*/

  // pop-up messages
  Message message;

  // Tab text constants multilanguge
  private String usuario, materias, datosMaterias;

  // The value of the column escribir and traducir multilanguge
  private String escribir, traducir;

  // The actual tab
  private String currentTab;

  // The current and old node
  private Node currentNode, oldNode;

  // Active user and another...
  private String usuario_nombre, password, pregunta, respuesta;
  private int usuario_id;
  private boolean usuario_activo;
  private int usuario_IdEnviar;

  private String usuario_nombreEnMateria, materia_nombre, directorio;
  private int materia_id;
  private int materia_IdEnviar;

  private String materia_nombreEnIdioma, idioma_nombre;
  private int idioma_id;
  private String escritura, traduccion;

  private int datos_id;

  // un arraylist con los users sacados de la base de datos
  private List<Usuario> usuarioArrayList;
  private List<Materia> materiaArrayList;
  private List<Idioma> idiomaArrayList;

  // Resulset of the tableview
  ResultSet rsUsuario, rsMateria, rsIdioma, rsIdiomaSuma;

  private ObservableList<Usuario> usuarioObservableList;
  private ObservableList<Materia> materiaObservableList;
  private ObservableList<Idioma> idiomaObservableList;

  int indiceUsuario, indiceMateria, indiceIdioma;

  // If the user is root or not
  private boolean root;

  // For the bounle of idioms
  ResourceBundle resources;

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

      // References to mainStage
      mainStage = MainScene.getMainStage();

      // Create the locale for the pop up messages
      /*/*  = new Message(mainStage, resources); */

      // creating the path

      // The path is an absolute path (relarive to the initial instalation)    
      path = System.getProperty("user.dir");
      se = System.getProperty("file.separator");

      // Creating the initial database
      createDatabase(connect());
      //insertData(connect()); // Only for testing purpose


      // Creating multilingual constans
      Locale loc = new Locale(Locale.getDefault().getLanguage());
      ResourceBundle resourceBundle = ResourceBundle
              .getBundle("LanguageApp.resources.bundles.LanguageApp", loc);

      usuario = resourceBundle.getString("Usuario");
      materias = resourceBundle.getString("Materias");
      datosMaterias = resourceBundle.getString("Idiomas de las materias");
      escribir = resourceBundle.getString("Escribir");
      traducir = resourceBundle.getString("Traducir");

      // The initial tab
      currentTab = usuario;
      tabPanelDataBase.getSelectionModel().clearAndSelect(0);

      // Setting the current node
      currentNode = tabPanelDataBase;
      oldNode = tabPanelDataBase;
      tabPanelDataBase.requestFocus();

      // Resulset of the tableview
      rsUsuario = null;
      rsMateria = null;
      rsIdioma = null;

      // Setting TableView
      settingTableColumn();

      // Settiong the intial border
      setBorder(tabPanelDataBase);
    } catch (Exception e) {
      Message.showException(e);
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Connect">

  /**
   *
   * @return @throws java.lang.Exception
   */
  public Connection connect() throws Exception
   {
    /*/* conn = null; */
    Connection conn = null;
    // path to the database
    String url = "jdbc:sqlite:" + path + se + "database.db";
    conn = DriverManager.getConnection(url);
    conn.createStatement().execute("PRAGMA foreign_keys = ON");
    // if doesn't exit it's create the database
    Class.forName("org.sqlite.JDBC");
    return conn;
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="CreateDataBase">

  /**
   *
   * @param conn
   * @throws SQLException
   * @throws Exception
   */
  private void createDatabase(Connection conn) throws SQLException, Exception
   {

    Statement stmt = null;

    try {

      conn.setAutoCommit(false);
      sp = conn.setSavepoint("crear_database");

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
              "ON DELETE CASCADE ON UPDATE CASCADE" +
              ");";

      sql += "CREATE TABLE IF NOT EXISTS datos ( " +
              "datos_id INTEGER NOT NULL, " +
              "fk_idioma_id INTEGER NOT NULL, " +
              "indice INTEGER NOT NULL, " +
              "escribir TEXT NOT NULL, " +
              "traducir TEXT NOT NULL, " +
              "PRIMARY KEY(datos_id), " +
              "FOREIGN KEY (fk_idioma_id) REFERENCES idiomas(idioma_id) " +
              "ON DELETE CASCADE ON UPDATE CASCADE" +
              ");";

      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      conn.commit();

      // Creating the root user
      handleRegistro("root", "1234", true,
              "¿Cuál es tu comida favorita?", "1234");

    } catch (SQLException e) {
      conn.rollback(sp);
      throw new Exception(e);
    } finally {
      if (!isNull(conn)) {
        conn.close();
        stmt.close();
      }
    }
   }


  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="insertData">

  /**
   *
   * @param conn
   * @throws SQLException
   * @throws Exception
   */
  private void insertData(Connection conn) throws SQLException, Exception
   {

    Statement stmt = null;

    try {

      conn.setAutoCommit(false);
      sp = conn.setSavepoint("insertar_tablas");

      //<editor-fold defaultstate="collapsed" desc="data">

      String sql = "INSERT INTO main.usuarios " +
              "(usuario_nombre, password, usuario_activo, pregunta, respuesta) " +
              "VALUES ('2', '2222', '0', '¿Cuál es tu comida favorita?', '2');";
      sql += "INSERT INTO main.usuarios " +
              "(usuario_nombre, password, usuario_activo, pregunta, respuesta) " +
              "VALUES ('3', '3333', '0', '¿Cuál es tu comida favorita?', '3');";
      sql += "INSERT INTO main.usuarios " +
              "(usuario_nombre, password, usuario_activo, pregunta, respuesta) " +
              "VALUES ('4', '4444', '0', '¿Cuál es tu comida favorita?', '4');";

      sql += "INSERT INTO main.materias " +
              "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
              "VALUES ('1', 'Los_Vengadores_1.mp4', " +
              "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Los_Vengadores_1\\', '1');";
      sql += "INSERT INTO main.materias " +
              "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
              "VALUES ('1', 'Numb_Linkin_Park.mp4', " +
              "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Numb_Linkin_Park\\', '0');";
      sql += "INSERT INTO main.materias " +
              "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
              "VALUES ('2', 'Los_Vengadores_1.mp4', " +
              "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Los_Vengadores_1\\', '1');";
      sql += "INSERT INTO main.materias " +
              "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
              "VALUES ('2', 'Numb_Linkin_Park.mp4', " +
              "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Numb_Linkin_Park\\', '0');";
      sql += "INSERT INTO main.materias " +
              "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
              "VALUES ('3', 'Los_Vengadores_1.mp4', " +
              "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Los_Vengadores_1\\', '1');";
      sql += "INSERT INTO main.materias " +
              "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
              "VALUES ('3', 'Numb_Linkin_Park.mp4', " +
              "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Numb_Linkin_Park\\', '0');";
      sql += "INSERT INTO main.materias " +
              "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
              "VALUES ('4', 'Los_Vengadores_1.mp4', " +
              "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Los_Vengadores_1\\', '1');";
      sql += "INSERT INTO main.materias " +
              "(fk_usuario_id, materia_nombre, directorio, materia_activo) " +
              "VALUES ('4', 'Numb_Linkin_Park.mp4', " +
              "'D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp\\media\\Numb_Linkin_Park\\', '0');";

      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('1', 'English.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('1', 'Spanish.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('1', 'Japanese.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('1', 'Italian.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('1', 'French.json');";

      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('2', 'English.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('2', 'Spanish.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('2', 'Japanese.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('2', 'Italian.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('2', 'French.json');";

      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('3', 'English.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('3', 'Spanish.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('3', 'Japanese.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('3', 'Italian.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('3', 'French.json');";

      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('4', 'English.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('4', 'Spanish.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('4', 'Japanese.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('4', 'Italian.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('4', 'French.json');";

      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('5', 'English.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('5', 'Spanish.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('5', 'Japanese.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('5', 'Italian.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('5', 'French.json');";

      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('6', 'English.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('6', 'Spanish.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('6', 'Japanese.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('6', 'Italian.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('6', 'French.json');";


      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('7', 'English.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('7', 'Spanish.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('7', 'Japanese.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('7', 'Italian.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('7', 'French.json');";


      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('8', 'English.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('8', 'Spanish.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('8', 'Japanese.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('8', 'Italian.json');";
      sql += "INSERT INTO main.idiomas (fk_materia_id, idioma_nombre) " +
              "VALUES ('8', 'French.json');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('1', '1', '11', '12');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('1', '2', '33', '13');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('1', '3', '55', '52');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('2', '1', '1', '84');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('2', '2', '3', '73');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('2', '3', '5', '77');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('3', '1', '21', '33');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('3', '2', '23', '6');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('3', '3', '25', '88');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('4',  '1', '31', '47');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('4', '2', '33', '96');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('4', '3', '45', '4');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('5', '1', '41', '3');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('5', '2', '53', '8');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('5', '3', '55', '38');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('6', '1', '16', '68');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('6', '2', '37', '49');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('6', '3', '58', '66');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('7', '1', '91', '66');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('7', '2', '12', '99');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('7', '3', '53', '74');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('8', '1', '17', '47');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('8', '2', '93', '97');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('8', '3', '5', '66');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('9', '1', '1', '58');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('9', '2', '2', '68');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('9', '3', '3', '79');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('10', '1', '4', '97');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('10', '2', '5', '68');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('10', '3', '6', '57');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('11', '1', '7', '23');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('11', '2', '8', '34');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('11', '3', '9', '45');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('12', '1', '10', '56');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('12', '2', '11', '68');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('12', '3', '12', '79');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('13', '1', '13', '97');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('13', '2', '14', '86');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('13', '3', '51', '75');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('14', '1', '15', '64');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('14', '2', '13', '53');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('14', '3', '17', '42');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('15', '1', '18', '43');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('15', '2', '19', '31');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('15', '3', '20', '15');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('16', '1', '21', '26');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('16', '2', '22', '37');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('16', '3', '23', '48');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('17', '1', '24', '59');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('17', '2', '25', '5');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('17', '3', '26', '94');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('18', '1', '27', '73');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('18', '2', '28', '84');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('18', '3', '29', '63');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('19', '1', '30', '65');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('19', '2', '31', '6');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('19', '3', '32', '52');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('20', '1', '44', '53');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('20', '2', '33', '13');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('20', '3', '25', '4');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('21', '1', '36', '59');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('21', '2', '37', '58');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('21', '3', '38', '57');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('22', '1', '39', '56');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('22', '2', '40', '55');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('22', '3', '41', '54');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('23', '1', '42', '53');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('23', '2', '43', '52');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('23', '3', '44', '51');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('24', '1', '45', '50');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('24', '2', '46', '49');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('24', '3', '47', '48');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('25', '1', '4', '97');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('25', '2', '5', '68');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('25', '3', '6', '57');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('26', '1', '7', '23');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('26', '2', '8', '34');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('26', '3', '9', '45');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('27', '1', '10', '56');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('27', '2', '11', '68');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('27', '3', '12', '79');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('28', '1', '13', '97');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('28', '2', '14', '86');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('28', '3', '51', '75');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('29', '1', '15', '64');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('29', '2', '13', '53');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('29', '3', '17', '42');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('30', '1', '18', '43');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('30', '2', '19', '31');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('30', '3', '20', '15');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('31', '1', '21', '26');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('31', '2', '22', '37');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('31', '3', '23', '48');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('32', '1', '36', '59');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('32', '2', '37', '58');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('32', '3', '38', '57');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('33', '1', '24', '59');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('33', '2', '25', '5');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('33', '3', '26', '94');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('34', '1', '27', '73');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('34', '2', '28', '84');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('34', '3', '29', '63');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('35', '1', '30', '65');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('35', '2', '31', '6');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('35', '3', '32', '52');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('36', '1', '44', '53');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('36', '2', '38', '57');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('36', '3', '25', '4');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('37', '1', '36', '59');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('37', '2', '37', '58');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('37', '3', '38', '57');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('38', '1', '39', '56');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('38', '2', '40', '55');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('38', '3', '41', '54');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('39', '1', '42', '53');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('39', '2', '43', '52');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('39', '3', '44', '51');";

      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('40', '1', '45', '50');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('40', '2', '46', '49');";
      sql += "INSERT INTO main.datos (fk_idioma_id, indice, escribir, traducir) " +
              "VALUES ('40', '3', '47', '48');";

      //</editor-fold>

      sp = conn.setSavepoint("insertar_tablas");
      stmt = conn.createStatement();
      stmt.executeUpdate(sql);
      conn.commit();

      stmt.close();

    } catch (Exception e) {
      conn.rollback(sp);
      throw new Exception(e);
    } finally {
      conn.close();
    }
   }

//</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Handles">
  /**
   *
   * @return @throws java.sql.SQLException
   */
  public Pair<Integer, String> handleCheckNombre() throws SQLException, Exception
   {
    Connection conn = null;
    Statement stmt = null;
    int id = 0;
    String nombre = null;

    // Preparing statement

    String sql = "SELECT usuario_id, usuario_nombre, password FROM usuarios " +
            "WHERE usuario_activo = 1";
    // Try connection
    conn = connect();

    stmt = conn.createStatement();
    //
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
      id = rs.getInt("usuario_id");
      nombre = rs.getString("usuario_nombre");
      password = rs.getString("password");
    } else {
      id = 0;
    }

    stmt.close();
    conn.close();
    return new Pair(id, nombre);
   }


  /**
   *
   * @param usuarioId
   * @return
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  public Pair<Boolean, String> handleCheckMateriaActivo(int usuarioId)
          throws SQLException, Exception
   {
    Connection conn = null;
    PreparedStatement pstmt = null;
    usuario_id = 0;
    Pair<Boolean, String> pair = new Pair<>(false, null);

    // Preparing statement
    conn = connect();
    String sql = "SELECT m.directorio, m.materia_nombre, m.materia_activo FROM materias m " +
            "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
            "WHERE u.usuario_id = ? AND m.materia_activo = 1";

    conn.setAutoCommit(false);

    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, usuarioId);

    ResultSet rs = pstmt.executeQuery();
    conn.commit();

    while (rs.next()) {
      pair = new Pair<>(
              rs.getInt("materia_activo") == 1,
              rs.getString("directorio") + se +
              rs.getString("materia_nombre"));
    }

    pstmt.close();
    conn.close();

    return pair;
   }


  /**
   *
   * @param activoBoolean If only wants to delete, put it false
   * @param usuario_id
   * @throws java.sql.SQLException
   */
  public void handleBorrarMarcar(boolean activoBoolean, int usuario_id)
          throws SQLException, Exception
   {

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    // In SQLinte doesn't exit boolean
    usuario_activo = activoBoolean;

    // Try connection
    conn = connect();
    conn.setAutoCommit(false);

    String sql = "UPDATE usuarios SET usuario_activo = 0;";
    stmt = conn.createStatement();
    stmt.executeUpdate(sql);
    conn.commit();
    stmt.close();
    // set the value
    sql = "UPDATE usuarios SET usuario_activo = ? WHERE usuario_id = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, usuario_activo ? 1 : 0);
    pstmt.setInt(2, usuario_id);
    pstmt.executeUpdate();
    conn.commit();

    pstmt.close();
    conn.close();
   }


  /**
   *
   * @param usuarioString
   * @param passwordString
   * @return
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  public Pair<Integer, String> handleCheckUser(String usuarioString, String passwordString)
          throws SQLException, Exception
   {
    Connection conn = null;
    PreparedStatement pstmt = null;

    usuario_nombre = null;
    usuario_id = 0;

    // Preparing statement
    String sql = "SELECT usuario_id, usuario_nombre FROM usuarios WHERE usuario_nombre = ? and password = ?";

    // Try connection
    conn = connect();
    conn.setAutoCommit(false);

    // preparing statement
    pstmt = conn.prepareStatement(sql);

    // set the value
    pstmt.setString(1, usuarioString);
    pstmt.setString(2, passwordString);

    //
    ResultSet rs = pstmt.executeQuery();

    if (rs.next()) {
      usuario_id = rs.getInt("usuario_id");
      usuario_nombre = rs.getString("usuario_nombre");
    }
    conn.close();
    return new Pair(usuario_id, usuario_nombre);
   }


  /**
   * @param usuarioString
   * @param passwordString
   * @param activoBoolean
   * @param preguntaString
   * @param respuestaString
   * @return
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  public int handleRegistro(String usuarioString, String passwordString,
          boolean activoBoolean, String preguntaString,
          String respuestaString) throws SQLException, Exception
   {

    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = null;
    usuario_id = 0;
    Pair<Integer, String> usuarioPair;
    int result = 0;

    try {

      usuarioPair = handleCheckUser(usuarioString, passwordString);

      if (!isNull(usuarioPair.getValue())) {
        result = usuarioPair.getKey();
      } else {

        // I create the user
        conn = connect();
        conn.setAutoCommit(false);
        sp = conn.setSavepoint("Registration");

        // Preparing statement
        sql = "INSERT INTO usuarios (usuario_nombre, password, usuario_activo, pregunta, respuesta) " +
                 "VALUES (?,?,?,?,?)";
        pstmt = conn.prepareStatement(sql);
        // set the value
        pstmt.setString(1, usuarioString);
        pstmt.setString(2, passwordString);
        pstmt.setInt(3, activoBoolean ? 1 : 0);
        pstmt.setString(4, preguntaString);
        pstmt.setString(5, respuestaString);
        pstmt.executeUpdate();
        conn.commit();
      }

    } catch (Exception e) {
      conn.rollback(sp);
      throw new Exception(e);
    } finally {
      if (!isNull(conn)) {
        pstmt.close();
        conn.close();
      } else {
      }
    }
    return result;
   }


  /**
   *
   * @param usuarioString
   * @param passwordString
   * @param activoBoolean
   * @throws java.lang.Exception
   */
  public void handleRegistro02(String usuarioString, String passwordString, boolean activoBoolean)
          throws Exception
   {

    Pair<Integer, String> usuarioPair;

    // Read the usuario_id of the rec
    usuarioPair = handleCheckUser(usuarioString, passwordString);
    usuario_id = usuarioPair.getKey();
    usuario_nombre = usuarioPair.getValue();
    //put all the usuario_id to 0 except the usuario_id
    handleBorrarMarcar(activoBoolean, usuario_id);
    setUsuario_id(usuario_id);
    setUsuario_nombre(usuario_nombre);
   }


  /**
   *
   * @param usuarioString
   * @param passwordString
   * @param activoBoolean
   * @param preguntaString
   * @param respuestaString
   * @throws java.lang.Exception
   */
  public void handleRegistro03(String usuarioString, String passwordString,
          boolean activoBoolean, String preguntaString, String respuestaString)
          throws Exception
   {

    Pair<Integer, String> usuarioPair;

    // Updated in the tableview
    if (activoBoolean) {
      usuarioObservableList.forEach((u) -> {
        u.setUsuario_activo(false);
      });
    }
    // Read the usuario_id of the rec
    usuarioPair = handleCheckUser(usuarioString, passwordString);
    usuario_id = usuarioPair.getKey();
    usuario_nombre = usuarioPair.getValue();

    usuarioObservableList.add(new Usuario(usuario_id, usuarioString, passwordString, activoBoolean, preguntaString, respuestaString));
    usuarioTableView.refresh();

    // Updated in the database
    handleBorrarMarcar(activoBoolean, usuario_id);
   }


  /**
   *
   * @param usu
   * @return
   * @throws java.lang.Exception
   */
  public int handleUpdate(Usuario usu) throws Exception
   {

    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = null;

    try {

      //put all the usuario_id to 0 except the usuario_id
      usuario_id = usu.getUsuario_id();
      usuario_nombre = usu.getUsuario_nombre();
      handleBorrarMarcar(false, usuario_id);

      // I create the user
      conn = connect();
      conn.setAutoCommit(false);
      sp = conn.setSavepoint("handleUpdate");

      password = usu.getPassword();
      usuario_activo = usu.getUsuario_activo();
      pregunta = usu.getPregunta();
      respuesta = usu.getRespuesta();

      sql = "UPDATE usuarios SET usuario_nombre = ?, password = ?, usuario_activo = ?, pregunta = ?, respuesta = ? WHERE usuario_id = ? ";
      pstmt = conn.prepareStatement(sql);
      // set the value
      pstmt.setString(1, usuario_nombre);
      pstmt.setString(2, password);
      pstmt.setInt(3, usuario_activo ? 1 : 0);
      pstmt.setString(4, pregunta);
      pstmt.setString(5, respuesta);
      pstmt.setInt(6, usuario_id);
      pstmt.executeUpdate();
      conn.commit();
      pstmt.close();

      // Updated in the tableview
      if (usu.getUsuario_activo()) {
        usuarioObservableList.forEach((u) -> {
          u.setUsuario_activo(false);
        });
        usu.setUsuario_activo(true);
      }
      int index = usuarioObservableList.indexOf(usu);
      usuarioObservableList.set(index, usu);
      usuarioTableView.refresh();

    } catch (Exception e) {
      conn.rollback(sp);
      throw new Exception(e);
    } finally {
      conn.close();
    }
    return 0;
   }


  /**
   *
   * @param u
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  public void handleDelete(Usuario u) throws SQLException, Exception
   {

    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = null;

    try {

      //put all the usuario_id to 0 except the usuario_id
      usuario_id = u.getUsuario_id();

      conn = connect();
      conn.setAutoCommit(false);
      sp = conn.setSavepoint("handleUpdate");

      sql = "DELETE FROM usuarios WHERE usuario_id = ? ";
      pstmt = conn.prepareStatement(sql);
      // set the value
      pstmt.setInt(1, usuario_id);

      pstmt.executeUpdate();
      conn.commit();
      pstmt.close();

      usuarioObservableList.remove(u);
      usuarioTableView.refresh();
      handleCloseModal();

    } catch (Exception e) {
      conn.rollback(sp);
      throw new Exception(e);
    } finally {
      conn.close();
    }

   }


  /**
   *
   * @throws java.lang.Exception
   */
  public void handleCloseModal() throws Exception
   {
    Platform.runLater(() -> {
      try {
        FadeTransition fOut = new FadeTransition(Duration.millis(1000));
        fOut.setFromValue(1.0);
        fOut.setToValue(0.0);
        fOut.setNode((Node) formDataBaseView);
        fOut.setOnFinished((e) -> {
          formStage.close();
        });
        fOut.play();
        formStage.close();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
   }


  /**
   *
   * @param usuarioString
   * @param preguntaString
   * @param respuestaString
   * @return
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   */
  public String handleRecordar(String usuarioString, String preguntaString, String respuestaString) throws SQLException, Exception
   {

    Connection conn = null;
    PreparedStatement pstmt = null;
    String pass = null;

    // Preparing statement
    String sql = "SELECT password FROM usuarios WHERE usuario_nombre = ? and pregunta = ? and respuesta = ?";

    // Try connection
    conn = connect();
    conn.setAutoCommit(false);

    // preparing statement
    pstmt = conn.prepareStatement(sql);

    // set the value
    pstmt.setString(1, usuarioString);
    pstmt.setString(2, preguntaString);
    pstmt.setString(3, respuestaString);

    //
    ResultSet rs = pstmt.executeQuery();
    conn.commit();

    while (rs.next()) {
      pass = rs.getString(1);
      return pass;
    }

    pstmt.close();
    conn.close();
    return pass;
   }



  /**
   *
   * @param trans = The actual language
   * @param currentTab = Escribir o Traducir
   * @param indexItemV = Index of the listViewH
   * @param success = The score of the exercise
   * @return
   * @throws java.sql.SQLException
   * @throws java.lang.Exception
   */
  public Pair<String, String> handleUpdateCorrection(String trans, String currentTab,
          int indexItemV, double success) throws SQLException, Exception
   {
    Connection conn = null;
    PreparedStatement pstmt = null;
    escritura = "0.0";
    traduccion = "0.0";
    double escribirDouble = 0.0, traducirDouble = 0.0;
    String action = "insert";

    // Try connection
    conn = connect();
    conn.setAutoCommit(false);

    usuario_id = getUsuario_id();

    String sql = "SELECT m.materia_id, i.idioma_id FROM materias m " +
            "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
            "INNER JOIN idiomas i ON m.materia_id = i.fk_materia_id " +
            "WHERE u.usuario_id = ? AND m.materia_activo = 1 AND i.idioma_nombre = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, usuario_id);
    pstmt.setString(2, trans);
    ResultSet rs = pstmt.executeQuery();
    conn.commit();

    if (rs.next()) {
      materia_id = rs.getInt("materia_id");
      idioma_id = rs.getInt("idioma_id");
    }

    // I check if there is any equal record
    sql = "SELECT d.datos_id, d.escribir, d.traducir " +
            "FROM materias m " +
            "INNER JOIN idiomas i ON m.materia_id = i.fk_materia_id " +
            "INNER JOIN datos d ON i.idioma_id = d.fk_idioma_id " +
            "WHERE i.fk_materia_id = ? AND i.idioma_nombre = ? AND d.indice = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, materia_id);
    pstmt.setString(2, trans);
    pstmt.setInt(3, indexItemV);
    rs = pstmt.executeQuery();
    conn.commit();


    if (rs.next()) {
      action = "update";
      datos_id = rs.getInt("datos_id");
      escritura = rs.getString("escribir");
      traduccion = rs.getString("traducir");
      if (escritura == null) escritura = "0";
      if (traduccion == null) traduccion = "0";
    }

    escribirDouble = Double.parseDouble(escritura);
    traducirDouble = Double.parseDouble(traduccion);
    if (currentTab.equals(escribir))
      escribirDouble = (escribirDouble > success) ? escribirDouble : success;
    if (currentTab.equals(traducir))
      traducirDouble = (traducirDouble > success) ? traducirDouble : success;
    escritura = String.valueOf(escribirDouble);
    traduccion = String.valueOf(traducirDouble);

    if (action.equals("insert")) {
      sql = "INSERT INTO datos (fk_idioma_id, indice, escribir, traducir) VALUES (?,?,?,?)";
      pstmt = conn.prepareStatement(sql);
      // set the value
      pstmt.setInt(1, idioma_id);
      pstmt.setInt(2, indexItemV);
      pstmt.setString(3, escritura);
      pstmt.setString(4, traduccion);
      pstmt.executeUpdate();
      conn.commit();
    } else if (action.equals("update")) {
      sql = "UPDATE datos SET escribir = ?, traducir = ? WHERE datos_id = ?";
      pstmt = conn.prepareStatement(sql);
      // set the value
      pstmt.setString(1, escritura);
      pstmt.setString(2, traduccion);
      pstmt.setInt(3, datos_id);
      pstmt.executeUpdate();
      conn.commit();
    }

    pstmt.close();
    conn.close();
    return new Pair<>(escritura, traduccion);
   }
  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="FormDataBase">

  //<editor-fold defaultstate="collapsed" desc="Setting initial formDatabase and events">

  /**
   *
   * @throws Exception
   */
  private void settingTableColumn() throws Exception
   {
    // Events of the tableview
    settingUsuarioEvent();
    settingMateriaEvent();
    settingIdiomaEvent();

    // Setting and events of the tableColumns and cells
    usuario_idTableColumn.setCellValueFactory(new PropertyValueFactory<>("usuario_id"));
    setTableColumnUser(usuario_nombreTableColumn, "usuario_nombre");
    setTableColumnUser(passwordTableColumn, "password");
    setTableColumnUser(usuario_activoTableColumn, "usuario_activo");
    setTableColumnUser(preguntaTableColumn, "pregunta");
    setTableColumnUser(respuestaTableColumn, "respuesta");

    materia_idTableColumn.setCellValueFactory(new PropertyValueFactory<>("materia_id"));
    setTableColumnSubjects(fk_usuario_nombreTableColumn, "usuario_nombre");
    setTableColumnSubjects(materia_nombreTableColumn, "materia_nombre");
    setTableColumnSubjects(directorioTableColumn, "directorio");

    idioma_idTableColumn.setCellValueFactory(new PropertyValueFactory<>("idioma_id"));
    setTableColumnIdioma(subject_nombreTableColumn, "materia_nombre");
    setTableColumnIdioma(idiomaTableColumn, "idioma_nombre");
    setTableColumnIdioma(escribirTableColumn, "escribir");
    setTableColumnIdioma(traducirTableColumn, "traducir");
   }


  /**
   *
   * @param event
   * @throws IOException
   * @throws MalformedURLException
   */
  private void setTableColumnUser(TableColumn<?, ?> tc, String valueFactory)
          throws IOException, MalformedURLException, Exception
   {

    if (!tc.getText().equals(toLocale("Usuario Activo"))) {
      TableColumn<Usuario, String> tableColumn = (TableColumn<Usuario, String>) tc;
      tableColumn.setEditable(false);
      tableColumn.setCellValueFactory(new PropertyValueFactory<>(valueFactory));

    } else {
      TableColumn<Usuario, Boolean> tableColumn = (TableColumn<Usuario, Boolean>) tc;
      tableColumn.setEditable(false);
      tableColumn.setCellValueFactory(new PropertyValueFactory<>(valueFactory));


      //tableColumn.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn));

      tableColumn.setCellFactory(p -> {
        CheckBox checkBox = new CheckBox();

        TableCell<Usuario, Boolean> tableCell = new TableCell<Usuario, Boolean>()
         {
          @Override
          protected void updateItem(Boolean item, boolean empty)
           {
            super.updateItem(item, empty);
            if (empty || item == null)
              setGraphic(null);
            else {
              setGraphic(checkBox);
              checkBox.setSelected(item);
            }
           }

         };

        checkBox.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
          if (event.getClickCount() <= 1) {
            try {

              Usuario usu = (Usuario) tableCell.getTableRow().getItem();
              usu.setUsuario_activo(!checkBox.isSelected());
              handleUpdate(usu);
              event.consume();
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }
        });

        checkBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
          try {
            if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
              Usuario usu = (Usuario) tableCell.getTableRow().getItem();
              usu.setUsuario_activo(!checkBox.isSelected());
              handleUpdate(usu);
              event.consume();
            }
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });

        tableCell.setAlignment(Pos.CENTER);
        tableCell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        return tableCell;
      });
    }
   }


  /**
   *
   * @param event
   * @throws IOException
   * @throws MalformedURLException
   */
  private void setTableColumnSubjects(TableColumn<Materia, String> tc, String valueFactory) throws IOException, MalformedURLException
   {

    TableColumn<Materia, String> tableColumn = (TableColumn<Materia, String>) tc;
    tableColumn.setEditable(true);
    tableColumn.setCellValueFactory(new PropertyValueFactory<>(valueFactory));
   }


  /**
   *
   * @param event
   * @throws IOException
   * @throws MalformedURLException
   */
  private void setTableColumnIdioma(TableColumn<?, ?> tc, String valueFactory) throws IOException, MalformedURLException
   {

    if (!tc.getText().equals(toLocale("Escribir")) &&
            (!tc.getText().equals(toLocale("Traducir")))) {
      TableColumn<Idioma, String> tableColumn = (TableColumn<Idioma, String>) tc;
      tableColumn.setEditable(true);
      tableColumn.setCellValueFactory(new PropertyValueFactory<>(valueFactory));
    } else {
      TableColumn<Idioma, Integer> tableColumn = (TableColumn<Idioma, Integer>) tc;
      tableColumn.setEditable(true);
      tableColumn.setCellValueFactory(new PropertyValueFactory<>(valueFactory));
    }
   }


  /**
   *
   */
  private void settingUsuarioEvent() throws Exception
   {
    // Events Key
    usuarioTableView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

      try {
        indiceUsuario = usuarioTableView.getSelectionModel().getSelectedIndex();
        KeyCode k = event.getCode();

        if (k == KeyCode.DOWN ||
                (k == KeyCode.TAB && !event.isShiftDown()) &&
                indiceUsuario < usuarioTableView.getItems().size()) {
          indiceUsuario++;
        }

        if ((k == KeyCode.UP ||
                k == KeyCode.TAB && event.isShiftDown()) &&
                indiceUsuario > 0) {
          indiceUsuario--;
        }

        if (k == KeyCode.ENTER || k == KeyCode.SPACE) {
          Usuario u = usuarioTableView.getSelectionModel().getSelectedItem();
          showModalForm(u);
        }
        usuarioTableView.getSelectionModel().clearAndSelect(indiceUsuario);
        usuario_IdEnviar = usuarioTableView.getSelectionModel().getSelectedItem().getUsuario_id();
        settingTableViewMateria();
        event.consume();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    // Events Mouse
    usuarioTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
      try {
        Usuario u = usuarioTableView.getSelectionModel().getSelectedItem();
        if (event.getClickCount() >= 2) {
          showModalForm(u);
        }

        usuario_IdEnviar = u.getUsuario_id();
        settingTableViewMateria();
        event.consume();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
   }


  /**
   *
   */
  private void settingMateriaEvent() throws Exception
   {
    // Events Key
    materiasTableView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      try {

        indiceMateria = materiasTableView.getSelectionModel().getSelectedIndex();

        if ((event.getCode() == KeyCode.TAB && !event.isShiftDown()) ||
                (event.getCode() == KeyCode.DOWN)) {
          int indexLast = materiasTableView.getItems().size();
          if (indiceMateria < indexLast) indiceMateria++;
          materiasTableView.getSelectionModel().clearAndSelect(indiceMateria);
        }
        if ((event.getCode() == KeyCode.TAB && event.isShiftDown()) ||
                (event.getCode() == KeyCode.UP)) {
          if (indiceMateria > 0) indiceMateria--;
          materiasTableView.getSelectionModel().clearAndSelect(indiceMateria);
        }
        event.consume();
        materia_IdEnviar = materiasTableView.getSelectionModel().getSelectedItem().getMateria_id();
        settingTableViewIdioma();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    // Events Mouse
    materiasTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
      try {
        materia_IdEnviar = materiasTableView.getSelectionModel().getSelectedItem().getMateria_id();
        settingTableViewIdioma();
        event.consume();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
   }


  /**
   *
   */
  private void settingIdiomaEvent() throws Exception
   {
    // Events Key
    idiomasTableView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

      indiceIdioma = idiomasTableView.getSelectionModel().getSelectedIndex();

      if ((event.getCode() == KeyCode.TAB && !event.isShiftDown()) ||
              (event.getCode() == KeyCode.DOWN)) {
        int indexLast = idiomasTableView.getItems().size();
        if (indiceIdioma < indexLast) indiceIdioma++;
        idiomasTableView.getSelectionModel().clearAndSelect(indiceIdioma);
        event.consume();
      }
      if ((event.getCode() == KeyCode.TAB && event.isShiftDown()) ||
              (event.getCode() == KeyCode.UP)) {
        if (indiceIdioma > 0) indiceIdioma--;
        idiomasTableView.getSelectionModel().clearAndSelect(indiceIdioma);
        event.consume();
      }
    });
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Show tableview with actual data">

  /**
   *
   */
  public void actualizarUsuario() throws Exception
   {
    // A TableColumn must have a cell value factory set on it. 
    // The cell value factory extracts the value to be displayed in each cell (on each row) in the column.
    // The PropertyValueFactory factory can extract a property value (field value) from a Java object. 
    // The name of the property is passed as a parameter to the PropertyValueFactory constructor.
    // The property name "usuario_nombre" will match the getter method getUsuario_nombre of 
    // the Usuario objects which contain the values are displayed on each row. 

    Object nombre = handleCheckNombre().getValue();
    Object id = handleCheckNombre().getKey();
    indiceUsuario = 0;

    usuario_nombre = (nombre != null) ? nombre.toString() : null;
    usuario_id = (id != null) ? (Integer) id : 0;

    usuarioArrayList = new ArrayList();
    materiaArrayList = new ArrayList();
    idiomaArrayList = new ArrayList();
    if (usuario_id == 0 || usuario_nombre == null) return;

    // placeholder
    usuarioTableView.setPlaceholder(new Label(resources
            .getString("No hay información para mostrar")));
    materiasTableView.setPlaceholder(new Label(resources
            .getString("No hay información para mostrar")));
    idiomasTableView.setPlaceholder(new Label(resources
            .getString("No hay información para mostrar")));

    // editable
    usuarioTableView.setEditable(true);
    usuario_idTableColumn.setEditable(false);
    materiasTableView.setEditable(true);
    materia_idTableColumn.setEditable(false);
    idiomasTableView.setEditable(true);
    idioma_idTableColumn.setEditable(false);

    // Selection mode
    usuarioTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    materiasTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    idiomasTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    // Creating a Arraylist with the usuario/usuarios of the database --------
    if (usuario_id > 0) {
      settingTableViewUsuario();
      /*/* settingTableColumn(); */
    }
   }


  /**
   *
   * @throws SQLException
   */
  private void settingTableViewUsuario() throws Exception
   {

    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    String sql = null;
    usuario_IdEnviar = 0;
    usuarioArrayList.clear();
    rsUsuario = null;
    // Setting the tableview Usuario

    conn = connect();
    conn.setAutoCommit(false);

    if (usuario_nombre.equals("root") &&
            (password.equals("1234"))) {
      root = true;
      sql = "SELECT * FROM usuarios";
      stmt = conn.createStatement();
      rsUsuario = stmt.executeQuery(sql);

    } else {
      root = false;
      sql = "SELECT * FROM usuarios WHERE usuario_id = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, usuario_id);
      rsUsuario = pstmt.executeQuery();
      usuario_id = mainScene.getUsuario_id();
      mainScene.setUsuario_id(usuario_id);
    }
    while (rsUsuario.next()) {
      usuario_id = rsUsuario.getInt("usuario_id");
      usuario_nombre = rsUsuario.getString("usuario_nombre");
      password = rsUsuario.getString("password");
      usuario_activo = rsUsuario.getInt("usuario_activo") == 1;
      pregunta = rsUsuario.getString("pregunta");
      respuesta = rsUsuario.getString("respuesta");

      usuarioArrayList.add(new Usuario(usuario_id, usuario_nombre, password,
              usuario_activo, pregunta, respuesta));

    }

    // Display row data -------------------
    usuarioObservableList = FXCollections.observableArrayList(usuarioArrayList);
    usuarioTableView.setItems(usuarioObservableList);

    // Setting the initial row
    usuarioTableView.requestFocus();
    usuarioTableView.getSelectionModel().clearAndSelect(0);
    usuarioTableView.scrollTo(0);

    usuario_IdEnviar = usuarioTableView.getSelectionModel().getSelectedItem().getUsuario_id();

    // Change settingTableViewMateria()
    settingTableViewMateria();

    conn.close();
   }


  /**
   *
   * @throws SQLException
   */
  private void settingTableViewMateria() throws Exception
   {
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = null;
    materia_IdEnviar = 0;
    materiaArrayList.clear();

    // Setting the tableview Materias
    conn = connect();
    conn.setAutoCommit(false);

    sql = "SELECT u.usuario_nombre, m.materia_id, m.fk_usuario_id, " +
            "m.materia_nombre, m.directorio " +
            "FROM usuarios u " +
            "INNER JOIN materias m on u.usuario_id = m.fk_usuario_id " +
            "WHERE m.fk_usuario_id = ?";
    pstmt = conn.prepareStatement(sql);
    pstmt.setInt(1, usuario_IdEnviar);
    rsMateria = pstmt.executeQuery();

    while (rsMateria.next()) {
      usuario_nombreEnMateria = rsMateria.getString("usuario_nombre");
      materia_id = rsMateria.getInt("materia_id");
      //fk_usuario_id = rsMateria.getInt("fk_usuario_id");
      materia_nombre = rsMateria.getString("materia_nombre");
      directorio = rsMateria.getString("directorio");

      materiaArrayList.add(new Materia(materia_id, usuario_nombreEnMateria,
              materia_nombre, directorio));
    }

    // Display row data -------------------
    materiaObservableList = FXCollections.observableArrayList(materiaArrayList);
    materiasTableView.setItems(materiaObservableList);

    if (!materiaArrayList.isEmpty()) {

      // Setting the initial row
      materiasTableView.requestFocus();
      materiasTableView.getSelectionModel().clearAndSelect(0);
      materiasTableView.scrollTo(0);
      materia_IdEnviar = materiasTableView.getSelectionModel().getSelectedItem().getMateria_id();
    }

    settingTableViewIdioma();

    conn.close();
   }


  /**
   *
   * @throws SQLException
   */
  private void settingTableViewIdioma() throws Exception
   {
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = null;
    idiomaArrayList.clear();

    try {

      // Setting the tableview Materias
      conn = connect();
      conn.setAutoCommit(false);
      sql = "SELECT m.materia_nombre, i.idioma_id, i.idioma_nombre " +
              "FROM materias m " +
              "INNER JOIN idiomas i on m.materia_id = i.fk_materia_id " +
              "WHERE i.fk_materia_id = ? " +
              "GROUP BY i.idioma_id";

      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, materia_IdEnviar);
      rsIdioma = pstmt.executeQuery();

      while (rsIdioma.next()) {
        idioma_id = rsIdioma.getInt("idioma_id");
        materia_nombreEnIdioma = rsIdioma.getString("materia_nombre");
        idioma_nombre = rsIdioma.getString("idioma_nombre");

        sql = "SELECT SUM(d.escribir) / COUNT(d.escribir) as escribir, " +
                "SUM(d.traducir) / COUNT(d.traducir) as traducir " +
                "FROM datos d " +
                "WHERE d.fk_idioma_id = ? ";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, idioma_id);
        rsIdiomaSuma = pstmt.executeQuery();
        if (rsIdiomaSuma.next()) {

          escritura = rsIdiomaSuma.getString("escribir");
          traduccion = rsIdiomaSuma.getString("traducir");
          if (escritura == null) escritura = "0.0";
          if (traduccion == null) traduccion = "0.0";
          escritura = escritura.concat(" %");
          traduccion = traduccion.concat(" %");
          idiomaArrayList.add(new Idioma(idioma_id, materia_nombreEnIdioma, idioma_nombre,
                  escritura, traduccion));
        }
      }


      // Display row data -------------------
      idiomaObservableList = FXCollections.observableArrayList(idiomaArrayList);
      idiomasTableView.setItems(idiomaObservableList);

      if (!idiomaArrayList.isEmpty()) {

        // Setting the initial row
        idiomasTableView.requestFocus();
        idiomasTableView.getSelectionModel().clearAndSelect(0);
        idiomasTableView.scrollTo(0);
      }
    } catch (Exception e) {
      conn.rollback(sp);
      throw new Exception(e);
    } finally {
      conn.close();
    }
   }


  //</editor-fold>

  /**
   *
   * @param u
   * @param indexBorder
   */
  private void showModalForm(Usuario u) throws Exception
   {

    URL urlFXML = new URL(getClass().getClassLoader()
            .getResource("LanguageApp/view/FormDataBaseView.fxml").toExternalForm());
    FXMLLoader loader = new FXMLLoader(urlFXML, resources);
    formDataBaseView = (AnchorPane) loader.load();
    formDataBaseScene = new Scene(formDataBaseView);
    formDataBaseController = loader.getController();
    formDataBaseController.setMainScene(mainScene);

    formDataBaseController.setRowIntoModal(u, root);

    formStage = new Stage();
    formStage.setScene(formDataBaseScene);
    formStage.initOwner(mainStage);
    formStage.initModality(Modality.APPLICATION_MODAL);

    Image icon = new Image(getClass().getClassLoader()
            .getResourceAsStream("LanguageApp/resources/images/languages_128.png"));
    formStage.getIcons().add(icon);
    formStage.setTitle(toLocale("Modificar la base de datos"));

    // Adding dark style
    JMetro jMetro = new JMetro(Style.DARK);
    jMetro.setScene(formDataBaseScene);

    formStage.setOnCloseRequest(e -> {
      e.consume();
      usuarioTableView.refresh();
      formStage.close();
    });

    formStage.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent key) -> {
      if (key.getCode() == KeyCode.ESCAPE) {
        try {
          handleCloseModal();
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    });
    formStage.showAndWait();
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Setting the borders">

  /**
   * Setting the border (cursor) of the node
   *
   * @param n the node to put the border
   */
  private void setBorder(Node n) throws Exception
   {
    currentNode.getStyleClass().removeAll(Collections.singleton("borderVisible"));
    n.getStyleClass().add("borderVisible");
    oldNode = currentNode;
    currentNode = n;

   }

  //</editor-fold> 

 }
