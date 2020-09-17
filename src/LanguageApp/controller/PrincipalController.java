package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.model.Item;
import LanguageApp.util.AudioClips;
import LanguageApp.util.Directorio;
import LanguageApp.util.Directory;
import LanguageApp.util.FillListView;
import LanguageApp.util.FormatTime;
import LanguageApp.util.GetJson;
import LanguageApp.util.HandleLocale01;
import LanguageApp.util.Message;
import LanguageApp.util.SaveWordsAsList;
import LanguageApp.util.SelectedFile;
import LanguageApp.util.SortPhrase;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.application.Platform;
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
  // hyperlink youtube
  @FXML Hyperlink youtubeLink;
  // pop-up messages
  Message message;
  // The focused and old node
  Node currentNode, oldNode;
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
  private String[] idiomaSubt;
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
  private final ChangeListener<Duration> mediaPlayerChangeListener;
  private final ChangeListener<Number> sliderMeediaChangeListener;
  private Thread mediaToSlideThread;
  private boolean mediaToSlider;
  // itemInicio
  private double start;
  private double end;
  private int indexItemV;
  // Save as a list of words
  private Set<String>[] wordSet;
  private Set<String>[] phraseSet;
  // Show ListView 
  private String[] itemWordsOriginal;
  private String[] itemWordsTranslation;
  // ListView MarkerText
  private String markerTextOriginal;
  private String markerTextTranslation;
  // AudiClip
  private Map<String, AudioClip>[] audioClipsWords;
  private Map<String, AudioClip>[] audioClipsPhrases;
  // Subtitle original (listviewH 01) and Subtitle translation (listviewH 02) 
  private int subOrig, subTrans;
  private boolean exitLoop;
  private int currentPauseItem; // the actual itemInicio when click the original pause 
  // slider media, to control if the moviment of the media comes from the slider or the media
  private static String mediaPlayerSlider;
  // Temporal to fix the sliderMedia
  private double errorMediaSalir;
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
  // Class instances
  private SelectedFile sf;
  private GetJson gj;
  private FillListView flw;
  private Directory dir;
  private AudioClips ac;
  private SaveWordsAsList swal;
  private FormatTime ft;
  private Directorio dire;
  private SortPhrase sp;
  // Active user
  private int usuario_id;
  // For the bounle of idioms
  ResourceBundle resources;
  // Creating multilingual constans
  private String leer, escribir, traducir;

//</editor-fold>  

