package com.moinammaoueni.smartHire.api.entity;

import java.util.List;





import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titreProfil;

    private Integer experience;

    private String cvUrl;

    @ElementCollection
    private List<String> competences;

    @OneToOne
    private Utilisateur utilisateur;
    
    @OneToMany(mappedBy = "candidate")
    private List<Application> applications;
}
