package com.emergency.entity;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by elmehdiharabida on 31/05/15.
 */
public class UserRecu extends SugarRecord<UserRecu> {
	private String telephone;
	private String autresInfos;
	private short cholesterol;
	private Date dateNaissance;
	private short diabete;
	private short groupSanguin;
	private String nom;
	private String prenom;
	private short sexe;
	private String gcmDeviceId;

	public String getTelephone () {
		return telephone;
	}

	public void setTelephone (String telephone) {
		this.telephone = telephone;
	}

	public String getAutresInfos () {
		return autresInfos;
	}

	public void setAutresInfos (String autresInfos) {
		this.autresInfos = autresInfos;
	}

	public short getCholesterol () {
		return cholesterol;
	}

	public void setCholesterol (short cholesterol) {
		this.cholesterol = cholesterol;
	}

	public Date getDateNaissance () {
		return dateNaissance;
	}

	public void setDateNaissance (Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public short getDiabete () {
		return diabete;
	}

	public void setDiabete (short diabete) {
		this.diabete = diabete;
	}

	public short getGroupSanguin () {
		return groupSanguin;
	}

	public void setGroupSanguin (short groupSanguin) {
		this.groupSanguin = groupSanguin;
	}

	public String getNom () {
		return nom;
	}

	public void setNom (String nom) {
		this.nom = nom;
	}

	public String getPrenom () {
		return prenom;
	}

	public void setPrenom (String prenom) {
		this.prenom = prenom;
	}

	public short getSexe () {
		return sexe;
	}

	public void setSexe (short sexe) {
		this.sexe = sexe;
	}

	public String getGcmDeviceId () {
		return gcmDeviceId;
	}

	public void setGcmDeviceId (String gcmDeviceId) {
		this.gcmDeviceId = gcmDeviceId;
	}
}
