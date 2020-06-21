package LanguageApp.util;

import LanguageApp.controller.MainController;
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

   // HashMap with the name and the Audiclip
   private Map<String, AudioClip> audioClips;
   // An unique audioclip
   private AudioClip ac;
   private Set<String> wordSet;
   private String path;

   /**
    * Setting the audioclips
    *
    * @param wordSet a Set of String, result of creating a list of words of the media
    * @return A map with the name and the Audioclip
    */
   public Map<String, AudioClip> setAudioClip (Set<String> wordSet) {

      // instance of the Map
      audioClips = new HashMap<>();
      this.wordSet = wordSet;
      String audioError = null;
      // The path is an absolute path (retarive to the initial instalation)    
      path = System.getProperty("user.dir");
      path = path.replace("\\", "/");

      try {
         for (String ws : wordSet) {

            audioError = ws; // if this audid doesn´t exist I show it in an message.

            String s = path + "/dictionary/" + ws + ".mp3";

           URI resource = new File(s).toURI();
           

            ac = new AudioClip(resource.toString());

            audioClips.put(ws, ac);
         }
      } catch (Exception e) {
         new MainController().message(Alert.AlertType.ERROR, "Error message",
                 "Falta el audio: \"" + audioError + "\"",
                 "AudioClips.java / setAudioClip()", e);
         System.out.println(audioError);

      }
      return audioClips;
   }


   /**
    * Blinding the slider with the audioClips
    *
    * @param rateSlider The slider that control the Rate
    */
   public void setAudioClipRateSlider (Slider rateSlider) {
      wordSet.forEach((ws) -> {
         audioClips.get(ws).rateProperty().bind(rateSlider.valueProperty());
      });
   }


   /**
    * Blinding the slider with the audioClips
    *
    * @param balanceSlider The slider that control the balance
    */
   public void setAudioClipBalanceSlider (Slider balanceSlider) {
      wordSet.forEach((ws) -> {
         audioClips.get(ws).balanceProperty().bind(balanceSlider.valueProperty());
      });
   }


   /**
    * Blinding the slider with the audioClips
    *
    * @param volumeSlider The slider that control the volume
    */
   public void setAudioClipVolumeSlider (Slider volumeSlider) {
      wordSet.forEach((ws) -> {
         audioClips.get(ws).volumeProperty().bind(volumeSlider.valueProperty());
      });
   }


}
