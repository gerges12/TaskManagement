package com.services.HistoryService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.models.History;
import com.models.Task;
import com.models.User;
import com.repository.HistoryRepository;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public void addHistory(History history) {
        history.setTimestamp(LocalDateTime.now());
        historyRepository.save(history);
    }

    @Override
    public List<History> getHistoryByTaskId(Long taskId) {
        return historyRepository.findByTaskId(taskId);
    }
    
    
    
    
    public void logTaskUpdate(Task task, String changeDescription, User changedBy) {
        History history = new History();
        history.setTask(task);
        history.setChangeDescription(changeDescription);
        history.setTimestamp(LocalDateTime.now());
        history.setChangedBy(changedBy);
        historyRepository.save(history);
    }
    
    
    
    
    
}