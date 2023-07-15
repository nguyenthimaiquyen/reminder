package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.NotificationScheduler;
import model.RemindType;
import model.Status;
import model.Task;
import model.TaskListModel;
import model.TrayIconTask;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;

import java.awt.Font;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.AWTException;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import controller.TaskListController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JDateChooser;



public class TaskListView extends JFrame {

	public JPanel contentPane;
	public TaskListModel model;
	public JTextField textField_TaskName_search;
	public JTable table;
	public JTextField textField_taskID;
	public JTextField textField_taskName;
	public JComboBox comboBox_status;
	public JComboBox comboBox_remindType;
	public JComboBox comboBox_status_search;
	public JSpinner hourSpinner;
	public JSpinner minuteSpinner;
	public JDateChooser dateChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws AWTException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					TaskListView taskListView = new TaskListView();
//					taskListView.openFile();
					taskListView.setVisible(true);
					NotificationScheduler noti = new NotificationScheduler(taskListView);
					noti.start();
					
					if (SystemTray.isSupported()) {
						TrayIconTask td = new TrayIconTask(taskListView);
			            td.displayTray();
			        } else {
			            System.err.println("System tray not supported!");
			        }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TaskListView() {
		this.model = new TaskListModel();
		this.setTitle("Manage Task");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 1008, 677);
		this.setLocationRelativeTo(null);
		
		//set icon
		URL urlIcon = TaskListView.class.getResource("tasks-icon.png");
		Image img = Toolkit.getDefaultToolkit().createImage(urlIcon);
		this.setIconImage(img);
		
		Action action = new TaskListController(this);
		
		//set menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuTask = new JMenu("Task");
		menuTask.setForeground(new Color(0, 0, 0));
		menuTask.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBar.add(menuTask);
		
		JMenuItem menuOpen = new JMenuItem("Open");
		menuOpen.addActionListener(action);
		menuOpen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuTask.add(menuOpen);
		
		JMenuItem menuExport = new JMenuItem("Export excel");
		menuExport.addActionListener(action);
		menuExport.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuTask.add(menuExport);
		
		JSeparator separator = new JSeparator();
		menuTask.add(separator);
		
		JMenuItem menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(action);
		menuExit.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuTask.add(menuExit);
		
		JMenu menuAbout = new JMenu("About");
		menuAbout.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuAbout.setForeground(new Color(0, 0, 0));
		menuBar.add(menuAbout);
		
		JMenuItem menuAboutMe = new JMenuItem("About me");
		menuAboutMe.addActionListener(action);
		menuAboutMe.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuAbout.add(menuAboutMe);
		
		//set giao diện của chương trình
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));	
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStatus_search = new JLabel("Status");
		lblStatus_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStatus_search.setBounds(73, 13, 60, 43);
		contentPane.add(lblStatus_search);
		
		JLabel lblTaskName_search = new JLabel("Task name");
		lblTaskName_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTaskName_search.setBounds(359, 13, 98, 43);
		contentPane.add(lblTaskName_search);
		
		textField_TaskName_search = new JTextField();
		textField_TaskName_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_TaskName_search.setColumns(10);
		textField_TaskName_search.setBounds(467, 13, 156, 43);
		contentPane.add(textField_TaskName_search);
		
		JButton btn_loadData = new JButton("Load data");
		btn_loadData.addActionListener(action);
		btn_loadData.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_loadData.setBounds(799, 13, 144, 43);
		contentPane.add(btn_loadData);
		
		comboBox_status_search = new JComboBox();
		comboBox_status_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ArrayList<Status> statusList = Status.getStatusList();
		comboBox_status_search.addItem("--Select status--");
		for (Status status : statusList) {
			comboBox_status_search.addItem(status.getStatus());
		}
		
		comboBox_status_search.setBounds(144, 13, 167, 43);
		contentPane.add(comboBox_status_search);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(31, 69, 938, 2);
		contentPane.add(separator_1);
		
		JLabel lblTaskList = new JLabel("Task List");
		lblTaskList.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTaskList.setBounds(31, 66, 129, 43);
		contentPane.add(lblTaskList);
		
		table = new JTable();
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setBackground(new Color(255, 255, 255));
		table.setSurrendersFocusOnKeystroke(true);
		table.setForeground(new Color(0, 0, 0));
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Task ID", "Task Name", "Date Deadline", "Time Deadline", "Status", "Remind Type"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(209);
		table.getColumnModel().getColumn(1).setMinWidth(21);
		table.getColumnModel().getColumn(2).setPreferredWidth(124);
		table.getColumnModel().getColumn(3).setPreferredWidth(87);
		table.getColumnModel().getColumn(4).setPreferredWidth(95);
		table.getColumnModel().getColumn(5).setPreferredWidth(91);
		table.setFont(table.getFont().deriveFont(table.getFont().getSize() + 8f));
		table.setRowHeight(25);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(31, 107, 938, 194);
		contentPane.add(scrollPane);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(31, 321, 938, 2);
		contentPane.add(separator_1_1);
		
		JLabel lblTaskInformation = new JLabel("Task Information");
		lblTaskInformation.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblTaskInformation.setBounds(34, 321, 227, 43);
		contentPane.add(lblTaskInformation);
		
