package banquemisr.challenge05.models;


import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import banquemisr.challenge05.models.enums.Priority;
import banquemisr.challenge05.models.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_seq")
	@SequenceGenerator(name = "history_seq", sequenceName = "history_sequence", allocationSize = 1)
	private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private String changeDescription; // e.g., "Status changed from TODO to DONE"

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User changedBy; // User who made the change

    // Getters and Setters
}
