package banquemisr.challenge05.controllers.Notification;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banquemisr.challenge05.services.NotificationService.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public String sendNotifications() {
        try {
        	notificationService.sendNotifications();
            return "Notifications sent successfully!";
        } catch (Exception e) {
            return "Error occurred while sending notifications: " + e.getMessage();
        }
    }
}