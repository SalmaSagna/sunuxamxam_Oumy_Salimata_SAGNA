package com.sunuxam.sunuxamxam.services;

import com.sunuxam.sunuxamxam.entities.Candidature;
import com.sunuxam.sunuxamxam.entities.Epreuve;
import com.sunuxam.sunuxamxam.entities.Note;
import com.sunuxam.sunuxamxam.repositories.CandidatureRepository;
import com.sunuxam.sunuxamxam.repositories.EpreuveRepository;
import com.sunuxam.sunuxamxam.repositories.NoteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final CandidatureRepository candidatureRepository;
    private final EpreuveRepository epreuveRepository;

    public NoteService(NoteRepository noteRepository, CandidatureRepository candidatureRepository, EpreuveRepository epreuveRepository) {
        this.noteRepository = noteRepository;
        this.candidatureRepository = candidatureRepository;
        this.epreuveRepository = epreuveRepository;
    }

    public Note saisir(Long candidatureId, Long epreuveId, Double valeur) {
        Candidature candidature = candidatureRepository.findById(candidatureId)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));
        Epreuve epreuve = epreuveRepository.findById(epreuveId)
                .orElseThrow(() -> new RuntimeException("Epreuve introuvable"));

        Note note = noteRepository.findByCandidatureIdAndEpreuveId(candidatureId, epreuveId)
                .orElse(new Note());

        note.setCandidature(candidature);
        note.setEpreuve(epreuve);
        note.setValeur(valeur);
        return noteRepository.save(note);
    }

    public List<Note> findByCandidature(Long candidatureId) {
        return noteRepository.findByCandidatureId(candidatureId);
    }
}