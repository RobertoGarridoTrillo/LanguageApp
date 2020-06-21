package LanguageApp.util;

import static java.lang.Math.floor;
import static java.lang.String.format;
import javafx.util.Duration;

/**
 *
 * @author Roberto Garrido Trillo
 */
public class FormatTime {

    /**
     * Format the Duration object elapsedToSeconds
     *
     * @param elapsedToSeconds (Duration currentTime = mediaPlayer.getCurrentTime();)
     * @param durationToSeconds The total durationToSeconds of the media
     * @return An string with the time correctly form
     */
    public String formatting (Duration elapsed, Duration duration) {

        int intElapsed = (int) floor(elapsed.toSeconds());
        int intDuration = (int) floor(duration.toSeconds());
        
        return format("%d:%02d:%02d %d:%02d:%02d", intElapsed / 3600, (intElapsed % 3600) / 60, 
                (intElapsed % 60), intDuration / 3600, (intDuration % 3600) / 60, (intDuration % 60));

    }

}
