package backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private ZonedDateTime timestamp;
}