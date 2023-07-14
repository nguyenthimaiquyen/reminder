package model;

import java.util.ArrayList;
import java.util.List;

public class TaskListModel {
	private List<Task> taskList;
	private String choice;
	private String fileName;

	public TaskListModel(List<Task> taskList) {
		this.taskList = taskList;
	}
	
	public TaskListModel() {
		this.taskList = new ArrayList<Task>();
		this.choice = "";
		this.fileName = "";
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	@Override
	public String toString() {
		return "TaskListModel [taskList=" + taskList + "]";
	}
	
	public void insert(Task task) {
		this.taskList.add(task);
	}
	
	public void delete(int index) {
		this.taskList.remove(index);
	}
	
	public void add(Task task) {
		this.taskList.add(task);
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}	
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean checkExist(Task checkTask) {
		for (Task task : taskList) {
			if (task.getIdTask() == checkTask.getIdTask()) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
