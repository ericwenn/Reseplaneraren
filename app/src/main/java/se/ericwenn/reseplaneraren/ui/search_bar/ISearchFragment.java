package se.ericwenn.reseplaneraren.ui.search_bar;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 8/2/16.
 */
public interface ISearchFragment {

    void setOriginLocation(ILocation origin);


    void setDestinationLocation( ILocation destination);
}
