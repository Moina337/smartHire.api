package com.moinammaoueni.smartHire.api.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobResponse {

    private Long id;

    private String titre;

    private String description;

    private Integer experienceMin;

    private List<String> competencesRequises;
}