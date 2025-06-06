package com.notifications.system_tray_notifications.basics;

import java.util.Arrays;

/**
 * The {@code AlarmSounds} class manages alarm sound sequences.
 * It allows setting and retrieving the sequence number for alarm sounds
 * and provides methods to retrieve or search for sounds by name or index.
 *
 * <p>This class helps in selecting and retrieving the alarm sound to be played during a notification. It includes
 * functionality to get the file name of the alarm sound based on the sequence number and ensures that a valid sound
 * is selected by default.</p>
 *
 * @author Muath Hassoun
 */
public class AlarmSounds {
    private boolean soundIsSelected;
    private int soundSequenceNumber;
    
    /**
     * Array containing the available alarm sound file names.
     */
    private static final String[] arrayOfSoundsSequence = {
            "Alarm01.wav",
            "Alarm02.wav",
            "Alarm03.wav",
            "Alarm04.wav",
            "Alarm05.wav",
            "Alarm06.wav",
            "Alarm07.wav",
            "Alarm08.wav",
            "Alarm09.wav",
            "Alarm10.wav"
    };
    
    /**
     * Constructs an {@code AlarmSounds} object with a specified sound sequence number.
     * Marks the sound as selected.
     *
     * @param soundSequenceNumber The sequence number to select for the alarm sound.
     */
    public AlarmSounds(int soundSequenceNumber){
        this.soundSequenceNumber = soundSequenceNumber;
        this.soundIsSelected = true;
    }
    
    /**
     * Retrieves the current sound sequence number.
     *
     * @return The current sequence number of the alarm sound.
     */
    public int getSoundSequenceNumber(){
        return soundSequenceNumber;
    }
    
    /**
     * Sets the sound sequence number and marks a sound as selected.
     *
     * @param soundSequenceNumber The sequence number to set for the alarm sound.
     */
    public void setSoundSequenceNumber(int soundSequenceNumber){
        if (soundSequenceNumber >= 1 && soundSequenceNumber <= arrayOfSoundsSequence.length) {
            this.soundSequenceNumber = soundSequenceNumber;
            this.soundIsSelected = true;
        } else {
            throw new IllegalArgumentException("Invalid sound sequence number.");
        }
    }
    
    /**
     * Retrieves the file name of the selected alarm sound.
     * If no sound has been selected, the first sound in the sequence is returned by default.
     *
     * @return The file name of the current alarm sound.
     */
    public String getSoundFileName(){
        if(soundIsSelected){
            return arrayOfSoundsSequence[soundSequenceNumber - 1];
        }
        return arrayOfSoundsSequence[0];
    }
    
    /**
     * Returns the index (1-based) of a given sound name in the sequence.
     *
     * @param soundName The name of the sound file to search for.
     * @return The index (1-based) if found, or -1 if not found.
     */
    public static int getIndexBySoundName(String soundName) {
        for (int i = 0; i < arrayOfSoundsSequence.length; i++) {
            if (arrayOfSoundsSequence[i].equalsIgnoreCase(soundName)) {
                return i + 1;
            }
        }
        return -1; // Not found
    }
    
    /**
     * Returns the sound file name at a given index (1-based).
     *
     * @param index The index of the sound (1-based).
     * @return The sound file name if the index is valid, otherwise null.
     */
    public static String getSoundNameByIndex(int index) {
        if (index >= 1 && index <= arrayOfSoundsSequence.length) {
            return arrayOfSoundsSequence[index - 1];
        }
        return null;
    }
    
    /**
     * Returns a copy of the full sound sequence array.
     *
     * @return A string array of all sound file names.
     */
    public static String[] getFullSoundSequence() {
        return Arrays.copyOf(arrayOfSoundsSequence, arrayOfSoundsSequence.length);
    }
}
