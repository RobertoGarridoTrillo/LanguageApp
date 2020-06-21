package LanguageApp.controller;

//<editor-fold defaultstate="collapsed" desc="Import">

import LanguageApp.main.MainScene;
import LanguageApp.model.Item;
import LanguageApp.util.AudioClips;
import LanguageApp.util.Directory;
import LanguageApp.util.FillListView;
import LanguageApp.util.FormatTime;
import LanguageApp.util.GetJson;
import LanguageApp.util.SaveWordsAsList;
import LanguageApp.util.SelectedFile;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
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
 *
 * @author Roberto Garrido Trillo
 */
public class MainController {


//<editor-fold defaultstate="collapsed" desc="fields class">
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

   @FXML private Button playButtonItemMachineReading;
   @FXML private Button stopButtonItemMachineReading;

   @FXML private Button playButtonItemOriginalWriting;
   @FXML private Button stopButtonItemOriginalWriting;
   @FXML private Button backButtonItemOriginalWriting;
   @FXML private Button forwardButtonItemOriginalWriting;

   @FXML private Button playButtonItemOriginalTranslation;
   @FXML private Button stopButtonItemOriginalTranslation;
   @FXML private Button backButtonItemOriginalTranslation;
   @FXML private Button forwardButtonItemOriginalTranslation;

   @FXML private Button correctionButtonWriting;
   @FXML private Button correctionButtonTranslation;

   @FXML private ListView<String> listViewV;
   @FXML private AnchorPane anchorListViewV;
   @FXML private ListView<String> listViewH01Reading;
   @FXML private ListView<String> listViewH02Reading;
   @FXML private ListView<String> listViewH02Writing;
   @FXML private ListView<String> listViewH02Translation;

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

   @FXML private Tooltip tooltiptlistViewV;
   @FXML private Tooltip tooltipListViewH1;
   @FXML private Tooltip tooltipSliderReading;
   @FXML private Tooltip tooltipRateReading;
   @FXML private Tooltip tooltipBalanceReading;
   @FXML private Tooltip tooltipVolumenReading;
   @FXML private Tooltip tooltipSliderWriting;
   @FXML private Tooltip tooltipRateWriting;
   @FXML private Tooltip tooltipBalanceWriting;
   @FXML private Tooltip tooltipVolumenWriting;
   @FXML private Tooltip tooltipSliderTranslation;
   @FXML private Tooltip tooltipRateTranslation;
   @FXML private Tooltip tooltipBalanceTranslation;
   @FXML private Tooltip tooltipVolumenTranslation;
   @FXML private Tooltip toottipPlayOriginalReading;
   @FXML private Tooltip toottipPlayOriginalWriting;
   @FXML private Tooltip toottipPlayOriginalTranslation;

// The focused and old node
   Node currentNode, oldNode;
   // The tab thar is checkes
   private String currentTab;
   // for transversal movements
   Node[] node;

   // Reference to the main Stage from the main Scene
   private Stage mainStage;
   // Json
   private Item[] itemsOriginal;
   private Item[] itemsTranslation;
   // Player
   private MediaPlayer mediaPlayer;
   private Media media;
   private URI mediaStringUrl;
   private int indexSelected;
   private boolean playButtonBoolean; // if i´m touching an Item, don´t scroll
   private Duration duration; //  the total lenght of the media
   private Duration currentTime; // the actual position of the media

   // item
   private double start;
   private double end;
   private int indexItemV;

   // Save as a list of words
   private Set<String> wordSet;

   // Show ListView 
   private String[] itemWordsOriginal;
   private String[] itemWordsTranslation;


   // ListView MarkerText
   private String markerTextOriginal;
   private String markerTextTranslation;

   // AudiClip
   private Map<String, AudioClip> audioClips;
   private boolean exitLoop;
   private int currentPauseItem; // the actuan item when click the original pause 

   // Actual getDirectory
   // private String path;

   // initial file if exits
   private String initFileIfExits;

   // slider media, to control if the moviment of the media comes 
   // from the slider or the media
   private String mediaPlayerSlider;

   // if I´m stopping from item original or media
   private boolean originalButton;

   // From original item to pause / play
   private Duration currentOriginal;

   // An Array with the image of the buttons
   private ImageView[] imageViews;

   // For relative Direcction 
   String se;
   String path;

   // For KeyCode
   final KeyCombination kcLeft = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN);
   final KeyCombination kcRight = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN);

