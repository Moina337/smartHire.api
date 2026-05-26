package com.moinammaoueni.smartHire.api.dto;

import com.moinammaoueni.smartHire.api.num.StatutApplication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateApplicationStatusRequest {

    private StatutApplication statut;
}