package com.lmajstorovic.agencyportal.repository;

import com.lmajstorovic.agencyportal.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser_ValidUser_CreatesAndSavesToDatabase() {
        // Arrange
        User user = new User("username", "rank");

        // Act
        User createdUser = userRepository.save(user);

        // Assert
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isEqualTo(user.getId());
        assertThat(createdUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(createdUser.getRank()).isEqualTo(user.getRank());
    }


    @Test
    void CreatesUser_And_SavesToDatabase_And_DeletesUser_FromDatabase() {

    }

    @Test
    void CreatesUser_And_SavesToDatabase_And_FindsUser_ByUsername_InDatabase_And_ReturnsUser() {
        User user = new User(
            "username",
            "rank"
        );
        userRepository.save(user);

        User userResult = userRepository
            .findUserByUsername("username")
            .orElse(null);

        assertThat(userResult).isNotNull();
        assertThat(userResult.getId()).isEqualTo(user.getId());
        assertThat(userResult.getUsername()).isEqualTo(user.getUsername());
        assertThat(userResult.getRank()).isEqualTo(user.getRank());
    }
}