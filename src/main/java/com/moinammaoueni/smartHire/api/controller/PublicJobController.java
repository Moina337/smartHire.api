package com.moinammaoueni.smartHire.api.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.services.JobService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public/jobs")
@RequiredArgsConstructor
@Tag(
    name = "PUBLIC JOBS",
    description = "Consultation publique des offres d'emploi"
)
public class PublicJobController {

    private final JobService jobService;

    @GetMapping
    public ResponseEntity<Page<JobResponse>> listJob(
           @ParameterObject Pageable pageable) {

        return ResponseEntity.ok(
                jobService.tousJobs(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> afficheJobParId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                jobService.afficheJobParId(id)
        );
    }
}