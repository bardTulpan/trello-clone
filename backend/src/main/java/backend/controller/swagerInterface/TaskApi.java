package backend.controller.swagerInterface;

import backend.dto.CreateTaskRequest;
import backend.dto.TaskResponse;
import backend.dto.UpdateTaskRequest;
import backend.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Управление задачами", description = "CRUD операции с задачами пользователя")
public interface TaskApi {

    @Operation(summary = "Создать новую задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "401", description = "Не авторизован")
    })
    TaskResponse createTask(
            @RequestBody CreateTaskRequest taskRequest,
            @Parameter(hidden = true) User user
    );

    @Operation(summary = "Обновить существующую задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные запроса"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskResponse updateTask(
            @RequestBody UpdateTaskRequest taskRequest,
            @Parameter(hidden = true) User user
    );

    @Operation(summary = "Отметить задачу как выполненную")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус задачи изменен"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskResponse doneTask(
            @PathVariable("taskId") UUID taskId,
            @Parameter(hidden = true) User user
    );

    @Operation(summary = "Удалить задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    void deleteTask(
            @PathVariable("taskId") UUID taskId,
            @Parameter(hidden = true) User user
    );

    @Operation(summary = "Получить задачу по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "401", description = "Не авторизован"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    TaskResponse getTask(
            @PathVariable("taskId") UUID taskId,
            @Parameter(hidden = true) User user
    );

    @Operation(summary = "Получить все задачи пользователя с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач получен"),
            @ApiResponse(responseCode = "401", description = "Не авторизован")
    })
    Page<TaskResponse> getAllTasks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @Parameter(hidden = true) User user
    );
}