package com.emergency.entity;

import com.orm.SugarRecord;

/**
 * Created by elmehdiharabida on 31/05/15.
 */
public class SituationRecue extends SugarRecord<SituationRecue> {

	private int idSituation;
	private String message;
	private short piecesJointes;
	private String titre;
	private short typeEnvoi;
	private UserRecu user;

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

	public UserRecu getUser () {
		return user;
	}

	public void setUser (UserRecu userRecu) {
		this.user = userRecu;
	}
}
