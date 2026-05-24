package com.moinammaoueni.smartHire.api.services;

import java.util.List;

import com.moinammaoueni.smartHire.api.dto.CandidateRequest;
import com.moinammaoueni.smartHire.api.dto.CandidateResponse;
import com.moinammaoueni.smartHire.api.dto.DetailCandidatResponse;

public interface CandidatService {

    // compléter ou créer profil candidat connecté
    CandidateResponse completerProfilCandidat(
            CandidateRequest request);

    // voir mon profil candidat
    DetailCandidatResponse monProfil();

    // modifier mon profil
    CandidateResponse modifierProfil(
            CandidateRequest request);

    // supprimer mon profil
    void supprimerProfil();

    // liste candidats (ADMIN / RECRUTEUR)
    List<CandidateResponse> listeCandidats();

    // voir candidat par id (ADMIN / RECRUTEUR)
    DetailCandidatResponse candidatParId(Long candidatId);
}