package com.emergency.entity;

import com.emergency.dto.AlerteDTO;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the suivi_alerte database table.
 *
 */

public class SuiviAlerte implements Serializable {
	private static final long serialVersionUID = 1L;

	private Alerte alerte;

	private Date dateMaj;

	private short etatSuivi;


	private String localisationReX;

	private String localisationReY;


	private User user;

	public SuiviAlerte() {
	}

	public Alerte getAlerte() {
		return this.alerte;
	}

	public Date getDateMaj() {
		return this.dateMaj;
	}

	public short getEtatSuivi() {
		return this.etatSuivi;
	}


	public String getLocalisationReX() {
		return this.localisationReX;
	}

	public String getLocalisationReY() {
		return this.localisationReY;
	}

	public User getUser() {
		return this.user;
	}

	public void setAlerte(final AlerteDTO alerte) {
		this.alerte = alerte;
	}

	public void setDateMaj(final Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	public void setEtatSuivi(final short etatSuivi) {
		this.etatSuivi = etatSuivi;
	}

	public void setLocalisationReX(final String localisationReX) {
		this.localisationReX = localisationReX;
	}

	public void setLocalisationReY(final String localisationReY) {
		this.localisationReY = localisationReY;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}