package com.gethotdrop.service;
import java.util.List;

import com.gethotdrop.hotdrop.Feed;
import com.gethotdrop.hotdrop.R;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Vibrator;
import android.util.Log;

public class LocationWorker extends IntentService {

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	private Cache cache;
    private LocationManager locManager;

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
        locManager = UpdateService.getLocationManager();
        cache = Cache.initialize(getApplication());
        Location loc = locManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) {
            loc = locManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (loc == null) {
            return;
        }
		if (cache == null) {
			Log.e("error", "cache was null");
		} else {
			if (cache.refreshCache(loc)) {
				//TODO: Feed update view
			}
			if (cache.isNewDrop())
				createNotification();
		}
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

	public void createNotification() {
		// Prepare intent which is triggered if the
		// notification is selected
		Intent intent = new Intent(this, Feed.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		// Build notification
		Notification noti = new Notification.Builder(this)
				.setContentTitle("Discovered Hotdrop")
				.setContentText("You have a new drop!").setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pIntent).getNotification();
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		// Hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		//Launch notification
		notificationManager.notify(0, noti);
		
		//Get Vibrator service
		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		// Vibrate for 300 milliseconds
		v.vibrate(300);
	}

}

