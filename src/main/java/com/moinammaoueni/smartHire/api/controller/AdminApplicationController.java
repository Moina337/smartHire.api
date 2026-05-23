package com.moinammaoueni.smartHire.api.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/applications")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(
    name = "ADMIN APPLICATIONS",
    description = "Administration des candidatures"
)
public class AdminApplicationController {
	
	//GET /api/admin/applications
	//PUT /api/admin/applications/{id}/status

}
