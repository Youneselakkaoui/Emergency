package com.emergency.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the recepteurs_situation database table.
 *
 */

public class RecepteursSituation implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date dateCreation;

	private Date dateModification;


	private Situation situation;

	private User user;

	public RecepteursSituation() {
	}

	public Date getDateCreation() {
		return this.dateCreation;
	}

	public Date getDateModification() {
		return this.dateModification;
	}


	public Situation getSituation() {
		return this.situation;
	}

	public User getUser() {
		return this.user;
	}

	public void setDateCreation(final Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public void setDateModification(final Date dateModification) {
		this.dateModification = dateModification;
	}


	public void setSituation(final Situation situation) {
		this.situation = situation;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}