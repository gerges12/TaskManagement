package com.dto;



import java.time.LocalDate;

import com.models.enums.Priority;
import com.models.enums.Status;

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

