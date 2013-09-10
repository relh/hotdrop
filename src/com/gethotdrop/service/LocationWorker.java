package com.gethotdrop.service;
import java.util.List;

import com.gethotdrop.ui.Feed;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.View;

public class LocationWorker extends IntentService {

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	private Cache cache;

	public LocationWorker() {
		super("HardWorker");
	}

	/**
	 * The IntentService calls this method from the default worker thread with
	 * the intent that started the service. When this method returns,
	 * IntentService stops the service, as appropriate.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Cache cache = Cache.initialize(getApplication());
		double lat = intent.getDoubleExtra("lat", 0F);
		double lng = intent.getDoubleExtra("lng", 0F);
		float acc = intent.getFloatExtra("acc", 3333);
		float bea = intent.getFloatExtra("bea", 333);
		Location loc = new Location("Hotdrop");
		loc.setLatitude(lat);
		loc.setLongitude(lng);
		loc.setAccuracy(acc);
		loc.setBearing(bea);

		if (cache == null) {
			Log.e("error", "cache was null");
		} else {

		}
		if (cache.refreshCache(loc))
			makeNotifications();
	}

	public boolean isForeground(String myPackage) {
		ActivityManager manager = (ActivityManager) this
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager
				.getRunningTasks(1);
		ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
		if (componentInfo.getPackageName().equals(myPackage))
			return true;
		return false;
	}

	@SuppressLint("NewApi")
	public void createNotification(View view, String type) {
		// Prepare intent which is triggered if the
		// notification is selected
		Intent intent = new Intent(this, Feed.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
		// Build notification
		// Actions are just fake
		Notification noti = new Notification.Builder(this)
				.setContentTitle("New mail from " + "test@gmail.com")
				//.setContentText("Subject").setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pIntent).build();
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);
	}

	public void makeNotifications() {
		//if (isForeground("com.gethotdrop.ui")) {
		//	Intent i = new Intent("com.gethotdrop.service.UPDATE_FEED");
		//	LocalBroadcastManager.getInstance(this).sendBroadcast(i);
		//} else {
			createNotification(null, "Hotdrop found!");

		//}
	}

}

