package com.moinammaoueni.smartHire.api.entity;


import java.time.LocalDate;
import java.util.List;

import com.moinammaoueni.smartHire.api.num.JobStatus;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    private String description;

    private Integer experienceMin;

    @ElementCollection
    private List<String> competencesRequises;

    private LocalDate datePublication;
   

    private LocalDate dateExpiration;

    private JobStatus status;
}