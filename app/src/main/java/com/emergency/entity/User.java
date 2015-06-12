package com.emergency.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 */

public class User extends SugarRecord<User> {

	// table name
//    public static final String TABLE_USER = "user";
//    //columns
//    public static final String USER_TELEPHONE = "telephone";
//    public static final String USER_AUTRESINFOS = "autres_infos";
//    public static final String USER_CHOLESTEROL = "cholesterol";
//    public static final String USER_DATENAISSANCE = "date_naissance";
//    public static final String USER_DIABETE = "diabete";
//    public static final String USER_GROUPESANGUIN = "groupe_sanguin";
//    public static final String USER_NOM = "nom";
//    public static final String USER_PRENOM = "prenom";
//    public static final String USER_SEXE = "sexe";
//    public static final String USER_GCM_DEVICE_ID = "gcm_device_id";
//    public static final String USER_ME = "me";
//    public static final String USER_DIGITS_ID = "digits_id";
//    public static final String CREATE_TABLE_USER = "CREATE TABLE " + User.TABLE_USER + " ( "
//            + User.USER_TELEPHONE + " CHAR(20) PRIMARY KEY, "
//            + User.USER_NOM + " CHAR(30), "
//            + User.USER_PRENOM + " CHAR(30), "
//            + User.USER_DATENAISSANCE + " DATE, "
//            + User.USER_SEXE + " SMALLINT(1), "
//            + User.USER_GROUPESANGUIN + " SMALLINT(1), "
//            + User.USER_DIABETE + " SMALLINT(1), "
//            + User.USER_CHOLESTEROL + " SMALLINT(1), "
//            + User.USER_AUTRESINFOS + " TEXT(500), "
//            + User.USER_GCM_DEVICE_ID + " TEXT(200), "
//            + User.USER_ME + " SMALLINT(1), "
//            + User.USER_DIGITS_ID + " BIGINT(20) "
//            + ");";

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
	private Date syncTime;
	private Date lastChanged;

	public Date getLastChanged () {
		return lastChanged;
	}

	public void setLastChanged (Date lastChanged) {
		this.lastChanged = lastChanged;
	}

	public Date getSyncTime () {
		return syncTime;
	}

	public void setSyncTime (Date syncTime) {
		this.syncTime = syncTime;
	}

	@Ignore
	private List<Situation> situations;

	private long digitsId;

	public long getDigitsId () {
		return digitsId;
	}

	public void setDigitsId (long digitsId) {
		this.digitsId = digitsId;
	}

	public User () {
		Calendar cal = Calendar.getInstance();
		cal.set(2001, 1, 1);
		this.dateNaissance = cal.getTime();
	}

//	public User (String telephone,
//	             String autresInfos,
//	             short cholesterol,
//	             Date dateNaissance,
//	             short diabete,
//	             short groupSanguin,
//	             String nom,
//	             String prenom,
//	             short sexe,
//	             String gcmDeviceId,
//	             //short me,
//	              long digitsId) {
//		this.telephone = telephone;
//		this.autresInfos = autresInfos;
//		this.cholesterol = cholesterol;
//		this.dateNaissance = dateNaissance;
//		this.diabete = diabete;
//		this.groupSanguin = groupSanguin;
//		this.nom = nom;
//		this.prenom = prenom;
//		this.sexe = sexe;
//		this.gcmDeviceId = gcmDeviceId;
//		// this.me = me;
//		this.digitsId = digitsId;
//	}

	public String getGcmDeviceId () {
		return this.gcmDeviceId;
	}

	public void setGcmDeviceId (String gcmDeviceId) {
		this.gcmDeviceId = gcmDeviceId;
	}

	public String getTelephone () {
		return this.telephone;
	}

	public void setTelephone (String telephone) {
		this.telephone = telephone;
	}

	public String getAutresInfos () {
		return this.autresInfos;
	}

	public void setAutresInfos (String autresInfos) {
		this.autresInfos = autresInfos;
	}

	public short getCholesterol () {
		return this.cholesterol;
	}

	public void setCholesterol (short cholesterol) {
		this.cholesterol = cholesterol;
	}

	public Date getDateNaissance () {

		return this.dateNaissance;
	}

	public void setDateNaissance (Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public short getDiabete () {
		return this.diabete;
	}

	public void setDiabete (short diabete) {
		this.diabete = diabete;
	}

	public short getGroupSanguin () {
		return this.groupSanguin;
	}

	public void setGroupSanguin (short groupSanguin) {
		this.groupSanguin = groupSanguin;
	}

	public String getNom () {
		return this.nom;
	}

	public void setNom (String nom) {
		this.nom = nom;
	}

	public String getPrenom () {
		return this.prenom;
	}

	public void setPrenom (String prenom) {
		this.prenom = prenom;
	}

	public short getSexe () {
		return this.sexe;
	}

	public void setSexe (short sexe) {
		this.sexe = sexe;
	}


	public List<Situation> getSituations () {
		return this.situations;
	}

	public void setSituations (List<Situation> situations) {
		this.situations = situations;
	}

	public Situation addSituation (Situation situation) {
		getSituations().add(situation);
		situation.setUser(this);

		return situation;
	}

	public Situation removeSituation (Situation situation) {
		getSituations().remove(situation);
		situation.setUser(null);

		return situation;
	}



}