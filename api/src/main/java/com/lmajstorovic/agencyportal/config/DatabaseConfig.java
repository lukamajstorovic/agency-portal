package com.lmajstorovic.agencyportal.config;

import com.lmajstorovic.agencyportal.model.Division;
import com.lmajstorovic.agencyportal.model.Rank;
import com.lmajstorovic.agencyportal.model.User;
import com.lmajstorovic.agencyportal.repository.DivisionRepository;
import com.lmajstorovic.agencyportal.repository.RankRepository;
import com.lmajstorovic.agencyportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Time;
import java.util.List;

@Configuration
public class DatabaseConfig {

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, RankRepository rankRepository, DivisionRepository divisionRepository) {
        return args -> {
            Division division1 = new Division(
                1,
                "Agent",
                Time.valueOf("00:15:00")
            );
            Division division2 = new Division(
                2,
                "Security",
                Time.valueOf("00:30:00")
            );

            divisionRepository.saveAll(List.of(division1, division2));

            Rank rank1 = new Rank();
            rank1.setName("Agent I");
            rank1.setPosition(1);
            rank1.setIdDivision(divisionRepository.findDivisionByName("Agent").get().getId());

            Rank rank2 = new Rank();
            rank2.setName("Agent II");
            rank2.setPosition(2);
            rank2.setIdDivision(divisionRepository.findDivisionByName("Agent").get().getId());

            Rank rank3 = new Rank();
            rank3.setName("Security I");
            rank3.setPosition(1);
            rank3.setIdDivision(divisionRepository.findDivisionByName("Security").get().getId());

            Rank rank4 = new Rank();
            rank4.setName("Security II");
            rank4.setPosition(2);
            rank4.setIdDivision(divisionRepository.findDivisionByName("Security").get().getId());

            rankRepository.saveAll(List.of(rank1, rank2, rank3, rank4));

            User user1 = new User("username1", "password1", rank1.getId());
            User user2 = new User("username2", "password2", rank2.getId());
            User user3 = new User("username3", "password3", rank3.getId());
            User user4 = new User("username4", "password4", rank4.getId());

            userRepository.saveAll(List.of(user1, user2, user3, user4));
        };
    }
}
