package com.moinammaoueni.smartHire.api.service;

import com.moinammaoueni.smartHire.api.config.CurrentUser;
import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;
import com.moinammaoueni.smartHire.api.entity.Application;
import com.moinammaoueni.smartHire.api.entity.Candidate;
import com.moinammaoueni.smartHire.api.entity.Job;
import com.moinammaoueni.smartHire.api.exception.AlreadyAppliedException;
import com.moinammaoueni.smartHire.api.exception.JobNotFoundException;
import com.moinammaoueni.smartHire.api.mappers.MapperInterface;
import com.moinammaoueni.smartHire.api.repository.ApplicationRepository;
import com.moinammaoueni.smartHire.api.repository.CandidatRepository;
import com.moinammaoueni.smartHire.api.repository.JobRepository;
import com.moinammaoueni.smartHire.api.services.ApplicationService;
import com.moinammaoueni.smartHire.api.services.ApplicationServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

	@InjectMocks
	private ApplicationServiceImpl applicationService;

	@Mock
	private ApplicationRepository applicationRepository;

	@Mock
	private JobRepository jobRepository;

	@Mock
	private CurrentUser currentUser;

	@Mock
	private CandidatRepository candidatRepository;

	@Mock
	private MapperInterface mapperInterface;

	// ✅ POSTULATION OK
	@Test
	void doitPostulerAvecSucces() {

		// user connecté
		when(currentUser.getId()).thenReturn(1L);

		// candidat
		Candidate candidate = new Candidate();
		candidate.setId(1L);

		when(candidatRepository.findByUtilisateurId(1L)).thenReturn(Optional.of(candidate));

		// job
		Job job = new Job();
		job.setId(1L);

		when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

		// pas déjà postulé
		when(applicationRepository.existsByCandidateIdAndJobId(1L, 1L)).thenReturn(false);

		// save application
		Application application = Application.builder().candidate(candidate).job(job).build();

		when(applicationRepository.save(any(Application.class))).thenReturn(application);

		// mapper
		ApplicationResponse response = new ApplicationResponse();

		when(mapperInterface.applicationToResponse(any(Application.class))).thenReturn(response);

		// appel
		ApplicationResponse result = applicationService.postuler(1L);

		// vérification
		assertNotNull(result);

		verify(applicationRepository).save(any(Application.class));
	}

	// ❌ 2. DEJA POSTULE
	@Test
	void shouldThrowExceptionWhenAlreadyApplied() {

		// user connecté
		when(currentUser.getId()).thenReturn(1L);

		// candidat existe
		Candidate candidate = new Candidate();
		candidate.setId(1L);

		when(candidatRepository.findByUtilisateurId(1L)).thenReturn(Optional.of(candidate));

		// job existe
		Job job = new Job();
		job.setId(1L);

		when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

		// déjà postulé
		when(applicationRepository.existsByCandidateIdAndJobId(1L, 1L)).thenReturn(true);

		// vérification exception précise
		AlreadyAppliedException exception = assertThrows(AlreadyAppliedException.class,
				() -> applicationService.postuler(1L));

		assertEquals("Vous avez déjà postulé", exception.getMessage());
	}

	// ❌ 3. JOB INEXISTANT
	@Test
	void shouldThrowExceptionWhenJobNotFound() {

		// user connecté
		when(currentUser.getId()).thenReturn(1L);

		// candidat existe
		Candidate candidate = new Candidate();
		candidate.setId(1L);

		when(candidatRepository.findByUtilisateurId(1L)).thenReturn(Optional.of(candidate));

		// job absent
		when(jobRepository.findById(1L)).thenReturn(Optional.empty());

		// exception attendue
		JobNotFoundException exception = assertThrows(JobNotFoundException.class,
				() -> applicationService.postuler(1L));

		assertEquals("Offre introuvable", exception.getMessage());
	}
}