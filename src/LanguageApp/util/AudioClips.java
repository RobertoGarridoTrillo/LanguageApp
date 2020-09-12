package LanguageApp.util;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioClip;


/**
 *
 * @author Roberto Garrido Trillo
 */
public class AudioClips {

   // Field Class
   // HashMap with the name and the Audiclip
   private Map<String, AudioClip> audioClips;
   // An unique audioclip
   private AudioClip ac;
   private Set<String> wordSet;
   private String path;

   // pop-up messages
   Message message;

   /**
    * Setting the audioclips
    *
    * @param wordSet a Set of String, result of creating a list of words of the media
    * @param lastdirectory
    * @param lastFile
    * @return A map with the name and the Audioclip
    */
   public Map<String, AudioClip> setAudioClip (Set<String> wordSet, String lastdirectory, Slider rateSlider, Slider balanceSlider, Slider volumeSlider)
   {

      // pop-up messages
      Message message = new Message(HandleLocale01.handleLocale01());

      // instance of the Map
      audioClips = new HashMap<>();

      //this.wordSet = wordSet;
      String audioError = null;

      String se = System.getProperty("file.separator");

      try {
         for (String ws : wordSet) {

            // This is to fix the forbbiden name con. in windows
            if (ws.equals("con")) {
               ws = "connn";
            }
            audioError = ws; // if this audid doesn´t exist I show it in an message.

            String s = lastdirectory + se + ws + ".mp3";

            URI resource = new File(s).toURI();

            ac = new AudioClip(resource.toString());

            audioClips.put(ws, ac);

            audioClips.get(ws).rateProperty().bind(rateSlider.valueProperty());
            audioClips.get(ws).balanceProperty().bind(balanceSlider.valueProperty());
            audioClips.get(ws).volumeProperty().bind(volumeSlider.valueProperty());
         }
      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "Falta el audio: \"" + audioError + "\"", "AudioClips.java / setAudioClip()", e);
      }
      return audioClips;
   }

}
