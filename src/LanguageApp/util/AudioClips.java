package LanguageApp.util;

import static LanguageApp.util.Message.message;
import static LanguageApp.util.Message.showException;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class AudioClips
 {

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
  public HashMap<String, AudioClip> setAudioClip(Set<String> set, String lastdirectory, Slider rateSlider, Slider balanceSlider, Slider volumeSlider)
   {

    Map<String, AudioClip> audioClips = new HashMap<>();
    audioClips.clear();

    String s;

    String audioError;

    String se = System.getProperty("file.separator");

    URI resource;

    audioError = null;

    try {

      for (String ws : set) {

        // This is to fix the forbbiden name con. in windows
        if (ws.equals("con")) ws = "connn";
        if (ws.equals("aux")) ws = "auxxx";

        audioError = ws; // if this audid doesn´t exist I show it in an message.

        s = lastdirectory + se + ws + ".mp3";

        resource = new File(s).toURI();
        AudioClip ac = new AudioClip(resource.toString());

        audioClips.put(ws, ac);
        // ac = null;

        audioClips.get(ws).setRate(rateSlider.getValue());
        audioClips.get(ws).setBalance(balanceSlider.getValue());
        audioClips.get(ws).setVolume(volumeSlider.getValue());
        audioClips.get(ws).rateProperty().bind(rateSlider.valueProperty());
        audioClips.get(ws).balanceProperty().bind(balanceSlider.valueProperty());
        audioClips.get(ws).volumeProperty().bind(volumeSlider.valueProperty()
                .divide(100));
      }
    } catch (Exception e) {
      final String error = audioError;
      Platform.runLater(() -> {
        try {
          message(
                  Alert.AlertType.ERROR,
                  "Error message", "Falta el audio: \"" + error + "\"",
                  "AudioClips.java / setAudioClip()",
                  null);
        } catch (Exception ex) {
          showException(ex);
        }
      });
    }
    System.gc();
    System.runFinalization();

    return new HashMap<String, AudioClip>()
     {
      {
        putAll(audioClips);
      }

     };
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
        audioMedia.get(ws).volumeProperty().bind(volumeSlider.valueProperty()
                .divide(100));
      }
    } catch (Exception e) {
      final String error = audioError;
      Platform.runLater(() -> {
        try {
          message(
                  Alert.AlertType.ERROR,
                  "Error message", "Falta el audio: \"" + error + "\"",
                  "AudioClips.java / setAudioClip()",
                  null);
        } catch (Exception ex) {
          showException(ex);
        }
      });
    }

    System.gc();
    System.runFinalization();
    return new HashMap<String, MediaPlayer>()
     {
      {
        putAll(audioMedia);
      }

     };
   }

 }
