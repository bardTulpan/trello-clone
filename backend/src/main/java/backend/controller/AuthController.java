package backend.controller;

import backend.controller.swagerInterface.AuthApi;
import backend.dto.RegistrationRequest;
import backend.dto.UserResponse;
import backend.entity.User;
import backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController implements AuthApi {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<Void> registation(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + authService.registrationAndSendWelcomeEmail(registrationRequest))
                .build();
    }

    @GetMapping("/user")
    public UserResponse getUser(@AuthenticationPrincipal User user) {
        return new UserResponse(user.getId(), user.getEmail());
    }

    @PostMapping("/login")
    public ResponseEntity<Void> signIn(@RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + authService.signIn(registrationRequest))
                .build();
    }
}
