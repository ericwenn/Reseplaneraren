package se.ericwenn.reseplaneraren.model.data;

import java.util.List;

import se.ericwenn.reseplaneraren.util.DataPromise;

/**
 * Created by ericwenn on 7/19/16.
 */
public interface IVasttrafikAPIBridge {

    DataPromise<List<ILocation>> findLocations(String search);

    DataPromise<List<ITrip>> getTrips( ILocation from, ILocation to );

    DataPromise<List<ILocation>> findNearbyStops(double lat, double lon);


}
