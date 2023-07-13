package test;

import javax.swing.UIManager;

import model.NotificationScheduler;
import view.TaskListView;

public class Main {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new TaskListView();
			NotificationScheduler noti = new NotificationScheduler();
			noti.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
