package LanguageApp.util;

import static LanguageApp.main.MainScene.resourcesClose;
import LanguageApp.main.MainScene;
import LanguageApp.model.Initial;
import static LanguageApp.util.Message.showException;
import java.sql.ResultSet;

public class Directory
 {

//<editor-fold defaultstate="collapsed" desc="Field Class">
  // Global directories
  private String path;
  private String lastDirectory;
  private String lastFile;

  //String se = "/";
  private String se = System.getProperty("file.separator");
  private String initialFile;

  // Instancias
  private Initial initial;

  // Global variables
  private int usuario_id;
  private int materia_id;
  private String materia_nombre;
  private int materia_activo;

  // Connetion varibles
  /*/*private Connection conn;
  private Statement stmt;
  private PreparedStatement pstmt;
  private String sql;
  // Resulset of the tableview */
  private ResultSet rs[];

  // Reference to the main Scene
  private MainScene mainScene;
//</editor-fold>


  public Directory()
   {
    try {

      // Create a model class with the last conn...etc
      initial = new Initial();

      // References to mainScene
      mainScene = MainScene.getMainScene();

      // Get a connetion
      /*/*conn = mainScene.getConnection();
      conn.setAutoCommit(false);*/
      rs = new ResultSet[1];

      // The path is an absolute path (retarive to the initial instalation)    
      path = System.getProperty("user.dir");
      path = path.replace("/", se);
      path = path.replace("//", se);
      path = path.replace("\\", se);

      //path = path.replace("//", se);

      // The last conn is an absolute rute    
      lastDirectory = path + se + "media" + se;
      initialFile = "";

      // Setting the connetion variables
      /*/*stmt = null;
      pstmt = null;
      sql = null; */
    } catch (Exception e) {
      resourcesClose(null, rs);
      showException(e);
    }
   }


  public void setUsuario(int usuario_id) throws Exception
   {
    this.usuario_id = usuario_id;
   }


  public boolean checkIni(ResultSet resultSet) throws Exception
   {
    boolean salida = false;

    rs[0] = resultSet;

    if (rs[0].next()) {
      lastDirectory = rs[0].getString(1);
      initialFile = rs[0].getString(2);
      salida = true;

    } else {
      lastDirectory = path + se + "media" + se;
      initialFile = "";
    }

    setPath(path);
    setLastDirectory(lastDirectory);
    setLastFile(initialFile);
    resourcesClose(null, rs);
    return salida;
   }


  public void setMateriaId(int materia_id) throws Exception
   {
    this.materia_id = materia_id;
   }
  
  public void setName(String name) throws Exception
   {
    // save in the model the global conn
    setPath(path);
    setLastDirectory(lastDirectory);
    setLastFile(name);
   }
   

  //<editor-fold defaultstate="collapsed" desc="Setters and Getters">
  public String getPath() throws Exception
   {
    return initial.getPath();
   }


  public void setPath(String path) throws Exception
   {
    this.path = path;
    initial.setPath(path);
   }


  public String getLastDirectory() throws Exception
   {
    return initial.getLastDirectory();
   }


  public void setLastDirectory(String lastDirectory) throws Exception
   {
    this.lastDirectory = lastDirectory;
    initial.setLastDirectory(lastDirectory);
   }


  public String getLastFile() throws Exception
   {
    return initial.getLastFile();
   }


  public void setLastFile(String lastFile) throws Exception
   {
    this.lastFile = lastFile;
    initial.setLastFile(lastFile);
   }

//</editor-fold>


 }
