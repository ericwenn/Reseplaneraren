package se.ericwenn.reseplaneraren.ui.map;

import se.ericwenn.reseplaneraren.ui.LocationProvider;

/**
 * Created by ericwenn on 8/1/16.
 */
public class MapFragmentFactory {

    public static IMapFragment create(LocationProvider locationProvider, IMapFragment.MarkerProvider markerProvider) {
        return MapFragment.newInstance( locationProvider, markerProvider );
    }

}
