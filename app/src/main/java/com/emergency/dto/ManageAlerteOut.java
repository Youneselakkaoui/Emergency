package com.emergency.dto;

import java.io.Serializable;

/**
 * Created by Noureddine on 11/05/2015.
 */
public class ManageAlerteOut implements Serializable {

    /**
     * eventuelle anomalie
     */
    private EmergencyAnomalieDTO anomalie;
    /**
     * Donnees utilisateur
     */
    private AlerteDTO alerteDTO;

    public EmergencyAnomalieDTO getAnomalie() {
        return anomalie;
    }


    public void setAnomalie(final EmergencyAnomalieDTO anomalie) {
        this.anomalie = anomalie;
    }

    public AlerteDTO getAlerteDTO() {
        return alerteDTO;
    }

    public void setAlerteDTO(AlerteDTO alerteDTO) {
        this.alerteDTO = alerteDTO;
    }
}
