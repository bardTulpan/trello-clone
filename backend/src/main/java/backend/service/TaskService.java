package backend.service;

import backend.config.PaginationProperties;
import backend.dto.CreateTaskRequest;
import backend.dto.TaskResponse;
import backend.dto.UpdateTaskRequest;
import backend.entity.Task;
import backend.entity.User;
import backend.exception.AccessDeniedException;
import backend.exception.NotFoundException;
import lombok.AllArgsConstructor;
import backend.mapper.TaskMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import backend.repository.TaskRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final PaginationProperties paginationProperties;

    @Transactional
    public TaskResponse createTask(CreateTaskRequest taskRequest, User user) {
        Task task = taskMapper.toEntity(taskRequest);
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    @Transactional
    public void deleteTask(UUID taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to delete this task");
        }
        taskRepository.delete(task);
    }

    @Transactional
    public TaskResponse updateTask(UpdateTaskRequest taskRequest, User user) {
        Task selectedTask = taskRepository.findById(taskRequest.getId())
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (!selectedTask.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this task");
        }

        taskMapper.updateEntityFromDto(taskRequest, selectedTask);

        return taskMapper.toResponse(selectedTask);
    }

    public TaskResponse getTask(UUID taskId, User user) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to get this task");
        }
        return taskMapper.toResponse(task);
    }

    public Page<TaskResponse> getTasks(int page, int size, User user) {
        Pageable pageable = PageRequest.of(page, Math.min(size, paginationProperties.getMaxSize()));

        return taskRepository
                .findByUserId(user.getId(), pageable)
                .map(taskMapper::toResponse);
    }

    @Transactional
    public TaskResponse changeCompleted(UUID taskId, User user) {
        Task selectedTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundException("Task not found"));

        if (!selectedTask.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this task");
        }

        selectedTask.setCompleted(!selectedTask.isCompleted());
        taskRepository.save(selectedTask);
        return taskMapper.toResponse(selectedTask);
    }
}