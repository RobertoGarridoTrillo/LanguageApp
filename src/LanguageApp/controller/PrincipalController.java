
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.scene.layout.BorderPane;
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
public class PrincipalController {


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

   // hyperlink youtube
   @FXML Hyperlink youtubeLink;

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

   public void setMainScene (MainScene aThis)
   {
      mainScene = aThis;
   }


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
         flw = new FillListView();
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

         // Setting Hyperlink youtube
         setHyperlink();

         node = new Node[]{
            listViewV, tabPanelListViewH,
            listViewH01Reading,
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
            rateSliderTranslation, balanceSliderTranslation, volumeSliderTranslation,
            youtubeLink
         };


         // Setting the listViewH and textField invisible or disabled
         listViewH01Reading.setVisible(true);
         textFieldWriting.setVisible(false);
         textFieldTranslation.setVisible(false);
         listViewH02Reading.setVisible(true);
         listViewH02Writing.setVisible(false);
         listViewH02Translation.setVisible(false);

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

            initFileIfExits = initFileIfExits.replace("mp4", "json");
            File file = new File(initFileIfExits);
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
   public void handleOpenMenu ()
   {

      // Open a new fileChooser, set the stage where it´s shows,return un File
      dir.readIni();
      File file = sf.getSelectedFile(mainStage, dir.getLastDirectory());
      handleOpenMenu2(file);

   }

