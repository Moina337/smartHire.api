package com.moinammaoueni.smartHire.api.config;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;




@Component
public class FileValidator {

    private static final List<String> ALLOWED_TYPES =
            List.of("application/pdf");

    public void validate(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("Fichier vide");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("Fichier trop volumineux");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new RuntimeException("Type non autorisé");
        }

        String original =
                StringUtils.cleanPath(file.getOriginalFilename());

        if (original.contains("..")) {
            throw new RuntimeException("Nom invalide");
        }
    }
}
