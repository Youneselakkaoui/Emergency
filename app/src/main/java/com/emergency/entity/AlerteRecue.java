package com.emergency.entity;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by elmehdiharabida on 31/05/15.
 */
public class AlerteRecue extends SugarRecord<AlerteRecue> {

	private int idAlerte;

	private Date dateEnvoi;

	private String localisationEmX;

	private String localisationEmY;

	private short statut;

	public SituationRecue getSituation () {
		return situation;
	}

	public void setSituation (SituationRecue situation) {
		this.situation = situation;
	}

	public Date getDateEnvoi () {
		return dateEnvoi;
	}

	public void setDateEnvoi (Date dateEnvoi) {
		this.dateEnvoi = dateEnvoi;
	}

	public String getLocalisationEmX () {
		return localisationEmX;
	}

	public void setLocalisationEmX (String localisationEmX) {
		this.localisationEmX = localisationEmX;
	}

	public String getLocalisationEmY () {
		return localisationEmY;
	}

	public void setLocalisationEmY (String localisationEmY) {
		this.localisationEmY = localisationEmY;
	}

	public short getStatut () {
		return statut;
	}

	public void setStatut (short statut) {
		this.statut = statut;
	}

	private SituationRecue situation;

	public int getIdAlerte () {
		return idAlerte;
	}

	public void setIdAlerte (int idAlerte) {
		this.idAlerte = idAlerte;
	}
}