   /**
    * Open a SelectFile and seek a json to load the phrases (Part2)
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
         String titleMp4 = flw.setListView(listViewV, itemsOriginal);

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
         audioClips = ac.setAudioClip(wordSet, dir.getLastFile());
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
         //message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "PrincipalController / handleOpenMenu2()", e);
      }

   }

   /**
    * When click on the close menu
    */
   public void handleCloseMenu ()
   {
      try {
         if (mediaPlayer == null) {
            return;
         }

         mediaPlayer.stop();
         mediaPlayer.dispose();
         audioClips = null;

         itemsOriginal = new Item[1];
         itemsOriginal[0] = new Item(0, 0, " ");
         itemsTranslation = new Item[1];
         itemsTranslation[0] = new Item(0, 0, " ");
         flw.setListView(listViewV, itemsOriginal);
         flw.setListView(listViewH01Reading, itemsOriginal);
         flw.setListView(listViewH02Reading, itemsOriginal);

         textFieldWriting.setText("");
         textFieldTranslation.setText("");

         handleStopButton();

         // Delete and create and empty initial file
         dir.createIni();

         // change the mainStage´s height depending of the media´s height
         // Setting the initial size

         // media = null;
         // mediaPlayer = null;
         // mediaView = null;
         // mediaView.setFitHeight(0);
         
         // mediaView.setMediaPlayer(null);
         //anchorMedia.setMaxHeight(0);
         anchorMedia.setMinHeight(120);
         // anchorMedia.setPrefHeight(115);


         int height = 404;
         mainStage.setHeight(height);
         height -= 71;
         listViewV.setMinHeight(height);
         listViewV.setMaxHeight(height);
         listViewV.setPrefHeight(height);

      } catch (Exception e) {
         message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "PrincipalController / handleMenuClose()", e);
      }
   }


   /**
    * handle of the "About message"
    */
   public void handleAboutMenu ()
   {
      message(Alert.AlertType.INFORMATION, "LanguageApp", "Sobre esta aplicación:", "Autor: Roberto Garrido Trillo",
              null);
   }


   /**
    * handle of the "About message"
    */
   public void handleControlesMenu ()
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

      try {
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
         //message(Alert.AlertType.ERROR, "Error message", e.getMessage(),     "PrincipalController / handlePlayButton()", e);      
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
         //message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "PrincipalController / handleStopButton()", e);
      }
   }

   /**
    * Handle of the Play Button Item
    */
   @FXML public void handlePlayButtonItemOriginal ()
   {
      try {

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
      } catch (Exception e) {
         // message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "PrincipalController / handlePlayButtonItemOriginal()", e);
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
      try {
         if (indexItemV < itemsOriginal.length) {
            // forward the index
            indexItemV++;

            // index of listview to zero
            listViewV.scrollTo(indexItemV);
            listViewV.getSelectionModel().select(indexItemV);
            backForward();
         }
      } catch (Exception e) {

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
         } else if (currentTab.equals("Traducir")) {
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
         } else if (currentTab.equals("Traducir")) {
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
         } else if (currentTab.equals("Traducir")) {
            indicatorSuccessTranslation.setProgress(success);
         }
      } catch (Exception e) {
         // message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "PrincipalController / handleCorrectionButton()", e);
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
      setSliderForm(rateSliderReading, 0.80, 1.0, 1.0, 0.02,
              1);
      rateLabelReading.setText("1,00"); // initial value
      setSliderForm(balanceSliderReading, -1.0, 1.0, 0.0, 1,
              10);
      balanceLabelReading.setText("0,00"); // initial value

      // Slider tab Writing
      setSliderForm(volumeSliderWriting, 0.0, 100.0, 20.0, 20,
              2);
      volumeLabelWriting.setText("20,00"); // initial value
      setSliderForm(rateSliderWriting, 0.80, 1.0, 1.0, 0.02,
              1);
      rateLabelWriting.setText("1,00"); // initial value
      setSliderForm(balanceSliderWriting, -1.0, 1.0, 0.0, 1,
              10);
      balanceLabelWriting.setText("0,00"); // initial value   

      // Slider tab Translation
      setSliderForm(volumeSliderTranslation, 0.0, 100.0, 20.0, 20,
              2);
      volumeLabelTranslation.setText("20,00"); // initial value
      setSliderForm(rateSliderTranslation, 0.80, 1.0, 1.0, 0.02,
              1);
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
            //message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "PrincipalController / setSliderForm()", e);
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
         message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "PrincipalController / setMediaPlayer()", e);
      }
   }

   /**
    * Return the status of the mediaPlayer
    *
    * @return
    */
   public String getMediaStatus ()
   {
      try {
         Status s = mediaPlayer.getStatus();
         if (s.equals(Status.PLAYING)) {
            if (originalButton) {
               return "playingOriginal";
            } else {
               return "playing";
            }
         }

         if (s.equals(Status.PAUSED)) {
            if (originalButton) {
               return "originalPause";
            } else {
               return "pause";
            }
         }
      } catch (Exception e) {
      }
      return "stop";

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
         /* message(Alert.AlertType.ERROR, "Error message", e.getMessage(), "PrincipalController / showListViewH()", e);*/

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

//<editor-fold defaultstate="collapsed" desc="Setting the Hyperlink">
   /**
    * Setting the hyperlink that link to youtube
    */
   public void setHyperlink ()
   {
      youtubeLink.setText("Youtube");

      youtubeLink.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle (ActionEvent e)
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
                       getMessage(), "PrincipalController / setEventMarker()", e);
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
                    "PrincipalController / mediaPlayer.setOnError()", null);
         }


      });

      media.setOnError(new Runnable() {

         @Override
         public void run ()
         {
            // Handle asynchronous error in Media object.
            message(Alert.AlertType.ERROR, "Error message",
                    media.getError().toString(),
                    "PrincipalController / media.setOnError()", null);
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
                    "PrincipalController / mediaView.setOnError()", null);
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

         new PrincipalController().message(Alert.AlertType.ERROR, "Error message",
                 e.getMessage(),
                 "PrincipalController.java / setMediaPlayerChangeListener()", e);

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

      alert.getDialogPane().getStylesheets().
              add(getClass().getResource("/LanguageApp/style/style.css").toExternalForm());
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
                     new PrincipalController().message(Alert.AlertType.ERROR,
                             "Error message",
                             e.getMessage(),
                             "PrincipalController.java / updateValues()", e);

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

            setChangedSizeDashboard();

            anchorMedia.setPrefHeight(media.getHeight());
            anchorMedia.setMinHeight(media.getHeight());

            mediaView.setFitHeight(media.getHeight());
         }
      });
   }

   /**
    * Change the size of the elements when close the media
    */
   public void setChangedSizeDashboard ()
   {
      int height = 404;
      // change the mainStage´s height depending of the media´s height
      if (media != null) {
         height = 764 - (480 - media.getHeight());
      }
      mainStage.setHeight(height);
      height -= 71;
      listViewV.setMinHeight(height);
      listViewV.setMaxHeight(height);
      listViewV.setPrefHeight(height);
   }

   /**
    * Change the size of the elements when open the login
    */
   public void setChangedSizeLogin ()
   {
      mainStage.setHeight(640);
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

            try {
               indexItemV = listViewV.getSelectionModel().getSelectedIndex();

               switch (ke.getCode()) {

                  case UP:
                     if (indexItemV > 0) {
                        indexItemV--;
                        listViewV.getSelectionModel().select(indexItemV);
                        showListViewH();
                        listViewV.getSelectionModel().select(indexItemV + 1);
                        //ke.consume();
                     }
                     break;

                  case DOWN:
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

            }
         }
      });

      listViewH01Reading.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent event)
         {
            try {

               if (event.getCode().equals(KeyCode.UP)) {
                  youtubeLink.requestFocus();
                  setBorder(youtubeLink);
                  event.consume();
               }

               if (event.getCode().equals(KeyCode.DOWN)) {

                  int[] i = new int[]{3, 4, 5, 6, 7, 18, 19, 20, 21, 22, 32, 33, 34, 35, 36};
                  boolean salida = false;

                  for (int j : i) {
                     if (oldNode.equals(node[j])) {
                        salida = true;
                     }
                  }
                  if (!salida) {
                     switch (currentTab) {
                        case "Leer":
                           oldNode = playButtonReading;
                           break;
                        case "Escribir":
                           oldNode = playButtonWriting;
                           break;
                        case "Traducir":
                           oldNode = playButtonTranslation;
                           break;
                        default:
                           break;
                     }
                  }

                  oldNode.requestFocus();
                  setBorder(oldNode);
                  event.consume();
               }

               if (event.getCode().equals(KeyCode.LEFT)) {
                  if (listViewH01Reading.getSelectionModel().getSelectedIndex() == 0) {
                     listViewV.requestFocus();
                     setBorder(listViewV);
                     event.consume();
                  }
               }

               if (event.getCode().equals(KeyCode.SPACE)) {
                  handlePlayButtonItemMachine();
                  event.consume();
               }

               if (event.getCode().equals(KeyCode.ENTER)) {
                  handlePlayButtonItemOriginal();
                  event.consume();
               }
            } catch (Exception e) {

            }
         }
      });

      // the event onclick is in Setting the listview
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Button">
      eventButton(playButtonReading, 2, 8, 4, 0);
      eventButton(backButtonReading, 2, 9, 5, 3);
      eventButton(forwardButtonReading, 2, 10, 6, 4);
      eventButton(stopButtonReading, 2, 11, 7, 5);
      eventMediaSlider(mediaSliderReading, 2, 12, -1, 6);
      eventButtonOriginalReading(playButtonItemOriginalReading, 3, 14, 9, 0);
      eventButton(backButtonItemOriginalReading, 4, 14, 10, 8);
      eventButton(forwardButtonItemOriginalReading, 5, 15, 11, 9);
      eventButton(stopButtonItemOriginalReading, 6, 15, 12, 10);
      eventButton(playButtonItemMachineReading, 7, 16, 13, 11);
      eventButton(stopButtonItemMachineReading, 7, 16, -1, 12);
      eventFilterSlider(rateSliderReading, 8, 1, 15, 0, 0.5, 2, 0.01);
      eventFilterSlider(balanceSliderReading, 10, 1, 16, 14, -1.0, 1, 0.1);
      eventFilterSlider(volumeSliderReading, 12, 1, -1, 15, 0.0, 100, 1);

      eventButton(playButtonWriting, 17, 23, 19, 0);
      eventButton(backButtonWriting, 17, 24, 20, 18);
      eventButton(forwardButtonWriting, 17, 25, 21, 19);
      eventButton(stopButtonWriting, 17, 26, 22, 20);
      eventMediaSlider(mediaSliderWriting, 17, 27, -1, 21);
      eventButtonOriginalReading(playButtonItemOriginalWriting, 18, 28, 24, 0);
      eventButton(backButtonItemOriginalWriting, 19, 28, 25, 23);
      eventButton(forwardButtonItemOriginalWriting, 20, 29, 26, 24);
      eventButton(stopButtonItemOriginalWriting, 21, 29, 27, 25);
      eventButton(correctionButtonWriting, 22, 30, -1, 26);
      eventFilterSlider(rateSliderWriting, 23, 1, 29, 0, 0.5, 2, 0.01);
      eventFilterSlider(balanceSliderWriting, 25, 1, 30, 28, -1.0, 1, 0.1);
      eventFilterSlider(volumeSliderWriting, 27, 1, -1, 29, 0.0, 100, 1);

      eventButton(playButtonTranslation, 31, 37, 33, 0);
      eventButton(backButtonTranslation, 31, 38, 34, 32);
      eventButton(forwardButtonTranslation, 31, 39, 35, 33);
      eventButton(stopButtonTranslation, 31, 40, 36, 34);
      eventMediaSlider(mediaSliderTranslation, 31, 41, -1, 35);
      eventButtonOriginalReading(playButtonItemOriginalTranslation, 32, 42, 38, 0);
      eventButton(backButtonItemOriginalTranslation, 33, 42, 39, 37);
      eventButton(forwardButtonItemOriginalTranslation, 34, 43, 40, 38);
      eventButton(stopButtonItemOriginalTranslation, 35, 43, 41, 39);
      eventButton(correctionButtonTranslation, 36, 44, -1, 40);
      eventFilterSlider(rateSliderTranslation, 37, 1, 43, 0, 0.5, 2, 0.01);
      eventFilterSlider(balanceSliderTranslation, 39, 1, 44, 42, -1.0, 1, 0.1);
      eventFilterSlider(volumeSliderTranslation, 41, 1, -1, 43, 0.0, 100, 1);

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
                          flw.setListView(listViewV, itemsOriginal);
                          setListViewH();
                          oldNode = rateSliderReading;
                          break;

                       case "Escribir":
                          flw.setListView(listViewV, itemsTranslation);
                          oldNode = rateSliderWriting;
                          break;

                       case "Traducir":
                          flw.setListView(listViewV, itemsOriginal);
                          oldNode = rateSliderTranslation;
                          break;

                       default:
                          break;
                    }
                    listViewV.getSelectionModel().select(indexItemV);

                    currentTab = t1.getText();
                    listViewH01Reading.setVisible(false);
                    textFieldWriting.setVisible(false);
                    textFieldTranslation.setVisible(false);
                    listViewH01Reading.setVisible(currentTab.equals("Leer"));
                    textFieldWriting.setVisible(currentTab.equals("Escribir"));
                    textFieldTranslation.setVisible(currentTab.equals("Traducir"));
                    listViewH02Reading.setVisible(currentTab.equals("Leer"));
                    listViewH02Writing.setVisible(currentTab.equals("Escribir"));
                    listViewH02Translation.setVisible(currentTab.equals("Traducir"));

                 }

                 @Override
                 public String toString ()
                 {
                    return super.toString();
                 }

              });

      tabPanelListViewH.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent event)
         {
            // An triger
            if (!event.getTarget().equals(tabPanelListViewH)) {
               return;
            }

            if (event.getCode().equals(KeyCode.UP)) {
               event.consume();
               if (oldNode.equals(listViewV)) {
                  if (currentTab.equals("Leer")) {
                     rateSliderReading.requestFocus();
                     setBorder(rateSliderReading);
                  }

                  if (currentTab.equals("Escribir")) {
                     rateSliderWriting.requestFocus();
                     setBorder(rateSliderWriting);
                  }
                  if (currentTab.equals("Traducir")) {
                     rateSliderTranslation.requestFocus();
                     setBorder(rateSliderTranslation);
                  }
               } else {
                  oldNode.requestFocus();
                  setBorder(oldNode);
               }

            }

            if (event.getCode().equals(KeyCode.LEFT) && currentTab.equals("Leer")) {
               setBorder(listViewV);
               listViewV.requestFocus();
               event.consume();
            }

            if (event.getCode().equals(KeyCode.RIGHT) && currentTab.equals("Traducir")) {
               event.consume();
            }

            if (event.getCode().equals(KeyCode.DOWN)) {
               event.consume();
            }
         }
      }
      );

      tabPanelListViewH.setOnMouseClicked(
              (MouseEvent) -> {
         setBorder(tabPanelListViewH);
         MouseEvent.consume();
         switch (currentTab) {
            case "Leer":
               flw.setListView(listViewV, itemsOriginal);
               setListViewH();
               oldNode = rateSliderReading;
               break;

            case "Escribir":
               flw.setListView(listViewV, itemsTranslation);
               oldNode = rateSliderWriting;
               break;

            case "Traducir":
               flw.setListView(listViewV, itemsOriginal);
               oldNode = rateSliderTranslation;
               break;

            default:
               break;
         }
      });
      //</editor-fold>        

