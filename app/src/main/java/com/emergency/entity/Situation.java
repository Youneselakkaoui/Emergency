package com.emergency.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the situation database table.
 */

public class Situation extends SugarRecord<Situation> {


	private int idSituation;
	private String message;
	private short piecesJointes;
	private String titre;
	private int niveau;

	public int getNiveau () {
		return niveau;
	}

	public void setNiveau (int niveau) {
		this.niveau = niveau;
	}

	private short typeEnvoi;
	@Ignore
	private List<RecepteursSituation> recepteursSituations;
	private User user;
	private Date dateLastModif;
	private Date dateSync;

	public Date getDateLastModif () {
		return dateLastModif;
	}

	public void setDateLastModif (Date dateLastModif) {
		this.dateLastModif = dateLastModif;
	}

	public Date getDateSync () {
		return dateSync;
	}

	public void setDateSync (Date dateSync) {
		this.dateSync = dateSync;
	}

	public Situation () {

	}

	public Situation (int idSituation, String emetteur, String titre, String message, short piecesJointes, short typeEnvoi) {
		this.idSituation = idSituation;
		this.message = message;
		this.piecesJointes = piecesJointes;
		this.titre = titre;
		this.typeEnvoi = typeEnvoi;
		this.user = new User();
		user.setTelephone(emetteur);
	}

	public int getIdSituation () {
		return this.idSituation;
	}

	public void setIdSituation (int idSituation) {
		this.idSituation = idSituation;
	}

	public String getMessage () {
		return this.message;
	}

	public void setMessage (String message) {
		this.message = message;
	}

	public short getPiecesJointes () {
		return this.piecesJointes;
	}

	public void setPiecesJointes (short piecesJointes) {
		this.piecesJointes = piecesJointes;
	}

	public String getTitre () {
		return this.titre;
	}

	public void setTitre (String titre) {
		this.titre = titre;
	}

	public short getTypeEnvoi () {
		return this.typeEnvoi;
	}

	public void setTypeEnvoi (short typeEnvoi) {
		this.typeEnvoi = typeEnvoi;
	}


	public List<RecepteursSituation> getRecepteursSituations () {
		if (recepteursSituations == null) {
			recepteursSituations = new ArrayList<RecepteursSituation>();
		}
		return this.recepteursSituations;
	}

	public void setRecepteursSituations (List<RecepteursSituation> recepteursSituations) {
		this.recepteursSituations = recepteursSituations;
	}

	public RecepteursSituation addRecepteursSituation (RecepteursSituation recepteursSituation) {
		getRecepteursSituations().add(recepteursSituation);
		recepteursSituation.setSituation(this);

		return recepteursSituation;
	}

	public RecepteursSituation removeRecepteursSituation (RecepteursSituation recepteursSituation) {
		getRecepteursSituations().remove(recepteursSituation);
		recepteursSituation.setSituation(null);

		return recepteursSituation;
	}

	public User getUser () {
		return this.user;
	}

	public void setUser (User user) {
		this.user = user;
	}

}