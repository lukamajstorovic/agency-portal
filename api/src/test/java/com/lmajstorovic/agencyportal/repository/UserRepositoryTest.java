package com.lmajstorovic.agencyportal.repository;

import com.lmajstorovic.agencyportal.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
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
        userModel = new User(
            "username",
            "password",
            "rank",
            true
        );
        this.userInDatabase = userRepository.save(userModel);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteById(this.userInDatabase.getId());
    }

    @Test
    void createUser_ValidUser_CreatesAndSavesToDatabase() {
        assertUsersEqual(userInDatabase, userModel);
    }

    @Test
    void createUser_SaveToDatabase_FindById() {
        Optional<User> userResult = userRepository.findById(userModel.getId());
        assertThat(userResult).isNotEmpty();
        User userResultPresent = userResult.get();
        assertUsersEqual(userResultPresent, userModel);
    }

    @Test
    void createUser_SaveToDatabase_FindByUsername() {
        Optional<User> userResult = userRepository.findUserByUsername(userModel.getUsername());
        assertThat(userResult).isNotEmpty();
        User userResultPresent = userResult.get();
        assertUsersEqual(userResultPresent, userModel);
    }

    @Test
    void createUser_SaveToDatabase_FindByRank() {
        List<User> userResultCollection = userRepository.findUsersByRank(userModel.getUsername());
        assertThat(userResultCollection).isNotNull();
        userResultCollection.forEach(userResult -> {
                assertUsersEqual(userResult, userModel);
            }
        );
    }

    @Test
    void createUser_SaveToDatabase_DeleteUser() {
        User userModelDelete = new User(
            "usernameDelete",
            "passwordDelete",
            "rankDelete",
            true
        );
        User userInDatabaseDelete = userRepository.save(userModelDelete);

        userRepository.delete(userInDatabaseDelete);

        Optional<User> deletedUserResult = userRepository.findUserByUsername(userModelDelete.getUsername());
        assertThat(deletedUserResult).isEmpty();
    }
}