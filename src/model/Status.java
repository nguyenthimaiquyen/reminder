package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Status implements Serializable{
	private String status;

	public Status(String status) {
		this.status = status;
	}

	public Status() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Status [status=" + status + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		return Objects.equals(status, other.status);
	}
	
	public static ArrayList<Status> getStatusList() {
		String[] arr_Status = {"Continue", "Out of Date"};
		ArrayList<Status> statusList = new ArrayList<>();
		for (String status : arr_Status) {
			Status stt = new Status(status);
			statusList.add(stt);
		}
		return statusList;
	}

	public static Status getStatusByIndex(int status2) {
		return Status.getStatusList().get(status2);
	}


	public static Status getStatusByName(String statusChosen) {
		ArrayList<Status> statusList = Status.getStatusList();
		for (Status stt : statusList) {
			if (stt.status.equals(statusChosen)) {
				return stt;
			}
		}
		return null;
	}
	
	
	
	
	
	
	
}
