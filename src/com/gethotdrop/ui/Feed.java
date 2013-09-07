package com.gethotdrop.ui;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Feed extends ListActivity {

    ArrayList<Drop> imageArry = new ArrayList<Drop>();
    DropAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		
        // add image and text in arraylist
        imageArry.add(new Drop(R.drawable.ic_launcher, "FaceBook"));
        imageArry.add(new Drop(R.drawable.ic_launcher, "Google"));
        imageArry.add(new Drop(R.drawable.ic_launcher, "Ical"));
        imageArry.add(new Drop(R.drawable.ic_launcher, "Outlook"));
        imageArry.add(new Drop(R.drawable.ic_launcher, "Twitter"));
        // add data in contact image adapter
        adapter = new DropAdapter(this, R.layout.activity_feed, imageArry);
        ListView dataList = (ListView) findViewById(R.id.list);
        dataList.setAdapter(adapter);
	}
        
	/*ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);

		listView = getListView();

		// Defined Array values to show in ListView
		String[] values = new String[] { "Android List View",
				"Adapter implementation", "Simple List View In Android",
				"Create List View Android", "Android Example",
				"List View Source Code", "List View Array Adapter",
				"Android Example List View" };

		// Define a new Adapter
		// First parameter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the TextView to which the data is written
		// Forth - the Array of data

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.card, R.id.details, values);

		// Assign adapter to ListView
		listView.setAdapter(adapter);

		// ListView Item Click Listener
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// ListView Clicked item index
				int itemPosition = position;

				// ListView Clicked item value
				String itemValue = (String) listView
						.getItemAtPosition(position);

				// Show Alert
				Toast.makeText(
						getApplicationContext(),
						"Position :" + itemPosition + "  ListItem : "
								+ itemValue, Toast.LENGTH_LONG).show();

			}

		});
	}*/
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feed, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_new:
			Intent intent = new Intent(Feed.this, Post.class);
			Feed.this.startActivity(intent);
			return true;
		case R.id.action_settings:
			//
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
*/
}
