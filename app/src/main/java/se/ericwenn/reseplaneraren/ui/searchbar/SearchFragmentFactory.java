package se.ericwenn.reseplaneraren.ui.searchbar;

import se.ericwenn.reseplaneraren.ui.LocationProvider;

/**
 * Created by ericwenn on 8/2/16.
 */
public class SearchFragmentFactory {
    public static ISearchFragment create() {
        return SearchFragment.newInstance();
    }
}
