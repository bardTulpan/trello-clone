package backend.repository;

import backend.entity.Task;
import backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    ArrayList<Task> getTasksByUser(User user);
    Page<Task> findByUserId(UUID userId, Pageable pageable);
}
