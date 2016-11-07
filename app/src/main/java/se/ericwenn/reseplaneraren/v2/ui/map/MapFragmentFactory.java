package se.ericwenn.reseplaneraren.v2.ui.map;

import se.ericwenn.reseplaneraren.ui.LocationProvider;

/**
 * Created by ericwenn on 8/1/16.
 */
public class MapFragmentFactory {

    public static IMapFragment create() {
        return MapFragment.newInstance();
    }

}
