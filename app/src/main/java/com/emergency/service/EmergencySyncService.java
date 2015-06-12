package com.emergency.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class EmergencySyncService extends Service {

	private static final Object sSyncAdapterLock = new Object();
	private static EmergencySyncAdapter emergencySyncAdapter = null;

	public EmergencySyncService () {
	}

	@Override
	public void onCreate () {
		synchronized (sSyncAdapterLock) {
			if (emergencySyncAdapter == null) {
				emergencySyncAdapter = new EmergencySyncAdapter(getApplicationContext(), true);
			}
		}
	}

	@Override
	public IBinder onBind (Intent intent) {
		return emergencySyncAdapter.getSyncAdapterBinder();
	}

	
}
