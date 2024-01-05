import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {


    Clip audio;

     // For the use of sound effects with a specified audio file path and the Clip class for playback.
     // Handles potential exceptions.

    Audio (String path) {
        File file = new File(path);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            audio = AudioSystem.getClip();
            audio.open(audioInputStream);
        } catch (UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