// Class instances
   private SelectedFile sf;
   private GetJson gj;
   private FillListView slw;
   private Directory dir;
   private AudioClips ac;
   private SaveWordsAsList swal;
   private FormatTime ft;

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Initialize">
   /**
    * When the method is initialize
    */
   public void initialize ()
   {
      try {

         // Instances
         sf = new SelectedFile();
         gj = new GetJson();
         slw = new FillListView();
         dir = new Directory();
         ac = new AudioClips();
         swal = new SaveWordsAsList();
         ft = new FormatTime();

         path = System.getProperty("user.dir");
         //se = System.getProperty("file.separator");
         se = "/";
         // References to mainScene
         mainStage = MainScene.getMainStage();

         // if it playing an item, doesn´t scroll
         playButtonBoolean = true;

         // Setting the sliders of the Volumen, balance, rate
         setSlider();
         // SliderMedia is in ScroolBar and it´s initialized in handleOpenMenu2

         // Settion the images of the butttons
         setImageButton();


         node = new Node[]{
            tabPanelListViewH, listViewH01Reading,
            playButtonReading, backButtonReading, forwardButtonReading, stopButtonReading,
            mediaSliderReading,
            playButtonItemOriginalReading, backButtonItemOriginalReading,
            forwardButtonItemOriginalReading, stopButtonItemOriginalReading,
            playButtonItemMachineReading, stopButtonItemMachineReading,
            rateSliderReading, balanceSliderReading, volumeSliderReading,
            textFieldWriting,
            playButtonWriting, backButtonWriting, forwardButtonWriting, stopButtonWriting,
            mediaSliderWriting,
            playButtonItemOriginalWriting, backButtonItemOriginalWriting,
            forwardButtonItemOriginalWriting, stopButtonItemOriginalWriting,
            correctionButtonWriting,
            rateSliderWriting, balanceSliderWriting, volumeSliderWriting,
            textFieldTranslation,
            playButtonTranslation, backButtonTranslation, forwardButtonTranslation, stopButtonTranslation,
            mediaSliderTranslation,
            playButtonItemOriginalTranslation, backButtonItemOriginalTranslation,
            forwardButtonItemOriginalTranslation, stopButtonItemOriginalTranslation,
            correctionButtonTranslation,
            rateSliderTranslation, balanceSliderTranslation, volumeSliderTranslation
         };


         // Setting the listViewH and textField invisible or disabled
         listViewH01Reading.setVisible(true);
         textFieldWriting.setVisible(false);
         textFieldTranslation.setVisible(false);

         // Setting the transversal focus of the nodes
         setTransversalFocus();

         // The initial tab
         currentTab = "Leer";

         // Setting the current node
         currentNode = listViewV;
         oldNode = listViewH01Reading;
         listViewV.requestFocus();
         // Settiong the intial border
         setBorder(listViewV);

         // Setting the tooltips      
         setTooltips();

         // Setting if the stop is from original button or media (true id original)
         originalButton = false;

         // The index of the listviewV when click in the pause Button
         currentPauseItem = 0;

         // Check if there´s an initial file
         initFileIfExits = dir.init();
         if (initFileIfExits != null && initFileIfExits.contains("mp4")) {

            File file = new File(initFileIfExits.replace("mp4", "json"));

            mediaView.setPreserveRatio​(true);

            handleOpenMenu2(file);

         }


      } catch (Exception e) {
         //message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "\nMainController / initialize()", e);
      }

   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Menu + inicio">

   /**
    * Open a SelectFile and seek a json to load the phrases (Part 1)
    */
   @FXML public void handleOpenMenu ()
   {

      // Open a new fileChooser, set the stage where it´s shows,return un File
      // If exits an last directory, it´ll open it
      File file = sf.getSelectedFile(mainStage, dir.getLastDirectory());
      handleOpenMenu2(file);

   }

   /**
    * pen a SelectFile and seek a json to load the phrases (Part2)
    *
    * @param file A File, readed by a filechooser or the initial file
    */
   private void handleOpenMenu2 (File file)
   {
      try {

         handleCloseMenu();

         // Read a json in English, send a File JSON, return an array of Item class objects
         itemsOriginal = gj.getJsonOriginal(file);

         // Read a json in Spanish, send a File JSON, return an array of 
         //Item class objects
         itemsTranslation = gj.getJsonTranslation(file);

         // fill a ListView with the phrases of the Items array
         String titleMp4 = slw.setListView(listViewV, itemsOriginal);

         // Setting the last directory
         dir.setLastDirectory(file.getParent());

         // Setting the last File
         dir.setLastFile(titleMp4);

         // Creating the path to the media
         mediaStringUrl = new File(dir.getLastDirectory() + "/" + dir.getLastFile()).toURI();

         // Creating a list of words of the media
         wordSet = swal.saveWordsAsList(itemsOriginal, titleMp4, file.getParent() + "/");

         // Set the MediaPlayer
         setMediaPlayer();

         // Setting audiclips
         audioClips = ac.setAudioClip(wordSet);
         ac.setAudioClipRateSlider(rateSliderReading);
         ac.setAudioClipBalanceSlider(balanceSliderReading);
         ac.setAudioClipVolumeSlider(volumeSliderReading);

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

         // Setting the first item
         indexItemV = 0;
         listViewV.scrollTo(-0);
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
         //message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainController / handleOpenMenu2()", e);
      }

   }

   /**
    * When click on the close menu
    */
   @FXML private void handleCloseMenu ()
   {
      try {
         if (mediaPlayer == null) {
            return;
         }
         mediaPlayer.stop();
         mediaPlayer.dispose();

         audioClips = null;

         ObservableList<String> list = FXCollections.observableArrayList();
         list.addAll("");
         listViewV.setItems(list);
         listViewH01Reading.setItems(list);
         listViewH02Reading.setItems(list);

         handleStopButton();
         setChangedSize();

      } catch (Exception e) {
         // message(Alert.AlertType.ERROR, "Error message", e.getMessage(),"MainController / handleMenuClose()", e);
      }
   }


   /**
    * handle of the "About message"
    */
   @FXML private void handleAboutMenu ()
   {
      message(Alert.AlertType.CONFIRMATION, "LanguageApp", "Sobre esta aplicación:", "Autor: Roberto Garrido Trillo", null);
   }


   /**
    * handle of the "About message"
    */
   @FXML private void handleControlesMenu ()
   {
      message(Alert.AlertType.INFORMATION, "LanguageApp", "Ayuda",
              "Controles: \n\n" +
              "Cursores:  para desplazarte por los diferentes " +
              "elementos.\n\n" +
              "Barra espaciadora / Enter:  para activar los " +
              "elementos.\n\n" +
              "Control + Derecha / Izquierda: para mover los slider de volumen" +
              ", Balance, velocidad y control de la película.\n\n" +
              "Control o Shift: para seleccionar mas de una palabra al " +
              "reproducirla.\n\n", null);
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Play, stop, pause Button...">

   /**
    * Setting the image int the buttons
    */
   private void setImageButton ()
   {
      try {
         imageViews = new ImageView[34];
         String[] ruta = {
            "play.png", "pause.png",
            "back.png", "forward.png",
            "stop.png",
            "play.png", "pause.png",
            "back.png", "forward.png",
            "stop.png",
            "play.png", "stop.png",
            "play.png", "pause.png",
            "back.png", "forward.png",
            "stop.png",
            "play.png", "pause.png",
            "back.png", "forward.png",
            "stop.png",
            "comprobar.png",
            "play.png", "pause.png",
            "back.png", "forward.png",
            "stop.png",
            "play.png", "pause.png",
            "back.png", "forward.png",
            "stop.png",
            "comprobar.png"};

         for (int i = 0; i < 34; i++) {
            Image image = new Image(getClass().getResource("/LanguageApp/resources/images/" + ruta[i]).toExternalForm());
            imageViews[i] = new ImageView(image);
            imageViews[i].setFitWidth(32);
            imageViews[i].setFitHeight(32);
         }

         playButtonReading.setGraphic(imageViews[0]);

         backButtonReading.setGraphic(imageViews[2]);
         forwardButtonReading.setGraphic(imageViews[3]);
         stopButtonReading.setGraphic(imageViews[4]);
         playButtonItemOriginalReading.setGraphic(imageViews[5]);

         backButtonItemOriginalReading.setGraphic(imageViews[7]);
         forwardButtonItemOriginalReading.setGraphic(imageViews[8]);
         stopButtonItemOriginalReading.setGraphic(imageViews[9]);
         playButtonItemMachineReading.setGraphic(imageViews[10]);
         stopButtonItemMachineReading.setGraphic(imageViews[11]);

         playButtonWriting.setGraphic(imageViews[12]);

         backButtonWriting.setGraphic(imageViews[14]);
         forwardButtonWriting.setGraphic(imageViews[15]);
         stopButtonWriting.setGraphic(imageViews[16]);
         playButtonItemOriginalWriting.setGraphic(imageViews[17]);

         backButtonItemOriginalWriting.setGraphic(imageViews[19]);
         forwardButtonItemOriginalWriting.setGraphic(imageViews[20]);
         stopButtonItemOriginalWriting.setGraphic(imageViews[21]);
         correctionButtonWriting.setGraphic(imageViews[22]);

         playButtonTranslation.setGraphic(imageViews[23]);

         backButtonTranslation.setGraphic(imageViews[25]);
         forwardButtonTranslation.setGraphic(imageViews[26]);
         stopButtonTranslation.setGraphic(imageViews[27]);
         playButtonItemOriginalTranslation.setGraphic(imageViews[28]);

         backButtonItemOriginalTranslation.setGraphic(imageViews[30]);
         forwardButtonItemOriginalTranslation.setGraphic(imageViews[31]);
         stopButtonItemOriginalTranslation.setGraphic(imageViews[32]);
         correctionButtonTranslation.setGraphic(imageViews[33]);
      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "\nMainController / setImageButton()", e);
      }

   }

   /**
    * Controller for slider media play button
    */
   @FXML public void handlePlayButton ()
   {

      Status st = mediaPlayer.getStatus();

      mediaSliderReading.setDisable(false);
      mediaSliderWriting.setDisable(false);
      mediaSliderTranslation.setDisable(false);

      // if "in pause"  drag the slidermedia it doesn´t detect the end of file
      if (mediaPlayer.getCurrentTime().equals(mediaPlayer.getStopTime())) {
         handleStopButton();
         mediaPlayer.play();
         playedImageButtonOriginal();
         playButtonReading.setGraphic(imageViews[0]);
         playButtonWriting.setGraphic(imageViews[12]);
         playButtonTranslation.setGraphic(imageViews[23]);
         return;
      }

      // if I push the normal play in "normal mode" (It plays of normal mode)
      try {
         if (st == Status.STOPPED && !originalButton) {

            //handleStopButton();
            indexItemV = listViewV.getSelectionModel().getSelectedIndex();
            if (indexItemV != 0) {
               start = itemsOriginal[indexItemV].getStart();

               mediaPlayer.setStartTime(Duration.seconds(start));
               mediaPlayer.setStopTime(duration);
            }
            mediaPlayer.stop();
            mediaPlayer.play();
            playButtonReading.setGraphic(imageViews[1]);
            playButtonWriting.setGraphic(imageViews[13]);
            playButtonTranslation.setGraphic(imageViews[24]);
            return;
         }

         // if I push the play button in "pause mode" (it pauses the media)   
         if (st == Status.PAUSED && !originalButton) {

            // Re-start the media
            mediaPlayer.setStartTime(mediaPlayer.getCurrentTime());
            mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());

            // Stoping the original pause button
            originalButton = false;

            mediaPlayer.play();
            playButtonReading.setGraphic(imageViews[1]);
            playButtonWriting.setGraphic(imageViews[13]);
            playButtonTranslation.setGraphic(imageViews[24]);
            return;
         }

         // if I push the play button in "original mode" (it continues 
         // until the end)                    
         if ((st == Status.PAUSED || st == Status.STOPPED) && originalButton) {

            mediaPlayer.setStartTime(mediaPlayer.getCurrentTime());
            mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());

            // Stoping the original pause button
            originalButton = false;
            // Enable the scroll to markers
            playButtonBoolean = true;

            mediaPlayer.play();
            playButtonReading.setGraphic(imageViews[1]);
            playButtonWriting.setGraphic(imageViews[13]);
            playButtonTranslation.setGraphic(imageViews[24]);
            return;
         }

         if (st == Status.PLAYING && !originalButton) {
            mediaPlayer.pause();
            playButtonReading.setGraphic(imageViews[0]);
            playButtonWriting.setGraphic(imageViews[12]);
            playButtonTranslation.setGraphic(imageViews[23]);
         }


      } catch (Exception e) {
         //message(Alert.AlertType.ERROR, "Error message", e.getMessage(),     "MainController / handlePlayButton()", e);      
      }
   }

   /**
    * Controller for the stop button
    */
   @FXML public void handleStopButton ()
   {

      try {

         if (!originalButton) {
            mediaPlayer.stop();
            mediaPlayer.setStartTime(Duration.ZERO);
            mediaPlayer.setStopTime(mediaPlayer.getMedia().getDuration());
            mediaPlayer.seek(Duration.ZERO);

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
            playedImageButtonOriginal();
            playButtonReading.setGraphic(imageViews[0]);
            playButtonWriting.setGraphic(imageViews[12]);
            playButtonTranslation.setGraphic(imageViews[23]);
         }
      } catch (Exception e) {
         //message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainController / handleStopButton()", e);
      }
   }

   /**
    * Handle of the Play Button Item
    */
   @FXML private void handlePlayButtonItemOriginal ()
   {

      Status status = mediaPlayer.getStatus();

      mediaSliderReading.setDisable(true);
      mediaSliderWriting.setDisable(true);
      mediaSliderTranslation.setDisable(true);

      indexItemV = listViewV.getSelectionModel().getSelectedIndex();

      start = itemsOriginal[indexItemV].getStart();
      end = itemsOriginal[indexItemV].getEnd();
      mediaPlayer.setStartTime(Duration.seconds(start));
      mediaPlayer.setStopTime(Duration.seconds(end));
      if (!originalButton) {
         Duration.seconds(start);
      }

      // it´s used for don´t scroll when it´s playing an item
      playButtonBoolean = false;

      // Stoping the media pause button
      originalButton = true;

      if (currentPauseItem == indexItemV) {
         if (status.equals(Status.PAUSED)) {
            pausedImagegButtonOriginal();
            mediaPlayer.setStartTime(currentOriginal);
            mediaPlayer.seek(currentOriginal);
            mediaPlayer.setStopTime(Duration.seconds(end));
            mediaPlayer.play();
         } else if (status.equals(Status.PLAYING)) {
            playedImageButtonOriginal();
            currentOriginal = mediaPlayer.getCurrentTime();
            mediaPlayer.seek(currentOriginal);
            mediaPlayer.pause();
            currentOriginal = mediaPlayer.getCurrentTime();
         } else if (status.equals(Status.STOPPED)) {
            pausedImagegButtonOriginal();
            mediaPlayer.play();
         }

      } else {
         // Setting the old value like the new value
         currentPauseItem = indexItemV;
         pausedImagegButtonOriginal();
         indexItemV = listViewV.getSelectionModel().getSelectedIndex();
         currentOriginal = new Duration(itemsOriginal[indexItemV].getStart());
         mediaPlayer.play();

      }
   }

   /**
    * Change the image of the play / pause button
    */
   private void playedImageButtonOriginal ()
   {
      playButtonItemOriginalReading.setGraphic(imageViews[5]);
      playButtonItemOriginalWriting.setGraphic(imageViews[17]);
      playButtonItemOriginalTranslation.setGraphic(imageViews[28]);
      playButtonReading.setGraphic(imageViews[0]);
      playButtonWriting.setGraphic(imageViews[12]);
      playButtonTranslation.setGraphic(imageViews[23]);
   }

   /**
    * Change the image of the play / pause button
    */
   private void pausedImagegButtonOriginal ()
   {
      playButtonItemOriginalReading.setGraphic(imageViews[6]);
      playButtonItemOriginalWriting.setGraphic(imageViews[18]);
      playButtonItemOriginalTranslation.setGraphic(imageViews[29]);
      playButtonReading.setGraphic(imageViews[0]);
      playButtonWriting.setGraphic(imageViews[12]);
      playButtonTranslation.setGraphic(imageViews[23]);
   }

   /**
    * Handle of the Button Stop Item Original
    */
   @FXML private void handleStopButtonItemOriginal ()
   {

      if (originalButton) {

         mediaPlayer.stop();
         // Change the image buttons to play
         playedImageButtonOriginal();
         playButtonReading.setGraphic(imageViews[0]);
         playButtonWriting.setGraphic(imageViews[12]);
         playButtonTranslation.setGraphic(imageViews[23]);
      }
   }

   /**
    * Handle of the Button Play Item Machine
    */
   @FXML private void handlePlayButtonItemMachine ()
   {

      // Create an Observablelist to listview H    
      ObservableList olItems = listViewH01Reading.getSelectionModel().
              getSelectedItems();

      if (olItems.size() > 0) {
         // Size of the list
         int size = olItems.size();
         int cont = 0;

         // Create an array of string whera I´ll put the texts of the listview H
         String[] text = new String[size];

         for (Object item : olItems) {
            text[cont] = item.toString();
            cont++;
         }

         // Play the audioclips one behind the other
         audioClips.get(text[0]).play();

         if (size > 1) {
            Thread audio = new Thread(new Runnable() {
               @Override
               public void run ()
               {

                  exitLoop = false;

                  for (int i = 1; i < size; i++) {

                     if (exitLoop) {
                        break;
                     }

                     while (audioClips.get(text[i - 1]).
                             isPlaying()) {
                     }
                     audioClips.get(text[i]).play();
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
   @FXML private void handleStopButtonItemMachine ()
   {
      exitLoop = true;
   }

   /**
    * handle of the
    */
   @FXML private void handleBackButton ()
   {
      if (indexItemV > 0) {
         // Back the index
         indexItemV--;

         // index of listview to zero
         listViewV.scrollTo(indexItemV);
         listViewV.getSelectionModel().select(indexItemV);
         backForward();
      }
   }

   /**
    * handle of the
    */
   @FXML private void handleForwardButton ()
   {
      if (indexItemV < itemsOriginal.length) {
         // forward the index
         indexItemV++;

         // index of listview to zero
         listViewV.scrollTo(indexItemV);
         listViewV.getSelectionModel().select(indexItemV);
         backForward();
      }
   }

   /**
    * This method complements the back and forward button
    */
   private void backForward ()
   {
      start = itemsOriginal[indexItemV].getStart();
      end = itemsOriginal[indexItemV].getEnd();
      mediaPlayer.setStartTime(Duration.seconds(start));
      mediaPlayer.setStopTime(Duration.seconds(end));

      // putting in listviewH the phases
      showListViewH();


      // Label time to cero
      duration = mediaPlayer.getMedia().getDuration();
      timeLabelReading.setText(ft.
              formatting(Duration.seconds(start), duration));

      // Enable the scroll to markers
      playButtonBoolean = true;

      // Stoping the original pause button
      originalButton = false;

      // The first lap of the slider
      mediaPlayerSlider = "media"; // to the first lap

      // Change the image buttons to play
      playedImageButtonOriginal();
      playButtonReading.setGraphic(imageViews[0]);
      playButtonWriting.setGraphic(imageViews[12]);
      playButtonTranslation.setGraphic(imageViews[23]);

      mediaPlayer.stop();

   }

   /**
    * handle of the
    */
   @FXML private void handleBackButtonItemOriginal ()
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
   @FXML private void handleForwardPlayButtonItemOriginal ()
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
   @FXML private void handlecorrectionButtonWriting ()
   {

      handleCorrectionButton(textFieldWriting);
   }

   /**
    * Setting the correction Button Translation
    */
   @FXML private void handlecorrectionButtonTranslation ()
   {

      handleCorrectionButton(textFieldTranslation);
   }

   /**
    * handle of the
    */
   private void handleCorrectionButton (TextField tf)
   {

      try {
         indexItemV = listViewV.getSelectionModel().getSelectedIndex();
         markerTextOriginal = itemsOriginal[indexItemV].getText();
         markerTextTranslation = itemsTranslation[indexItemV].getText();

         // Extract the orignial text and the answer
         if (currentTab.equals("Escribir")) {
            itemWordsOriginal = swal.cleanWords(markerTextOriginal);
         } else if (currentTab.equals("Traducir") || currentTab.equals("Abajo")) {
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

         if (currentTab.equals("Escribir")) {
            listViewH02Writing.setVisible(true);
            listViewH02Writing.setItems(listItemOriginal);
         } else if (currentTab.equals("Traducir") || currentTab.equals("Abajo")) {
            listViewH02Translation.setVisible(true);
            listViewH02Translation.setItems(listItemOriginal);
         }

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
         if (currentTab.equals("Escribir")) {
            indicatorSuccessWriting.setProgress(success);
         } else if (currentTab.equals("Traducir") || currentTab.equals("Abajo")) {
            indicatorSuccessTranslation.setProgress(success);
         }
      } catch (Exception e) {
         // message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainController / handleCorrectionButton()", e);
      }

   }
//</editor-fold>           

//<editor-fold defaultstate="collapsed" desc="setting Sliders Volume, Rate, Balance">
   /**
    * handler for sliders
    */
   public void setSlider ()
   {
      // Slider tab Reading
      setSliderForm(volumeSliderReading, 0.0, 100.0, 20.0, 20,
              2);
      volumeLabelReading.setText("20,00"); // initial value
      setSliderForm(rateSliderReading, 0.5, 2.0, 1.0, 0.5,
              7);
      rateLabelReading.setText("1,00"); // initial value
      setSliderForm(balanceSliderReading, -1.0, 1.0, 0.0, 1,
              10);
      balanceLabelReading.setText("0,00"); // initial value

      // Slider tab Writing
      setSliderForm(volumeSliderWriting, 0.0, 100.0, 20.0, 20,
              2);
      volumeLabelWriting.setText("20,00"); // initial value
      setSliderForm(rateSliderWriting, 0.5, 2.0, 1.0, 0.5,
              7);
      rateLabelWriting.setText("1,00"); // initial value
      setSliderForm(balanceSliderWriting, -1.0, 1.0, 0.0, 1,
              10);
      balanceLabelWriting.setText("0,00"); // initial value   

      // Slider tab Translation
      setSliderForm(volumeSliderTranslation, 0.0, 100.0, 20.0, 20,
              2);
      volumeLabelTranslation.setText("20,00"); // initial value
      setSliderForm(rateSliderTranslation, 0.5, 2.0, 1.0, 0.5,
              7);
      rateLabelTranslation.setText("1,00"); // initial value
      setSliderForm(balanceSliderTranslation, -1.0, 1.0, 0.0, 1,
              10);
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
   private void setSliderForm (Slider slider, Double min, Double max,
           Double initial,
           double majorTick, int minorTick)
   {
      // min, max and initial value
      slider.setMin(min);
      slider.setMax(max);
      slider.setValue(initial);

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
            //message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainController / setSliderForm()", e);
         }
      });
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting the media">

   /**
    *
    * Setting the media
    *
    */
   public void setMediaPlayer ()
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

         // setting the playback errors
         setError();

         // Setting the end time of the file and fixing the problem with the 
         // freezing media
         currentTime = Duration.ZERO;
         setEndTimefile();

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainController / setMediaPlayer()", e);
      }
   }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting the listView">
   /**
    * Set the Handler event for the listview V
    */
   private void setListViewV ()
   {
      try {

         // Enable the selection mode
         listViewV.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

         //For vertical ListView this is the height, for a horizontal ListView this is the width.  
         listViewV.setFixedCellSize(34);

         // Enable the listeners
         listViewV.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle (MouseEvent event)
            {
               setBorder(listViewV);
               indexItemV = listViewV.getSelectionModel().getSelectedIndex();
               showListViewH();

               // if I do double click
               if (event.getClickCount() == 2) {
                  handlePlayButtonItemOriginal();
               }
            }
         });
      } catch (Exception e) {
         e.printStackTrace();
      }
   }


   /**
    * Put the word of item in listView H
    */
   private void showListViewH ()
   {

      try {
         // Get the index of the clicked item
         indexItemV = listViewV.getSelectionModel().getSelectedIndex();
         markerTextOriginal = itemsOriginal[indexItemV].getText();
         markerTextTranslation = itemsTranslation[indexItemV].getText();

         // Cleanig the phrase
         itemWordsOriginal = swal.cleanWords(markerTextOriginal);
         itemWordsTranslation = swal.cleanWords(markerTextTranslation);


         // Filling listViewH01Reading with words
         ObservableList<String> listItemOriginal = FXCollections.
                 observableArrayList();
         ObservableList<String> listItemTranslation = FXCollections.
                 observableArrayList();

         // Filling the list
         listItemOriginal.addAll(Arrays.asList(itemWordsOriginal));
         listItemTranslation.addAll(Arrays.asList(itemWordsTranslation));

         listViewH01Reading.setItems(listItemOriginal);
         listViewH02Reading.setItems(listItemTranslation);

         listViewH01Reading.getSelectionModel().select(0);


      } catch (Exception e) {
        /* message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "MainController / showListViewH()", e);*/

      }
   }


   /**
    * Set the Handler event for the listview H
    */
   private void setListViewH ()
   {

      // Enable the selection mode
      listViewH01Reading.getSelectionModel().setSelectionMode(
              SelectionMode.MULTIPLE);

      // Enable the listeners
      listViewH01Reading.setOnMouseClicked(new EventHandler<MouseEvent>() {

         @Override
         public void handle (MouseEvent event)
         {

            // if I do double click
            if (event.getClickCount() == 2) {
               handlePlayButtonItemMachine();
            }
            setBorder(listViewH01Reading);
            currentTab = "Leer";
         }


      });
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting the Time and Event marker">

   /**
    * Setting the markers in the media
    *
    * @param items An object of type item
    */
   public void setMarkers (Item[] items)
   {
      ObservableMap<String, Duration> markers = media.getMarkers();

      for (int i = 0; i < items.length - 1; i++) {
         String id = String.valueOf(items[i].getId());
         double milis = items[i].getStart();
         markers.put(id, Duration.seconds(milis));
      }
   }


   /**
    *
    * Add a marker event handler
    */
   public void setEventMarker ()
   {

      mediaPlayer.setOnMarker(new EventHandler<MediaMarkerEvent>() {

         @Override
         public void handle (MediaMarkerEvent event)
         {

            try {

               if (!playButtonBoolean) {
                  return;
               }
               // Calling scrolling to read marker
               String markerText = setScrollingMarket(event);

               showListViewH();
            } catch (Exception e) {
               message(Alert.AlertType.ERROR, "Error message", e.
                       getMessage(), "MainController / setEventMarker()", e);
            }
         }
      });
   }


   /**
    * method that scroll the listViewV
    */
   private String setScrollingMarket (MediaMarkerEvent event)
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

      // Setting the global index of the item
      indexItemV = indexSelected;

      return markerText;
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Executing Playback errors">

   /**
    * Catch the playback errors
    */
   private void setError ()
   {

      // Create Handlers for handling Errors
      mediaPlayer.setOnError(new Runnable() {

         @Override
         public void run ()
         {
            // Handle asynchronous error in Player object.
            message(Alert.AlertType.ERROR, "Error message", mediaPlayer.
                    getError().toString(),
                    "MainController / mediaPlayer.setOnError()", null);
         }


      });

      media.setOnError(new Runnable() {

         @Override
         public void run ()
         {
            // Handle asynchronous error in Media object.
            message(Alert.AlertType.ERROR, "Error message",
                    media.getError().toString(),
                    "MainController / media.setOnError()", null);
         }


      });

      //Collect the playback errors
      mediaView.setOnError(new EventHandler<MediaErrorEvent>() {

         @Override
         public void handle (MediaErrorEvent event)
         {
            // Handle asynchronous error in MediaView.
            message(Alert.AlertType.ERROR, "Error message", event.
                    getMediaError().toString(),
                    "MainController / mediaView.setOnError()", null);
         }


      });

   }
//</editor-fold>    

//<editor-fold defaultstate="collapsed" desc="Executing MediaPlayerChangeListener">

   /**
    * Set the events of the media
    */
   private void setMediaPlayerChangeListener ()
   {
      try {
         // Add a ChangeListener to the mediaPlayer
         mediaPlayer.statusProperty().addListener(
                 new ChangeListener<MediaPlayer.Status>() {


            @Override
            public void changed (
                    ObservableValue<? extends MediaPlayer.Status> ov,
                    final MediaPlayer.Status oldStatus,
                    final MediaPlayer.Status newStatus)
            {

               /* System.out.println("Status changed from " + oldStatus +
                    " to " + newStatus); */
            }
         });

         // Add a Handler for PLAYING status
         mediaPlayer.setOnPlaying(new Runnable() {

            @Override
            public void run ()
            {
               //System.out.println("Playing now");
            }


         });

         // Add a Handler for STOPPED status
         mediaPlayer.setOnPaused(new Runnable() {

            @Override
            public void run ()
            {
               // System.out.println("Paused now");
               //mediaPlayer.pause();

            }


         });

         // Add a Handler for STOPPED status
         mediaPlayer.setOnStopped(new Runnable() {

            @Override
            public void run ()
            {
               // System.out.println("Stopped now");
               // mediaPlayer.stop();

            }


         });
         // Add a Handler for END status
         mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run ()
            {
               // reset
               if (originalButton) {
                  mediaSliderReading.setDisable(false);
                  mediaSliderWriting.setDisable(false);
                  mediaSliderTranslation.setDisable(false);
                  mediaPlayer.stop();
                  start = itemsOriginal[indexItemV].getStart();
                  end = itemsOriginal[indexItemV].getEnd();
                  mediaPlayer.setStartTime(Duration.seconds(start));
                  mediaPlayer.setStopTime(Duration.seconds(end));
                  currentOriginal = Duration.seconds(start);
                  mediaPlayer.seek(currentOriginal);

                  Status status = mediaPlayer.getStatus();
                  if (status.equals(Status.PAUSED) || status.equals(
                          Status.PLAYING)) {
                     playButtonItemOriginalReading.setGraphic(
                             imageViews[5]);
                     playButtonItemOriginalWriting.setGraphic(
                             imageViews[17]);
                     playButtonItemOriginalTranslation.setGraphic(
                             imageViews[28]);
                  } else {
                     playButtonItemOriginalReading.setGraphic(
                             imageViews[6]);
                     playButtonItemOriginalWriting.setGraphic(
                             imageViews[18]);
                     playButtonItemOriginalTranslation.setGraphic(
                             imageViews[29]);
                     if (originalButton) {
                        currentOriginal = mediaPlayer.getCurrentTime();
                        mediaPlayer.seek(currentOriginal);
                        mediaPlayer.pause();
                        return;
                     }
                  }

                  return;
               }
               handleStopButton();


            }
         });
      } catch (Exception e) {

         new MainController().message(Alert.AlertType.ERROR, "Error message",
                 e.getMessage(),
                 "MainController.java / setMediaPlayerChangeListener()", e);

      }
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
   public void message (Alert.AlertType alertType, String title, String about, String contextText, Exception ex)
   {

      Alert alert = new Alert(alertType);
      alert.setTitle(title);
      alert.getDialogPane().setMinWidth(600);
      alert.getDialogPane().setMinHeight(480);
      alert.getDialogPane().setPrefWidth(600);
      alert.getDialogPane().setPrefHeight(480);
      alert.setResizable(true);
      alert.setHeaderText(about);
      alert.getDialogPane().setContentText(contextText);

      if (ex != null) {
         // Create expandable Exception.
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         ex.printStackTrace(pw);
         String exceptionText = sw.toString();

         Label label = new Label("The exception stacktrace was:");

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

      alert.getDialogPane().getStylesheets().add(getClass().getResource("/LanguageApp/style/style.css").toExternalForm());
      alert.getDialogPane().getStyleClass().add("style");

      Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
      Image icon = new Image(getClass().getResourceAsStream("/LanguageApp/resources/images/languages_128.png"));
      stage.getIcons().add(icon);


      alert.showAndWait();
   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Scroolbar">

   /**
    * Setting the slider that control the media
    *
    * @param sliderMedia The objet slider
    */
   private void setSliderMedia (Slider sliderMedia)
   {


      //Set the media to change the mediaSliderReading when it´s playing
      mediaPlayer.currentTimeProperty().addListener(
              new ChangeListener<Duration>() {
         @Override
         public void changed (ObservableValue<? extends Duration> ov,
                 Duration t, Duration t1)
         {
            if (mediaPlayerSlider.equals("media")) {
               updateValues(mediaPlayerSlider);
            }
         }
      });

      // Set the slider to change the media
      sliderMedia.valueProperty().addListener(new ChangeListener<Number>() {
         @Override
         public void changed (ObservableValue<? extends Number> ov,
                 Number oldValue,
                 Number newValue)
         {

            // If there´s some change in the slidermedia updateValues
            if (duration != null && mediaPlayerSlider.equals("slider")) {
               updateValues(mediaPlayerSlider);
            }
         }
      });

      // set the slider blocking the change media when click on the slider
      sliderMedia.setOnMouseClicked((MouseEvent) -> {
         mediaPlayerSlider = "media";
         sliderMedia.requestFocus();
         setBorder(sliderMedia);
      });

      // Setting the slider when pressing on it
      sliderMedia.setOnMousePressed(new EventHandler<MouseEvent>() {
         @Override
         public void handle (MouseEvent t)
         {
            mediaPlayerSlider = "slider";
            updateValues(mediaPlayerSlider);
         }
      });

      // Setting the slider when I release it.
      sliderMedia.setOnMouseReleased(new EventHandler<MouseEvent>() {
         @Override
         public void handle (MouseEvent t)
         {
            mediaPlayerSlider = " media";
            updateValues(mediaPlayerSlider);
         }
      });
   }

   /**
    * updateValues
    *
    * @param mediaPlayerSlider The slider that control the media
    */
   public void updateValues (String mediaPlayerSlider)
   {

      if (timeLabelReading != null && mediaSliderReading != null &&
              duration != null) {
         Platform.runLater(new Runnable() {
            @Override
            public void run ()
            {
               // Extract the current time of the mediaplayer
               currentTime = mediaPlayer.getCurrentTime();


               // if origin is media I move the slider
               if (mediaPlayerSlider.equals("media")) {

                  double totalDuration = duration.toMillis();
                  double positionMedia = currentTime.toMillis();
                  double finalDouble = ((positionMedia * 100) /
                          totalDuration);

                  mediaSliderReading.setValue(finalDouble);
               }

               // if origin is slider i move the media
               if (mediaPlayerSlider.equals("slider")) {

                  // if duration is unknown don´t execute it
                  mediaSliderReading.setDisable(duration.isUnknown());
                  mediaSliderWriting.setDisable(duration.isUnknown());
                  mediaSliderTranslation.setDisable(duration.isUnknown());
                  try {
                     if (!mediaSliderReading.isDisabled() && duration.
                             greaterThan(Duration.ZERO)) {

                        double positionSlider = mediaSliderReading.
                                getValue();
                        double totalDuration = duration.toMillis();
                        double positionMedia = (positionSlider *
                                totalDuration) / 100;
                        Duration finalDuration = new Duration(
                                positionMedia);
                        mediaPlayer.seek(finalDuration);
                     }
                  } catch (Exception e) {
                     new MainController().message(Alert.AlertType.ERROR,
                             "Error message",
                             e.getMessage(),
                             "MainController.java / updateValues()", e);

                  }
               }
               timeLabelReading.setText(ft.
                       formatting(currentTime, duration));

            }
         });
      }
   }

   /**
    * Setting the end time of the file
    */
   private void setEndTimefile ()
   {
      mediaPlayer.setOnReady(new Runnable() {

         @Override
         public void run ()
         {

            currentTime = Duration.ZERO;
            duration = mediaPlayer.getMedia().getDuration();
            timeLabelReading.setText(ft.formatting(currentTime, duration));

            mediaPlayer.play();
            mediaPlayer.stop();

            setChangedSize();

            anchorMedia.setPrefHeight(media.getHeight());
            anchorMedia.setMinHeight(media.getHeight());

            mediaView.setFitHeight(media.getHeight());
         }
      });
   }

   /**
    * Change the size of the elements when close the media
    */
   private void setChangedSize ()
   {
      // change the mainStage´s height depending of the media´s height
      int height = 760 - (480 - media.getHeight());
      mainStage.setHeight(height);
      height -= 67;
      listViewV.setMinHeight(height);
      listViewV.setMaxHeight(height);
      listViewV.setPrefHeight(height);
   }

   /**
    * Setting the values of the mediaSliderReading
    *
    * @param max An Double (0 - 100) representing the time end of the item
    */
   private void setTicksSliderMedia (Slider sliderMedia, double max)
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
   private void setBinding ()
   {
      mediaSliderReading.valueProperty().bindBidirectional(mediaSliderWriting.
              valueProperty());
      mediaSliderWriting.valueProperty().bindBidirectional(
              mediaSliderTranslation.valueProperty());

      binding(rateSliderReading, rateSliderWriting, rateLabelReading,
              rateLabelWriting);
      binding(rateSliderWriting, rateSliderTranslation, rateLabelReading,
              rateLabelTranslation);
      binding(balanceSliderReading, balanceSliderWriting, balanceLabelReading,
              balanceLabelWriting);
      binding(balanceSliderWriting, balanceSliderTranslation,
              balanceLabelWriting, balanceLabelTranslation);
      binding(volumeSliderReading, volumeSliderWriting, volumeLabelReading,
              volumeLabelWriting);
      binding(volumeSliderWriting, volumeSliderTranslation, volumeLabelWriting,
              volumeLabelTranslation);
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
   private void binding (Slider s1, Slider s2, Label l1, Label l2)
   {
      s1.valueProperty().bindBidirectional(s2.valueProperty());
      l1.textProperty().bindBidirectional(l2.textProperty());

   }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="setTransversalFocus ">

   /**
    * Setting the movement between elements
    */
   private void setTransversalFocus ()
   {

//<editor-fold defaultstate="collapsed" desc="listView">

      listViewV.setOnKeyPressed(new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent ke)
         {
            indexItemV = listViewV.getSelectionModel().getSelectedIndex();
            showListViewH();
            System.out.println("event " + ke.getCode());

            switch (ke.getCode()) {

               case LEFT:
                  handleBackButtonItemOriginal();
                  ke.consume();
                  break;

               case RIGHT:
                  if (oldNode.equals(currentNode)) {
                     oldNode = tabPanelListViewH;
                     if (currentTab.equals("Leer")) {
                        currentTab = "Arriba";
                     }
                     if (currentTab.equals("Traducir")) {
                        currentTab = "Abajo";
                     }
                  }
                  oldNode.requestFocus();
                  setBorder(oldNode);
                  ke.consume();
                  break;

               case SPACE:
                  handlePlayButtonItemOriginal();
                  ke.consume();
                  break;
               default:
                  break;
            }
         }
      });

      listViewH01Reading.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent event)
         {

            if (event.getCode().equals(KeyCode.UP)) {
               event.consume();
            }

            if (event.getCode().equals(KeyCode.DOWN)) {

               if (oldNode.equals(anchorListViewV)) {
                  oldNode = tabPanelListViewH;
               }
               currentTab = "Arriba";
               oldNode.requestFocus();
               setBorder(oldNode);
               event.consume();

            }

            if (event.getCode()
                    .equals(KeyCode.LEFT)) {
               if (listViewH01Reading.getSelectionModel().getSelectedIndex() == 0) {
                  listViewV.requestFocus();
                  setBorder(listViewV);
                  event.consume();
               }
            }

            if (event.getCode()
                    .equals(KeyCode.SPACE)) {
               handlePlayButtonItemMachine();
               event.consume();
            }

            if (event.getCode()
                    .equals(KeyCode.ENTER)) {
               handlePlayButtonItemOriginal();
               event.consume();
            }
         }
      }
      );

      // the event onclick is in Setting the listview
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Play Button">
      eventButton(playButtonReading,
              1, 7, 3, 0);
      eventButton(backButtonReading,
              1, 8, 4, 2);
      eventButton(forwardButtonReading,
              1, 9, 5, 3);
      eventButton(stopButtonReading,
              1, 10, 6, 4);
      eventMediaSlider(mediaSliderReading,
              1, 11, -1, 5);
      eventButtonOriginalReading(playButtonItemOriginalReading,
              2, 13, 8, 0);
      eventButton(backButtonItemOriginalReading,
              3, 13, 9, 7);
      eventButton(forwardButtonItemOriginalReading,
              4, 14, 10, 8);
      eventButton(stopButtonItemOriginalReading,
              5, 14, 11, 9);
      eventButton(playButtonItemMachineReading,
              6, 14, 12, 10);
      eventButton(stopButtonItemMachineReading,
              6, 15, -1, 11);
      eventFilterSlider(rateSliderReading,
              0, 14, 0.5, 2, 0.01);
      eventFilterSlider(balanceSliderReading,
              13, 15, -1.0, 1, 0.1);
      eventFilterSlider(volumeSliderReading,
              14, -1, 0.0, 100, 1);

      eventButton(playButtonWriting,
              16, 22, 18, 0);
      eventButton(backButtonWriting,
              16, 23, 19, 17);
      eventButton(forwardButtonWriting,
              16, 24, 20, 18);
      eventButton(stopButtonWriting,
              16, 25, 21, 19);
      eventMediaSlider(mediaSliderWriting,
              16, 26, -1, 20);
      eventButtonOriginalReading(playButtonItemOriginalWriting,
              17, 27, 23, 0);
      eventButton(backButtonItemOriginalWriting,
              18, 27, 24, 22);
      eventButton(forwardButtonItemOriginalWriting,
              19, 28, 25, 23);
      eventButton(stopButtonItemOriginalWriting,
              20, 28, 26, 24);
      eventButton(correctionButtonWriting,
              21, 29, -1, 25);
      eventFilterSlider(rateSliderWriting,
              0, 28, 0.5, 2, 0.01);
      eventFilterSlider(balanceSliderWriting,
              27, 29, -1.0, 1, 0.1);
      eventFilterSlider(volumeSliderWriting,
              28, -1, 0.0, 100, 1);

      eventButton(playButtonTranslation,
              30, 36, 32, 0);
      eventButton(backButtonTranslation,
              30, 37, 33, 31);
      eventButton(forwardButtonTranslation,
              30, 38, 34, 32);
      eventButton(stopButtonTranslation,
              30, 39, 35, 33);
      eventMediaSlider(mediaSliderTranslation,
              30, 40, -1, 34);
      eventButtonOriginalReading(playButtonItemOriginalTranslation,
              31, 41, 37, 0);
      eventButton(backButtonItemOriginalTranslation,
              32, 41, 38, 36);
      eventButton(forwardButtonItemOriginalTranslation,
              33, 42, 39, 37);
      eventButton(stopButtonItemOriginalTranslation,
              34, 42, 40, 38);
      eventButton(correctionButtonTranslation,
              35, 43, -1, 39);
      eventFilterSlider(rateSliderTranslation,
              0, 42, 0.5, 2, 0.01);
      eventFilterSlider(balanceSliderTranslation,
              41, 43, -1.0, 1, 0.1);
      eventFilterSlider(volumeSliderTranslation,
              42, -1, 0.0, 100, 1);

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Stop machine and Correction ">
      stopButtonItemMachineReading.addEventFilter(KeyEvent.KEY_PRESSED,
              (event) -> {
         if (event.getCode().equals(KeyCode.RIGHT)) {
            event.consume();
         }
      }
      );
      correctionButtonWriting.addEventFilter(KeyEvent.KEY_PRESSED,
              (event) -> {
         if (event.getCode().equals(KeyCode.RIGHT)) {
            event.consume();
         }
      }
      );
      correctionButtonTranslation.addEventFilter(KeyEvent.KEY_PRESSED,
              (event) -> {
         if (event.getCode().equals(KeyCode.RIGHT)) {
            event.consume();
         }
      }
      );

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Tab pane">
      tabPanelListViewH.getSelectionModel()
              .selectedItemProperty().addListener(new ChangeListener<Tab>() {
                 @Override
                 public void changed (ObservableValue<? extends Tab> ov, Tab t, Tab t1)
                 {
                    indexItemV = listViewV.getSelectionModel().getSelectedIndex();
                    switch (t1.getText()) {

                       case "Leer":
                          slw.setListView(listViewV, itemsOriginal);
                          setListViewH();
                          break;

                       case "Escribir":
                          slw.setListView(listViewV, itemsTranslation);
                          break;

                       case "Traducir":
                          slw.setListView(listViewV, itemsOriginal);
                          break;

                       default:
                          break;
                    }
                    listViewV.getSelectionModel().select(indexItemV);
                    //listViewV.requestFocus();
                    currentTab = t1.getText();
                    listViewH01Reading.setVisible(false);
                    textFieldWriting.setVisible(false);
                    textFieldTranslation.setVisible(false);
                    listViewH01Reading.setVisible(currentTab.equals("Leer"));
                    textFieldWriting.setVisible(currentTab.equals("Escribir"));
                    textFieldTranslation.setVisible(currentTab.equals("Traducir") || currentTab.equals("Abajo"));

                    if (t.getText().equals("Escribir") && t1.getText().equals("Leer")) {
                       currentTab = "Arriba";
                    }
                    if (t.getText().equals("Escribir") && t1.getText().equals("Traducir")) {
                       currentTab = "Abajo";
                    }

                 }

                 @Override
                 public String toString ()
                 {
                    return super.toString();
                 }

              }
              );

      tabPanelListViewH.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent event
         )
         {

            // An triger
            if (!event.getTarget().equals(tabPanelListViewH)) {
               return;
            }


            if (event.getCode().equals(KeyCode.RIGHT)) {
               event.consume();
               if (currentTab.equals("Leer") || currentTab.equals("Arriba")) {
                  setBorder(playButtonReading);
                  playButtonReading.requestFocus();
               }
               if (currentTab.equals("Escribir")) {
                  setBorder(playButtonWriting);
                  playButtonWriting.requestFocus();
               }
               if (currentTab.equals("Traducir") || currentTab.equals("Abajo")) {
                  setBorder(playButtonTranslation);
                  playButtonTranslation.requestFocus();
               }
            }

            if (event.getCode().equals(KeyCode.LEFT)) {
               event.consume();
               listViewV.requestFocus();
               setBorder(listViewV);
            }

            if (event.getCode().equals(KeyCode.UP) && currentTab.equals("Arriba")) {
               event.consume();
               listViewH01Reading.requestFocus();
               setBorder(listViewH01Reading);
               currentTab = "Leer";
            }

            if (event.getCode().equals(KeyCode.DOWN) && currentTab.equals("Abajo")) {
               event.consume();
            }
         }
      }
      );

      tabPanelListViewH.setOnMouseClicked(
              (MouseEvent) -> {
         if (currentTab.equals("Leer")) {
            currentTab = "Arriba";
         }
         if (currentTab.equals("Traducir")) {
            currentTab = "Abajo";
         }
         setBorder(tabPanelListViewH);
         MouseEvent.consume();
      }
      );
      //</editor-fold>        

