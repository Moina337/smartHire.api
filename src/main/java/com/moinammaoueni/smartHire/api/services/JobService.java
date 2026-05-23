package com.moinammaoueni.smartHire.api.services;



import java.util.List;

import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;

public interface JobService {
	
	JobResponse creerUnJob(JobRequest jobRequest);
	
	List<JobResponse> listeJob();
	
	JobResponse afficheJobParId(long jib_id);
	
}
