package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JOptionPane;

import view.TaskListView;

public class TaskListController implements Action{
	public TaskListView view;

	public TaskListController(TaskListView view) {
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cm = e.getActionCommand();		
		
		if (cm.equals("Create")) {
			JOptionPane.showMessageDialog(view, "Please enter task's name is maximum 255 characters!");
			this.view.deleteForm();
			this.view.model.setChoice("Create");
		} else if (cm.equals("Save")) {
			try {
				this.view.processAddTask();
			} catch (Exception e2) {
				e2.printStackTrace();
			}			
		} else if (cm.equals("Update")) {
			this.view.showTask();
		} else if (cm.equals("Delete")) {
			this.view.deleteTask();
		} else if (cm.equals("Cancel")) {
			this.view.deleteForm();
		} else if (cm.equals("Search")) {
			this.view.findTask();
		} else if (cm.equals("Load data")) {
			this.view.loadDataTable();
		} else if (cm.equals("About me")) {
			this.view.showAbout();
		} else if (cm.equals("Exit")) {
			this.view.exitProgram();
		} else if (cm.equals("Save file")) {
			this.view.saveFile();
			JOptionPane.showMessageDialog(view, "You have successfully saved the file!");
		} else if (cm.equals("Open")) {
			this.view.openFile();
		} else if (cm.equals("Export excel")) {
			this.view.exportToExcel();
		}
	}

	@Override
	public Object getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putValue(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnabled(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub		
	}
	
}
