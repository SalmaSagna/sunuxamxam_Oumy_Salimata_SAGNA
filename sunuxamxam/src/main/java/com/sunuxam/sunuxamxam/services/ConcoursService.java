package com.sunuxam.sunuxamxam.services;

import com.sunuxam.sunuxamxam.entities.Concours;
import com.sunuxam.sunuxamxam.repositories.ConcoursRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ConcoursService {

    private final ConcoursRepository concoursRepository;

    public ConcoursService(ConcoursRepository concoursRepository) {
        this.concoursRepository = concoursRepository;
    }

    public List<Concours> findAll() {
        return concoursRepository.findAll();
    }

    public Concours findById(Long id) {
        return concoursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concours introuvable"));
    }

    public Concours create(Concours concours) {
        return concoursRepository.save(concours);
    }

    public Concours update(Long id, Concours data) {
        Concours c = findById(id);
        c.setTitre(data.getTitre());
        c.setDescription(data.getDescription());
        c.setDateLimite(data.getDateLimite());
        c.setDateDeliberation(data.getDateDeliberation());
        return concoursRepository.save(c);
    }

    public void delete(Long id) {
        concoursRepository.deleteById(id);
    }

    public Concours publierResultats(Long id) {
        Concours c = findById(id);

        if (LocalDate.now().isBefore(c.getDateDeliberation())) {
            throw new RuntimeException("Impossible de publier avant la date de délibération (" + c.getDateDeliberation() + ")");
        }

        c.setResultatsPublies(true);
        return concoursRepository.save(c);
    }
}