package com.gethotdrop.ui;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gethotdrop.core.Drop;
import com.gethotdrop.hotdrop.HotDropActivity;

public class Post extends HotDropActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_feed:
	    	Intent intent = new Intent(Post.this, Feed.class);
	    	Post.this.startActivity(intent);
	        return true;
	    case R.id.action_settings:
	        //TODO
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	protected class CreateDrop extends AsyncTask<Drop, Void, Drop> {
		@Override
		protected Drop doInBackground(Drop... drops) {
			Drop d = drops[0];
			Location l = mService.getRecentLocation();
			Drop curDrop = new Drop(d.getId(), d.getUserId(), l.getLatitude(),
				l.getLongitude(), d.getMessage(), d.getCreatedAt(),
					d.getUpdatedAt());
			mService.updateCache();
			return curDrop;
		}
		protected void onPostExecute(Long result) {
			// do ui stuff
		}
	}
}