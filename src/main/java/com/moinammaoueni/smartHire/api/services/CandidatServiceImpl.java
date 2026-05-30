package com.moinammaoueni.smartHire.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
	@Transactional
	@PreAuthorize("hasRole('CANDIDAT')")
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
	@PreAuthorize("hasRole('CANDIDAT')")
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
		return mapperInterface.candidatToDetailCandidat(candidat);
        
	}

	@Override
	@Transactional
	@PreAuthorize("hasRole('CANDIDAT')")
	public CandidateResponse modifierProfil(CandidateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	@PreAuthorize("hasRole('CANDIDAT')")
	public void supprimerProfil() {
		// TODO Auto-generated method stub

	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Page<CandidateResponse> listeCandidats(Pageable pageable) {
		// TODO Auto-generated method stub

		 return   candidatRepository.findAll(pageable)
				 .map(mapperInterface::candidatToCandidateResponse);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public DetailCandidatResponse candidatParId(Long candidatId) {

		Candidate candidat = candidatRepository.findById(candidatId)
				.orElseThrow(() -> new CandidatNotFoundException("Candidat non trouvé"));

		return mapperInterface.candidatToDetailCandidat(candidat);
	}

	@Override
	@PreAuthorize("hasRole('CANDIDAT')")
	@Transactional
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
		Candidate candidat = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		// 4. supprimer ancien CV
		if (candidat.getCvUrl() != null && !candidat.getCvUrl().isBlank()) {

			fileStorageService.delete(candidat.getCvUrl());
		}

		// 5. sauvegarder nouveau CV
		String fileName = fileStorageService.save(file);

		// 6. update DB
		candidat.setCvUrl(fileName);

		Candidate saved = candidatRepository.save(candidat);

		// 7. mapping response
		return mapperInterface.candidatToDetailCandidat(saved);

	}
}
