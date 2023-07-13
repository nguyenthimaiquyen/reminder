package model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.swing.JOptionPane;

public class Task implements Serializable{
	private static int AUTO_IDTASK = 1;
	private int idTask;
	private String task;
	private Date date;
	private int hour;
	private int minute;
	private Status status;
	private RemindType remindType;

	
	public Task() {
	}
		
	public Task(int idTask, String task, Date date, int hour, int minute, Status status, RemindType remindType) {
		this.idTask = idTask;
		this.task = task;
		this.date = date;
		this.hour = hour;
		this.minute = minute;
		this.status = status;
		this.remindType = remindType;
	}

	public int getIdTask() {
		return idTask;
	}

	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public RemindType getRemindType() {
		return remindType;
	}

	public void setRemindType(RemindType remindType) {
		this.remindType = remindType;
	}

	@Override
	public String toString() {
		return "Task [idTask=" + idTask + ", task=" + task + ", date=" + date + ", hour=" + hour + ", minute=" + minute
				+ ", status=" + status + ", remindType=" + remindType + "]";
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

}
