package com.moinammaoueni.smartHire.api.dto;



import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class JobUpdate {
	

	    private String titre;

	    private String description;

	    private Integer experienceMin;

}
