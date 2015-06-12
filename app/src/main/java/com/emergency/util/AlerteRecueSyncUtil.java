package com.emergency.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.emergency.activities.MainActivity;
import com.emergency.activities.R;
import com.emergency.business.AlerteRecueManager;
import com.emergency.business.UserManager;
import com.emergency.business.impl.AlerteRecueManagerImpl;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.dto.ManageUserIn;
import com.emergency.dto.ManageUserOut;
import com.emergency.dto.SyncAlertesOut;
import com.emergency.entity.Alerte;
import com.emergency.entity.AlerteRecue;
import com.emergency.entity.User;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by elmehdiharabida on 02/06/15.
 */
public class AlerteRecueSyncUtil {
	static UserManager userManager = new UserManagerImpl();
	static AlerteRecueManager alerteRecueManager = new AlerteRecueManagerImpl();


	public static int syncAlerteRecue () {
		int sync = 0;
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		User user = userManager.getUser();
		if (user != null) {
			ManageUserIn manageUserIn = UserUtil.mapUserToManageUserIn(user);
			SyncAlertesOut alertesOut = restTemplate.postForObject(EmergencyConstants.SYNC_ALERTES_URL, manageUserIn, SyncAlertesOut.class);
			for (AlerteRecue syncAlerteOut : alertesOut.getAlerte()) {
				if (syncAlerteOut != null && syncAlerteOut.getSituation() != null && syncAlerteOut.getSituation().getUser() != null) {
					if (alerteRecueManager.sync(syncAlerteOut) == 1) {
						UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(EmergencyConstants.AR_ALERTE_URL)
								.queryParam("alerteId", syncAlerteOut.getIdAlerte()).queryParam("telephone", userManager.getUser().getTelephone());

						ManageUserOut userOut = restTemplate.postForObject(builder.build().encode().toUri(), null, ManageUserOut.class);
						sync++;
					}
				}
			}

		}
		return sync;
	}


}
