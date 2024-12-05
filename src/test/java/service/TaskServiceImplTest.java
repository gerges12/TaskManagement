package service;

import com.dto.TaskDTO;
import com.models.Task;
import com.models.User;
import com.models.enums.Priority;
import com.models.enums.Status;
import com.repository.TaskRepository;
import com.repository.UserRepository;
import com.services.NotificationService.NotificationService;
import com.services.Taskservice.TaskServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private TaskServiceImpl taskService;

    private User loggedInUser;
    private User assignedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock users
        loggedInUser = new User("loggedInUser", "loggedin@example.com", "password");
        assignedUser = new User("assignedUser", "assigned@example.com", "password");

        // Mock the authentication context to simulate a logged-in user
        SecurityContextHolder.getContext().setAuthentication(mockAuthentication());
    }

    private Authentication mockAuthentication() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("loggedInUser");
        return authentication;
    }

    @Test
    void testCreateTask() {
        // Setup TaskDTO
        TaskDTO taskDTO = new TaskDTO("Task Title", "Task Description", assignedUser.getId(), LocalDate.now(), Status.TODO, Priority.HIGH);

        // Mock user repository calls
        when(userRepository.findByUsername("loggedInUser")).thenReturn(Optional.of(loggedInUser));
        when(userRepository.findById(taskDTO.getAssignedToId())).thenReturn(Optional.of(assignedUser));

        // Mock the task repository to save the task
        Task savedTask = new Task(1L, taskDTO.getTitle(), taskDTO.getDescription(), taskDTO.getStatus(), taskDTO.getPriority(), taskDTO.getDueDate(), assignedUser, loggedInUser);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Call the createTask method
        Task createdTask = taskService.createTask(taskDTO);

        // Assertions to verify the behavior
        assertNotNull(createdTask);
        assertEquals(taskDTO.getTitle(), createdTask.getTitle());
        assertEquals(taskDTO.getAssignedToId(), createdTask.getAssignedTo());
        assertEquals(loggedInUser, createdTask.getAssignedBy());

        // Verify that the notification service is called
        verify(notificationService, times(1)).sendUpdateNotification(createdTask, loggedInUser);
    }
}
