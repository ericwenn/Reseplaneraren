package se.ericwenn.reseplaneraren.v2.ui.search_bar;

import se.ericwenn.reseplaneraren.model.data.ILocation;

/**
 * Created by ericwenn on 11/5/16.
 */
public interface SearchFragmentController {

    void originChanged(String searchTerm);

    void destinationChanged(String searchTerm);

    void search(ILocation origin, ILocation destination);

}
