package com.lmajstorovic.agencyportal.auth;

import com.lmajstorovic.agencyportal.config.JwtService;
import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

    @Transactional
    public void updatePassword(UUID userId, String password) {
        Optional<User> user = userRepository.findById(userId);
        User existingUser;
        if (user.isPresent()) {
            existingUser = user.get();
            if (password == null || password.length() < 8) {
                throw new IllegalArgumentException("Password must be at least 8 characters long");
            } else if (Objects.equals(
                existingUser.getPassword(),
                passwordEncoder.encode(password)
            )) {
                throw new IllegalStateException("New password can not be the same as the old password");
            } else {
                existingUser.setPassword(
                    passwordEncoder.encode(password)
                );
                userRepository.save(existingUser);
            }
        } else {
            throw new IllegalStateException("User with id " + userId + " not found");
        }
    }
}
