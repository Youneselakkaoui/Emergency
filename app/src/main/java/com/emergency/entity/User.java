package com.emergency.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user database table.
 */

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    // table name
    public static final String TABLE_USER = "user";
    //columns
    public static final String USER_TELEPHONE = "telephone";
    public static final String USER_AUTRESINFOS = "autres_infos";
    public static final String USER_CHOLESTEROL = "cholesterol";
    public static final String USER_DATENAISSANCE = "date_naissance";
    public static final String USER_DIABETE = "diabete";
    public static final String USER_GROUPESANGUIN = "groupe_sanguin";
    public static final String USER_NOM = "nom";
    public static final String USER_PRENOM = "prenom";
    public static final String USER_SEXE = "sexe";
    public static final String USER_GCM_DEVICE_ID = "gcm_device_id";
    public static final String USER_ME = "me";


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
    private short me;
    private List<RecepteursSituation> recepteursSituations;
    private List<Situation> situations;
    private List<SuiviAlerte> suiviAlertes;

    public User() {
    }

    public User(String telephone,
                String autresInfos,
                short  cholesterol,
                Date   dateNaissance,
                short  diabete,
                short  groupSanguin,
                String nom,
                String prenom,
                short  sexe,
                String gcmDeviceId,
                short me) {
        this.telephone = telephone;
        this.autresInfos = autresInfos;
        this.cholesterol = cholesterol;
        this.dateNaissance = dateNaissance;
        this.diabete = diabete;
        this.groupSanguin = groupSanguin;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.gcmDeviceId = gcmDeviceId;
        this.me = me;
    }
    public String getGcmDeviceId() {
        return this.gcmDeviceId;
    }
    public void setGcmDeviceId(String gcmDeviceId) {
        this.gcmDeviceId = gcmDeviceId;
    }

    public short getMe() {
        return this.me;
    }
    public void setMe(short me) {
        this.me = me;
    }
    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAutresInfos() {
        return this.autresInfos;
    }

    public void setAutresInfos(String autresInfos) {
        this.autresInfos = autresInfos;
    }

    public short getCholesterol() {
        return this.cholesterol;
    }

    public void setCholesterol(short cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Date getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public short getDiabete() {
        return this.diabete;
    }

    public void setDiabete(short diabete) {
        this.diabete = diabete;
    }

    public short getGroupSanguin() {
        return this.groupSanguin;
    }

    public void setGroupSanguin(short groupSanguin) {
        this.groupSanguin = groupSanguin;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public short getSexe() {
        return this.sexe;
    }

    public void setSexe(short sexe) {
        this.sexe = sexe;
    }

    public List<RecepteursSituation> getRecepteursSituations() {
        return this.recepteursSituations;
    }

    public void setRecepteursSituations(List<RecepteursSituation> recepteursSituations) {
        this.recepteursSituations = recepteursSituations;
    }

    public RecepteursSituation addRecepteursSituation(RecepteursSituation recepteursSituation) {
        getRecepteursSituations().add(recepteursSituation);
        recepteursSituation.setUser(this);

        return recepteursSituation;
    }

    public RecepteursSituation removeRecepteursSituation(RecepteursSituation recepteursSituation) {
        getRecepteursSituations().remove(recepteursSituation);
        recepteursSituation.setUser(null);

        return recepteursSituation;
    }

    public List<Situation> getSituations() {
        return this.situations;
    }

    public void setSituations(List<Situation> situations) {
        this.situations = situations;
    }

    public Situation addSituation(Situation situation) {
        getSituations().add(situation);
        situation.setUser(this);

        return situation;
    }

    public Situation removeSituation(Situation situation) {
        getSituations().remove(situation);
        situation.setUser(null);

        return situation;
    }

    public List<SuiviAlerte> getSuiviAlertes() {
        return this.suiviAlertes;
    }

    public void setSuiviAlertes(List<SuiviAlerte> suiviAlertes) {
        this.suiviAlertes = suiviAlertes;
    }

    public SuiviAlerte addSuiviAlerte(SuiviAlerte suiviAlerte) {
        getSuiviAlertes().add(suiviAlerte);
        suiviAlerte.setUser(this);

        return suiviAlerte;
    }

    public SuiviAlerte removeSuiviAlerte(SuiviAlerte suiviAlerte) {
        getSuiviAlertes().remove(suiviAlerte);
        suiviAlerte.setUser(null);

        return suiviAlerte;
    }

}