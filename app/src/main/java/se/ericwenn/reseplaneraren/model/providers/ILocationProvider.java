package se.ericwenn.reseplaneraren.model.providers;

import java.util.List;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.util.DataPromise;

/**
 * Created by ericwenn on 8/16/16.
 */
public interface ILocationProvider {

    DataPromise<List<ILocation>> favorites();
    DataPromise<List<ILocation>> nearbyStops( double lat, double lon );
    DataPromise<List<ILocation>> nearbyStops( double lat, double lon, int maxDistance );
    DataPromise<List<ILocation>> find(String searchTerm );
}
