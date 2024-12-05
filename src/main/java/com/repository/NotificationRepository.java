package com.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientIdAndIsReadFalse(Long userId); // Find unread notifications for a specific user
}

