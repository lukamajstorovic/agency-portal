package com.lmajstorovic.agencyportal.controller;

import com.lmajstorovic.agencyportal.auth.AuthenticationService;
import com.lmajstorovic.agencyportal.model.Rank;
import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.pojo.UpdateActiveStatusRequest;
import com.lmajstorovic.agencyportal.pojo.UpdatePersonalSecretaryRequest;
import com.lmajstorovic.agencyportal.pojo.UpdateRankRequest;
import com.lmajstorovic.agencyportal.pojo.UpdateTagRequest;
import com.lmajstorovic.agencyportal.service.DivisionService;
import com.lmajstorovic.agencyportal.service.RankService;
import com.lmajstorovic.agencyportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/user")
public class UserController {

    private final DivisionService divisionService;
    private final RankService rankService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserController(
        DivisionService divisionService,
        RankService rankService,
        UserService userService,
        AuthenticationService authenticationService
    ) {
        this.divisionService = divisionService;
        this.rankService = rankService;
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
            throw new IllegalStateException("User with username " + username +
                " does not exist");
        }
    }

    @GetMapping(path = "/rank")
    public List<User> getUsersByRank(@RequestBody UUID idRank) {
        return userService.getUsersByRank(idRank);
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
        String rankName = updateRankRequest.getRank();
        Rank rank = rankService.getRankByName(rankName);
        userService.updateRank(
            username,
            rank.getId()
        );
    }

    @PatchMapping(path = "/update/secretary")
    public void updatePersonalSecretary(@RequestBody UpdatePersonalSecretaryRequest updatePersonalSecretaryRequest) {
        UUID userId = updatePersonalSecretaryRequest.getUserId();
        String secretaryUsername =
            updatePersonalSecretaryRequest.getSecretaryUsername();
        userService.updatePersonalSecretary(
            userId,
            secretaryUsername
        );
    }

    @PatchMapping(path = "/update/active")
    public void updateActiveStatus(@RequestBody UpdateActiveStatusRequest updateActiveStatusRequest) {
        UUID userId = updateActiveStatusRequest.getUserId();
        Boolean active = updateActiveStatusRequest.getActive();
        userService.updateActiveStatus(
            userId,
            active
        );
    }

    @DeleteMapping(path = "/delete/{userId}")
    public void deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUser(userId);
    }
}
