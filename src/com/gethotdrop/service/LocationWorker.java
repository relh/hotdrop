package com.gethotdrop.service;

import java.util.PriorityQueue;
import java.util.Queue;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.IBinder;

public class LocationWorker extends Service {

	static APIQuery query;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (query == null) {
			query = new APIQuery();
			query.execute();
		}
		return startId;
	}
	
	public class APIQuery extends AsyncTask<Location, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Location... loc) {
			return cache.refreshCache(loc[0]);//
		}
		
		protected void onPostExecute(Boolean b) {
			if (b)
			makeNotifications();
			m_handler.postDelayed(m_update, m_interval);
		}
		
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
