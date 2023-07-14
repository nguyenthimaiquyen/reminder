package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class RemindType implements Serializable{
	private String remindType;

	public RemindType(String remindType) {
		this.remindType = remindType;
	}

	public RemindType() {
	}

	public String getRemindType() {
		return remindType;
	}

	public void setRemindType(String remindType) {
		this.remindType = remindType;
	}

	@Override
	public String toString() {
		return "RemindType [remindType=" + remindType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(remindType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemindType other = (RemindType) obj;
		return Objects.equals(remindType, other.remindType);
	}
	
	public static ArrayList<RemindType> getRemindTypeList() {
		String[] arr_remindType = {"Never"};
		ArrayList<RemindType> remindTypeList = new ArrayList<>();
		for (String remindType : arr_remindType) {
			RemindType remind = new RemindType(remindType);
			remindTypeList.add(remind);
		}
		return remindTypeList;
	}

	public static RemindType getRemindTypeByIndex(int remindType2) {
		return RemindType.getRemindTypeList().get(0);
	}

	public static RemindType getRemindTypeByName(String remindChosen) {
		ArrayList<RemindType> remindTypeList = RemindType.getRemindTypeList();
		for (RemindType remind : remindTypeList) {
			if (remind.remindType.equals(remindChosen)) {
				return remind;
			}
		}
		return null;
	}
	

}
