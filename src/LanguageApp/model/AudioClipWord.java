package LanguageApp.model;

import java.util.Map;
import javafx.scene.media.AudioClip;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class AudioClipWord
 {

  private Map<String, AudioClip> audioClip;


  public AudioClipWord(Map<String, AudioClip> audioClip)
   {
    this.audioClip = audioClip;
   }


  public Map<String, AudioClip> getAudioClip()
   {
    return audioClip;
   }


  public void setAudioClip(Map<String, AudioClip> audioClip)
   {
    this.audioClip = audioClip;
   }
}
