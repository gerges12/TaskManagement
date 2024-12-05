package com.services.NotificationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.models.Notification;
import com.models.Task;
import com.models.User;
import com.repository.NotificationRepository;
import com.repository.TaskRepository;
import com.security.services.UserDetailsServiceImpl;
import com.services.HistoryService.HistoryService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    private HistoryService historyService;

    @Value("${email.sender}")
    private String senderEmail;

    // Method to send a notification when a task is updated
    public void sendUpdateNotification(Task task, User changedBy) {
        try {
            // Fetch recipient email from assigned user
            String recipientEmail = task.getAssignedTo().getEmail();
            String subject = "Task Updated: " + task.getTitle();
            String text = String.format("Dear %s,\n\nThe task '%s' has been updated on %s. Please check the details.\n\nBest regards,\nTask Management System",
                    task.getAssignedTo().getUsername(), task.getTitle(), LocalDateTime.now());

            // Send the email notification then saving notification to database
            sendMailAndNotification(recipientEmail, subject, text);

            // Log the history of task update with a simple message
            String changeDescription = "Task '" + task.getTitle() + "' was updated on " + LocalDateTime.now();
            historyService.logTaskUpdate(task, changeDescription, changedBy);

        } catch (Exception e) {
            System.err.println("Error sending task update notification: " + e.getMessage());
        }
    }


    // Method to send notifications for tasks that have upcoming deadlines within the next 7 days
    @Scheduled(cron = "0 0 9 * * ?")  // This cron expression will run every day at 9 AM
    public void sendNotifications() {
        List<Task> tasks = getTasksWithUpcomingDeadlines();
        for (Task task : tasks) {
            try {
                // Send a reminder for tasks due within the next 7 days
                String recipientEmail = task.getAssignedTo().getEmail();
                String subject = "Task Deadline Reminder: " + task.getTitle();
                String text = String.format("Dear %s,\n\nThis is a reminder that the task '%s' is due on %s. Please make sure to complete it on time.\n\nBest regards,\nTask Management System",
                        task.getAssignedTo().getUsername(),
                        task.getTitle(),
                        task.getDueDate());
                sendMailAndNotification(recipientEmail, subject, text);
            } catch (Exception e) {
                System.err.println("Error sending task reminder notification: " + e.getMessage());
            }
        }
    }

    // Method to retrieve tasks that have a due date within the next 7 days
    public List<Task> getTasksWithUpcomingDeadlines() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(7); // Getting tasks that are due in the next 7 days
        return taskRepository.findByDueDateBetween(today, tomorrow);
    }

    // Common method to send a simple email message and save a notification
    private void sendMailAndNotification(String to, String subject, String text) {
        try {
            // Create and configure the email message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            
            // Send the email
            emailSender.send(message);

            // Save the notification in the database
            Notification notification = new Notification();
            notification.setMessage(subject + " - " + text);
            notification.setRecipient(userDetailsService.getUserByEmail(to)); // Retrieve the user by email and assign to notification
            notification.setTimestamp(LocalDateTime.now());
            notification.setRead(false); // Default as unread
            notificationRepository.save(notification);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

   

	
}
