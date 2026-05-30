package com.moinammaoueni.smartHire.api.services;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;

public interface FileStorageService {

    String save(MultipartFile file);

    void delete(String fileName);

    String getUrl(String fileName);
    
    Path load(String fileName);
}