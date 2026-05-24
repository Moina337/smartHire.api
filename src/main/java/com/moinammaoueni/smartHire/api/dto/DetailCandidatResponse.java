package com.moinammaoueni.smartHire.api.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DetailCandidatResponse {

    private Long id;

    // infos compte
    private String nom;
    private String email;

    // infos candidat
    private String titreProfil;
    private Integer experience;
    private String cvUrl;
    private List<String> competences;
}