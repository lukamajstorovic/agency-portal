package com.lmajstorovic.agencyportal.service;

import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;
    private User userModel;
    private User userInDatabase;

    private void assertUsersEqual(User actual, User expected) {
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
        assertThat(actual.getRank()).isEqualTo(expected.getRank());
        assertThat(actual.getTag()).isEqualTo(expected.getTag());
        assertThat(actual.getIdPersonalSecretary()).isEqualTo(expected.getIdPersonalSecretary());
        assertThat(actual.getApproved()).isEqualTo(expected.getApproved());
        assertThat(actual.getCreatedAt().getTime()).isEqualTo(expected.getCreatedAt().getTime());
    }

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        userModel = new User(
            "username",
            "password",
            "rank"
        );
        this.userInDatabase = userRepository.save(userModel);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteById(this.userInDatabase.getId());
    }

    @Test
    void getUsers() {
        List<User> users = userService.getUsers();

        assertThat(users).isNotEmpty();
    }

    @Test
    void addNewUser() {
        User newUser = new User(
            "new_user",
            "new_password",
            "new_rank"
        );

        userService.addNewUser(newUser);

        User user = userRepository.findUserByUsername(newUser.getUsername()).orElse(null);
        assertThat(user).isNotNull();
        assertUsersEqual(user, newUser);

        userRepository.deleteById(user.getId());
    }

    @Test
    void deleteUser() {
        UUID userIdToDelete = userInDatabase.getId();

        userService.deleteUser(userIdToDelete);

        Optional<User> deletedUser = userRepository.findById(userIdToDelete);
        assertThat(deletedUser).isEmpty();
    }

    @Test
    void getUserByUsername() {
        String usernameToRetrieve = userInDatabase.getUsername();

        User retrievedUser = userService.getUserByUsername(usernameToRetrieve);

        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getUsername()).isEqualTo(usernameToRetrieve);
    }

    @Test
    void getUsersByRank() {
        String rankToRetrieve = userInDatabase.getRank();

        List<User> users = userService.getUsersByRank(rankToRetrieve);

        assertThat(users).isNotEmpty();
        assertThat(users).allMatch(user -> user.getRank().equals(rankToRetrieve));
    }


    @Test
    void updateUserWithValidData() {
        User updatedUser = new User();
        updatedUser.setId(userModel.getId());
        updatedUser.setUsername("new_username");
        updatedUser.setPassword("new_password");
        updatedUser.setRank("new_rank");
        updatedUser.setTag("new");
        updatedUser.setIdPersonalSecretary(UUID.randomUUID());
        updatedUser.setApproved(false);
        updatedUser.setCreatedAt(userModel.getCreatedAt());

        userService.updateUser(updatedUser);

        User user = userRepository.findById(updatedUser.getId()).orElse(null);
        assertThat(user).isNotNull();
        assertUsersEqual(user, updatedUser);
    }

    @Test
    void updateUsernameToExistingUsername() {
        // Arrange
        User existingUser = new User(
            "existing_username",
            "password",
            "rank"
        );

        User updatedUser = new User();
        updatedUser.setUsername("new_username");
        updatedUser.setPassword("new_password");
        updatedUser.setRank("new_rank");
        updatedUser.setTag("tag");
        updatedUser.setIdPersonalSecretary(UUID.randomUUID());

        updatedUser = userRepository.save(existingUser);

        updatedUser.setUsername(userModel.getUsername());

        User finalUpdatedUser = updatedUser;
        assertThatThrownBy(() -> userService.updateUser(finalUpdatedUser))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Username is already taken");

        userRepository.deleteById(existingUser.getId());
    }

    @Test
    void updateWithEmptyUsername() {
        userModel.setUsername("");

        assertThatThrownBy(() -> userService.updateUser(userModel))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Username cannot be empty");
    }

    @Test
    void updateWithInvalidPassword() {
        userModel.setPassword("short");

        assertThatThrownBy(() -> userService.updateUser(userModel))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Password must be at least 8 characters long");
    }

    @Test
    void updateWithEmptyRank() {
        userModel.setRank("");

        assertThatThrownBy(() -> userService.updateUser(userModel))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Rank cannot be empty");
    }

    @Test
    void updateWithEmptyTag() {
        userModel.setTag("");

        assertThatThrownBy(() -> userService.updateUser(userModel))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Tag must be between 2 and 4 characters long");
    }

    @Test
    void updateWithTagContainingBrackets() {
        userModel.setTag("[invalid]");

        assertThatThrownBy(() -> userService.updateUser(userModel))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Tag must be between 2 and 4 characters long");
    }

    @Test
    void updateWithTagAlreadyExists() {
        // Arrange
        User existingUser = new User(
            "existing_user",
            "password",
            "rank"
        );
        existingUser.setTag("here");

        existingUser = userRepository.save(existingUser);
        userModel.setTag("here");

        assertThatThrownBy(() -> userService.updateUser(userModel))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Tag is already taken");

        userRepository.deleteById(existingUser.getId());
    }
}