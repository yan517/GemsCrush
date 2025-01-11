//Author:Cindy Lam

package Game;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Handles playing, stoping, and looping of sounds for the game.
 *
 * @author Tyler Thomas
 *
 */
public class Sound {

    private Clip clip;

    public Sound(String fileName) {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        try {
            InputStream audioSrc = Sound.class.getClassLoader().getResourceAsStream(fileName);

            if (audioSrc == null) {
                System.err.println("file is not found： " + fileName);
                return;
            }

            InputStream bufferedIn = new BufferedInputStream(audioSrc);

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(audioStream);
             
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }

        // play, stop, loop the sound clip
    }

    public void play() {
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
