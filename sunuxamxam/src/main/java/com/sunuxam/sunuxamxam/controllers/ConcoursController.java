package com.sunuxam.sunuxamxam.controllers;

import com.sunuxam.sunuxamxam.entities.Concours;
import com.sunuxam.sunuxamxam.services.ConcoursService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController //Elle indique que les réponses des méthodes doivent être directement envoyées au client au format JSON
@RequestMapping("/api/concours")
public class ConcoursController {

    private final ConcoursService concoursService;

    public ConcoursController(ConcoursService concoursService) {
        this.concoursService = concoursService;
    }

    @GetMapping
    public List<Concours> findAll() {
        return concoursService.findAll();
    }

    @GetMapping("/{id}")
    public Concours findById(@PathVariable Long id) {
        return concoursService.findById(id);
    }

    @PostMapping
    public Concours create(@RequestBody Concours concours) {
        return concoursService.create(concours);
    }

    @PutMapping("/{id}")
    public Concours update(@PathVariable Long id, @RequestBody Concours concours) {
        return concoursService.update(id, concours);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        concoursService.delete(id);
    }

    @PutMapping("/{id}/publier-resultats")
    public Concours publierResultats(@PathVariable Long id) {
        return concoursService.publierResultats(id);
    }
}