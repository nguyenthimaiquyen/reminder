package model;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import view.TaskListView;

public class TrayIconTask {
public TaskListView view;	
	
	public TrayIconTask(TaskListView view) {
		this.view = view;
	}	
	
	public void displayTray() throws AWTException {
		Date currentTime = new Date();			
		List<Task> tasks = this.view.model.getTaskList();
		for (int i = 0; i < tasks.size(); i++) {
			System.out.println("vào vòng for chưa");
			Task task = tasks.get(i);				
			Date taskDateTime = task.getDate();
			int hour = task.getHour();
			int minute = task.getMinute();
			String date = taskDateTime.getDate()+"/"+taskDateTime.getMonth()+"/"+taskDateTime.getYear();
			String currentDate = currentTime.getDate()+"/"+currentTime.getMonth()+"/"+currentTime.getYear();
			
			if (date.equals(currentDate) &&
				task.getHour() == LocalTime.now().getHour() &&
				task.getMinute() == LocalTime.now().getMinute() ) {
				System.out.println("vào vòng if chưa nhỉ");
	
				//Obtain only one instance of the SystemTray object
		        SystemTray tray = SystemTray.getSystemTray();

		        //If the icon is a file
		        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
		        //Alternative (if the icon is on the classpath):
		        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

		        TrayIcon trayIcon = new TrayIcon(image, "Tray");
		        //Let the system resize the image if needed
		        trayIcon.setImageAutoSize(true);
		        //Set tooltip text for the tray icon
		        trayIcon.setToolTip("System tray icon demo");
		        tray.add(trayIcon);

		        trayIcon.displayMessage(tasks.get(i).getTask() + " is time up!", "Notification of due your task", MessageType.INFO);
				System.out.println("task vào đây rồi nhé!");
			}
		}		
        
    }
}
