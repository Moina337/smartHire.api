package com.moinammaoueni.smartHire.api.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	
    String save(MultipartFile file);
    
    void delete(String fileName);
}
