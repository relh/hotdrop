package com.gethotdrop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.location.Location;

import com.gethotdrop.core.Api;
import com.gethotdrop.core.Drop;

public class Cache {
	private Api api;
	private Map<Integer, Drop> activeDrops = new HashMap<Integer, Drop>();
	private Map<Integer, Drop> allDrops = new HashMap<Integer, Drop>();

	private static Cache instance = null;

	protected Cache(Context context) {
		api = new Api(Installation.id(context));
	}

	public static Cache initialize(Context context) {
		if (instance == null)
			instance = new Cache(context);
		return instance;
	}
	
	public static Cache getInstance() {;
		return instance;
	}

	public boolean refreshCache(Location location) {
		if (location == null ){
			return false;
		}
		double radius = 0;
		Map<Integer, Drop> newAllDrops;
		try {
			radius = api.getRadius();
			Map<Integer, Drop> oldAllDrops = allDrops;
			newAllDrops = api.getHotdrops(
					location.getLatitude(), location.getLongitude(), 25);
		} catch (Exception e){
			return false;
		}
		
		Map<Integer, Drop> oldActiveDrops = activeDrops;
		Map<Integer, Drop> newActiveDrops = new HashMap<Integer, Drop>();
		for (Drop drop : newAllDrops.values()) {
			if (drop.getLocation().distanceTo(location) <= radius * 1000) {
				newActiveDrops.put(drop.getId(), drop);
			}
		}

		if (equalMaps(oldActiveDrops, newActiveDrops))
			return false;
		else {
			activeDrops = newActiveDrops;
			return true;
		}

	}

	public boolean equalMaps(Map<Integer, Drop> a, Map<Integer, Drop> b) {
		if (a.size() != b.size())
			return false;
		for (Integer k : a.keySet()) {
			if (!(b.containsKey(k)))
				return false;
		}
		return true;
	}
	
	public Map<Integer, Drop> getActiveDrops() {
		return activeDrops;
	}
	
	public List<Drop> getActiveDropsList() {
		ArrayList<Drop> dropList = new ArrayList<Drop>();
		for(Drop drop : activeDrops.values()) {
			dropList.add(drop);
		}
		return dropList;
	}
	
	public Map<Integer, Drop> getAllDrops() {
		return allDrops;
	}
}