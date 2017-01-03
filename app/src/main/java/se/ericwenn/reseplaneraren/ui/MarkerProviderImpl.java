package se.ericwenn.reseplaneraren.ui;

import java.util.List;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.VasttrafikAPIBridge;
import se.ericwenn.reseplaneraren.util.DataPromise;


/**
 * Created by ericwenn on 9/3/16.
 */
public class MarkerProviderImpl implements se.ericwenn.reseplaneraren.ui.map.IMapFragment.MarkerProvider {
    @Override
    public DataPromise<List<ILocation>> getMarkers(double lat, double lon) {
        return VasttrafikAPIBridge.getInstance().findNearbyStops(lat, lon);
    }
}
