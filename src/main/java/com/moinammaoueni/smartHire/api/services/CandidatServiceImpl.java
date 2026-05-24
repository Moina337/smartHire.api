package com.moinammaoueni.smartHire.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moinammaoueni.smartHire.api.config.CurrentUser;
import com.moinammaoueni.smartHire.api.dto.CandidateRequest;
import com.moinammaoueni.smartHire.api.dto.CandidateResponse;
import com.moinammaoueni.smartHire.api.dto.DetailCandidatResponse;
import com.moinammaoueni.smartHire.api.entity.Candidate;
import com.moinammaoueni.smartHire.api.entity.Utilisateur;
import com.moinammaoueni.smartHire.api.exception.UserNotFoundException;
import com.moinammaoueni.smartHire.api.mappers.MapperInterface;
import com.moinammaoueni.smartHire.api.repository.CandidatRepository;
import com.moinammaoueni.smartHire.api.repository.UtilisateurRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CandidatServiceImpl implements CandidatService {
	
	public final CurrentUser currentUser;
	
	public final CandidatRepository candidatRepository;
	
	public final UtilisateurRepository utilisateurRepository;
	
	public final MapperInterface mapperInterface;

	@Override
	public CandidateResponse completerProfilCandidat(CandidateRequest request) {

	     Long userId = currentUser.getId();

	    Utilisateur utilisateur = utilisateurRepository.findById(userId)
	            .orElseThrow(() ->
	                    new UserNotFoundException("Utilisateur introuvable"));

	    // 2. mapper request -> entity
	    Candidate candidat = mapperInterface.candidatRquestToCandidat(request);

	    // 3. associer utilisateur
	    candidat.setUtilisateur(utilisateur);

	    // 4. sauvegarder candidat
	    Candidate saved = candidatRepository.save(candidat);

	    // 5. retourner response
	    return mapperInterface.candidatToCandidateResponse(saved);
	}

	@Override
	public DetailCandidatResponse monProfil() {

	    // 1. utilisateur connecté
	    Long userId = currentUser.getId();

	    // 2. récupérer utilisateur
	    Utilisateur utilisateur = utilisateurRepository.findById(userId)
	            .orElseThrow(() ->
	                    new UserNotFoundException("Utilisateur introuvable"));

	    // 3. récupérer candidat lié à l'utilisateur
	    Candidate candidat = candidatRepository.findByUtilisateurId(userId)
	            .orElseThrow(() ->
	                    new RuntimeException("Profil candidat non complété"));

	    // 4. construire response
	    DetailCandidatResponse response = mapperInterface.candidatToDetailCandidat(candidat);

	    return response;
	}

	@Override
	public CandidateResponse modifierProfil(CandidateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void supprimerProfil() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CandidateResponse> listeCandidats() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DetailCandidatResponse candidatParId(Long candidatId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
