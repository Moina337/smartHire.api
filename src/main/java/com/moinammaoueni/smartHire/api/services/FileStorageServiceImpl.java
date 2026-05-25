package com.moinammaoueni.smartHire.api.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.util.UUID;


@Service
@Profile("dev")
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public String save(MultipartFile file) {

        try {
            // chemin absolu basé sur le projet
            Path uploadPath = Paths.get("uploads").toAbsolutePath();

            // créer dossier s’il n’existe pas
            Files.createDirectories(uploadPath);

            // nom sécurisé
            String fileName = UUID.randomUUID() + "_" +
                    file.getOriginalFilename().replace(" ", "_");

            // chemin final du fichier
            Path filePath = uploadPath.resolve(fileName);

            // copie du fichier
            Files.copy(file.getInputStream(), filePath);

            return "http://localhost:8080/uploads/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Erreur upload fichier", e);
        }
    }
    
    @Override
    public void delete(String fileName) {

        try {
            Path uploadPath = Paths.get("uploads").toAbsolutePath();

            Path filePath = uploadPath.resolve(fileName);

            Files.deleteIfExists(filePath);

        } catch (IOException e) {
            throw new RuntimeException("Erreur suppression fichier", e);
        }
    }
}