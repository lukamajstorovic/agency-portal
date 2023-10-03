package com.lmajstorovic.agencyportal.service;

import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

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

    public User getUserById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new IllegalStateException("User with id " + id + " does not exist");
        }
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public List<User> getUsersByRank(String rank) {
        List<User> userCollection = userRepository.findUsersByRank(rank);
        if (!userCollection.isEmpty()) {
            return userCollection;
        } else {
            throw new IllegalStateException("Users with rank " + rank + " do not exist");
        }
    }

    private Boolean usernameAlreadyExists(String username) {
        Optional<User> userResult = userRepository.findUserByUsername(username);
        return userResult.isPresent();
    }

    private Boolean tagAlreadyExists(String tag) {
        Optional<String> tagResult = userRepository.findTag(tag);
        return tagResult.isPresent();
    }

    @Transactional
    public void updateUsername(UUID userId, String username) {
        Optional<User> user = userRepository.findById(userId);
        User existingUser;
        if (user.isPresent()) {
            existingUser = user.get();
            if (username.isBlank()) {
                throw new IllegalArgumentException("Username can not be empty");
            } else if (Objects.equals(existingUser.getUsername(), username)) {
                throw new IllegalStateException("New username can not be the same as the old username");
            } else if (usernameAlreadyExists(username)) {
                throw new IllegalStateException("Username is already taken");
            } else {
                existingUser.setUsername(username);
                userRepository.save(existingUser);
            }
        } else {
            throw new IllegalStateException("User with id " + userId + " not found");
        }
    }

    @Transactional
    public void updateRank(String username, String rank) {
        Optional<User> user = userRepository.findUserByUsername(username);
        User existingUser;
        if (user.isPresent()) {
            existingUser = user.get();
            if (rank.isEmpty()) {
                throw new IllegalArgumentException("Rank cannot be empty");
            } else {
                existingUser.setRank(rank);
                userRepository.save(existingUser);
                //TODO: CREATE A PROMOTION LOG
            }
        } else {
            //TODO: CREATE A PROMOTION LOG
        }
    }

    @Transactional
    public void updateTag(UUID userId, String tag) {
        Optional<User> user = userRepository.findById(userId);
        User existingUser;
        if (user.isPresent()) {
            existingUser = user.get();
            if (tag.contains("[]")) {
                tag = tag
                    .replace("[", "")
                    .replace("]", "");
            } else if (tag.length() < 2 || tag.length() > 4 || tag.isBlank()) {
                throw new IllegalArgumentException("Tag must be between 2 and 4 characters long");
            } else if (Objects.equals(existingUser.getTag(), tag)) {
                throw new IllegalStateException("New tag can not be the same as the old tag");
            } else if (tagAlreadyExists(tag)) {
                throw new IllegalStateException("Tag is already taken");
            } else {
                existingUser.setTag(tag);
                userRepository.save(existingUser);
            }
        } else {
            throw new IllegalStateException("User with id " + userId + " not found");
        }
    }

    @Transactional
    public void updatePersonalSecretary(UUID userId, String secretaryUsername) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> secretary = userRepository.findUserByUsername(secretaryUsername);
        User existingUser;
        User existingSecretary;
        if (user.isPresent()) {
            existingUser = user.get();
            if (secretaryUsername.isBlank()) {
                throw new IllegalArgumentException("Secretary username can not be empty");
            } else if (secretary.isPresent()) {
                existingSecretary = secretary.get();
                if (existingSecretary.getId() == existingUser.getIdPersonalSecretary()) {
                    throw new IllegalStateException("User is already marked as the personal secretary");
                } else {
                    existingUser.setIdPersonalSecretary(existingSecretary.getId());
                    userRepository.save(existingUser);
                }
            } else {
                throw new IllegalStateException("User with username " + secretaryUsername + " not found");
            }
        } else {
            throw new IllegalStateException("User with id " + userId + " not found");
        }
    }

    @Transactional
    public void updateApprovedStatus(UUID userId, Boolean approved) {
        Optional<User> user = userRepository.findById(userId);
        User existingUser;
        if (user.isPresent()) {
            existingUser = user.get();
            if (approved == null) {
                throw new IllegalArgumentException("Approved can not be null");
            } else if (existingUser.getApproved() == approved && approved) {
                if (approved) {
                    throw new IllegalStateException("User is already approved");
                } else {
                    throw new IllegalStateException("User is already not approved");
                }
            } else {
                existingUser.setApproved(approved);
                userRepository.save(existingUser);
                //TODO: CREATE A PROMOTION LOG
            }
        } else {
            throw new IllegalStateException("User with id " + userId + " not found");
        }
    }

    @Transactional
    public void updateUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String rank = user.getRank();
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

        if (approved == null) {
            throw new IllegalArgumentException("Approved can not be null");
        }

        existingUser.setUsername(username);
        existingUser.setPassword(password);
        existingUser.setRank(rank);
        existingUser.setIdPersonalSecretary(idPersonalSecretary);
        existingUser.setApproved(approved);

        userRepository.save(existingUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.getUserByUsername(username);
        UserDetails existingUser;
        if (user.isPresent()) {
            existingUser = user.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
        return existingUser;
    }
}
