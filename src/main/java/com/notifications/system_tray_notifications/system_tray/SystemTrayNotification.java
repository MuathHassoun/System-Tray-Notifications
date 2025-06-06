package com.notifications.system_tray_notifications.system_tray;
import static com.notifications.system_tray_notifications.influence.DisplayMessages.printErrorMessage;
import com.notifications.system_tray_notifications.basics.AlarmSounds;
import com.notifications.system_tray_notifications.basics.Notifications;
import com.notifications.system_tray_notifications.influence.PlaySounds;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

/**
 * This class handles the creation of a system tray notification with customizable options.
 * It allows for displaying notifications, playing sounds, and showing dialogs with custom messages or panels.
 *
 * @author Muath Hassoun
 */
public class SystemTrayNotification{
    /**
     * Flag to determine if a JPanel should be shown in the dialog
      */
    private boolean isToShowA$Panel = false;

    /**
     * Message to be displayed if a JPanel is not shown
     */
    private String message = "Alarm!";

    /**
     * JPanel to be displayed in the dialog if enabled
     */
    private JPanel panel = new JPanel();

    /**
     * The TrayIcon object that represents the icon displayed in the system tray.
     * This icon is used to trigger notifications and handle user interactions (e.g., clicks).
     * It is initialized in the `Tray` method and is used to display notifications and the associated popup menu.
     */
    private static TrayIcon trayIcon;

    /**
     * The Swing Timer instance.
     */
    private static Timer timer;
    
