package com.lmajstorovic.agencyportal.config;

import com.lmajstorovic.agencyportal.model.Division;
import com.lmajstorovic.agencyportal.model.Rank;
import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.RankRepository;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseConfig {
   
   @Bean
   CommandLineRunner commandLineRunner(UserRepository userRepository, RankRepository rankRepository) {
      return args -> {
         Rank rank1 = new Rank();
         rank1.setName("Rank 1");
         
         Rank rank2 = new Rank();
         rank2.setName("Rank 2");
         
         rankRepository.saveAll(List.of(rank1, rank2));
         
         User user1 = new User("username1", "password1", rank1.getId());
         User user2 = new User("username2", "password2", rank2.getId());
         
         userRepository.saveAll(List.of(user1, user2));
      };
   }
}
