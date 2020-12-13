package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.util.Tools;
import LanguageApp.main.MainScene;
import LanguageApp.model.AudioClipPhrase;
import LanguageApp.model.AudioClipWord;
import LanguageApp.model.Item;
import LanguageApp.util.Directorio;
import LanguageApp.util.FillListView;
import LanguageApp.util.FormatTime;
import LanguageApp.util.GetJson;
import LanguageApp.util.Message;
import LanguageApp.util.SaveWordsAsList;
import LanguageApp.util.SelectedFile;
import LanguageApp.util.SortPhrase;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;

import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaErrorEvent;
import javafx.scene.media.MediaMarkerEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
//</editor-fold>

/**
 * Empty constructor
 *
 * @author Roberto Garrido Trillo
 */
public class PrincipalController implements Initializable
 {

//<editor-fold defaultstate="collapsed" desc="fields class">

  //<editor-fold defaultstate="collapsed" desc="---------------------- FXML">
  @FXML private BorderPane MainViewBorderPane;

  @FXML private AnchorPane principalViewAnchorPane;

  @FXML private AnchorPane dataBaseviewAnchorPane;

  @FXML private Button playButtonReading;
  @FXML private Button stopButtonReading;
  @FXML private Button backButtonReading;
  @FXML private Button forwardButtonReading;

  @FXML private Button playButtonWriting;
  @FXML private Button stopButtonWriting;
  @FXML private Button backButtonWriting;
  @FXML private Button forwardButtonWriting;

  @FXML private Button playButtonTranslation;
  @FXML private Button stopButtonTranslation;
  @FXML private Button backButtonTranslation;
  @FXML private Button forwardButtonTranslation;

  @FXML private Button playButtonItemOriginalReading;
  @FXML private Button stopButtonItemOriginalReading;
  @FXML private Button backButtonItemOriginalReading;
  @FXML private Button forwardButtonItemOriginalReading;

  @FXML private Button playButtonItemOriginalWriting;
  @FXML private Button stopButtonItemOriginalWriting;
  @FXML private Button backButtonItemOriginalWriting;
  @FXML private Button forwardButtonItemOriginalWriting;

  @FXML private Button playButtonItemOriginalTranslation;
  @FXML private Button stopButtonItemOriginalTranslation;
  @FXML private Button backButtonItemOriginalTranslation;
  @FXML private Button forwardButtonItemOriginalTranslation;

  @FXML private Button playButtonItemVirtualReading;
  @FXML private Button stopButtonItemVirtualReading;
  @FXML private Button backButtonItemVirtualReading;
  @FXML private Button forwardButtonItemVirtualReading;

  @FXML private Button playButtonItemVirtualWriting;
  @FXML private Button stopButtonItemVirtualWriting;
  @FXML private Button backButtonItemVirtualWriting;
  @FXML private Button forwardButtonItemVirtualWriting;

  @FXML private Button playButtonItemVirtualTranslation;
  @FXML private Button stopButtonItemVirtualTranslation;
  @FXML private Button backButtonItemVirtualTranslation;
  @FXML private Button forwardButtonItemVirtualTranslation;

  @FXML private Button playButtonItemVirtualTranslatedReading;
  @FXML private Button stopButtonItemVirtualTranslatedReading;
  @FXML private Button backButtonItemVirtualTranslatedReading;
  @FXML private Button forwardButtonItemVirtualTranslatedReading;

  @FXML private Button playButtonItemVirtualTranslatedWriting;
  @FXML private Button stopButtonItemVirtualTranslatedWriting;
  @FXML private Button backButtonItemVirtualTranslatedWriting;
  @FXML private Button forwardButtonItemVirtualTranslatedWriting;

  @FXML private Button playButtonItemVirtualTranslatedTranslation;
  @FXML private Button stopButtonItemVirtualTranslatedTranslation;
  @FXML private Button backButtonItemVirtualTranslatedTranslation;
  @FXML private Button forwardButtonItemVirtualTranslatedTranslation;

  @FXML private Button correctionButtonWriting;
  @FXML private Button correctionButtonTranslation;



  @FXML private ListView<String> listViewV;

  @FXML private AnchorPane anchorListViewV;

  @FXML private ListView<String> listViewH01Reading;

  @FXML private ListView<String> listViewH02Reading;

  @FXML private MediaView mediaView;

  @FXML private Slider volumeSliderReading;
  @FXML private Slider rateSliderReading;
  @FXML private Slider balanceSliderReading;

  @FXML private Slider volumeSliderWriting;
  @FXML private Slider rateSliderWriting;
  @FXML private Slider balanceSliderWriting;

  @FXML private Slider volumeSliderTranslation;
  @FXML private Slider rateSliderTranslation;
  @FXML private Slider balanceSliderTranslation;

  @FXML private Slider mediaSliderReading;
  @FXML private Slider mediaSliderWriting;
  @FXML private Slider mediaSliderTranslation;

  @FXML private Label volumeLabelReading;
  @FXML private Label rateLabelReading;
  @FXML private Label balanceLabelReading;

  @FXML private Label timeLabelReading;

  @FXML private Label volumeLabelWriting;
  @FXML private Label rateLabelWriting;
  @FXML private Label balanceLabelWriting;

  @FXML private Label volumeLabelTranslation;
  @FXML private Label rateLabelTranslation;
  @FXML private Label balanceLabelTranslation;

  @FXML private TextField textFieldWriting;
  @FXML private TextField textFieldTranslation;

  @FXML private AnchorPane anchorMedia;

  @FXML private ProgressIndicator indicatorSuccessWriting;

  @FXML private ProgressIndicator indicatorSuccessTranslation;

  @FXML private TabPane tabPanelListViewH;
  @FXML private Tab tabLeerListViewH;
  @FXML private Tab tabEscribirListViewH;
  @FXML private Tab tabTraducirListViewH;

  @FXML private AnchorPane gridPaneAnchorPane00Reading;

  // hyperlink youtube
  @FXML Hyperlink youtubeLink;
  //</editor-fold>

  // pop-up messages
  Message message;

  // The focused and old node
  Node currentNode, oldNode;
  int[] oldNodeSliderUp, oldNodeMediaSliderDown,
          oldNodeListViewH1Down, oldNodeTabPaneUp;

  // The tab thar is checkes
  private String currentTab;

  // for transversal movements
  Node[] node;

  // Reference to the main Stage from the main Scene
  private Stage mainStage;

  // Reference to the main Scene
  private MainScene mainScene;

  // Json
  private Item[] itemsOriginal, itemsTranslation;

  private Item[][] idiomas;

  private String subtitleAudio;

  File jsonBinder;

  String[] subtitle;

// Player
  private MediaPlayer mediaPlayer;

  private Media media;

  private URI mediaStringUrl;

  private int indexSelected;

  private boolean playButtonBoolean; // if i´m touching an Item, don´t scroll

  private Duration duration; //  the total lenght of the media

  private Duration currentTime; // the actual position of the media

  private Status status; // the status of the media

  private double sliderMediaCurrent; // the temporal status of the sliderMedia
/*/*
  private ChangeListener<Duration> mediaPlayerChangeListener;

  private ChangeListener<Number> sliderMeediaChangeListener; */

  private Thread mediaToSlideThread;

  private String mediaToSlider; // pause stop play the thread media to slider 

  // itemInicio
  private double start;
  private double end;

  private Duration startVirtual, startTranslatedVirtual;
  private Duration endVirtual, endTranslatedVirtual;

  private int indexItemV;

  // Save as a list of words
  private Set<String>[] wordSet;

  private String[] phraseSet;
  private String[][] phraseString;

  // Show ListView 
  private String[] itemWordsOriginal;
  private String[] itemWordsTranslation;

  // ListView MarkerText
  private String markerTextOriginal;
  private String markerTextTranslation;

  // AudiClip
  /*/*private Map<String, AudioClip>[] audioClipsWords;
  private Map<String, MediaPlayer>[] audioClipsPhrases; */

  private AudioClipWord[] audioClipsWords;
  private AudioClipPhrase[] audioClipsPhrases;

  private MediaPlayer virtual, translatedVirtual;
  private double backForw;

  // Subtitle original (listviewH 01) and Subtitle translation (listviewH 02) 
  private String trans;
  private int subOrig, subTrans;

  private boolean exitLoop;

  private int currentPauseItem; // the actual itemInicio when click the original pause 

  // slider media, to control if the moviment of the media comes from the slider or the media
  private static String mediaPlayerSlider;

  // if I´m stopping from itemInicio original or media
  private boolean originalButton;

  // From original itemInicio to pause / play
  private Duration currentOriginal;

  // An Array with the image of the buttons
  private ImageView[] imageViews;

  // For relative Direcction 
  String se;
  String path;

  // For KeyCode
  final KeyCombination kcLeft = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN);
  final KeyCombination kcRight = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN);

  // Extension of the media
  String mp;

  // For correction button
  private double success;

  // Class instances
  // private SelectedFile sf;
  // private GetJson gj;
  // private FillListView flw;
  // private AudioClips ac;
  // private SaveWordsAsList swal;
  // private FormatTime ft;
  private Directorio dire;
  // private SortPhrase sp;

  // Active user
  private int usuario_id;
  private String usuario_nombre;
  private boolean materia_activo;

  // ProgressBar
  private double progressBarStep;
  private int totalMessages;
  // The value of the progressBar in mainScene
  DoubleProperty progressBarValue = new SimpleDoubleProperty(0.0);

  // For the bounle of idioms
  ResourceBundle resources;

  // Creating multilingual constans
  private String leer, escribir, traducir;

  // Thread handleOpenMenu2Thread
  public Thread handleOpenMenu2Thread;

  private String handleOpenMenu2; // Play, pause, stop handleOpenMenu2

  private final Object handleOpenMenu2Lock = new Object();


//</editor-fold>  

