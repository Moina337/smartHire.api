package com.moinammaoueni.smartHire.api.controller;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(
    name = "AUTH",
    description = "Authentification et gestion des comptes"
)
public class AuthController {
	
	// POST /api/auth/register
	//POST /api/auth/login

}