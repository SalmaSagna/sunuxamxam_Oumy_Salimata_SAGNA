package com.sunuxam.sunuxamxam.controllers;

import com.sunuxam.sunuxamxam.entities.Epreuve;
import com.sunuxam.sunuxamxam.services.EpreuveService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/concours/{concoursId}/epreuves")
public class EpreuveController {

    private final EpreuveService epreuveService;

    public EpreuveController(EpreuveService epreuveService) {
        this.epreuveService = epreuveService;
    }

    @GetMapping
    public List<Epreuve> findByConcours(@PathVariable Long concoursId) {
        return epreuveService.findByConcours(concoursId);
    }

    @PostMapping
    public Epreuve create(@PathVariable Long concoursId, @RequestBody Epreuve epreuve) {
        return epreuveService.create(concoursId, epreuve);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        epreuveService.delete(id);
    }
}