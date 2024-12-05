package banquemisr.challenge05.controllers.Task;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import banquemisr.challenge05.dto.TaskDTO;
import banquemisr.challenge05.models.Task;
import banquemisr.challenge05.services.Taskservice.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    @Autowired

    private  TaskService taskService;

    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
    
    
	@GetMapping("/search")
	public List<Task> searchTasks(@RequestParam(required = false) String title,
			@RequestParam(required = false) String description, @RequestParam(required = false) String status,
			@RequestParam(required = false) String dueDate) {
		return taskService.searchTasks(title, description, status, dueDate);
	}
    
    
    
    
	 @GetMapping("/paginated")
	    public ResponseEntity<Page<Task>> getPaginatedTasks(
	            @RequestParam(defaultValue = "0") int page,      
	            @RequestParam(defaultValue = "10") int size      
	    ) {
	        return taskService.getTasksWithPagination(page, size);
	    }
    
    
    
    
    
}
