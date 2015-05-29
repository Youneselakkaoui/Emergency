package com.emergency.util;

import com.emergency.dto.ManageUserIn;
import com.emergency.dto.UserDTO;
import com.emergency.entity.User;

/**
 * Created by elmehdiharabida on 10/05/2015.
 */
public class UserUtil {

    public static User mapUser(ManageUserIn userIn, short me) {
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
        user.setGcmDeviceId(userIn.getUserDTO().getGcmDeviceId());
        user.setMe(me);
        return user;
    }

    public static User mapUserDtoToUser (UserDTO userDTO, short me){
        User user = new User();
        user.setDateNaissance(userDTO.getDateNaissance());
        user.setTelephone(userDTO.getTelephone());
        user.setPrenom(userDTO.getPrenom());
        user.setNom(userDTO.getNom());
        user.setAutresInfos(userDTO.getAutresInfos());
        user.setCholesterol(userDTO.getCholesterol());
        user.setDiabete(userDTO.getDiabete());
        user.setGroupSanguin(userDTO.getGroupSanguin());
        user.setSexe(userDTO.getSexe());
        user.setGcmDeviceId(userDTO.getGcmDeviceId());
        user.setDigitsId(userDTO.getDigitsId());
        user.setMe(me);

        return user;
    }
}
