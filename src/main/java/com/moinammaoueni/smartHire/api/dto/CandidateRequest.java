package com.moinammaoueni.smartHire.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRequest {

    private String titreProfil;
    private Integer experience;
    private List<String> competences;
    
}