//<editor-fold defaultstate="collapsed" desc="TextField">

      eventTextfield(textFieldWriting, listViewH02Writing, playButtonWriting);
      eventTextfield(textFieldTranslation, listViewH02Translation, playButtonTranslation);
      //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Hyperlink youtube">
      youtubeLink.setOnKeyPressed(new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent ke)
         {
            if (ke.getCode().equals(KeyCode.UP) || ke.getCode().equals(KeyCode.LEFT)) {
               listViewV.requestFocus();
               setBorder(listViewV);
               ke.consume();
            }
            if (ke.getCode().equals(KeyCode.DOWN) || ke.getCode().equals(KeyCode.RIGHT)) {
               switch (currentTab) {
                  case "Leer":
                     listViewH01Reading.requestFocus();
                     setBorder(listViewH01Reading);
                     ke.consume();
                     break;
                  case "Escribir":
                     textFieldWriting.requestFocus();
                     setBorder(textFieldWriting);
                     ke.consume();
                     break;
                  case "Traducir":
                     textFieldTranslation.requestFocus();
                     setBorder(textFieldTranslation);
                     ke.consume();
                     break;
                  default:
                     break;
               }
            }
            if (ke.getCode().equals(KeyCode.ENTER)) {
               youtubeLink.requestFocus();
               setBorder(youtubeLink);
               oldNode = youtubeLink;
               mainScene.getHostServices().showDocument("https://www.youtube.com/channel/UCP9QxVSsuLDdxQvxfFvHpEQ");
               ke.consume();
            }

         }
      });

      // The setOnMouseClick is in setHyperlink
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

   //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Helper to the event">
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

