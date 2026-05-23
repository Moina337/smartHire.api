package com.moinammaoueni.smartHire.api.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CandidateResponse {

    private Long id;
    private String nom;
    private String titreProfil;
    private Integer experience;
    private List<String> competences;
}
