package com.moinammaoueni.smartHire.api.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.moinammaoueni.smartHire.api.exception.AlreadyAppliedException;
import com.moinammaoueni.smartHire.api.exception.CandidatNotFoundException;
import com.moinammaoueni.smartHire.api.exception.JobNotFoundException;
import com.moinammaoueni.smartHire.api.exception.PostulationNotFoundException;
import com.moinammaoueni.smartHire.api.mappers.MapperInterface;
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
	public ApplicationResponse postuler(Long jobId) {

		// 1. user connecté
		Long userId = currentUser.getId();

		// 2. candidat
		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		// 3. job
		Job job = jobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException("Offre introuvable"));

		//  déjà postulé ?
		boolean alreadyApplied = applicationRepository.existsByCandidateIdAndJobId(candidate.getId(), jobId);

		if (alreadyApplied) {

			throw new AlreadyAppliedException("Vous avez déjà postulé");
		}


		// 7. save application
		Application application = Application.builder().candidate(candidate).job(job).build();

		Application saved = applicationRepository.save(application);

		return mapperInterface.applicationToResponse(saved);

	}

	
	@Override
	@PreAuthorize("hasRole('CANDIDAT')")
	public List<PostulationResponse> mesPostulations() {

		Long userId = currentUser.getId();

		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		return applicationRepository.findByCandidateId(candidate.getId()).stream()
				.map(mapperInterface::applicationToPostulation).toList();
	}

	@Override
	@PreAuthorize("hasRole('CANDIDAT')")
	public ApplicationResponse detailPostulation(Long applicationId) {

		// 1. current user
		Long userId = currentUser.getId();

		// 2. candidat connecté
		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		// 3. postulation
		Application application = applicationRepository.findByIdAndCandidateId(applicationId, candidate.getId())
				.orElseThrow(() -> new PostulationNotFoundException("Postulation introuvable"));

		
		// 4. mapping
		return mapperInterface.applicationToResponse(application);

	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public Page<PostulationResponse> toutesPostulations(Pageable pageable) {

		return applicationRepository.findAll(pageable).map(mapperInterface::applicationToPostulation);
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public ApplicationResponse detail(Long id) {

		Application application = applicationRepository.findById(id)
				.orElseThrow(() -> new PostulationNotFoundException("Postulation introuvable"));

		 return mapperInterface.applicationToResponse(application);

	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public ApplicationResponse changerStatut(Long id, StatutApplication statut) {

		Application application = applicationRepository.findById(id)
				.orElseThrow(() -> new PostulationNotFoundException("Postulation introuvable"));

		application.setStatut(statut);

		Application saved = applicationRepository.save(application);

		return mapperInterface.applicationToResponse(saved);

	}

}