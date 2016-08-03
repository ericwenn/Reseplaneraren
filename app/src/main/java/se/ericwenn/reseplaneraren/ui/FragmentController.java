package se.ericwenn.reseplaneraren.ui;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.model.data.ITrip;

/**
 * Created by ericwenn on 8/2/16.
 */
public interface FragmentController {

    public void onSearchTermChanged( String searchTerm, Field f);
    public void onLocationSelected(ILocation l, Field f);
    public void onTripSelected( ITrip t);


    enum Field {
        ORIGIN,
        DESTINATION
    }
}
