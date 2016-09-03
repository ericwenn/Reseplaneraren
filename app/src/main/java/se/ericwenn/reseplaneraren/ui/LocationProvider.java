package se.ericwenn.reseplaneraren.ui;

import android.location.Location;

/**
 * Created by ericwenn on 9/1/16.
 */
public class LocationProvider {
    private Location lastLocation = null;
    private OnLocationChangedListener mListener = null;

    public LocationProvider(Location location) {
        lastLocation = location;
    }
    public LocationProvider() {

    }


    public Location getLastLocation() {
        return lastLocation;
    }

    public void updateLocation(Location location) {
        lastLocation = location;
        if( mListener != null ) {
            mListener.onLocationChanged(location);
        }
    }

    public void setLocationChangedListener( OnLocationChangedListener listener ) {
        mListener = listener;
    }



    public interface OnLocationChangedListener {
        public void onLocationChanged(Location newLocation);
    }
}
