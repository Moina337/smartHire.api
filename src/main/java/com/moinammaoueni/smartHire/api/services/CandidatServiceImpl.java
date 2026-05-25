package com.moinammaoueni.smartHire.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moinammaoueni.smartHire.api.config.CurrentUser;
import com.moinammaoueni.smartHire.api.dto.CandidateRequest;
import com.moinammaoueni.smartHire.api.dto.CandidateResponse;
import com.moinammaoueni.smartHire.api.dto.DetailCandidatResponse;
import com.moinammaoueni.smartHire.api.entity.Candidate;
import com.moinammaoueni.smartHire.api.entity.Utilisateur;
import com.moinammaoueni.smartHire.api.exception.CandidatNotFoundException;
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

	private final FileStorageService fileStorageService;

	@Override
	public CandidateResponse completerProfilCandidat(CandidateRequest request) {

		Long userId = currentUser.getId();

		Utilisateur utilisateur = utilisateurRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

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
				.orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

		// 3. récupérer candidat lié à l'utilisateur
		Candidate candidat = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new RuntimeException("Profil candidat non complété"));

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

		List<Candidate> candidates = candidatRepository.findAll();

		List<CandidateResponse> responses = candidates.stream().map(mapperInterface::candidatToCandidateResponse)
				.toList();

		return responses;
	}

	@Override
	public DetailCandidatResponse candidatParId(Long candidatId) {

		Candidate candidat = candidatRepository.findById(candidatId)
				.orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé"));

		return mapperInterface.candidatToDetailCandidat(candidat);
	}

	@Override
	public DetailCandidatResponse uploadCv(MultipartFile file) {

	    // 1. validation
	    if (file == null || file.isEmpty()) {
	        throw new RuntimeException("CV obligatoire");
	    }

	    // validation PDF
	    if (!"application/pdf".equals(file.getContentType())) {
	        throw new RuntimeException("Le CV doit être un fichier PDF");
	    }

	    // validation taille 5MB
	    if (file.getSize() > 5 * 1024 * 1024) {
	        throw new RuntimeException("Le fichier dépasse 5MB");
	    }

	    // 2. utilisateur connecté
	    Long userId = currentUser.getId();

	    // 3. récupérer candidat
	    Candidate candidat =
	            candidatRepository.findByUtilisateurId(userId)
	            .orElseThrow(() ->
	                    new CandidatNotFoundException(
	                            "Profil candidat introuvable"));

	    // 4. supprimer ancien CV
	    if (candidat.getCvUrl() != null
	            && !candidat.getCvUrl().isBlank()) {

	        fileStorageService.delete(
	                candidat.getCvUrl()
	        );
	    }

	    // 5. sauvegarder nouveau CV
	    String fileName =
	            fileStorageService.save(file);

	    // 6. update DB
	    candidat.setCvUrl(fileName);

	    Candidate saved =
	            candidatRepository.save(candidat);

	    // 7. mapping response
	    DetailCandidatResponse response =
	            mapperInterface.candidatToDetailCandidat(saved);

	    response.setCvUrl(
	            fileStorageService.getUrl(
	                    saved.getCvUrl()
	            )
	    );

	    return response;
	}
}
