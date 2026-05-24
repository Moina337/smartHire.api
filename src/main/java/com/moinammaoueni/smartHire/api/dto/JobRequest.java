package com.moinammaoueni.smartHire.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JobRequest {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @Min(value = 0, message = "L'expérience minimale ne peut pas être négative")
    private Integer experienceMin;

    @NotEmpty(message = "Les compétences sont obligatoires")
    private List<@NotBlank(message = "Une compétence ne peut pas être vide") String> competencesRequises;
}