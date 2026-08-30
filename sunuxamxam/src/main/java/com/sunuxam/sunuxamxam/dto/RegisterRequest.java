package com.sunuxam.sunuxamxam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String password;
}