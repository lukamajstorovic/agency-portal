package com.lmajstorovic.agencyportal.service;

import com.lmajstorovic.agencyportal.model.Rank;
import com.lmajstorovic.agencyportal.repository.RankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RankService {
   private final RankRepository rankRepository;
   
   @Autowired
   public RankService(RankRepository rankRepository) {
      this.rankRepository = rankRepository;
   }
   
   public List<Rank> getRanks() {
      return rankRepository.findAll();
   }
   
   public Rank getRankByName(String name) {
      Optional<Rank> rankOptional = rankRepository.findRankByName(name);
      if (rankOptional.isPresent()) {
         return rankOptional.get();
      } else {
         throw new IllegalStateException("User with rank " + name + " does " +
            "not exist");
      }
   }
   
   public Rank getRankByDivision(UUID idDivision) {
      Optional<Rank> rankOptional =
         rankRepository.findRankByDivision(idDivision);
      if (rankOptional.isPresent()) {
         return rankOptional.get();
      } else {
         throw new IllegalStateException("Division with id " + idDivision +
            " does not exist");
      }
   }
}
