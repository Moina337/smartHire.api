package com.moinammaoueni.smartHire.api.services;

import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {
	
	JobResponse creerUnJob(JobRequest jobRequest);
	
	Page<JobResponse> tousJobs(
	        Pageable pageable);
	
	JobResponse afficheJobParId(long jib_id);
	
}
