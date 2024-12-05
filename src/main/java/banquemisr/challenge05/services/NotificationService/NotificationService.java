package banquemisr.challenge05.services.NotificationService;

import java.util.List;

import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.User;

public interface NotificationService {

	void sendUpdateNotification(Task task ,  User changedBy); // Public by default

	void sendNotifications();

}
