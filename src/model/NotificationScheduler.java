package model;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import view.TaskListView;

public class NotificationScheduler extends Thread {
    public TaskListView view;

    public NotificationScheduler(TaskListView view) {
        this.view = view;
    }

    @Override
    public void run() {
        while (true) {
            Date currentTime = new Date();
            List<Task> tasks = this.view.model.getTaskList();
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                Date taskDateTime = task.getDate();
                int hour = task.getHour();
                int minute = task.getMinute();
                
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String date = formatter.format(taskDateTime);
                String currentDate = formatter.format(currentTime);
//                int differenceTime = task.getMinute() - LocalTime.now().getMinute();
//                int oneMinute = 60000;
//                System.out.println(differenceTime); 
//                boolean mark = true;
                if (date.equals(currentDate) &&
                    task.getHour() == LocalTime.now().getHour() &&
//                    differenceTime < oneMinute && differenceTime > 0 && mark
                    task.getMinute() == LocalTime.now().getMinute()) {
                    JOptionPane.showMessageDialog(view, tasks.get(i).getTask() + " is almost time up, hurry up!");
                    if (SystemTray.isSupported()) {
                        try {
                            displayTray(tasks.get(i));
                        } catch (AWTException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("System tray not supported!");
                    }
//                    mark = false;
                    
                }              
                
            }

            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void displayTray(Task task) throws AWTException {

        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage(task.getTask() + " is almost time up, hurry up!", "Notification of due your task", MessageType.INFO);
    }


}