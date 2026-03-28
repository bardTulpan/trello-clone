package backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be correct")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 60, message = "Password must be between 6 and 60 characters long")
    private String password;
}
