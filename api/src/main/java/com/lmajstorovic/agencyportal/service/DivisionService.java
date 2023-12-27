package com.lmajstorovic.agencyportal.service;

import com.lmajstorovic.agencyportal.model.Division;
import com.lmajstorovic.agencyportal.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DivisionService {
   private final DivisionRepository divisionRepository;
   
   @Autowired
   public DivisionService(DivisionRepository divisionRepository) {
      this.divisionRepository = divisionRepository;
   }
   
   public List<Division> getDivisions() {
      return divisionRepository.findAll();
   }
   
   public Optional<Division> getDivisionByName(String name) {
      return divisionRepository.findDivisionByName(name);
   }
}
