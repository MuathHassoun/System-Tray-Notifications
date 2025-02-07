package com.notifications.system_tray_notifications.influence;
import static com.notifications.system_tray_notifications.influence.DisplayMessages.printErrorMessage;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The {@code PlaySounds} class provides functionality to play sound files
 * from the resources folder. It uses the Java Sound API to handle audio streams
 * and playback.
 *
 * @author Muath Hassoun
 */
public class PlaySounds {

    /**
     * Plays a sound file from the resources' folder.
     *
     * @param fileName The name of the sound file in the resources' folder.
     *                 The file should be located under the "/Alarm-Sounds/" directory.
     *
     * @throws IllegalArgumentException If the specified file cannot be found.
     */
    public static void playSound(String fileName) {
        try {
            InputStream audioSrc = PlaySounds.class.getResourceAsStream("/Alarm-Sounds/" + fileName);
            if (audioSrc == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            printErrorMessage(e);
        }
    }
}