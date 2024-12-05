package com.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.models.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByTaskId(Long taskId); // Retrieve history entries for a specific task
}
