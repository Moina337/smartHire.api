package com.moinammaoueni.smartHire.api.services;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.moinammaoueni.smartHire.api.config.AppProperties;

import lombok.RequiredArgsConstructor;

@Service
public class LocalFileStorageService implements FileStorageService {

    private final AppProperties appProperties;
    private final Path uploadPath;

    public LocalFileStorageService(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.uploadPath = Paths.get("uploads")
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("Erreur création dossier", e);
        }
    }

	public String save(MultipartFile file) {

		try {
			String original =
			        StringUtils.cleanPath(file.getOriginalFilename());

			String extension = "";

			int dot = original.lastIndexOf(".");
			if (dot > 0) {
			    extension = original.substring(dot);
			}

			String fileName =
			        UUID.randomUUID() + extension;

			Path target = uploadPath.resolve(fileName).normalize();

			Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

			return fileName;

		} catch (IOException e) {
			throw new RuntimeException("Erreur upload", e);
		}
	}

	@Override
	public void delete(String fileName) {

		try {

			Path filePath = uploadPath
			        .resolve(fileName)
			        .normalize();

			if (!filePath.startsWith(uploadPath)) {
			    throw new RuntimeException("Accès interdit");
			}

			Files.deleteIfExists(filePath);

		} catch (IOException e) {

			throw new RuntimeException("Erreur suppression fichier", e);
		}
	}

	@Override
	public String getUrl(String fileName) {

		if (fileName == null) {
			return null;
		}

		return UriComponentsBuilder
		        .fromHttpUrl(appProperties.getFileBaseUrl())
		        .path("/")
		        .path(fileName)
		        .toUriString();
	}

	@Override
	public Path load(String fileName) {
		
		Path filePath = uploadPath
	            .resolve(fileName)
	            .normalize();

	    if (!filePath.startsWith(uploadPath)) {
	        throw new RuntimeException("Accès interdit fichier");
	    }

	    return filePath;
	}
	
	
	
}