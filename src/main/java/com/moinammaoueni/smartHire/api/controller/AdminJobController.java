package com.moinammaoueni.smartHire.api.controller;



import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.services.JobService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/admin/jobs")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(
    name = "ADMIN JOBS",
    description = "Administration des offres d'emploi"
)
public class AdminJobController {
	
	//POST /api/admin/jobs
//	PUT /api/admin/jobs/{id}
//	DELETE /api/admin/jobs/{id}
//	GET /api/admin/jobs
	
	 private final JobService jobService;

	
	@PostMapping
    @Operation(
        summary = "Créer une offre d'emploi",
        description = "Permet de créer une nouvelle offre d'emploi dans le système"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Offre créée avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<JobResponse> create(@Valid @RequestBody JobRequest request) {
		
        return  ResponseEntity.ok(jobService.creerUnJob(request));
    }

    @GetMapping
    @Operation(
        summary = "Lister les offres d'emploi",
        description = "Retourne la liste complète des offres d'emploi"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    public ResponseEntity<Page<JobResponse>> getAll(@RequestParam(
            required = false
        ) String keyword,@ParameterObject Pageable pageable) {
    	
        return ResponseEntity.ok(jobService.tousJobs(keyword, pageable));
    }

}
