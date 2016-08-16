package se.ericwenn.reseplaneraren.model.providers;

import java.util.ArrayList;
import java.util.List;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.util.DataPromise;
import se.ericwenn.reseplaneraren.util.DataPromiseImpl;

/**
 * Created by ericwenn on 8/16/16.
 */
public class LocationProvider implements ILocationProvider {
    @Override
    public DataPromise<List<ILocation>> favorites() {
        DataPromiseImpl<List<ILocation>> promise = new DataPromiseImpl<>();

        ArrayList<ILocation> favorites = new ArrayList<>();

        favorites.add(new ILocation() {
            @Override
            public String getName() {
                return "Hem";
            }

            @Override
            public Double getLatitude() {
                return null;
            }

            @Override
            public Double getLongitude() {
                return null;
            }

            @Override
            public LocationType getLocationType() {
                return null;
            }

            @Override
            public String getID() {
                return "01";
            }
        });

        favorites.add(new ILocation() {
            @Override
            public String getName() {
                return "Jobb";
            }

            @Override
            public Double getLatitude() {
                return null;
            }

            @Override
            public Double getLongitude() {
                return null;
            }

            @Override
            public LocationType getLocationType() {
                return null;
            }

            @Override
            public String getID() {
                return "02";
            }
        });


        promise.resolveData(favorites);

        return promise;

    }

    @Override
    public DataPromise<List<ILocation>> nearbyStops(double lat, double lon) {
        return null;
    }

    @Override
    public DataPromise<List<ILocation>> nearbyStops(double lat, double lon, int maxDistance) {
        return null;
    }

    @Override
    public DataPromise<List<ILocation>> find(String searchTerm) {
        return null;
    }
}
