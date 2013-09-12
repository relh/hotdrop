package com.gethotdrop.service;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class MyLocationListener implements LocationListener {
	private Context con;
	public MyLocationListener(Context c) {
		con = c;
	}
	@Override
	public void onLocationChanged(Location location) {
		Intent i = new Intent(con, LocationWorker.class);
		con.startService(i);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
