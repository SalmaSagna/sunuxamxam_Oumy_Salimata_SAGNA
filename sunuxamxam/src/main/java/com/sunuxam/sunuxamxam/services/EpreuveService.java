package com.sunuxam.sunuxamxam.services;

import com.sunuxam.sunuxamxam.entities.Concours;
import com.sunuxam.sunuxamxam.entities.Epreuve;
import com.sunuxam.sunuxamxam.repositories.ConcoursRepository;
import com.sunuxam.sunuxamxam.repositories.EpreuveRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EpreuveService {

    private final EpreuveRepository epreuveRepository;
    private final ConcoursRepository concoursRepository;

    public EpreuveService(EpreuveRepository epreuveRepository, ConcoursRepository concoursRepository) {
        this.epreuveRepository = epreuveRepository;
        this.concoursRepository = concoursRepository;
    }

    public List<Epreuve> findByConcours(Long concoursId) {
        return epreuveRepository.findByConcoursId(concoursId);
    }

    public Epreuve create(Long concoursId, Epreuve epreuve) {
        Concours concours = concoursRepository.findById(concoursId)
                .orElseThrow(() -> new RuntimeException("Concours introuvable"));
        epreuve.setConcours(concours);
        return epreuveRepository.save(epreuve);
    }

    public void delete(Long id) {
        epreuveRepository.deleteById(id);
    }
}