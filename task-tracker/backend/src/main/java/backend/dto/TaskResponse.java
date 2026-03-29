package backend.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private boolean completed;
    private Instant updatedAt;
}