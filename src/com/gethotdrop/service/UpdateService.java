package com.gethotdrop.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class UpdateService extends Service {
	public LocationManager locManager;
	public MyLocationListener locListener;
	Cache cache;
	private long minTime = (1000) * 60 * (1 / 4);
	private float minDistance = 5F;
	private String provider = LocationManager.GPS_PROVIDER;

	/**
	 * Service Events
	 */
	@Override
	public void onCreate() {
		   cache = Cache.initialize(getApplication());


		Log.e("service", "This is a service");
		locManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		// get singletons
		// location update
		long minTime = (1000) * 60 * (1/60);
		float minDistance = .2F;
		locListener = new MyLocationListener(getApplicationContext());
		locManager.requestLocationUpdates(provider, minTime, minDistance,
				locListener);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		
		// TODO Auto-generated method stub
		return null;
		
	}	
}
