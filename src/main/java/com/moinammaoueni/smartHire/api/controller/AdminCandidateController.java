package com.moinammaoueni.smartHire.api.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/candidates")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(
    name = "ADMIN CANDIDATES",
    description = "Gestion des candidats"
)
public class AdminCandidateController {
	
	//GET /api/admin/candidates
	//GET /api/admin/candidates/{id}

}
