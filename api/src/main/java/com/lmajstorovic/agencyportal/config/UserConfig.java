package com.lmajstorovic.agencyportal.config;

import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UserConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            User user1 = new User(
                "username1",
                "password1",
                "rank1",
                true
            );
            User user2 = new User(
                "username2",
                "password2",
                "rank2",
                true
            );
            repository.saveAll(
                List.of(user1, user2)
            );
        };
    }
}