//		textField_taskID = new JTextField();
//		textField_taskID.setFont(new Font("Tahoma", Font.PLAIN, 18));
//		textField_taskID.setColumns(10);
//		textField_taskID.setBounds(154, 366, -1, 2);
//		contentPane.add(textField_taskID);
		
		JLabel Label_taskName = new JLabel("Task Name");
		Label_taskName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Label_taskName.setBounds(45, 390, 89, 43);
		contentPane.add(Label_taskName);
		
		textField_taskName = new JTextField();
		textField_taskName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_taskName.setColumns(10);
		textField_taskName.setBounds(155, 390, 249, 43);
		contentPane.add(textField_taskName);
		
		JLabel lblDateDeadline = new JLabel("Due date");
		lblDateDeadline.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDateDeadline.setBounds(55, 443, 96, 43);
		contentPane.add(lblDateDeadline);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStatus.setBounds(555, 419, 68, 43);
		contentPane.add(lblStatus);
		
		comboBox_status = new JComboBox();
		ArrayList<Status> statuses = Status.getStatusList();
		comboBox_status.addItem(statuses.get(0).getStatus());
		
		comboBox_status.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_status.setBounds(634, 419, 249, 43);
		contentPane.add(comboBox_status);
		
		JLabel lblRemindType = new JLabel("Remind Type");
		lblRemindType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRemindType.setBounds(505, 472, 119, 43);
		contentPane.add(lblRemindType);
		
		comboBox_remindType = new JComboBox();
		ArrayList<RemindType> remindTypeList = RemindType.getRemindTypeList();
		for (RemindType remindType : remindTypeList) {
			comboBox_remindType.addItem(remindType.getRemindType());
		}
		
		comboBox_remindType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBox_remindType.setBounds(635, 472, 248, 43);
		contentPane.add(comboBox_remindType);
		
		JLabel lblTimeDeadline = new JLabel("Due time");
		lblTimeDeadline.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTimeDeadline.setBounds(537, 366, 76, 43);
		contentPane.add(lblTimeDeadline);
		
		JButton btn_create = new JButton("Create");
		btn_create.addActionListener(action);
		btn_create.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_create.setBounds(65, 547, 108, 43);
		contentPane.add(btn_create);
		
		JButton btn_delete = new JButton("Delete");
		btn_delete.addActionListener(action);
		btn_delete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_delete.setBounds(243, 547, 108, 43);
		contentPane.add(btn_delete);
		
		JButton btn_update = new JButton("Update");
		btn_update.addActionListener(action);
		btn_update.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_update.setBounds(422, 547, 108, 43);
		contentPane.add(btn_update);
		
		JButton btn_save = new JButton("Save");
		btn_save.addActionListener(action);
		btn_save.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_save.setBounds(606, 547, 108, 43);
		contentPane.add(btn_save);
		
		JButton btn_complete = new JButton("Complete");
		btn_complete.addActionListener(action);
		btn_complete.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_complete.setBounds(790, 547, 108, 43);
		contentPane.add(btn_complete);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(31, 535, 938, 2);
		contentPane.add(separator_1_2);
		
		JButton btn_search = new JButton("Search");
		btn_search.addActionListener(action);
		btn_search.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btn_search.setBounds(690, 13, 89, 43);
		contentPane.add(btn_search);
		
		dateChooser = new JDateChooser();
		dateChooser.getCalendarButton().setFont(new Font("Tahoma", Font.PLAIN, 18));
		dateChooser.setDateFormatString("dd/MM/yyyy");
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		dateChooser.setBounds(155, 443, 249, 43);
		contentPane.add(dateChooser);
		
		//set giờ phút
		hourSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
		hourSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		hourSpinner.setBounds(684, 366, 53, 43);
		contentPane.add(hourSpinner);
		
		JLabel lblHour = new JLabel("Hour:");
		lblHour.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHour.setBounds(634, 366, 53, 43);
		contentPane.add(lblHour);		
		
		minuteSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
	    minuteSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
	    minuteSpinner.setBounds(830, 366, 53, 43);		
        contentPane.add(minuteSpinner);
       
        JLabel lblMinute = new JLabel("Minute:");
        lblMinute.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblMinute.setBounds(763, 366, 68, 43);
		contentPane.add(lblMinute);     
        
        		
		this.setVisible(true);
	}

	//xóa dữ liệu task
		public void deleteForm() {
//			textField_taskID.setText("");
			textField_taskName.setText("");
			dateChooser.setDateFormatString("");
			hourSpinner.setValue(0);
			minuteSpinner.setValue(0);
//			comboBox_status.setSelectedIndex(0);
//			comboBox_remindType.setSelectedIndex(0);		
		}
		
		//hàm thêm task vào bảng dữ liệu
		public void addTaskInTable(Task task) {
			DefaultTableModel model_table = (DefaultTableModel) table.getModel();
			model_table.addRow( new Object[] {
					task.getIdTask()+"",
					task.getTask(),
					task.getDate().getDate() + "/" + (task.getDate().getMonth()+1) + "/" +(task.getDate().getYear()+1900),
					task.getHour()+":"+task.getMinute(),
					task.getStatus().getStatus(),
					task.getRemindType().getRemindType()});
		}
		
		//hàm thêm task hoặc cập nhật task
		public void addOrUpdateTask(Task task) {
			
			DefaultTableModel model_table = (DefaultTableModel) table.getModel();
			if(!this.model.checkExist(task)) {
				this.model.insert(task);
				this.addTaskInTable(task);
			} else {				
				Task taskChoosen = getTaskChosen();				
				List<Task> taskList = this.model.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					if (taskChoosen.getIdTask() == taskList.get(i).getIdTask()) {
						this.model.delete(i);
						this.model.add(task);
						System.out.println("không vào đây à");
					}
				}	
				int i = 0;
				int rowCount = model_table.getRowCount();				
				for (i = 0; i < rowCount; i++) {
					String id = model_table.getValueAt(i, 0)+"";
					if(id.equals(taskChoosen.getIdTask()+"")) {
						model_table.setValueAt(taskChoosen.getIdTask()+"", i, 0);
						model_table.setValueAt(task.getTask(), i, 1);
						model_table.setValueAt(task.getDate().getDate() + "/" + (task.getDate().getMonth()+1) + "/" +(task.getDate().getYear()+1900), i, 2);
						model_table.setValueAt(task.getHour()+":"+task.getMinute(), i, 3);
						model_table.setValueAt(task.getStatus().getStatus()+"", i, 4);
						model_table.setValueAt(task.getRemindType().getRemindType()+"", i, 5);	
						break;
					}
				}
				model_table.removeRow(i);
			}		
		}
		
		//hàm lấy dữ liệu từ các trường và tạo thành 1 đối tượng task để thêm task
		public void processAddTask() {
//			int idTask = Integer.valueOf(this.textField_taskID.getText());
			int idTask = new Task().getIdTask();
			String taskName = this.textField_taskName.getText();
			Date date = this.dateChooser.getDate();
			int hour = Integer.valueOf(this.hourSpinner.getValue()+"");
			int minute = Integer.valueOf(this.minuteSpinner.getValue()+"");
//			int status = this.comboBox_status.getSelectedIndex() - 1;
			Status stt = Status.getStatusByIndex(0);
//			int remindType = this.comboBox_remindType.getSelectedIndex() - 1;
			RemindType remind = RemindType.getRemindTypeByIndex(0);
			
			Task task = new Task(idTask, taskName, date, hour, minute, stt, remind);		
			
			while(true) {
				if (taskName.length() > 255) {
					JOptionPane.showMessageDialog(this, "Please enter task's name is maximum 255 characters!");
				} else {
					break;
				}				
			}			
			this.addOrUpdateTask(task);		
		}		
		
		//hàm lấy ra task mà người dùng chọn để cập nhật
		public Task getTaskChosen() {
			DefaultTableModel model_table = (DefaultTableModel) table.getModel();
			int i_row = table.getSelectedRow();
			
			int idTask = Integer.valueOf(model_table.getValueAt(i_row, 0)+"");
			String taskName = model_table.getValueAt(i_row, 1) + "";
			String s_date = model_table.getValueAt(i_row, 2)+"";
			Date date = new Date(s_date);
			
			String[] time = (model_table.getValueAt(i_row, 3) + "").split(":");
			int hour = Integer.valueOf(time[0]);
			int minute = Integer.valueOf(time[1]);
			
			Status stt = Status.getStatusByName(model_table.getValueAt(i_row, 4)+"");
			RemindType remind = RemindType.getRemindTypeByName(model_table.getValueAt(i_row, 5)+"");
			
			Task task = new Task(idTask, taskName, date, hour, minute, stt, remind);
			return task;
		}
		
		//hàm hiển thị thông tin task mà người dùng đã chọn lên các trường dữ liệu để cập nhật
		public void showTask() {	
			Task task = getTaskChosen();
			
//			this.textField_taskID.setText(task.getIdTask() + "");
			this.textField_taskName.setText(task.getTask());
			this.dateChooser.setDate(task.getDate());
			this.hourSpinner.setValue(task.getHour());
			this.minuteSpinner.setValue(task.getMinute());
//			this.comboBox_status.setSelectedItem(task.getStatus().getStatus());
//			this.comboBox_remindType.setSelectedItem(task.getRemindType().getRemindType());		
		}

		//hàm xóa task
		public void deleteTask() {
			DefaultTableModel model_table = (DefaultTableModel) table.getModel();
			int i_row = table.getSelectedRow();
			int choice = JOptionPane.showConfirmDialog(this, "Are you sure want to delete your task?");		
			if (choice == JOptionPane.YES_OPTION) {
				Task task = getTaskChosen();
				List<Task> taskList = this.model.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					if (task.getIdTask() == taskList.get(i).getIdTask()) {
						this.model.delete(i);
					}
				}				
				model_table.removeRow(i_row);
			}
		}
		

		//hàm tìm task
		public void findTask() {
			//gọi hàm load lại toàn bộ dữ liệu task
			this.loadDataTable();
			
			//thực hiện tìm kiếm task
			int status = this.comboBox_status_search.getSelectedIndex()-1;
			String TaskNameSearch = this.textField_TaskName_search.getText();
			DefaultTableModel model_table = (DefaultTableModel) table.getModel();
			int rowCount = model_table.getRowCount();
			
			Set<Integer> idTaskNeedToDelete = new TreeSet<Integer>();
			if(status >= 0) {
				Status sttChosen = Status.getStatusByIndex(status);
				for (int i = 0; i < rowCount; i++) {
					String statusName = model_table.getValueAt(i, 4)+ "";
					String id = model_table.getValueAt(i, 0)+"";
					if(!statusName.equals(sttChosen.getStatus())) {
						idTaskNeedToDelete.add(Integer.valueOf(id));
					}
				}
			}
			if (TaskNameSearch.length() > 0) {
				for (int i = 0; i < rowCount; i++) {
					String name = model_table.getValueAt(i, 1)+"";
					//String id = model_table.getValueAt(i, 0)+"";
					if(!name.equals(TaskNameSearch)) {
						idTaskNeedToDelete.add(Integer.valueOf(model_table.getValueAt(i, 0)+""));
					}
				}
			}
			for (Integer IdNeedToDelete : idTaskNeedToDelete) {
				rowCount = model_table.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					String idInTable = model_table.getValueAt(i, 0)+"";
					if(idInTable.equals(IdNeedToDelete.toString())) {
						try {
							model_table.removeRow(i);
						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}		
		}
		
		//hàm load lại toàn bộ dữ liệu task đã có
		public void loadDataTable() {
			while(true) {
				DefaultTableModel model_table = (DefaultTableModel) table.getModel();
				int rowCount = model_table.getRowCount();
				if (rowCount == 0) {
					break;
				} else {
					try {
						model_table.removeRow(0);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}		
			for (Task task : this.model.getTaskList()) {
				this.addTaskInTable(task);
			}
		}

		//hàm hiển thị thông tin về chương trình
		public void showAbout() {
			JOptionPane.showMessageDialog(this, "The software is to manage your tasks with version 1.0!");
		}

		//hàm thoát chương trình
		public void exitProgram() {
			int response = JOptionPane.showConfirmDialog(this, 
					"Are you sure to exit the program?", 
					"Exit", 
					JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		
		//hàm lưu dữ liệu vào file
		private void saveProcess(String path) {
			try {
				this.model.setFileName(path);
				FileOutputStream fos = new FileOutputStream(path);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				for (Task task : this.model.getTaskList()) {
					oos.writeObject(task);
				}
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//hàm hiển thị thông báo để lưu file
		public void saveFile() {
			if (this.model.getFileName().length() > 0) {
				saveProcess(this.model.getFileName());
			} else {
				JFileChooser fc = new JFileChooser();
				int returnValue = fc.showSaveDialog(this);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					saveProcess(file.getAbsolutePath());
				}
			}
		}	
		
		//hàm mở file có sẵn
		public void openProcess(File file) {
			ArrayList<Task> list = new ArrayList<Task>();
			try {
				this.model.setFileName(file.getAbsolutePath());
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Task task = null;
				while((task = (Task) ois.readObject()) != null) {
					list.add(task);
				}			
				ois.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			this.model.setTaskList(list);
		}

		//hàm hiển thị thông báo mở file
		public void openFile() {
			JFileChooser fc = new JFileChooser();
			int returnValue = fc.showOpenDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				openProcess(file);
				loadDataTable();
			}
		}
		
		//hàm xuất ra file excel
		public void exportToExcel() {			
			//tạo 1 workbook
			Workbook workbook = new XSSFWorkbook();
			//tạo 1 sheet mới
			org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Task Data");
			//tạo tiêu đề cột
			Row headerRow = sheet.createRow(0);
			String[] headers = {"ID Task", "Task", "Date", "Time", "Status", "Remind Type"};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}
			//xuất dữ liệu từ danh sách task vào file excel
			List<Task> taskList = this.model.getTaskList();
			int rowNum = 1;
			for (Task task : taskList) {
				Row row = sheet.createRow(rowNum++);
				
				Cell idCell = row.createCell(0);
				idCell.setCellValue(task.getIdTask());
				
				Cell taskCell = row.createCell(1);
				taskCell.setCellValue(task.getTask());
				
				Cell dateCell = row.createCell(2);
				dateCell.setCellValue(task.getDate().getDate()+"/"+(task.getDate().getMonth()+1)+"/"+(task.getDate().getYear()+1900));
				
				Cell hourCell = row.createCell(3);
				hourCell.setCellValue(task.getHour()+":"+task.getMinute());
				
				Cell statusCell = row.createCell(4);
				statusCell.setCellValue(task.getStatus().getStatus());
				
				Cell remindTypeCell = row.createCell(5);
				remindTypeCell.setCellValue(task.getRemindType().getRemindType());
			}
			//tự động điều chỉnh độ rộng cột
			for (int i = 0; i < headers.length; i++) {
				sheet.autoSizeColumn(i);
			}
			//lưu workbook thành file
			JFileChooser fc = new JFileChooser();
			int returnValue = fc.showSaveDialog(this);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (file != null) {
					try (FileOutputStream fos = new FileOutputStream(file.getAbsolutePath())) {
						workbook.write(fos);	
						JOptionPane.showMessageDialog(this, "You have successfully export to excel file!");
						workbook.close();
					} catch (IOException  e) {
						e.printStackTrace();
					}
				}			
			}		
		}

		//hàm cập nhật task đã hoàn thành
		public void modifyTask() {
			DefaultTableModel model_table = (DefaultTableModel) table.getModel();
			int i_row = table.getSelectedRow();
			int choice = JOptionPane.showConfirmDialog(this, "Has your task completed?");		
			if (choice == JOptionPane.YES_OPTION) {
				Task task = getTaskChosen();
				List<Task> taskList = this.model.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					if (task.getIdTask() == taskList.get(i).getIdTask()) {
						model_table.setValueAt(Status.getStatusByIndex(1), i, 4);
						taskList.get(i).setStatus(Status.getStatusByIndex(1));
					}
				}				
			}
		}

		
	
	
	
}
