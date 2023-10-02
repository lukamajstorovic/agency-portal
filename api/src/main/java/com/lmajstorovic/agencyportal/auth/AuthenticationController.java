package com.lmajstorovic.agencyportal.auth;

import com.lmajstorovic.agencyportal.pojo.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth/user")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PatchMapping("/update/password")
    public void updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        UUID userId = updatePasswordRequest.getUserId();
        String password = updatePasswordRequest.getPassword();
        authenticationService.updatePassword(
            userId,
            password
        );
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
