package backend.mapper;

import backend.dto.CreateTaskRequest;
import backend.dto.TaskResponse;
import backend.dto.UpdateTaskRequest;
import backend.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(UpdateTaskRequest taskRequest);
    Task toEntity(CreateTaskRequest taskRequest);

    @Mapping(source = "completed", target = "completed")
    TaskResponse toResponse(Task task);

    void updateEntityFromDto(UpdateTaskRequest dto, @MappingTarget Task entity);
}
