package com.sunuxam.sunuxamxam.controllers;

import com.sunuxam.sunuxamxam.entities.Note;
import com.sunuxam.sunuxamxam.services.NoteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public Note saisir(@RequestParam Long candidatureId, @RequestParam Long epreuveId, @RequestParam Double valeur) {
        return noteService.saisir(candidatureId, epreuveId, valeur);
    }

    @GetMapping("/candidature/{candidatureId}")
    public List<Note> findByCandidature(@PathVariable Long candidatureId) {
        return noteService.findByCandidature(candidatureId);
    }
}