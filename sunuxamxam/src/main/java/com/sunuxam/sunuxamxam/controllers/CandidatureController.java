package com.sunuxam.sunuxamxam.controllers;

import com.sunuxam.sunuxamxam.entities.Candidature;
import com.sunuxam.sunuxamxam.entities.StatutCandidature;
import com.sunuxam.sunuxamxam.services.CandidatureService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {

    private final CandidatureService candidatureService;

    public CandidatureController(CandidatureService candidatureService) {
        this.candidatureService = candidatureService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public Candidature create(
            @RequestParam Long candidatId,
            @RequestParam Long concoursId,
            @RequestParam(required = false) MultipartFile cv,
            @RequestParam(required = false) MultipartFile photo,
            @RequestParam(required = false) MultipartFile diplome
    ) throws IOException {
        return candidatureService.create(candidatId, concoursId, cv, photo, diplome);
    }

    @GetMapping("/candidat/{candidatId}")
    public List<Candidature> findByCandidat(@PathVariable Long candidatId) {
        return candidatureService.findByCandidat(candidatId);
    }

    @GetMapping("/concours/{concoursId}")
    public List<Candidature> findByConcours(@PathVariable Long concoursId) {
        return candidatureService.findByConcours(concoursId);
    }

    @PutMapping("/{id}/statut")
    public Candidature updateStatut(@PathVariable Long id, @RequestParam StatutCandidature statut) {
        return candidatureService.updateStatut(id, statut);
    }

    @GetMapping("/{id}/resultat")
    public Candidature voirResultat(@PathVariable Long id) {
        return candidatureService.voirResultat(id);
    }
}