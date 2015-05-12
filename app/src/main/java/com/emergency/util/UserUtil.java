package com.emergency.util;

import com.emergency.dto.ManageUserIn;
import com.emergency.entity.User;

/**
 * Created by elmehdiharabida on 10/05/2015.
 */
public class UserUtil {

    public static User mapUser(ManageUserIn userIn) {
        User user = new User();
        user.setDateNaissance(userIn.getUserDTO().getDateNaissance());
        user.setTelephone(userIn.getUserDTO().getTelephone());
        user.setPrenom(userIn.getUserDTO().getPrenom());
        user.setNom(userIn.getUserDTO().getNom());
        user.setAutresInfos(userIn.getUserDTO().getAutresInfos());
        user.setCholesterol(userIn.getUserDTO().getCholesterol());
        user.setDiabete(userIn.getUserDTO().getDiabete());
        user.setGroupSanguin(userIn.getUserDTO().getGroupSanguin());
        user.setSexe(userIn.getUserDTO().getSexe());
        return user;
    }
}
