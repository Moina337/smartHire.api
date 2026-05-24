package com.moinammaoueni.smartHire.api.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moinammaoueni.smartHire.api.config.JwtService;
import com.moinammaoueni.smartHire.api.config.UtilisateurDetails;
import com.moinammaoueni.smartHire.api.dto.AuthResponse;
import com.moinammaoueni.smartHire.api.dto.LoginRequest;
import com.moinammaoueni.smartHire.api.dto.RegisterRequest;
import com.moinammaoueni.smartHire.api.entity.Utilisateur;
import com.moinammaoueni.smartHire.api.exception.UserAlreadyExistsException;
import com.moinammaoueni.smartHire.api.exception.UserNotFoundException;
import com.moinammaoueni.smartHire.api.num.Role;
import com.moinammaoueni.smartHire.api.repository.UtilisateurRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtilisateurServiceImpl implements UtilisateurService, UserDetailsService {

	private final UtilisateurRepository utilisateurRepository;

	private final PasswordEncoder encoder;

	private final JwtService jwtService;

	@Override
	public void register(RegisterRequest request) {

		// 1. check email
		if (utilisateurRepository.existsByEmail(request.getEmail())) {

			throw new UserAlreadyExistsException("Email déjà utilisé");

		}

		System.out.println(request.getPassword());

		// 2. create user
		Utilisateur utilisateur = Utilisateur.builder().nom(request.getNom()).email(request.getEmail())
				.password(encoder.encode(request.getPassword())).role(Role.CANDIDAT).build();

		// 3. save
		utilisateurRepository.save(utilisateur);

	}

	@Override
	public AuthResponse connection(LoginRequest request) {

		// 1. chercher utilisateur
		Utilisateur user = utilisateurRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

		// 2. verifier mot de passe
		if (!encoder.matches(request.getPassword(), user.getPassword())) {

			throw new BadCredentialsException("Email ou mot de passe incorrect");
		}

		// 3. claims JWT
		Map<String, Object> claims = new HashMap<>();

		claims.put("role", user.getRole().name());

		claims.put("userId", user.getId());

		// 4. generation token
		String token = jwtService.generateToken(claims, user.getEmail());

		// 5. response
		return AuthResponse.builder().token(token).build();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Utilisateur user = utilisateurRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

		return new UtilisateurDetails(user);
	}

}