package banquemisr.challenge05.services.HistoryService;

import java.util.List;

import banquemisr.challenge05.models.History;
import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.models.User;

public interface HistoryService {
	void addHistory(History history);

	List<History> getHistoryByTaskId(Long taskId);
    public void logTaskUpdate(Task task, String changeDescription, User changedBy) ;

}