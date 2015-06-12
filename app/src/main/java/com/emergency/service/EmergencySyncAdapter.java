package com.emergency.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.emergency.activities.GcmIntentService;
import com.emergency.activities.MainActivity;
import com.emergency.activities.R;
import com.emergency.business.UserManager;
import com.emergency.business.impl.UserManagerImpl;
import com.emergency.util.AlerteRecueSyncUtil;
import com.emergency.util.EmergencyConstants;
import com.emergency.util.UserSyncUtil;

/**
 * Created by elmehdiharabida on 01/06/15.
 */
public class EmergencySyncAdapter extends AbstractThreadedSyncAdapter {
	private final AccountManager mAccountManager;
	private NotificationManager mNotificationManager;
	private UserManager userManager = new UserManagerImpl();

	public EmergencySyncAdapter (Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		mAccountManager = AccountManager.get(context);
	}

	@Override
	public void onPerformSync (Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
		try {
			if (userManager.getUser() != null) {
				UserSyncUtil.syncUser();
				int sync = AlerteRecueSyncUtil.syncAlerteRecue();
				if (sync > 0) {
					sendNotification(getContext().getString(R.string.new_alerts));
				}
			}


		} catch (Exception e) {
			syncResult.hasError();
		}
	}

	private void sendNotification (String msg) {
		mNotificationManager = (NotificationManager)
				getContext().getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0,
				new Intent(getContext(), MainActivity.class), 0);

		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(getContext())
						.setSmallIcon(R.drawable.ic_people /*ic_stat_gcm*/)
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


		mNotificationManager.notify(++GcmIntentService.NOTIFICATION_ID, mBuilder.build());
	}



	private void sendNotificationReceiveAlert (String msg, long idAlerte) {
		mNotificationManager = (NotificationManager)
				getContext().getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0,
				new Intent(getContext(), MainActivity.class), 0);

		Intent intent = new Intent(getContext(), MainActivity.class);
		intent.putExtra("idAlerte", idAlerte);


		PendingIntent pIntent = PendingIntent.getActivity(getContext(),0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);



		NotificationCompat.Builder mBuilder =
				new NotificationCompat.Builder(getContext())
						.setSmallIcon(R.drawable.ic_people /*ic_stat_gcm*/)
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


		mNotificationManager.notify(++GcmIntentService.NOTIFICATION_ID, mBuilder.build());
	}

}
