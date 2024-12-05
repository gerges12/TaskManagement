package banquemisr.challenge05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import banquemisr.challenge05.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
	
    List<Task> findByDueDateBetween(LocalDate start, LocalDate end);
    
    Page<Task> findAll(Pageable pageable);


}