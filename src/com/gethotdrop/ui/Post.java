package com.gethotdrop.ui;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.gethotdrop.ui.R;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.gethotdrop.core.Drop;
import com.gethotdrop.hotdrop.HotDropActivity;

public class Post extends HotDropActivity {

	private static int TAKE_PICTURE = 1;
	private Uri outputFileUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
	    OnClickListener layoutOnClickListener = new OnClickListener() {
	        public void onClick(View v) {
	            takePhoto(v);
	        }

	        public void takePhoto(View view) {
	        	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    		File file = new File(Environment.getExternalStorageDirectory(), "asd.jpg");
	     
	    		outputFileUri = Uri.fromFile(file);
	    		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
	    		startActivityForResult(intent, TAKE_PICTURE);
	        }

	       
	    };
		
		this.findViewById(R.id.layout_photo).setOnClickListener(layoutOnClickListener);
		//this.findViewById(R.id.layout_video).setOnClickListener(layoutOnClickListener);
		//this.findViewById(R.id.layout_upload).setOnClickListener(layoutOnClickListener);
		
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
	    case android.R.id.home:
	        NavUtils.navigateUpFromSameTask(this);
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

	 @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == TAKE_PICTURE){
 			Toast.makeText(getBaseContext(), outputFileUri.toString(), Toast.LENGTH_SHORT).show();
 			findViewById(R.id.layout_photo).setVisibility(View.GONE);
 			findViewById(R.id.layout_video).setVisibility(View.GONE);
 			findViewById(R.id.layout_upload).setVisibility(View.GONE);
 			findViewById(R.id.chosenImage).setVisibility(View.VISIBLE);
         }
     }
}