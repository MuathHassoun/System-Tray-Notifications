package com.notifications.system_tray_notifications.basics;

/**
 * The {@code notifications} class represents a system tray notification configuration.
 * It includes properties such as the application title, icon path, alarm title,
 * alarm message, notification duration, and whether the notification repeats.
 *
 * @author Muath Hassoun
 */
public class notifications {
	private String appTitle;
	private String iconPath;
	private String alarmTitle;
	private String alarmMessage;
	private int duration;
	private boolean isRepeating;

	/**
	 * Constructs a new {@code notifications} object with the specified parameters.
	 *
	 * @param AppTitle     The title of the application sending the notification.
	 * @param IconPath     The file path to the notification icon.
	 * @param AlarmTitle   The title of the alarm/notification.
	 * @param AlarmMessage The message content of the alarm/notification.
	 * @param duration     The duration (in seconds) the notification will be displayed.
	 * @param isRepeating  A flag indicating whether the notification should repeat.
	 */
	public notifications(
			String AppTitle, String IconPath,
			String AlarmTitle, String AlarmMessage,
			int duration, boolean isRepeating
	){
		this.appTitle = AppTitle;
		this.iconPath = IconPath;
		this.alarmTitle = AlarmTitle;
		this.alarmMessage = AlarmMessage;
		this.duration = duration;
		this.isRepeating = isRepeating;
	}

	/**
	 * @return The title of the application sending the notification.
	 */
	public String getAppTitle(){
		return appTitle;
	}

	/**
	 * Sets the title of the application sending the notification.
	 *
	 * @param appTitle The application title to set.
	 */
	public void setAppTitle(String appTitle){
		this.appTitle = appTitle;
	}

	/**
	 * @return The file path to the notification icon.
	 */
	public String getIconPath(){
		return iconPath;
	}

	/**
	 * Sets the file path to the notification icon.
	 *
	 * @param iconPath The icon path to set.
	 */
	public void setIconPath(String iconPath){
		this.iconPath = iconPath;
	}

	/**
	 * @return The title of the alarm/notification.
	 */
	public String getAlarmTitle(){
		return alarmTitle;
	}

	/**
	 * Sets the title of the alarm/notification.
	 *
	 * @param alarmTitle The alarm title to set.
	 */
	public void setAlarmTitle(String alarmTitle){
		this.alarmTitle = alarmTitle;
	}

	/**
	 * @return The message content of the alarm/notification.
	 */
	public String getAlarmMessage(){
		return alarmMessage;
	}

	/**
	 * Sets the message content of the alarm/notification.
	 *
	 * @param alarmMessage The alarm message to set.
	 */
	public void setAlarmMessage(String alarmMessage){
		this.alarmMessage = alarmMessage;
	}

	/**
	 * @return The duration (in seconds) the notification will be displayed.
	 */
	public int getDuration(){
		return duration;
	}

	/**
	 * Sets the duration (in seconds) the notification will be displayed.
	 *
	 * @param duration The duration to set.
	 */
	public void setDuration(int duration){
		this.duration = duration;
	}

	/**
	 * @return A flag indicating whether the notification should repeat.
	 */
	public boolean getIsRepeating(){
		return isRepeating;
	}

	/**
	 * Sets the flag indicating whether the notification should repeat.
	 *
	 * @param isRepeating The repetition flag to set.
	 */
	public void setIsRepeating(boolean isRepeating){
		this.isRepeating = isRepeating;
	}
}