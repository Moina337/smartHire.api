package com.moinammaoueni.smartHire.api.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.moinammaoueni.smartHire.api.services.FileService;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/cv/{fileName}")
    public ResponseEntity<Resource> getCv(@PathVariable String fileName) {
        Resource resource = fileService.loadCv(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf") // Force l'interprétation en PDF
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }

}