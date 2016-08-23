package se.ericwenn.reseplaneraren.ui;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.ITrip;

/**
 * Created by ericwenn on 8/2/16.
 */
public interface FragmentController {

    void onSearchTermChanged(String searchTerm, Field f);
    void onLocationSelected(ILocation l, Field f);
    void onTripSelected(ITrip t);
    void starLocation(ILocation l);


    enum Field {
        ORIGIN,
        DESTINATION
    }
}