//<editor-fold defaultstate="collapsed" desc="Constructor">

  public PrincipalController()
   {
    /*            --------------- NO SIRVEN, SOLO DE RECUERDO ------------*/

    // Listener for the media when changes move the slide
    mediaPlayerChangeListener = new ChangeListener<Duration>()
     {

      @Override
      public void changed(
              ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
       {
        mediaPlayerSlider = "media";
        updateValuesMedia();
       }


     };
    // Listener for the slider when changes move the media
    sliderMeediaChangeListener = new ChangeListener<Number>()
     {

      @Override
      public void changed(
              ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
       {
        if (duration != null) {
          changeListviewBySliderMedia();
          mediaPlayerSlider = "slider";
          updateValuesSlider();
        }
       }


     };

    // Instances
    sf = new SelectedFile();
    gj = new GetJson();
    flw = new FillListView();
    dire = new Directorio();
    ac = new AudioClips();
    swal = new SaveWordsAsList();
    ft = new FormatTime();
    sp = new SortPhrase();
   }

//</editor-fold>

  /**
   *
   * @param aThis
   */
  public void setMainScene(MainScene aThis)
   {
    mainScene = aThis;
   }

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
      HandleLocale01.handleLocale01();
      message = new Message(resources);

      path = System.getProperty("user.dir");
      se = System.getProperty("file.separator");
      //se = "/";
      // if it playing an itemInicio, doesn´t scroll
      playButtonBoolean = true;
      // Setting the sliders of the Volumen, balance, rate
      setSlider();
      // Settion the images of the butttons
      setImageButton();
      // Setting Hyperlink youtube
      setHyperlink();
      //<editor-fold defaultstate="collapsed" desc="nodos">
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
        rateSliderReading, balanceSliderReading, volumeSliderReading,
        textFieldWriting,
        playButtonWriting, backButtonWriting, forwardButtonWriting, stopButtonWriting,
        mediaSliderWriting,
        playButtonItemOriginalWriting, backButtonItemOriginalWriting,
        forwardButtonItemOriginalWriting, stopButtonItemOriginalWriting,
        playButtonItemVirtualWriting, backButtonItemVirtualWriting,
        forwardButtonItemVirtualWriting, stopButtonItemVirtualWriting,
        correctionButtonWriting,
        rateSliderWriting, balanceSliderWriting, volumeSliderWriting,
        textFieldTranslation,
        playButtonTranslation, backButtonTranslation, forwardButtonTranslation, stopButtonTranslation,
        mediaSliderTranslation,
        playButtonItemOriginalTranslation, backButtonItemOriginalTranslation,
        forwardButtonItemOriginalTranslation, stopButtonItemOriginalTranslation,
        playButtonItemVirtualTranslation, backButtonItemVirtualTranslation,
        forwardButtonItemVirtualTranslation, stopButtonItemVirtualTranslation,
        correctionButtonTranslation,
        rateSliderTranslation, balanceSliderTranslation, volumeSliderTranslation};
      //</editor-fold>
      // Setting the listViewH and textField invisible or disabled
      listViewH01Reading.setVisible(true);
      textFieldWriting.setVisible(false);
      textFieldTranslation.setVisible(false);
      // Setting the transversal focus of the nodes
      setTransversalFocus();
      // The initial tab
      currentTab = leer;
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
      // Setting the status of the media and the sliderMedia
      status = Status.STOPPED;
      // Setting the event that move the slider media by the media
      mediaToSlider = false;
      // Setting initial user
      usuario_id = mainScene.getUsuario_id();
      // Setting the subtitle that I'm going to put in listview H01 y H02
      subOrig = 0;
      subTrans = 1;

      // Check if there´s an initial file
      if (dire.checkIni(usuario_id)) {
        handleOpenMenu2();
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / initialize()", e.toString(), e);
    }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Menu + inicio">
  /**
   * Open a SelectFile and seek a json to load the phrases (Part 1)
   */
  public void handleOpenMenu()
   {
    // Open a new fileChooser, set the stage where it´s shows,return un File
    usuario_id = mainScene.getUsuario_id();
    try {
      File file = sf.getSelectedFile(mainStage, dire.getLastDirectory());
      // Setting the globlal directory
      String lastDirectory = file.getParent() + se;//el que acabo de abrir con el filechooser
      String name = file.getName();
      // Checking if exists some equal
      dire.checkAndSetLastDirectory(name, lastDirectory, usuario_id);
      handleOpenMenu2();
    } catch (NullPointerException ex) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleOpenMenu()", ex.toString(), ex);
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleOpenMenu()", e.toString(), e);
    }
   }


  /**
   * Open a SelectFile and seek a json to load the phrases (Part2)
   */
  public void handleOpenMenu2()
   {
    try {
      handleCloseMenu("handleOpenMenu2");
      // Lock the name of the file idiomas.Json in the directory Json and load the idiomas[][]
      File jsonBinder = new File(dire.getLastDirectory() + "Json\\\\");
      String[] listado = jsonBinder.list();
      idiomas = new Item[listado.length][];
      wordSet = (Set<String>[]) new Set[listado.length];
      phraseSet = (Set<String>[]) new Set[listado.length];
      audioClipsWords = (Map<String, AudioClip>[]) new Map[listado.length];
      audioClipsPhrases = (Map<String, AudioClip>[]) new Map[listado.length];
      for (int x = 0; x < listado.length; x ++) {
        // Read a json ,call a read JSON, return an array of Item class objects
        File file = new File(dire.getLastDirectory() + "Json\\\\" + listado[x]);
        if (file.exists()) {
          // Read the Json witn the Item[]
          idiomas[x] = gj.getJson(file);
          // Extract the languages from the last row of the any itemInicio
          idiomaSubt = idiomas[0][idiomas[0].length - 1].getText().split(" ");
          wordSet[x] = swal.saveWordsAsList(idiomas[x]);
          phraseSet[x] = sp.sortPhrases(idiomas[x]);
        }
      }
      // fill a ListView with the phrases of the Items array
      itemsOriginal = idiomas[0];
      itemsTranslation = idiomas[1];
      flw.setListView(listViewV, itemsOriginal);
      // Creating the path to the media
      mediaStringUrl = new File(dire.getLastDirectory() + dire.getLastFile()).toURI();
      // Set the MediaPlayer
      setMediaPlayer();
      // Setting audiclips
      for (int x = 0; x < listado.length; x ++) {
        audioClipsWords[x] = ac.setAudioClip(wordSet[x],
                dire.getLastDirectory() + listado[x]
                .replaceAll(".json", "") + "Dictionary\\Words",
                rateSliderReading, balanceSliderReading, volumeSliderReading);
        audioClipsPhrases[x] = ac.setAudioClip(phraseSet[x],
                dire.getLastDirectory() + listado[x]
                .replaceAll(".json", "") + "Dictionary\\Phrases",
                rateSliderReading, balanceSliderReading, volumeSliderReading);
      }
      // Setting the listview
      setListViewV();
      setListViewH();
      // Setting the sliderMedia
      setSliderMedia(mediaSliderReading);
      setSliderMedia(mediaSliderWriting);
      setSliderMedia(mediaSliderTranslation);
      setTicksSliderMedia(mediaSliderReading, 100);
      setTicksSliderMedia(mediaSliderWriting, 100);
      setTicksSliderMedia(mediaSliderTranslation, 100);
      // The first lap of the slider
      mediaPlayerSlider = "media"; // to the first lap
      // Setting the first itemInicio
      indexItemV = 0;
      listViewV.scrollTo( - 0);
      listViewV.getSelectionModel().select(0);
      showListViewH();
      // Setting the initial pause
      indexItemV = 0;
      currentOriginal = new Duration(itemsOriginal[indexItemV].getStart());
      // Setting the binding beetwen Writer Tab and Reader Tab
      setBinding();
      // Initial call that return the total duration of the file and 
      // set it in  the label textLabel  
      setEndTimefile();
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleOpenMenu2()", e.toString(), e);
    }
   }


  /**
   * When click on the close menu
   *
   * @param origen Only when the origen is handleOpenMenu2 I create an empty inicial file is not, only close all.
   */
  public void handleCloseMenu(String origen)
   {
    try {
      if (mediaPlayer == null) {
        return;
      }
      handleStopButton();
      mediaPlayerStop();
      mediaPlayer.dispose();
      audioClipsWords = null;
      itemsOriginal = new Item[1];
      itemsOriginal[0] = new Item(0, 0, " ");
      itemsTranslation = new Item[1];
      itemsTranslation[0] = new Item(0, 0, " ");
      flw.setListView(listViewV, itemsOriginal);
      flw.setListView(listViewH01Reading, itemsOriginal);
      flw.setListView(listViewH02Reading, itemsOriginal);
      textFieldWriting.setText("");
      textFieldTranslation.setText("");
      // Delete and create and empty initial file
      usuario_id = mainScene.getUsuario_id();
      if ( ! origen.equals("handleOpenMenu2")) {
        dire.createIni(usuario_id);
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleMenuClose()", e.toString(), e);
    }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Play, stop, pause Button...">
  /**
   * Setting the image int the buttons
   */
  private void setImageButton()
   {
    try {
      imageViews = new ImageView[47];
      String[] ruta = {
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
        "comprobar.png"
      };
      for (int i = 0; i < imageViews.length; i ++) {
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

      /* --------  Writting ----------*/
      playButtonWriting.setGraphic(imageViews[15]);
      backButtonWriting.setGraphic(imageViews[17]);
      forwardButtonWriting.setGraphic(imageViews[18]);
      stopButtonWriting.setGraphic(imageViews[19]);
      playButtonItemOriginalWriting.setGraphic(imageViews[20]);
      backButtonItemOriginalWriting.setGraphic(imageViews[22]);
      forwardButtonItemOriginalWriting.setGraphic(imageViews[23]);
      stopButtonItemOriginalWriting.setGraphic(imageViews[24]);
      playButtonItemVirtualWriting.setGraphic(imageViews[25]);
      backButtonItemVirtualWriting.setGraphic(imageViews[27]);
      forwardButtonItemVirtualWriting.setGraphic(imageViews[28]);
      stopButtonItemVirtualWriting.setGraphic(imageViews[29]);
      correctionButtonWriting.setGraphic(imageViews[30]);

      /* --------  Translation ----------*/
      playButtonTranslation.setGraphic(imageViews[31]);
      backButtonTranslation.setGraphic(imageViews[33]);
      forwardButtonTranslation.setGraphic(imageViews[34]);
      stopButtonTranslation.setGraphic(imageViews[35]);
      playButtonItemOriginalTranslation.setGraphic(imageViews[36]);
      backButtonItemOriginalTranslation.setGraphic(imageViews[38]);
      forwardButtonItemOriginalTranslation.setGraphic(imageViews[39]);
      stopButtonItemOriginalTranslation.setGraphic(imageViews[40]);
      playButtonItemVirtualTranslation.setGraphic(imageViews[41]);
      backButtonItemVirtualTranslation.setGraphic(imageViews[43]);
      forwardButtonItemVirtualTranslation.setGraphic(imageViews[44]);
      stopButtonItemVirtualTranslation.setGraphic(imageViews[45]);
      correctionButtonTranslation.setGraphic(imageViews[46]);
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "MainController / setImageButton()", e.toString(), e);
    }
   }


  /**
   * Controller for slider media play button
   */
  @FXML public void handlePlayButton()
   {
    try {
      /*/* Status st = mediaPlayer.getStatus(); */
      mediaSliderReading.setDisable(false);
      mediaSliderWriting.setDisable(false);
      mediaSliderTranslation.setDisable(false);

//<editor-fold defaultstate="collapsed" desc="if "in pause"  drag the slidermedia it doesn´t detect the end of file">
      if (mediaPlayer.getCurrentTime().equals(mediaPlayer.getStopTime())) {
        handleStopButton();
        mediaPlayerPlay();
        playedImageButton();
        playedImageButtonOriginal();
        return;
      }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="if I push the normal play in "normal mode" (It's stopped)">
      if ((status == Status.STOPPED || status == Status.READY) &&  ! originalButton) {
        //handleStopButton();
        indexItemV = listViewV.getSelectionModel().getSelectedIndex();
        if (indexItemV != 0) {
          start = itemsOriginal[indexItemV].getStart();
          mediaPlayer.setStartTime(Duration.seconds(start));
          mediaPlayer.setStopTime(duration);
        }
        mediaPlayerStop();
        mediaPlayerPlay();
        pausedImageButton();
        return;
      }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="if I push the play button in "pause mode" (i's paused)">
      if (status == Status.PAUSED &&  ! originalButton) {
        // Re-start the media
        mediaPlayer.setStartTime(mediaPlayer.getCurrentTime());
        mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());
        // Stoping the original pause button
        originalButton = false;
        mediaPlayerPlay();
        pausedImageButton();
        return;
      }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="if I push the play button in "original mode" (it continues until the end)">
      if ((status == Status.PAUSED || status == Status.STOPPED || status == Status.READY) && originalButton) {
        mediaPlayer.setStartTime(mediaPlayer.getCurrentTime());
        mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());
        // Stoping the original pause button
        originalButton = false;
        // Enable the scroll to markers
        playButtonBoolean = true;
        mediaPlayerPlay();
        pausedImageButton();
        playedImageButtonOriginal();
        return;
      }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="if I push the play button in "original mode" (it continues until the end)">
      if ((status == Status.PLAYING) && originalButton) {
        mediaPlayer.setStartTime(mediaPlayer.getCurrentTime());
        mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());
        // Stoping the original pause button
        originalButton = false;
        // Enable the scroll to markers
        playButtonBoolean = true;
        mediaPlayerPlay();
        pausedImageButton();
        playedImageButtonOriginal();
        return;
      }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="By default">
      if (status == Status.PLAYING &&  ! originalButton) {
        mediaPlayerPause();
        playedImageButton();
        playedImageButtonOriginal();
      }
//</editor-fold>

    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handlePlayButton()", e.toString(), e);
    }
   }


  /**
   * Controller for the stop button
   */
  @FXML public void handleStopButton()
   {
    try {
      //if (!originalButton) {
      mediaPlayerStop();
      mediaPlayer.setStartTime(Duration.ZERO);
      mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());
      /*/*mediaPlayer.seek(Duration.ZERO);*/
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
      timeLabelReading.setText(ft.formatting(Duration.ZERO, duration));
      // Enable the scroll to markers
      playButtonBoolean = true;
      // Stoping the original pause button
      originalButton = false;
      // The first lap of the slider
      mediaPlayerSlider = "media"; // to the first lap
      // Change the image buttons to play
      playedImageButton();
      playedImageButtonOriginal();
      //}
    } catch (ArrayIndexOutOfBoundsException ex) {
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleStopButton()", e.toString(), e);
    }
   }


  /**
   * Handle of the Play Button Item
   */
  @FXML public void handlePlayButtonItemOriginal()
   {
    try {

      /*/* Status status = mediaPlayer.getStatus(); */
      mediaSliderReading.setDisable(true);
      mediaSliderWriting.setDisable(true);
      mediaSliderTranslation.setDisable(true);
      indexItemV = listViewV.getSelectionModel().getSelectedIndex();
      start = itemsOriginal[indexItemV].getStart();
      end = itemsOriginal[indexItemV].getEnd();
      mediaPlayer.setStartTime(Duration.seconds(start));
      mediaPlayer.setStopTime(Duration.seconds(end));
      // if (!originalButton) Duration.seconds(start);
      // it´s used for don´t scroll when it´s playing an itemInicio
      playButtonBoolean = false;
      // Si le doy al play frase pero ya está reproduciendo en normal
      if ( ! originalButton) {
        pausedImageButtonOriginal();
        playedImageButton();
        mediaPlayerPlay();
        originalButton = true;
        return;
      }
      originalButton = true;
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
            currentOriginal = mediaPlayer.getCurrentTime();
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
        indexItemV = listViewV.getSelectionModel().getSelectedIndex();
        currentOriginal = new Duration(itemsOriginal[indexItemV].getStart());
        mediaPlayerPlay();
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handlePlayButtonItemOriginal()", e.toString(), e);
    }
   }


  /**
   * Handle of the Button Stop Item Original
   */
  @FXML private void handleStopButtonItemOriginal()
   {
    if (originalButton) {
      mediaPlayerStop();
      // Change the image buttons to play
      playedImageButton();
      playedImageButtonOriginal();
    }
   }


  /**
   * Handle of the Button Play Item Machine
   */
  @FXML private void handlePlayButtonItemOriginalMachine()
   {
    // Create an Observablelist to listview H    
    ObservableList olItems = listViewH01Reading.getSelectionModel().getSelectedItems();
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
            cont ++;
            break;
          case "aux":
            text[cont] = "auxxx";
            cont ++;
            break;
          default:
            text[cont] = item.toString();
            cont ++;
            break;
        }
      }
      // Play the audioclips one behind the other
      audioClipsWords[subOrig].get(text[0]).play();
      if (size > 1) {
        Thread audio = new Thread(new Runnable()
         {

          @Override
          public void run()
           {
            exitLoop = false;
            for (int i = 1; i < size; i ++) {
              if (exitLoop) {
                break;
              }
              while (audioClipsWords[subOrig].get(text[i - 1]).
                      isPlaying()) {
              }
              audioClipsWords[subOrig].get(text[i]).play();
            }
           }


         });
        audio.start();
      }
    }
   }


  /**
   * Handle of the Button Play Item Machine
   */
  @FXML private void handlePlayButtonItemTranslationMachine()
   {
    // Create an Observablelist to listview H    
    ObservableList olItems = listViewH02Reading.getSelectionModel().getSelectedItems();
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
            cont ++;
            break;
          case "aux":
            text[cont] = "auxxx";
            cont ++;
            break;
          default:
            text[cont] = item.toString();
            cont ++;
            break;
        }
      }
      // Play the audioclips one behind the other
      audioClipsWords[subTrans].get(text[0]).play();
      if (size > 1) {
        Thread audio = new Thread(new Runnable()
         {

          @Override
          public void run()
           {
            exitLoop = false;
            for (int i = 1; i < size; i ++) {
              if (exitLoop) {
                break;
              }
              while (audioClipsWords[subTrans].get(text[i - 1]).
                      isPlaying()) {
              }
              audioClipsWords[subTrans].get(text[i]).play();
            }
           }


         });
        audio.start();
      }
    }
   }


  /**
   * Handle of the button Stop Machine
   */
  @FXML private void handleStopButtonItemMachine()
   {
    exitLoop = true;
   }


  /**
   * handle of the
   */
  @FXML private void handleBackButton()
   {
    if (indexItemV > 0) {
      // Back the index
      indexItemV --;
      // index of listview to zero
      listViewV.scrollTo(indexItemV);
      listViewV.getSelectionModel().select(indexItemV);
      backForward();
    }
   }


  /**
   * handle of the
   */
  @FXML private void handleForwardButton()
   {
    try {
      if (indexItemV < itemsOriginal.length) {
        // forward the index
        indexItemV ++;
        // index of listview to zero
        listViewV.scrollTo(indexItemV);
        listViewV.getSelectionModel().select(indexItemV);
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
    start = itemsOriginal[indexItemV].getStart();
    /*/*end = itemsOriginal[indexItemV].getEnd();*/
    end = duration.toMillis();
    mediaPlayer.setStartTime(Duration.seconds(start));
    mediaPlayer.setStopTime(Duration.seconds(end));
    // putting in listviewH the phases
    showListViewH();
    // Label time to cero
    duration = mediaPlayer.getMedia().getDuration();
    timeLabelReading.setText(ft.formatting(Duration.seconds(start), duration));
    // Enable the scroll to markers
    playButtonBoolean = true;
    // Stoping the original pause button
    originalButton = false;
    // The first lap of the slider
    mediaPlayerSlider = "media"; // to the first lap
    // Change the image buttons to play
    playedImageButton();
    playedImageButtonOriginal();
    mediaPlayerStop();
   }


  /**
   * handle of the
   */
  @FXML private void handleBackButtonItemOriginal()
   {
    if (originalButton) {
      if (mediaPlayer.getCurrentTime()
              .greaterThanOrEqualTo(Duration.seconds(start)
                      .add(Duration.millis(1000)))) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.
                millis(1000)));
        currentOriginal = mediaPlayer.getCurrentTime();
      }
    }
   }


  /**
   * handle of the
   */
  @FXML private void handleForwardPlayButtonItemOriginal()
   {
    if (originalButton) {
      if (mediaPlayer.getCurrentTime()
              .lessThanOrEqualTo(Duration.seconds(end)
                      .subtract(Duration.millis(1000)))) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.
                millis(1000)));
        currentOriginal = mediaPlayer.getCurrentTime();
      }
    }
   }


  /**
   * Setting the Correction button
   */
  @FXML private void handlecorrectionButtonWriting()
   {
    handleCorrectionButton(textFieldWriting);
   }


  /**
   * Setting the correction Button Translation
   */
  @FXML private void handlecorrectionButtonTranslation()
   {
    handleCorrectionButton(textFieldTranslation);
   }


  /**
   *
   */
  private void playedImageButton()
   {
    playButtonReading.setGraphic(imageViews[0]);
    playButtonWriting.setGraphic(imageViews[15]);
    playButtonTranslation.setGraphic(imageViews[31]);
   }


  /**
   *
   */
  private void pausedImageButton()
   {
    playButtonReading.setGraphic(imageViews[1]);
    playButtonWriting.setGraphic(imageViews[16]);
    playButtonTranslation.setGraphic(imageViews[32]);
   }


  /**
   * Change the image of the play / pause button
   */
  private void playedImageButtonOriginal()
   {
    playButtonItemOriginalReading.setGraphic(imageViews[5]);
    playButtonItemOriginalWriting.setGraphic(imageViews[20]);
    playButtonItemOriginalTranslation.setGraphic(imageViews[36]);
   }


  /**
   * Change the image of the play / pause button
   */
  private void pausedImageButtonOriginal()
   {
    playButtonItemOriginalReading.setGraphic(imageViews[6]);
    playButtonItemOriginalWriting.setGraphic(imageViews[21]);
    playButtonItemOriginalTranslation.setGraphic(imageViews[37]);
   }


  /**
   * Change the image of the play / pause button
   */
  private void playedImageButtonVirtual()
   {
    playButtonItemVirtualReading.setGraphic(imageViews[10]);
    playButtonItemVirtualWriting.setGraphic(imageViews[25]);
    playButtonItemVirtualTranslation.setGraphic(imageViews[41]);
   }


  /**
   * Change the image of the play / pause button
   */
  private void pausedImagegButtonVirtual()
   {
    playButtonItemVirtualReading.setGraphic(imageViews[11]);
    playButtonItemVirtualWriting.setGraphic(imageViews[26]);
    playButtonItemVirtualTranslation.setGraphic(imageViews[42]);
   }


  /**
   * handle of the
   */
  private void handleCorrectionButton(TextField tf)
   {
    try {
      listViewH02Reading.setVisible(true);
      indexItemV = listViewV.getSelectionModel().getSelectedIndex();
      markerTextOriginal = itemsOriginal[indexItemV].getText();
      markerTextTranslation = itemsTranslation[indexItemV].getText();
      // Extract the orignial text and the answer
      if (currentTab.equals(escribir)) {
        itemWordsOriginal = swal.cleanWords(markerTextOriginal);
      } else if (currentTab.equals(traducir)) {
        itemWordsOriginal = swal.cleanWords(markerTextTranslation);
      }
      // Count the letters within blanc spaces
      if ( ! tf.getText().isEmpty() && tf.getText().trim().length() > 0) {
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
      double success = 0;
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
      for (int i = 0; i < sizePhrase; i ++) {
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
        for (int j = 0; j < size[i].length(); j ++) {
          String l1 = String.valueOf(itemWordsOriginal[i].charAt(j));
          String l2 = String.valueOf(itemWordsTranslation[i].charAt(j));
          if (l1.equals(l2)) {
            success += letterPoints;
          }
        }
      }
      // put the result in the progress indicator
      success /= 100;
      if (currentTab.equals(escribir)) {
        indicatorSuccessWriting.setProgress(success);
      } else if (currentTab.equals(traducir)) {
        indicatorSuccessTranslation.setProgress(success);
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleCorrectionButton()", e.toString(), e);
    }
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
    setSliderForm(balanceSliderReading,  - 1.0, 1.0, 0.0, 1, 10);
    balanceLabelReading.setText("0,00"); // initial value
    // Slider tab Writing
    setSliderForm(volumeSliderWriting, 0.0, 100.0, 20.0, 20, 2);
    volumeLabelWriting.setText("20,00"); // initial value
    setSliderForm(rateSliderWriting, 0.80, 1.0, 1.0, 0.02, 1);
    rateLabelWriting.setText("1,00"); // initial value
    setSliderForm(balanceSliderWriting,  - 1.0, 1.0, 0.0, 1, 10);
    balanceLabelWriting.setText("0,00"); // initial value   
    // Slider tab Translation
    setSliderForm(volumeSliderTranslation, 0.0, 100.0, 20.0, 20, 2);
    volumeLabelTranslation.setText("20,00"); // initial value
    setSliderForm(rateSliderTranslation, 0.80, 1.0, 1.0, 0.02, 1);
    rateLabelTranslation.setText("1,00"); // initial value
    setSliderForm(balanceSliderTranslation,  - 1.0, 1.0, 0.0, 1, 10);
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
  private void setSliderForm(Slider slider, Double min, Double max,
          Double initial,
          double majorTick, int minorTick)
   {
    // min, max and initial value
    slider.setMin(min);
    slider.setMax(max);
    slider.setValue(initial);
    slider.setBlockIncrement(10.1);
    // incicates whether the value of the slide should be aligned with the
    // tick marks.
    slider.setSnapToTicks(true);
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
        Double doubleValue = newValue.doubleValue();// Number to Double
        String value = String.valueOf(doubleValue);// Double to string
        value = value.format("%.2f", newValue);// to String.format
        if (slider == volumeSliderReading) {
          volumeLabelReading.setText(value);
          // Configuring the rate
          mediaPlayer.setVolume((Double) newValue / 100);
        } else if (slider == rateSliderReading) {
          rateLabelReading.setText(value);
          // Configuring the rate
          mediaPlayer.setRate((Double) newValue);
        } else if (slider == balanceSliderReading) {
          balanceLabelReading.setText(value);
          // Configuring the rate
          mediaPlayer.setBalance((Double) newValue);
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
      mediaPlayer.setVolume(volumeSliderReading.getValue());
      mediaPlayer.setRate(rateSliderReading.getValue());
      mediaPlayer.setBalance(balanceSliderReading.getValue());
      // Set the markers on the media
      setMarkers(itemsOriginal);
      // Setting a markers listeners to the media
      setEventMarker();
      // setting the playback errors
      setMediaPlayerChangeListener();
      // Setting the event (Thread) that move the sliderMedia
      setEventMedia();
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

  //<editor-fold defaultstate="collapsed" desc="MediaPlayer events to Slider">

  /**
   *
   */
  private void setEventMedia()
   {
    mediaToSlideThread = new Thread(new Runnable()
     {

      @Override
      public void run()
       {
        try {
          while (getMediaToSlider()) {
            mediaToSlideThread.sleep(1000);
            Platform.runLater(() -> updateValuesMedia());
          }
        } catch (InterruptedException e) {
          message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / mediaToSlidemedia()", e.toString(), e);
        }
       }

     });
    //if (mediaToSlideThread.currentThread().getState().equals(Thread.State.RUNNABLE)) {
    mediaToSlideThread.start();
    mediaToSliderPlay();
    //}
   }

  /**
   *
   */
  private synchronized void mediaToSliderStop()
   {
    this.mediaToSlider = false;
   }

  /**
   *
   */
  private synchronized void mediaToSliderPlay()
   {
    this.mediaToSlider = true;
   }

  /**
   *
   */
  private synchronized boolean getMediaToSlider()
   {
    return this.mediaToSlider;
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

      /*/* Status s = mediaPlayer.getStatus(); */
      if (status.equals(Status.PLAYING)) {
        if (originalButton) {
          return "playingOriginal";
        } else {
          return "playing";
        }
      }
      if (status.equals(Status.PAUSED)) {
        if (originalButton) {
          return "originalPause";
        } else {
          return "pause";
        }
      }
    } catch (NullPointerException ex) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / handleOpenMenu()", ex.toString(), ex);
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

//<editor-fold defaultstate="collapsed" desc="Setting the listView">

  /**
   * Set the Handler event for the listview V
   */
  private void setListViewV()
   {
    try {
      // Enable the selection mode
      listViewV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
      //For vertical ListView this is the height, for a horizontal ListView this is the width.  
      listViewV.setFixedCellSize(40);
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
          indexItemV = listViewV.getSelectionModel().getSelectedIndex();
          showListViewH();
          // if I do double click
          if (event.getClickCount() == 2) {
            handlePlayButtonItemOriginal();
          }
         }


       });
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setListViewV()", e.toString(), e);
    }
   }


  /**
   * Put the word of itemInicio in listView H
   */
  private void showListViewH()
   {
    try {
      // Get the index of the clicked itemInicio
      indexItemV = listViewV.getSelectionModel().getSelectedIndex();
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
      listViewH01Reading.setItems(listItemOriginal);
      listViewH02Reading.setItems(listItemTranslation);
      listViewH01Reading.getSelectionModel().select(0);
      listViewH02Reading.getSelectionModel().select(0);
    } catch (ArrayIndexOutOfBoundsException ex) {
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / showListViewH()", e.toString(), e);
    }
   }


  /**
   * Set the Handler event for the listview H
   */
  private void setListViewH()
   {
    try {
      // Enable the selection mode
      listViewH01Reading.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      listViewH02Reading.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      // Enable the listeners
      listViewH01Reading.setOnMouseClicked(new EventHandler<MouseEvent>()
       {

        @Override
        public void handle(MouseEvent event)
         {
          // if I do double click
          if (event.getClickCount() == 2) {
            handlePlayButtonItemOriginalMachine();
          }
          setBorder(listViewH01Reading);
          currentTab = leer;
         }


       });
      listViewH02Reading.setOnMouseClicked(new EventHandler<MouseEvent>()
       {

        @Override
        public void handle(MouseEvent event)
         {
          // if I do double click
          if (event.getClickCount() == 2) {
            handlePlayButtonItemTranslationMachine();
          }
          setBorder(listViewH02Reading);
          currentTab = leer;
         }


       });
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setListViewH()", e.toString(), e);
    }
   }


  /**
   *
   */
  private void changeListviewBySliderMedia()
   {
    double positionSlider, totalDuration;
    try {
      positionSlider = mediaSliderReading.getValue();
      totalDuration = duration.toMillis();
      sliderMediaCurrent = (positionSlider * totalDuration) / 100000;
      int idInicio, idFinal, contInicio, contFinal, itemInicio, itemFinal, suplenteInicio, suplenteFinal;
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
      do {
        idInicio = itemsOriginal[itemInicio].getId();
        idFinal = itemsOriginal[itemFinal].getId();
        inicio = itemsOriginal[itemInicio].getStart();
        fin = itemsOriginal[itemFinal].getEnd();

        /*/*System.out.println("item " + itemInicio + " - " + itemFinal +
                "  lonj " + lonjInicio + " - " + lonjFinal +
                " inicio " + inicio + " " + sliderMediaCurrent + " " + fin +
                " cont " + contInicio + " - " + contFinal +
                " itemFinal-itemInicio " + (itemFinal - itemInicio)); */
        if (inicio < sliderMediaCurrent) {
          suplenteInicio = itemInicio;
          contInicio = 2;
          itemInicio = itemInicio + ((lonjInicio - itemInicio) / contInicio);
        } else {
          contInicio = (contInicio > 15) ? contInicio ++ : 15;
          lonjInicio = itemInicio;
          itemInicio = suplenteInicio + ((lonjInicio - suplenteInicio) / contInicio);
        }
        if (fin > sliderMediaCurrent) {
          suplenteFinal = itemFinal;
          contFinal = 2;
          itemFinal = itemFinal - ((itemFinal - lonjFinal) / contFinal);
        } else {
          contFinal = (contFinal >= 15) ? contFinal ++ : 15;
          lonjFinal = itemFinal;
          itemFinal = suplenteFinal - ((suplenteFinal - lonjFinal) / contFinal);
        }
        // Check if the sliderMedia is minor or mayor to the star or end
        if (sliderMediaCurrent < itemsOriginal[0].getStart()) salida = 1;
        if (sliderMediaCurrent > itemsOriginal[itemsOriginal.length - 1].getStart()) salida = 2;
      } while ( ! ((sliderMediaCurrent >= inicio && sliderMediaCurrent <= fin) &&
              ((lonjInicio - lonjFinal) <= 3 && (lonjInicio - lonjFinal) >= 0) ||
              ((salida != 0))));
      if (salida == 2 || idFinal < idInicio) idInicio = idFinal;
      // System.out.println("id " + idInicio);
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
      for (int i = 0; i < items.length - 1; i ++) {
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
          if ( ! playButtonBoolean) {
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

            /*/* Status status = mediaPlayer.getStatus(); */
            if (status.equals(Status.PAUSED) || status.equals(Status.PLAYING)) {
              playedImageButton();
              playedImageButtonOriginal();
            } else {
              playedImageButton();
              playedImageButtonOriginal();
              /*/*return;*/
            }
          }
          if ( ! originalButton) {
            /*/*currentOriginal = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(currentOriginal);
            mediaPlayerPause();*/
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
    //Set the media to change the mediaSliderReading when it´s played
    /*/* mediaPlayer.currentTimeProperty().addListener(mediaPlayerChangeListener); */
    // Set the slider to change the media when it's moved
    /*/*sliderMedia.valueProperty().addListener(sliderMeediaChangeListener);*/
    // set the slider blocking the change media when click on the 

    sliderMedia.setOnMouseClicked(new EventHandler<MouseEvent>()
     {

      @Override
      public void handle(MouseEvent t)
       {
        sliderMedia.requestFocus();
        setBorder(sliderMedia);
        changeListviewBySliderMedia();
        mediaPlayerSlider = "slider";
        updateValuesSlider();
       }


     });
    
    // Setting the slider when pressing on it
    sliderMedia.setOnMousePressed(new EventHandler<MouseEvent>()
     {
      @Override
      public void handle(MouseEvent t)
       {
        mediaPlayerSlider = "slider";
        errorMediaSalir = mediaSliderReading.getValue();
       }

     });
    
    // Setting the slider when I release it.
    sliderMedia.setOnMouseReleased(new EventHandler<MouseEvent>()
     {

      @Override
      public void handle(MouseEvent t)
       {
        mediaPlayerSlider = "media";
        System.out.println("media");
        updateValuesSlider();
       }

     });
    
        
    // Setting the slider when I release it.
    sliderMedia.setOnMouseExited(new EventHandler<MouseEvent>()
     {

      @Override
      public void handle(MouseEvent t)
       {
        if (mediaPlayerSlider.equals("slider"))
        System.out.println("mal");
        
       }

     });
   }


  /**
   * updateValuesMedia
   *
   */
  public void updateValuesMedia()
   {
    if (timeLabelReading != null && mediaSliderReading != null && duration != null) {
      // Extract the current time of the mediaplayer
      currentTime = mediaPlayer.getCurrentTime();

      double totalDuration = duration.toMillis();
      double positionMedia = currentTime.toMillis();
      double positionFinal = ((positionMedia * 100) / totalDuration);
      if (mediaSliderReading.equals("media")) mediaSliderReading.setValue(positionFinal);

    }
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
      if ( ! mediaSliderReading.isDisable() && duration.greaterThan(Duration.ZERO)) {
        Status temp = status;
        mediaPlayer.stop();
        mediaPlayer.seek(new Duration(sliderMediaCurrent));
        mediaPlayer.setStartTime(Duration.seconds(sliderMediaCurrent));
        mediaPlayer.setStopTime(duration);
        if (temp.equals(Status.PLAYING)) mediaPlayer.play();
        if (temp.equals(Status.PAUSED)) mediaPlayer.pause();
      }
    } catch (Exception e) {
      message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController.java / updateValues()", e.toString(), e);
    }

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
    sliderMedia.setMinorTickCount(10);
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
    //<editor-fold defaultstate="collapsed" desc="listViewV">
    // ListView V
    listViewV.setOnKeyPressed(new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent ke)
       {
        try {
          indexItemV = listViewV.getSelectionModel().getSelectedIndex();
          switch (ke.getCode()) {
            case UP:
              if (indexItemV > 0) {
                indexItemV --;
                listViewV.getSelectionModel().select(indexItemV);
                showListViewH();
                listViewV.getSelectionModel().select(indexItemV + 1);
                //ke.consume();
              }
              break;
            case DOWN:
              if (indexItemV < itemsOriginal.length) {
                indexItemV ++;
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

    //<editor-fold defaultstate="collapsed" desc="listViewH">
    // the event onclick is in Setting the listview
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

    //<editor-fold defaultstate="collapsed" desc="Button, sliders, sliderMedia">
    eventButton(playButtonReading, 4, 11, 6, 0);
    eventButton(backButtonReading, 4, 12, 7, 5);
    eventButton(forwardButtonReading, 4, 13, 8, 6);
    eventButton(stopButtonReading, 4, 14, 9, 7);
    eventMediaSlider(mediaSliderReading, 4, 15, -1, 8);
    eventButton(playButtonItemOriginalReading, 4, 18, 11, 0);
    eventButton(backButtonItemOriginalReading, 5, 18, 12, 10);
    eventButton(forwardButtonItemOriginalReading, 6, 18, 13, 11);
    eventButton(stopButtonItemOriginalReading, 7, 18, 14, 12);
    eventButton(playButtonItemVirtualReading, 8, 19, 15, 13);
    eventButton(backButtonItemVirtualReading, 9, 19, 16, 14);
    eventButton(forwardButtonItemVirtualReading, 9, 19, 17, 15);
    eventButton(stopButtonItemVirtualReading, 9, 20, -1, 16);
    eventFilterSlider(rateSliderReading, 11, 1, 19, 0, 0.5, 2, 0.01);
    eventFilterSlider(balanceSliderReading, 14, 1, 20, 18,  - 1.0, 1, 0.1);
    eventFilterSlider(volumeSliderReading, 17, 1, -1, 19, 0.0, 100, 1);
    /* ------------------------------------------------------------------------------------- */
    eventButton(playButtonWriting, 21, 28, 23, 0);
    eventButton(backButtonWriting, 21, 29, 24, 22);
    eventButton(forwardButtonWriting, 21, 30, 25, 23);
    eventButton(stopButtonWriting, 21, 31, 26, 24);
    eventMediaSlider(mediaSliderWriting, 21, 32, -1, 25);
    eventButton(playButtonItemOriginalWriting, 21, 36, 28, 0);
    eventButton(backButtonItemOriginalWriting, 22, 36, 29, 27);
    eventButton(forwardButtonItemOriginalWriting, 23, 36, 30, 28);
    eventButton(stopButtonItemOriginalWriting, 24, 36, 31, 29);
    eventButton(playButtonItemVirtualWriting, 25, 37, 32, 30);
    eventButton(backButtonItemVirtualWriting, 26, 37, 33, 31);
    eventButton(forwardButtonItemVirtualWriting, 26, 37, 34, 32);
    eventButton(stopButtonItemVirtualWriting, 26, 38, 35, 33);
    eventButton(correctionButtonWriting, 26, 38, -1, 34);
    eventFilterSlider(rateSliderWriting, 28, 1, 37, 0, 0.5, 2, 0.01);
    eventFilterSlider(balanceSliderWriting, 31, 1, 38, 36,  - 1.0, 1, 0.1);
    eventFilterSlider(volumeSliderWriting, 34, 1, -1, 37, 0.0, 100, 1);
    /* ------------------------------------------------------------------------------------- */
    eventButton(playButtonTranslation, 39, 46, 41, 0);
    eventButton(backButtonTranslation, 39, 47, 42, 40);
    eventButton(forwardButtonTranslation, 39, 48, 43, 41);
    eventButton(stopButtonTranslation, 39, 49, 44, 42);
    eventMediaSlider(mediaSliderTranslation, 39, 50, -1, 43);
    eventButton(playButtonItemOriginalTranslation, 39, 54, 46, 0);
    eventButton(backButtonItemOriginalTranslation, 40, 54, 47, 45);
    eventButton(forwardButtonItemOriginalTranslation, 41, 54, 48, 46);
    eventButton(stopButtonItemOriginalTranslation, 42, 54, 49, 47);
    eventButton(playButtonItemVirtualTranslation, 43, 55, 50, 48);
    eventButton(backButtonItemVirtualTranslation, 44, 55, 51, 49);
    eventButton(forwardButtonItemVirtualTranslation, 44, 55, 52, 50);
    eventButton(stopButtonItemVirtualTranslation, 44, 56, 53, 51);
    eventButton(correctionButtonTranslation, 44, 56, -1, 52);
    eventFilterSlider(rateSliderTranslation, 46, 1, 55, 0, 0.5, 2, 0.01);
    eventFilterSlider(balanceSliderTranslation, 49, 1, 56, 54,  - 1.0, 1, 0.1);
    eventFilterSlider(volumeSliderTranslation, 52, 1, -1, 55, 0.0, 100, 1);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tab pane">

    tabPanelListViewH.getSelectionModel()
            .selectedItemProperty().addListener(new ChangeListener<Tab>()
             {

              @Override
              public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1)
               {
                if (t1.getText().equals(leer)) {
                  indexItemV = listViewV.getSelectionModel().getSelectedIndex();
                  flw.setListView(listViewV, itemsOriginal);
                  oldNode = rateSliderReading;
                } else if (t1.getText().equals(escribir)) {
                  flw.setListView(listViewV, itemsTranslation);
                  oldNode = rateSliderWriting;
                } else if (t1.getText().equals(traducir)) {
                  flw.setListView(listViewV, itemsOriginal);
                  oldNode = rateSliderTranslation;
                }
                listViewV.getSelectionModel().select(indexItemV);
                currentTab = t1.getText();

                listViewH01Reading.setVisible(currentTab.equals(leer));
                textFieldWriting.setVisible(currentTab.equals(escribir));
                textFieldTranslation.setVisible(currentTab.equals(traducir));
                listViewH02Reading.setVisible(currentTab.equals(leer));
               }


              @Override
              public String toString()
               {
                return super.toString();
               }

             }
            );

    tabPanelListViewH.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent event
      )
       {
        KeyCode key = event.getCode();
        // An triger
        if ( ! event.getTarget().equals(tabPanelListViewH)) return;

        if (key.equals(KeyCode.UP)) {
          event.consume();
          if (oldNode.equals(listViewV)) {
            if (currentTab.equals(leer)) {
              rateSliderReading.requestFocus();
              setBorder(rateSliderReading);
            }
            if (currentTab.equals(escribir)) {
              rateSliderWriting.requestFocus();
              setBorder(rateSliderWriting);
            }
            if (currentTab.equals(traducir)) {
              rateSliderTranslation.requestFocus();
              setBorder(rateSliderTranslation);
            }
          } else {
            oldNode.requestFocus();
            setBorder(oldNode);
          }
        }
        if (key.equals(KeyCode.LEFT) && currentTab.equals(leer)) {
          setBorder(listViewV);
          listViewV.requestFocus();
          event.consume();
        }
        if (key.equals(KeyCode.RIGHT) && currentTab.equals(traducir)) {
          event.consume();
        }
        if (key.equals(KeyCode.DOWN)) {
          event.consume();
        }
       }

     }
    );

    tabPanelListViewH.setOnMouseReleased((MouseEvent) -> {

      setBorder(tabPanelListViewH);
      MouseEvent.consume();

      if (currentTab.equals(leer)) {
        flw.setListView(listViewV, itemsOriginal);
        oldNode = rateSliderReading;
      } else if (currentTab.equals(escribir)) {
        flw.setListView(listViewV, itemsTranslation);
        oldNode = rateSliderWriting;
      } else if (currentTab.equals(traducir)) {
        flw.setListView(listViewV, itemsOriginal);
        oldNode = rateSliderTranslation;
      }
    }
    );
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="TextField">
    eventTextfield(textFieldWriting, 3, 22, 0, -1);
    eventTextfield(textFieldTranslation, 3, 40, 0, -1);
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
        if (i != -1) {
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
   }

  //</editor-fold>

  //<editor-fold defaultstate="collapsed" desc="MediaSlider">

  /**
   * Helper to the slider media event
   *
   * @param slider The slider to setting
   * @param up the above node
   * @param down the belong node
   * @param right the right node
   * @param left the left node
   */
  private void eventMediaSlider(Slider slider, int up, int down, int right, int left)
   {
    slider.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {

      //<editor-fold defaultstate="collapsed" desc="Teclas combinadas con Control">

      if (kcLeft.match(event) && slider.getValue() > 0) {
        slider.setValue(slider.getValue() - 1);

        changeListviewBySliderMedia();

        mediaPlayerSlider = "slider";
        updateValuesSlider();
        event.consume();
        return;
      }
      if (kcLeft.match(event) && slider.getValue() <= 0) {
        event.consume();
        return;
      }
      if (kcRight.match(event) && slider.getValue() < duration.toMillis()) {
        slider.setValue(slider.getValue() + 1);

        changeListviewBySliderMedia();

        mediaPlayerSlider = "slider";
        updateValuesSlider();
        event.consume();
        return;
      }
      if (kcRight.match(event) && slider.getValue() >= duration.toMillis()) {
        handleStopButton();
        event.consume();
        return;
      }

      //</editor-fold>

      KeyCode code = event.getCode();
      int i = -1;

      // This is to the oldnode, listViewH01Reading or listViewH02Reading Writting Translatiion
      int[] is = new int[]{14, 15, 16, 17, 31, 32, 33, 34, 35, 49, 50, 51, 52, 53};

      if (code.equals(KeyCode.UP)) {
        i = up;
      }

      if (code.equals(KeyCode.DOWN)) {
        i = down;
        for (int n : is) {
          if (oldNode.equals(node[n])) {
            i = n;
          }
        }
      }

      if (code.equals(KeyCode.LEFT)) {
        i = left;
      }

      if (code.equals(KeyCode.RIGHT)) {
        i = right;
        event.consume(); // Fix the slider
      }

      if (code.equals(KeyCode.SPACE) || code.equals(KeyCode.ENTER)) {
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
        oldNode = slider;
        event.consume();
      }
    });
    // the slider.setOnMouseClicked is in Scroll Bar
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
  private void eventFilterSlider(Slider slider, int up, int down, int right, int left, double min, double max,
          double per)
   {
    slider.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
      KeyCode code = event.getCode();
      if (kcLeft.match(event) && slider.getValue() > min) {
        slider.setValue(slider.getValue() - per);
        event.consume();
        return;
      } else if (kcLeft.match(event) && slider.getValue() == min) {
        event.consume();
        return;
      }
      if (kcRight.match(event) && slider.getValue() < max) {
        slider.setValue(slider.getValue() + per);
        event.consume();
        return;
      } else if (kcRight.match(event) && slider.getValue() == max) {
        event.consume();
        return;
      }
      if (code.equals(KeyCode.UP)) {
        int[] i = new int[]{0, 1, 18, 19, 20, 36, 37, 38, 54, 55, 56};
        boolean salida = false;
        for (int j : i) {
          if (oldNode.equals(node[j])) {
            salida = true;
            break;
          }
        }
        if (salida) {
          node[up].requestFocus();
          setBorder(node[up]);
          event.consume();
        } else {
          oldNode.requestFocus();
          setBorder(oldNode);
          event.consume();
        }
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
    });
    slider.setOnMouseClicked(
            (MouseEvent) -> {
      slider.requestFocus();
      setBorder(slider);
      MouseEvent.consume();
    });
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
    tf.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent event)
       {
        /*/*listViewH02Reading.setVisible(false);*/
        KeyCode ke = event.getCode();
        int i = -1;
        if (ke.equals(KeyCode.UP)) {
          i = up;
        }
        if (ke.equals(KeyCode.DOWN)) {
          int[] is = new int[]{27, 22, 23, 24, 25, 26, 45, 40, 41, 42, 43, 44};
          //Arrays.asList(is).contains(Arrays.asList(node).indexOf(oldNode));
          i = down;
          for (int n : is) {
            if (oldNode.equals(node[n])) i = n;
          }
        }
        if (ke.equals(KeyCode.LEFT) && tf.getCaretPosition() <= 0) {
          /*/*i = Integer.rotateLeft(i, subTrans);*/
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


     }
    );
    tf.setOnMouseClicked(
            (e) -> {
      setBorder(tf);
      oldNode = tf;
      e.consume();
      /*/*listViewH02Reading.setVisible(true);*/
    }
    );
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
  private void eventListviewH(Node n, int up, int down, int right, int left)
   {
    n.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>()
     {

      @Override
      public void handle(KeyEvent event)
       {
        // los textfield llevan un metodo propio, aqui solo se tocon los listview H01 H02
        int i = -1;
        KeyCode ke = event.getCode();
        // This is to the oldnode, listViewH01Reading or listViewH02Reading Writting Translatiion
        int[] is = (n.equals(listViewH01Reading)) ? new int[]{10, 5, 6, 7, 8, 9} : new int[]{3};
        try {

          if (ke.equals(KeyCode.UP)) {
            i = up;
          }

          if (ke.equals(KeyCode.DOWN)) {
            i = down;
            for (int n : is) {
              if (oldNode.equals(node[n])) {
                i = n;
              }
            }
          }

          if (ke.equals(KeyCode.LEFT)) {
            switch (currentNode.getId()) {
              case "listViewH01Reading":
                if (listViewH01Reading.getSelectionModel().getSelectedIndex() == 0) {
                  i = left;
                }
                break;
              case "listViewH02Reading":
                if (listViewH02Reading.getSelectionModel().getSelectedIndex() == 0) {
                  i = left;
                }
                break;
              default:
            }
          }

          if (ke.equals(KeyCode.RIGHT)) {
            i = right;
          }

          if (ke.equals(KeyCode.SPACE) || ke.equals(KeyCode.ENTER)) {
            if (currentNode.equals(listViewH01Reading)) {
              handlePlayButtonItemOriginalMachine();
            } else {
              handlePlayButtonItemTranslationMachine();
            }
            event.consume();
          }
          if (i != -1) {
            node[i].requestFocus();
            setBorder(node[i]);
            oldNode = n;
            event.consume();
          }
        } catch (Exception e) {
          message.message(Alert.AlertType.ERROR, "Error message", "PrincipalController / setTransversalFocus()", e.toString(), e);
        }
       }

     ;
   }

  );

      // the event onclick is in Setting the listview
   }
;
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
