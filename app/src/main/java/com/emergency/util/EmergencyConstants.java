package com.emergency.util;

/**
 * Constantes pour l'application Emergency
 * Created by elmehdiharabida on 19/04/2015.
 */
public interface EmergencyConstants {

    /**
     * Protocole utilise par le web service
     */
    String EMERGENCY_WS_PROTOCOL = "http";
    /**
     * host du web service
     */
    String EMERGENCY_WS_HOST = "10.0.2.2";
    /**
     * Port du web service
     */
    int EMERGENCY_WS_PORT = 8080;
    /**
     * Path pour createUser
     */
    //String MANAGE_USER_URL = "http://equipe1.meditapps.com:8090/EmergencyWeb-0.0.1-SNAPSHOT/service/emergencyUser/manageUser";
    String MANAGE_USER_URL = "http://192.168.43.20:8080/EmergencyWeb/service/emergencyUser/manageUser";

    //int logo = com.digits.sdk.android.R.drawable.tw__ic_logo_default;
    //int
    String NOTIFICATION_ALERTE = "ALERTE";
    String NOTIFICATION_AR_ALERTE = "AR_ALERTE";
    String NOTIFICATION_INFORMATION = "INFORMATION";


}
