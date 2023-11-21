package com.lmajstorovic.agencyportal.repository;

import com.lmajstorovic.agencyportal.model.Division;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class DivisionRepositoryTest {

    @Autowired
    private DivisionRepository divisionRepository;
    private Division divisionModel;
    private Division divisionInDatabase;

    @BeforeEach
    void setUp() {
        divisionModel = new Division(
            1,
            "Agent"
        );
        this.divisionInDatabase = divisionRepository.save(divisionModel);
    }

    @AfterEach
    void tearDown() {
        divisionRepository.deleteById(this.divisionInDatabase.getId());
    }

    @Test
    void createUser_ValidUser_CreatesAndSavesToDatabase() {
        assertThat(divisionInDatabase).isEqualTo(divisionModel);
    }

    @Test
    void createDivision_SaveToDatabase_FindById() {
        Optional<Division> divisionResult = divisionRepository.findById(divisionModel.getId());
        assertThat(divisionResult).isNotEmpty();
        Division divisionResultPresent = divisionResult.get();
        System.out.println(divisionModel.getCreatedAt() + " A " + divisionResultPresent.getCreatedAt());
        assertThat(divisionResultPresent).isEqualTo(divisionModel);
    }

    @Test
    void createDivision_SaveToDatabase_FindByName() {
        Optional<Division> divisionResult = divisionRepository.findDivisionByName(divisionModel.getName());
        assertThat(divisionResult).isNotEmpty();
        Division divisionResultPresent = divisionResult.get();
        assertThat(divisionResultPresent).isEqualTo(divisionModel);
    }

    @Test
    void createDivision_SaveToDatabase_FindAll() {
        List<Division> divisionResultCollection = divisionRepository.findAll();
        assertThat(divisionResultCollection).isNotNull();
        divisionResultCollection.forEach(divisionResult -> {
                assertThat(divisionResult).isEqualTo(divisionModel);
            }
        );
    }

    @Test
    void createUser_SaveToDatabase_DeleteUser() {
        Division divisionModelToDelete = new Division(
            2,
            "Security"
        );
        Division divisionModelInDatabaseToDelete = divisionRepository.save(divisionModelToDelete);

        divisionRepository.delete(divisionModelInDatabaseToDelete);

        Optional<Division> deletedDivisionResult = divisionRepository.findById(divisionModelToDelete.getId());
        assertThat(deletedDivisionResult).isEmpty();
    }

}
