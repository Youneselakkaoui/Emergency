package com.emergency.util;

import com.emergency.dto.AlerteDTO;
import com.emergency.dto.ManageAlerteOut;
import com.emergency.entity.AlerteRecue;
import com.emergency.entity.SituationRecue;
import com.emergency.entity.UserRecu;

/**
 * Created by elmehdiharabida on 01/06/15.
 */
public class AlerteRecueUtil {

	public static AlerteRecue manageAlereOutToalerteRecueMapper(ManageAlerteOut manageAlerteOut){
		AlerteDTO alerte = manageAlerteOut.getAlerteDTO();
		//initialisation
		AlerteRecue alerteRecue = new AlerteRecue();
		alerteRecue.setSituation(new SituationRecue());
		alerteRecue.getSituation().setUser(new UserRecu());
		//user shit
		alerteRecue.getSituation().getUser().setDateNaissance(alerte.getSituation().getUser().getDateNaissance());
		alerteRecue.getSituation().getUser().setTelephone(alerte.getSituation().getUser().getTelephone());
		alerteRecue.getSituation().getUser().setGroupSanguin(alerte.getSituation().getUser().getGroupSanguin());
		alerteRecue.getSituation().getUser().setAutresInfos(alerte.getSituation().getUser().getAutresInfos());
		alerteRecue.getSituation().getUser().setCholesterol(alerte.getSituation().getUser().getCholesterol());
		alerteRecue.getSituation().getUser().setDiabete(alerte.getSituation().getUser().getDiabete());
		alerteRecue.getSituation().getUser().setGcmDeviceId(alerte.getSituation().getUser().getGcmDeviceId());
		alerteRecue.getSituation().getUser().setNom(alerte.getSituation().getUser().getNom());
		alerteRecue.getSituation().getUser().setPrenom(alerte.getSituation().getUser().getPrenom());
		alerteRecue.getSituation().getUser().setSexe(alerte.getSituation().getUser().getSexe());
		//Situation shit
		alerteRecue.getSituation().setTitre(alerte.getSituation().getTitre());
		alerteRecue.getSituation().setIdSituation(alerte.getSituation().getIdSituation());
		alerteRecue.getSituation().setMessage(alerte.getSituation().getMessage());
		alerteRecue.getSituation().setTypeEnvoi(alerte.getSituation().getTypeEnvoi());
		alerteRecue.getSituation().setPiecesJointes(alerte.getSituation().getPiecesJointes());
		//alerte shit
		alerteRecue.setDateEnvoi(alerte.getDateEnvoi());
		alerteRecue.setIdAlerte(alerte.getIdAlerte());
		alerteRecue.setStatut(alerte.getStatut());
		alerteRecue.setLocalisationEmX(alerte.getLocalisationEmX());
		alerteRecue.setLocalisationEmY(alerte.getLocalisationEmY());


		return alerteRecue;
	}

}
