package se.ericwenn.reseplaneraren.ui.result;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 8/2/16.
 */
public interface ITripSearchFragment {
    void changeRoute( ILocation origin, ILocation destination);
}
