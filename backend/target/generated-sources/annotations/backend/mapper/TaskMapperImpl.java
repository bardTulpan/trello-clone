package backend.mapper;

import backend.dto.CreateTaskRequest;
import backend.dto.TaskResponse;
import backend.dto.UpdateTaskRequest;
import backend.entity.Task;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-23T14:35:42+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Ubuntu)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task toEntity(UpdateTaskRequest taskRequest) {
        if ( taskRequest == null ) {
            return null;
        }

        Task task = new Task();

        task.setId( taskRequest.getId() );
        task.setTitle( taskRequest.getTitle() );
        task.setDescription( taskRequest.getDescription() );

        return task;
    }

    @Override
    public Task toEntity(CreateTaskRequest taskRequest) {
        if ( taskRequest == null ) {
            return null;
        }

        Task task = new Task();

        task.setTitle( taskRequest.getTitle() );
        task.setDescription( taskRequest.getDescription() );

        return task;
    }

    @Override
    public TaskResponse toResponse(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setCompleted( task.isCompleted() );
        taskResponse.setId( task.getId() );
        taskResponse.setTitle( task.getTitle() );
        taskResponse.setDescription( task.getDescription() );
        taskResponse.setUpdatedAt( task.getUpdatedAt() );

        return taskResponse;
    }

    @Override
    public void updateEntityFromDto(UpdateTaskRequest dto, Task entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setTitle( dto.getTitle() );
        entity.setDescription( dto.getDescription() );
    }
}
