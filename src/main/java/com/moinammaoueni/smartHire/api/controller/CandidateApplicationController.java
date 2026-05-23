package com.moinammaoueni.smartHire.api.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	//POST /api/candidate/applications
	//GET /api/candidate/applications
	//GET /api/candidate/applications/{id}

}
