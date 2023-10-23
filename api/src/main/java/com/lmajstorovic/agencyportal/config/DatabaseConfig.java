package com.lmajstorovic.agencyportal.config;

import com.lmajstorovic.agencyportal.model.Division;
import com.lmajstorovic.agencyportal.model.Rank;
import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DatabaseConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            Division division = new Division(
                1,
                "Division 1"
            );
            Rank rank1 = new Rank(
                1,
                "Rank 1",
                division.getId()
            );
            Rank rank2 = new Rank(
                2,
                "Rank 2",
                division.getId()
            );
            User user1 = new User(
                "username1",
                "password1",
                rank1.getId()
            );
            User user2 = new User(
                "username2",
                "password2",
                rank2.getId()
            );
            repository.saveAll(
                List.of(user1, user2)
            );
        };
    }
}
