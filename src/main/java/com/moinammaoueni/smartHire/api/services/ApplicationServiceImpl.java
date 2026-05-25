package com.moinammaoueni.smartHire.api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.moinammaoueni.smartHire.api.config.CurrentUser;
import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;
import com.moinammaoueni.smartHire.api.entity.Application;
import com.moinammaoueni.smartHire.api.entity.Candidate;
import com.moinammaoueni.smartHire.api.entity.Job;
import com.moinammaoueni.smartHire.api.exception.CandidatNotFoundException;
import com.moinammaoueni.smartHire.api.exception.JobNotFoundException;
import com.moinammaoueni.smartHire.api.exception.UserAlreadyExistsException;
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

	@Override
	public ApplicationResponse postuler(Long jobId) {

		Long userId = currentUser.getId();

		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		Job job = jobRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException("Offre introuvable"));

		boolean alreadyApplied = applicationRepository.existsByCandidateIdAndJobId(candidate.getId(), jobId);

		if (alreadyApplied) {
			throw new RuntimeException("Vous avez déjà postulé");
		}

		Application application = Application.builder().candidate(candidate).job(job).appliedAt(LocalDateTime.now())
				.statut(StatutApplication.EN_ATTENTE).build();

		Application saved = applicationRepository.save(application);

		return mapperInterface.applicationToResponse(saved);
	}

	@Override
	public List<ApplicationResponse> mesPostulations() {

		Long userId = currentUser.getId();

		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		return applicationRepository.findByCandidateId(candidate.getId()).stream()
				.map(mapperInterface::applicationToResponse).toList();
	}

	@Override
	public ApplicationResponse detailPostulation(Long applicationId) {

		Long userId = currentUser.getId();

		Candidate candidate = candidatRepository.findByUtilisateurId(userId)
				.orElseThrow(() -> new CandidatNotFoundException("Profil candidat introuvable"));

		Application application = applicationRepository.findByIdAndCandidateId(applicationId, candidate.getId())
				.orElseThrow(() -> new RuntimeException("Postulation introuvable"));

		return mapperInterface.applicationToResponse(application);
	}
}