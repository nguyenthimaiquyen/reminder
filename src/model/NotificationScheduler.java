package model;

import java.time.LocalDate;
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
			
			//có task nào có thời gian hết hạn là currentTime hoặc cách currentTime dưới 1 phút thì thông báo
			for (int i = 0; i < tasks.size(); i++) {
				Task task = tasks.get(i);				
				Date taskDateTime = task.getDate();
				int hour = task.getHour();
				int minute = task.getMinute();
//				long timeDifference = taskDateTime.getTime() - currentTime.getTime();				
//				long oneMinute = 60000;
				if (taskDateTime.equals(currentTime) &&
					task.getHour() == LocalTime.now().getHour() &&
					task.getMinute() == LocalTime.now().getMinute() ) {
					JOptionPane.showMessageDialog(view, tasks.get(i).getTask() + " is time up!");
					System.out.println("task vào đây rồi nhé!");
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
