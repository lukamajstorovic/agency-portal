package com.lmajstorovic.agencyportal.service;

import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public List<User> getUsersByRank(String rank) {
        List<User> userCollection = userRepository.findUsersByRank(rank);
        if (!userCollection.isEmpty()) {
            return userCollection;
        } else {
            throw new IllegalStateException("Users with rank " + rank + " do not exist");
        }
    }

    public Boolean tagAlreadyExists(String tag) {
        Optional<String> tagResult = userRepository.findTag(tag);
        return tagResult.isPresent();
    }

    @Transactional
    public void updateUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String rank = user.getRank();
        String tag = user.getTag();
        UUID idPersonalSecretary = user.getIdPersonalSecretary();
        Boolean approved = user.getApproved();

        Optional<User> existingUserOptional = userRepository.findById(user.getId());
        if (existingUserOptional.isEmpty()) {
            throw new IllegalStateException("User not found");
        }
        User existingUser = existingUserOptional.get();

        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (!existingUser.getUsername().equals(username)) {
            Optional<User> userOptional = userRepository.findUserByUsername(username);
            if (userOptional.isPresent()) {
                throw new IllegalStateException("Username is already taken");
            }
        }

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        if (rank == null || rank.isEmpty()) {
            throw new IllegalArgumentException("Rank cannot be empty");
        }

        if (tag == null) {
            throw new IllegalArgumentException("Tag can not be empty");
        } else if (tag.contains("[]")) {
            tag = tag
                .replace("[", "")
                .replace("]", "");
        } else if (tag.length() < 2 || tag.length() > 4) {
            throw new IllegalArgumentException("Tag must be between 2 and 4 characters long");
        } else if (tagAlreadyExists(tag)) {
            throw new IllegalStateException("Tag is already taken");
        }

        if (approved == null) {
            throw new IllegalArgumentException("Approved can not be null");
        }

        existingUser.setUsername(username);
        existingUser.setPassword(password);
        existingUser.setRank(rank);
        existingUser.setTag(tag);
        existingUser.setIdPersonalSecretary(idPersonalSecretary);
        existingUser.setApproved(approved);

        userRepository.save(existingUser);
    }
}
