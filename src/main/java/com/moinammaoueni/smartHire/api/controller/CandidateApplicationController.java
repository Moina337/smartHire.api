package com.moinammaoueni.smartHire.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;
import com.moinammaoueni.smartHire.api.dto.PostulationResponse;
import com.moinammaoueni.smartHire.api.services.ApplicationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidate/applications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(
    name = "CANDIDATE APPLICATIONS",
    description = "Gestion des candidatures"
    
)
public class CandidateApplicationController {

    private final ApplicationService applicationService;

    // POST /api/candidate/applications/{jobId}
    @PostMapping(
            value = "/{jobId}",
            consumes = "multipart/form-data"
    )
    public ApplicationResponse postuler(
            @PathVariable Long jobId,
            @RequestParam("file")
            MultipartFile file) {

        return applicationService
                .postuler(jobId, file);
    }

    // GET /api/candidate/applications
    @GetMapping
    public List<PostulationResponse> mesPostulations() {

        return applicationService.mesPostulations();
    }

    // GET /api/candidate/applications/{id}
    @GetMapping("/{id}")
    public ApplicationResponse detailPostulation(
            @PathVariable Long id) {

        return applicationService.detailPostulation(id);
    }
}