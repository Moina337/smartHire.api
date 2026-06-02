package com.moinammaoueni.smartHire.api.dto;

import java.time.LocalDateTime;

import com.moinammaoueni.smartHire.api.num.StatutApplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApplicationResponse {

    private Long id;

    private Long jobId;

    private String jobTitre;

    private Long candidateId;

    private String candidateName;

    private String candidateEmail;

    private StatutApplication statut;

    private LocalDateTime appliedAt;

    private String cvFileUrl;

}