package banquemisr.challenge05.dto;



import java.time.LocalDate;

import banquemisr.challenge05.models.enums.Priority;
import banquemisr.challenge05.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private String title;
    private String description;
    private Long assignedToId;
    private LocalDate dueDate;
    private Status status;
    private Priority priority;
}

