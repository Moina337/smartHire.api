package com.moinammaoueni.smartHire.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moinammaoueni.smartHire.api.dto.CandidateResponse;
import com.moinammaoueni.smartHire.api.dto.DetailCandidatResponse;
import com.moinammaoueni.smartHire.api.services.CandidatService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/candidates")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "ADMIN CANDIDATES", description = "Gestion des candidats")
public class AdminCandidateController {

	// GET /api/admin/candidates
	// GET /api/admin/candidates/{id}

	public final CandidatService candidatService;

	@GetMapping
	public ResponseEntity<List<CandidateResponse>> listeCandidats() {

		List<CandidateResponse> responses = candidatService.listeCandidats();

		return ResponseEntity.ok(responses);

	}

	
	@GetMapping("/{id}")
	public ResponseEntity<DetailCandidatResponse> afficheCandidatParId(Long candidat_id) {

		DetailCandidatResponse response = candidatService.candidatParId(candidat_id);

		return ResponseEntity.ok(response);

	}

}
