package com.moinammaoueni.smartHire.api.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;
import com.moinammaoueni.smartHire.api.dto.PostulationResponse;
import com.moinammaoueni.smartHire.api.dto.UpdateApplicationStatusRequest;
import com.moinammaoueni.smartHire.api.services.ApplicationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/applications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ADMIN APPLICATIONS", description = "Administration des candidatures")
public class AdminApplicationController {

	private final ApplicationService adminApplicationService;

	// GET /api/admin/applications
	@GetMapping
	public ResponseEntity<Page<PostulationResponse>> toutesPostulations(@ParameterObject Pageable pageable) {

		return ResponseEntity.ok(adminApplicationService.toutesPostulations(pageable));
	}

	// GET /api/admin/applications/{id}
	@GetMapping("/{id}")
	public ResponseEntity<ApplicationResponse> detail(@PathVariable Long id) {

		return ResponseEntity.ok(adminApplicationService.detail(id));
	}

	// PUT /api/admin/applications/{id}/status
	@PutMapping("/{id}/status")
	public ResponseEntity<ApplicationResponse> changerStatut(@PathVariable Long id,
			@RequestBody UpdateApplicationStatusRequest request) {

		return ResponseEntity.ok(adminApplicationService.changerStatut(id, request.getStatut()));
	}
}