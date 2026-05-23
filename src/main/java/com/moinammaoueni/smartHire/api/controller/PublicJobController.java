package com.moinammaoueni.smartHire.api.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.services.JobService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/public/jobs")
@RequiredArgsConstructor
@Tag(
    name = "PUBLIC JOBS",
    description = "Consultation publique des offres d'emploi"
)
public class PublicJobController {
	
	//GET /api/public/jobs
	//GET /api/public/jobs/{id}
	
	public final JobService jobService;
	
	
	
	@GetMapping
	public  ResponseEntity< List<JobResponse>> listJob() {
		
		List<JobResponse> jobs = jobService.listeJob();
		
		return ResponseEntity.ok(jobs);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JobResponse> afficheJobParId(Long job_id) {
		
		JobResponse response = jobService.afficheJobParId(job_id);
		
		return ResponseEntity.ok(response);
	}

}
