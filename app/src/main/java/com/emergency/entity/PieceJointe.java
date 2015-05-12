package com.emergency.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the piece_jointe database table.
 */

public class PieceJointe implements Serializable {
    private static final long serialVersionUID = 1L;

    private int idPieceJointe;

    private Date dateCreation;

    private String type;

    private String url;
    private Alerte alerte;

    public PieceJointe() {
    }

    public int getIdPieceJointe() {
        return this.idPieceJointe;
    }

    public void setIdPieceJointe(int idPieceJointe) {
        this.idPieceJointe = idPieceJointe;
    }

    public Date getDateCreation() {
        return this.dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Alerte getAlerte() {
        return this.alerte;
    }

    public void setAlerte(Alerte alerte) {
        this.alerte = alerte;
    }

}