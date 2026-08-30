package com.sunuxam.sunuxamxam.repositories;

import com.sunuxam.sunuxamxam.entities.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    List<Candidature> findByCandidatId(Long candidatId);
    List<Candidature> findByConcoursId(Long concoursId);
}