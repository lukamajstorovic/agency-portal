package com.lmajstorovic.agencyportal.service;

import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(User user) {
        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Username taken");
        }
        userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        boolean userExists = userRepository.existsById(userId);
        if (!userExists) {
            throw new IllegalStateException("User with id " + userId + " does not exist");
        }
        userRepository.deleteById(userId);
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalStateException("User with username " + username + " does not exist");
        }
    }

    @Transactional
    public void updateUser(User user) {
        String username = user.getUsername();
        String rank = user.getRank();
        if (username != null &&
            !username.isEmpty() &&
            !Objects.equals(user.getUsername(), username)) {
            user.setUsername(username);
            Optional<User> userOptional = userRepository
                .findUserByUsername(username);
            if (userOptional.isPresent()) {
                throw new IllegalStateException("username taken");
            }
        }
        if (rank != null &&
            !rank.isEmpty() &&
            !Objects.equals(user.getRank(), rank)) {
            user.setRank(rank);
        }
    }
}
