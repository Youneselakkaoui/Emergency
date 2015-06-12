package com.emergency.util;

import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.dto.ManageUserIn;
import com.emergency.dto.ManageUserOut;
import com.emergency.entity.User;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by elmehdiharabida on 01/06/15.
 */
public class UserSyncUtil {

	static UserManager userManager = new UserManagerImpl();

	public static void syncUser () {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		User user = userManager.getUser();
		if (user != null && (user.getSyncTime().compareTo(user.getLastChanged()) < 0)) {
			ManageUserIn manageUserIn = UserUtil.mapUserToManageUserIn(user);
			ManageUserOut userOut = restTemplate.postForObject(EmergencyConstants.MANAGE_USER_URL, manageUserIn, ManageUserOut.class);
			if (userOut != null && userOut.getAnomalie() == null) {
				user.setSyncTime(new Date());
				userManager.edit(user);
			}
		}
	}

}
