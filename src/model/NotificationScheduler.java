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
import javax.swing.JOptionPane;
import view.TaskListView;

public class NotificationScheduler extends Thread{
	public TaskListView view;	
	
	public NotificationScheduler(TaskListView view) {
		this.view = view;
	}	

	@Override
	public void run() {
		while(true) {
			Date currentTime = new Date();			
			List<Task> tasks = this.view.model.getTaskList();
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);				
				Date taskDateTime = task.getDate();
				int hour = task.getHour();
				int minute = task.getMinute();
				String date = taskDateTime.getDate()+"/"+taskDateTime.getMonth()+"/"+taskDateTime.getYear();
				String currentDate = currentTime.getDate()+"/"+currentTime.getMonth()+"/"+currentTime.getYear();
				
				if (date.equals(currentDate) &&
					task.getHour() == LocalTime.now().getHour() &&
					task.getMinute() == LocalTime.now().getMinute() ) {
					JOptionPane.showMessageDialog(view, tasks.get(i).getTask() + " is time up!");
				}
			}			
			
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