    /**
     * Initializes and displays a system tray notification with a custom icon, sound, and message behavior.
     * <p>
     * This method performs the following actions:
     * <ul>
     *   <li>Loads a PNG icon from the application resources ({@code /icon/icon.png}).</li>
     *   <li>Creates a {@link TrayIcon} with the loaded image and application title.</li>
     *   <li>Associates a custom popup menu with various tray actions (hide, mute, restart, about, exit).</li>
     *   <li>Handles click events on the tray icon to show either a message or a {@code JPanel}.</li>
     *   <li>Sets up a repeating or one-time alarm notification with sound and message display.</li>
     * </ul>
     * <p>
     * If the platform does not support system tray notifications, or if the icon resource fails to load,
     * the method exits silently. Exceptions during execution are printed using the {@code printErrorMessage} utility.
     *
     * @param notification_object an instance of {@link Notifications} containing title, message, icon path, and settings
     * @param alarm_object        an instance of {@link AlarmSounds} for retrieving and managing the alarm sound
     */
    public void CreateTrayIcon(Notifications notification_object, AlarmSounds alarm_object) {
        try {
            if (!SystemTray.isSupported()) {
                System.out.println("SystemTray is not supported on this platform.");
                return;
            }
            SystemTray systemTray = SystemTray.getSystemTray();
            InputStream is = SystemTrayNotification.class.getResourceAsStream("/icon/icon.png");
            if (is == null) return;
            java.awt.Image image = ImageIO.read(is);
            
            trayIcon = new TrayIcon(image, notification_object.getAppTitle());
            trayIcon.setPopupMenu(createPopupMenu());
            trayIcon.addActionListener(
                    _ -> {
                        message = notification_object.getAlarmMessage();
                        showDialog(notification_object.getAlarmTitle());
                    }
            );
            
            setupSystemTray(
                    alarm_object,
                    notification_object.getAlarmTitle(),
                    notification_object.getAlarmMessage(),
                    notification_object.getDuration(),
                    notification_object.getIsRepeating()
            );
            
            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                printErrorMessage(e);
            }
        } catch (Exception e) {
            printErrorMessage(e);
        }
    }
    
    /**
     * Creates and returns a fully configured popup menu for the system tray icon.
     * <p>
     * The menu includes the following options:
     * <ul>
     *     <li><b>Hide Icon:</b> Removes the tray icon from the system tray.</li>
     *     <li><b>Mute Sound:</b> Stops the alarm timer and disables sound temporarily.</li>
     *     <li><b>Restart Timer:</b> Restarts the alarm timer to resume notifications.</li>
     *     <li><b>About:</b> Displays an informational dialog about the application.</li>
     *     <li><b>Exit:</b> Terminates the application immediately.</li>
     * </ul>
     * <p>
     * This menu enhances user interaction by allowing control over the tray behavior,
     * sound settings, and access to basic application information.
     *
     * @return A {@code PopupMenu} object with predefined tray actions.
     */
    private static PopupMenu createPopupMenu() {
        PopupMenu popup = new PopupMenu();
        
        MenuItem hideIconItem = new MenuItem("Hide Icon");
        hideIconItem.addActionListener(_ -> {
            SystemTray.getSystemTray().remove(trayIcon);
        });
        
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.addActionListener(_ -> {
            JOptionPane.showMessageDialog(null,
                    "Notification System\nVersion 1.0\nDeveloped by Muath Hassoun",
                    "About", JOptionPane.INFORMATION_MESSAGE);
        });
        
        MenuItem muteItem = new MenuItem("Mute Sound");
        muteItem.addActionListener(_ -> {
            stopTimer();
            trayIcon.displayMessage("Muted", "Alarm sound has been muted.", TrayIcon.MessageType.INFO);
        });
        
        MenuItem restartTimerItem = new MenuItem("Restart Timer");
        restartTimerItem.addActionListener(_ -> {
            startTimer();
            trayIcon.displayMessage("Timer Restarted", "Alarm timer is running again.", TrayIcon.MessageType.INFO);
        });
        
        MenuItem exitItem = new MenuItem("Stop Program");
        exitItem.addActionListener(_ -> System.exit(0));
        
        popup.add(hideIconItem);
        popup.add(muteItem);
        popup.add(restartTimerItem);
        popup.addSeparator();
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(exitItem);
        return popup;
    }
    
    /**
     * Configures the system tray icon to display notifications.
     * The notification can optionally repeat based on the specified duration.
     *
     * @param alarm_object The `AlarmSounds` object to retrieve sound details
     * @param title The title for the notification and dialog
     * @param message The message to be shown in the notification and dialog
     * @param duration The time interval (in milliseconds) for how long the notification will show
     * @param isRepeating A boolean value indicating whether the notification should repeat
     */
    private static void setupSystemTray(AlarmSounds alarm_object, String title, String message, int duration, boolean isRepeating) {
        timer = new Timer(duration, _ -> {
            PlaySounds.playSound(alarm_object.getSoundFileName());
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        });
        timer.setRepeats(isRepeating);
        timer.start();
    }

    /**
     * Initializes the timer with a given delay, repetition mode, and action listener.
     * If the timer is already initialized, it stops and reinitializes it.
     *
     * @param delay        The delay in milliseconds between action events.
     * @param isRepeating  {@code true} if the timer should repeat, {@code false} if it should fire only once.
     * @param listener     The action listener that handles timer events.
     */
    public static void initializeTimer(int delay, boolean isRepeating, ActionListener listener) {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(delay, listener);
        timer.setRepeats(isRepeating);
        timer.setCoalesce(true);
    }

    /**
     * Starts the timer if it has been initialized.
     * If the timer is not initialized, an error message is printed to the error stream.
     */
    public static void startTimer() {
        if (timer == null) {
            System.err.println("Error: Cannot start timer. It is not initialized.");
            return;
        }
        timer.start();
    }

    /**
     * Stops the timer if it is running.
     * If the timer is not initialized, an error message is printed to the error stream.
     */
    public static void stopTimer() {
        if (timer == null) {
            System.err.println("Error: Cannot stop timer. It is not initialized.");
            return;
        }
        timer.stop();
    }

    /**
     * Checks if the timer is currently running.
     * If the timer is not initialized, a warning message is printed.
     *
     * @return {@code true} if the timer is running, {@code false} otherwise.
     */
    public static boolean isAlive() {
        if (timer == null) {
            System.err.println("Warning: Timer is not initialized.");
            return false;
        }
        return timer.isRunning();
    }

    /**
     * Checks if the timer is coalescing events.
     * Coalescing means that multiple pending timer events are merged into a single event
     * to prevent event flooding.
     * If the timer is not initialized, a warning message is printed.
     *
     * @return {@code true} if the timer is coalescing events, {@code false} otherwise.
     */
    public static boolean isCoalescing() {
        if (timer == null) {
            System.err.println("Warning: Timer is not initialized.");
            return false;
        }
        return timer.isCoalesce();
    }

    /**
     * Displays a dialog with either a JPanel or a message, depending on the configuration.
     * The dialog will be centered on the screen.
     *
     * @param title The title for the message dialog
     */
    private void showDialog(String title) {
        if(isToShowA$Panel){
            JOptionPane.showMessageDialog(null, this.panel, title, JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, this.message, title, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Sets whether the dialog should display a JPanel.
     *
     * @param isToShowPanel A boolean value indicating whether to show a JPanel
     */
    public void setIsToShowPanel(boolean isToShowPanel){
        this.isToShowA$Panel = isToShowPanel;
    }

    /**
     * Sets the JPanel to be displayed in the dialog.
     *
     * @param panel The JPanel to be displayed
     */
    public void setPanel(JPanel panel){
        this.panel = panel;
    }
}