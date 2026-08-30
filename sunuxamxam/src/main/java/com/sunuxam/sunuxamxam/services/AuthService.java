package com.sunuxam.sunuxamxam.services;

import com.sunuxam.sunuxamxam.dto.LoginRequest;
import com.sunuxam.sunuxamxam.dto.RegisterRequest;
import com.sunuxam.sunuxamxam.entities.Role;
import com.sunuxam.sunuxamxam.entities.Utilisateur;
import com.sunuxam.sunuxamxam.repositories.UtilisateurRepository;
import com.sunuxam.sunuxamxam.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Utilisateur register(RegisterRequest request) {
        Utilisateur u = new Utilisateur();
        u.setNom(request.getNom());
        u.setPrenom(request.getPrenom());
        u.setEmail(request.getEmail());
        u.setTelephone(request.getTelephone());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        u.setRole(Role.CANDIDAT);
        return utilisateurRepository.save(u);
    }

    public String login(LoginRequest request) {
        Utilisateur u = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), u.getPassword())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        return jwtUtil.generateToken(u.getEmail(), u.getRole().name(), u.getId());
    }
}