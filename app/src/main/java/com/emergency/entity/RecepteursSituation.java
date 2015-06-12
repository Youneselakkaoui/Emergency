package com.emergency.entity;

import com.orm.SugarRecord;



/**
 * The persistent class for the recepteurs_situation database table.
 */

public class RecepteursSituation extends SugarRecord<RecepteursSituation> {
	private Situation situation;
	private String numUser;


	public Situation getSituation () {
		return situation;
	}

	public void setSituation (Situation situation) {
		this.situation = situation;
	}

	public String getNumUser () {
		return numUser;
	}

	public void setNumUser (String numUser) {
		this.numUser = numUser;
	}
}