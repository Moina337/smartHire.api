package com.moinammaoueni.smartHire.api.services;

import java.util.List;

import com.moinammaoueni.smartHire.api.dto.ApplicationResponse;

public interface ApplicationService {

    ApplicationResponse postuler(Long jobId);

    List<ApplicationResponse> mesPostulations();

    ApplicationResponse detailPostulation(
            Long applicationId
    );
}