package com.sunuxam.sunuxamxam.repositories;

import com.sunuxam.sunuxamxam.entities.Epreuve;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {
    List<Epreuve> findByConcoursId(Long concoursId);
}