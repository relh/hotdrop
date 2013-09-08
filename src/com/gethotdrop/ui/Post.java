package com.gethotdrop.ui;

import android.content.Intent;
import android.os.Bundle;
import java.io.File;
import com.gethotdrop.ui.R;

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
	    Toast.makeText(getBaseContext(), item.toString(), Toast.LENGTH_SHORT).show();
		switch (item.getItemId()) {
		case R.id.submit:
			String submitThroughDan = ((EditText)findViewById(R.id.submitText)).getText().toString(); 
			//
			return true;
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