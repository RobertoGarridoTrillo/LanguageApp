package LanguageApp.util;

import static java.lang.Math.floor;
import static java.lang.String.format;
import javafx.scene.control.Alert;
import javafx.util.Duration;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class FormatTime {

   // pop-up messages
   Message message = new Message();

   /**
    * Format the Duration object elapsedToSeconds
    *
    * @param elapsed
    * @param duration
    * @return An string with the time correctly form
    */
   public String formatting (Duration elapsed, Duration duration)
   {
      int intElapsed = 0;
      int intDuration = 0;
      try {
         intElapsed = (int) floor(elapsed.toSeconds());
         intDuration = (int) floor(duration.toSeconds());

      } catch (Exception e) {
         message.message(Alert.AlertType.ERROR, "Error message", "FormatTime / formatting()", e.toString(), e);
      }
      return format("%d:%02d:%02d %d:%02d:%02d", intElapsed / 3600, (intElapsed % 3600) / 60,
              (intElapsed % 60), intDuration / 3600, (intDuration % 3600) / 60, (intDuration % 60));

   }


}
