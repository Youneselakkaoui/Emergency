package com.emergency.entity;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the situation database table.
 */

public class Situation implements Serializable {
    private static final long serialVersionUID = 1L;

    // Table name
    public static final String TABLE_SITUATION = "situation";
    // Common column names
    public static final String SITUATION_KEY_ID = "id_situation";
    public static final String SITUATION_ID_EMETTEUR = "id_emetteur";
    public static final String SITUATION_TITRE = "titre";
    public static final String SITUATION_MESSAGE = "message";
    public static final String SITUATION_PIECES_JOINTES = "pieces_jointes";
    public static final String SITUATION_TYPE_ENVOI = "type_envoi";

    private int idSituation;
    private String message;
    private short piecesJointes;
    private String titre;
    private short typeEnvoi;
    private List<Alerte> alertes;
    private List<RecepteursSituation> recepteursSituations;
    private User user;

    public Situation() {

    }

    public Situation(int idSituation, String emetteur, String titre, String message, short piecesJointes, short typeEnvoi) {
        this.idSituation = idSituation;
        this.message = message;
        this.piecesJointes = piecesJointes;
        this.titre = titre;
        this.typeEnvoi = typeEnvoi;
        this.user = new User();
        user.setTelephone(emetteur);
    }

    public int getIdSituation() {
        return this.idSituation;
    }

    public void setIdSituation(int idSituation) {
        this.idSituation = idSituation;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public short getPiecesJointes() {
        return this.piecesJointes;
    }

    public void setPiecesJointes(short piecesJointes) {
        this.piecesJointes = piecesJointes;
    }

    public String getTitre() {
        return this.titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public short getTypeEnvoi() {
        return this.typeEnvoi;
    }

    public void setTypeEnvoi(short typeEnvoi) {
        this.typeEnvoi = typeEnvoi;
    }

    public List<Alerte> getAlertes() {
        return this.alertes;
    }

    public void setAlertes(List<Alerte> alertes) {
        this.alertes = alertes;
    }

    public Alerte addAlerte(Alerte alerte) {
        getAlertes().add(alerte);
        alerte.setSituation(this);

        return alerte;
    }

    public Alerte removeAlerte(Alerte alerte) {
        getAlertes().remove(alerte);
        alerte.setSituation(null);

        return alerte;
    }

    public List<RecepteursSituation> getRecepteursSituations() {
        return this.recepteursSituations;
    }

    public void setRecepteursSituations(List<RecepteursSituation> recepteursSituations) {
        this.recepteursSituations = recepteursSituations;
    }

    public RecepteursSituation addRecepteursSituation(RecepteursSituation recepteursSituation) {
        getRecepteursSituations().add(recepteursSituation);
        recepteursSituation.setSituation(this);

        return recepteursSituation;
    }

    public RecepteursSituation removeRecepteursSituation(RecepteursSituation recepteursSituation) {
        getRecepteursSituations().remove(recepteursSituation);
        recepteursSituation.setSituation(null);

        return recepteursSituation;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}