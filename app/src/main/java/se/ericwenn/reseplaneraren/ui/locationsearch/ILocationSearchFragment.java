package se.ericwenn.reseplaneraren.ui.locationsearch;

import se.ericwenn.reseplaneraren.ui.FragmentController;

/**
 * Created by ericwenn on 8/2/16.
 */
public interface ILocationSearchFragment {

    /**
     * Change the searchterm in the search ui.
     * The Field is used for the controller callback to ensure correct fields are changed.
     * @param searchTerm What to search for
     * @param f Are we looking for origin or destination
     */
    void changeSearchTerm( String searchTerm, FragmentController.Field f);


}
