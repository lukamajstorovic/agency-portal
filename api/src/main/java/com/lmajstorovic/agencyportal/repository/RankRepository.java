package com.lmajstorovic.agencyportal.repository;

import com.lmajstorovic.agencyportal.model.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface RankRepository
   extends JpaRepository<Rank, UUID> {
   @Query("SELECT rank from Rank rank where rank.name = ?1")
   Optional<Rank> findRankByName(String name);
   
   @Query("SELECT rank from Rank rank where rank.idDivision = ?1")
   Optional<Rank> findRankByDivision(UUID idDivision);
}
