package com.services.NotificationService;

import java.util.List;

import com.models.Task;
import com.models.User;

public interface NotificationService {

	void sendUpdateNotification(Task task ,  User changedBy); // Public by default

	void sendNotifications();

}
