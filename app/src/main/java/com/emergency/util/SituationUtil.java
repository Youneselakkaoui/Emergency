package com.emergency.util;

import com.emergency.dto.SituationDTO;
import com.emergency.entity.RecepteursSituation;
import com.emergency.entity.Situation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elmehdiharabida on 06/06/15.
 */
public class SituationUtil {

	public static SituationDTO situationToSituationDTO (Situation situation) {

		if (situation != null) {
			SituationDTO situationDTO = new SituationDTO();
			situationDTO.setIdSituation(situation.getIdSituation());
			situationDTO.setMessage(situation.getMessage());
			situationDTO.setPiecesJointes(situation.getPiecesJointes());

			List<String> recepteurs = new ArrayList<String>();
			if (situation.getRecepteursSituations() != null) {
				for (RecepteursSituation recepteurSituation : situation.getRecepteursSituations())
					recepteurs.add(recepteurSituation.getNumUser());
			}

			situationDTO.setRecepteursSituations(recepteurs);
			situationDTO.setTitre(situation.getTitre());
			situationDTO.setTypeEnvoi(situation.getTypeEnvoi());
			//User
			situationDTO.setUser(UserUtil.mapUserToUserDTO(situation.getUser()));
			return situationDTO;
		}
		return null;

	}
}
