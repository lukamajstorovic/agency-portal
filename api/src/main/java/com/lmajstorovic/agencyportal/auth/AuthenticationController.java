package com.lmajstorovic.agencyportal.auth;

import com.lmajstorovic.agencyportal.pojo.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
   
   private final AuthenticationService authenticationService;
   
   @PostMapping("/register")
   public ResponseEntity<MessageResponse> register(
      @RequestBody RegisterRequest request
   ) {
      authenticationService.register(request);
      MessageResponse messageResponse = new MessageResponse("Registration " +
         "successful");
      return ResponseEntity.ok(messageResponse);
   }
   
   @PatchMapping("/auth/user/update/password")
   public void updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
      UUID userId = updatePasswordRequest.getUserId();
      String password = updatePasswordRequest.getPassword();
      authenticationService.updatePassword(
         userId,
         password
      );
   }
   
   @PostMapping("/login")
   public ResponseEntity<AuthenticationResponse> login(
      @RequestBody AuthenticationRequest request
   ) {
      AuthenticationResponse authenticationResponse =
         authenticationService.authenticate(request);
      
      return ResponseEntity.ok(authenticationResponse);
   }
   
}
