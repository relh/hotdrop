package com.gethotdrop.ui;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Feed extends Activity {

	ArrayList<Drop> imageArry = new ArrayList<Drop>();
	DropAdapter adapter;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed);
		// example drops
		imageArry.add(new Drop(R.drawable.ic_launcher, "I"));
		imageArry.add(new Drop(R.drawable.ic_launcher, "am"));
		imageArry.add(new Drop(R.drawable.ic_launcher, "a"));
		imageArry.add(new Drop(R.drawable.ic_launcher, "drop"));
		imageArry.add(new Drop(R.drawable.ic_launcher, "yaya"));
		
		adapter = new DropAdapter(this, R.layout.card, imageArry);
		list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int itemPosition = position;
				Drop itemValue = (Drop) list.getItemAtPosition(position);

				Toast.makeText(
						getApplicationContext(),
						"Position :" + itemPosition + "  ListItem : "
								+ itemValue.toString(), Toast.LENGTH_SHORT).show();
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
			//
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
