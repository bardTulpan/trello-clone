package backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 60, message = "Title must be less than 60 characters")
    private String title;
    @Size(max = 5000, message = "Description must be less than 5000 characters")
    private String description;
}
