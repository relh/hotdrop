package com.gethotdrop.hotdrop;

import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

public class UpdateService extends Service {
	private int m_interval = 1000 * 60 * 5; // ms*s*m
	private Handler m_handler;
	private LocationManager locManager;
	private LocationCache lCache;
	private LocationListener locListen;

	@Override
	public void onCreate() {
		// get radius from API
		// dropRadius = api.getRadius();

		// setup location monitoring
		locManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		lCache = new LocationCache(getApplicationContext(), 5L, locManager);

		locListen = new LocationListener() {
			public void onLocationChanged(Location location) {
				lCache.handleLocationUpdate(location);
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}
			public void onProviderDisabled(String provider) {
			}
		};

		// setup time checking
		m_handler = new Handler();
		m_updateChecker.run();

		// start checking for location change

	}

	public void resetUpdates() {
		m_handler.removeCallbacks(m_updateChecker);
		m_updateChecker.run();
	}

	Runnable m_updateChecker = new Runnable() {
		@Override
		public void run() {
			updateCache();
			m_handler.postDelayed(m_updateChecker, m_interval);
		}
	};

	public void updateCache() {
		Map<Integer, Drop> mockModel = new HashMap<Integer, Drop>();
		lCache.updateModel(mockModel);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
