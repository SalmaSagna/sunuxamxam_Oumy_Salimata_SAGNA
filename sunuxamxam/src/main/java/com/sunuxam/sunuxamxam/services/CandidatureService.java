package com.sunuxam.sunuxamxam.services;

import com.sunuxam.sunuxamxam.entities.*;
import com.sunuxam.sunuxamxam.repositories.CandidatureRepository;
import com.sunuxam.sunuxamxam.repositories.ConcoursRepository;
import com.sunuxam.sunuxamxam.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CandidatureService {

    private final CandidatureRepository candidatureRepository;
    private final ConcoursRepository concoursRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;

    public CandidatureService(CandidatureRepository candidatureRepository, ConcoursRepository concoursRepository, UtilisateurRepository utilisateurRepository) {
        this.candidatureRepository = candidatureRepository;
        this.concoursRepository = concoursRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Candidature create(Long candidatId, Long concoursId, MultipartFile cv, MultipartFile photo, MultipartFile diplome) throws IOException {
        Utilisateur candidat = utilisateurRepository.findById(candidatId)
                .orElseThrow(() -> new RuntimeException("Candidat introuvable"));
        Concours concours = concoursRepository.findById(concoursId)
                .orElseThrow(() -> new RuntimeException("Concours introuvable"));

        if (LocalDate.now().isAfter(concours.getDateLimite())) {
            throw new RuntimeException("La date limite de candidature est dépassée");
        }

        if (candidatureRepository.findByCandidatIdAndConcoursId(candidatId, concoursId).isPresent()) {
            throw new RuntimeException("Vous avez déjà postulé à ce concours");
        }

        Candidature c = new Candidature();
        c.setCandidat(candidat);
        c.setConcours(concours);
        c.setStatut(StatutCandidature.EN_ATTENTE);
        c.setDateCandidature(LocalDateTime.now());
        c.setCheminCv(saveFile(cv));
        c.setCheminPhoto(saveFile(photo));
        c.setCheminDiplome(saveFile(diplome));

        return candidatureRepository.save(c);
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        File dir = new File(uploadDir).getAbsoluteFile();
        if (!dir.exists()) dir.mkdirs();

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(dir, filename);
        file.transferTo(dest);

        return uploadDir + "/" + filename;
    }

    public List<Candidature> findByCandidat(Long candidatId) {
        return candidatureRepository.findByCandidatId(candidatId);
    }

    public List<Candidature> findByConcours(Long concoursId) {
        return candidatureRepository.findByConcoursId(concoursId);
    }

    public Candidature updateStatut(Long id, StatutCandidature statut) {
        Candidature c = candidatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (Boolean.TRUE.equals(c.getConcours().getResultatsPublies())) {
            throw new RuntimeException("Impossible de modifier une candidature après publication des résultats");
        }

        c.setStatut(statut);
        return candidatureRepository.save(c);
    }

    public Candidature voirResultat(Long id) {
        Candidature c = candidatureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidature introuvable"));

        if (!Boolean.TRUE.equals(c.getConcours().getResultatsPublies())) {
            throw new RuntimeException("Les résultats ne sont pas encore publiés");
        }
        return c;
    }
}