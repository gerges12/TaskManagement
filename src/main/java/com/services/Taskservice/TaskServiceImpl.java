package com.services.Taskservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dto.TaskDTO;
import com.exception.TaskNotFoundException;
import com.models.Task;
import com.models.User;
import com.repository.TaskRepository;
import com.repository.UserRepository;
import com.services.NotificationService.NotificationService;
import com.specification.TaskSpecification;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    @Autowired
	private  TaskRepository taskRepository;
    @Autowired
	private  NotificationService notificationService;
    @Autowired
	private  UserRepository userRepository;

	@Override
	public Task createTask(TaskDTO taskDTO) {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		User loggedInUser = userRepository.findByUsername(loggedInUsername)
				.orElseThrow(() -> new TaskNotFoundException("Logged-in user not found"));

		User assignedToUser = userRepository.findById(taskDTO.getAssignedToId())
				.orElseThrow(() -> new TaskNotFoundException("Assigned user not found"));

		Task task = new Task();
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setDueDate(taskDTO.getDueDate());
		task.setStatus(taskDTO.getStatus());
		task.setPriority(taskDTO.getPriority());
		task.setAssignedBy(loggedInUser);
		task.setAssignedTo(assignedToUser);

		return taskRepository.save(task);
	}

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	@Override
	public Optional<Task> getTaskById(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	public ResponseEntity<Task> updateTask(Long id, Task task) {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		User loggedInUser = userRepository.findByUsername(loggedInUsername)
				.orElseThrow(() -> new TaskNotFoundException("Logged-in user not found"));
		Task updatedTask = taskRepository.findById(id).map(existingTask -> {
			existingTask.setTitle(task.getTitle());
			existingTask.setDescription(task.getDescription());
			existingTask.setStatus(task.getStatus());
			existingTask.setPriority(task.getPriority());
			existingTask.setDueDate(task.getDueDate());

			Task savedTask = taskRepository.save(existingTask);
			notificationService.sendUpdateNotification(savedTask, loggedInUser);
			return savedTask;
		}).orElseThrow(() -> new TaskNotFoundException("Task not found with ID: " + id));

		return ResponseEntity.ok(updatedTask);
	}

	@Override
	public void deleteTask(Long id) {
		taskRepository.deleteById(id);
	}

	@Override
	public List<Task> searchTasks(String title, String description, String status, String dueDate) {
		Specification<Task> spec = Specification.where(TaskSpecification.hasTitle(title))
				.and(TaskSpecification.hasDescription(description)).and(TaskSpecification.hasStatus(status))
				.and(TaskSpecification.hasDueDate(dueDate));

		return taskRepository.findAll(spec);
	}

	@Override
	public ResponseEntity<Page<Task>> getTasksWithPagination(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<Task> tasksPage = taskRepository.findAll(pageable);

		return ResponseEntity.ok(tasksPage);
	}

}
