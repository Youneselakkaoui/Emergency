package com.emergency.dto;

import java.util.List;

/**
 * Created by elmehdiharabida on 06/06/15.
 */
public class SituationDTO {
	private int idSituation;

	private String message;

	private short piecesJointes;

	/**
	 * Liste des numeros de telephone des recepteurs
	 */
	private List<String> recepteursSituations;

	private String titre;

	private short typeEnvoi;

	private UserDTO user;

	public int getIdSituation () {
		return idSituation;
	}

	public void setIdSituation (int idSituation) {
		this.idSituation = idSituation;
	}

	public String getMessage () {
		return message;
	}

	public void setMessage (String message) {
		this.message = message;
	}

	public short getPiecesJointes () {
		return piecesJointes;
	}

	public void setPiecesJointes (short piecesJointes) {
		this.piecesJointes = piecesJointes;
	}

	public List<String> getRecepteursSituations () {
		return recepteursSituations;
	}

	public void setRecepteursSituations (List<String> recepteursSituations) {
		this.recepteursSituations = recepteursSituations;
	}

	public String getTitre () {
		return titre;
	}

	public void setTitre (String titre) {
		this.titre = titre;
	}

	public short getTypeEnvoi () {
		return typeEnvoi;
	}

	public void setTypeEnvoi (short typeEnvoi) {
		this.typeEnvoi = typeEnvoi;
	}

	public UserDTO getUser () {
		return user;
	}

	public void setUser (UserDTO user) {
		this.user = user;
	}
}
