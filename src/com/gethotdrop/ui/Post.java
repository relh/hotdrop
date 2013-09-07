package com.gethotdrop.ui;

import com.gethotdrop.ui.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class Post extends Activity {

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
}