package se.ericwenn.reseplaneraren.ui.map;

/**
 * Created by ericwenn on 8/1/16.
 */
public class MapFragmentFactory {

    public static IMapFragment create() {
        return MapFragment.newInstance();
    }

}
