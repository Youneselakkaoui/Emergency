package com.emergency.emergency.dto;

import java.io.Serializable;

/**
 * Anomalie retournee par le service Emergency
 *
 * @author elmehdiharabida
 *
 */
public class EmergencyAnomalieDTO extends EmergencyDTO {

    /**
     *
     */
    private static final long serialVersionUID = 6737620856212743891L;
    /**
     * Code anomalie
     */
    private String codeAnomalie;
    /**
     * Libelle anomalie
     */
    private String libelleAnomalie;

    public String getCodeAnomalie() {
        return codeAnomalie;
    }

    public String getLibelleAnomalie() {
        return libelleAnomalie;
    }

    public void setCodeAnomalie(final String string) {
        this.codeAnomalie = string;
    }

    public void setLibelleAnomalie(final String libelleAnomalie) {
        this.libelleAnomalie = libelleAnomalie;
    }

}
