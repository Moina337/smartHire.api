package com.moinammaoueni.smartHire.api.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.moinammaoueni.smartHire.api.num.JobStatus;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(length = 2000)
    private String description;

    private Integer experienceMin;

    @ElementCollection
    private List<String> competencesRequises = new ArrayList<>();

    private LocalDate datePublication;

    private LocalDate dateExpiration;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @OneToMany(mappedBy = "job")
    private List<Application> applications = new ArrayList<>();

    @PrePersist
    public void prePersist() {

        if (datePublication == null) {
            datePublication = LocalDate.now();
        }

        if (status == null) {
            status = JobStatus.OUVERTE;
        }
    }
}