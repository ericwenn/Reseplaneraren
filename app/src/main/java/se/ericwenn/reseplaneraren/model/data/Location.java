package se.ericwenn.reseplaneraren.model.data;

import android.util.Log;

/**
 * Created by ericwenn on 7/14/16.
 */
public class Location implements ILocation {
    private static final String TAG = "Location";
    public String id;
    public double lon;
    public double lat;
    public String name;
    public String type;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getLatitude() {
        return lat;
    }

    @Override
    public Double getLongitude() {
        return lon;
    }

    @Override
    public LocationType getLocationType() {
        Log.d(TAG, "getLocationType: type="+(type == null ? "null" : "'"+type+"'"));
        if(type == null) {
            return LocationType.STOP;
        }
        switch(type) {
            case "POI":
                return LocationType.POI;
            case "ADR":
                return LocationType.ADRESS;
            default:
                return LocationType.STOP;

        }
    }

    @Override
    public String getID() {
        return id;
    }
}
