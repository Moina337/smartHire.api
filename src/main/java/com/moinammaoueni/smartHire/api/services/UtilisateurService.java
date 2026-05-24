package com.moinammaoueni.smartHire.api.services;


import com.moinammaoueni.smartHire.api.dto.AuthResponse;
import com.moinammaoueni.smartHire.api.dto.LoginRequest;
import com.moinammaoueni.smartHire.api.dto.RegisterRequest;

public interface UtilisateurService {
	
	AuthResponse register(RegisterRequest request);
	
	AuthResponse connection(LoginRequest request);

}
