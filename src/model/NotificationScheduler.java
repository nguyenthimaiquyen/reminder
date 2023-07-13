package model;

import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import view.TaskListView;

public class NotificationScheduler extends Thread{
	public TaskListView view;	
	
	public NotificationScheduler(TaskListView view) {
		this.view = view;
	}	
	
	public NotificationScheduler() {
	}
	
	@Override
	public void run() {
		while(true) {
			Date currentTime = new Date();
			TaskListModel model = new TaskListModel();
			List<Task> tasks = model.getTaskList();			
			//có task nào có thời gian hết hạn là currentTime hoặc cách currentTime dưới 1 phút thì thông báo
			for (int i = 0; i < tasks.size(); i++) {
				System.out.println("code vào đây chưa");
				Task task = tasks.get(i);
				Date taskDateTime = task.getDate();
				long timeDifference = taskDateTime.getTime() - currentTime.getTime();
				long oneMinute = 60000;
				if (taskDateTime.equals(currentTime) || timeDifference <= oneMinute ) {
					JOptionPane.showMessageDialog(view, tasks.get(i).getTask() + " is time up!");
					this.view.comboBox_status.setSelectedIndex(2);
					System.out.println("code chạy ngon chưa");
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
