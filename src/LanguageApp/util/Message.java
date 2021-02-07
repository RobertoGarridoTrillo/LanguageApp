package LanguageApp.util;

import LanguageApp.main.MainScene;
import static LanguageApp.util.HandleLocale.toLocale;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class Message
 {

  //<editor-fold defaultstate="collapsed" desc="Field Class">

  // Reference to the main Stage from the main Scene
  private static Stage mainStage;
  // Reference to the main Scene
  private static MainScene mainScene;

  // For the bounle of idioms
  private static ResourceBundle resources;

  // For the list of classes of the app
  private static String[] classesApp;

  // File Separator
  String se;

  // Check the java 
  private static final String LOCAL_MACHINE = "HKLM";
  private static final String KEY_LANGUAGEAPP = "SOFTWARE\\LanguageApp\\LanguageApp";
  private static final String KEY_JAVA_RUNTIME = "SOFTWARE\\JavaSoft\\Java Runtime Environment";
  private static final String KEY_JAVA_DEVELOPMENT = "SOFTWARE\\JavaSoft\\Java Development Kit";
  private static final String SUBKEY_LANGUAGEAPP = "InstallPath";
  private static final String SUBKEY_JAVA = "CurrentVersion";
  //</editor-fold>


  //<editor-fold defaultstate="collapsed" desc="Constructors">
  public Message(ResourceBundle resources) throws Exception
   {

    // References to mainStage
    this.mainStage = MainScene.getMainStage();

    this.resources = resources;

    handleCreateClassesList();

    handleCheckJava();

   }
  //</editor-fold>


  //<editor-fold defaultstate="collapsed" desc="Reference to MainScene">
  public void setMainScene(MainScene aThis) throws Exception
   {
    mainScene = aThis;
   }
  //</editor-fold>


  //<editor-fold defaultstate="collapsed" desc="Check if Java exists">
  private void handleCreateClassesList() throws Exception
   {

    //<editor-fold defaultstate="collapsed" desc="FileNameFilter and FileFilter">

    // The filter used to get all the classes of the app
    FilenameFilter nameFilter = new FilenameFilter()
     {
      @Override
      public boolean accept(File f, String name)
       {
        return (name.endsWith(".java"));
       }


     };

    FileFilter dirFilter = new FileFilter()
     {
      @Override
      public boolean accept(File f)
       {
        return f.isDirectory();
       }


     };

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="make a array with the classes of the app">
    // The path is an absolute path (retarive to the initial instalation)    
    String pathApp = System.getProperty("user.dir");
    // Only for testing in IDE
    se = System.getProperty("file.separator");
    pathApp = handleFileSeparatorReplace(pathApp, se);

    // only for IDE use

    if (pathApp.equals(
            "D:\\wamp64\\www\\dreamweaver\\NetBeansProjects\\LanguageApp"))
      pathApp += "\\src\\LanguageApp";

    File[] dirsApp = new File(pathApp).listFiles(dirFilter);// return null if doesn't

    if (dirsApp != null && dirsApp.length > 0) {

      ArrayList<String> al = new ArrayList();
      String d, packet;

      for (File dirApp : dirsApp) {

        d = dirApp.getCanonicalPath() + se;
        packet = d.substring(d.lastIndexOf("LanguageApp"), d.length());
        packet = handleFileSeparatorReplace(packet, ".");

        String[] ss = dirApp.list(nameFilter);
        for (int i = 0; i < ss.length; i++) {
          ss[i] = packet.concat(ss[i].substring(0, ss[i].lastIndexOf(".java")));
        }
        al.addAll(Arrays.asList(ss));

      }
      // The java classese of the application
      classesApp = al.toArray(new String[al.size()]);
    }

    //</editor-fold>

   }


  private String handleFileSeparatorReplace(String s, String t) throws Exception
   {
    s = s.replace("/", t);
    s = s.replace("//", t);
    s = s.replace("\\", t);
    return s;
   }


  private boolean handleCheckJava() throws Exception
   {
    CheckJava checkJava = new CheckJava();

    String javaLanguageAppPath;
    boolean javaLanguageAppExists = false, javaExists = true;
    double javaVersionSystem = 0;


    // Check if java 1.8 LanguageApp exists
    javaLanguageAppPath = checkJava.getValue(LOCAL_MACHINE, KEY_LANGUAGEAPP, SUBKEY_LANGUAGEAPP);

    javaLanguageAppExists = new File(
            javaLanguageAppPath + "\\jre1.8.0_251\\bin\\javaw.exe").exists();

    // if languageApp hasn´t java
    if (!javaLanguageAppExists) {

      // if the system hasn´t java 1.8
      String temp = checkJava.getValue(LOCAL_MACHINE, KEY_JAVA_RUNTIME, SUBKEY_JAVA);
      if (!"".equals(temp)) javaVersionSystem = Double.parseDouble(temp);

      if (!Tools.rangeExc(javaVersionSystem, 1.8, 1.9)) {

        // if the system hasn´t java 1.8 in onother register
        temp = checkJava.getValue(LOCAL_MACHINE, KEY_JAVA_DEVELOPMENT, SUBKEY_JAVA);
        if (!"".equals(temp)) javaVersionSystem = Double.parseDouble(temp);

        if (!Tools.rangeExc(javaVersionSystem, 1.8, 1.9)) {
          // show message and exit
          JOptionPane.showMessageDialog(
                  null,
                  toLocale("Java no encontrado"),
                  toLocale("Mensaje de error"),
                  JOptionPane.ERROR_MESSAGE);
          exit();
        }
      }
    }
    return javaExists;
   }

  //</editor-fold>  

  public synchronized static void showException(Exception e)
   {
    String header = null, body = null;
    String elements[] = null;
    try {

      // elements of the printStackTracer
      elements = getElements(e);

      // message header
      header = getHeader(e);

      // message body
      body = getBody(elements);

      // show message 
      throw new Exception(e);

    } catch (Exception ex) {
      // show message, if the exception is in message.java the exit  
      if (!showMessage(header, body, e)) {
        exit();
      }
    }
   }


  private static void exit()
   {
    Platform.exit();
    System.exit(0);
   }


  private static String getHeader(Exception e)
   {
    String eMessage = null;
    String header = null;

    if (e.getMessage() != null) {

      eMessage = e.getMessage();

      if (e.getMessage().contains("/"))
        eMessage = e.getMessage().trim()
                .substring(e.getMessage().lastIndexOf("/"));

    } else if (e.getCause() != null) {

      eMessage = e.getCause().toString();

      if (e.getCause().toString().contains("/"))
        eMessage = e.getCause().toString().trim()
                .substring(e.getMessage().lastIndexOf("/"));

    } else
      eMessage = toLocale("El origen es inexistente o desconocido");

    header = toLocale("Error") + ": " + e.getClass().getSimpleName() +
            "   " + eMessage;

    return header;

   }


  private static String getBody(String[] elements) throws Exception
   {

    boolean salida = false, b = false;
    int at, dot, openedParenth, colon, closedParenth, lineNumber;
    String className, methodName, fileName, s, body = null;

    for (int i = 0, n = elements.length; i < n && salida == false; i++) {

      s = elements[i];
      if (s == null || s.equals("") || (int) s.charAt(0) != 0x09) //Hex 0x09
        continue;

      elements[i] = elements[i].trim();

      at = elements[i].indexOf("at") + 3;
      openedParenth = elements[i].indexOf('(');
      dot = (int) elements[i].substring(
              0, openedParenth).lastIndexOf('.');
      colon = elements[i].indexOf(':');
      closedParenth = elements[i].lastIndexOf(')');

      if (at <= 0 || openedParenth <= 0 || dot <= 0 ||
              colon <= 0 || closedParenth <= 0) continue;

      className = elements[i].substring(at, dot);
      methodName = elements[i].substring(dot + 1, openedParenth);
      fileName = elements[i].substring(openedParenth + 1, colon);
      lineNumber = Integer.valueOf(elements[i].substring(colon + 1, closedParenth));

      if (classesApp != null)
        b = Arrays.asList(classesApp).contains(className);
      else {
        b = true;
        salida = true;
      }
      if (b) {
        if (body == null) body = "";
        body += //toLocale("Clase") + "> " + className + " |  " +
                toLocale("Archivo") + "> " + fileName + " |  " +
                toLocale("Metodo") + "> " + methodName + "()" + " |  " +
                toLocale("Numero de linea") + "> " + lineNumber + "\n";
      }
    }

    return body;
   }


  private static String[] getElements(Exception e) throws Exception
   {
    String[] elements = null, temp = null;

    try {

      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      temp = sw.getBuffer().toString().split("\\r\\n|\\n|\\r");
      elements = new String[temp.length];
      // from end to start
      for (int i = 0; i < temp.length - 1; i++) {
        elements[temp.length - i - 1] = temp[i];
      }

    } catch (Exception ex) {
      StackTraceElement traces[] = e.getStackTrace();
      elements = new String[traces.length];

      for (int i = traces.length - 1; i >= 0; i--) {
        int fileNumber = traces[i].getLineNumber();
        elements[i] = ((char) 0x09) + "at " +
                traces[i].getClassName() + "." +
                traces[i].getMethodName() + "(" +
                traces[i].getFileName() + ":" +
                ((fileNumber < 0) ? 0 : fileNumber) + ")";
      }
    }

    return elements;
   }


  public static boolean showMessage(String header, String body, Exception e)
   {
    boolean salida = true;
    try {

      salida = message(Alert.AlertType.ERROR, "Mensaje de error", header, body, e);

    } catch (Exception ex) {

      salida = message(ex);

    }
    return salida;
   }


  public static boolean message(Alert.AlertType alertType, String title,
          String header, String body, Exception e) throws Exception
   {

    // References to mainScene
    mainStage = MainScene.getMainStage();

    Alert alert = new Alert(alertType);

    alert.setResizable(false);
    alert.initModality(Modality.APPLICATION_MODAL);

    if (!mainStage.getModality().equals(Modality.NONE))
      alert.initOwner(mainStage);

    alert.setTitle(toLocale(title));
    // Si es igual a esto lo traduce, si no, lo pone literalmente sin buscar en Bounds
    if (header.equals("¿Quieres cerrar la sesión?") ||
            header.equals("¿Quieres salir de la aplicación?") ||
            header.equals("Acerca de esta aplicación:") ||
            header.equals("Archivos multimedia movidos o perdidos")) {
      alert.getDialogPane().setHeaderText(toLocale(header));
    } else {
      alert.getDialogPane().setHeaderText(header);
    }
    // Deleting the last line jump
    int bl = body.length();
    char[] cs = body.toCharArray();

    for (int i = 0; i < bl; i++) {
      if (cs[bl - 1] == '\n') {
        body = body.substring(0, bl - 1);
        bl --;
      } else break;
    }

    double largo = header.length();
    double alto = body.split("\\r\\n|\\n|\\r").length;

    for (String s : body.split("\\r\\n|\\n|\\r")) {
      if (s.length() > largo) largo = s.length();
    }

    alert.getDialogPane().setMinWidth(500);
    alert.getDialogPane().setPrefWidth(largo * 8.7);
    //alert.getDialogPane().setMinHeight(alto * 60);
    //alert.getDialogPane().setPrefHeight(300);

    if (body.equals("Autor: Roberto Garrido Trillo")) {
      alert.getDialogPane().setContentText(toLocale(body));
    } else {
      alert.getDialogPane().setContentText(body);
    }
    if (e != null) {
      // Create expandable Exception.
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      String exceptionText = sw.toString();


      Label label = new Label(toLocale("El seguimiento del error fue:"));
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
            add(Message.class
                    .getResource("/LanguageApp/style/messages.css")
                    .toExternalForm());
    alert.getDialogPane().getStyleClass().add("messages");

    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
    Image icon = new Image(Message.class
            .getResourceAsStream("/LanguageApp/resources/images/languages_128.png"));
    stage.getIcons().add(icon);


    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) return result.get().equals(ButtonType.OK);
    else return false;
   }


  private static boolean message(Exception e)
   {
    String string = getHeader(e) + "\n";

    for (StackTraceElement ste : e.getStackTrace()) {

      if (ste != null) string += ste.getFileName() + "   " +
                ste.getMethodName() + "()   " +
                ste.getLineNumber() + "\n";
      else string += toLocale("El origen es inexistente o desconocido") + "\n";

    }
    JOptionPane.showMessageDialog(null,
            toLocale("El seguimiento del error fue:") + "\n" +
            string,
            toLocale("Mensaje de error"),
            JOptionPane.ERROR_MESSAGE
    );
    return false;
   }


 }
