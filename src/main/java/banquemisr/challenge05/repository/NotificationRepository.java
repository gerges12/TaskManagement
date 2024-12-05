package banquemisr.challenge05.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import banquemisr.challenge05.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdAndIsReadFalse(Long userId); // Find unread notifications for a specific user
}

