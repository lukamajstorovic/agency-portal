package com.lmajstorovic.agencyportal.repository;

import com.lmajstorovic.agencyportal.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User userModel;
    private User userInDatabase;

    @BeforeEach
    void setUp() {
        userModel = new User(
            "username",
            "rank"
        );
        this.userInDatabase = userRepository.save(userModel);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteById(this.userInDatabase.getId());
    }

    @Test
    void createUser_ValidUser_CreatesAndSavesToDatabase() {
        assertThat(userInDatabase).isNotNull();
        assertThat(userInDatabase.getId()).isEqualTo(userModel.getId());
        assertThat(userInDatabase.getUsername()).isEqualTo(userModel.getUsername());
        assertThat(userInDatabase.getRank()).isEqualTo(userModel.getRank());
    }

    @Test
    void createUser_SaveToDatabase_FindById() {
        Optional<User> userResult = userRepository.findById(userModel.getId());
        assertThat(userResult).isNotEmpty();
        User userResultPresent;
        if (userResult.isPresent()) {
            userResultPresent = userResult.get();
            assertThat(userResultPresent.getId()).isEqualTo(userModel.getId());
            assertThat(userResultPresent.getUsername()).isEqualTo(userModel.getUsername());
            assertThat(userResultPresent.getRank()).isEqualTo(userModel.getRank());
        }
    }

    @Test
    void createUser_SaveToDatabase_FindByUsername() {
        Optional<User> userResult = userRepository.findUserByUsername(userModel.getUsername());
        assertThat(userResult).isNotEmpty();
        User userResultPresent;
        if (userResult.isPresent()) {
            userResultPresent = userResult.get();
            assertThat(userResultPresent.getId()).isEqualTo(userModel.getId());
            assertThat(userResultPresent.getUsername()).isEqualTo(userModel.getUsername());
            assertThat(userResultPresent.getRank()).isEqualTo(userModel.getRank());
        }
    }

    @Test
    void createUser_SaveToDatabase_FindByRank() {
        List<User> userResultCollection = userRepository.findUsersByRank(userModel.getUsername());
        assertThat(userResultCollection).isNotNull();
        userResultCollection.forEach(userResult -> {
                assertThat(userResult.getId()).isEqualTo(userModel.getId());
                assertThat(userResult.getUsername()).isEqualTo(userModel.getUsername());
                assertThat(userResult.getRank()).isEqualTo(userModel.getRank());
            }
        );
    }

    @Test
    void createUser_SaveToDatabase_DeleteUser() {
        User userModelDelete = new User(
            "usernameDelete",
            "rankDelete"
        );
        User userInDatabaseDelete = userRepository.save(userModelDelete);

        userRepository.delete(userInDatabaseDelete);

        Optional<User> deletedUserResult = userRepository.findUserByUsername(userModelDelete.getUsername());
        assertThat(deletedUserResult).isEmpty();
    }
}