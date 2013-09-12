package com.gethotdrop.hotdrop;

import java.util.List;
import com.gethotdrop.core.Drop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DropAdapter extends ArrayAdapter<Drop> {
   Context context;
   int layoutResourceId;
   List<Drop> data;
   
   public DropAdapter(Context context, int layoutResourceId, List<Drop> dropArray) {
       super(context, layoutResourceId, dropArray);
       this.layoutResourceId = layoutResourceId;
       this.context = context;
       this.data = dropArray;
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

           holder.ups = (TextView)row.findViewById(R.id.ups);
           holder.timestamp = (TextView)row.findViewById(R.id.timestamp);
           //holder.image = (ImageView)row.findViewById(R.id.image);
           row.setTag(holder);
       }
       else
       {
           holder = (ImageHolder)row.getTag();
       }

       Drop thisDrop = data.get(position);

       holder.note.setText(thisDrop.message);
       holder.ups.setText("!");
       holder.timestamp.setText(thisDrop.getCreatedAt().toString());
       //int outImage=R.drawable.ic_camera;
       // holder.image.setImageResource(outImage);
      return row;

   }

   static class ImageHolder
   {
      // ImageView image;
       TextView note;
       TextView timestamp;
       TextView ups;
   }
}