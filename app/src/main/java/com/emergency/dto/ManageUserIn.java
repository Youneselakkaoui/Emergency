package com.emergency.dto;


import java.io.Serializable;


/**
 * Entree de manageUser
 *
 * @author elmehdiharabida
 */
public class ManageUserIn implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -354734881571599956L;

    /**
     * code fonction 0 pour creer et 1 pour update
     */
    private short codeFonction;
    // A voir
    // /**
    // * locale
    // */
    // private Locale locale;
    /**
     * Objet user a creer ou mettre a jour
     */
    private UserDTO userDTO;

    public short getCodeFonction() {
        return codeFonction;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setCodeFonction(final short codeFonction) {
        this.codeFonction = codeFonction;
    }

    public void setUserDTO(final UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    // public Locale getLocale() {
    // return locale;
    // }
    //
    // public void setLocale(Locale locale) {
    // this.locale = locale;
    // }

}
