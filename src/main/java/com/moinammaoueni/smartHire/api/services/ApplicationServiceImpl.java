package com.moinammaoueni.smartHire.api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.moinammaoueni.smartHire.api.config.CurrentUser;
import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;
import com.moinammaoueni.smartHire.api.dto.PostulationResponse;
import com.moinammaoueni.smartHire.api.entity.Application;
import com.moinammaoueni.smartHire.api.entity.Candidate;
import com.moinammaoueni.smartHire.api.entity.Job;
import com.moinammaoueni.smartHire.api.exception.CandidatNotFoundException;
import com.moinammaoueni.smartHire.api.exception.JobNotFoundException;
import com.moinammaoueni.smartHire.api.exception.UserAlreadyExistsException;
import com.moinammaoueni.smartHire.api.mappers.MapperInterface;
import com.moinammaoueni.smartHire.api.num.JobStatus;
import com.moinammaoueni.smartHire.api.num.StatutApplication;
import com.moinammaoueni.smartHire.api.repository.ApplicationRepository;
import com.moinammaoueni.smartHire.api.repository.CandidatRepository;
import com.moinammaoueni.smartHire.api.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

	private final ApplicationRepository applicationRepository;
	private final CandidatRepository candidatRepository;
	private final JobRepository jobRepository;
	private final CurrentUser currentUser;
	private final MapperInterface mapperInterface;
	private final FileStorageService fileStorageService;

	@Override
	@PreAuthorize("hasRole('CANDIDAT')")
	@Transactional
	public ApplicationResponse postuler(Long jobId, MultipartFile cvFile) {

		// 1. user connecté
		Long userId = currentUser.getId();

		// 2. candidat
		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		// 3. job
		Job job = jobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException("Offre introuvable"));

		// 4. déjà postulé ?
		boolean alreadyApplied = applicationRepository.existsByCandidateIdAndJobId(candidate.getId(), jobId);

		if (alreadyApplied) {

			throw new RuntimeException("Vous avez déjà postulé");
		}

		// 5. CV obligatoire
		if (cvFile == null || cvFile.isEmpty()) {

			throw new RuntimeException("CV obligatoire");
		}

		// 6. upload CV
		String cvFileName = fileStorageService.save(cvFile);

		// 7. save application
		Application application = Application.builder().candidate(candidate).job(job).cvFileName(cvFileName).build();

		Application saved = applicationRepository.save(application);

		ApplicationResponse response = mapperInterface.applicationToResponse(saved);

		response.setCvFileUrl(fileStorageService.getUrl(cvFileName));

		return response;
	}

	@Override
	public List<PostulationResponse> mesPostulations() {

		Long userId = currentUser.getId();

		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		return applicationRepository.findByCandidateId(candidate.getId()).stream()
				.map(mapperInterface::applicationToPostulation).toList();
	}

	@Override
	public ApplicationResponse detailPostulation(Long applicationId) {

		// 1. current user
		Long userId = currentUser.getId();

		// 2. candidat connecté
		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		// 3. postulation
		Application application = applicationRepository.findByIdAndCandidateId(applicationId, candidate.getId())
				.orElseThrow(() -> new RuntimeException("Postulation introuvable"));

		System.out.println(application.getCvFileName());

		// 4. mapping
		ApplicationResponse response = mapperInterface.applicationToResponse(application);

		// 5. enrichir URL CV
		if (application.getCvFileName() != null && !application.getCvFileName().isBlank()) {

			response.setCvFileUrl(fileStorageService.getUrl(application.getCvFileName()));

		}

		return response;
	}

	
	
	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<PostulationResponse> toutesPostulations() {

		return applicationRepository.findAll().stream().map(mapperInterface::applicationToPostulation).toList();
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public ApplicationResponse detail(Long id) {

		Application application = applicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Postulation introuvable"));

		ApplicationResponse response = mapperInterface.applicationToResponse(application);

		if (application.getCvFileName() != null && !application.getCvFileName().isBlank()) {

			response.setCvFileUrl(fileStorageService.getUrl(application.getCvFileName()));
		}

		return response;
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public ApplicationResponse changerStatut(Long id, StatutApplication statut) {

		Application application = applicationRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Postulation introuvable"));

		application.setStatut(statut);

		Application saved = applicationRepository.save(application);

		ApplicationResponse response = mapperInterface.applicationToResponse(saved);

		if (saved.getCvFileName() != null && !saved.getCvFileName().isBlank()) {

			response.setCvFileUrl(fileStorageService.getUrl(saved.getCvFileName()));
		}

		return response;
	}

}