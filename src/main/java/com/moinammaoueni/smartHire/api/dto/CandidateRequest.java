package com.moinammaoueni.smartHire.api.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateRequest {

	@NotBlank(message = "Le titre du profil est obligatoire")
	@Size(max = 100, message = "Titre du profil trop long")
	private String titreProfil;

	@Min(value = 0, message = "L'expérience ne peut pas être négative")
	private Integer experience;

	@NotEmpty(message = "Au moins une compétence est requise")
	private List<@NotBlank(message = "Une compétence ne peut pas être vide") String> competences;
}