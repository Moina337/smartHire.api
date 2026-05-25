package com.moinammaoueni.smartHire.api.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moinammaoueni.smartHire.api.config.AppProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalFileStorageService
        implements FileStorageService {

    private final AppProperties appProperties;

    private final Path uploadPath =
            Paths.get("uploads").toAbsolutePath();

    @Override
    public String save(MultipartFile file) {

        try {

            Files.createDirectories(uploadPath);

            String fileName =
                    UUID.randomUUID()
                    + "_"
                    + file.getOriginalFilename()
                            .replace(" ", "_");

            Path filePath =
                    uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath
            );

            return fileName;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erreur upload fichier", e);
        }
    }

    @Override
    public void delete(String fileName) {

        try {

            Path filePath =
                    uploadPath.resolve(fileName);

            Files.deleteIfExists(filePath);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Erreur suppression fichier", e);
        }
    }

    @Override
    public String getUrl(String fileName) {

        if (fileName == null) {
            return null;
        }

        return appProperties.getFileBaseUrl()
                + "/"
                + fileName;
    }
}