package com.moinammaoueni.smartHire.api.entity;

import java.time.LocalDateTime;

import com.moinammaoueni.smartHire.api.num.StatutApplication;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // date de postulation
    private LocalDateTime appliedAt;

    // statut de la candidature
    @Enumerated(EnumType.STRING)
    private StatutApplication statut;

    // candidat qui postule
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    // message / lettre de motivation
    @Column(length = 2000)
    private String message;

    // audit
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {

        LocalDateTime now = LocalDateTime.now();

        this.appliedAt = now;
        this.createdAt = now;

        if (this.statut == null) {
            this.statut = StatutApplication.EN_ATTENTE;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}