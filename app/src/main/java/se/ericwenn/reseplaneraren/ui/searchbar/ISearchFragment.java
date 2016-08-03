package se.ericwenn.reseplaneraren.ui.searchbar;

import se.ericwenn.reseplaneraren.model.data.ILocation;
import se.ericwenn.reseplaneraren.ui.FragmentController;

/**
 * Created by ericwenn on 8/2/16.
 */
public interface ISearchFragment {
    void setOriginLocation(ILocation origin);
    void setDestinationLocation( ILocation destination);

    void focusField( FragmentController.Field f );
    void swapSearchFields();


}
