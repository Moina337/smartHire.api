package com.moinammaoueni.smartHire.api.dto;

import org.springframework.web.multipart.MultipartFile;

public record FileUploadRequest(
        MultipartFile file
) {}
