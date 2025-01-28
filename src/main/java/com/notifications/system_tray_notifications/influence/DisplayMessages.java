package com.notifications.system_tray_notifications.influence;
import javax.swing.JOptionPane;

/**
 * The {@code DisplayMessages} class provides methods to display error messages
 * using a graphical user interface (GUI) dialog box.
 *
 * @author Muath Hassoun
 */
public class DisplayMessages {

    /**
     * Displays an error message dialog with details about the exception.
     *
     * @param e The exception whose details are to be displayed.
     *          The dialog will include the exception's class name and message.
     */
    public static void printErrorMessage(Throwable e){
        JOptionPane.showMessageDialog(
                null,
                "Error: " + e.getClass().getName() + "\nMessage: " + e.getMessage(),
                "Battery Level Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}