package com.gethotdrop.hotdrop;

import java.util.Iterator;
import java.util.Map;

import com.gethotdrop.core.Drop;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;

public class LocationCache {
	public static final float ALERT_RADIUS = 25F;
	
	private float DROP_RADIUS;
	private Map<Integer, Drop> dropModel;
	private Map<Integer, Drop> closeDrops;
	private Map<Integer, Drop> activeDrops;
	private Intent bIntent;
	private Context aContext;
	private LocationManager lm;
	public LocationCache(Context c, float radius, LocationManager lmIn) {
		lm = lmIn;
		aContext = c;
		DROP_RADIUS = radius;
		bIntent = new Intent("com.gethotdrop.hotdrop.NOTIFY");
	}
	
	public void handleLocationUpdate(String provider) {
		handleLocationUpdate(lm.getLastKnownLocation(provider));
	}
	
	public void handleLocationUpdate(Location loc) {
		Iterator<Drop> it = dropModel.values().iterator();
		Drop cur = null;
		while (it.hasNext()) {
			cur = it.next();
			if (loc.distanceTo(cur.getLocation()) < DROP_RADIUS) {
				activeDrops.put(cur.getId(), cur);
			} else if (loc.distanceTo(cur.getLocation()) < ALERT_RADIUS) {
				activeDrops.remove(cur.getId());
				Drop oldDrop = closeDrops.put(cur.getId(), cur);
				if (oldDrop == null) {
					this.trackDrop(cur);
				}
			} else {
				activeDrops.remove(cur.getId());
				Drop oldDrop = closeDrops.remove(cur.getId());
				if (oldDrop != null) {
					this.unTrackDrop(cur);
				}
			}
		}
		
	}
	
	public void updateModel(Map<Integer, Drop> model) {
		dropModel = model;
	}
	private PendingIntent getPartialIntent(int id) {
		PendingIntent pi = PendingIntent.getBroadcast(aContext, id, bIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		return pi;
	}
	
	public void trackDrop(Drop d) {
		lm.addProximityAlert(d.getLatitude(), d.getLongitude(), DROP_RADIUS, -1, this.getPartialIntent(d.getId()));
	}
	
	public void unTrackDrop(Drop d) {
		lm.removeProximityAlert(this.getPartialIntent(d.getId()));
	}
}
	
