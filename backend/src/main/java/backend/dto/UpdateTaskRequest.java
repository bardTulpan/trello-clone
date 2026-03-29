package backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateTaskRequest {
    @NotNull(message = "Id is required")
    private UUID id;
    @NotBlank(message = "Title is required")
    @Size(max = 60, message = "Title must be less than 60 characters")
    private String title;
    @Size(max = 5000, message = "Description must be less than 5000 characters")
    private String description;
}
