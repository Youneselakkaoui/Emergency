package com.emergency.util;

import com.emergency.dto.ManageUserIn;
import com.emergency.dto.UserDTO;
import com.emergency.entity.User;

/**
 * Created by elmehdiharabida on 10/05/2015.
 */
public class UserUtil {

	public static User mapUser (ManageUserIn userIn, short me) {
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
		if (me == (short) 1) {
			user.setId(1L);
		}
		return user;
	}

	public static User mapUserDtoToUser (UserDTO userDTO, short me) {
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
		if (me == (short) 1) {
			user.setId(1L);
		}

		return user;
	}

	public static ManageUserIn mapUserToManageUserIn (User user) {
		ManageUserIn manageUserIn = new ManageUserIn();
		UserDTO userDTO = new UserDTO();
		manageUserIn.setUserDTO(userDTO);

		manageUserIn.setCodeFonction((short) 1);
		userDTO.setDateNaissance(user.getDateNaissance());
		userDTO.setTelephone(user.getTelephone());
		userDTO.setPrenom(user.getPrenom());
		userDTO.setNom(user.getNom());
		userDTO.setAutresInfos(user.getAutresInfos());
		userDTO.setCholesterol(user.getCholesterol());
		userDTO.setDiabete(user.getDiabete());
		userDTO.setGroupSanguin(user.getGroupSanguin());
		userDTO.setSexe(user.getSexe());
		userDTO.setGcmDeviceId(user.getGcmDeviceId());
		userDTO.setDigitsId(user.getDigitsId());

		return manageUserIn;

	}

	public static UserDTO mapUserToUserDTO (User userDTO) {

		if (userDTO != null) {
			UserDTO user = new UserDTO();
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
			return user;
		}



		return null;
	}
}