//<editor-fold defaultstate="collapsed" desc="ButtonOriginal">

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
      n.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
   private void eventFilterSlider (Slider slider, int up, int down, int right, int left, double min, double max,
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
            int[] i = new int[]{0, 1, 14, 15, 16, 28, 29, 30, 42, 43, 44};
            boolean salida = false;

            for (int j : i) {
               if (oldNode.equals(node[j])) {
                  salida = true;
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
    * @param lw The listview that become visible or invisible with the answer
    * @param bt The default button to go when click on down and there's conflict with the origin
    */
   private void eventTextfield (TextField tf, ListView lw, Button bt)
   {
      tf.addEventFilter(KeyEvent.KEY_PRESSED,
              new EventHandler<KeyEvent>() {
         @Override
         public void handle (KeyEvent event)
         {

            lw.setVisible(false);

            if (event.getCode().equals(KeyCode.ENTER)) {
               lw.setVisible(true);
               handleCorrectionButton(tf);
               handlePlayButtonItemOriginal();
               event.consume();
            }

            if (event.getCode().equals(KeyCode.DOWN) &&
                    (oldNode.equals(currentNode) || oldNode.equals(listViewV) || oldNode.equals(youtubeLink))) {
               bt.requestFocus();
               setBorder(bt);
               event.consume();
            } else if (event.getCode().equals(KeyCode.DOWN) && !oldNode.equals(currentNode)) {
               oldNode.requestFocus();
               setBorder(oldNode);
               event.consume();
            }

            if (event.getCode().equals(KeyCode.UP)) {
               youtubeLink.requestFocus();
               setBorder(youtubeLink);
               event.consume();
            }

            if (event.getCode().equals(KeyCode.LEFT) && tf.getCaretPosition() <= 0) {
               listViewV.requestFocus();
               setBorder(listViewV);
               event.consume();
            }
         }

      });

      tf.setOnMouseClicked((e) -> {
         if (tf.equals(textFieldWriting)) {
            listViewH02Writing.setVisible(false);
         } else {
            listViewH02Translation.setVisible(false);
         }
         setBorder(tf);
         oldNode = tf;
         e.consume();
      });
   }
//</editor-fold>

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
              "Enter: Reproduce la frase.\n" +
              "Space: Reproduce la palabra.\n";
      String t3 = "Izquierda / Derecha: Desplazarte por la lista.\n" +
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
