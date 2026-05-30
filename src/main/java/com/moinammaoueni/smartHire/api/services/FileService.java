package com.moinammaoueni.smartHire.api.services;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.moinammaoueni.smartHire.api.config.CurrentUser;
import com.moinammaoueni.smartHire.api.entity.Candidate;
import com.moinammaoueni.smartHire.api.repository.CandidatRepository;

import org.springframework.security.access.AccessDeniedException;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

    private final CandidatRepository candidatRepository;
    private final CurrentUser currentUser;
    private final FileStorageService fileStorageService;

    public Resource loadCv(String fileName) {

        // 1. retrouver candidat lié au CV
        Candidate candidat = candidatRepository.findByCvUrl(fileName)
                .orElseThrow(() -> new RuntimeException("CV introuvable"));

        // 2. utilisateur connecté
        Long userId = currentUser.getId();

        // 3. vérifier propriétaire
        boolean isOwner = candidat.getUtilisateur().getId().equals(userId);

        // 4. vérifier admin
        boolean isAdmin = currentUser.hasRole("ADMIN");

        // 5. sécurité
        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Accès interdit au CV");
        }

        // 6. charger fichier
        
        Path file = fileStorageService.load(fileName);

        try {
            return new UrlResource(file.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Erreur accès CV", e);
        }
    }
}