package com.sunuxam.sunuxamxam.services;

import com.sunuxam.sunuxamxam.entities.Concours;
import com.sunuxam.sunuxamxam.repositories.ConcoursRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcoursServiceTest {

    @Mock
    private ConcoursRepository concoursRepository;

    @InjectMocks
    private ConcoursService concoursService;

    @Test
    void publierResultats_doitEchouer_siAvantDateDeliberation() {
        Concours concours = new Concours();
        concours.setId(1L);
        concours.setDateDeliberation(LocalDate.now().plusDays(5));

        when(concoursRepository.findById(1L)).thenReturn(Optional.of(concours));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            concoursService.publierResultats(1L);
        });

        assertTrue(exception.getMessage().contains("délibération"));
    }

    @Test
    void publierResultats_doitReussir_siApresDateDeliberation() {
        Concours concours = new Concours();
        concours.setId(1L);
        concours.setDateDeliberation(LocalDate.now().minusDays(1));
        concours.setResultatsPublies(false);

        when(concoursRepository.findById(1L)).thenReturn(Optional.of(concours));
        when(concoursRepository.save(any(Concours.class))).thenAnswer(i -> i.getArgument(0));

        Concours resultat = concoursService.publierResultats(1L);

        assertTrue(resultat.getResultatsPublies());
    }
}