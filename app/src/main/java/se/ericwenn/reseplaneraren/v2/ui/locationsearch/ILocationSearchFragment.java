package se.ericwenn.reseplaneraren.v2.ui.locationsearch;

import se.ericwenn.reseplaneraren.v2.SearchField;

/**
 * Created by ericwenn on 8/2/16.
 */
public interface ILocationSearchFragment {

    /**
     * Change the searchterm in the search ui.
     * The Field is used for the controller callback to ensure correct fields are changed.
     * @param searchTerm What to search for
     */
    void changeSearchTerm(String searchTerm, SearchField f);


}