//<editor-fold defaultstate="collapsed" desc="Constructor">

  public PrincipalController()
   {

    //<editor-fold defaultstate="collapsed" desc="Instances">
    // sf = new SelectedFile();
    // gj = new GetJson();
    // flw = new FillListView();
    // ac = new AudioClips();
    dire = new Directorio();
    // swal = new SaveWordsAsList();
    // ft = new FormatTime();
    // sp = new SortPhrase();

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Thread that change the slider when the media has changed">
    // syncronized with this
    mediaToSlideThread = new Thread(new Runnable()
     {
      @Override
      public void run()
       {
        try {
          while (!mediaToSlider.equals("stop")) {

            synchronized (PrincipalController.this) {
              while (mediaToSlider.equals("pause")) {
                PrincipalController.this.wait();
                // para saltarnos un ciclo cuando movemos el slider estando en pausa y evitar
                // el salto 
                mediaPlayerSlider = "neutro";
              }
            }

            mediaToSlideThread.sleep(1000);

            if (timeLabelReading != null && mediaSliderReading != null &&
                    duration != null && mediaPlayer != null) {

              // Extract the current time of the media and put in in the label
              currentTime = mediaPlayer.getCurrentTime();
              Platform.runLater(() -> {
                FormatTime ft = new FormatTime();
                timeLabelReading.setText(ft.formatting(currentTime, duration));
              });

              double totalDuration = duration.toMillis();
              double positionMedia = currentTime.toMillis();
              double positionFinal = ((positionMedia * 100) / totalDuration);

              if (mediaPlayerSlider.equals("media")) {
                mediaSliderReading.setValue(positionFinal);
              }
              mediaPlayerSlider = "media";
            }

            /*/*System.out.println("status " + status +
                    " mediaPlayerSlider " + mediaPlayerSlider +
                    " mediaToSlider " + mediaToSlider +
                    " nombre del hilo " + mediaToSlideThread.getName() +
                    " currentTime " + currentTime);*/
          }
        } catch (InterruptedException e) {
          message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / mediaToSlideThread", e.toString(), e);
        }
       }

     }
    );

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Thread that prepare the dashboard (handleOpenMenu2)">

    // syncronized with object
    handleOpenMenu2Thread = new Thread(new Runnable()
     {
      @Override
      public void run()
       {
        try {

          int idiomasLength = 0;

          while (!handleOpenMenu2.equals("stop")) {

            //System.out.println("Entrando " + " " + handleOpenMenu2Thread.getName());
            //System.out.println("handleOpenMenu2 " + handleOpenMenu2);

            synchronized (handleOpenMenu2Lock) {
              while (handleOpenMenu2.equals("pause")) {
                handleOpenMenu2Lock.wait();
                //System.out.println("Esperando " + " " + handleOpenMenu2Thread.getName());
                //System.out.println("handleOpenMenu2 " + handleOpenMenu2);

              }

            }
            if (handleOpenMenu2.equals("stop")) return;

            //System.out.println("Repito ciclo Esperar " + handleOpenMenu2 +
            //        " " + handleOpenMenu2Thread.getName());

            //<editor-fold defaultstate="collapsed" desc="Creando Matrices">

            handleCloseMenu("handleOpenMenu2");

            // Getting initial user and materia_activo
            usuario_id = mainScene.getUsuario_id();
            usuario_nombre = mainScene.getUsuario_nombre();
            // Checking if exists some equal, if doesn't it's created
            dire.checkIni(usuario_id);

            // Si por error hay mas de una materia activa
            String ld = dire.getLastDirectory();
            String lf = dire.getLastFile();
            dire.checkAndSetLastDirectory(lf, ld, usuario_id);

            materia_activo = mainScene.handleCheckMateriaActivo(usuario_id);
            if (materia_activo) {

              // Lock the name of the file idiomas.Json in the directory Json and load the idiomas[][]
              File jsonBinder = new File(dire.getLastDirectory() + "Json\\\\");

              // el subtitle suele salir ordenado, pero no es seguro, solo para ir cargango jsons
              // Name of the carpets into Json (Englis, Spanish...)
              subtitle = jsonBinder.list();

              // if the directoriy has problem (empty, moved...) delete the row database
              if (subtitle == null) dire.delete(usuario_id);
              while (subtitle == null) {
              }
              int subtitleLength = subtitle.length;

              // Problemas with the generic array - fixed
              // Array of items[] -> idiomas[][]
              idiomas = new Item[subtitleLength][];

              // set of words
              wordSet = (Set<String>[]) new LinkedHashSet[subtitleLength];

              phraseSet = new String[subtitleLength]; // set of phrases

              phraseString = new String[subtitleLength][];

              int totalMessages = 7 + (subtitleLength * 5);

              setProgressBar(totalMessages, "Creando Matrices");

              audioClipsWords = new AudioClipWord[subtitleLength];
              audioClipsPhrases = new AudioClipPhrase[subtitleLength];

              //</editor-fold>     

              //<editor-fold defaultstate="collapsed" desc="Comenzando a crear los audios">

              setProgressBar(totalMessages, "Comenzando a crear los audios");

              dire.checkAndSetIdioma(subtitle);

              for (int x = 0; x < subtitleLength; x++) {
                setProgressBar(totalMessages, "Leyendo Json en " +
                        subtitle[x].replaceAll(".json", ""));

                // Read a json ,call a read JSON, return an array of Item class objects
                File file = new File(dire.getLastDirectory() + "Json\\\\" + subtitle[x]);

                if (file.exists()) {
                  // Read the Json witn the Item[]
                  GetJson gj = new GetJson();
                  // 196.4 MB
                  idiomas[x] = gj.getJson(file);
                  gj = null;

                  // Read the subtitle and capitalize
                  subtitle[x] = subtitle[x].replaceAll(".json", "");
                  subtitle[x] = subtitle[x].substring(0, 1)
                          .toUpperCase() + subtitle[x].substring(1);

                  // Extract the languages of the media, the rest from subtitle[]
                  subtitleAudio = idiomas[x][idiomas[x].length - 1].getText();

                  setProgressBar(totalMessages, "Creando palabras en " + subtitle[x]);
                  SaveWordsAsList swal = new SaveWordsAsList();
                  wordSet[x] = swal.saveWordsAsList(idiomas[x]);

                  setProgressBar(totalMessages, "Creando frases en " + subtitle[x]);
                  SortPhrase sp = new SortPhrase();
                  phraseSet = sp.sortPhrases(idiomas[x]);
                  sp = null;
                  phraseString[x] = new String[phraseSet.length];
                  phraseString[x] = phraseSet;
                  //
                  idiomasLength = phraseString[x].length;
                }
              }

              // Creating the menu with flags (deleting the useless flag )
              mainScene.setVisibleFlagMenu(subtitle, subtitleAudio);

              //</editor-fold>

              //<editor-fold defaultstate="collapsed" desc="Llenando tablas">
              setProgressBar(totalMessages, "Llenando tablas");

              // Fill itemOriginal with the first language of the subtitleAudio[0]
              subOrig = Tools.getIndex(subtitle, subtitleAudio);


              // Fill translation with the default language of the machine, if it's equal to the
              // first then put spanis by default
              String languageDefault = Tools.getLanguageDefault();
              trans = (!subtitleAudio.equals(languageDefault)) ? languageDefault : "Spanish";

              subTrans = Tools.getIndex(subtitle, trans);

              itemsTranslation = idiomas[subTrans];
              itemsOriginal = idiomas[subOrig];

             
              // fill a ListView with the phrases of the Items array
              FillListView flw = new FillListView();
              flw.setListView(listViewV, itemsOriginal);

              // Creating the path to the media
              mediaStringUrl = new File(dire.getLastDirectory() + dire.getLastFile()).toURI();

              //</editor-fold>

              //<editor-fold defaultstate="collapsed" desc="Configurando los archivos de medios">
              setProgressBar(totalMessages, "Configurando los archivos de medios");

              // Set the MediaPlayer
              setMediaPlayer();

              // Setting audiclips
              for (int x = 0; x < subtitleLength; x++) {

                setProgressBar(totalMessages, "Creando tablas de palabras en " + subtitle[x]);

                //--

                Map<String, AudioClip> mapAdioClip =
                        new HashMap<String, AudioClip>(wordSet[x].size());

                for (String ws : wordSet[x]) {

                  // This is to fix the forbbiden name con. in windows
                  if (ws.equals("con")) ws = "connn";
                  if (ws.equals("aux")) ws = "auxxx";
                  String direction = dire.getLastDirectory() + "Dictionaries\\" +
                          subtitle[x] + "Dictionary\\Words\\" + se + ws + ".mp3";
                  File fileClip = new File(direction);

                  if (!fileClip.exists()) {
                    message.message(Alert.AlertType.ERROR, "Error de audio",
                            "Falta el audio", direction, null);
                    return;
                  }

                  mapAdioClip.put(ws, new AudioClip(new File(direction).toURI().toString()));
                  setAudioClip(mapAdioClip.get(ws),
                          rateSliderReading, balanceSliderReading, volumeSliderReading);
                }

                audioClipsWords[x] = new AudioClipWord(mapAdioClip);
                //--


                setProgressBar(totalMessages, "Creando tablas de frases en " + subtitle[x]);

                String direction = dire.getLastDirectory() + "Dictionaries\\" +
                        subtitle[x] + "Dictionary\\Phrases";

                audioClipsPhrases[x] = new AudioClipPhrase(setAudioMedia(
                        phraseString[x], direction,
                        rateSliderReading, balanceSliderReading, volumeSliderReading));

              }
              System.gc();
              System.runFinalization();
              //</editor-fold>

              //<editor-fold defaultstate="collapsed" desc="Configurando los deslizadores">

              setProgressBar(totalMessages, "Configurando los deslizadores");
              Platform.runLater(() -> {
                // Setting the sliderMedia
                setSliderMedia(mediaSliderReading);
                setSliderMedia(mediaSliderWriting);
                setSliderMedia(mediaSliderTranslation);
                setTicksSliderMedia(mediaSliderReading, 100);
                setTicksSliderMedia(mediaSliderWriting, 100);
                setTicksSliderMedia(mediaSliderTranslation, 100);
              });

              //</editor-fold>

              //<editor-fold defaultstate="collapsed" desc="Configuración final">

              setProgressBar(totalMessages, "Configuración final");
              // The first lap of the slider
              mediaPlayerSlider = "media"; // to the first lap

              // Setting the first itemInicio
              indexItemV = 0;
              listViewV.scrollTo(0);
              listViewV.getSelectionModel().select(0);


              // Setting the initial pause
              currentOriginal = new Duration(itemsOriginal[indexItemV].getStart());

              // Setting the binding beetwen Writer Tab and Reader Tab
              setBinding();
              // Initial call that return the total duration of the file 
              //and set it in  the label textLabel  
              setEndTimefile();


              setSetterOpenMenu2(subtitleLength, idiomasLength);

              setSubtitle(subtitle);
              setIdiomas(idiomas);
              setWordSet(wordSet);
              /*/* setPhraseSet(phraseSet);*/
              setPhraseString(phraseString);
              setAudioClipsWords(audioClipsWords);
              setAudioClipsPhrases(audioClipsPhrases);
              setSubtitleAudio(subtitleAudio);
              setItemsOriginal(itemsOriginal);
              setItemsTranslation(itemsTranslation);

              
wordSet[0] = null;wordSet[1] = null;wordSet[2] = null;wordSet[3] = null;wordSet[4] = null;      
idiomas[0] = null;idiomas[1] = null;idiomas[2] = null;idiomas[3] = null;idiomas[4] = null;   
phraseSet[0] = null;phraseSet[1] = null;phraseSet[2] = null;phraseSet[3] = null;phraseSet[4] = null;
phraseString[0] = null;phraseString[1] = null;phraseString[2] = null;phraseString[3] = null;phraseString[4] = null;   
wordSet = null;
idiomas = null;
phraseSet = null;
phraseString = null;
System.gc();

              subtitle = null;
              idiomas = null;
              wordSet = null;
              phraseSet = null;
              phraseString = null;
              audioClipsWords = null;
              audioClipsPhrases = null;
              subtitleAudio = null;
              itemsOriginal = null;
              itemsTranslation = null;
              System.gc();
              showListViewH();
              setProgressBar(totalMessages, "Finalizado");
            }

            // Pause the Thread
            handleOpenMenu2Pause();

            //</editor-fold>
          }
        } catch (Exception e) {
          Platform.runLater(() -> {
            message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleOpenMenu2Thread()", e.toString(), e);
          });
        }
       }

     });

    //</editor-fold>

   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="setMainScene">

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
    try {
      this.resources = resources;

      // References to mainScene
      mainStage = MainScene.getMainStage();

      // Creating multilingual constans
      Locale loc = new Locale(Locale.getDefault().getLanguage());
      ResourceBundle rs = ResourceBundle.getBundle("LanguageApp.resources.bundles.LanguageApp", loc);

      leer = rs.getString("Leer");
      escribir = rs.getString("Escribir");
      traducir = rs.getString("Traducir");

      // Create the locale for the pop up messages
      /*/*HandleLocale01.handleLocale01();*/
      message = new Message(resources);

      path = System.getProperty("user.dir");
      se = System.getProperty("file.separator");
      //se = "/";

      // if it playing an itemInicio, doesn´t scroll
      playButtonBoolean = false;

      // Setting the sliders of the Volumen, balance, rate
      setSlider();

      // Setting the images of the butttons
      setImageButton();
      // setting the ammount of back and forward of the media
      backForw = 500.0;

      // Setting Hyperlink youtube
      setHyperlink();

      //<editor-fold defaultstate="collapsed" desc="------------------- Nodos">
      node = new Node[]{
        listViewV,
        tabPanelListViewH,
        listViewH02Reading,
        youtubeLink,
        listViewH01Reading,
        playButtonReading, backButtonReading, forwardButtonReading, stopButtonReading,
        mediaSliderReading,
        playButtonItemOriginalReading, backButtonItemOriginalReading,
        forwardButtonItemOriginalReading, stopButtonItemOriginalReading,
        playButtonItemVirtualReading, backButtonItemVirtualReading,
        forwardButtonItemVirtualReading, stopButtonItemVirtualReading,
        playButtonItemVirtualTranslatedReading, backButtonItemVirtualTranslatedReading,
        forwardButtonItemVirtualTranslatedReading, stopButtonItemVirtualTranslatedReading,
        rateSliderReading, balanceSliderReading, volumeSliderReading,
        textFieldWriting,
        playButtonWriting, backButtonWriting, forwardButtonWriting, stopButtonWriting,
        mediaSliderWriting,
        playButtonItemOriginalWriting, backButtonItemOriginalWriting,
        forwardButtonItemOriginalWriting, stopButtonItemOriginalWriting,
        playButtonItemVirtualWriting, backButtonItemVirtualWriting,
        forwardButtonItemVirtualWriting, stopButtonItemVirtualWriting,
        playButtonItemVirtualTranslatedWriting, backButtonItemVirtualTranslatedWriting,
        forwardButtonItemVirtualTranslatedWriting, stopButtonItemVirtualTranslatedWriting,
        correctionButtonWriting,
        rateSliderWriting, balanceSliderWriting, volumeSliderWriting,
        textFieldTranslation,
        playButtonTranslation, backButtonTranslation, forwardButtonTranslation, stopButtonTranslation,
        mediaSliderTranslation,
        playButtonItemOriginalTranslation, backButtonItemOriginalTranslation,
        forwardButtonItemOriginalTranslation, stopButtonItemOriginalTranslation,
        playButtonItemVirtualTranslation, backButtonItemVirtualTranslation,
        forwardButtonItemVirtualTranslation, stopButtonItemVirtualTranslation,
        playButtonItemVirtualTranslatedTranslation, backButtonItemVirtualTranslatedTranslation,
        forwardButtonItemVirtualTranslatedTranslation, stopButtonItemVirtualTranslatedTranslation,
        correctionButtonTranslation,
        rateSliderTranslation, balanceSliderTranslation, volumeSliderTranslation};
      //</editor-fold>

      // Setting the listViewH and textField invisible or disable
      listViewH01Reading.setVisible(true);
      listViewH02Reading.setVisible(true);
      textFieldWriting.setVisible(false);
      textFieldTranslation.setVisible(false);

      // Setting the transversal focus of the nodes
      setTransversalFocus();

      // The initial tab
      currentTab = leer;
      tabPanelListViewH.getSelectionModel().select(0);

      // Setting the current node
      currentNode = listViewV;
      oldNode = listViewH01Reading;

      listViewV.requestFocus();

      // Settiong the intial border
      setBorder(listViewV);

      // Setting if the stop is from original button or media (true idInicio original)
      originalButton = false;

      // The index of the listviewV when click in the pause Button
      currentPauseItem = 0;

      // Setting the statu- of the media and the sliderMedia
      status = Status.STOPPED;

      // Getting initial user
      usuario_id = mainScene.getUsuario_id();
      usuario_nombre = mainScene.getUsuario_nombre();

      // Setting the default ubtitle that I'm going to put in listview H01 y H02
      subOrig = 0;
      subTrans = 1;

      // The value of the progressBar in mainScene
      progressBarValue.addListener((observable, oldValue, newValue) -> {
        tabPanelListViewH.setDisable(progressBarValue.lessThan(1).get());
        listViewV.setDisable(progressBarValue.lessThan(1).get());
        //tabPanelListViewV.setDisable(progressBarValue.lessThan(1).get());        
      });

      // Setting the event handleOpenMenu2Thread
      //handleOpenMenu2Lock = new Object();
      handleOpenMenu2 = "pause";
      handleOpenMenu2Thread.start();

      // Setting the event that move the slider media by the media
      mediaToSliderPause();
      mediaToSlideThread.start();

      // Check if there´s an initial file
      if (dire.checkIni(usuario_id)) {
        handleOpenMenu2Play();
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / initialize()", e.toString(), e);
    }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Menu + inicio">

  //<editor-fold defaultstate="collapsed" desc="handleOpenMenu">

  /**
   * Open a SelectFile and seek a json to load the phrases (Part 1)
   *
   * @return
   */
  public boolean handleOpenMenu()
   {
    // Open a new fileChooser, set the stage where it´s shows,return un File
    usuario_id = mainScene.getUsuario_id();
    try {

      // Open a filechooser
      SelectedFile selectedFile = new SelectedFile();
      File file = selectedFile.getSelectedFile(mainStage, dire.getLastDirectory());
      if (file == null) {

        return false;
      }

      // Setting the globlal directory
      String lastDirectory = file.getParent() + se;//el que acabo de abrir con el filechooser
      String name = file.getName();

      // Checking if exists some equal, if doesn't it's created
      dire.checkAndSetLastDirectory(name, lastDirectory, usuario_id);

      handleOpenMenu2Play();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleOpenMenu()", e.toString(), e);
    }
    return true;
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Play / Pause / Stop handleOpenMenu2 Thread">

  /**
   *
   */
  public void handleOpenMenu2Play()
   {
    synchronized (handleOpenMenu2Lock) {
      handleOpenMenu2 = "play";
      handleOpenMenu2Lock.notify();
    }
   }


  /**
   *
   */
  private void handleOpenMenu2Pause()
   {
    synchronized (handleOpenMenu2Lock) {
      handleOpenMenu2 = "pause";
      handleOpenMenu2Lock.notify();
    }
   }


  /**
   *
   */
  private void handleOpenMenu2Salir()
   {
    synchronized (handleOpenMenu2Lock) {

      handleOpenMenu2 = "stop";
      handleOpenMenu2Lock.notify();
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="handleCloseMenu">

  /**
   * When click on the close menu
   *
   * @param origen Only when the origen is handleOpenMenu2Thread I create an empty inicial file is not, only close all.
   */
  public void handleCloseMenu(String origen)
   {
    try {
      if (mediaPlayer == null)  return;
      // if the app is loading a film return
      if (handleOpenMenu2Thread != null && handleOpenMenu2Thread.isAlive()) return;

      handleStopButton();


      // Setting the listViewH and textField invisible or disable
      listViewH01Reading.setVisible(true);
      listViewH02Reading.setVisible(true);
      textFieldWriting.setVisible(false);
      textFieldTranslation.setVisible(false);

      // Delete flogs
      mainScene.setInvisibleFlagMenu(subtitle, subtitleAudio);

      for (int x = 0; x < subtitle.length; x++) {

        idiomas[x] = null;
        subtitle[x] = null;

        wordSet[x] = null;
        /*/*phraseSet[x] = null; */
        phraseString[x] = null;

        audioClipsWords[x] = null;
        audioClipsPhrases[x] = null;
        System.gc();
      }
      idiomas = null;
      subtitle = null;
      subtitleAudio = null;

      wordSet = null;
      /*/* phraseSet = null; */
      phraseString = null;

      audioClipsWords = null;
      audioClipsPhrases = null;

      itemsOriginal = null;
      itemsTranslation = null;
      itemWordsOriginal = null;
      itemWordsTranslation = null;

      //mediaView = null;
      mediaPlayer.dispose();
      mediaPlayer = null;
      media = null;
      *virtual.dispose();
      virtual = null;
      translatedVirtual.dispose();
      translatedVirtual = null;
      System.gc();

      itemsOriginal = new Item[1];
      itemsOriginal[0] = new Item(0, 0, " ");
      itemsTranslation = new Item[1];
      itemsTranslation[0] = new Item(0, 0, " ");

      FillListView flw = new FillListView();
      flw.setListView(listViewV, itemsOriginal);
      flw.setListView(listViewH01Reading, itemsOriginal);
      flw.setListView(listViewH02Reading, itemsOriginal);

      textFieldWriting.setText("");
      textFieldTranslation.setText("");

      tabPanelListViewH.getSelectionModel().select(0);

      System.gc();
      // Setting the sliders of the Volumen, balance, rate
      setSlider();

      // Delete and create and empty initial file
      usuario_id = mainScene.getUsuario_id();


      switch (origen) {
        case "handleCloseMenu":
          dire.createIni(usuario_id);
          break;
        case "buttonLoginMenu":
          break;
        case "buttonUnloginMenu":
          /*/*System.out.println("Creando un nuevo principio");
          dire.createIni(usuario_id);
          handleOpenMenu2Salir();
          mediaToSliderStop();
          //handleOpenMenu2Play(); */
          break;
        default:
          break;
      }

    } catch (Exception e) {
      Platform.runLater(() -> {
        message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleMenuClose()", e.toString(), e);
      });
    }

   }

//</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Play, stop, pause Button...">

//<editor-fold defaultstate="collapsed" desc="Play media normal">
  /**
   * Controller for slider media play button
   */
  @FXML
  public void handlePlayButton()
   {
    try {
      if (mediaPlayer == null) return; // if doesn't exits a media return 

      mediaSliderReading.setDisable(false);
      mediaSliderWriting.setDisable(false);
      mediaSliderTranslation.setDisable(false);

      //<editor-fold defaultstate="collapsed" desc="if "in pause"  drag the slidermedia it doesn´t detect the end of file">

      if (mediaPlayer.getCurrentTime().equals(mediaPlayer.getStopTime())) {
        handleStopButton();

        helperPlayButton(false, true,
                "play", "play", "play",
                "play");
        return;
      }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="if I push the normal play in "normal mode" (It's stopped)">

      if ((status == Status.STOPPED || status == Status.READY) && !originalButton) {

        getIndexitemV();

        if (indexItemV != 0) {
          start = itemsOriginal[indexItemV].getStart();
          mediaPlayer.setStartTime(Duration.seconds(start));
          mediaPlayer.setStopTime(duration);
        }
        mediaPlayerStop();

        helperPlayButton(false, true,
                "play", "pause", "play",
                "play");
        return;
      }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="if I push the play button in "pause mode" (i's paused)">
      if (status == Status.PAUSED && !originalButton) {

        mediaPlayer.setStartTime(mediaPlayer.getCurrentTime());
        mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());

        helperPlayButton(false, true,
                "play", "pause", "play",
                "play");
        return;
      }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="if I push the play button in "original mode" (it continues until the end)">
      if ((status == Status.PAUSED || status == Status.STOPPED || status == Status.READY) && originalButton) {
        mediaPlayer.setStartTime(mediaPlayer.getCurrentTime());
        mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());

        helperPlayButton(false, true,
                "play", "pause", "play",
                "play");
        return;
      }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="if I push the play button in "original mode" (it continues until the end)">
      if ((status == Status.PLAYING) && originalButton) {
        mediaPlayer.setStartTime(mediaPlayer.getCurrentTime());
        mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());

        helperPlayButton(true, true,
                "play", "pause", "play",
                "play");
        return;
      }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="By default">
      if (status == Status.PLAYING && !originalButton) {

        helperPlayButton(false, true,
                "pause", "play", "play",
                "pause");
      }
      //</editor-fold>

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handlePlayButton()", e.toString(), e);
    }
   }




  /**
   *
   * @param botonOriginal Stop false / Play true - The original pause button mode
   * @param scrollToMarkers Stop false / Play true - Enable the scroll to markers
   * @param AccionMedia "play", "stop", "pause" the media
   * @param ImageButton Play true, Pause false - Image of the play button
   * @param ImageButtonOriginal Play true, Pause false - Image of the play button original
   * @param threadMediaToSlider Play true, Pause false - Thread media to slider
   */
  private void helperPlayButton(boolean botonOriginal,
          boolean scrollToMarkers, String AccionMedia,
          String ImageButton, String ImageButtonOriginal,
          String threadMediaToSlider)
   {
    //  Stopping / Playing the original pause button mode
    originalButton = botonOriginal;

    // Enable the scroll to markers
    playButtonBoolean = scrollToMarkers;

    // Play, stop, pause the media
    switch (AccionMedia) {
      case "play": {
        mediaPlayerPlay();
        break;
      }
      case "pause": {
        mediaPlayerPause();
        break;
      }
      default:
        mediaPlayerStop();
        break;
    }

    // Image play / pause button
    if (ImageButton.equals("play")) playedImageButton();
    else pausedImageButton();

    // Image play / pause button
    if (ImageButtonOriginal.equals("play")) playedImageButtonOriginal();
    else pausedImageButtonOriginal();

    // Thread media to slider
    if (threadMediaToSlider.equals("play")) {
      mediaToSliderPlay();
    } else mediaToSliderPause();
   }



  /**
   * handle of the
   */
  @FXML
  private void handleBackButton()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    if (indexItemV > 0) {
      // Back the index
      indexItemV--;

      backForward();
    }
   }


  /**
   * handle of the
   */
  @FXML
  private void handleForwardButton()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    try {
      if (indexItemV < itemsOriginal.length - 2) {
        // forward the index
        indexItemV++;

        backForward();
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleForwardButton()", e.toString(), e);
    }
   }


  /**
   * This method complements the back and forward button
   */
  private void backForward()
   {

    // index of listview to zero
    listViewV.scrollTo(indexItemV);
    listViewV.getSelectionModel().select(indexItemV);
    // putting in listviewH the phases
    showListViewH();

    // 
    start = itemsOriginal[indexItemV].getStart();
    end = duration.toMillis();
    mediaPlayer.setStartTime(Duration.seconds(start));
    mediaPlayer.setStopTime(Duration.seconds(end));
    // Label time to cero
    duration = mediaPlayer.getMedia().getDuration();
    FormatTime ft = new FormatTime();
    timeLabelReading.setText(ft.formatting(Duration.seconds(start), duration));

    // Enable the scroll to markers
    playButtonBoolean = true;
    // Stoping the original pause button
    originalButton = false;
    //Thread media to slider
    mediaToSliderPlay();

    mediaPlayerSlider = "slider";
    updateValuesSlider();
    mediaPlayerSlider = "media";
   }


  /**
   * Controller for the stop button
   */
  @FXML
  public void handleStopButton()
   {
    try {
      if (mediaPlayer == null) return; // if doesn't exits a media return

      mediaPlayerStop(); // The normal stop
      mediaToSliderPause(); // thread media to slider

      mediaSliderReading.setDisable(false);
      mediaSliderWriting.setDisable(false);
      mediaSliderTranslation.setDisable(false);

      mediaPlayer.setStartTime(Duration.ZERO);
      mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());

      // index of listview to zero
      indexItemV = 0;
      listViewV.scrollTo(0);
      listViewV.getSelectionModel().select(0);

      // putting in listviewH the phases
      showListViewH();
      duration = mediaPlayer.getMedia().getDuration();
      // SliderMedia to Zero
      setTicksSliderMedia(mediaSliderReading, 100);
      setTicksSliderMedia(mediaSliderWriting, 100);
      setTicksSliderMedia(mediaSliderTranslation, 100);
      // Label time to cero
      FormatTime ft = new FormatTime();
      timeLabelReading.setText(ft.formatting(Duration.ZERO, duration));
      // Enable the scroll to markers
      playButtonBoolean = true;
      // Stoping the original pause button
      originalButton = false;
      // The first lap of the slider
      mediaPlayerSlider = "media"; // to the first lap
      // Change the image buttons to play
      Platform.runLater(() -> {
        playedImageButton();
        playedImageButtonOriginal();
      });

    } catch (ArrayIndexOutOfBoundsException ex) {
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleStopButton()", e.toString(), e);
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Play media original">

  /**
   * Handle of the Play Button Item
   */
  @FXML
  public void handlePlayButtonItemOriginal()
   {
    try {

      if (mediaPlayer == null) return; // if doesn't exits a media return

      mediaSliderReading.setDisable(true);
      mediaSliderWriting.setDisable(true);
      mediaSliderTranslation.setDisable(true);
      getIndexitemV();

      start = itemsOriginal[indexItemV].getStart();
      end = itemsOriginal[indexItemV].getEnd();
      mediaPlayer.setStartTime(Duration.seconds(start));
      mediaPlayer.setStopTime(Duration.seconds(end));

      // it´s used for don´t scroll when it´s playing an itemInicio
      playButtonBoolean = false;
      //Thread media to slider
      mediaToSliderPlay();

      // Si le doy al play frase pero ya está reproduciendo en normal
      if (!originalButton) {
        originalButton = true;
        pausedImageButtonOriginal();
        playedImageButton();
        mediaPlayerPlay();
        mediaToSliderPlay();
        return;
      }
      // originalButton = true;
      if (currentPauseItem == indexItemV) {
        switch (status) {
          case PAUSED:
            pausedImageButtonOriginal();
            playedImageButton();
            mediaPlayer.setStartTime(currentOriginal);
            mediaPlayer.seek(currentOriginal);
            mediaPlayer.setStopTime(Duration.seconds(end));
            mediaPlayerPlay();

            break;
          case PLAYING:
            playedImageButtonOriginal();
            playedImageButton();
            currentOriginal = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(currentOriginal);
            mediaPlayerPause();
            break;
          case STOPPED:
          case READY:
            pausedImageButtonOriginal();
            playedImageButton();
            mediaPlayerPlay();
            break;
          default:
            break;
        }
      } else {
        // Setting the old value like the new value
        currentPauseItem = indexItemV;
        pausedImageButtonOriginal();
        playedImageButton();
        getIndexitemV();
        currentOriginal = new Duration(itemsOriginal[indexItemV].getStart());
        mediaPlayerPlay();
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handlePlayButtonItemOriginal()", e.toString(), e);
    }
   }


  /**
   * handle of the
   */
  @FXML
  private void handleBackButtonItemOriginal()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    if (originalButton) {
      if (mediaPlayer.getCurrentTime()
              .greaterThanOrEqualTo(Duration.seconds(start)
                      .add(Duration.millis(backForw)))) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.
                millis(backForw)));
        currentOriginal = mediaPlayer.getCurrentTime();
      }
    }
   }


  /**
   * handle of the
   */
  @FXML
  private void handleForwardButtonItemOriginal()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    if (originalButton) {
      if (mediaPlayer.getCurrentTime()
              .lessThanOrEqualTo(Duration.seconds(end)
                      .subtract(Duration.millis(backForw)))) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.
                millis(backForw)));
        currentOriginal = mediaPlayer.getCurrentTime();
      }
    }
   }


  /**
   * Handle of the Button Stop Item Original
   */
  @FXML
  private void handleStopButtonItemOriginal()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    mediaSliderReading.setDisable(false);
    mediaSliderWriting.setDisable(false);
    mediaSliderTranslation.setDisable(false);
    if (mediaPlayer == null) return; // if doesn't exits a media return

    if (originalButton) {
      mediaPlayerStop();
      // Change the image buttons to play
      playedImageButton();
      playedImageButtonOriginal();
      //Thread media to slider
      mediaToSliderPlay();
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Play media virtual">

  /**
   *
   */
  @FXML
  private void handlePlayButtonItemVirtual()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    Status s = virtual.getStatus();
    if (s.equals(Status.PAUSED) || s.equals(Status.STOPPED) || s.equals(Status.READY)) {
      virtual.play();
      pausedImageButtonVirtual();
    } else {
      virtual.pause();
      playedImageButtonVirtual();
    }
   }


  /**
   *
   */
  @FXML
  private void handleBackButtonItemVirtual()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    Duration d = virtual.getCurrentTime();
    Duration s = startVirtual;

    if (!virtual.getStatus().equals(Status.PLAYING)) return; // if doesn't exits a media return
    if (d.greaterThanOrEqualTo(startVirtual.add(Duration.millis(backForw)))) {
      virtual.seek(virtual.getCurrentTime().subtract(Duration.millis(backForw)));
    }
   }


  /**
   *
   */
  @FXML
  private void handleForwardButtonItemVirtual()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    if (!virtual.getStatus().equals(Status.PLAYING)) return; // if doesn't exits a media return
    if (virtual.getCurrentTime()
            .lessThanOrEqualTo(endVirtual.subtract(Duration.millis(backForw / 2)))) {
      virtual.seek(virtual.getCurrentTime().add(Duration.millis(backForw / 2)));
    }
   }


  /**
   *
   */
  @FXML
  private void handleStopButtonItemVirtual()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    virtual.stop();
    playedImageButtonVirtual();
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Play media translated virtual">

  /**
   *
   */
  @FXML private void handlePlayButtonItemTranslatedVirtual()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    Status s = translatedVirtual.getStatus();
    if (s.equals(Status.PAUSED) || s.equals(Status.STOPPED) || s.equals(Status.READY)) {
      translatedVirtual.play();
      pausedImageButtonTranslatedVirtual();
    } else {
      translatedVirtual.pause();
      playedImageButtonTranslatedVirtual();
    }
   }


  /**
   *
   */
  @FXML
  private void handleBackButtonItemTranslatedVirtual()
   {
    // if doesn't exits a media return // if doesn't exits a media return
    if (mediaPlayer == null || translatedVirtual == null) return;

    if (translatedVirtual.getCurrentTime()
            .greaterThanOrEqualTo(startTranslatedVirtual.add(Duration.millis(backForw)))) {
      translatedVirtual.seek(translatedVirtual.getCurrentTime()
              .subtract(Duration.millis(backForw)));
    }
   }


  /**
   *
   */
  @FXML
  private void handleForwardButtonItemTranslatedVirtual()
   {
    if (mediaPlayer == null || translatedVirtual == null) return;
    if (translatedVirtual.getCurrentTime()
            .lessThanOrEqualTo(endTranslatedVirtual.subtract(Duration.millis(backForw / 2)))) {
      translatedVirtual.seek(translatedVirtual.getCurrentTime()
              .add(Duration.millis(backForw / 2)));
    }
   }


  /**
   *
   */
  @FXML
  private void handleStopButtonItemTranslatedVirtual()
   {
    if (mediaPlayer == null || translatedVirtual == null) return;

    translatedVirtual.stop();
    playedImageButtonTranslatedVirtual();
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Play words machine listview">

  /**
   * Handle of the Button Play Item Machine
   */
  @FXML private void handlePlayButtonItemOriginalMachine(ListView lv)
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    // Create an Observablelist to listview H    
    ObservableList olItems = lv.getSelectionModel().getSelectedItems();
    int dest = (lv == listViewH01Reading) ? subOrig : subTrans;

    if (olItems.size() > 0) {
      // Size of the list
      int size = olItems.size();
      int cont = 0;
      // Create an array of string whera I´ll put the texts of the listview H
      String[] text = new String[size];
      // This is to fix the forbbiden name con. in windows
      for (Object item : olItems) {
        switch (item.toString()) {
          case "con":
            text[cont] = "connn";
            cont++;
            break;
          case "aux":
            text[cont] = "auxxx";
            cont++;
            break;
          default:
            text[cont] = item.toString();
            cont++;
            break;
        }
      }
      // Play the audioclips one behind the other
      audioClipsWords[dest].getAudioClip().get(text[0]).play();
      if (size > 1) {
        Thread audio = new Thread(new Runnable()
         {

          @Override
          public void run()
           {
            exitLoop = false;
            for (int i = 1; i < size; i++) {
              if (exitLoop) {
                break;
              }
              while (audioClipsWords[dest].getAudioClip().get(text[i - 1]).isPlaying()) {
              }
              audioClipsWords[dest].getAudioClip().get(text[i]).play();
            }
           }


         });
        audio.start();
      }
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Correction of writting and transtlation">

  /**
   * Setting the Correction button
   */
  @FXML
  private void handlecorrectionButtonWriting()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    handleCorrectionButton(textFieldWriting);
   }


  /**
   * Setting the correction Button Translation
   */
  @FXML private void handlecorrectionButtonTranslation()
   {
    if (mediaPlayer == null) return; // if doesn't exits a media return

    handleCorrectionButton(textFieldTranslation);
   }


  /**
   * handle of the
   */
  private void handleCorrectionButton(TextField tf)
   {
    try {
      if (mediaPlayer == null) return; // if doesn't exits a media return
      SaveWordsAsList swal = new SaveWordsAsList();

      listViewH02Reading.setVisible(true);
      getIndexitemV();
      markerTextOriginal = itemsOriginal[indexItemV].getText();
      markerTextTranslation = itemsTranslation[indexItemV].getText();
      // Extract the orignial text and the answer
      if (currentTab.equals(escribir)) {
        itemWordsOriginal = swal.cleanWords(markerTextOriginal);
      } else if (currentTab.equals(traducir)) {
        itemWordsOriginal = swal.cleanWords(markerTextTranslation);
      }
      // Count the letters within blanc spaces
      if (!tf.getText().isEmpty() && tf.getText().trim().length() > 0) {
        itemWordsTranslation = swal.cleanWords(tf.getText());
      }
      // Filling listViewH02Writing  with words
      ObservableList<String> listItemOriginal = FXCollections.observableArrayList();
      ObservableList<String> listItemTranslation = FXCollections.observableArrayList();
      // Filling the list
      listItemOriginal.addAll(Arrays.asList(itemWordsOriginal));
      listItemTranslation.addAll(Arrays.asList(itemWordsTranslation));
      if (tf.getText().isEmpty() || tf.getText().trim().length() <= 0) {
        return;
      }
      // Calculating success
      success = 0;
      double words;
      double wordsPoints;
      double letters;
      double letterPoints;
      String[] size;
      int sizePhrase;
      // calculating who phrase has less words
      words = itemWordsOriginal.length;
      wordsPoints = 100 / words;
      //System.out.println("palabras " + words);
      //System.out.println("Puntos por palabra " + wordsPoints);
      int pOri = itemWordsOriginal.length;
      int pTra = itemWordsTranslation.length;
      if (pOri < pTra) {
        sizePhrase = itemWordsOriginal.length;
        words = words - (words / 100);
      } else if (pOri > pTra) {
        sizePhrase = itemWordsTranslation.length;
        words = words - (words / 100);
      } else {
        sizePhrase = itemWordsTranslation.length;
      }
      //System.out.println("Se elige para comparar la frase con " + sizePhrase + " palabras");
      //System.out.println("Puntos por palabra con rebaja " + wordsPoints);
      // Comparing words
      for (int i = 0; i < sizePhrase; i++) {
        // Calculing how points has every letter
        letters = itemWordsOriginal[i].length();
        letterPoints = wordsPoints / letters;
        //System.out.println("letras en la palabra " + i + " -  " + letters);
        //System.out.println("puntos por letra " + letterPoints);
        // calculating who word has less letters
        int sOri = itemWordsOriginal[i].length();
        int sTra = itemWordsTranslation[i].length();
        if (sOri < sTra) {
          size = itemWordsOriginal;
          letterPoints = letterPoints - (letterPoints / 10);
        } else if (sOri > sTra) {
          size = itemWordsTranslation;
          letterPoints = letterPoints - (letterPoints / 10);
        } else {
          size = itemWordsTranslation;
        }
        //System.out.println("puntos por letra con rebaja " + letterPoints);
        for (int j = 0; j < size[i].length(); j++) {
          String l1 = String.valueOf(itemWordsOriginal[i].charAt(j));
          String l2 = String.valueOf(itemWordsTranslation[i].charAt(j));
          if (l1.equals(l2)) {
            success += letterPoints;
          }
        }
      }
      // put the result in the progress indicator
      success /= 100;
      Platform.runLater(() -> {
        if (currentTab.equals(escribir)) {
          indicatorSuccessWriting.setProgress(success);
        } else if (currentTab.equals(traducir)) {
          indicatorSuccessTranslation.setProgress(success);
        }
        trans = trans.replaceAll("Menu", "");
        mainScene.handleUpdateCorrection(trans.concat(".json"), currentTab, indexItemV, success);
      });
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleCorrectionButton()", e.toString(), e);
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Setting imagenes">

  /**
   * Setting the image int the buttons
   */
  private void setImageButton()
   {
    try {
      String[] ruta = {
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "comprobar.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "play.png", "pause.png", "back.png", "forward.png", "stop.png",
        "comprobar.png"
      };
      imageViews = new ImageView[ruta.length];
      for (int i = 0; i < imageViews.length; i++) {
        Image image = new Image(getClass().getResource("/LanguageApp/resources/images/" + ruta[i]).toExternalForm());
        imageViews[i] = new ImageView(image);
        imageViews[i].setFitWidth(36);
        imageViews[i].setFitHeight(36);
      }
      // Play and pause have the same image     
      /* --------  Reading ----------*/
      playButtonReading.setGraphic(imageViews[0]);
      backButtonReading.setGraphic(imageViews[2]);
      forwardButtonReading.setGraphic(imageViews[3]);
      stopButtonReading.setGraphic(imageViews[4]);
      playButtonItemOriginalReading.setGraphic(imageViews[5]);
      backButtonItemOriginalReading.setGraphic(imageViews[7]);
      forwardButtonItemOriginalReading.setGraphic(imageViews[8]);
      stopButtonItemOriginalReading.setGraphic(imageViews[9]);
      playButtonItemVirtualReading.setGraphic(imageViews[10]);
      backButtonItemVirtualReading.setGraphic(imageViews[12]);
      forwardButtonItemVirtualReading.setGraphic(imageViews[13]);
      stopButtonItemVirtualReading.setGraphic(imageViews[14]);
      playButtonItemVirtualTranslatedReading.setGraphic(imageViews[15]);
      backButtonItemVirtualTranslatedReading.setGraphic(imageViews[17]);
      forwardButtonItemVirtualTranslatedReading.setGraphic(imageViews[18]);
      stopButtonItemVirtualTranslatedReading.setGraphic(imageViews[19]);

      /* --------  Writting ----------*/
      playButtonWriting.setGraphic(imageViews[20]);
      backButtonWriting.setGraphic(imageViews[22]);
      forwardButtonWriting.setGraphic(imageViews[23]);
      stopButtonWriting.setGraphic(imageViews[24]);
      playButtonItemOriginalWriting.setGraphic(imageViews[25]);
      backButtonItemOriginalWriting.setGraphic(imageViews[27]);
      forwardButtonItemOriginalWriting.setGraphic(imageViews[28]);
      stopButtonItemOriginalWriting.setGraphic(imageViews[29]);
      playButtonItemVirtualWriting.setGraphic(imageViews[30]);
      backButtonItemVirtualWriting.setGraphic(imageViews[32]);
      forwardButtonItemVirtualWriting.setGraphic(imageViews[33]);
      stopButtonItemVirtualWriting.setGraphic(imageViews[34]);
      playButtonItemVirtualTranslatedWriting.setGraphic(imageViews[35]);
      backButtonItemVirtualTranslatedWriting.setGraphic(imageViews[37]);
      forwardButtonItemVirtualTranslatedWriting.setGraphic(imageViews[38]);
      stopButtonItemVirtualTranslatedWriting.setGraphic(imageViews[39]);
      correctionButtonWriting.setGraphic(imageViews[40]);

      /* --------  Translation ----------*/
      playButtonTranslation.setGraphic(imageViews[41]);
      backButtonTranslation.setGraphic(imageViews[43]);
      forwardButtonTranslation.setGraphic(imageViews[44]);
      stopButtonTranslation.setGraphic(imageViews[45]);
      playButtonItemOriginalTranslation.setGraphic(imageViews[46]);
      backButtonItemOriginalTranslation.setGraphic(imageViews[48]);
      forwardButtonItemOriginalTranslation.setGraphic(imageViews[49]);
      stopButtonItemOriginalTranslation.setGraphic(imageViews[50]);
      playButtonItemVirtualTranslation.setGraphic(imageViews[51]);
      backButtonItemVirtualTranslation.setGraphic(imageViews[53]);
      forwardButtonItemVirtualTranslation.setGraphic(imageViews[54]);
      stopButtonItemVirtualTranslation.setGraphic(imageViews[55]);
      playButtonItemVirtualTranslatedTranslation.setGraphic(imageViews[56]);
      backButtonItemVirtualTranslatedTranslation.setGraphic(imageViews[58]);
      forwardButtonItemVirtualTranslatedTranslation.setGraphic(imageViews[59]);
      stopButtonItemVirtualTranslatedTranslation.setGraphic(imageViews[60]);
      correctionButtonTranslation.setGraphic(imageViews[61]);
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setImageButton()", e.toString(), e);
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Setting virtual media">

  /**
   *
   */
  private void setVirtualMedia()
   {
    if (phraseString == null || audioClipsPhrases == null) return;

    String virtualString = phraseString[subOrig][indexItemV];
    virtual = audioClipsPhrases[subOrig].getAudioMedia().get(virtualString);

    String virtualTranslateString = phraseString[subTrans][indexItemV];
    translatedVirtual = audioClipsPhrases[subTrans].getAudioMedia().get(virtualTranslateString);

    startVirtual = new Duration(virtual.getStartTime().toMillis());
    endVirtual = new Duration(virtual.getStopTime().toMillis());
    startTranslatedVirtual = new Duration(virtual.getStartTime().toMillis());
    endTranslatedVirtual = new Duration(virtual.getStopTime().toMillis());

    virtual.setOnEndOfMedia(() -> {
      virtual.stop();
      virtual.seek(startVirtual);
      playedImageButtonVirtual();
    });
    translatedVirtual.setOnEndOfMedia(() -> {
      translatedVirtual.stop();
      translatedVirtual.seek(startVirtual);
      playedImageButtonTranslatedVirtual();
    });
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Cambio de imagenes play - pause">

  /**
   *
   */
  private void playedImageButton()
   {
    Platform.runLater(() -> {
      playButtonReading.setGraphic(imageViews[0]);
      playButtonWriting.setGraphic(imageViews[20]);
      playButtonTranslation.setGraphic(imageViews[41]);
    });
   }


  /**
   *
   */
  private void pausedImageButton()
   {
    Platform.runLater(() -> {
      playButtonReading.setGraphic(imageViews[1]);
      playButtonWriting.setGraphic(imageViews[21]);
      playButtonTranslation.setGraphic(imageViews[42]);
    });
   }


  /**
   * Change the image of the play / pause button
   */
  private void playedImageButtonOriginal()
   {
    Platform.runLater(() -> {
      playButtonItemOriginalReading.setGraphic(imageViews[5]);
      playButtonItemOriginalWriting.setGraphic(imageViews[25]);
      playButtonItemOriginalTranslation.setGraphic(imageViews[46]);
    });
   }


  /**
   * Change the image of the play / pause button
   */
  private void pausedImageButtonOriginal()
   {
    Platform.runLater(() -> {
      playButtonItemOriginalReading.setGraphic(imageViews[6]);
      playButtonItemOriginalWriting.setGraphic(imageViews[26]);
      playButtonItemOriginalTranslation.setGraphic(imageViews[47]);
    });
   }


  /**
   * Change the image of the play / pause button
   */
  private void playedImageButtonVirtual()
   {
    Platform.runLater(() -> {
      playButtonItemVirtualReading.setGraphic(imageViews[10]);
      playButtonItemVirtualWriting.setGraphic(imageViews[30]);
      playButtonItemVirtualTranslation.setGraphic(imageViews[51]);
    });
   }


  /**
   * Change the image of the play / pause button
   */
  private void pausedImageButtonVirtual()
   {
    Platform.runLater(() -> {
      playButtonItemVirtualReading.setGraphic(imageViews[11]);
      playButtonItemVirtualWriting.setGraphic(imageViews[31]);
      playButtonItemVirtualTranslation.setGraphic(imageViews[52]);
    });
   }


  /**
   * Change the image of the play / pause button
   */
  private void playedImageButtonTranslatedVirtual()
   {
    Platform.runLater(() -> {
      playButtonItemVirtualTranslatedReading.setGraphic(imageViews[15]);
      playButtonItemVirtualTranslatedWriting.setGraphic(imageViews[35]);
      playButtonItemVirtualTranslatedTranslation.setGraphic(imageViews[56]);
    });
   }


  /**
   * Change the image of the play / pause button
   */
  private void pausedImageButtonTranslatedVirtual()
   {
    Platform.runLater(() -> {
      playButtonItemVirtualTranslatedReading.setGraphic(imageViews[16]);
      playButtonItemVirtualTranslatedWriting.setGraphic(imageViews[36]);
      playButtonItemVirtualTranslatedTranslation.setGraphic(imageViews[57]);
    });
   }

  //</editor-fold>

//</editor-fold>    

//<editor-fold defaultstate="collapsed" desc="ProgressBar">

  /**
   *
   * @param longitud double. El número de veces que voy a llamar la función, para que calcule la lonjitud
   * @param text String. The text to show.
   */
  private void setProgressBar(double longitud, String text) throws InterruptedException
   {
    //System.out.println("cont " + cont++ + " " + text);
    progressBarStep = longitud;
    mainScene.setLabelText(text);
    mainScene.setProgressBarValue(progressBarStep);
    /*/*Thread.sleep(2000); */
   }


  /**
   *
   * @param progressBarValue
   */
  public void setProgressBarValue(DoubleProperty progressBarValue)
   {
    this.progressBarValue.setValue(progressBarValue.getValue());
    //System.out.println("welcome " + this.progressBarValue.getValue());
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="setting Sliders Volume, Rate, Balance">

  /**
   * handler for sliders
   */
  public void setSlider()
   {
    // Slider tab Reading
    setSliderForm(volumeSliderReading, 0.0, 100.0, 20.0, 20, 2);
    volumeLabelReading.setText("20,00"); // initial value
    setSliderForm(rateSliderReading, 0.80, 1.0, 1.0, 0.02, 1);
    rateLabelReading.setText("1,00"); // initial value
    setSliderForm(balanceSliderReading, -1.0, 1.0, 0.0, 1, 10);
    balanceLabelReading.setText("0,00"); // initial value
    // Slider tab Writing
    setSliderForm(volumeSliderWriting, 0.0, 100.0, 20.0, 20, 2);
    volumeLabelWriting.setText("20,00"); // initial value
    setSliderForm(rateSliderWriting, 0.80, 1.0, 1.0, 0.02, 1);
    rateLabelWriting.setText("1,00"); // initial value
    setSliderForm(balanceSliderWriting, -1.0, 1.0, 0.0, 1, 10);
    balanceLabelWriting.setText("0,00"); // initial value   
    // Slider tab Translation
    setSliderForm(volumeSliderTranslation, 0.0, 100.0, 20.0, 20, 2);
    volumeLabelTranslation.setText("20,00"); // initial value
    setSliderForm(rateSliderTranslation, 0.80, 1.0, 1.0, 0.02, 1);
    rateLabelTranslation.setText("1,00"); // initial value
    setSliderForm(balanceSliderTranslation, -1.0, 1.0, 0.0, 1, 10);
    balanceLabelTranslation.setText("0,00"); // initial value    
   }


  /**
   * Setting the Sliders Volume, Rate, Balance
   *
   * @param slider The slider to set
   * @param min The minimum value
   * @param max The maximum value
   * @param initial The initial value
   * @param majorTick The numbers of major ticks
   * @param minorTick The numbers of minor ticks
   */
  private void setSliderForm(Slider slider, double min, double max,
          double initial, double majorTick, int minorTick)
   {

    // min, max and initial value
    slider.setMin(min);
    slider.setMax(max);
    slider.setValue(initial);
    slider.setBlockIncrement(10.1);
    // incicates whether the value of the slide should be aligned with the
    // tick marks.
    slider.setSnapToTicks(false);
    // show tick marks and numbers label
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    // Setting the majot ticks ( total marks = value maximo / majorTickUnit)
    slider.setMajorTickUnit(majorTick);
    // Setting the numbers of minor ticks between two major tick
    slider.setMinorTickCount(minorTick);

    // Setting the event listener
    slider.valueProperty().addListener((Observable, oldValue, newValue) ->
    {
      try {
        if (mediaPlayer == null) return; // if doesn't exits a media return

        Double doubleValue = newValue.doubleValue();// Number to Double
        String value = String.valueOf(doubleValue);// Double to string
        value = value.format("%.2f", newValue);// to String.format

        if (slider == volumeSliderReading) {
          volumeLabelReading.setText(value);
        } else if (slider == rateSliderReading) {
          rateLabelReading.setText(value);
        } else if (slider == balanceSliderReading) {
          balanceLabelReading.setText(value);
        }
      } catch (Exception e) {
        message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setSliderForm()", e.toString(), e);
      }
    });
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting the media">

  //<editor-fold defaultstate="collapsed" desc="Setting media">

  /**
   *
   * Setting the media
   *
   */
  public void setMediaPlayer()
   {
    try {
      // Create a Media
      media = new Media(mediaStringUrl.toString());
      // Create a Media Player
      mediaPlayer = new MediaPlayer(media);
      // Assigning a mediaplayera to the mediaview
      mediaView.setMediaPlayer(mediaPlayer);
      // Preserving the ratio of the media
      mediaView.setPreserveRatio(true);
      // Configuring the rate
      mediaPlayer.setRate(rateSliderReading.getValue());
      mediaPlayer.setBalance(balanceSliderReading.getValue());
      mediaPlayer.setVolume(volumeSliderReading.getValue());
      mediaPlayer.rateProperty().bind(rateSliderReading.valueProperty());
      mediaPlayer.balanceProperty().bind(balanceSliderReading.valueProperty());
      mediaPlayer.volumeProperty().bind(volumeSliderReading.valueProperty().divide(100));


      // Set the markers on the media
      setMarkers(itemsOriginal);
      // Setting a markers listeners to the media
      setEventMarker();
      // setting the playback errors
      setMediaPlayerChangeListener();

      // Starting the Thread media to slider
      /*-- if (mediaToSlideThread.currentThread().getState().equals(Thread.State.RUNNABLE)) {}
      mediaToSliderPlay();    */

      // setting the playback errors
      setError();
      // Setting the end time of the file and fixing the problem with the freezing media
      currentTime = Duration.ZERO;
      setEndTimefile();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setMediaPlayer()", e.toString(), e);
    }
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Play / Pause / Stop MediaPlayer events to Slider">

  /**
   *
   */
  private synchronized void mediaToSliderPlay()
   {
    mediaToSlider = "play";
    notify();
   }


  /**
   *
   */
  private synchronized void mediaToSliderPause()
   {
    mediaToSlider = "pause";
    notify();
   }


  /**
   *
   */
  private synchronized void mediaToSliderStop()
   {
    mediaToSlider = "stop";
    notify();
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Get Media Status">
  /**
   * Return the status of the mediaPlayer
   *
   * @return
   */
  public String getMediaStatus()
   {
    try {

      if (status.equals(Status.PLAYING)) {
        if (originalButton) {
          return "playingOriginal";
        } else {
          return "playing";
        }
      } else if (status.equals(Status.PAUSED)) {
        if (originalButton) {
          return "pauseOriginal";
        } else {
          return "pause";
        }
      } else {
        return "stop";
      }

    } catch (NullPointerException ex) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / getMediaStatus()", ex.toString(), ex);
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / getMediaStatus()", e.toString(), e);
    }
    return "stop";
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="mediaPlayer Play / Pause / Stop">

  /**
   *
   */
  private void mediaPlayerPlay()
   {
    mediaPlayer.play();
    status = Status.PLAYING;
   }


  /**
   *
   */
  private void mediaPlayerStop()
   {
    mediaPlayer.stop();
    status = Status.STOPPED;
   }


  /**
   *
   */
  private void mediaPlayerPause()
   {
    mediaPlayer.pause();
    status = Status.PAUSED;

   }

  //</editor-fold>

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting Audioclips">

  /**
   * Setting the audioclips
   *
   * @param set a Set of String, result of creating a list of words of the media
   * @param lastdirectory
   * @param rateSlider
   * @param balanceSlider
   * @param volumeSlider
   * @param lastFile
   * @return A map with the name and the Audioclip
   */
  public void setAudioClip(AudioClip audioClips, Slider rateSlider, Slider balanceSlider, Slider volumeSlider)
   {

    try {

      audioClips.setRate(rateSlider.getValue());
      audioClips.setBalance(balanceSlider.getValue());
      audioClips.setVolume(volumeSlider.getValue());
      audioClips.rateProperty().bind(rateSlider.valueProperty());
      audioClips.balanceProperty().bind(balanceSlider.valueProperty());
      audioClips.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

    } catch (Exception e) {

      Platform.runLater(() -> {
        message.message(Alert.AlertType.ERROR, "Error message",
                "PrincipalController / setAudioClip()", e.toString(), e);
      });
    }
    System.gc();
    System.runFinalization();

   }


  /**
   *
   * @param set
   * @param lastdirectory
   * @param rateSlider
   * @param balanceSlider
   * @param volumeSlider
   * @return
   */
  public Map<String, MediaPlayer> setAudioMedia(String[] set, String lastdirectory, Slider rateSlider, Slider balanceSlider, Slider volumeSlider)
   {
    // An unique audioclip
    MediaPlayer me;

    String s;

    Map<String, MediaPlayer> audioMedia = new HashMap<>();

    String audioError;

    String se = System.getProperty("file.separator");

    URI resource;
    audioMedia.clear();

    audioError = null;

    se = System.getProperty("file.separator");

    try {
      for (String ws : set) {

        // This is to fix the forbbiden name con. in windows
        if (ws.equals("con")) ws = "connn";
        if (ws.equals("aux")) ws = "auxxx";

        audioError = ws; // if this audid doesn´t exist I show it in an message.

        s = lastdirectory + se + ws + ".mp3";

        resource = new File(s).toURI();

        me = new MediaPlayer(new Media(resource.toString()));

        audioMedia.put(ws, me);
        // me.dispose();
        // me = null;
        audioMedia.get(ws).setRate(rateSlider.getValue());
        audioMedia.get(ws).setBalance(balanceSlider.getValue());
        audioMedia.get(ws).setVolume(volumeSlider.getValue());
        audioMedia.get(ws).rateProperty().bind(rateSlider.valueProperty());
        audioMedia.get(ws).balanceProperty().bind(balanceSlider.valueProperty());
        audioMedia.get(ws).volumeProperty().bind(volumeSlider.valueProperty().divide(100));
      }
    } catch (Exception e) {
      final String error = audioError;
      Platform.runLater(() -> {
        message.message(Alert.AlertType.ERROR, "Error message", "Falta el audio: \"" +
                error + "\"", "AudioClips.java / setAudioClip()", e);
      });
    }

    System.gc();
    System.runFinalization();
    return audioMedia;
   }


//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting the listView">

  /**
   *
   */
  private void changeListviewBySliderMedia(double positionMouse)
   {
    int idInicio = 0, idFinal, contInicio, contFinal, itemInicio, itemFinal, suplenteInicio, suplenteFinal;
    double positionSlider, totalDuration;

    //positionSlider = mediaSliderReading.getValue();
    totalDuration = duration.toMillis();
    //sliderMediaCurrent = (positionSlider * totalDuration) / 100000;
    positionMouse = (positionMouse * 100) / 367;
    sliderMediaCurrent = (positionMouse * totalDuration) / 100000;
    double inicio, fin;
    int lonjInicio = itemsOriginal.length - 1;
    int lonjFinal = 0;

    itemInicio = 0;
    itemFinal = lonjInicio;
    suplenteInicio = 0;
    suplenteFinal = lonjFinal;
    contInicio = 2;
    contFinal = 2;
    int salida = 0;
    try {

      do {
        idInicio = itemsOriginal[itemInicio].getId();
        idFinal = itemsOriginal[itemFinal].getId();
        inicio = itemsOriginal[itemInicio].getStart();
        fin = itemsOriginal[itemFinal].getStart();

        /*System.out.println("item " + itemInicio + " - " + itemFinal +
                "  lonj " + lonjInicio + " - " + ((itemsOriginal.length - 1) - lonjFinal) +
                " inicio " + inicio + " " + sliderMediaCurrent + " " + fin +
                " cont " + contInicio + " - " + contFinal +
                " itemFinal-itemInicio " + (itemFinal - itemInicio));*/
        if (inicio < sliderMediaCurrent) {
          suplenteInicio = itemInicio;
          contInicio = 2;
          itemInicio = itemInicio + ((lonjInicio - itemInicio) / contInicio);
        } else {
          contInicio = (contInicio > 15) ? 15 : contInicio++;
          lonjInicio = itemInicio;
          itemInicio = suplenteInicio + ((lonjInicio - suplenteInicio) / contInicio);
        }
        if (fin > sliderMediaCurrent) {
          suplenteFinal = itemFinal;
          contFinal = 2;
          itemFinal = itemFinal - ((itemFinal - lonjFinal) / contFinal);
        } else {
          contFinal = (contFinal >= 15) ? 15 : contFinal++;
          lonjFinal = itemFinal;
          itemFinal = suplenteFinal - ((suplenteFinal - lonjFinal) / contFinal);
        }

        // Check if the sliderMedia is minor or mayor to the star or end
        if (sliderMediaCurrent < itemsOriginal[0].getStart()) salida = 1;
        if (sliderMediaCurrent > itemsOriginal[itemsOriginal.length - 1].getStart()) salida = 2;

      } while (!((sliderMediaCurrent >= inicio && sliderMediaCurrent <= fin) &&
              ((lonjInicio - lonjFinal) <= 3 && (lonjInicio - lonjFinal) >= 0) ||
              ((salida != 0))));

      // System.out.println("idInicio " + idInicio + " idFinal " + idFinal + " salida " + salida);

      if (salida == 2 || idFinal < idInicio) idInicio = idFinal;

      // System.out.println("idInicio " + idInicio + " idFinal " + idFinal + " salida " + salida);

      // Scrolling the listview
      listViewV.scrollTo(idInicio);
      // Selecting Item of the listViewV
      listViewV.getSelectionModel().select(idInicio);
      // Setting the global index of the itemInicio
      indexItemV = idInicio;
      // Filling the listviewH
      showListViewH();

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / changeListviewBySliderMedia()", e.toString(), e);
    }
   }


  /**
   *
   * @param trans
   */
  public void changeListViewByFlag(String trans)
   {
    if (mediaPlayer == null) return;

    this.trans = trans;

    trans = trans.replaceAll("Menu", "");

    subTrans = Tools.getIndex(subtitle, trans);

    itemsTranslation = idiomas[subTrans];

    //changeListview(trans); // Reuse changeListView to show itemTranslation
    if (currentTab.equals(leer) || currentTab.equals(traducir)) {
      FillListView flw = new FillListView();
      flw.setListView(listViewV, itemsOriginal);
      showListViewH();
    }
    if (currentTab.equals(escribir)) {
      FillListView flw = new FillListView();
      flw.setListView(listViewV, itemsTranslation);
      showListViewH();
    }
   }


  /**
   * This method is use to change the listViewV depend the text (leee, escribir, traducir)
   *
   * @param text
   */
  private void changeListview(String text)
   {
    if (mediaPlayer == null) return;

    getIndexitemV();

    if (text.equals(leer)) {
      oldNode = rateSliderReading;
      if (mediaPlayer != null) { // if doesn't exits a media return
        FillListView flw = new FillListView();
        flw.setListView(listViewV, itemsOriginal);
        currentTab = leer;
      }

    } else if (text.equals(escribir)) {
      oldNode = rateSliderWriting;
      if (mediaPlayer != null) { // if doesn't exits a media return
        currentTab = escribir;
        // Setting the listViewH itemInicio
        FillListView flw = new FillListView();
        flw.setListView(listViewV, itemsTranslation);
        /*/*
        tabPanelListViewH.getSelectionModel().clearAndSelect(1);// if comes from flag menuItem
        tabPanelListViewH.requestFocus(); */
      }

    } else if (text.equals(traducir)) {
      oldNode = rateSliderTranslation;
      if (mediaPlayer != null) { // if doesn't exits a media return
        currentTab = traducir;
        FillListView flw = new FillListView();
        flw.setListView(listViewV, itemsOriginal);
      }
    }


    // Fill showListViewH01(original) and H2(translation)
    Platform.runLater(() -> {
      listViewV.getSelectionModel().clearAndSelect(indexItemV);
      listViewV.scrollTo(indexItemV);
      showListViewH();
    });


    // Put visible and invisible the textfield and listviews
    listViewH01Reading.setVisible(currentTab.equals(leer));
    textFieldWriting.setVisible(currentTab.equals(escribir));
    textFieldTranslation.setVisible(currentTab.equals(traducir));
    listViewH02Reading.setVisible(currentTab.equals(leer));
   }


  /**
   * Put the word of itemInicio in listView H
   */
  private void showListViewH()
   {
    try {
      if (mediaPlayer == null) return; // if doesn't exits a media return
      SaveWordsAsList swal = new SaveWordsAsList();

      // Get the index of the clicked itemInicio
      getIndexitemV();
      markerTextOriginal = itemsOriginal[indexItemV].getText();

      markerTextTranslation = itemsTranslation[indexItemV].getText();

      // Cleanig the phrase
      itemWordsOriginal = swal.cleanWords(markerTextOriginal);
      itemWordsTranslation = swal.cleanWords(markerTextTranslation);

      // Filling listViewH01Reading with words
      ObservableList<String> listItemOriginal = FXCollections.observableArrayList();
      ObservableList<String> listItemTranslation = FXCollections.observableArrayList();

      // Filling the list
      listItemOriginal.addAll(Arrays.asList(itemWordsOriginal));
      listItemTranslation.addAll(Arrays.asList(itemWordsTranslation));

      // 
      Platform.runLater(() -> {
        listViewH01Reading.setItems(listItemOriginal);
        listViewH02Reading.setItems(listItemTranslation);

        listViewH01Reading.getSelectionModel().clearAndSelect(0);
        listViewH02Reading.getSelectionModel().clearAndSelect(0);

        // Changing the indicator success with the result of the database
        // Is cero fot force it to compare with the result
        trans = trans.replaceAll("Menu", "");
        Pair<String, String> p = mainScene.handleUpdateCorrection(
                trans.concat(".json"), currentTab, indexItemV, 0);
        indicatorSuccessWriting.setProgress(Double.valueOf(p.getKey()));
        indicatorSuccessTranslation.setProgress(Double.valueOf(p.getValue()));
        // Set the media virtual and translates
        setVirtualMedia();
      });


    } catch (ArrayIndexOutOfBoundsException ex) {
    } catch (Exception e) {
      Platform.runLater(() -> {
        message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / showListViewH()", e.toString(), e);
      });
    }

   }


  /**
   *
   */
  private String getIndexitemV()
   {
    indexItemV = listViewV.getSelectionModel().getSelectedIndex();
    // just in case doesn't exit.
    if (indexItemV < 0) {
      indexItemV = 0;
      listViewV.getSelectionModel().clearAndSelect(indexItemV);
    }
    return listViewV.getSelectionModel().getSelectedItem();
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting the Hyperlink">

  /**
   * Setting the hyperlink that link to youtube
   */
  public void setHyperlink()
   {
    youtubeLink.setText("Youtube");
    youtubeLink.setOnAction(new EventHandler<ActionEvent>()
     {

      @Override
      public void handle(ActionEvent e)
       {
        youtubeLink.requestFocus();
        setBorder(youtubeLink);
        oldNode = youtubeLink;
        mainScene.getHostServices().showDocument("https://www.youtube.com/channel/UCP9QxVSsuLDdxQvxfFvHpEQ");
       }


     });
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting the Time and Event marker">

  /**
   * Setting the markers in the media
   *
   * @param items An object of type itemInicio
   */
  public void setMarkers(Item[] items)
   {
    ObservableMap<String, Duration> markers = media.getMarkers();
    try {
      for (int i = 0; i < items.length - 1; i++) {
        String id = String.valueOf(items[i].getId());
        double milis = items[i].getStart();
        markers.put(id, Duration.seconds(milis));
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setMarkers()", e.toString(), e);
    }
   }


  /**
   *
   * Add a marker event handler
   */
  public void setEventMarker()
   {
    mediaPlayer.setOnMarker(new EventHandler<MediaMarkerEvent>()
     {

      @Override
      public void handle(MediaMarkerEvent event)
       {
        try {
          if (!playButtonBoolean) {
            return;
          }
          // Calling scrolling to read marker
          String markerText = setScrollingMarket(event);
          showListViewH();
        } catch (Exception e) {
          message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setEventMarker()", e.toString(), e);
        }
       }


     });
   }


  /**
   * method that scroll the listViewV
   */
  private String setScrollingMarket(MediaMarkerEvent event)
   {
    // Creating a Object Pair
    Pair<String, Duration> marker = event.getMarker();
    String markerText = marker.getKey();
    Duration markerTime = marker.getValue();
    // Extracting the index
    indexSelected = Integer.valueOf(markerText);
    markerText = itemsOriginal[Integer.valueOf(markerText)].getText();
    // Scrolling the listview
    listViewV.scrollTo(indexSelected);
    // Selecting Item of the listViewV
    listViewV.getSelectionModel().select(indexSelected);
    // Setting the global index of the itemInicio
    indexItemV = indexSelected;
    return markerText;
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Executing Playback errors">
  /**
   * Catch the playback errors
   */
  private void setError()
   {
    // Create Handlers for handling Errors
    mediaPlayer.setOnError(new Runnable()
     {

      @Override
      public void run()
       {
        // Handle asynchronous error in Player object.
        message.message(Alert.AlertType.ERROR, "Error message", mediaPlayer.getError().toString(), "PrincipalController / mediaPlayer.setOnError()", null);
       }


     });
    media.setOnError(new Runnable()
     {

      @Override
      public void run()
       {
        // Handle asynchronous error in Media object.
        message.message(Alert.AlertType.ERROR, "Error message", media.getError().toString(), "PrincipalController / media.setOnError()", null);
       }


     });
    //Collect the playback errors
    mediaView.setOnError(new EventHandler<MediaErrorEvent>()
     {

      @Override
      public void handle(MediaErrorEvent event)
       {
        // Handle asynchronous error in MediaView.
        message.message(Alert.AlertType.ERROR, "Error message", event.getMediaError().toString(), "PrincipalController / mediaView.setOnError()", null);
       }


     });
   }
//</editor-fold>    

//<editor-fold defaultstate="collapsed" desc="Executing MediaPlayerChangeListener">

  /**
   * Set the events of the media
   */
  private void setMediaPlayerChangeListener()
   {
    try {
      // Add a ChangeListener to the mediaPlayer
      mediaPlayer.statusProperty().addListener(new ChangeListener<MediaPlayer.Status>()
       {

        @Override
        public void changed(
                ObservableValue<? extends MediaPlayer.Status> ov,
                final MediaPlayer.Status oldStatus,
                final MediaPlayer.Status newStatus)
         {

          /* System.out.println("Status changed from " + oldStatus +
                    " to " + newStatus); */
         }


       });
      // Add a Handler for PLAYING status
      mediaPlayer.setOnPlaying(new Runnable()
       {

        @Override
        public void run()
         {
          //System.out.println("Playing now");
         }


       });
      // Add a Handler for STOPPED status
      mediaPlayer.setOnPaused(new Runnable()
       {

        @Override
        public void run()
         {
          // System.out.println("Paused now");
          //mediaPlayerPause();
         }


       });
      // Add a Handler for STOPPED status
      mediaPlayer.setOnStopped(new Runnable()
       {

        @Override
        public void run()
         {
          // System.out.println("Stopped now");
          // mediaPlayerStop();
         }


       });
      // Add a Handler for END status
      mediaPlayer.setOnEndOfMedia(new Runnable()
       {

        @Override
        public void run()
         {
          // reset
          if (originalButton) {
            mediaSliderReading.setDisable(false);
            mediaSliderWriting.setDisable(false);
            mediaSliderTranslation.setDisable(false);
            mediaPlayerStop();
            start = itemsOriginal[indexItemV].getStart();
            end = itemsOriginal[indexItemV].getEnd();
            mediaPlayer.setStartTime(Duration.seconds(start));
            mediaPlayer.setStopTime(Duration.seconds(end));
            currentOriginal = Duration.seconds(start);
            mediaPlayer.seek(currentOriginal);

            if (status.equals(Status.PAUSED) || status.equals(Status.PLAYING)) {
              playedImageButton();
              playedImageButtonOriginal();
            } else {
              playedImageButton();
              playedImageButtonOriginal();
            }
          }
          if (!originalButton) {
            handleStopButton();
          }
         }


       });
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController.java / setMediaPlayerChangeListener()", e.toString(), e);
    }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Scroolbar">
  /**
   * Setting the slider that control the media
   *
   * @param sliderMedia The objet slider
   */
  private void setSliderMedia(Slider sliderMedia)
   {
    // Setting the slider when pressing on it
    sliderMedia.setOnMousePressed(new EventHandler<MouseEvent>()
     {
      @Override
      public void handle(MouseEvent t)
       {
        mediaPlayerSlider = "neutro";
       }

     });

    // Setting the slider when I release it.
    sliderMedia.setOnMouseReleased(new EventHandler<MouseEvent>()
     {
      @Override
      public void handle(MouseEvent t)
       {
        if (!sliderMedia.isDisable()) {
          sliderMedia.requestFocus();
          setBorder(sliderMedia);
          oldNode = sliderMedia;
        }

        if (mediaPlayer != null) {
          mediaPlayerSlider = "slider";
          changeListviewBySliderMedia(t.getX());
          updateValuesSlider();
          mediaPlayerSlider = "media";
        }
       }

     });

    // Setting the slider when I release it.
    sliderMedia.setOnMouseExited(new EventHandler<MouseEvent>()
     {

      @Override
      public void handle(MouseEvent t)
       {
        if (mediaPlayerSlider.equals("neutro") && !sliderMedia.isDisable()) {
          sliderMedia.requestFocus();
          setBorder(sliderMedia);
          oldNode = sliderMedia;

          if (mediaPlayer != null) {
            changeListviewBySliderMedia(t.getX());

            mediaPlayerSlider = "slider";
            updateValuesSlider();
            mediaPlayerSlider = "media";
          }
        }
       }

     });
   }


  /**
   * updateValuesSlider
   *
   */
  public void updateValuesSlider()
   {

    // if duration is unknown don´t execute it
    mediaSliderReading.setDisable(duration.isUnknown());
    mediaSliderWriting.setDisable(duration.isUnknown());
    mediaSliderTranslation.setDisable(duration.isUnknown());

    try {

      if (!mediaSliderReading.isDisable() && duration.greaterThan(Duration.ZERO)) {

        Status temp = status;
        double stSeconds = itemsOriginal[indexItemV].getStart(); // in seconds
        double stMilli = (stSeconds * 1000);
        double totalDuration = duration.toMillis();
        double positionFinal = ((stMilli * 100) / totalDuration);

        mediaPlayer.stop();
        mediaPlayer.seek(new Duration(stMilli));
        mediaPlayer.setStartTime(new Duration(stMilli));
        mediaPlayer.setStopTime(duration);

        Platform.runLater(() -> {
          mediaSliderReading.setValue(positionFinal);
        });
        if (temp.equals(Status.PLAYING)) mediaPlayer.play();
        if (temp.equals(Status.PAUSED)) mediaPlayer.pause();
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController.java / updateValues()", e.toString(), e);
    }
    FormatTime ft = new FormatTime();
    timeLabelReading.setText(ft.formatting(currentTime, duration));
   }


  /**
   * Setting the end time of the file
   */
  private void setEndTimefile()
   {
    mediaPlayer.setOnReady(new Runnable()
     {

      @Override
      public void run()
       {
        FormatTime ft = new FormatTime();
        currentTime = Duration.ZERO;
        duration = mediaPlayer.getMedia().getDuration();
        timeLabelReading.setText(ft.formatting(currentTime, duration));
        mediaPlayer.play();
        mediaPlayer.stop();
        anchorMedia.setPrefHeight(media.getHeight());
        anchorMedia.setMinHeight(media.getHeight());
        mediaView.setFitHeight(media.getHeight());
       }


     });
   }


  /**
   * Setting the values of the mediaSliderReading
   *
   * @param max An Double (0 - 100) representing the time end of the itemInicio
   */
  private void setTicksSliderMedia(Slider sliderMedia, double max)
   {
    sliderMedia.setMin(0);
    sliderMedia.setMax(max);
    sliderMedia.setValue(0);
    sliderMedia.setSnapToTicks(false);
    sliderMedia.setShowTickLabels(true);
    sliderMedia.setShowTickMarks(true);
    sliderMedia.setMajorTickUnit(10);
    sliderMedia.setMinorTickCount(1);
   }

//</editor-fold>    

//<editor-fold defaultstate="collapsed" desc="Binding">
  /**
   * Binding the Slider of Reading to Writing
   */
  private void setBinding()
   {
    mediaSliderReading.valueProperty().bindBidirectional(mediaSliderWriting.valueProperty());
    mediaSliderWriting.valueProperty().bindBidirectional(mediaSliderTranslation.valueProperty());
    binding(rateSliderReading, rateSliderWriting, rateLabelReading, rateLabelWriting);
    binding(rateSliderWriting, rateSliderTranslation, rateLabelReading, rateLabelTranslation);
    binding(balanceSliderReading, balanceSliderWriting, balanceLabelReading, balanceLabelWriting);
    binding(balanceSliderWriting, balanceSliderTranslation, balanceLabelWriting, balanceLabelTranslation);
    binding(volumeSliderReading, volumeSliderWriting, volumeLabelReading, volumeLabelWriting);
    binding(volumeSliderWriting, volumeSliderTranslation, volumeLabelWriting, volumeLabelTranslation);
   }

//</editor-fold>    

//<editor-fold defaultstate="collapsed" desc="Helper to the Binding">

  /**
   * Helper to the binding
   *
   * @param s1 First Slider to bind
   * @param s2 Second Slider to bind
   * @param l1 label of the first slider
   * @param l2 label of the second slider
   */
  private void binding(Slider s1, Slider s2, Label l1, Label l2)
   {
    s1.valueProperty().bindBidirectional(s2.valueProperty());
    l1.textProperty().bindBidirectional(l2.textProperty());
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Seting TransversalFocus ">

  /**
   * Setting the movement between elements
   */
  private void setTransversalFocus()
   {

    //<editor-fold defaultstate="collapsed" desc="listViewV ">

    //<editor-fold defaultstate="collapsed" desc="Setting listViewV ">

    // Enable the selection mode
    listViewV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    //For vertical ListView this is the height, for a horizontal ListView this is the width.  
    listViewV.setFixedCellSize(40);

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Key Events">

    listViewV.setOnKeyPressed(new EventHandler<KeyEvent>()
     {
      @Override
      public void handle(KeyEvent ke)
       {
        try {

          getIndexitemV();

          switch (ke.getCode()) {
            case TAB:
              ke.consume();
              break;
            case UP:
              if (mediaPlayer == null) return; // if doesn't exits a media return

              if (indexItemV > 0) {
                indexItemV--;
                listViewV.getSelectionModel().select(indexItemV);
                showListViewH();
                listViewV.getSelectionModel().select(indexItemV + 1);
                //ke.consume();
              }
              break;
            case DOWN:
              if (mediaPlayer == null) return; // if doesn't exits a media return

              if (indexItemV < itemsOriginal.length) {
                indexItemV++;
                listViewV.getSelectionModel().select(indexItemV);
                showListViewH();
                listViewV.getSelectionModel().select(indexItemV - 1);
                //ke.consume();
              }
              break;
            case LEFT:
              handleBackButtonItemOriginal();
              ke.consume();
              break;
            case RIGHT:
              if (oldNode.equals(currentNode)) {
                oldNode = tabPanelListViewH;
              }
              oldNode.requestFocus();
              setBorder(oldNode);
              ke.consume();
              break;
            case SPACE:
              handlePlayButtonItemOriginal();
              ke.consume();
              break;
            case ENTER:
              handlePlayButtonItemOriginal();
              ke.consume();
              break;
            default:
              break;
          }
        } catch (Exception e) {
          message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setTransversalFocus()", e.toString(), e);
        }
       }

     });

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mouse Events">

    // Enable the listeners
    listViewV.setOnMouseClicked(new EventHandler<MouseEvent>()
     {
      @Override
      public void handle(MouseEvent event)

       {

        // Setting the current node
        listViewV.requestFocus();
        setBorder(listViewV);
        oldNode = tabPanelListViewH;
        ////////if (mediaPlayer == null)return; // if doesn't exits a media return

        getIndexitemV();
        showListViewH();
        // if I do double click
        if (event.getClickCount() == 2) {
          handlePlayButtonItemOriginal();
        }
       }

     });

    listViewV.setOnMouseReleased((e) -> {
      ////////if (mediaPlayer == null)return; // if doesn't exits a media return

      setBorder(listViewV);
      oldNode = tabPanelListViewH;
      getIndexitemV();

      showListViewH();
    });

    //</editor-fold>

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="listViewH">

    // Enable the selection mode
    listViewH01Reading.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    listViewH02Reading.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    eventListviewH(listViewH01Reading, 3, 5, -1, 0);
    eventListviewH(listViewH02Reading, -1, 3, -1, 0);

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Hyperlink youtube">
    youtubeLink.setOnKeyPressed(new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent ke)
       {
        KeyCode k = ke.getCode();
        Node no = youtubeLink;
        switch (k) {
          case UP:
            if (listViewH02Reading.isVisible()) no = listViewH02Reading;
            break;
          case DOWN:
            if (currentTab.equals(leer)) no = listViewH01Reading;
            if (currentTab.equals(escribir)) no = textFieldWriting;
            if (currentTab.equals(traducir)) no = textFieldTranslation;
            break;
          case RIGHT:
            break;
          case LEFT:
            no = listViewV;
            break;
          case ENTER:
          case SPACE:
            mainScene.getHostServices()
                    .showDocument("https://www.youtube.com/channel/UCP9QxVSsuLDdxQvxfFvHpEQ");
            break;
          default:
            break;
        }
        no.requestFocus();
        setBorder(no);
        ke.consume();
       }


     }
    // The setOnMouseClick is in setHyperlink

    );
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Button, sliders, mediaSlider">

    eventButton(playButtonReading, 4, 12, 6, 0);
    eventButton(backButtonReading, 4, 13, 7, 5);
    eventButton(forwardButtonReading, 4, 14, 8, 6);
    eventButton(stopButtonReading, 4, 15, 9, 7);

    eventMediaSlider(mediaSliderReading, 4, 17, -1, 8);

    eventButton(playButtonItemOriginalReading, 5, 22, 11, 0);
    eventButton(backButtonItemOriginalReading, 5, 22, 12, 10);
    eventButton(forwardButtonItemOriginalReading, 5, 22, 13, 11);
    eventButton(stopButtonItemOriginalReading, 6, 22, 14, 12);

    eventButton(playButtonItemVirtualReading, 7, 22, 15, 13);
    eventButton(backButtonItemVirtualReading, 8, 23, 16, 14);
    eventButton(forwardButtonItemVirtualReading, 9, 23, 17, 15);
    eventButton(stopButtonItemVirtualReading, 9, 23, 18, 16);

    eventButton(playButtonItemVirtualTranslatedReading, 9, 23, 19, 17);
    eventButton(backButtonItemVirtualTranslatedReading, 9, 24, 20, 18);
    eventButton(forwardButtonItemVirtualTranslatedReading, 9, 24, 21, 19);
    eventButton(stopButtonItemVirtualTranslatedReading, 9, 24, -1, 20);

    eventFilterSlider(rateSliderReading, 12, 1, 23, 0, 0.5, 2, 0.01);
    eventFilterSlider(balanceSliderReading, 17, 1, 24, 22, -1.0, 1, 0.1);
    eventFilterSlider(volumeSliderReading, 20, 1, -1, 23, 0.0, 100, 1);
    /* ------------------------------------------------------------------------------------- */
    eventButton(playButtonWriting, 25, 33, 27, 0);
    eventButton(backButtonWriting, 25, 34, 28, 26);
    eventButton(forwardButtonWriting, 25, 35, 29, 27);
    eventButton(stopButtonWriting, 25, 36, 30, 28);

    eventMediaSlider(mediaSliderWriting, 25, 38, -1, 29);

    eventButton(playButtonItemOriginalWriting, 26, 44, 32, 0);
    eventButton(backButtonItemOriginalWriting, 26, 44, 33, 31);
    eventButton(forwardButtonItemOriginalWriting, 26, 44, 34, 32);
    eventButton(stopButtonItemOriginalWriting, 27, 44, 35, 33);

    eventButton(playButtonItemVirtualWriting, 28, 44, 36, 34);
    eventButton(backButtonItemVirtualWriting, 29, 45, 37, 35);
    eventButton(forwardButtonItemVirtualWriting, 30, 45, 38, 36);
    eventButton(stopButtonItemVirtualWriting, 30, 45, 39, 37);

    eventButton(playButtonItemVirtualTranslatedWriting, 30, 45, 40, 38);
    eventButton(backButtonItemVirtualTranslatedWriting, 30, 46, 41, 39);
    eventButton(forwardButtonItemVirtualTranslatedWriting, 30, 46, 42, 40);
    eventButton(stopButtonItemVirtualTranslatedWriting, 30, 46, 43, 41);

    eventButton(correctionButtonWriting, 30, 46, -1, 42);

    eventFilterSlider(rateSliderWriting, 33, 1, 45, 0, 0.5, 2, 0.01);
    eventFilterSlider(balanceSliderWriting, 38, 1, 46, 44, -1.0, 1, 0.1);
    eventFilterSlider(volumeSliderWriting, 41, 1, -1, 45, 0.0, 100, 1);
    /* ------------------------------------------------------------------------------------- */
    eventButton(playButtonTranslation, 47, 55, 49, 0);
    eventButton(backButtonTranslation, 47, 56, 50, 48);
    eventButton(forwardButtonTranslation, 47, 57, 51, 49);
    eventButton(stopButtonTranslation, 47, 58, 52, 50);

    eventMediaSlider(mediaSliderTranslation, 47, 60, -1, 51);

    eventButton(playButtonItemOriginalTranslation, 48, 66, 54, 0);
    eventButton(backButtonItemOriginalTranslation, 48, 66, 55, 53);
    eventButton(forwardButtonItemOriginalTranslation, 48, 66, 56, 54);
    eventButton(stopButtonItemOriginalTranslation, 49, 66, 57, 55);

    eventButton(playButtonItemVirtualTranslation, 50, 66, 58, 56);
    eventButton(backButtonItemVirtualTranslation, 51, 67, 59, 57);
    eventButton(forwardButtonItemVirtualTranslation, 52, 67, 60, 58);
    eventButton(stopButtonItemVirtualTranslation, 52, 67, 61, 59);

    eventButton(playButtonItemVirtualTranslatedTranslation, 52, 67, 62, 60);
    eventButton(backButtonItemVirtualTranslatedTranslation, 52, 68, 63, 61);
    eventButton(forwardButtonItemVirtualTranslatedTranslation, 52, 68, 64, 62);
    eventButton(stopButtonItemVirtualTranslatedTranslation, 52, 68, 65, 63);

    eventButton(correctionButtonTranslation, 52, 68, -1, 64);

    eventFilterSlider(rateSliderTranslation, 55, 1, 67, 0, 0.5, 2, 0.01);
    eventFilterSlider(balanceSliderTranslation, 60, 1, 68, 66, -1.0, 1, 0.1);
    eventFilterSlider(volumeSliderTranslation, 63, 1, -1, 67, 0.0, 100, 1);

    oldNodeSliderUp = new int[]{0, 1, 22, 44, 66, 23, 45, 67, 24, 46, 68};
    oldNodeMediaSliderDown = new int[]{4, 25, 47, 8, 29, 51, 9, 30, 52};
    oldNodeListViewH1Down = new int[]{0, 3, 4, 25, 47};
    oldNodeTabPaneUp = new int[]{0, 1};
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab pane">

    tabPanelListViewH.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent event)
       {

        KeyCode key = event.getCode();
        // An triger
        if (!event.getTarget().equals(tabPanelListViewH)) return;
        if (key.equals(KeyCode.TAB)) event.consume();

        if (key.equals(KeyCode.UP)) {
          event.consume();


          if (key.equals(KeyCode.UP)) {

            for (int n : oldNodeTabPaneUp) {
              if (oldNode.equals(node[n])) {
                if (currentTab.equals(leer)) {
                  oldNode = rateSliderReading;
                }
                if (currentTab.equals(escribir)) {
                  oldNode = rateSliderWriting;
                }
                if (currentTab.equals(traducir)) {
                  oldNode = rateSliderTranslation;
                }
                break;
              }
            }

            oldNode.requestFocus();
            setBorder(oldNode);
            oldNode = tabPanelListViewH;
            event.consume();
          }

        }

        if (key.equals(KeyCode.LEFT)) {
          if (currentTab.equals(leer)) {
            setBorder(listViewV);
            listViewV.requestFocus();
            event.consume();
          } else {
            tabPanelListViewH.getSelectionModel().selectPrevious();
            changeListview(tabPanelListViewH.getSelectionModel().getSelectedItem().getText());
            event.consume();

          }
        }

        if (key.equals(KeyCode.RIGHT)) {
          if (currentTab.equals(traducir)) {
            event.consume();
          } else {
            tabPanelListViewH.getSelectionModel().selectNext();
            changeListview(tabPanelListViewH.getSelectionModel().getSelectedItem().getText());
            event.consume();

          }
        }
        if (key.equals(KeyCode.DOWN)) {
          event.consume();
        }
       }

     });
    tabPanelListViewH.setOnMousePressed((event) -> {
      event.consume();
    });

    tabPanelListViewH.setOnMouseReleased((event) -> {

      Tab t = tabPanelListViewH.getSelectionModel().getSelectedItem();
      changeListview(t.getText());
      setBorder(tabPanelListViewH);
      oldNode = tabPanelListViewH;
      event.consume();
    });

    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="TextField">
    eventTextfield(textFieldWriting, 3, 26, 0, -1);
    eventTextfield(textFieldTranslation, 3, 48, 0, -1);
//</editor-fold>
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Event TransversalFocus">

  //<editor-fold defaultstate="collapsed" desc="Button">

  /**
   * Helper to the play button event
   *
   * @param up the above node
   * @param down the belong node
   * @param right the right node
   * @param left the left node
   * @param button The play button
   */
  private void eventButton(Node n, int up, int down, int right, int left)
   {
    n.setOnKeyPressed(new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent ke)
       {
        KeyCode key = ke.getCode();

        int i = -1;
        if (key.equals(KeyCode.TAB)) {
          ke.consume();
        }
        if (key.equals(KeyCode.UP)) {
          i = up;
        }
        if (key.equals(KeyCode.DOWN)) {
          i = down;
        }
        if (key.equals(KeyCode.RIGHT)) {
          i = right;
        }
        if (key.equals(KeyCode.LEFT)) {
          i = left;
        }
        if (i != -1 && !node[i].isDisable()) {
          node[i].requestFocus();
          setBorder(node[i]);
          oldNode = n;
          ke.consume();
        }
       }

     });

    n.setOnMouseClicked((MouseEvent) -> {
      n.requestFocus();
      setBorder(n);
      oldNode = n;
      MouseEvent.consume();
    });

    n.setOnMouseReleased((MouseEvent) -> {
      n.requestFocus();
      setBorder(n);
      oldNode = n;
      MouseEvent.consume();
    });
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="MediaSlider">

  /**
   * Helper to the sliderMedia media event
   *
   * @param sliderMedia The sliderMedia to setting
   * @param up the above node
   * @param down the belong node
   * @param right the right node
   * @param left the left node
   */
  private void eventMediaSlider(Slider sliderMedia, int up, int down, int right, int left)
   {
    sliderMedia.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {

      //<editor-fold defaultstate="collapsed" desc="Teclas combinadas con Control">
      if (mediaPlayer != null) {
        if (kcLeft.match(event) && sliderMedia.getValue() > 0) {
          /*/*sliderMedia.setValue(sliderMedia.getValue() - 1); 

          changeListviewBySliderMedia(); */
          handleBackButton();

          mediaPlayerSlider = "slider";
          updateValuesSlider();
          event.consume();
          return;
        }
        if (kcLeft.match(event) && sliderMedia.getValue() <= 0) {
          event.consume();
          return;
        }
        if (kcRight.match(event) && sliderMedia.getValue() < duration.toMillis()) {
          /*/*sliderMedia.setValue(sliderMedia.getValue() + 1);

          changeListviewBySliderMedia(); */
          handleForwardButton();

          mediaPlayerSlider = "slider";
          updateValuesSlider();
          event.consume();
          return;
        }
        if (kcRight.match(event) && sliderMedia.getValue() >= duration.toMillis()) {
          handleStopButton();
          event.consume();
          return;
        }
      }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="Key Events">

      KeyCode code = event.getCode();
      int i = -1;

      if (code.equals(KeyCode.TAB)) {
        event.consume();
      }
      if (code.equals(KeyCode.UP)) {
        i = up;
      }

      if (code.equals(KeyCode.DOWN)) {
        for (int n : oldNodeMediaSliderDown) {
          if (oldNode.equals(node[n])) {
            oldNode = node[down];
            break;
          }
        }
        oldNode.requestFocus();
        setBorder(oldNode);
        oldNode = sliderMedia;
        event.consume();
      }

      if (code.equals(KeyCode.LEFT)) {
        i = left;
      }

      if (code.equals(KeyCode.RIGHT)) {
        i = right;
        event.consume(); // Fix the sliderMedia
      }

      if ((code.equals(KeyCode.SPACE) || code.equals(KeyCode.ENTER)) && mediaPlayer != null) {
        switch (status) {
          case STOPPED:
          case PAUSED:
            mediaPlayerPlay();
            playedImageButton();
            break;
          case PLAYING:
            mediaPlayerStop();
            pausedImageButton();
            break;
          default:
            break;
        }
      }
      if (i != -1) {
        node[i].requestFocus();
        setBorder(node[i]);
        oldNode = sliderMedia;
        event.consume();
      }
      //</editor-fold>

    });
    // the sliderMedia.setOnMouseClicked is in ScrollBar
   }

//</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="Slider">

  /**
   * Helper to the filter slider event
   *
   * @param slider slider The slider balance, volume and rate
   * @param right the right node
   * @param left the left node
   * @param min The min value of the slider
   * @param max The max value of the slider
   * @param per The step
   */
  private void eventFilterSlider(Slider slider, int up, int down, int right, int left, double min, double max, double per)
   {
    slider.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {

      //<editor-fold defaultstate="collapsed" desc="Control + Events">
      if (kcLeft.match(event)) {
        if (slider.getValue() > min) {
          slider.setValue(slider.getValue() - per);
        }
        event.consume();
        return;
      }

      if (kcRight.match(event)) {
        if (slider.getValue() < max) {
          slider.setValue(slider.getValue() + per);
        }
        event.consume();
        return;
      }
      //</editor-fold>

      //<editor-fold defaultstate="collapsed" desc="Key Events">
      KeyCode code = event.getCode();

      if (code.equals(KeyCode.TAB)) {
        event.consume();
      }
      if (code.equals(KeyCode.UP)) {

        for (int j : oldNodeSliderUp) {
          if (oldNode.equals(node[j])) {
            oldNode = node[up];
            break;
          }
        }
        oldNode.requestFocus();
        setBorder(oldNode);
        oldNode = slider;
        event.consume();
      }

      if (code.equals(KeyCode.DOWN)) {
        node[down].requestFocus();
        setBorder(node[down]);
        event.consume();
        return;
      }

      if (code.equals(KeyCode.LEFT)) {
        node[left].requestFocus();
        setBorder(node[left]);
        event.consume();
        return;
      }
      if (code.equals(KeyCode.RIGHT) && right != -1) {
        node[right].requestFocus();
        setBorder(node[right]);
        event.consume();
        return;
      } else {
        event.consume();
      }
      //</editor-fold>

    });

    //<editor-fold defaultstate="collapsed" desc="Mouse Events">

    slider.setOnMouseClicked((MouseEvent) -> {
      slider.requestFocus();
      setBorder(slider);
      MouseEvent.consume();
    });

    slider.setOnMouseReleased((MouseEvent) -> {
      slider.requestFocus();
      setBorder(slider);
      MouseEvent.consume();
    });

    //</editor-fold>

   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="TextField">

  /**
   * Setting the moviment when i'm in a textField
   *
   * @param tf the textFieldWriting or textFieldTranslation
   * @param up
   * @param down
   * @param left
   * @param right
   */
  private void eventTextfield(TextField tf, int up, int down, int left, int right)
   {

    //<editor-fold defaultstate="collapsed" desc="Key Events">
    tf.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent event)
       {
        KeyCode ke = event.getCode();
        int i = -1;
        if (ke.equals(KeyCode.TAB)) {
          event.consume();
        }

        if (ke.equals(KeyCode.UP)) {
          i = up;
        }

        if (ke.equals(KeyCode.DOWN)) {
          for (int j : oldNodeListViewH1Down) {
            if (oldNode.equals(node[j])) {
              oldNode = node[down];
              break;
            }
          }
          oldNode.requestFocus();
          setBorder(oldNode);
          oldNode = tf;
          event.consume();
          return;
        }
        if (ke.equals(KeyCode.LEFT) && tf.getCaretPosition() <= 0) {
          i = left;
        }
        if (ke.equals(KeyCode.RIGHT)) {
          i = right;
        }
        if (ke.equals(KeyCode.ENTER)) {
          listViewH02Reading.setVisible(true);
          i = Arrays.asList(node).indexOf(tf);
          handleCorrectionButton(tf);
          handlePlayButtonItemOriginal();
          event.consume();
        }
        if (i != -1) {
          node[i].requestFocus();
          setBorder(node[i]);
          oldNode = tf;
          event.consume();
        } else listViewH02Reading.setVisible(false);
       }

     });
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mouse Events">
    tf.setOnMouseClicked((e) -> {
      setBorder(tf);
      oldNode = tf;
      e.consume();
    });

    tf.setOnMouseReleased((e) -> {
      setBorder(tf);
      oldNode = tf;
      e.consume();
    });
    //</editor-fold>
   }

//</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="ListviewH">

  /**
   * Helper to the play button event
   *
   * @param up the above node
   * @param down the belong node
   * @param right the right node
   * @param left the left node
   * @param button The play button
   */
  private void eventListviewH(ListView lw, int up, int down, int right, int left)
   {

    //<editor-fold defaultstate="collapsed" desc="Key events">
    lw.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent event)
       {
        // los textfield llevan un metodo propio, aqui solo se tocon los listview H01 H02
        int i = -1;
        KeyCode ke = event.getCode();

        try {

          if (ke.equals(KeyCode.TAB)) {
            event.consume();
          }

          if (ke.equals(KeyCode.UP)) {
            i = up;
          }

          if (ke.equals(KeyCode.DOWN) && lw.equals(listViewH01Reading)) {
            for (int j : oldNodeListViewH1Down) {
              if (oldNode.equals(node[j])) {
                oldNode = node[down];
                break;
              }
            }
            oldNode.requestFocus();
            setBorder(oldNode);
            oldNode = lw;
            event.consume();
          }

          if (ke.equals(KeyCode.DOWN) && lw.equals(listViewH02Reading)) {
            i = down;
          }

          if (ke.equals(KeyCode.LEFT)) {
            if (lw.getSelectionModel().getSelectedIndex() == 0) {
              i = left;
            }
          }

          if (ke.equals(KeyCode.RIGHT)) {
            i = right;
          }

          if (ke.equals(KeyCode.SPACE) || ke.equals(KeyCode.ENTER)) {
            handlePlayButtonItemOriginalMachine(lw);
            event.consume();
          }
          if (i != -1) {
            node[i].requestFocus();
            setBorder(node[i]);
            oldNode = lw;
            event.consume();
          }
        } catch (Exception e) {
          message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setTransversalFocus()", e.toString(), e);
        }
       }

     ;
    }


      );
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mouse Events">
      lw.setOnMouseClicked(
            new EventHandler<MouseEvent>()
     {
      @Override
      public void handle(MouseEvent event
      )
       {
        // if I do double click
        if (event.getClickCount() == 2) {
          handlePlayButtonItemOriginalMachine(lw);
        }
        setBorder(lw);
        //currentTab = leer;
       }

     }
    );

    lw.setOnMouseReleased(
            (event) -> {
      lw.requestFocus();
      setBorder(lw);
      oldNode = lw;
      event.consume();
      currentTab = leer;
    });
    //</editor-fold>

   }

  //</editor-fold>

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
    if (n.equals(listViewV)) {
      n = anchorListViewV;
      currentNode.getStyleClass().removeAll(Collections.singleton("borderVisible"));
      n.getStyleClass().add("borderVisible");
      n = listViewV;
      oldNode = currentNode;
      currentNode = n;
      //System.out.println("oldNode " + oldNode);
      //System.out.println("currentNode " + currentNode + "\n");
      return;
    }
    if (currentNode.equals(listViewV)) {
      currentNode = anchorListViewV;
      currentNode.getStyleClass().removeAll(Collections.singleton("borderVisible"));
      n.getStyleClass().add("borderVisible");
      currentNode = listViewV;
      oldNode = currentNode;
      currentNode = n;
      //System.out.println("oldNode " + oldNode);
      //System.out.println("currentNode " + currentNode + "\n");
      return;
    }
    currentNode.getStyleClass().removeAll(Collections.singleton("borderVisible"));
    n.getStyleClass().add("borderVisible");
    oldNode = currentNode;
    currentNode = n;
    //System.out.println("oldNode " + oldNode);
    //System.out.println("currentNode " + currentNode + "\n");
   }

//</editor-fold>  

 }
