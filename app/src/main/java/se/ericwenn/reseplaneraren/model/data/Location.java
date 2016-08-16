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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.lon, lon) != 0) return false;
        if (Double.compare(location.lat, lat) != 0) return false;
        if (!id.equals(location.id)) return false;
        return name.equals(location.name);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        temp = Double.doubleToLongBits(lon);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}
