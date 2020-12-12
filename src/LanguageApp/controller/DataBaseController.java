package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import static LanguageApp.main.MainScene.setUsuario_id;
import static LanguageApp.main.MainScene.getUsuario_id;
import static LanguageApp.main.MainScene.setUsuario_nombre;
import static LanguageApp.main.MainScene.getUsuario_nombre;
import LanguageApp.model.Idioma;
import LanguageApp.model.Materia;
import LanguageApp.model.Usuario;
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
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
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
  @FXML private Tab tabUsuario;
  @FXML private Tab tabMaterias;

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
  Connection conn01, conn02;
  Statement stmt;

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

    // Create the locale for the pop up messages
    message = new Message(resources);

    // creating the path

    // The path is an absolute path (relarive to the initial instalation)    
    path = System.getProperty("user.dir");
    se = System.getProperty("file.separator");

    //Global varibles
    conn01 = null;
    stmt = null;

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
    tabPanelDataBase.getSelectionModel().select(0);

    // Setting the current node
    currentNode = tabPanelDataBase;
    oldNode = tabPanelDataBase;
    tabPanelDataBase.requestFocus();

    // Resulset of the tableview
    rsUsuario = null;
    rsMateria = null;
    rsIdioma = null;

    // Settiong the intial border
    setBorder(tabPanelDataBase);

   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Connect">

  /**
   *
   * @return
   */
  public Connection connect()
   {
    conn01 = null;

    try {
      // path to the database
      String url = "jdbc:sqlite:" + path + se + "database.db";
      conn01 = DriverManager.getConnection(url);
      conn01.createStatement().execute("PRAGMA foreign_keys = ON");
      // if doesn't exit it's create the database
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException | SQLException e) {
      message.message(Alert.AlertType.ERROR, 
              "Error message", "DataBaseController / connect()", e.toString(), e);
    }
    return conn01;
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="CreateDataBase">

  /**
   * Create a new database if doesn't exit
   */
  private void createDatabase(Connection conn)
   {

    stmt = null;

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

      stmt.close();
      conn.close();

      // Creating the root user
      handleRegistro("root", "1234", true,
              "¿Cuál es tu comida favorita?", "1234");

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
  private void insertData(Connection conn)
   {

    stmt = null;

    try {

      conn.setAutoCommit(false);
      sp = conn.setSavepoint("insertar_tablas");

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

  //<editor-fold defaultstate="collapsed" desc="Handles">

  /**
   *
   * @return
   */
  public Pair<Integer, String> handleCheckNombre()
   {
    conn01 = null;
    stmt = null;
    usuario_id = 0;
    usuario_nombre = null;
    password = null;

    // Preparing statement
    try {
      String sql = "SELECT usuario_id, usuario_nombre, password FROM usuarios WHERE usuario_activo = 1";
      // Try connection
      conn01 = connect();

      stmt = conn01.createStatement();
      //
      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) {
        usuario_id = rs.getInt("usuario_id");
        usuario_nombre = rs.getString("usuario_nombre");
        password = rs.getString("password");
      } else {
        usuario_id = 0;
      }

      stmt.close();
      conn01.close();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / handleCheckNombre()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / handleCheckNombre()", e.toString(), e);
      }
    }
    return new Pair(usuario_id, usuario_nombre);
   }


  /**
   *
   * @param usuarioId
   * @return
   */
  public boolean handleCheckMateriaActivo(int usuarioId)
   {
    conn02 = null;
    PreparedStatement pstmt = null;
    usuario_id = 0;
    boolean result = false;

    // Preparing statement
    try {
      String sql = "SELECT m.materia_activo FROM materias m " +
              "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
              "WHERE u.usuario_id = ?";

      // Try connection

      // path to the database
      String url = "jdbc:sqlite:" + path + se + "database.db";
      conn02 = DriverManager.getConnection(url);
      conn02.createStatement().execute("PRAGMA foreign_keys = ON");
      // if doesn't exit it's create the database
      Class.forName("org.sqlite.JDBC");

      conn02.setAutoCommit(false);

      pstmt = conn02.prepareStatement(sql);
      pstmt.setInt(1, usuarioId);

      ResultSet rs = pstmt.executeQuery();
      conn02.commit();

      while (rs.next()) {
        if (rs.getInt("materia_activo") == 1) result = true;
      }

      pstmt.close();
      conn02.close();

      
    } catch (Exception e) {
      Platform.runLater(() -> {
        message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / handleCheckNombre()", e.toString(), e);
      });
    } finally {
      try {
        if (conn02 != null) {
          conn02.close();
        }
      } catch (Exception e) {
        Platform.runLater(() -> {
          message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / handleCheckNombre()", e.toString(), e);
        });
      }
    }
    return result;
   }


  /**
   *
   * @param activoBoolean If only wants to delete, put it false
   * @param usuario_id
   */
  public void handleBorrarMarcar(boolean activoBoolean, int usuario_id)
   {

    conn01 = null;
    stmt = null;
    PreparedStatement pstmt = null;

    // In SQLinte doesn't exit boolean
    usuario_activo = activoBoolean;

    try {
      // Try connection
      conn01 = connect();
      conn01.setAutoCommit(false);

      String sql = "UPDATE usuarios SET usuario_activo = 0;";
      stmt = conn01.createStatement();
      stmt.executeUpdate(sql);
      conn01.commit();
      stmt.close();
      // set the value
      sql = "UPDATE usuarios SET usuario_activo = ? WHERE usuario_id = ?";
      pstmt = conn01.prepareStatement(sql);
      pstmt.setInt(1, usuario_activo ? 1 : 0);
      pstmt.setInt(2, usuario_id);
      pstmt.executeUpdate();
      conn01.commit();
      pstmt.close();
      conn01.close();

    } catch (Exception e) {
      Platform.runLater(() -> {
        message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / handlBorrarMarcar()", e.toString(), e);
      });
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "DataBaseController / handlBorrarMarcar()", e.toString(), e);
      }
    }
   }


  /**
   *
   * @param usuarioString
   * @param passwordString
   * @return
   */
  public Pair<Integer, String> handleCheckUser(String usuarioString, String passwordString)
   {
    conn01 = null;
    PreparedStatement pstmt = null;

    usuario_nombre = null;
    usuario_id = 0;

    // Preparing statement
    String sql = "SELECT usuario_id, usuario_nombre FROM usuarios WHERE usuario_nombre = ? and password = ?";
    try {
      // Try connection
      conn01 = connect();
      conn01.setAutoCommit(false);

      // preparing statement
      pstmt = conn01.prepareStatement(sql);

      // set the value
      pstmt.setString(1, usuarioString);
      pstmt.setString(2, passwordString);

      //
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        usuario_id = rs.getInt("usuario_id");
        usuario_nombre = rs.getString("usuario_nombre");
      }

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleCheckUser()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (SQLException e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleCheckUser()", e.toString(), e);
      }
    }
    return new Pair(usuario_id, usuario_nombre);
   }


  /**
   * @param usuarioString
   * @param passwordString
   * @param activoBoolean
   * @param preguntaString
   * @param respuestaString
   * @return
   */
  public int handleRegistro(String usuarioString, String passwordString, boolean activoBoolean, String preguntaString, String respuestaString)
   {

    conn01 = null;
    PreparedStatement pstmt = null;
    stmt = null;
    String sql = null;
    usuario_id = 0;
    Pair<Integer, String> usuarioPair;

    try {

      usuarioPair = handleCheckUser(usuarioString, passwordString);
      if ((String) usuarioPair.getValue() != null) {
        return usuarioPair.getKey();
      }

      // I create the user
      conn01 = connect();
      conn01.setAutoCommit(false);
      sp = conn01.setSavepoint("Registration");

      // Preparing statement
      sql = "INSERT INTO usuarios (usuario_nombre, password, usuario_activo, pregunta, respuesta) " + "VALUES (?,?,?,?,?)";
      pstmt = conn01.prepareStatement(sql);
      // set the value
      pstmt.setString(1, usuarioString);
      pstmt.setString(2, passwordString);
      pstmt.setInt(3, activoBoolean ? 1 : 0);
      pstmt.setString(4, preguntaString);
      pstmt.setString(5, respuestaString);
      pstmt.executeUpdate();
      conn01.commit();
      pstmt.close();

    } catch (Exception e) {
      try {
        conn01.rollback(sp);
      } catch (SQLException ex) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", ex.toString(), ex);
      }
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
      }
    }
    return 0;
   }


  /**
   *
   * @param usuarioString
   * @param passwordString
   * @param activoBoolean
   * @param preguntaString
   * @param respuestaString
   */
  public void handleRegistro02(String usuarioString, String passwordString, boolean activoBoolean)
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
   */
  public void handleRegistro03(String usuarioString, String passwordString, boolean activoBoolean, String preguntaString, String respuestaString)
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
   * @param usuarios
   * @return
   */
  public int handleUpdate(Usuario usu)
   {

    conn01 = null;
    PreparedStatement pstmt = null;
    stmt = null;
    String sql = null;


    try {

      //put all the usuario_id to 0 except the usuario_id
      usuario_id = usu.getUsuario_id();
      usuario_nombre = usu.getUsuario_nombre();
      handleBorrarMarcar(false, usuario_id);

      // I create the user
      conn01 = connect();
      conn01.setAutoCommit(false);
      sp = conn01.setSavepoint("handleUpdate");

      password = usu.getPassword();
      usuario_activo = usu.getUsuario_activo();
      pregunta = usu.getPregunta();
      respuesta = usu.getRespuesta();

      sql = "UPDATE usuarios SET usuario_nombre = ?, password = ?, usuario_activo = ?, pregunta = ?, respuesta = ? WHERE usuario_id = ? ";
      pstmt = conn01.prepareStatement(sql);
      // set the value
      pstmt.setString(1, usuario_nombre);
      pstmt.setString(2, password);
      pstmt.setInt(3, usuario_activo ? 1 : 0);
      pstmt.setString(4, pregunta);
      pstmt.setString(5, respuesta);
      pstmt.setInt(6, usuario_id);
      pstmt.executeUpdate();
      conn01.commit();
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
      try {
        conn01.rollback(sp);
      } catch (SQLException ex) {
        message.message(Alert.AlertType.ERROR, "Error message", "Database / handleUpdate()", ex.toString(), ex);
      }
      message.message(Alert.AlertType.ERROR, "Error message", "Database / handleUpdate()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "Database / handleUpdate()", e.toString(), e);
      }
    }
    return 0;
   }


  /**
   *
   * @param u
   * @param usuarios
   */
  public void handleDelete(Usuario u)
   {

    conn01 = null;
    PreparedStatement pstmt = null;
    stmt = null;
    String sql = null;

    try {

      //put all the usuario_id to 0 except the usuario_id
      usuario_id = u.getUsuario_id();

      conn01 = connect();
      conn01.setAutoCommit(false);
      sp = conn01.setSavepoint("handleUpdate");

      sql = "DELETE FROM usuarios WHERE usuario_id = ? ";
      pstmt = conn01.prepareStatement(sql);
      // set the value
      pstmt.setInt(1, usuario_id);

      pstmt.executeUpdate();
      conn01.commit();
      pstmt.close();

      usuarioObservableList.remove(u);
      usuarioTableView.refresh();
      handleCloseModal();

    } catch (Exception e) {
      try {
        conn01.rollback(sp);
      } catch (SQLException ex) {
        message.message(Alert.AlertType.ERROR, "Error message", "Database / handleDelete()", ex.toString(), ex);
      }
      message.message(Alert.AlertType.ERROR, "Error message", "Database / handleDelete()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "Database / handleDelete()", e.toString(), e);
      }
    }
   }


  /**
   *
   */
  public void handleCloseModal()
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
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
   }


  /**
   *
   * @param usuarioString
   * @param preguntaString
   * @param respuestaString
   * @return
   */
  public String handleRecordar(String usuarioString, String preguntaString, String respuestaString)
   {

    conn01 = null;
    PreparedStatement pstmt = null;
    String password = null;

    // Preparing statement
    String sql = "SELECT password FROM usuarios WHERE usuario_nombre = ? and pregunta = ? and respuesta = ?";
    try {
      // Try connection
      conn01 = connect();
      conn01.setAutoCommit(false);

      // preparing statement
      pstmt = conn01.prepareStatement(sql);

      // set the value
      pstmt.setString(1, usuarioString);
      pstmt.setString(2, preguntaString);
      pstmt.setString(3, respuestaString);

      //
      ResultSet rs = pstmt.executeQuery();
      conn01.commit();

      while (rs.next()) {
        password = rs.getString(1);
        return password;
      }

      pstmt.close();
      conn01.close();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "MainScene / handleRegistro()", e.toString(), e);
      }
    }
    return password;
   }


  /**
   *
   * @param trans = The actual language
   * @param currentTab = Escribir o Traducir
   * @param indexItemV = Index of the listViewH
   * @param success = The score of the exercise
   * @return
   */
  public Pair<String, String> handleUpdateCorrection(String trans, String currentTab, int indexItemV, double success)
   {
    conn01 = null;
    PreparedStatement pstmt = null;
    escritura = "0.0";
    traduccion = "0.0";
    double escribirDouble = 0.0, traducirDouble = 0.0;
    String action = "insert";

    // Preparing statement
    try {
      // Try connection
      conn01 = connect();
      conn01.setAutoCommit(false);

      usuario_id = getUsuario_id();

      String sql = "SELECT m.materia_id, i.idioma_id FROM materias m " +
              "INNER JOIN usuarios u ON u.usuario_id = m.fk_usuario_id " +
              "INNER JOIN idiomas i ON m.materia_id = i.fk_materia_id " +
              "WHERE u.usuario_id = ? AND m.materia_activo = 1 AND i.idioma_nombre = ?";
      pstmt = conn01.prepareStatement(sql);
      pstmt.setInt(1, usuario_id);
      pstmt.setString(2, trans);
      ResultSet rs = pstmt.executeQuery();
      conn01.commit();

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
      pstmt = conn01.prepareStatement(sql);
      pstmt.setInt(1, materia_id);
      pstmt.setString(2, trans);
      pstmt.setInt(3, indexItemV);
      rs = pstmt.executeQuery();
      conn01.commit();


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
        pstmt = conn01.prepareStatement(sql);
        // set the value
        pstmt.setInt(1, idioma_id);
        pstmt.setInt(2, indexItemV);
        pstmt.setString(3, escritura);
        pstmt.setString(4, traduccion);
        pstmt.executeUpdate();
        conn01.commit();
      } else if (action.equals("update")) {
        sql = "UPDATE datos SET escribir = ?, traducir = ? WHERE datos_id = ?";
        pstmt = conn01.prepareStatement(sql);
        // set the value
        pstmt.setString(1, escritura);
        pstmt.setString(2, traduccion);
        pstmt.setInt(3, datos_id);
        pstmt.executeUpdate();
        conn01.commit();
      }

      pstmt.close();
      conn01.close();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message",
              "DataBaseCotroller / handleUpdateCorrection()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message",
                "DataBaseCotroller / handleUpdateCorrection()", e.toString(), e);
      }
    }
    return new Pair<String, String>(escritura, traduccion);
   }


  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="FormDataBase">

  /**
   *
   */
  public void mostrarUsuario()
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
    
    if (usuario_id == 0 || usuario_nombre == null) return;
    usuarioArrayList = new ArrayList();
    materiaArrayList = new ArrayList();
    idiomaArrayList = new ArrayList();

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

      try {

        usuario_idTableColumn.setCellValueFactory(new PropertyValueFactory<>("usuario_id"));
        setTableColumnUser(usuario_nombreTableColumn, "usuario_nombre", 0);
        setTableColumnUser(passwordTableColumn, "password", 1);
        setTableColumnUser(usuario_activoTableColumn, "usuario_activo", 2);
        setTableColumnUser(preguntaTableColumn, "pregunta", 3);
        setTableColumnUser(respuestaTableColumn, "respuesta", 4);

        materia_idTableColumn.setCellValueFactory(new PropertyValueFactory<>("materia_id"));
        setTableColumnSubjects(fk_usuario_nombreTableColumn, "usuario_nombre", 0);
        setTableColumnSubjects(materia_nombreTableColumn, "materia_nombre", 1);
        setTableColumnSubjects(directorioTableColumn, "directorio", 2);

        idioma_idTableColumn.setCellValueFactory(new PropertyValueFactory<>("idioma_id"));
        setTableColumnIdioma(subject_nombreTableColumn, "materia_nombre", 0);
        setTableColumnIdioma(idiomaTableColumn, "idioma_nombre", 1);
        setTableColumnIdioma(escribirTableColumn, "escribir", 0);
        setTableColumnIdioma(traducirTableColumn, "traducir", 1);


        settingTableViewUsuario();
        settingUsuarioEvent();
        settingMateriaEvent();
        settingIdiomaEvent();

      } catch (Exception e) {
        Platform.runLater(() -> {
          message.message(Alert.AlertType.ERROR, "Error message",
                  "DataBaseController / handleCheckNombre()", e.toString(), e);
        });
      }
    }

   }


  /**
   *
   * @throws SQLException
   */
  private void settingTableViewUsuario()
   {

    conn01 = null;
    stmt = null;
    PreparedStatement pstmt = null;
    String sql = null;
    usuario_IdEnviar = 0;
    usuarioArrayList.clear();

    // Setting the tableview Usuario
    try {
      conn01 = connect();
      conn01.setAutoCommit(false);

      if (usuario_nombre.equals("root") &&
              (password.equals("1234"))) {
        root = true;
        sql = "SELECT * FROM usuarios";
        stmt = conn01.createStatement();
        rsUsuario = stmt.executeQuery(sql);

      } else {
        root = false;
        sql = "SELECT * FROM usuarios WHERE usuario_id = ?";
        pstmt = conn01.prepareStatement(sql);
        pstmt.setInt(1, usuario_id);
        rsUsuario = pstmt.executeQuery();

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
      usuarioTableView.getSelectionModel().select(0);
      usuarioTableView.scrollTo(0);
      usuario_IdEnviar = usuarioTableView.getSelectionModel().getSelectedItem().getUsuario_id();

      conn01.close();

      // Change settingTableViewMateria()
      settingTableViewMateria();

    } catch (SQLException e) {
      message.message(Alert.AlertType.ERROR, "Error message",
              "DataBaseController / settingTableViewUsuario()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message",
                "DataBaseController / settingTableViewUsuario()", e.toString(), e);
      }
    }
   }


  /**
   *
   * @throws SQLException
   */
  private void settingTableViewMateria()
   {
    conn01 = null;
    stmt = null;
    PreparedStatement pstmt = null;
    String sql = null;
    materia_IdEnviar = 0;
    materiaArrayList.clear();

    // Setting the tableview Materias
    try {
      conn01 = connect();
      conn01.setAutoCommit(false);

      sql = "SELECT u.usuario_nombre, m.materia_id, m.fk_usuario_id, " +
              "m.materia_nombre, m.directorio " +
              "FROM usuarios u " +
              "INNER JOIN materias m on u.usuario_id = m.fk_usuario_id " +
              "WHERE m.fk_usuario_id = ?";
      pstmt = conn01.prepareStatement(sql);
      pstmt.setInt(1, usuario_IdEnviar);
      rsMateria = pstmt.executeQuery();

      while (rsMateria.next()) {
        usuario_nombreEnMateria = rsMateria.getString("usuario_nombre");
        materia_id = rsMateria.getInt("materia_id");
        //fk_usuario_id = rsMateria.getInt("fk_usuario_id");
        materia_nombre = rsMateria.getString("materia_nombre");
        directorio = rsMateria.getString("directorio");

        materiaArrayList.add(new Materia(materia_id, usuario_nombreEnMateria, materia_nombre, directorio));
      }

      // Display row data -------------------
      materiaObservableList = FXCollections.observableArrayList(materiaArrayList);
      materiasTableView.setItems(materiaObservableList);

      if (materiaArrayList.isEmpty()) {
        conn01.close();
        settingTableViewIdioma();
        return;
      }

      // Setting the initial row
      materiasTableView.requestFocus();
      materiasTableView.getSelectionModel().select(0);
      materiasTableView.scrollTo(0);
      materia_IdEnviar = materiasTableView.getSelectionModel().getSelectedItem().getMateria_id();

      conn01.close();

      // Change TableViewIdioma()
      settingTableViewIdioma();

    } catch (SQLException e) {
      message.message(Alert.AlertType.ERROR, "Error message",
              "DataBaseController / settingTableViewMateria()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message",
                "DataBaseController / settingTableViewMateria()", e.toString(), e);
      }
    }
   }


  /**
   *
   * @throws SQLException
   */
  private void settingTableViewIdioma()
   {
    conn01 = null;
    stmt = null;
    PreparedStatement pstmt = null;
    String sql = null;
    idiomaArrayList.clear();

    // Setting the tableview Materias
    try {
      conn01 = connect();
      conn01.setAutoCommit(false);
      sql = "SELECT m.materia_nombre, i.idioma_id, i.idioma_nombre " +
              "FROM materias m " +
              "INNER JOIN idiomas i on m.materia_id = i.fk_materia_id " +
              "WHERE i.fk_materia_id = ? " +
              "GROUP BY i.idioma_id";

      pstmt = conn01.prepareStatement(sql);
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
        pstmt = conn01.prepareStatement(sql);
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

      if (idiomaArrayList.isEmpty()) {
        conn01.close();
        return;
      }

      // Setting the initial row
      idiomasTableView.requestFocus();
      idiomasTableView.getSelectionModel().select(0);
      idiomasTableView.scrollTo(0);

      conn01.close();
    } catch (SQLException e) {
      message.message(Alert.AlertType.ERROR, "Error message",
              "DataBaseController / settingTableViewIdioma()", e.toString(), e);
    } finally {
      try {
        if (conn01 != null) {
          conn01.close();
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message",
                "DataBaseController / settingTableViewIdioma()", e.toString(), e);
      }
    }
   }


  /**
   *
   */
  private void settingUsuarioEvent()
   {
    // Events Key
    usuarioTableView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

      if ((event.getCode() == KeyCode.TAB && !event.isShiftDown()) ||
              (event.getCode() == KeyCode.DOWN)) {
        indiceUsuario = usuarioTableView.getSelectionModel().getSelectedIndex();
        int indexLast = usuarioTableView.getItems().size();
        if (indiceUsuario < indexLast) indiceUsuario++;
        usuarioTableView.getSelectionModel().select(indiceUsuario);
      }
      if ((event.getCode() == KeyCode.TAB && event.isShiftDown()) ||
              (event.getCode() == KeyCode.UP)) {
        indiceUsuario = usuarioTableView.getSelectionModel().getSelectedIndex();
        if (indiceUsuario > 0) indiceUsuario--;
        usuarioTableView.getSelectionModel().select(indiceUsuario);
      }
      usuario_IdEnviar = usuarioTableView.getSelectionModel().getSelectedItem().getUsuario_id();
      event.consume();
      settingTableViewMateria();
    });

    // Events Mouse
    usuarioTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
      event.consume();
      usuario_IdEnviar = usuarioTableView.getSelectionModel().getSelectedItem().getUsuario_id();
      settingTableViewMateria();
    });
   }


  /**
   *
   */
  private void settingMateriaEvent()
   {
    // Events Key
    materiasTableView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

      indiceMateria = materiasTableView.getSelectionModel().getSelectedIndex();

      if ((event.getCode() == KeyCode.TAB && !event.isShiftDown()) ||
              (event.getCode() == KeyCode.DOWN)) {
        int indexLast = materiasTableView.getItems().size();
        if (indiceMateria < indexLast) indiceMateria++;
        materiasTableView.getSelectionModel().select(indiceMateria);
      }
      if ((event.getCode() == KeyCode.TAB && event.isShiftDown()) ||
              (event.getCode() == KeyCode.UP)) {
        if (indiceMateria > 0) indiceMateria--;
        materiasTableView.getSelectionModel().select(indiceMateria);
      }
      event.consume();
      materia_IdEnviar = materiasTableView.getSelectionModel().getSelectedItem().getMateria_id();
      settingTableViewIdioma();
    });

    // Events Mouse
    materiasTableView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
      materia_IdEnviar = materiasTableView.getSelectionModel().getSelectedItem().getMateria_id();
      settingTableViewIdioma();
      event.consume();
    });
   }


  /**
   *
   */
  private void settingIdiomaEvent()
   {
    // Events Key
    idiomasTableView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

      indiceIdioma = idiomasTableView.getSelectionModel().getSelectedIndex();

      if ((event.getCode() == KeyCode.TAB && !event.isShiftDown()) ||
              (event.getCode() == KeyCode.DOWN)) {
        int indexLast = idiomasTableView.getItems().size();
        if (indiceIdioma < indexLast) indiceIdioma++;
        idiomasTableView.getSelectionModel().select(indiceIdioma);
        event.consume();
      }
      if ((event.getCode() == KeyCode.TAB && event.isShiftDown()) ||
              (event.getCode() == KeyCode.UP)) {
        if (indiceIdioma > 0) indiceIdioma--;
        idiomasTableView.getSelectionModel().select(indiceIdioma);
        event.consume();
      }
    });
   }


  /**
   *
   * @param event
   * @throws IOException
   * @throws MalformedURLException
   */
  private void setTableColumnUser(TableColumn<?, ?> tc, String valueFactory, int index) throws IOException, MalformedURLException
   {

    if (!tc.getText().equals(resources.getString("Usuario Activo"))) {
      TableColumn<Usuario, String> tableColumn = (TableColumn<Usuario, String>) tc;
      tableColumn.setEditable(true);
      tableColumn.setCellValueFactory(new PropertyValueFactory<>(valueFactory));

      tableColumn.setOnEditStart((event) -> {
        showModalForm(event.getRowValue(), index);
      });      
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

        checkBox.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
          Usuario usu = (Usuario) tableCell.getTableRow().getItem();
          usu.setUsuario_activo(!checkBox.isSelected());
          handleUpdate(usu);
        });

        checkBox.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
          if (event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.ENTER) {
            Usuario usu = (Usuario) tableCell.getTableRow().getItem();
            usu.setUsuario_activo(!checkBox.isSelected());
            handleUpdate(usu);
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
  private void setTableColumnSubjects(TableColumn<Materia, String> tc, String valueFactory, int index) throws IOException, MalformedURLException
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
  private void setTableColumnIdioma(TableColumn<?, ?> tc, String valueFactory, int index) throws IOException, MalformedURLException
   {

    if (!tc.getText().equals(resources.getString("Escribir")) &&
            (!tc.getText().equals(resources.getString("Traducir")))) {
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
   * @param u
   * @param index
   */
  private void showModalForm(Usuario u, int index)
   {
    try {
      URL urlFXML = new URL(MainScene.class
              .getResource("/LanguageApp/view/formDataBaseView.fxml")
              .toExternalForm());
      FXMLLoader loader = new FXMLLoader(urlFXML, resources);
      formDataBaseView = (AnchorPane) loader.load();
      formDataBaseScene = new Scene(formDataBaseView);
      formDataBaseController = loader.getController();
      formDataBaseController.setMainScene(mainScene);

      formDataBaseController.setRowIntoModal(u, index, root);

      formStage = new Stage();
      formStage.setScene(formDataBaseScene);
      formStage.initOwner(mainStage);
      formStage.initModality(Modality.APPLICATION_MODAL);

      Image icon = new Image(getClass()
              .getResourceAsStream("/LanguageApp/resources/images/languages_128.png"));
      formStage.getIcons().add(icon);
      formStage.setTitle(resources.getString("Modificar la base de datos"));

      // Adding dark style
      JMetro jMetro = new JMetro(Style.DARK);
      jMetro.setScene(formDataBaseScene);

      formStage.setOnCloseRequest(e -> {
        e.consume();
        usuarioTableView.refresh();
        formStage.close();
      });

      formStage.addEventFilter(KeyEvent.KEY_PRESSED, (key) -> {
        if (key.getCode() == KeyCode.ESCAPE) {
          handleCloseModal();
        }
      });
      formStage.showAndWait();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Setting the borders">

  /**
   * Setting the border (cursor) of the node
   *
   * @param n the node to put the border
   */
  private void setBorder(Node n)
   {
    // si el que va a pintar o del que viene es listviewV lo cambio por AnchorPanel,
    // para que pinte mejor
    /*/if (n.equals(listViewV)) {
      n = anchorListViewV;
      currentNode.getStyleClass().removeAll(Collections.singleton("borderVisible"));
      n.getStyleClass().add("borderVisible");
      n = listViewV;
      oldNode = currentNode;
      currentNode = n;
      //System.out.println("oldNode " + oldNode);
      //System.out.println("currentNode " + currentNode + "\n");
      return;
    }*/
 /*/*if (currentNode.equals(listViewV)) {
      currentNode = anchorListViewV;
      currentNode.getStyleClass().removeAll(Collections.singleton("borderVisible"));
      n.getStyleClass().add("borderVisible");
      currentNode = listViewV;
      oldNode = currentNode;
      currentNode = n;
      //System.out.println("oldNode " + oldNode);
      //System.out.println("currentNode " + currentNode + "\n");
      return;
    } */
    currentNode.getStyleClass().removeAll(Collections.singleton("borderVisible"));
    n.getStyleClass().add("borderVisible");
    oldNode = currentNode;
    currentNode = n;
    //System.out.println("oldNode " + oldNode);
    //System.out.println("currentNode " + currentNode + "\n");
   }

  //</editor-fold> 

 }
