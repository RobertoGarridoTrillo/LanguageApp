package LanguageApp.model;

import java.util.Map;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class AudioClipPhrase
 {

  private Map<String, MediaPlayer> audioMedia;


  public AudioClipPhrase(Map<String, MediaPlayer> audioMedia)
   {
    this.audioMedia = audioMedia;
   }


  public AudioClipPhrase()
   {
   }



  public Map<String, MediaPlayer> getAudioMedia()
   {
    return audioMedia;
   }


  public void setAudioMedia(Map<String, MediaPlayer> audioMedia)
   {
    this.audioMedia = audioMedia;
   }

 }
