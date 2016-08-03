package se.ericwenn.reseplaneraren.model.data;

/**
 * Created by ericwenn on 7/19/16.
 */
public interface IVasttrafikAPIBridge {

    void getTrips( ILocation from, ILocation to, Listener l );
    void findLocations( String search, Listener l);
    void findNearbyStops(double lat, double lon, Listener l);


    interface Listener {
        void onSuccess( Object o );
        void onFailure( Object o );
    }

}
