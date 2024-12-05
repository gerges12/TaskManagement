package com.services.HistoryService;

import java.util.List;

import com.models.History;
import com.models.Task;
import com.models.User;

public interface HistoryService {
	void addHistory(History history);

	List<History> getHistoryByTaskId(Long taskId);
    public void logTaskUpdate(Task task, String changeDescription, User changedBy) ;

}