//<editor-fold defaultstate="collapsed" desc="TextField">

      textFieldWriting.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent event
         )
         {

            listViewH02Writing.setVisible(false);

            if (event.getCode().equals(KeyCode.ENTER)) {
               listViewH02Writing.setVisible(true);
               handleCorrectionButton(textFieldWriting);
               handlePlayButtonItemOriginal();
               event.consume();
            }

            if (event.getCode().equals(KeyCode.DOWN) && !oldNode.equals(currentNode)) {
               oldNode.requestFocus();
               setBorder(oldNode);
               event.consume();
            } else if (event.getCode().equals(KeyCode.DOWN) && oldNode.equals(currentNode)) {
               node[17].requestFocus();
               setBorder(node[17]);
               event.consume();
            }

            if (event.getCode().equals(KeyCode.UP)) {
               event.consume();
            }
         }
      }
      );

      textFieldWriting.setOnMouseClicked(
              (e) -> {
         listViewH02Writing.setVisible(false);
         setBorder(textFieldWriting);
         oldNode = textFieldWriting;
         e.consume();
      }
      );

      textFieldTranslation.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent event
         )
         {

            listViewH02Translation.setVisible(false);

            if (event.getCode().equals(KeyCode.ENTER)) {
               listViewH02Translation.setVisible(true);
               handleCorrectionButton(textFieldTranslation);
               handlePlayButtonItemOriginal();
               event.consume();
            }

            if (event.getCode().equals(KeyCode.DOWN)) {
               oldNode.requestFocus();
               setBorder(oldNode);
               event.consume();
            } else if (event.getCode().equals(KeyCode.DOWN) && oldNode.equals(currentNode)) {
               node[31].requestFocus();
               setBorder(node[17]);
               event.consume();
            }

            if (event.getCode().equals(KeyCode.UP)) {
               event.consume();

            }
         }
      }
      );

      textFieldTranslation.setOnMouseClicked(
              (e) -> {
         listViewH02Translation.setVisible(false);
         setBorder(textFieldTranslation);
         oldNode = textFieldTranslation;
         e.consume();
      }
      );
   }
   //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Setting the borders">
   /**
    * Setting the border (cursor) of the node
    *
    * @param n the node to put the border
    */
   private void setBorder (Node n)
   {

      /* ObservableList<String> styleClasses = currentNode.getStyleClass();
      if(!styleClasses.contains("borderVisible")){
         styleClasses.add("borderVisible");
      } else {
      // remove all occurrences:
      styleClasses.removeAll(Collections.singleton("invalid-field"));
      }*/

      if (n.equals(listViewV)) {
         n = anchorListViewV;
      }
      int[] s = new int[]{13, 14, 15, 27, 28, 29, 41, 42, 43};

      boolean salida = true;
      for (int i : s) {
         if (node[i].equals(currentNode)) {
            salida = false;
         }
      }
      if (salida) {
         oldNode = currentNode;
      }

      currentNode.getStyleClass().removeAll(Collections.singleton("borderVisible"));

      n.getStyleClass().add("borderVisible");

      currentNode = n;
   }
   //</editor-fold>

   //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helper to the event">
   /**
    * Helper to the play button event
    *
    * @param up the above node
    * @param down the belong node
    * @param right the right node
    * @param left the left node
    * @param button The play button
    */
   private void eventButton (Node n, int up, int down, int right, int left)
   {
      n.setOnKeyPressed(
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent ke)
         {
            int i = -1;

            if (ke.getCode().equals(KeyCode.UP)) {
               i = up;
            }
            if (ke.getCode().equals(KeyCode.DOWN)) {
               i = down;
            }
            if (ke.getCode().equals(KeyCode.RIGHT)) {
               i = right;
            }

            if (ke.getCode().equals(KeyCode.LEFT)) {
               if (currentTab.equals("Leer") || currentTab.equals("Arriba")) {
                  currentTab = "Arriba";
               }
               if (currentTab.equals("Traducir") || currentTab.equals("Abajo")) {
                  currentTab = "Abajo";
               }
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

      n.setOnMouseClicked(
              (MouseEvent) -> {
         n.requestFocus();
         setBorder(n);
         oldNode = n;
         MouseEvent.consume();
      });
   }

   /**
    * Helper to the play button event
    *
    * @param up the above node
    * @param down the belong node
    * @param right the right node
    * @param left the left node
    * @param button The play button
    */
   private void eventButtonOriginalReading (Node n, int up, int down, int right, int left)
   {
      n.setOnKeyPressed(
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent ke)
         {
            int i = -1;

            if (ke.getCode().equals(KeyCode.UP)) {
               i = up;
            }
            if (ke.getCode().equals(KeyCode.DOWN)) {
               i = down;
            }
            if (kcRight.match(ke) && mediaPlayer.getCurrentTime().lessThanOrEqualTo(Duration.seconds(end)
                    .subtract(Duration.millis(1000)))) {
               mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.millis(1000)));
               currentOriginal = mediaPlayer.getCurrentTime();
               ke.consume();
               return;
            } else if (kcRight.match(ke)) {
               return;
            }
            if (ke.getCode().equals(KeyCode.RIGHT)) {
               i = right;
            }

            if (kcLeft.match(ke) && mediaPlayer.getCurrentTime().greaterThanOrEqualTo(Duration.seconds(start)
                    .add(Duration.millis(1000)))) {
               mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(Duration.
                       millis(1000)));
               currentOriginal = mediaPlayer.getCurrentTime();
               ke.consume();
               return;
            } else if (kcLeft.match(ke)) {
               return;
            }
            if (ke.getCode().equals(KeyCode.LEFT)) {
               if (currentTab.equals("Leer") || currentTab.equals("Arriba")) {
                  currentTab = "Arriba";
               }
               if (currentTab.equals("Traducir") || currentTab.equals("Abajo")) {
                  currentTab = "Abajo";
               }
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

      n.setOnMouseClicked(
              (MouseEvent) -> {
         n.requestFocus();
         setBorder(n);
         oldNode = n;
         MouseEvent.consume();
      });
   }


   /**
    * Helper to the slider media event
    *
    * @param slider The slider to setting
    * @param up the above node
    * @param down the belong node
    * @param right the right node
    * @param left the left node
    */
   private void eventMediaSlider (Slider slider, int up, int down, int right, int left)
   {
      slider.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {


         if (kcLeft.match(event) && slider.getValue() > 0) {
            slider.setValue(slider.getValue() - 1);
            mediaPlayerSlider = "slider";
            updateValues(mediaPlayerSlider);
            event.consume();
            return;
         }
         if (kcLeft.match(event) && slider.getValue() <= 0) {
            event.consume();
            return;
         }
         if (kcRight.match(event) && slider.getValue() < duration.toMillis()) {
            slider.setValue(slider.getValue() + 1);
            mediaPlayerSlider = "slider";
            updateValues(mediaPlayerSlider);
            event.consume();
            return;
         }
         if (kcRight.match(event) && slider.getValue() >= duration.toMillis()) {
            handleStopButton();
            event.consume();
            return;
         }

         if (event.getCode().equals(KeyCode.UP)) {
            node[up].requestFocus();
            setBorder(node[up]);
            event.consume();
         }
         if (event.getCode().equals(KeyCode.DOWN)) {
            node[down].requestFocus();
            setBorder(node[down]);
            event.consume();
         }
         if (event.getCode().equals(KeyCode.LEFT)) {
            node[left].requestFocus();
            setBorder(node[left]);
            event.consume();
         }
         if (event.getCode().equals(KeyCode.RIGHT)) {
            event.consume();
         }


      });


      // the slider.setOnMouseClicked is in Scroll Bar
   }

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
   private void eventFilterSlider (Slider slider, int left, int right, double min, double max,
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

         if (code.equals(KeyCode.LEFT)) {
            node[left].requestFocus();
            setBorder(node[left]);
            event.consume();
            return;
         }

         if (code.equals(KeyCode.UP)) {
            oldNode.requestFocus();
            setBorder(oldNode);
            event.consume();
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

//<editor-fold defaultstate="collapsed" desc="Tooltips">
   /**
    * Setting the Tooltips
    */
   private void setTooltips ()
   {
      String t1 = "Control + Izquierda: Bajar nivel.\n" +
              "Control + Derecha: Subir nivel.\n" +
              "Izquierda / Derecha: Cambia de zona.";
      String t2 = "Izquierda: Ir a la lista de frases.\n" +
              "Space: Reproduce la frase.\n" +
              "Enter: Reproduce la palabra.\n";
      String t3 = "Arriba / Abajo: Desplazarte por la lista.\n" +
              "Space: Reproduce la frase.\n" +
              "Izquierda: Retrocede la frase.\n" +
              "Derecha: Cambia de zona.";
      String t4 = "Space: Reproduce la frase.\n" +
              "Control + Derecha: Avanza un poco la frase.\n" +
              "Control + Izquierda: Retrocede un poco la frase.";

      tooltiptlistViewV.setText(t3);
      tooltipListViewH1.setText(t2);

      tooltipRateReading.setText(t1);
      tooltipBalanceReading.setText(t1);
      tooltipVolumenReading.setText(t1);
      tooltipSliderWriting.setText(t1);
      tooltipRateWriting.setText(t1);
      tooltipBalanceWriting.setText(t1);
      tooltipVolumenWriting.setText(t1);
      tooltipSliderTranslation.setText(t1);
      tooltipRateTranslation.setText(t1);
      tooltipBalanceTranslation.setText(t1);
      tooltipVolumenTranslation.setText(t1);
      tooltipSliderReading.setText(t1);

      toottipPlayOriginalReading.setText(t4);
      toottipPlayOriginalWriting.setText(t4);
      toottipPlayOriginalTranslation.setText(t4);

   }
//</editor-fold>

}