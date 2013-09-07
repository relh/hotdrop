package com.gethotdrop.hotdrop;

import java.io.IOException;
import java.util.Map;
import org.json.JSONException;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.gethotdrop.core.Api;
import com.gethotdrop.core.Drop;

public class UpdateService extends Service {
	// Binder for client
	private final IBinder mBinder = new UpdateBinder();
	private final double SEARCH_RADIUS = 25;

	private int m_interval = 1000 * 60 * (2 / 10); // ms*s*m
	private Handler m_handler;
	private LocationManager locManager;
	private LocationCache lCache;
	private LocationListener locListener;
	private Api API;
	private boolean waitLocation;

	@Override
	public void onCreate() {
		// get radius from API
		// dropRadius = api.getRadius();
		// setup location monitoring
		API = new Api(Installation.id(getApplicationContext()));
		waitLocation = false;
		Log.e("SERVICE", "ServiceCreated");
		locManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		try {
			lCache = new LocationCache(getApplicationContext(),
					(float) API.getRadius(), locManager);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		locListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				waitLocation = false;
				Log.d("LOCATION", "Lat:" + location.getLatitude() + ", Lng:"
						+ location.getLongitude());
				Log.d("LOCATION", "accuracy: " + location.getAccuracy());
				if (waitLocation) {
					new GetDropData().execute(location);
				} else {
					lCache.handleLocationUpdate(location);
				}
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
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				(long) (1000) * 60 * (1 / 2), 15F, locListener);
	}
	public void postBegin() {
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, .5F, locListener);
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, .5F, locListener);
	}
	public void postEnd() {
		locManager.removeUpdates(locListener);
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				(long) (1000) * 60 * (1 / 2), 15F, locListener);
	}
	public void resetUpdates() {
		m_handler.removeCallbacks(m_updateChecker);
		m_updateChecker.run();
	}
	public Location getRecentLocation() {
		Criteria c = new Criteria();
		c.setAccuracy(Criteria.ACCURACY_HIGH);
		String provider = locManager.getBestProvider(c, true);
		return locManager.getLastKnownLocation(provider);
	}
	Runnable m_updateChecker = new Runnable() {
		@Override
		public void run() {
			updateCache();
			m_handler.postDelayed(m_updateChecker, m_interval);
		}
	};
	public Api getAPI() {
		return API;
	}
	public void updateCache() {
		waitLocation = true;
		locManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,
				locListener, null);
	}

	public class UpdateBinder extends Binder {
		public UpdateService getService() {
			return UpdateService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	protected class GetDropData extends
			AsyncTask<Location, Void, Map<Integer, Drop>> {
		@Override
		protected Map<Integer, Drop> doInBackground(Location... locations) {
			Location loc = locations[0];
			Map<Integer, Drop> drops = null;
			try {
				drops = API.getHotdrops(loc.getLatitude(), loc.getLongitude(),
						SEARCH_RADIUS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return drops;
		}

		protected void onPostExecute(Map<Integer, Drop> result) {
			lCache.updateModel(result);
			lCache.handleLocationUpdate(LocationManager.NETWORK_PROVIDER);
		}
	}

}
