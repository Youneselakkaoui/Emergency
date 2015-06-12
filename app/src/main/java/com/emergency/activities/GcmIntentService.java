package com.emergency.activities;

import com.emergency.business.AlerteRecueManager;
import com.emergency.business.UserManager;
import com.emergency.business.impl.AlerteRecueManagerImpl;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.dao.AlerteRecueDao;
import com.emergency.dao.impl.AlerteRecueDaoImpl;
import com.emergency.dto.ManageAlerteOut;
import com.emergency.dto.ManageUserOut;
import com.emergency.dto.SyncAlertesOut;
import com.emergency.entity.AlerteRecue;
import com.emergency.entity.SituationRecue;
import com.emergency.util.AlerteRecueUtil;
import com.emergency.util.EmergencyConstants;
import com.emergency.util.SyncUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.loopj.android.http.AsyncHttpClient;
import com.orm.SugarTransactionHelper;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
	public static int NOTIFICATION_ID = 0;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;
	private static AlerteRecueManager alerteRecueManager = new AlerteRecueManagerImpl();
	private static UserManager userManager = new UserManagerImpl();

	public GcmIntentService () {
		super("GcmIntentService");
	}

	public static final String TAG = "GCM Demo";

	@Override
	protected void onHandleIntent (Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// The getMessageType() intent parameter must be the intent you received
		// in your BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
		    /*
		     * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Send error: " + extras.toString());
			}
			else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted messages on server: " + extras.toString());
				// If it's a regular GCM message, do some work.
			}
			else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

//				try {
					processNotification(intent);
//				} finally {
//					//Log.e(TAG, e.getMessage());
//					SyncUtils.TriggerRefresh();
//				}
				Log.i(TAG, "Received: " + extras.toString());
			}
		}
		// Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void processNotification (Intent s) {

		String notificationType = s.getStringExtra("notificatioType");
		String requestObjectId = s.getStringExtra("requestObjectId");
		if (EmergencyConstants.NOTIFICATION_ALERTE.equals(notificationType) && StringUtils.hasText(requestObjectId)) {
			notifyAlert(requestObjectId);
		}
		else if (EmergencyConstants.NOTIFICATION_AR_ALERTE.equals(notificationType) && StringUtils.hasText(requestObjectId)) {
			notifyAR(requestObjectId);
		}
		else {
			SyncUtils.TriggerRefresh();
		}


	}

	private void notifyAR (String requestObjectId) {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		Map<String, String> uriVariables = new HashMap<String, String>();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(EmergencyConstants.FIND_USER_URL)
				.queryParam("telephone", requestObjectId);
		ManageUserOut userOut = restTemplate.getForObject(builder.build().encode().toUri(), ManageUserOut.class);
		String emetteur = concat(userOut.getUserDTO().getNom(), userOut.getUserDTO().getPrenom());


		if (userOut != null && userOut.getUserDTO() != null && StringUtils.hasText(emetteur)) {
			requestObjectId = emetteur;
		}
		sendNotification(getResources().getString(R.string.notification_ar_alerte, requestObjectId));
	}

	private void notifyAlert (String alerteId) {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		Map<String, String> uriVariables = new HashMap<String, String>();
		uriVariables.put("alerteId", alerteId);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(EmergencyConstants.GET_ALERTE_URL)
				.queryParam("alerteId", alerteId);
		ManageAlerteOut alerteOut = restTemplate.getForObject(builder.build().encode().toUri().toString(), ManageAlerteOut.class /*, uriVariables*/);
		if (alerteOut != null && alerteOut.getAlerteDTO() != null && alerteOut.getAlerteDTO().getSituation() != null && alerteOut.getAlerteDTO().getSituation().getUser() != null) {
			AlerteRecue alerteRecue = AlerteRecueUtil.manageAlereOutToalerteRecueMapper(alerteOut);

			alerteRecueManager.add(alerteRecue);
			//alerteRecueDao.insert(alerteRecue);
			String emetteur = concat(alerteRecue.getSituation().getUser().getNom(), alerteRecue.getSituation().getUser().getPrenom());
			if (StringUtils.hasText(emetteur)) {
				sendReceivedAlertNotification(getResources().getString(R.string.notification_alerte, emetteur), alerteRecue.getId());
			}
			else {
				sendReceivedAlertNotification(getResources().getString(R.string.notification_alerte, alerteRecue.getSituation().getUser().getTelephone()), alerteRecue.getId());
			}
			builder = UriComponentsBuilder.fromHttpUrl(EmergencyConstants.AR_ALERTE_URL)
					.queryParam("alerteId", alerteId).queryParam("telephone", userManager.getUser().getTelephone());
			ManageUserOut userOut = restTemplate.postForObject(builder.build().encode().toUri(), null, ManageUserOut.class);

		}

	}

	// Put the message into a notification and post it.
	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification (String msg) {
		mNotificationManager = (NotificationManager)
				this.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class), 0);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.mipmap.ic_launcher2/*ic_people /*ic_stat_gcm*/)
						.setContentTitle("Emergency")
						.setAutoCancel(true)
						.setStyle(new NotificationCompat.BigTextStyle()
								.bigText(msg))
						.setContentText(msg);

		//Vibration
		mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

		//LED
		mBuilder.setLights(Color.RED, 3000, 3000);
		mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

		mBuilder.setContentIntent(contentIntent);
		mBuilder.setAutoCancel(true);


		NOTIFICATION_ID++;
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

	public static String concat (String str1, String str2) {
		return str1 == null ? str2
				: str2 == null ? str1
				: str1 + " " + str2;
	}

	private ClientHttpRequestFactory clientHttpRequestFactory () {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(30000);
		factory.setConnectTimeout(30000);
		return factory;
	}

	private void sendReceivedAlertNotification (String msg, long idAlert) {
		mNotificationManager = (NotificationManager)
				this.getSystemService(Context.NOTIFICATION_SERVICE);

		// Instantiate a Builder object.
		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(this)
						.setSmallIcon(R.drawable.heart_monitor/*ic_people /*ic_stat_gcm*/)
						.setContentTitle("Emergency")
						.setAutoCancel(true)
						.setStyle(new NotificationCompat.BigTextStyle()
								.bigText(msg))
						.setContentText(msg);
// Creates an Intent for the Activity
		Intent notifyIntent =
				new Intent(this, NotificationAlerteActivity.class);
// Sets the Activity to start in a new, empty task
		notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		notifyIntent.putExtra("idAlerte",idAlert);
// Creates the PendingIntent
		PendingIntent notifyPendingIntent =
				PendingIntent.getActivity(
						this,
						0,
						notifyIntent,
						PendingIntent.FLAG_UPDATE_CURRENT
				);

// Puts the PendingIntent into the notification builder
		mBuilder.setContentIntent(notifyPendingIntent);
// Notifications are issued by sending them to the
// NotificationManager system service.
		 mNotificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Builds an anonymous Notification object from the builder, and
// passes it to the NotificationManager

		//Vibration
		mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

		//LED
		mBuilder.setLights(Color.RED, 3000, 3000);
		mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
		NOTIFICATION_ID++;
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());

//		NotificationCompat.Builder mBuilder =
//				new NotificationCompat.Builder(this)
//						.setSmallIcon(R.drawable.ic_people /*ic_stat_gcm*/)
//						.setContentTitle("Emergency")
//						.setAutoCancel(true)
//						.setStyle(new NotificationCompat.BigTextStyle()
//								.bigText(msg))
//						.setContentText(msg);
//
//		//Vibration
//		mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
//
//		//LED
//		mBuilder.setLights(Color.RED, 3000, 3000);
//		mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
//		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//				new Intent(this, MainActivity.class), 0);
//		mBuilder.setContentIntent(contentIntent);
//		mBuilder.setAutoCancel(true);
//
//
//		NOTIFICATION_ID++;
//		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}
