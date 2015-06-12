package com.emergency.util;

/**
 * Constantes pour l'application Emergency
 * Created by elmehdiharabida on 19/04/2015.
 */
public interface EmergencyConstants {


	//String PREFIX = "http://192.168.43.20:8080/EmergencyWeb/service";
	//String PREFIX = "http://192.168.1.2:8080/EmergencyWeb/service";
	String PREFIX = "http://equipe1.meditapps.com:8090/EmergencyWeb-0.0.1-SNAPSHOT/service";
	/**
	 * Path pour createUser
	 */
	//String MANAGE_USER_URL = "http://equipe1.meditapps.com:8090/EmergencyWeb-0.0.1-SNAPSHOT/service/emergencyUser/manageUser";
	String MANAGE_USER_URL = PREFIX + "/emergencyUser/manageUser";

	String SYNC_ALERTES_URL = PREFIX + "/emergencyReceiveAlert/syncAlertes";

	String GET_ALERTE_URL = PREFIX + "/emergencyAlerte/findAlerte";

	String AR_ALERTE_URL = PREFIX + "/emergencyReceiveAlert/accuseAlerte";

	String FIND_USER_URL = PREFIX + "/emergencyUser/findUser";


	String SEND_ALERT = PREFIX + "/emergencyAlerte/alert";
	//int logo = com.digits.sdk.android.R.drawable.tw__ic_logo_default;

	String NOTIFICATION_ALERTE = "ALERTE";
	String NOTIFICATION_AR_ALERTE = "AR_ALERTE";
	String NOTIFICATION_INFORMATION = "INFORMATION";

}
