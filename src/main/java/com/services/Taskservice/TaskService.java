package com.services.Taskservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.dto.TaskDTO;
import com.models.Task;
import com.specification.TaskSpecification;

public interface TaskService {
	Task createTask(TaskDTO taskDTO);

	List<Task> getAllTasks();

	Optional<Task> getTaskById(Long id);

	ResponseEntity<Task> updateTask(Long id, Task task);

	void deleteTask(Long id);

	public List<Task> searchTasks(String title, String description, String status, String dueDate);

	ResponseEntity<Page<Task>> getTasksWithPagination(int page, int size);

	

	}
