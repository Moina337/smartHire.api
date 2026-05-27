package com.moinammaoueni.smartHire.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.moinammaoueni.smartHire.api.dto.JobRequest;
import com.moinammaoueni.smartHire.api.dto.JobResponse;
import com.moinammaoueni.smartHire.api.entity.Job;
import com.moinammaoueni.smartHire.api.exception.JobNotFoundException;
import com.moinammaoueni.smartHire.api.mappers.MapperInterface;
import com.moinammaoueni.smartHire.api.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final MapperInterface mapperInterface;


    @Override
    public JobResponse creerUnJob(JobRequest jobRequest) {

        Job job = mapperInterface.jobRequestToJob(jobRequest);

        Job savedJob = jobRepository.save(job);

        return mapperInterface.jobToJobResponse(savedJob);
    }

    @Override
    public Page<JobResponse>
    tousJobs(
            String keyword,
            Pageable pageable) {

        Page<Job> jobs;

        if(keyword != null
                && !keyword.isBlank()) {

            jobs =
                jobRepository
                .findByTitreContainingIgnoreCase(
                        keyword,
                        pageable);

        } else {

            jobs =
                jobRepository
                .findAll(pageable);
        }

        return jobs.map(
                mapperInterface::jobToJobResponse);
    }

	@Override
	public JobResponse afficheJobParId(long job_id) {
		// TODO Auto-generated method stub
		
		Job job = jobRepository.findById(job_id)
				.orElseThrow(() ->
			    new JobNotFoundException(
			        "Offre avec id "
			        + job_id +
			        " introuvable"
			    )
			);
		
		return mapperInterface.jobToJobResponse(job);
	}
    
    
}