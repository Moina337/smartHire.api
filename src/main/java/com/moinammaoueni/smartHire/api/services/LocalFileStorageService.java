package com.moinammaoueni.smartHire.api.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalFileStorageService implements FileStorageService {

	@Override
	public String save(MultipartFile file) {

	    try {

	        Path uploadPath =
	                Paths.get("uploads").toAbsolutePath();

	        Files.createDirectories(uploadPath);

	        String fileName =
	                UUID.randomUUID()
	                + "_"
	                + file.getOriginalFilename()
	                        .replace(" ", "_");

	        Path filePath =
	                uploadPath.resolve(fileName);

	        Files.copy(file.getInputStream(), filePath);

	        return fileName; // ✔ uniquement le nom

	    } catch (IOException e) {
	        throw new RuntimeException(
	                "Erreur upload fichier", e);
	    }
	}
	
	
	@Override
	public void delete(String fileName) {

	    try {

	        Path uploadPath =
	                Paths.get("uploads").toAbsolutePath();

	        Path filePath =
	                uploadPath.resolve(fileName);

	        Files.deleteIfExists(filePath);

	    } catch (IOException e) {
	        throw new RuntimeException(
	                "Erreur suppression fichier", e);
	    }
	}
	
	
}
