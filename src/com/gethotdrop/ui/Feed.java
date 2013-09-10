package com.gethotdrop.ui;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.gethotdrop.service.Cache;
import com.gethotdrop.service.UpdateService;


public class Feed extends Activity {
	DropAdapter adapter;
	ListView list;
	LocalBroadcastManager bManager;	
	static Context c;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);
		getActionBar().setTitle(R.string.action_feed);
		
		c = this;
		
		startService(new Intent(this, UpdateService.class));
		
		bManager = LocalBroadcastManager.getInstance(this);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.gethotdrop.service.UPDATE_FEED");
		bManager.registerReceiver(mFeedUpdateReceiver, intentFilter);
		
//		Location l = new Location(LocationManager.PASSIVE_PROVIDER);
//		l.setLatitude(39.9525);
//		l.setLongitude(-75.1909);
		Cache myCache = Cache.initialize(getBaseContext());
//		myCache.refreshCache(l);
		
		adapter = new DropAdapter(this, R.layout.card, Cache.getInstance().getActiveDropsList());
		
		list = (ListView) findViewById(R.id.list);
		list.setOverScrollMode(ListView.OVER_SCROLL_ALWAYS);
		//list.setOverscrollHeader(header);
		//list.setOverscrollFooter(getResources().getDrawable(R.drawable.overflow_bottom));
		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_post:
			Intent intent = new Intent(Feed.this, Post.class);
			this.startActivity(intent);
			return true;
		case R.id.action_settings:
			//createNotification(null, "notification here");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public BroadcastReceiver mFeedUpdateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			   // Extract data included in the Intent
		       if(intent.getAction().equals("com.gethotdrop.service.UPDATE_FEED")) {
		    	   Log.v("Test", "this is the activity");
		    	   DropAdapter adapter = new DropAdapter(c, R.layout.card, Cache.getInstance().getActiveDropsList());
		    	   list.setAdapter(adapter);
		        }
		  }
	};
}

//public void createNotification(View view, String type) {
//// Prepare intent which is triggered if the
//// notification is selected
////Intent intent = new Intent(this, NotificationReceiver.class);
//// Build notification
//// Actions are just fake
//Notification noti = new Notification.Builder(this)
//    .setContentTitle(type +  " Warning")
//    .setContentText("Dangerous levels of " + type).build();
//    
//NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//// Hide the notification after its selected
//noti.flags |= Notification.FLAG_AUTO_CANCEL;
//notificationManager.notify(0, noti);
//}

//list.setOnItemClickListener(new OnItemClickListener() {
//@Override
//public void onItemClick(AdapterView<?> parent, View view,
//		int position, long id) {
//
//	//int itemPosition = position;
//	//Drop itemValue = (Drop) list.getItemAtPosition(position);
//	
//	// int ups = Integer.parseInt(getApplicationContext().getResources().getString(R.id.ups));	
//}
//});
