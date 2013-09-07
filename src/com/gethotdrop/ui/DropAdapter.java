package com.gethotdrop.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DropAdapter extends ArrayAdapter<Drop> {
   
	Context context;
   int layoutResourceId;
   ArrayList<Drop> data=new ArrayList<Drop>();
   
   public DropAdapter(Context context, int layoutResourceId, ArrayList<Drop> data) {
       super(context, layoutResourceId, data);
       this.layoutResourceId = layoutResourceId;
       this.context = context;
       this.data = data;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
       View row = convertView;
       ImageHolder holder = null;

       if(row == null)
       {
           LayoutInflater inflater = ((Activity)context).getLayoutInflater();
           row = inflater.inflate(layoutResourceId, parent, false);

           holder = new ImageHolder();
           holder.note = (TextView)row.findViewById(R.id.note);
           holder.image = (ImageView)row.findViewById(R.id.image);
           row.setTag(holder);
       }
       else
       {
           holder = (ImageHolder)row.getTag();
       }

       Drop myImage = data.get(position);
       holder.note.setText(myImage.message);
       int outImage=myImage.image;
       holder.image.setImageResource(outImage);
      return row;

   }

   static class ImageHolder
   {
       ImageView image;
       TextView note;
   }
}