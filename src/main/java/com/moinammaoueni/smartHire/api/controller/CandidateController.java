 package com.moinammaoueni.smartHire.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.moinammaoueni.smartHire.api.config.FileValidator;
import com.moinammaoueni.smartHire.api.dto.CandidateRequest;
import com.moinammaoueni.smartHire.api.dto.CandidateResponse;
import com.moinammaoueni.smartHire.api.dto.DetailCandidatResponse;
import com.moinammaoueni.smartHire.api.services.CandidatService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidate/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "CANDIDATE PROFILE", description = "Gestion du profil candidat")
public class CandidateController {

	private final CandidatService candidatService;
	
	private final FileValidator validator;
	

	//  GET - voir mon profil candidat
	@GetMapping
	public ResponseEntity<DetailCandidatResponse> getMyProfile() {

		DetailCandidatResponse response = candidatService.monProfil();

		return ResponseEntity.ok(response);
		
	}

	// POST - créer / compléter profil candidat
	@PostMapping
	public ResponseEntity<CandidateResponse> createProfile(@Valid @RequestBody CandidateRequest request) {

		CandidateResponse response = candidatService.completerProfilCandidat(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}

	// PUT - modifier profil candidat
	@PutMapping
	public ResponseEntity<CandidateResponse> updateProfile(@Valid @RequestBody CandidateRequest request) {

		CandidateResponse response = candidatService.modifierProfil(request);

		return ResponseEntity.ok(response);
		
	}

	@PutMapping(value = "/cv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<DetailCandidatResponse> uploadCv(@RequestParam("file") MultipartFile file) {

		 validator.validate(file);
		 
		return ResponseEntity.ok(candidatService.uploadCv(file));
		
	}
}