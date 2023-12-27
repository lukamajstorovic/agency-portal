package com.lmajstorovic.agencyportal.repository;

import com.lmajstorovic.agencyportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository
   extends JpaRepository<User, UUID> {
   
   @Query("SELECT user from User user where user.username = ?1")
   Optional<User> findUserByUsername(String username);
   
   @Query("SELECT user from User user where user.idRank = ?1")
   List<User> findUsersByRank(UUID idRank);
   
   @Query("SELECT user.tag from User user where user.tag = ?1")
   Optional<String> findTag(String tag);
}
