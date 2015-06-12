package com.emergency.util;

import com.emergency.dto.AlerteDTO;
import com.emergency.dto.ManageAlerteIn;
import com.emergency.entity.Alerte;

/**
 * Created by elmehdiharabida on 06/06/15.
 */
public class AlerteUtil {

	public static ManageAlerteIn alerteToManageAlertInMapper (short codeFonction, Alerte alerte) {
		if (alerte != null) {
			ManageAlerteIn alerteIn = new ManageAlerteIn();
			
			//infos alerte
			alerteIn.setCodeFonction(codeFonction);
			alerteIn.setAlerteDTO(alerteToAlerteDTOMapper(alerte));
			return alerteIn;
		}
		return null;
	}

	public static AlerteDTO alerteToAlerteDTOMapper (Alerte alerte) {
		if (alerte != null) {
			AlerteDTO alerteDTO = new AlerteDTO();
			alerte.setIdAlerte(alerte.getIdAlerte());
			alerteDTO.setDateEnvoi(alerte.getDateEnvoi());
			alerteDTO.setLocalisationEmX(alerte.getLocalisationEmX());
			alerteDTO.setLocalisationEmY(alerte.getLocalisationEmY());
			alerteDTO.setStatut(alerte.getStatut());
			//situation
			alerteDTO.setSituation(SituationUtil.situationToSituationDTO(alerte.getSituation()));
			return alerteDTO;
		}
		return null;
	}
}
