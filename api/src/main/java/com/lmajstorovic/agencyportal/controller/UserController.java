package com.lmajstorovic.agencyportal.controller;

import com.lmajstorovic.agencyportal.auth.AuthenticationService;
import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.pojo.*;
import com.lmajstorovic.agencyportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/auth/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(
        UserService userService,
        AuthenticationService authenticationService
    ) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(path = "/all")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(path = "id/{id}")
    public User getUserById(@PathVariable("id") UUID id) {
        return userService.getUserById(id);
    }

    @GetMapping(path = "/username/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalStateException("User with username " + username + " does not exist");
        }
    }

    @GetMapping(path = "/rank")
    public List<User> getUsersByRank(@RequestBody String rank) {
        return userService.getUsersByRank(rank);
    }

    @PatchMapping(path = "/update/username")
    public void updateUsername(@RequestBody UpdateUsernameRequest updateUsernameRequest) {
        UUID userId = updateUsernameRequest.getUserId();
        String username = updateUsernameRequest.getUsername();
        userService.updateUsername(
            userId,
            username
        );
    }

    @PatchMapping(path = "/update/tag")
    public void updateTag(@RequestBody UpdateTagRequest updateTagRequest) {
        UUID userId = updateTagRequest.getUserId();
        String tag = updateTagRequest.getTag();
        userService.updateTag(
            userId,
            tag
        );
    }

    @PatchMapping(path = "/update/rank")
    public void updateRank(@RequestBody UpdateRankRequest updateRankRequest) {
        String username = updateRankRequest.getUsername();
        String rank = updateRankRequest.getRank();
        userService.updateRank(
            username,
            rank
        );
    }

    @PatchMapping(path = "/update/secretary")
    public void updatePersonalSecretary(@RequestBody UpdatePersonalSecretaryRequest updatePersonalSecretaryRequest) {
        UUID userId = updatePersonalSecretaryRequest.getUserId();
        String secretaryUsername = updatePersonalSecretaryRequest.getSecretaryUsername();
        userService.updatePersonalSecretary(
            userId,
            secretaryUsername
        );
    }

    @PatchMapping(path = "/update/approved")
    public void updateApprovedStatus(@RequestBody UpdateApprovedStatusRequest updateApprovedStatusRequest) {
        UUID userId = updateApprovedStatusRequest.getUserId();
        Boolean approved = updateApprovedStatusRequest.getApproved();
        userService.updateApprovedStatus(
            userId,
            approved
        );
    }

    @DeleteMapping(path = "/delete/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
    }
}
