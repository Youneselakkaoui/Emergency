package com.emergency.dto;

import java.io.Serializable;

/**
 * Created by Noureddine on 11/05/2015.
 */
public class ManageAlerteIn implements Serializable {

    private short codeFonction;
    // A voir
    // /**
    // * locale
    // */
    // private Locale locale;
    /**
     * Objet user a creer ou mettre a jour
     */
    private AlerteDTO alerteDTO;

    public short getCodeFonction() {
        return codeFonction;
    }

    public void setCodeFonction(final short codeFonction) {
        this.codeFonction = codeFonction;
    }

    public AlerteDTO getAlerteDTO() {
        return alerteDTO;
    }

    public void setAlerteDTO(final AlerteDTO alerteDTO) {
        this.alerteDTO = alerteDTO;
    }
}
