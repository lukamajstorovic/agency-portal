package com.lmajstorovic.agencyportal.auth;

import com.lmajstorovic.agencyportal.config.JwtService;
import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode((request.getPassword())));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var token = AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
        System.out.println("JWT: \n" + jwtToken);
        System.out.println("TOKEN: \n" + token);
        return token;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        var user = userRepository.findUserByUsername(request.getUsername())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var token = AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
        System.out.println("JWT: \n" + jwtToken);
        System.out.println("TOKEN: \n" + token);
        return token;
    }
}
