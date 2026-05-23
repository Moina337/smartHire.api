package com.moinammaoueni.smartHire.api.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/candidate/profile")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(
    name = "CANDIDATE PROFILE",
    description = "Gestion du profil candidat"
)
public class CandidateController {
	
	//GET /api/candidate/profile
	//PUT /api/candidate/profile

}