package com.lmajstorovic.agencyportal.repository;

import com.lmajstorovic.agencyportal.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DivisionRepository
    extends JpaRepository<Division, UUID> {
    @Query("SELECT division from Division division where division.name = ?1")
    Optional<Division> findDivisionByName(String name);
}
