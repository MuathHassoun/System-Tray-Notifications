package com.notifications.system_tray_notifications.system_tray;
import static com.notifications.system_tray_notifications.influence.DisplayMessages.printErrorMessage;

import com.notifications.system_tray_notifications.basics.AlarmSounds;
import com.notifications.system_tray_notifications.basics.Notifications;
import com.notifications.system_tray_notifications.influence.PlaySounds;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.awt.*;
import java.awt.event.ActionListener;
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
     * Initializes and displays a system tray notification with customizable options.
     * When the tray icon is clicked, it will decide whether to show a dialog with a message or a JPanel.
     *
     * @param notification_object An object of the `notifications` class containing the notification details
     * @param alarm_object An object of the `AlarmSounds` class for sound handling
     */
    public void CreateTrayIcon(Notifications notification_object, AlarmSounds alarm_object){
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported on this platform.");
            return;
        }
        FlatMacLightLaf.setup();
        SystemTray systemTray = SystemTray.getSystemTray();
        Image iconImage = Toolkit.getDefaultToolkit().getImage(notification_object.getIconPath());

        trayIcon = new TrayIcon(iconImage, notification_object.getAppTitle());
        trayIcon.setPopupMenu(createPopupMenu());
        trayIcon.addActionListener(
                e -> {
                    message = notification_object.getAlarmMessage();
                    showDialog(
                            notification_object.getAlarmTitle()
                    );
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
    }

    /**
     * Creates a popup menu for the system tray icon.
     * Adds an option to stop the program.
     *
     * @return A configured PopupMenu for the tray icon
     */
    private static PopupMenu createPopupMenu() {
    	PopupMenu popup = new PopupMenu();
        MenuItem stopItem = new MenuItem("Stop Program");
        stopItem.addActionListener(e -> System.exit(0));
        popup.add(stopItem);
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
        timer = new Timer(duration,
        e -> {
            PlaySounds.playSound(alarm_object.getSoundFileName());
            trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
        });
        timer.setRepeats(isRepeating);
        timer.start();
    }

    /**
     * Initializes the timer with a given delay, repetition mode, and action listener.
     * If the timer is already initialized, it stops and reinitialized it.
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