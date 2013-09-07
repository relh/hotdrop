package com.gethotdrop.ui;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.gethotdrop.hotdrop.HotDropActivity;

public class Feed extends HotDropActivity {

	ArrayList<Drop> imageArry = new ArrayList<Drop>();
	DropAdapter adapter;
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);

		getActionBar().setTitle(R.string.action_feed);
		
		// example drops
		imageArry.add(new Drop(R.drawable.placeholderimage, "I like to fish off of piers and wave at sydney"));
		imageArry.add(new Drop(R.drawable.placeholderimage, "You fly across the stars as we stare into the void"));
		imageArry.add(new Drop(R.drawable.placeholderimage, "I love Lana Del Rey; Jeff how could you be so evil?!"));
		imageArry.add(new Drop(R.drawable.placeholderimage, "drop"));
		imageArry.add(new Drop(R.drawable.placeholderimage, "yaya"));
		
		adapter = new DropAdapter(this, R.layout.card, imageArry);
		list = (ListView) findViewById(R.id.list);
		list.setOverScrollMode(ListView.OVER_SCROLL_ALWAYS);
		//list.setOverscrollFooter(getResources().getDrawable(R.drawable.overflow_bottom));
		list.setAdapter(adapter);

		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				//int itemPosition = position;
				//Drop itemValue = (Drop) list.getItemAtPosition(position);
				
				// int ups = Integer.parseInt(getApplicationContext().getResources().getString(R.id.ups));
				
			}

		});
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
			Feed.this.startActivity(intent);
			
			return true;
		case R.id.action_settings:
			createNotification(null, "AQ");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	  public void createNotification(View view, String type) {
		    // Prepare intent which is triggered if the
		    // notification is selected
//		    Intent intent = new Intent(this, NotificationReceiver.class);

		    // Build notification
		    // Actions are just fake
		    Notification noti = new Notification.Builder(this)
		        .setContentTitle(type +  " Warning")
		        .setContentText("Dangerous levels of " + type).build();
		        
		    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		    // Hide the notification after its selected
		    noti.flags |= Notification.FLAG_AUTO_CANCEL;

		    notificationManager.notify(0, noti);

		  }

}
