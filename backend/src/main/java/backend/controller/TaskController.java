package backend.controller;

import backend.controller.swagerInterface.TaskApi;
import backend.dto.CreateTaskRequest;
import backend.dto.TaskResponse;
import backend.dto.UpdateTaskRequest;
import backend.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import backend.service.TaskService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/task")
public class TaskController implements TaskApi {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody CreateTaskRequest taskRequest, @AuthenticationPrincipal User user) {
        return taskService.createTask(taskRequest, user);
    }

    @PutMapping
    public TaskResponse updateTask(@RequestBody UpdateTaskRequest taskRequest, @AuthenticationPrincipal User user) {
        return taskService.updateTask(taskRequest, user);
    }

    @PatchMapping("/{taskId}")
    public TaskResponse doneTask(@PathVariable UUID taskId, @AuthenticationPrincipal User user) {
        return taskService.changeCompleted(taskId, user);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID taskId, @AuthenticationPrincipal User user) {
        taskService.deleteTask(taskId, user);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse getTask(@PathVariable UUID taskId, @AuthenticationPrincipal User user) {
        return taskService.getTask(taskId, user);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public Page<TaskResponse> getAllTasks(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "20") int size,
                                          @AuthenticationPrincipal User user) {
        return taskService.getTasks(page, size, user);
    }
}
