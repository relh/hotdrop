package com.gethotdrop.hotdrop;
import java.util.Date;

import android.location.Location;

public class Drop {
    private int id;
    private int userId;
    private double latitude;
    private double longitude;
    private Location location;
    private String message;
    private Date created;
    private Date modified;
    
    public int getId() {
        return id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public String getMessage() {
        return message;
    }
    
    public Date getCreated() {
        return created;
    }
    
    public Date getModified() {
        return modified;
        
    }
    
    public Location getLocation() {
    	return location;
    }
    
}