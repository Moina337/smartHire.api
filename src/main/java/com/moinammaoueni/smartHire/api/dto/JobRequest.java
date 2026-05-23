package com.moinammaoueni.smartHire.api.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequest {

    private String titre;
    private String description;
    private Integer experienceMin;
    private List<String> competencesRequises;
}
