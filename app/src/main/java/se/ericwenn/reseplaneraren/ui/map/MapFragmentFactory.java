package se.ericwenn.reseplaneraren.ui.map;

import se.ericwenn.reseplaneraren.ui.LocationProvider;

/**
 * Created by ericwenn on 8/1/16.
 */
public class MapFragmentFactory {

    private static final String TAG = "MapFragmentFactory";

    public static IMapFragment create(LocationProvider locationProvider) {
        return MapFragment.newInstance( locationProvider );
    }

}
