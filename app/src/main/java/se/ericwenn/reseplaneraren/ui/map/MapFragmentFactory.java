package se.ericwenn.reseplaneraren.ui.map;

import android.util.Log;

/**
 * Created by ericwenn on 8/1/16.
 */
public class MapFragmentFactory {

    private static final String TAG = "MapFragmentFactory";

    public static IMapFragment create() {
        Log.d(TAG, "create() called with: " + "");
        return MapFragment.newInstance();
    }

}
