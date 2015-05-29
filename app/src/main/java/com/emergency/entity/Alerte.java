package com.emergency.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the alerte database table.
 */
public class Alerte implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String TABLE_ALERTE = "alerte";
    private static final String ALERTE_RECUE_ID = "id_alerte";
    private static final String ALERTE_DATE_ENVOI = "date_envoi";
    private static final String ALERTE_LOCX = "localisation_em_x";
    private static final String ALERTE_LOCY = "localisation_em_y";
    private static final String ALERTE_EMETTEUR_ID ="id_emetteur";
    private static final String ALERTE_SITUATION_TITRE = "titre";
    private static final String ALERTE_SITUATION_MESSAGE = "message";
    private static final String ALERTE_SITUATION_PIECES_JOINTES = "pieces_jointes";

    public static final String ALERTE_USER_AUTRESINFOS = "autres_infos";
    public static final String ALERTE_USER_CHOLESTEROL = "cholesterol";
    public static final String ALERTE_USER_DATENAISSANCE = "date_naissance";
    public static final String ALERTE_USER_DIABETE = "diabete";
    public static final String ALERTE_USER_GROUPESANGUIN = "groupe_sanguin";
    public static final String ALERTE_USER_NOM = "nom";
    public static final String ALERTE_USER_PRENOM = "prenom";
    public static final String ALERTE_USER_SEXE = "sexe";

    public static final String CREATE_TABLE_ALERTE = "CREATE TABLE " + TABLE_ALERTE + " ( "
            + ALERTE_RECUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ALERTE_EMETTEUR_ID + " CHAR(20), "
            + ALERTE_LOCX + " CHAR(45), "
            + ALERTE_LOCY + " CHAR(45), "
            + ALERTE_DATE_ENVOI + " DATE, "
            + ALERTE_SITUATION_TITRE + " CHAR(45), "
            + ALERTE_SITUATION_MESSAGE + " TEXT(300), "
            + ALERTE_SITUATION_PIECES_JOINTES + " SMALLINT(1), "
            + ALERTE_USER_NOM + " CHAR(30), "
            + ALERTE_USER_PRENOM + " CHAR(30), "
            + ALERTE_USER_SEXE + " SMALLINT(1), "
            + ALERTE_USER_DATENAISSANCE + " DATE, "
            + ALERTE_USER_GROUPESANGUIN + " SMALLINT(1), "
            + ALERTE_USER_DIABETE + " SMALLINT(1), "
            + ALERTE_USER_CHOLESTEROL + " SMALLINT(1), "
            + ALERTE_USER_AUTRESINFOS + " TEXT(500) "
            + ");";

    private int idAlerte;

    private Date dateEnvoi;

    private String localisationEmX;

    private String localisationEmY;

    private short statut;

    private Situation situation;

    //private List<PieceJointe> pieceJointes;

    //private List<SuiviAlerte> suiviAlertes;

    public Alerte() {
    }

    public int getIdAlerte() {
        return this.idAlerte;
    }

    public void setIdAlerte(int idAlerte) {
        this.idAlerte = idAlerte;
    }

    public Date getDateEnvoi() {
        return this.dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getLocalisationEmX() {
        return this.localisationEmX;
    }

    public void setLocalisationEmX(String localisationEmX) {
        this.localisationEmX = localisationEmX;
    }

    public String getLocalisationEmY() {
        return this.localisationEmY;
    }

    public void setLocalisationEmY(String localisationEmY) {
        this.localisationEmY = localisationEmY;
    }

    public short getStatut() {
        return this.statut;
    }

    public void setStatut(short statut) {
        this.statut = statut;
    }

    public Situation getSituation() {
        return this.situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    /*public List<PieceJointe> getPieceJointes() {
        return this.pieceJointes;
    }

    public void setPieceJointes(List<PieceJointe> pieceJointes) {
        this.pieceJointes = pieceJointes;
    }

    public PieceJointe addPieceJointe(PieceJointe pieceJointe) {
        getPieceJointes().add(pieceJointe);
        pieceJointe.setAlerte(this);

        return pieceJointe;
    }

    public PieceJointe removePieceJointe(PieceJointe pieceJointe) {
        getPieceJointes().remove(pieceJointe);
        pieceJointe.setAlerte(null);

        return pieceJointe;
    }

    public List<SuiviAlerte> getSuiviAlertes() {
        return this.suiviAlertes;
    }

    public void setSuiviAlertes(List<SuiviAlerte> suiviAlertes) {
        this.suiviAlertes = suiviAlertes;
    }

    public SuiviAlerte addSuiviAlerte(SuiviAlerte suiviAlerte) {
        getSuiviAlertes().add(suiviAlerte);
        //suiviAlerte.setAlerte(this);

        return suiviAlerte;
    }

    public SuiviAlerte removeSuiviAlerte(SuiviAlerte suiviAlerte) {
        getSuiviAlertes().remove(suiviAlerte);
        suiviAlerte.setAlerte(null);

        return suiviAlerte;
    }*/

}