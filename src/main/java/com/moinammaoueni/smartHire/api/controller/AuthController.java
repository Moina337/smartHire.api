package com.moinammaoueni.smartHire.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moinammaoueni.smartHire.api.dto.AuthResponse;
import com.moinammaoueni.smartHire.api.dto.LoginRequest;
import com.moinammaoueni.smartHire.api.dto.RegisterRequest;
import com.moinammaoueni.smartHire.api.services.UtilisateurService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "AUTH", description = "Authentification et gestion des comptes")
public class AuthController {

    private final UtilisateurService utilisateurService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse>
    inscription(@Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(utilisateurService.register(request));
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> connection(@Valid @RequestBody LoginRequest request){
    	
    	AuthResponse response = utilisateurService.connection(request);
    	
    	return ResponseEntity.ok(response);
    }
}