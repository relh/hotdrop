package com.gethotdrop.hotdrop;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.File;
import com.gethotdrop.core.Api;
import com.gethotdrop.service.Installation;
import com.gethotdrop.service.LocationWorker;
import com.gethotdrop.service.UpdateService;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class Post extends Activity {
	LocationManager locManager;
	LocationListener listen;

	static Context c;
	private static int TAKE_PICTURE = 1;
	private Uri outputFileUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		c = this;
		locManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		listen = new LocationListener() {

			@Override
			public void onLocationChanged(Location arg0) {
			}

			@Override
			public void onProviderDisabled(String arg0) {
			}

			@Override
			public void onProviderEnabled(String arg0) {
			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
		};

		OnClickListener layoutOnClickListener = new OnClickListener() {
			public void onClick(View v) {
				takePhoto(v);
			}

			public void takePhoto(View view) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File file = new File(Environment.getExternalStorageDirectory(),
						"asd.jpg");

				outputFileUri = Uri.fromFile(file);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
				startActivityForResult(intent, TAKE_PICTURE);
			}

		};

		this.findViewById(R.id.layout_photo).setOnClickListener(
				layoutOnClickListener);
		// this.findViewById(R.id.layout_video).setOnClickListener(layoutOnClickListener);
		// this.findViewById(R.id.layout_upload).setOnClickListener(layoutOnClickListener);

	}

	@Override
	public void onResume() {
		super.onResume();
		startService(new Intent(this, UpdateService.class));
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
				.5F, listen);
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				1000, .5F, listen);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locManager.removeUpdates(listen);
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
		case R.id.submit:			
			String submitThroughDan = ((EditText) findViewById(R.id.submitText))
					.getText().toString();
			if (submitThroughDan.isEmpty()) {
				Toast.makeText(getBaseContext(), "Write a message!", Toast.LENGTH_SHORT)
				.show();
			} else {
				new CreateDrop().execute(submitThroughDan);
			}
			return true;
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_settings:
			// TODO
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKE_PICTURE) {
			findViewById(R.id.layout_photo).setVisibility(View.GONE);
			findViewById(R.id.layout_video).setVisibility(View.GONE);
			findViewById(R.id.layout_upload).setVisibility(View.GONE);
			findViewById(R.id.chosenImage).setVisibility(View.VISIBLE);
		}
	}

	protected class CreateDrop extends AsyncTask<String, Void, Long> {
		@Override
		protected Long doInBackground(String... strings) {
			String s = strings[0];
			Api API = new Api(Installation.id(getApplicationContext()));
			Location l = locManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (l == null) {
				l = locManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
			try {
				API.setHotdrop(l.getLatitude(), l.getLongitude(), s);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return 1L;
		}

		@Override
		protected void onPostExecute(Long result) {
            Intent i = new Intent(c, LocationWorker.class);
            c.startService(i);
			NavUtils.navigateUpFromSameTask((Activity) c);

		}
	}

	// do ui stuff
}